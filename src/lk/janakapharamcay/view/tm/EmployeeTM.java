package lk.janakapharamcay.view.tm;

public class EmployeeTM {

    private String EmpId;
    private String firstName;
    private String lastName;
    private String EmpAddress;
    private String EmpTelNumber;
    private String EmpNIC;
    private String EmpEmail;

    public EmployeeTM() {
    }

    public EmployeeTM(String empId, String firstName, String lastName, String empAddress, String empTelNumber,
                      String empNIC, String empEmail) {
        setEmpId(empId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        setEmpAddress(empAddress);
        setEmpTelNumber(empTelNumber);
        setEmpNIC(empNIC);
        setEmpEmail(empEmail);
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
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
        return EmpAddress;
    }

    public void setEmpAddress(String empAddress) {
        EmpAddress = empAddress;
    }

    public String getEmpTelNumber() {
        return EmpTelNumber;
    }

    public void setEmpTelNumber(String empTelNumber) {
        EmpTelNumber = empTelNumber;
    }

    public String getEmpNIC() {
        return EmpNIC;
    }

    public void setEmpNIC(String empNIC) {
        EmpNIC = empNIC;
    }

    public String getEmpEmail() {
        return EmpEmail;
    }

    public void setEmpEmail(String empEmail) {
        EmpEmail = empEmail;
    }
}
