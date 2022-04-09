package reseau.project.status.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    private int userNumber;
    private int usersContact;
    private String givenName;
    private String contactPhotoURL;
    private String contactBio;
}
