package Objectt;

public class Product extends Objectt {
    protected String Category;

    public Product(int id, String Name, String Category) {
        this.id = id;
        this.Name = Name;
        this.Category = Category;
    }

    public String getCategory(){
        return this.Category;
    }

}
