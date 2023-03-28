package People;

public class Buyer extends People {
    protected int Passport;

    public Buyer(int id, String Name, String Surname, String Patronymic, int Passport, String Address, int Telephone) {
        this.id = id;
        this.Name = Name;
        this.Surname = Surname;
        this.Patronymic = Patronymic;
        this.Passport = Passport;
        this.Address = Address;
        this.Telephone = Telephone;
    }

    public int getPassport(){
        return this.Passport;
    }
}
