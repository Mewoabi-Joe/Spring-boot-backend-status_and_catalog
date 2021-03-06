package reseau.project.status.catalogs;


import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reseau.project.status.businesses.Business;
import reseau.project.status.exceptions.BadRequestException;
import reseau.project.status.exceptions.NotFoundException;
import reseau.project.status.items.ItemRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class CatalogService implements CatalogInterface {
    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Catalog> getAllCatalogsOfABusiness(String businessId) {
        return catalogRepository.findAllByBusinessId(UUID.fromString(businessId));
    }

    @Override
    public Catalog getCatalogByBusinessAndCatalogId(String businessId, String catalogId) {
        UUID bID = null;
        UUID cID = null;
        try{
            bID = UUID.fromString(businessId);
            cID = UUID.fromString(catalogId);
        }catch (Exception e){
            throw new BadRequestException("Wrong business or catalog Id provided");
        }

        Optional<Catalog> optionalCatalog = catalogRepository.findByBusinessIdAndCatalogId(bID, cID);
        if(!optionalCatalog.isPresent()) throw new NotFoundException("Could not find a catalog with the info you provided");
        return optionalCatalog.get();
    }

    @Override
    public Catalog addOrUpdateABusinessCatalog(String businessId, String catalogId1, String catalogName, String catalogDescription, MultipartFile firstImage) throws IOException {
        UUID catalogId;
        if(catalogId1 == null || catalogId1.equalsIgnoreCase("")){
            if(firstImage == null){
                throw new BadRequestException("provide an image for the catalog");
            }
            catalogId = Uuids.timeBased();
        }else{
           Optional<Catalog> existingCatalog =  catalogRepository.findByBusinessIdAndCatalogId(UUID.fromString(businessId), UUID.fromString(catalogId1));
           if(existingCatalog.isPresent()){
               Catalog theCatalog = existingCatalog.get();
               theCatalog.setCatalogDescription(catalogDescription);
               theCatalog.setCatalogName(catalogName);
               if(firstImage != null) theCatalog.setFirstImage(firstImage.getBytes());
               return catalogRepository.save(theCatalog);
           }else{
               throw new BadRequestException("No business with that Id has a catalog with that ID");
           }
        }
        return catalogRepository.save(new Catalog(UUID.fromString(businessId),catalogId, catalogName, firstImage.getBytes(),catalogDescription ));
    }

    public void validateBusinessId(UUID businessId){
        if(catalogRepository.findByBusinessId(businessId).size() == 0) throw new BadRequestException("No catalog with such an id exist");
    }

    public Catalog validateBusinessAndCatalogId(UUID businessId, UUID catalogId){
        Optional<Catalog> optionalCatalog = catalogRepository.findByBusinessIdAndCatalogId(businessId, catalogId);
        if(!optionalCatalog.isPresent()) throw new NotFoundException("that Business does not have an catalog with that id");
        return optionalCatalog.get();
    }

    public Catalog validateSequenceBusinessAndCatalogId(UUID businessId, UUID catalogId){
        validateBusinessId(businessId);
       return validateBusinessAndCatalogId(businessId,catalogId);
    }

    @Override
    public Catalog deleteCatalogOfBusiness(String businessId, String catalogId) {
        Catalog catalog = validateSequenceBusinessAndCatalogId(UUID.fromString(businessId), UUID.fromString(catalogId));
        itemRepository.deleteByCatalogId(UUID.fromString(catalogId));
        catalogRepository.deleteByBusinessIdAndCatalogId(UUID.fromString(businessId), UUID.fromString(catalogId));
        return catalog;
    }

    @Override
    public List<Catalog> getAllCatalogsInBusinesses() {
        return catalogRepository.findAll();
    }
}
