import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FamilyTree {
	private static ArrayList<Person> family = new ArrayList<>();
	private static ArrayList<Person> familyMembers = new ArrayList<>();
	private static Scanner in = new Scanner(System.in);
	private static Person selectedPerson = new Person();
	public static void main(String args[]){

		System.out.println("===================================================================\nFAMILY TREE CREATOR!");
		System.out.println("\n\nNo family members yet! Let's do create one!");
		selectedPerson = createMember(null, null, null);
		showMemberDetails();
	}

	public static void showMemberDetails(){
		System.out.println("===================================================================\nFAMILY TREE CREATOR!\n");
		
		System.out.println(findMemberById(selectedPerson.getPersonId()));
		String userInput = in.nextLine();
		if(selectedPerson.getMotherId() != null){
			Person mother = findMemberById(selectedPerson.getMotherId());
			System.out.println("1. Mother: " + mother.getFirstName() + " " + mother.getMiddleName() + " " + mother.getLastName());
			familyMembers.add(mother);
		}

		if(selectedPerson.getFatherId() != null){
			Person father = findMemberById(selectedPerson.getFatherId());
			System.out.println("2. Father: " + father.getFirstName() + " " + father.getMiddleName() + " " + father.getLastName());
			familyMembers.add(father);
		}

		System.out.println("\nSiblings: ");
		ArrayList<Person> siblings = findSiblings(selectedPerson);
		if(siblings.isEmpty()){
			System.out.println("No siblings.");
		} else {
			for (Person sibling : siblings) {
				familyMembers.add(sibling);
				System.out.println((siblings.indexOf(sibling) + familyMembers.size()) + ". " + sibling.getFirstName() + " " + sibling.getMiddleName() + " " + sibling.getLastName());
			}
		}

		System.out.println("\nChildren: ");
		ArrayList<Person> childrens = findChildren(selectedPerson);
		if(childrens.isEmpty()){
			System.out.println("No children.");
		} else {
			for (Person children : childrens) {
				familyMembers.add(children);
				System.out.println((childrens.indexOf(children) + familyMembers.size()) + ". " + children.getFirstName() + " " + children.getMiddleName() + " " + children.getLastName());
			}
		}

		System.out.println("\nPlease enter the letter of your desired command or select a number of the specific family member:\na. Create a member.\nb. Update a member\nc. Remove a member\nd. Exit program");
		
		switch(in.next()){
			case "a":
				createMemberSelected(selectedPerson);
			break;
		}
	}

	public static ArrayList<Person> findSiblings(Person person){
		ArrayList<Person> siblings = new ArrayList<>();

		if(person.getMotherId() != null && person.getFatherId() != null){
			for (Person personFromFamily : family) {
				if(personFromFamily.getMotherId() == person.getMotherId() && personFromFamily.getFatherId() == person.getFatherId()){
					siblings.add(personFromFamily);
				}
			}
		}
		return siblings;
	}

	public static ArrayList<Person> findChildren(Person person){
		ArrayList<Person> children = new ArrayList<>();

		if(person.getPartnerId() != null){
			for (Person personFromFamily : family) {
				if((person.getSex() == Sex.FEMALE ? person.getPersonId() : person.getPartnerId()) == personFromFamily.getMotherId() && (person.getSex() == Sex.MALE ? person.getPersonId() : person.getPartnerId()) == personFromFamily.getFatherId()){
					children.add(personFromFamily);
				}
			}
		}
		return children;
	}

	public static void createMemberSelected(Person person){
		System.out.print("CREATE A MEMBER\n\nSelect the relationship of this new member to " + person.getFirstName() + " " + person.getLastName() + ". \n");
		for (Relationship rs : Relationship.values()) {
			System.out.println((Relationship.valueOf(rs.toString()).ordinal() + 1) + ". " + rs.toString());
		}
		System.out.println("b. Back");
		System.out.print("Relationship: ");
		switch(Relationship.values()[in.nextInt() - 1]){
			case CHILD:
				if(person.getPartnerId() != null){
					selectedPerson = createMember(null, (person.getSex() == Sex.FEMALE ? person.getPersonId() : person.getPartnerId()), (person.getSex() == Sex.MALE ? person.getPersonId() : person.getPartnerId()));
				} else {
					System.out.println("You can't have a child without having a partner. Please add your partner first.");
					pressAnyKeyToContinue();
				}
				break;
			case FATHER:
				selectedPerson = createMember((person.getMotherId() != null ? person.getMotherId() : null), null, null);
				break;
			case MOTHER:
				selectedPerson =createMember((person.getFatherId() != null ? person.getFatherId() : null), null, null);
				break;
			case PARTNER:
				selectedPerson = createMember(person.getPersonId(), null, null);
				break;
			case SIBLING:
				selectedPerson = createMember(null , (person.getMotherId() != null ? person.getMotherId() : null),  (person.getFatherId() != null ? person.getFatherId() : null));
				break;
			default:
				createMemberSelected(person);
				break;
		}
		showMemberDetails();
	}

	private static void pressAnyKeyToContinue()
 { 
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
 }

	public static Person findMemberById(String personId){
		Person foundPerson = new Person();
		for (Person person : family) {
			if(person.getPersonId() == personId){
				foundPerson = person;
			}
		}
		return foundPerson;
	}

	public static Person createMember(String partnerId, String motherId, String fatherId){
		Person member;
		String firstname, middlename, lastname;
		LocalDate birthdate = LocalDate.now();
		CivilStatus cs;
		Sex sex;
		boolean validInput = false;
		LocalDate curDate = LocalDate.now();

		System.out.print("Firstname: ");
		firstname = in.next();
		System.out.print("Middlename: ");
		middlename = in.next();
		System.out.print("Lastname: ");
		lastname = in.next();
		while(!validInput){
			System.out.print("\nBirthday(YYYY-MM-DD format): ");
			try {
				birthdate = LocalDate.parse(in.next());
				if(curDate.isBefore(birthdate)){
					System.out.print("BIRTHDATE ERROR: Your birthday cannot be ahead of the current date!");
				} else {
					validInput = true;
				}
			} catch (Exception e) {
				System.out.print("INPUT ERROR: Invalid Date!");
			}
		}
		System.out.print("Civil Status: \n");
		for (CivilStatus chooseCs : CivilStatus.values()) {
			System.out.println((CivilStatus.valueOf(chooseCs.toString()).ordinal() + 1) + ". " + chooseCs.toString());
		}
		cs = CivilStatus.values()[in.nextInt() - 1];
		System.out.print("Sex: \n");
		for (Sex chooseSex : Sex.values()) {
			System.out.println((Sex.valueOf(chooseSex.toString()).ordinal() + 1) + ". " + chooseSex.toString());
		}
		sex = Sex.values()[in.nextInt() - 1];

		member = new Person(firstname, middlename, lastname, birthdate, cs, sex, partnerId, motherId, fatherId);
		family.add(member);
		return member; 
	}

}