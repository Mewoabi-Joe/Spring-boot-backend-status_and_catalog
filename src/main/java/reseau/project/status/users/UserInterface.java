package reseau.project.status.users;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserInterface {
    public User createUser(int userNumber, MultipartFile file, String userBio, String userName) throws Exception;

    public User getOneUser(int userNumber);

    public List<User> getAllUsers();

    public User deleteOneUser(int userNumber);
}
