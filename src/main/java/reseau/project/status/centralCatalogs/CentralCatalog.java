package reseau.project.status.centralCatalogs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "central_catalogs")
public class CentralCatalog {
    @NotEmpty( message = "A catalogName has to be provided in the body ")
    @Size( min = 3, message = "Atleast 3 characters catalogName is required")
    @PrimaryKeyColumn(name = "catalog_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String catalogName;

    @Size( min = 3, message = "Atleast 3 characters itemName is required")
    private String itemName;

    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] itemImage;

    private String description;

    private double price;

    private int rating;


    @Positive(message = "A userNumber key must be provided in body")
    @Min(value = 600000000, message = "userNumber should be of correct phone number format")
    @Max(value = 700000000, message = "userNumber should be of correct phone number format")
    private int owner;

}
