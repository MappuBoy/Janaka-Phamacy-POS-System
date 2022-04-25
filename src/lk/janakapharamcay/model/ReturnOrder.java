package lk.janakapharamcay.model;

public class ReturnOrder {

    private String returnItemId;
    private String itemId;
    private String brandName;
    private String description;
    private String returnDate;
    private String reason;
    private int qty;
    private String supplierId;

    public ReturnOrder() {
    }

    public ReturnOrder(String returnItemId, String itemId, String brandName, String description, String returnDate,
                       String reason, int qty, String supplierId) {
        this.setReturnItemId(returnItemId);
        this.setItemId(itemId);
        this.setBrandName(brandName);
        this.setDescription(description);
        this.setReturnDate(returnDate);
        this.setReason(reason);
        this.setQty(qty);
        this.setSupplierId(supplierId);
    }

    public String getReturnItemId() {
        return returnItemId;
    }

    public void setReturnItemId(String returnItemId) {
        this.returnItemId = returnItemId;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
