package Invoice;

public class ReceiptInvoice extends Invoice{
    protected String Supplier;

    public ReceiptInvoice(int id, String Supplier, String Product, int Price, String Date) {
        this.id = id;
        this.Supplier = Supplier;
        this.Product = Product;
        this.Price = Price;
        this.Date = Date;
    }

    public String getSupplier(){
        return this.Supplier;
    }
}
