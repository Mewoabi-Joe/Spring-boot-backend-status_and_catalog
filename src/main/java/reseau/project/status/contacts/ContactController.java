package reseau.project.status.contacts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllUsersAndTheirContacts(){
        return new ResponseEntity<>(contactService.getAllUsersAndTheirContacts(), HttpStatus.OK);
    }

    @GetMapping("/{userNumber}")
    public ResponseEntity<List<ContactResponse>> getContactsOfAUser(@PathVariable int userNumber){
        List<ContactResponse> contactResponses = contactService.getAllContactsOfAUser(userNumber);
        return new ResponseEntity<>(contactResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contact> addUserOneContact(@Valid @RequestBody Contact contact){
        return new ResponseEntity<>(contactService.addUserOneContact(contact), HttpStatus.CREATED);
    }

}
