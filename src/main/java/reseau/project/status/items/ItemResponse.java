package reseau.project.status.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
   private String catalogId;
   private String itemId;
   private String itemName;
   private String itemImage;
   private String itemDescription;
   private double itemPrice;
   private int itemRating;
}
