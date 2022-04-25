package lk.janakapharamcay.model;

public class Prescription {

    private String prescriptionNumber;
    private String itemId;
    private String brandName;
    private String description;
    private int qty;
    private double unitPrice;
    private double orderItemPrice;

    public Prescription() {
    }

    public Prescription(String prescriptionNumber, String itemId, String brandName, String description, int qty, double unitPrice, double orderItemPrice) {
        this.prescriptionNumber = prescriptionNumber;
        this.itemId = itemId;
        this.brandName = brandName;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.orderItemPrice = orderItemPrice;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(double orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }
}
