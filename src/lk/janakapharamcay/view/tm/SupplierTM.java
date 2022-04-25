package lk.janakapharamcay.view.tm;

public class SupplierTM {

    private String suppID;
    private String name;
    private String address;
    private String telNumber;
    private String regNumber;
    private String email;

    public SupplierTM() {
    }

    public SupplierTM(String suppID, String name, String address, String telNumber, String regNumber, String email) {
        this.suppID = suppID;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.regNumber = regNumber;
        this.email = email;
    }

    public String getSuppID() {
        return suppID;
    }

    public void setSuppID(String suppID) {
        this.suppID = suppID;
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
