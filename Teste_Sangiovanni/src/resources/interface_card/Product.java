package resources.interface_card;

public class Product {
    private String productName;
    private int id;
    private Double valorUnitario;

    public Product(String productName, int id, Double valorUnitario) {
        this.productName = productName;
        this.id = id;
        this.valorUnitario = valorUnitario;
    }

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

    public Double getValorUnitario() {
        return valorUnitario;
    }
    @Override
    public String toString() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
