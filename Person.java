import java.time.LocalDate;
import java.time.Period;

public class Person {

	private static int COUNT = 0;
	private String personId;
	//private int generationId = 1;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate birthdate;
	private CivilStatus civilStatus;
	private Sex sex;

	private String partnerId;
	private String motherId;
	private String fatherId;

	private LocalDate curDate = LocalDate.now();

	public Person(){
		
	}
	
	public Person(String firstName, String middleName, String lastName, LocalDate birthdate, CivilStatus civilStatus,
	Sex sex, String partnerId, String motherId, String fatherId) {

	this.firstName = firstName;
	this.middleName = middleName;
	this.lastName = lastName;
	this.birthdate = birthdate;
	this.civilStatus = civilStatus;
	this.sex = sex;
	this.partnerId = partnerId;
	this.motherId = motherId;
	this.fatherId = fatherId;
	COUNT++;
	generatePersonId();
	}

	@Override
	public String toString() {
		return "Name: " + this.firstName + " " + this.middleName + " " + this.lastName +
				"\nBirthday: " + this.birthdate.toString() +
				"\nAge: " + getAge().toString() +
				"\nCivil Status: " + this.civilStatus.toString() +
				"\nSex: " + this.sex.toString()
		;
	}

	private String generatePersonId(){
		return "P" + COUNT;
	}

	// GETTERS
	public String getAge(){ 
		String age = "";     
		if ((birthdate != null) && (curDate != null)){
			age = Period.between(birthdate, curDate).getYears() + " years old";  
		}
		return age;
	}

	public String getPersonId(){
		return this.personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public CivilStatus getCivilStatus() {
		return civilStatus;
	}

	public Sex getSex() {
		return sex;
	}
	
	public LocalDate getCurDate() {
		return curDate;
	}

	public String getMotherId(){
		return this.motherId;
	}

	public String getFatherId(){
		return this.fatherId;
	}

	public String getPartnerId(){
		return this.partnerId;
	}

	// SETTERS
	public void updateCivilStatus(CivilStatus civilStatus) {
		this.civilStatus = civilStatus;
	}

	public void updateSex(Sex sex) {
		this.sex = sex;
	}
	public void updateBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public void updateLastName(String lastName) {
		this.lastName = lastName;
	}

	public void updateMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void updateFirstName(String firstName) {
		this.firstName = firstName;
	}
}
