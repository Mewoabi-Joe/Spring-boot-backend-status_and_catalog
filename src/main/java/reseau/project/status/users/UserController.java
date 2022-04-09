package reseau.project.status.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.CONTENT_TYPE;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> {
            String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/user/view/")
                    .path(Integer.toString(user.getUserNumber()))
                    .toUriString();

            UserResponse userResponse = new UserResponse(user.getUserNumber(), downloadURl, user.getUserBio(), user.getUserName(), user.isPublicAccount(), user.isBusinessAccount(), user.getEmail());
            userResponses.add(userResponse);
        });

        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/{userNumber}")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable int userNumber) {
        log.info("IN GET ONE USER CONTROLLER NUMBER IS: " + userNumber);
        User user = userService.getOneUser(userNumber);
        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/view/")
                .path(Integer.toString(user.getUserNumber()))
                .toUriString();

        UserResponse userResponse = new UserResponse(user.getUserNumber(), downloadURl, user.getUserBio(), user.getUserName(), user.isPublicAccount(), user.isBusinessAccount(), user.getEmail());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/view/{userNumber}")
    public ResponseEntity<Resource> viewFile(@PathVariable int userNumber){
        log.info("ARRIVED HERE ");
        User user = userService.getOneUser(userNumber);
        int fileSize = user.getUserPhoto().length;
        return ResponseEntity.ok()
                .header(CONTENT_TYPE, "image/jpg")
                .header(CONTENT_LENGTH, String.valueOf(fileSize))
                .body(new ByteArrayResource(user.getUserPhoto()));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestParam("userNumber") int userNumber,
            @RequestParam("userName") String userName,
            @RequestParam("userPhoto") MultipartFile file,
            @RequestParam("userBio") String userBio) throws Exception {

        User user = userService.createUser(userNumber, file, userBio, userName);
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //Can always use if I need file name
        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("user/view/")
                .path(Integer.toString(user.getUserNumber()))
                .toUriString();

        UserResponse userResponse = new UserResponse(
                user.getUserNumber(),
//                fileName,                      NOTE: I can always add them in the future if I need to. but I will need to add the fields to the user class so that they can be persisted in the data base
//                file.getContentType(),                CONSULT INBOX APP FOR ATRIBUTE DETAILS
//                file.getSize(),
                downloadURl,
                user.getUserBio()
        ,user.getUserName()
        , user.isPublicAccount(), user.isBusinessAccount(), user.getEmail());

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userNumber}")
    public ResponseEntity<UserResponse> deleteOneUser(@PathVariable int userNumber){
        User deletedUser = userService.deleteOneUser(userNumber);
        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/view/")
                .path(Integer.toString(deletedUser.getUserNumber()))
                .toUriString();

        UserResponse userResponse = new UserResponse(deletedUser.getUserNumber(), downloadURl, deletedUser.getUserBio(), deletedUser.getUserName(), deletedUser.isPublicAccount(), deletedUser.isBusinessAccount(), deletedUser.getEmail());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
