package reseau.project.status.users;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserInterface {

    User createUpdateUser(int usersNumber, MultipartFile file, String userBio, String userName, String isBusinessAccount) throws Exception;

    public User getOneUser(int userNumber);

    public List<User> getAllUsers();

    public User deleteOneUser(int userNumber);
}
