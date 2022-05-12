package reseau.project.status.items;


import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reseau.project.status.catalogs.Catalog;
import reseau.project.status.exceptions.BadRequestException;
import reseau.project.status.exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ItemService implements ItemInterface {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItemsOfACatalog(String catalogId) {
        return itemRepository.findAllByCatalogId(UUID.fromString(catalogId));
    }

    @Override
    public Item getItemByCatalogAndItemId(String catalogId, String itemId) {
        UUID cID = null;
        UUID iID = null;
        try {
            cID = UUID.fromString(catalogId);
            iID = UUID.fromString(itemId);
        } catch (Exception e) {
            throw new BadRequestException("Wrong catalog or item Id provided");
        }

        Optional<Item> optionalItem = itemRepository.findByCatalogIdAndItemId(cID, iID);
        if (!optionalItem.isPresent()) throw new NotFoundException("Could not find a item with the info you provided");
        return optionalItem.get();
    }

    @Override
    public Item addOneItemToCatalog(String catalogId, String itemId1, String itemName, MultipartFile itemImage, String itemDescription, double itemPrice, double itemRating) throws IOException {
        UUID itemId;
        if (itemId1 == null) {
            itemId = Uuids.timeBased();
        } else {
            itemId = UUID.fromString(itemId1);
        }
        return itemRepository.save(new Item(UUID.fromString(catalogId), itemId, itemName, itemImage.getBytes(), itemDescription, itemPrice, itemRating));
    }

    @Override
    public Item deleteItemOfCatalog(String catalogId, String itemId) {
        Item item = validateSequenceCatalogAndItemId(UUID.fromString(catalogId), UUID.fromString(itemId));
        itemRepository.deleteByCatalogIdAndItemId(UUID.fromString(catalogId), UUID.fromString(itemId));
        return item;
    }

    public void validateCatalogId(UUID catalogId){
        if(itemRepository.findByCatalogId(catalogId).size() == 0) throw new BadRequestException("No item with such an id exist");
    }

    public Item validateCatalogAndItemId(UUID catalogId, UUID itemId){
        Optional<Item> optionalItem = itemRepository.findByCatalogIdAndItemId(catalogId, itemId);
        if(!optionalItem.isPresent()) throw new NotFoundException("that Catalog does not have an item with that id");
        return optionalItem.get();
    }

    public Item validateSequenceCatalogAndItemId(UUID catalogId, UUID itemId){
        validateCatalogId(catalogId);
        return validateCatalogAndItemId(catalogId,itemId);
    }

}
