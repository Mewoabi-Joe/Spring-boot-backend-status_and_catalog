package reseau.project.status.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "users")
public class User {

    @NotBlank(message = "User number must be present")
    @Id
    @PrimaryKeyColumn(name = "user_number", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int userNumber;

    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] userPhoto;

    @Transient
    private String photoUrl;

    private String userName;

    private String userBio;

    private boolean isPublicAccount;

    private boolean isBusinessAccount;

    private String email;

    public User(int usersNumber, byte[] bytes, String userBio, String userName) {
        this.userNumber = usersNumber;
        this.userPhoto = bytes;
        this.userBio = userBio;
        this.userName = userName;
    }
}
