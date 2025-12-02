package foodmanagementsystem;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			// seed admin
			Database.users.add(new User(1, "Admin", "ADMIN"));
			Database.users.get(0).status = "APPROVED";

			int choice;

			do {
				System.out.println("\n=== FOOD DONATION SYSTEM ===");
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("0. Exit");

				choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
				case 1 -> registerUser();
				case 2 -> login();
				case 0 -> System.out.println("Exiting..");
				}
			} while (choice != 0);
		}
	}

	static void registerUser() {
		try (Scanner sc = new Scanner(System.in)) {
			int id = Database.users.size() + 1;

			System.out.print("Enter Name: ");
			String name = sc.nextLine();

			System.out.print("Role (DONOR/RECIPIENT): ");
			String role = sc.nextLine().toUpperCase();

			Database.users.add(new User(id, name, role));
		}
		System.out.println("Registration pending approval.");
	}

	static void login() {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Enter Name: ");
			String name = sc.nextLine();

			for (User u : Database.users) {
				if (u.name.equals(name)) {
					if (u.status.equals("PENDING")) {
						System.out.println("Account awaiting admin approval.");
						return;
					}

					if (u.role.equals("ADMIN"))
						new AdminService().adminMenu();
					else
						new UserService().userMenu(u);

					return;
				}
			}

			System.out.println("No user found.");
			sc.close();
		}
	}
}
