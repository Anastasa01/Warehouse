package Objectt;

public class Warehouse extends Objectt {
    protected String Unit;

    public Warehouse(int id, String Name, String Unit) {
        this.id = id;
        this.Name = Name;
        this.Unit = Unit;
    }

    public String getUnit(){
        return this.Unit;
    }

}
