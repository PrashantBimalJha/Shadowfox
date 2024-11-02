import java.io.*;
import java.util.*;

class PersonContact {
    private String name;
    private String phone;
    private String email;

    public PersonContact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
    }
}

public class ContactName {
    private ArrayList<PersonContact> contactList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private final String filePath = "contacts.txt";

    public static void main(String[] args) {
        ContactName system = new ContactName();
        system.loadContactsFromFile();
        system.run();
    }

    public void run() {
        int option;
        do {
            System.out.println("\nContact Management System:");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Search Contact");
            System.out.println("6. Sort Contacts by Name");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    searchContact();
                    break;
                case 6:
                    sortContactsByName();
                    break;
                case 7:
                    saveContactsToFile();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (option != 7);
    }

    // Feature 1: Add Contact with validation and duplicate check
    private void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (!validatePhone(phone)) {
            System.out.println("Invalid phone number. It should be 10 digits.");
            return;
        }
        if (!validateEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        if (isDuplicate(phone)) {
            System.out.println("Contact with this phone number already exists.");
            return;
        }

        PersonContact contact = new PersonContact(name, phone, email);
        contactList.add(contact);
        System.out.println("Contact added successfully.");
    }

    // Feature 2: View Contacts
    private void viewContacts() {
        if (contactList.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("\nList of Contacts:");
            for (int i = 0; i < contactList.size(); i++) {
                System.out.println((i + 1) + ". " + contactList.get(i));
            }
        }
    }

    // Feature 3: Update Contact
    private void updateContact() {
        System.out.print("Enter the contact number to update: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (index <= 0 || index > contactList.size()) {
            System.out.println("Invalid contact number.");
        } else {
            PersonContact contact = contactList.get(index - 1);

            System.out.print("Enter new name (leave blank to keep unchanged): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                contact.setName(name);
            }

            System.out.print("Enter new phone number (leave blank to keep unchanged): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty() && validatePhone(phone)) {
                contact.setPhone(phone);
            }

            System.out.print("Enter new email (leave blank to keep unchanged): ");
            String email = scanner.nextLine();
            if (!email.isEmpty() && validateEmail(email)) {
                contact.setEmail(email);
            }

            System.out.println("Contact updated successfully.");
        }
    }

    // Feature 4: Delete Contact
    private void deleteContact() {
        System.out.print("Enter the contact number to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (index <= 0 || index > contactList.size()) {
            System.out.println("Invalid contact number.");
        } else {
            contactList.remove(index - 1);
            System.out.println("Contact deleted successfully.");
        }
    }

    // Feature 5: Search Contact
    private void searchContact() {
        System.out.print("Enter name, phone, or email to search: ");
        String query = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (PersonContact contact : contactList) {
            if (contact.getName().toLowerCase().contains(query) || 
                contact.getPhone().contains(query) || 
                contact.getEmail().toLowerCase().contains(query)) {
                System.out.println(contact);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No contact found.");
        }
    }

    // Feature 6: Sort Contacts by Name
    private void sortContactsByName() {
        Collections.sort(contactList, Comparator.comparing(PersonContact::getName));
        System.out.println("Contacts sorted by name.");
    }

    // Validate phone number (digits only)
    private boolean validatePhone(String phone) {
        return phone.matches("\\d{10}");
    }

    // Validate email format
    private boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Check for duplicate phone number
    private boolean isDuplicate(String phone) {
        for (PersonContact contact : contactList) {
            if (contact.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    // Feature 7: Save contacts to file for persistence
    private void saveContactsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (PersonContact contact : contactList) {
                writer.write(contact.getName() + "," + contact.getPhone() + "," + contact.getEmail() + "\n");
            }
            System.out.println("Contacts saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving contacts to file.");
        }
    }

    // Load contacts from file on startup
    private void loadContactsFromFile() {
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] details = line.split(",");
                    if (details.length == 3) {
                        contactList.add(new PersonContact(details[0], details[1], details[2]));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading contacts from file.");
            }
        }
    }
}
