package reseau.project.status.items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "items")
public class Item {
    @NotNull( message = "A CatalogId has to be provided in the body ")
    @PrimaryKeyColumn(name = "catalog_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID catalogId;

    @NotNull( message = "An itemId has to be provided in the body ")
    @PrimaryKeyColumn(name = "item_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID itemId;

    @Size( min = 3, message = "Atleast 3 characters itemName is required")
    private String itemName;

    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] itemImage;

    private String itemDescription;

    private double itemPrice;

    private double itemRating;

}
