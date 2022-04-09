package reseau.project.status.catalogs;

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
@Table(value = "catalogs")
public class Catalog {
    @NotNull( message = "A BusinessId has to be provided in the body ")
    @PrimaryKeyColumn(name = "business_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID businessId;

    @NotNull( message = "A CatalogId has to be provided in the body ")
    @PrimaryKeyColumn(name = "catalog_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID catalogId;

    @Size( min = 3, message = "Atleast 3 characters CatalogName is required")
    private String catalogName;

    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] firstImage;

    private String catalogDescription;

}
