package reseau.project.status.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int userNumber;
    private String userPhotoUrl;
    private String userBio;
    private String userName;
    private boolean isPublicAccount;

    private boolean isBusinessAccount;

    private String email;
}