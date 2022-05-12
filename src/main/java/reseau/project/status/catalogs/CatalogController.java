package reseau.project.status.catalogs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reseau.project.status.businesses.Business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_TYPE;

@RestController
@Slf4j
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @PostMapping
    public ResponseEntity<CatalogResponse> addOneCatalogToBusiness(
            @RequestParam(required = true) String businessId,
            @RequestParam(required = false) String catalogId,
            @RequestParam(required = true) String catalogName,
            @RequestParam(required = false) String catalogDescription,
            @RequestParam(required = true) MultipartFile firstImageUrl
    ) throws IOException {
//        log.info("IN BUSINESS SERVICE ADDING BUSINESS:");
//        log.info("userNumber: "+ catalog.getUserNumber() );
//        log.info("catalogName: "+ catalog.getCatalogName() );
//        log.info("catalogDescription: "+ catalog.getDescription() );
//        log.info("location: "+ catalog.getLocation() );
//        log.info("itemCategorie: "+ catalog.getItemCategories() );
//        log.info("openHours: "+ catalog.getOpenHours() );

        Catalog catalog = catalogService.addOneCatalogToBusiness(businessId, catalogId, catalogName, catalogDescription, firstImageUrl);

        CatalogResponse catalogResponse = new CatalogResponse(catalog.getBusinessId().toString(), catalog.getCatalogId().toString(), catalog.getCatalogName(), catalog.getCatalogDescription(), getImageUrl(catalog.getBusinessId().toString(), catalog.getCatalogId().toString()));
        return new ResponseEntity<>(catalogResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view_image/{businessId}")
    public ResponseEntity<Resource> viewImage(@PathVariable String businessId,
                                              @RequestParam() String catalogId
                                               ) { //MUST USE STRING HERE
        Catalog catalog = catalogService.getCatalogByBusinessAndCatalogId(businessId, catalogId);
        int fileSize = 0;
        ByteArrayResource byteArrayResource = null;
            fileSize = catalog.getFirstImage().length;
            byteArrayResource = new ByteArrayResource(catalog.getFirstImage());

        return ResponseEntity.ok()
                .header(CONTENT_TYPE, "image/jpg")
                .header(CONTENT_LENGTH, String.valueOf(fileSize))
                .body(byteArrayResource);
    }

    public String getImageUrl(String businessId, String catalogId ){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("catalog/view_image/")
                .path(businessId)
                .toUriString()+"?catalogId=" + catalogId;
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<List<CatalogResponse>> getAllCatalogsOfABusiness(
            @PathVariable String businessId
){
        List<Catalog> catalogs = catalogService.getAllCatalogsOfABusiness(businessId);
        List<CatalogResponse> catalogResponses = new ArrayList<>();
        catalogs.forEach(catalog -> {
            catalogResponses.add(new CatalogResponse(catalog.getBusinessId().toString(), catalog.getCatalogId().toString(),catalog.getCatalogName(),catalog.getCatalogDescription(),getImageUrl(catalog.getBusinessId().toString(), catalog.getCatalogId().toString())));
        });

        return new ResponseEntity<>(catalogResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{businessId}")
    public ResponseEntity<CatalogResponse> deleteABusinessCatalog(@PathVariable String businessId,
                                                         @RequestParam String catalogId){
        log.info("ARRIVED HERE In Delete : businessId: " + businessId + " catalogId: " + catalogId);

        Catalog catalog = catalogService.deleteCatalogOfBusiness(businessId, catalogId);

        CatalogResponse catalogResponse = new CatalogResponse(catalog.getBusinessId().toString(), catalog.getCatalogId().toString(), catalog.getCatalogName(), catalog.getCatalogDescription(), getImageUrl(catalog.getBusinessId().toString(), catalog.getCatalogId().toString()));
        return new ResponseEntity<>(catalogResponse, HttpStatus.OK);

    }

}
