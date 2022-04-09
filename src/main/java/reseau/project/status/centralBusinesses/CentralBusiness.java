package reseau.project.status.centralBusinesses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "central_businesses")
public class CentralBusiness {
    @NotEmpty( message = "A businessName has to be provided in the body ")
    @Size( min = 3, message = "Atleast 3 characters businessName is required")
    @PrimaryKeyColumn(name = "business_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String businessName;

    private String description;

    private String location;


    private String threeImageUrls;

    private List<String> itemCategories;

    private List< @Frozen HashMap<String,List<Integer>>> openHours;

}
