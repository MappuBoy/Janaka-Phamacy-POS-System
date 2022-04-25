package lk.janakapharamcay.model;

public class RegisteredCustomer {

    private String regCustId;
    private String firstName;
    private String lastName;
    private String address;
    private String telPhone;
    private String nic;

    public RegisteredCustomer() {
    }

    public RegisteredCustomer(String regCustId, String firstName, String lastName, String address, String telPhone, String nic) {
        this.regCustId = regCustId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telPhone = telPhone;
        this.nic = nic;
    }

    public String getRegCustId() {
        return regCustId;
    }

    public void setRegCustId(String regCustId) {
        this.regCustId = regCustId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return "RegisteredCustomer{" +
                "regCustId='" + regCustId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", telPhone='" + telPhone + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
