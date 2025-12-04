package foodmanagementsystem;

/**
 * User class represents a system user: DONOR / RECIPIENT / ADMIN.
 * 
 * Demonstrates Encapsulation: - Private fields - Public getters & setters
 */
public class User {

	private int id;
	private String name;
	private String role; // ADMIN / DONOR / RECIPIENT
	private String status; // PENDING / APPROVED / REJECTED
	private String email;
	private String phone;
	private String address;

	// Constructor used during registration
	public User(int id, String name, String role, String email, String phone, String address) {
		this.id = id;
		this.name = name;
		this.role = role.toUpperCase();
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.status = "PENDING"; // Default state before admin approval
	}

	// -------- Getters & Setters (Encapsulation practice) ----------

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return id + " | " + name + " | " + role + " | Status: " + status;
	}
}
