package lk.janakapharamcay.view.tm;

public class CartTm {
    private String itemId;
    private String brand;
    private String description;
    private double unitPrice;
    private int qty;
    private double orderItemPrice;

    public CartTm() {
    }

    public CartTm(String itemId, String brand, String description, double unitPrice, int qty, double orderItemPrice) {
        this.itemId = itemId;
        this.brand = brand;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.orderItemPrice = orderItemPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(double orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "itemId='" + itemId + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", orderItemPrice=" + orderItemPrice +
                '}';
    }
}
