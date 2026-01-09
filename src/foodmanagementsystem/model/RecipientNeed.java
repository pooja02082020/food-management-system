package foodmanagementsystem.model;

/**
 * RecipientNeed represents donation demand of a recipient.
 * 
 * Each entry ties a recipient to their required item type and quantity.
 */
public class RecipientNeed {

    private int userId;          // Recipient User ID
    private String itemType;     // What item they need
    private int quantityNeeded;  // Quantity required

    public RecipientNeed(int userId, String itemType, int quantityNeeded) {
        this.userId = userId;
        this.itemType = itemType;
        this.quantityNeeded = quantityNeeded;
    }

    // Encapsulation
    public int getUserId() { return userId; }

    public String getItemType() { return itemType; }

    public void setItemType(String itemType) { this.itemType = itemType; }

    public int getQuantityNeeded() { return quantityNeeded; }

    public void setQuantityNeeded(int quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    @Override
    public String toString() {
        return "Need: " + itemType + " | Qty: " + quantityNeeded;
    }
}
