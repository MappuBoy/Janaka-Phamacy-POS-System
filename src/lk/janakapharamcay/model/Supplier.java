package lk.janakapharamcay.model;

public class Supplier {
    private String id;
    private String name;
    private String address;
    private String telNumber;
    private String regNumber;
    private String email;

    public Supplier() {
    }

    public Supplier(String id, String name, String address, String telNumber, String regNumber, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.regNumber = regNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
