package reseau.project.status.statusPreview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "status_preview")
public class StatusPreview {
    @Id
    @PrimaryKeyColumn(name = "user_number", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int userNumber;

    private int numberOfStatus;

    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] lastImageThumb;
    // TODO: 27-Mar-22  //Preview to display will save the last status thumb gotten from the frontend(thumb of either image,text or video)
    // For now lets just use the users profile photo as the thumb we will store the url string to access it here


    private LocalDateTime lastStatusTime;
}
