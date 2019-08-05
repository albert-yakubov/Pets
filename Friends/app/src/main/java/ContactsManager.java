

import com.example.friends.Contact;

import java.util.ArrayList;

public class ContactsManager {
    private ArrayList<Contact> contacts;

    public ContactsManager(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ContactsManager() {
        this.contacts = new ArrayList<Contact>( );
    }

    public ArrayList<Contact> getContacts() {
        return contacts;

    }

    public void createContact(String name, String phoneNumber) {
        this.contacts.add(new Contact(name, phoneNumber));
    }

    public Contact getLast() {
        return contacts.get(contacts.size( )-1);

    }
}
