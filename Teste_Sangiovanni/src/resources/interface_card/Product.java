package resources.interface_card;

public class Product {
    private String productName;
    private int id;

    public Product(String productName, int id) {
        this.productName = productName;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
