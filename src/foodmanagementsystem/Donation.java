package foodmanagementsystem;

/**
 * Donation class tracks a food donation request.
 * 
 * Fields include item details, donor/recipient relationships, and status.
 * 
 * Demonstrates Basic Java, Constructor usage & Encapsulation.
 */
public class Donation {

	private int id;
	private int donorId;
	private Integer recipientId; // Null until assigned
	private String foodItem;
	private int quantity;
	private String expiryDate;
	private String preparationDate;
	private String pickupAddress;
	private String status; // PENDING / APPROVED / REJECTED / ASSIGNED / DELIVERED

	public Donation(int id, int donorId, String foodItem, int quantity, String expiryDate, String preparationDate,
			String pickupAddress) {

		this.id = id;
		this.donorId = donorId;
		this.foodItem = foodItem;
		this.quantity = quantity;
		this.expiryDate = expiryDate;
		this.preparationDate = preparationDate;
		this.pickupAddress = pickupAddress;
		this.status = "PENDING";
	}

	// Getters & setters (Encapsulation)
	public int getId() {
		return id;
	}

	public int getDonorId() {
		return donorId;
	}

	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}

	public String getFoodItem() {
		return foodItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return id + ". " + foodItem + " | Qty: " + quantity + " | Status: " + status
				+ (recipientId != null ? " | Assigned to Recipient: " + recipientId : "");
	}
}
