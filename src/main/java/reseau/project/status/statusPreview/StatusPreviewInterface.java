package reseau.project.status.statusPreview;
import reseau.project.status.users.User;

import java.time.LocalDateTime;
import java.util.List;

public interface StatusPreviewInterface {
    // TODO: 26-Mar-22 : Normally we need to be getting the last image thumb to updata aswell.
    public StatusPreview deleteStatusPreview(int userNumber);

    List<StatusPreview> getAllStatusPreviews();

    StatusPreview getOneStatusPreview(int userNumber);

    List<StatusPreview> getPermittedStatusPreviewsForUser(int userNumber);
}
