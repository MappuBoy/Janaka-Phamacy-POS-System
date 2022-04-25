package lk.janakapharamcay.model;

public class ItemOrder {
    private String suppId;
    private String itemId;
    private String brandName;
    private String description;
    private String orderDate;
    private String deliveryDate;
    private int qty;

    public ItemOrder() {
    }

    public ItemOrder(String suppId, String itemId, String brandName, String description, String orderDate,
                     String deliveryDate, int qty) {
        this.suppId = suppId;
        this.itemId = itemId;
        this.brandName = brandName;
        this.description = description;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.qty = qty;
    }

    public String getSuppId() {
        return suppId;
    }

    public void setSuppId(String suppId) {
        this.suppId = suppId;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
