package resources.interface_card;

public class SupplierCount {
    private String supplierName;
    private Long productCount;

    public SupplierCount(String supplierName, Long productCount) {
        this.supplierName = supplierName;
        this.productCount = productCount;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }
}
