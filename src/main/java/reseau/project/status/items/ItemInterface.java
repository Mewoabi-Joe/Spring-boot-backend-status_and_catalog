package reseau.project.status.items;

import org.springframework.web.multipart.MultipartFile;
import reseau.project.status.catalogs.Catalog;

import java.io.IOException;
import java.util.List;

public interface ItemInterface {
    public List<Item> getAllItemsOfACatalog(String catalogId);
    public Item getItemByCatalogAndItemId(String catalogId, String itemId);
    public Item addOrUpdateACatalogItem(String catalogId, String itemId, String itemName,MultipartFile itemImage, String itemDescription, double itemPrice, double itemRating ) throws IOException;


    Item deleteItemOfCatalog(String catalogId, String itemId);

    List<Item> getAllItemsInCatalogs();
}
