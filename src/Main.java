/*
 * Requirements:
 *    hardcode examples of all three classes being linked
 *    be able to add new Person
 *    be able to add new Company
 *    be able to add new Link of Person + Company
 *    be able to edit Company.name
 *    be able to edit Person.name
 *    be able to edit Link...
 *            I think this means only ever editing Link.companyId ... what company the person works for
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Company> companyDB = new ArrayList<>();
    private static ArrayList<Person> personDB = new ArrayList<>();
    private static ArrayList<Link> linkDB = new ArrayList<>();

    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args){

        // HARDCODING + PRINTING

        // call the DB constructor methods
        createCompanyDB();
        createPersonDB();
        createLinkDB();

        // display our constructed information
        // showAllLinksByCompany();
        // showAllLinksWithSpacing();
        // showAllLinks();

        // ADDING a Person, a Company, or a Link...
        // EDIT a Person, or a Company...
        String userStr;
        do {
            // get user input
            System.out.print("Would you like to...\n" +
                        "\t[\"P\"] add a Person\n" +
                        "\t[\"C\"] add a Company\n" +
                        "\t[\"L\"] add a Link\n" +
                        "\t[\"E\"] Edit some information\n" +
                        "\t[\"Q\"] to Quit\n" +
                    "Your Choice: ");
            userStr = keyboard.nextLine();
            // process user choice
            if (userStr.equalsIgnoreCase("P")) {
                createNewPerson();
                showPersonDB();
            } else if (userStr.equalsIgnoreCase("C")) {
                createNewCompany();
                showCompanyDB();
            } else if (userStr.equalsIgnoreCase("L")) {
                createNewLink();
                showLinkDB();
            } else if (userStr.equalsIgnoreCase("E")) {
                System.out.print("Would you like to Edit a Person (\"P\") or a Company (\"C\"), or enter \"Q\" to quit: ");
                userStr = keyboard.nextLine();
                if(userStr.equalsIgnoreCase("P")){
                    editPerson();
                } else if(userStr.equalsIgnoreCase("C")) {
                    editCompany();
                } else if(userStr.equalsIgnoreCase("Q")) {
                    break;
                }
            }
            else if (userStr.equalsIgnoreCase("Q")) {
                break;
            }
        } while (true);

        keyboard.close();
    }

    private static void editPerson(){
        System.out.println("Enter the id of the Person you'd like to Edit...");
        showPersonDB();
        System.out.print("Your choice: ");
        long userId = keyboard.nextLong();
        keyboard.nextLine();
        Person myPerson = null;
        for(Person person : personDB){
            if(person.getId() == userId) {
                myPerson = person;
            }
        }
        if (myPerson != null) {
            System.out.print("Please enter a new Name: ");
            String userStr = keyboard.nextLine();
            myPerson.setName(userStr);
            System.out.println("Successfully changed!");
            System.out.println("\tId: " + myPerson.getId() + " Name: " + myPerson.getName());
        } else {
            System.out.println("Sorry, couldn't find that Person!");
        }
    }

    private static void editCompany(){
        System.out.println("Enter the id of the Company you'd like to Edit...");
        showCompanyDB();
        System.out.print("Your choice: ");
        long companyId = keyboard.nextLong();
        keyboard.nextLine();
        Company myCompany = null;
        for(Company company : companyDB){
            if(company.getId() == companyId) {
                myCompany = company;
            }
        }
        if (myCompany != null) {
            System.out.print("Please enter a new Name: ");
            String userStr = keyboard.nextLine();
            myCompany.setName(userStr);
            System.out.println("Successfully changed!");
            System.out.println("\tId: " + myCompany.getId() + " Name: " + myCompany.getName());
        } else {
            System.out.println("Sorry, couldn't find that Company!");
        }
    }

    private static void createNewLink(){
        long personId;
        System.out.println("Please enter the id of the Person you wish to Link...");
        showPersonDB();
        System.out.print("Your choice: ");
        personId = keyboard.nextLong();
        keyboard.nextLine();
        String personName = lookUpPersonName(personId);

        long companyId;
        System.out.println("Please enter the id of the Company you wish to Link with " + personName);
        showCompanyDB();
        System.out.print("Your choice: ");
        companyId = keyboard.nextLong();
        keyboard.nextLine();
        String companyName = lookUpCompanyName(companyId);

        System.out.println("We are linking " + personName + " and " + companyName);
        System.out.print("Enter the Start Date for " + personName + " at " + companyName + ": ");
        String startDate = keyboard.nextLine();

        System.out.println("Enter the Role " + personName + " has at " + companyName + ": ");
        String role = keyboard.nextLine();

        long id = linkDB.size()+1;
        linkDB.add(new Link(id, companyId, personId, startDate, role));
    }

    private static void createNewCompany(){
        String name;
        System.out.println("Please enter the Company's name: ");
        name = keyboard.nextLine();
        long id = companyDB.size()+1;
        companyDB.add(new Company(id, name));
    }

    private static void createNewPerson(){
        String name;
        System.out.print("Please enter the Person's name: ");
        name = keyboard.nextLine();
        long id = personDB.size()+1;
        personDB.add(new Person(id, name));
    }

    private static void showPersonDB(){
        // print all people
        // System.out.println("Person DB:");
        for(Person person : personDB){
            System.out.println("\tId: " + person.getId() + " Name: " + person.getName());
        }
    }

    private static void showCompanyDB(){
        // print all companies
        // System.out.println("Company DB: ");
        for(Company company: companyDB){
            System.out.println("\tId: " + company.getId() + " Name: " + company.getName());
        }
    }

    private static void showLinkDBByCompany(){
        // be able to print out who's working at what company...
        // System.out.println("\nLooping Over Companies!\n");
        for (Company company : companyDB) {
            System.out.println(company.getName() + ":\n");
            // loop through all links
            for (Link link : linkDB) {
                // see if the link belongs to current company
                if (link.getCompanyId() == company.getId()) {
                    // loop through all people
                    for (Person person : personDB) {
                        // see if person belongs to current link
                        if (person.getId() == link.getPersonId()) {
                            System.out.println("\t" + person.getName() + " :: "
                                    + link.getStartDate() + ", " + link.getRole());
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    private static String lookUpCompanyName(long companyId){
        for (Company company : companyDB) {
            if (company.getId() == companyId) {
                return company.getName();
            }
        }
        return "Unknown";
    }

    private static String lookUpPersonName(long personId){
        for (Person person : personDB) {
            if (person.getId() == personId) {
                return person.getName();
            }
        }
        return "Unknown";
    }


    private static void showLinkDBWithSpacing(){
        // System.out.print("\nLooping Over Links! Extra new line every time the company changes! \n");
        long rememberMe = 0;
        for (Link link : linkDB) {
            if (rememberMe != link.getCompanyId()) {
                // this spaces it out every time the company changes...
                System.out.println();
            }
            System.out.println(lookUpCompanyName(link.getCompanyId()) + " + "
                    + lookUpPersonName(link.getPersonId()) + " :: "
                    + link.getStartDate() + ", " + link.getRole());
            rememberMe = link.getCompanyId();
        }
    }

    private static void showLinkDB(){
        // System.out.println("\nLooping Over Links! AKA VICTOR's WAY! \n");
        for (Link link : linkDB) {
            System.out.println(lookUpCompanyName(link.getCompanyId()) + " + "
                    + lookUpPersonName(link.getPersonId()) + " :: "
                    + link.getStartDate() + ", " + link.getRole());
        }
    }

    private static void createCompanyDB(){
        companyDB.add(new Company(1, "Apple"));
        companyDB.add(new Company(2, "Microsoft"));
        companyDB.add(new Company(3, "Montgomery College"));
    }

    private static void createPersonDB(){
        personDB.add(new Person(1, "Mina"));
        personDB.add(new Person(2, "Pierz"));
        personDB.add(new Person(3, "Tony"));
        personDB.add(new Person(4, "Victor"));
        personDB.add(new Person(5, "Linh"));
        personDB.add(new Person(6, "Yousef"));
        personDB.add(new Person(7, "Feng"));
        personDB.add(new Person(8, "Kenisha"));
    }

    private static void createLinkDB(){
        // 1 == Apple || 2 == Microsoft || 3 == MC
        // 1 == Mina || 2 == Pierz || 3 == Tony || 4 == Victor || 5 == Linh || 6 == Yousef
        //          7 == Feng || 8 == Kenisha
        linkDB.add(new Link(1, 1, 1, "June 2018", "Awesome Software Developer"));
        linkDB.add(new Link(2, 1, 2, "July 2019", "Another amazing Developer"));
        linkDB.add(new Link(3, 3, 3, "December 2020", "Instructor"));
        linkDB.add(new Link(4, 2, 4, "December 2020", "Developer"));
        linkDB.add(new Link(5, 2, 5, "August 2019", "Microsoft Maven"));
        linkDB.add(new Link(6, 2, 6, "November 2019", "Data Maven"));
        linkDB.add(new Link(7, 3, 7, "July 2020", "Instructor"));
        linkDB.add(new Link(8, 3, 8, "July 2018", "Master Instructor"));
    }

}
