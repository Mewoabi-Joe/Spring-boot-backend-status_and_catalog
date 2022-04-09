package reseau.project.status.status;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface StatusInterface {
    public Status addAStatus(int userNumber, String statusCaption, String statusText, MultipartFile statusImage, MultipartFile statusVideo, boolean isPublicStatus, String disappearTime) throws IOException;
    public Status getStatusByUserAndPostTime(int userNumber, LocalDateTime postTime) ;
    public List<Status> getStatusOfAUser(int userNumber);

    List<Status> getStatusOfAllUsers();
}
