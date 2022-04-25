package lk.janakapharamcay.view.tm;

public class DoctorTM {
    private String docId;
    private String docName;
    private double bill;

    public DoctorTM() {
    }

    public DoctorTM(String docId, String docName, double bill) {
        this.setDocId(docId);
        this.setDocName(docName);
        this.setBill(bill);
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}
