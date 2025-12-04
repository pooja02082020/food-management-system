package foodmanagementsystem;

import java.util.Scanner;

/**
 * DonorService contains actions a Donor can perform.
 * 
 * Demonstrates: - Menu-driven console - Input validation - Encapsulation usage
 */
public class DonorService {

	private Scanner sc = new Scanner(System.in);

	/** Main menu for donor **/
	public void donorMenu(User u) {
		int choice;
		do {
			System.out.println("\n=== DONOR DASHBOARD ===");
			System.out.println("1. Donate Food");
			System.out.println("2. View My Donations");
			System.out.println("3. Update My Profile");
			System.out.println("4. Delete My Account");
			System.out.println("0. Logout");
			System.out.print("Enter choice: ");

			choice = readIntSafely();

			switch (choice) {
			case 1 -> donateFood(u);
			case 2 -> viewMyDonations(u);
			case 3 -> updateProfile(u);
			case 4 -> deleteAccount(u);
			case 0 -> System.out.println("Logged out.");
			default -> System.out.println("Invalid choice.");
			}

		} while (choice != 0);
	}

	/**
	 * Collects donation details and stores in database
	 */
	private void donateFood(User u) {
		System.out.println("\n--- Donate Food ---");

		System.out.print("Food item name: ");
		String item = sc.nextLine();

		System.out.print("Quantity: ");
		int qty = readIntSafely();

		System.out.print("Preparation date (YYYY-MM-DD): ");
		String prep = sc.nextLine();

		System.out.print("Expiry date (YYYY-MM-DD): ");
		String exp = sc.nextLine();

		System.out.print("Pickup location/address: ");
		String addr = sc.nextLine();

		int donationId = Database.donations.size() + 1;

		Donation d = new Donation(donationId, u.getId(), item, qty, exp, prep, addr);

		Database.donations.add(d);
		Database.log("Donation Request Created by Donor: " + u.getName());

		System.out.println("Donation submitted — admin approval required.");
	}

	/** Lists only the donor's donations */
	private void viewMyDonations(User u) {
		System.out.println("\n--- My Donation History ---");

		boolean found = false;

		for (Donation d : Database.donations) {
			if (d.getDonorId() == u.getId()) {
				System.out.println(d);
				found = true;
			}
		}

		if (!found)
			System.out.println("No donation history.");
	}

	/** Allows donor to update personal profile */
	private void updateProfile(User u) {
		System.out.println("\n--- Update Profile ---");

		System.out.print("New name (leave blank to keep): ");
		String name = sc.nextLine();
		if (!name.isBlank())
			u.setName(name);

		System.out.print("New email (leave blank to keep): ");
		String email = sc.nextLine();
		if (!email.isBlank())
			u.setEmail(email);

		System.out.print("New phone (leave blank to keep): ");
		String phone = sc.nextLine();
		if (!phone.isBlank())
			u.setPhone(phone);

		System.out.print("New address (leave blank to keep): ");
		String address = sc.nextLine();
		if (!address.isBlank())
			u.setAddress(address);

		Database.log("Profile Updated for User ID: " + u.getId());
		System.out.println("Profile updated successfully.");
	}

	/** Deletes donor and their donation relation */
	private void deleteAccount(User u) {
		System.out.print("Are you sure you want to delete your account? (yes/no): ");
		String ans = sc.nextLine();

		if (ans.equalsIgnoreCase("yes")) {
			Database.users.removeIf(user -> user.getId() == u.getId());
			Database.log("Donor Account Deleted: " + u.getId());
			System.out.println("Account deleted.");
		} else {
			System.out.println("Deletion cancelled.");
		}
	}

	/** Utility for safe integer input */
	private int readIntSafely() {
		while (!sc.hasNextInt()) {
			System.out.println("Enter a valid number: ");
			sc.next();
		}
		int num = sc.nextInt();
		sc.nextLine();
		return num;
	}
}
