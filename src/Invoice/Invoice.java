package Invoice;

public abstract class Invoice {
    protected int id;
    protected String Product;
    protected int Price;
    protected String Date;


    public int getId(){
        return this.id;
    }
    public String getProduct(){
        return this.Product;
    }
    public int getPrice(){
        return this.Price;
    }
    public String getDate(){
        return this.Date;
    }
}
