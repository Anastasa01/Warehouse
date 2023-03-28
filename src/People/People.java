package People;

public abstract class People {

	protected int id;
	protected String Name;
	protected String Surname;
	protected String Patronymic;
	protected String Address;
	protected int Telephone;

	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.Name;
	}
	public String getSurname(){
		return this.Surname;
	}
	public String getPatronymic(){
		return this.Patronymic;
	}
	public String getAddress(){
		return this.Address;
	}
	public int getTelephone(){
		return this.Telephone;
	}
}