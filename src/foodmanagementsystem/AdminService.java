package foodmanagementsystem;

import java.util.Scanner;

public class AdminService {
    Scanner sc = new Scanner(System.in);

    public void adminMenu() {
        int choice;

        do {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Pending Users");
            System.out.println("2. Approve User");
            System.out.println("3. View Pending Donations");
            System.out.println("4. Approve Donation");
            System.out.println("0. Logout");

            choice = sc.nextInt();

            switch(choice) {
                case 1 -> viewPendingUsers();
                case 2 -> approveUser();
                case 3 -> viewPendingDonations();
                case 4 -> approveDonation();
                case 0 -> System.out.println("Logged out.");
            }
        } while(choice != 0);
    }

    void viewPendingUsers() {
        System.out.println("\n--- Pending Users ---");
        for(User u : Database.users) {
            if(u.status.equals("PENDING"))
                System.out.println(u.id + " - " + u.name);
        }
    }

    void approveUser() {
        System.out.print("Enter User ID to approve: ");
        int id = sc.nextInt();
        for(User u : Database.users) {
            if(u.id == id) {
                u.status = "APPROVED";
                System.out.println("User approved!");
                return;
            }
        }
        System.out.println("User not found");
    }

    void viewPendingDonations() {
        System.out.println("\n--- Pending Donations ---");
        for(Donation d : Database.donations) {
            if(d.status.equals("PENDING"))
                System.out.println(d.id + " - " + d.food);
        }
    }

    void approveDonation() {
        System.out.print("Enter Donation ID to approve: ");
        int id = sc.nextInt();
        for(Donation d : Database.donations) {
            if(d.id == id) {
                d.status = "APPROVED";
                System.out.println("Donation approved!");
                return;
            }
        }
        System.out.println("Donation not found");
    }
}
