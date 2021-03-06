package reseau.project.status.businesses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "businesses")
public class Business {
    @Positive( message = "The number should be given")
    @Min(value = 600000000, message = "A usersNumber key should be provided with a valid phone number as value")
    @Max(value = 700000000, message = "A usersContact key should be provided with a valid phone number as value")
    @PrimaryKeyColumn(name = "user_number", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int userNumber;

    @PrimaryKeyColumn(name = "business_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID businessId;

    @NotEmpty( message = "Provide a business name")
    @Size( min = 3, message = "Atleast 3 characters businessName is required")
    private String businessName;

    @NotEmpty( message = "Provide a business description")
    @Size( min = 5, message = "Atleast 5 characters businessDescription is required")
    private String businessDescription;

    @NotEmpty( message = "Provide a business address")
    @Size( min = 5, message = "Atleast 5 characters businessLocation is required")
    private String businessLocation;

    List<String> threeImageUrls;

    @NotNull( message = "Provide atleast one item category")
    @NotEmpty( message = "Provide atleast one item category")
    private List<String> itemCategories;

    private List< @Frozen HashMap<String,List<Integer>>> openHours;

}
