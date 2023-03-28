package People;

public class Supplier extends People {
	protected String Company;

	public Supplier(int id, String Name, String Surname, String Patronymic, String Company, String Address, int Telephone) {
		this.id = id;
		this.Name = Name;
		this.Surname = Surname;
		this.Patronymic = Patronymic;
		this.Company = Company;
		this.Address = Address;
		this.Telephone = Telephone;
	}

	public String getCompany(){
		return this.Company;
	}
}
