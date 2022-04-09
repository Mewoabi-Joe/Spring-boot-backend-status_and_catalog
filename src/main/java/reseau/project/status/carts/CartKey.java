package reseau.project.status.carts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class CartKey {
    @Positive( message = "The userNumber should be given in body")
    @Min(value = 600000000, message = "A usersNumber key should be provided with a valid phone number as value")
    @Max(value = 700000000, message = "A usersContact key should be provided with a valid phone number as value")
    @PrimaryKeyColumn(name = "user_number", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int userNumber;

    @Positive( message = "The ownersNumber should be present in body")
    @Min(value = 600000000, message = "A ownerNumber key should be provided with a valid phone number as value")
    @Max(value = 700000000, message = "A ownerNumber key should be provided with a valid phone number as value")
    @PrimaryKeyColumn(name = "owner_number", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
private int ownerNumber;

    @NotNull(message = "categoryId must be present in request body")
    @PrimaryKeyColumn(name = "category_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
private UUID categoryId;

    @PrimaryKeyColumn(name = "item_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private UUID itemId;
}
