package reseau.project.status.contacts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reseau.project.status.exceptions.BadRequestException;
import reseau.project.status.exceptions.NotFoundException;
import reseau.project.status.users.User;
import reseau.project.status.users.UserRepository;
import reseau.project.status.users.UserResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ContactService implements ContactInterface {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Contact> getAllUsersAndTheirContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact addUserOneContact(Contact contact) {
        int userNumber = contact.getUserNumber();
        int usersContact = contact.getUsersContact();
            if (!(userRepository.findById(userNumber).isPresent())) throw new BadRequestException("The user with number: " + userNumber + " does not have an account on our platform.");

            if (!(userRepository.findById(usersContact).isPresent())) throw new BadRequestException("The user with number: " + usersContact + " does not have an account on our platform.");
            if(userNumber == usersContact) throw new BadRequestException("A users contact should be a different user");
            if (contact.getGivenName() == null || contact.getGivenName().trim().length() < 3) throw new BadRequestException("Your contact must have a name of atleast 3 characters");
            return contactRepository.save(contact);
    }

    @Override
    public List<ContactResponse> getAllContactsOfAUser(int userNumber) {
        List<Contact> usersContacts = contactRepository.findAllByUserNumber(userNumber);
        List<ContactResponse> contactResponses = new ArrayList<>();
        usersContacts.forEach(contact -> {
            User theUser = userRepository.findById(contact.getUsersContact()).get();
            String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/user/view/")
                    .path(Integer.toString(theUser.getUserNumber()))
                    .toUriString();
            contactResponses.add(new ContactResponse(contact.getUserNumber(), contact.getUsersContact(),
                    contact.getGivenName(), downloadURl, theUser.getUserBio()));
        });

        return contactResponses;
    }
}
