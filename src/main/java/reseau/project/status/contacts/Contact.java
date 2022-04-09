package reseau.project.status.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "contacts")
public class Contact {
    @Positive( message = "The number should be given")
    @Min(value = 600000000, message = "A usersNumber key should be provided with a valid phone number as value")
    @Max(value = 700000000, message = "A usersContact key should be provided with a valid phone number as value")
    @PrimaryKeyColumn(name = "user_number", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int userNumber;

    @Min(value = 600000000, message = "A usersContact key should be provided with a valid phone number as value")
    @Max(value = 700000000, message = "A usersContact key should be provided with a valid phone number as value")
    @PrimaryKeyColumn(name = "users_contact", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private int usersContact;


    @NotNull(message = "givenName must be present")
    private String givenName;

}
