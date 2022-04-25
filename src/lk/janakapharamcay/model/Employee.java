package lk.janakapharamcay.model;

public class Employee {

     private String empId;
     private String firstName;
     private String lastName;
     private String empAddress;
     private String empTelNumber;
     private String empNIC;
     private String empUserName;
     private String empPassword;
     private String empEmail;

    public Employee() {
    }

    public Employee(String empId, String firstName, String lastName, String empAddress, String empTelNumber,
                    String empNIC, String empUserName, String empPassword, String empEmail) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.empAddress = empAddress;
        this.empTelNumber = empTelNumber;
        this.empNIC = empNIC;
        this.empUserName = empUserName;
        this.empPassword = empPassword;
        this.empEmail = empEmail;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpTelNumber() {
        return empTelNumber;
    }

    public void setEmpTelNumber(String empTelNumber) {
        this.empTelNumber = empTelNumber;
    }

    public String getEmpNIC() {
        return empNIC;
    }

    public void setEmpNIC(String empNIC) {
        this.empNIC = empNIC;
    }

    public String getEmpUserName() {
        return empUserName;
    }

    public void setEmpUserName(String empUserName) {
        this.empUserName = empUserName;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }
}
