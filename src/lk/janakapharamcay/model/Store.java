package lk.janakapharamcay.model;

public class Store {
    private String itemId;
    private String suppId;
    private String itemType;
    private String brandName;
    private String description;
    private int qty;
    private double unitPrice;

    public Store() {
    }

    public Store(String itemId, String suppId, String itemType, String brandName, String description, int qty,
                 double unitPrice) {
        this.itemId = itemId;
        this.suppId = suppId;
        this.itemType = itemType;
        this.brandName = brandName;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSuppId() {
        return suppId;
    }

    public void setSuppId(String suppId) {
        this.suppId = suppId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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
}
