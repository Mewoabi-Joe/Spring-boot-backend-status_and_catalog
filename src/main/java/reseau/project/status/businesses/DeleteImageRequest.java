package reseau.project.status.businesses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  DeleteImageRequest {
    @Positive( message = "The number should be given")
    @Min(value = 600000000, message = "A usersNumber key should be provided with a valid phone number as value")
    private int userNumber;

    @NotNull( message = "A businessId has to be provided in the body ")
    private UUID businessId;

    @NotEmpty( message = "An imageUrl to be provided in the body ")
    @Size( min = 10, message = "a valid imageUrl is required")
    private String imageUrl;


}
