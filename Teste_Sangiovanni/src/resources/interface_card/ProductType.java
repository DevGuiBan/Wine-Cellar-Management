package resources.interface_card;

public class ProductType {
    private final String id;
    private String name;

    public ProductType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
