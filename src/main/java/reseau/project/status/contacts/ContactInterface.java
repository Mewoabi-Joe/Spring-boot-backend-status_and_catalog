package reseau.project.status.contacts;

import java.util.List;

public interface ContactInterface {
    public List<Contact> getAllUsersAndTheirContacts();
    public Contact addUserOneContact(Contact contact);
    public List<ContactResponse> getAllContactsOfAUser(int userNumber);
}
