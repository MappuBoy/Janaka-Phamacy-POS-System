package lk.janakapharamcay.view.tm;

public class ReturnPrescriptionDetailTM {
    private String returnPrescriptionId;
    private String prescriptionNumber;
    private String returnDate;
    private String reason;

    public ReturnPrescriptionDetailTM() {
    }

    public ReturnPrescriptionDetailTM(String returnPrescriptionId, String prescriptionNumber, String returnDate,
                                    String reason) {
        this.setReturnPrescriptionId(returnPrescriptionId);
        this.setPrescriptionNumber(prescriptionNumber);
        this.setReturnDate(returnDate);
        this.setReason(reason);
    }

    public String getReturnPrescriptionId() {
        return returnPrescriptionId;
    }

    public void setReturnPrescriptionId(String returnPrescriptionId) {
        this.returnPrescriptionId = returnPrescriptionId;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
