package foodmanagementsystem;

import java.util.Scanner;

/**
 * RecipientService enables recipients to: - Update their needs - Track assigned
 * donations - View delivery history - Manage their profile
 */
public class RecipientService {

	private Scanner sc = new Scanner(System.in);

	public void recipientMenu(User u) {
		int choice;
		do {
			System.out.println("\n=== RECIPIENT DASHBOARD ===");
			System.out.println("1. Update Needs");
			System.out.println("2. View My Needs");
			System.out.println("3. View Assigned Donations");
			System.out.println("4. View Received History");
			System.out.println("5. Delete My Account");
			System.out.println("0. Logout");
			System.out.print("Enter choice: ");

			choice = readIntSafely();

			switch (choice) {
			case 1 -> updateNeeds(u);
			case 2 -> viewNeeds(u);
			case 3 -> viewAssignedDonations(u);
			case 4 -> viewReceivedHistory(u);
			case 5 -> deleteAccount(u);
			case 0 -> System.out.println("Logged out.");
			default -> System.out.println("Invalid choice.");
			}

		} while (choice != 0);
	}

	/**
	 * Update or add new need for recipient
	 */
	private void updateNeeds(User u) {
		System.out.print("\nEnter item type required: ");
		String itemType = sc.nextLine();

		System.out.print("Enter quantity required: ");
		int qty = readIntSafely();

		RecipientNeed found = null;

		// If need exists, update it
		for (RecipientNeed rn : Database.recipientNeeds) {
			if (rn.getUserId() == u.getId()) {
				found = rn;
				break;
			}
		}

		if (found == null) {
			Database.recipientNeeds.add(new RecipientNeed(u.getId(), itemType, qty));
		} else {
			found.setItemType(itemType);
			found.setQuantityNeeded(qty);
		}

		Database.log("Recipient Need Updated for User ID: " + u.getId());
		System.out.println("Needs updated.");
	}

	/** Displays current need if exists */
	private void viewNeeds(User u) {
		for (RecipientNeed rn : Database.recipientNeeds) {
			if (rn.getUserId() == u.getId()) {
				System.out.println("Current Need: " + rn);
				return;
			}
		}
		System.out.println("No needs recorded.");
	}

	/** Shows assigned donations */
	private void viewAssignedDonations(User u) {
		System.out.println("\n--- Assigned Donations ---");
		boolean found = false;

		for (Donation d : Database.donations) {
			if (d.getRecipientId() != null && d.getRecipientId() == u.getId()) {
				System.out.println(d);
				found = true;
			}
		}

		if (!found)
			System.out.println("No assigned donations.");
	}

	/** Shows delivered donations */
	private void viewReceivedHistory(User u) {
		System.out.println("\n--- Delivered Donations ---");
		boolean found = false;

		for (Donation d : Database.donations) {
			if (d.getRecipientId() != null && d.getRecipientId() == u.getId() && d.getStatus().equals("DELIVERED")) {
				System.out.println(d);
				found = true;
			}
		}

		if (!found)
			System.out.println("No delivered donations yet.");
	}

	/** Removes account & needs */
	private void deleteAccount(User u) {
		System.out.print("Confirm delete? yes/no: ");
		String ans = sc.nextLine();

		if (ans.equalsIgnoreCase("yes")) {
			Database.users.removeIf(user -> user.getId() == u.getId());
			Database.recipientNeeds.removeIf(rn -> rn.getUserId() == u.getId());
			Database.log("Recipient Account Deleted: " + u.getId());
			System.out.println("Account deleted.");
		} else {
			System.out.println("Cancelled.");
		}
	}

	/** Safe number input */
	private int readIntSafely() {
		while (!sc.hasNextInt()) {
			System.out.println("Enter valid number: ");
			sc.next();
		}
		int num = sc.nextInt();
		sc.nextLine();
		return num;
	}
}
