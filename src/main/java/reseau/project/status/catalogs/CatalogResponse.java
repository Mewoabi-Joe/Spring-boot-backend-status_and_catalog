package reseau.project.status.catalogs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResponse {
   private String businessId;
   private String catalogId;
   private String catalogName;
   private String catalogDescription;
   private String firstImageUrl;
}
