package foodmanagementsystem;

import java.util.Scanner;

/**
 * Main class acts as: - Application launcher - Global menu controller
 */
public class Main {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// Pre-register 1 admin user
		User admin = new User(1, "System Admin", "ADMIN", "admin@food.org", "0000000000", "HQ");
		admin.setStatus("APPROVED");
		Database.users.add(admin);

		System.out.println("==== FOOD DONATION MANAGEMENT SYSTEM ====");

		int choice;
		do {
			System.out.println("\n--- MAIN MENU ---");
			System.out.println("1. Register (Donor / Recipient)");
			System.out.println("2. Login as User");
			System.out.println("3. Login as Admin");
			System.out.println("0. Exit");
			System.out.print("Enter choice: ");

			choice = safeInt();

			switch (choice) {
			case 1 -> registerUser();
			case 2 -> loginUser();
			case 3 -> adminLogin();
			case 0 -> System.out.println("Goodbye.");
			default -> System.out.println("Invalid option.");
			}

		} while (choice != 0);
	}

	/** Register a new donor or recipient */
	private static void registerUser() {
		System.out.println("\n--- Registration ---");
		int id = Database.users.size() + 1;

		System.out.print("Enter Name: ");
		String name = sc.nextLine();

		System.out.print("Role (DONOR / RECIPIENT): ");
		String role = sc.nextLine();

		System.out.print("Email: ");
		String email = sc.nextLine();

		System.out.print("Phone: ");
		String phone = sc.nextLine();

		System.out.print("Address: ");
		String address = sc.nextLine();

		User newUser = new User(id, name, role, email, phone, address);
		Database.users.add(newUser);

		Database.log("New user registered: " + name);

		System.out.println("Registration done — awaiting admin approval.");
	}

	/** User Login */
	private static void loginUser() {
		System.out.print("\nEnter Name: ");
		String name = sc.nextLine();

		for (User u : Database.users) {
			if (u.getName().equalsIgnoreCase(name)) {

				if (u.getStatus().equals("PENDING")) {
					System.out.println("Awaiting admin approval.");
					return;
				}

				if (u.getStatus().equals("REJECTED")) {
					System.out.println("Account rejected.");
					return;
				}

				if (u.getRole().equals("DONOR"))
					new DonorService().donorMenu(u);
				else if (u.getRole().equals("RECIPIENT"))
					new RecipientService().recipientMenu(u);
				else
					System.out.println("Admin users must login via admin portal.");

				return;
			}
		}

		System.out.println("User not found.");
	}

	/** Admin login with simple username/password */
	private static void adminLogin() {
		System.out.print("Enter Username: ");
		String u = sc.nextLine();

		System.out.print("Enter Password: ");
		String p = sc.nextLine();

		// Hardcoded credentials
		if (u.equals("admin") && p.equals("admin123")) {
			System.out.println("Welcome Admin.");
			new AdminService().adminMenu();
		} else {
			System.out.println("Invalid credentials.");
		}
	}

	// Utility method
	private static int safeInt() {
		while (!sc.hasNextInt()) {
			System.out.println("Enter valid number:");
			sc.next();
		}
		int num = sc.nextInt();
		sc.nextLine();
		return num;
	}
}
