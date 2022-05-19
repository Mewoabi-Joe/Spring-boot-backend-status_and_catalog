package reseau.project.status.catalogs;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CatalogInterface {
   public List<Catalog> getAllCatalogsOfABusiness(String businessId);
public Catalog getCatalogByBusinessAndCatalogId(String businessId, String catalogId);
   public Catalog addOrUpdateABusinessCatalog(String catalogId, String catalogId1, String catalogName, String catalogDescription, MultipartFile firstImage) throws IOException;

    Catalog deleteCatalogOfBusiness(String businessId, String catalogId);

    List<Catalog> getAllCatalogsInBusinesses();
}
