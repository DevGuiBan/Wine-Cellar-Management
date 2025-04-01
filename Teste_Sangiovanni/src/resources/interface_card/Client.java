package resources.interface_card;

public class Client {
    private String name;
    private String id;
    private String data;
    private String payment;

    public Client(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Client() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return name;
    }

    public void reset(){
        this.id = null;
        this.data = null;
        this.payment = null;
        this.name = null;
    }

    public String data(){
        return "name: " + name + ", id: " + id + ", data: " + data + ", payment: " + payment;
    }
}
