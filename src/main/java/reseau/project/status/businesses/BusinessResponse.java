package reseau.project.status.businesses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponse {
    private int UserNumber;
    private UUID businessId;
    private String businessName;
    private String businessDescription;
    private String businessLocation;
    private String firstImage;
    private String secondImage;
    private String thirdImage;
    private List<String> itemCategories;
    private List<HashMap<String,List<Integer>>> openHours;
}
