package reseau.project.status.items;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reseau.project.status.catalogs.Catalog;
import reseau.project.status.catalogs.CatalogResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_TYPE;

@RestController
@Slf4j
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> addOneItemToCatalog(
            @RequestParam(required = true) String catalogId,
            @RequestParam(required = false) String itemId,
            @RequestParam(required = true) String itemName,
            @RequestParam(required = true) MultipartFile itemImage,
            @RequestParam(required = false) String itemDescription,
            @RequestParam(required = false) double itemPrice,
            @RequestParam(required = false) int itemRating
    ) throws IOException {
//        log.info("IN BUSINESS SERVICE ADDING BUSINESS:");
//        log.info("userNumber: "+ item.getUserNumber() );
//        log.info("itemName: "+ item.getItemName() );
//        log.info("itemDescription: "+ item.getDescription() );
//        log.info("location: "+ item.getLocation() );
//        log.info("itemCategorie: "+ item.getItemCategories() );
//        log.info("openHours: "+ item.getOpenHours() );

        Item item = itemService.addOneItemToCatalog(catalogId, itemId, itemName, itemImage, itemDescription, itemPrice, itemRating);

        ItemResponse itemResponse = new ItemResponse(item.getCatalogId().toString(), item.getItemId().toString(), item.getItemName(),getImageUrl(item.getCatalogId().toString(), item.getItemId().toString()),item.getItemDescription(), item.getItemPrice(),item.getItemRating() );
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view_image/{catalogId}")
    public ResponseEntity<Resource> viewImage(@PathVariable String catalogId,
                                              @RequestParam() String itemId
    ) { //MUST USE STRING HERE

        Item item = itemService.getItemByCatalogAndItemId(catalogId, itemId);
        int fileSize = 0;
        ByteArrayResource byteArrayResource = null;
        fileSize = item.getItemImage().length;
        byteArrayResource = new ByteArrayResource(item.getItemImage());

        return ResponseEntity.ok()
                .header(CONTENT_TYPE, "image/jpg")
                .header(CONTENT_LENGTH, String.valueOf(fileSize))
                .body(byteArrayResource);
    }

    public String getImageUrl(String catalogId, String itemId ){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("item/view_image/")
                .path(catalogId)
                .toUriString()+"?itemId=" + itemId;
    }

    @GetMapping("/{catalogId}")
    public ResponseEntity<List<ItemResponse>> getAllItemsOfACatalog(
            @PathVariable String catalogId
    ){
        List<Item> items = itemService.getAllItemsOfACatalog(catalogId);
        List<ItemResponse> itemResponses = new ArrayList<>();
        items.forEach(item -> {
            itemResponses.add(new ItemResponse(item.getCatalogId().toString(), item.getItemId().toString(), item.getItemName(),getImageUrl(item.getCatalogId().toString(), item.getItemId().toString()),item.getItemDescription(), item.getItemPrice(),item.getItemRating() ));
        });

        return new ResponseEntity<>(itemResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{catalogId}")
    public ResponseEntity<ItemResponse> deleteABusinessItem(@PathVariable String catalogId,
                                                                  @RequestParam String itemId){
        log.info("ARRIVED HERE In Delete : catalogId: " + catalogId + " itemId: " + itemId);

        Item item = itemService.deleteItemOfCatalog(catalogId, itemId);

        ItemResponse itemResponse = new ItemResponse(item.getCatalogId().toString(), item.getItemId().toString(), item.getItemName(),getImageUrl(item.getCatalogId().toString(), item.getItemId().toString()),item.getItemDescription(),item.getItemPrice(),item.getItemRating() );
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);

    }
}
