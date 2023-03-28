package Invoice;

public class ExpenditureInvoice extends Invoice{
    protected String Buyer;

    public ExpenditureInvoice(int id, String Buyer, String Product, int Price, String Date) {
        this.id = id;
        this.Buyer = Buyer;
        this.Product = Product;
        this.Price = Price;
        this.Date = Date;
    }

    public String getBuyer(){
        return this.Buyer;
    }
}
