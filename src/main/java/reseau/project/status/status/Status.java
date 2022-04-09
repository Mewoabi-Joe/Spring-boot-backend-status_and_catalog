package reseau.project.status.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "status")
public class Status {

    @Positive(message = "A userNumber key must be provided in body")
    @Min(value = 600000000, message = "userNumber should be of correct phone number format")
    @Max(value = 700000000, message = "userNumber should be of correct phone number format")

    @PrimaryKeyColumn(name = "user_number", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int userNumber;

    @PrimaryKeyColumn(name = "post_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime postTime;

    private String statusCaption;

    private String statusText;

    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] statusImage;

    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] statusVideo;

    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private boolean isPublicStatus; //Lets say the default(false) for private true: public.

    private LocalDateTime disappearTime;   //We could use duration


}
