package lk.janakapharamcay.model;

public class PrescriptionDetail {

    private String prescriptionNumber;
    private String regCustId;
    private String docId;
    private String empId;
    private String prescriptionDate;
    private String prescriptionTime;
    private double prescriptionCost;

    public PrescriptionDetail() {
    }

    public PrescriptionDetail(String prescriptionNumber, String regCustId, String docId, String empId, String prescriptionDate, String prescriptionTime, double prescriptionCost) {
        this.prescriptionNumber = prescriptionNumber;
        this.regCustId = regCustId;
        this.docId = docId;
        this.empId = empId;
        this.prescriptionDate = prescriptionDate;
        this.prescriptionTime = prescriptionTime;
        this.prescriptionCost = prescriptionCost;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public String getRegCustId() {
        return regCustId;
    }

    public void setRegCustId(String regCustId) {
        this.regCustId = regCustId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getPrescriptionTime() {
        return prescriptionTime;
    }

    public void setPrescriptionTime(String prescriptionTime) {
        this.prescriptionTime = prescriptionTime;
    }

    public double getPrescriptionCost() {
        return prescriptionCost;
    }

    public void setPrescriptionCost(double prescriptionCost) {
        this.prescriptionCost = prescriptionCost;
    }
}
