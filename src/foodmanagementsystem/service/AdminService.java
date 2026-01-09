package foodmanagementsystem.service;

import java.util.Scanner;

import foodmanagementsystem.model.Donation;
import foodmanagementsystem.model.User;
import foodmanagementsystem.repository.Database;

/**
 * AdminService manages:
 * - user approvals
 * - donation approvals
 * - assigning donations to recipients
 * - generating analytics reports
 *
 * Admin is the highest authority in the system.
 */
public class AdminService {

    private Scanner sc = new Scanner(System.in);
    private ReportService reportService = new ReportService();

    /** Admin Dashboard Menu */
    public void adminMenu() {
        int choice;
        do {
            System.out.println("\n==== ADMIN DASHBOARD ====");
            System.out.println("1. View Pending Users");
            System.out.println("2. Approve/Reject User");
            System.out.println("3. View Pending Donations");
            System.out.println("4. Approve/Reject Donation");
            System.out.println("5. Assign Donation to Recipient");
            System.out.println("6. View All Users");
            System.out.println("7. View All Donations");
            System.out.println("8. Mark Donation as Delivered");
            System.out.println("9. View Reports & Analytics");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            choice = safeIntInput();

            switch (choice) {
                case 1 -> viewPendingUsers();
                case 2 -> approveRejectUser();
                case 3 -> viewPendingDonations();
                case 4 -> approveRejectDonation();
                case 5 -> assignDonationToRecipient();
                case 6 -> viewAllUsers();
                case 7 -> viewAllDonations();
                case 8 -> markDonationDelivered();
                case 9 -> reportService.showReports();
                case 0 -> System.out.println("Logged out.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    /** Lists users waiting for approval */
    private void viewPendingUsers() {
        System.out.println("\n--- Pending Registrations ---");
        boolean found = false;
        for (User u : Database.users) {
            if (u.getStatus().equals("PENDING")) {
                System.out.println(u);
                found = true;
            }
        }
        if (!found) System.out.println("No pending users.");
    }

    /** Admin approves or rejects user */
    private void approveRejectUser() {
        System.out.print("Enter User ID: ");
        int id = safeIntInput();

        User target = findUser(id);
        if (target == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("(A)pprove or (R)eject? ");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("A")) {
            target.setStatus("APPROVED");
            System.out.println("User approved.");
            Database.log("Admin approved user: " + id);
        } else if (choice.equalsIgnoreCase("R")) {
            target.setStatus("REJECTED");
            System.out.println("User rejected.");
            Database.log("Admin rejected user: " + id);
        } else {
            System.out.println("Invalid input.");
        }
    }

    /** Shows pending donations */
    private void viewPendingDonations() {
        System.out.println("\n--- Pending Donations ---");
        for (Donation d : Database.donations) {
            if (d.getStatus().equals("PENDING"))
                System.out.println(d);
        }
    }

    /** Approve or Reject donation request */
    private void approveRejectDonation() {
        System.out.print("Enter Donation ID: ");
        int id = safeIntInput();

        Donation target = findDonation(id);
        if (target == null) {
            System.out.println("Donation not found.");
            return;
        }

        System.out.print("(A)pprove or (R)eject? ");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("A")) {
            target.setStatus("APPROVED");
            Database.log("Admin approved donation: " + id);
            System.out.println("Donation approved.");
        } else if (choice.equalsIgnoreCase("R")) {
            target.setStatus("REJECTED");
            Database.log("Admin rejected donation: " + id);
            System.out.println("Donation rejected.");
        }
    }

    /** Assigns approved donation to a recipient */
    private void assignDonationToRecipient() {
        System.out.println("\n--- Approved Donations (Awaiting Assignment) ---");
        for (Donation d : Database.donations) {
            if (d.getStatus().equals("APPROVED") && d.getRecipientId() == null)
                System.out.println(d);
        }

        System.out.print("Enter Donation ID to assign: ");
        int did = safeIntInput();

        Donation target = findDonation(did);
        if (target == null) {
            System.out.println("Donation not found.");
            return;
        }

        System.out.println("\n--- Approved Recipients ---");
        for (User u : Database.users) {
            if (u.getRole().equals("RECIPIENT") && u.getStatus().equals("APPROVED"))
                System.out.println(u);
        }

        System.out.print("Enter Recipient ID: ");
        int rid = safeIntInput();

        User recipient = findUser(rid);
        if (recipient == null || !recipient.getRole().equals("RECIPIENT")) {
            System.out.println("Invalid recipient.");
            return;
        }

        target.setRecipientId(rid);
        target.setStatus("ASSIGNED");
        Database.log("Donation " + did + " assigned to recipient " + rid);

        System.out.println("Assigned successfully.");
    }

    /** Lists all users */
    private void viewAllUsers() {
        System.out.println("\n--- All Users ---");
        Database.users.forEach(System.out::println);
    }

    /** Lists all donations */
    private void viewAllDonations() {
        System.out.println("\n--- All Donations ---");
        Database.donations.forEach(System.out::println);
    }

    /** Marks donation delivered */
    private void markDonationDelivered() {
        System.out.print("Enter Donation ID: ");
        int id = safeIntInput();

        Donation target = findDonation(id);
        if (target == null) {
            System.out.println("Donation not found.");
            return;
        }

        if (target.getRecipientId() == null) {
            System.out.println("Donation not assigned yet.");
            return;
        }

        target.setStatus("DELIVERED");
        Database.log("Donation delivered: " + id);
        System.out.println("Donation marked delivered.");
    }

    // Small helper method — reduces duplicate code
    private User findUser(int id) {
        for (User u : Database.users) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    private Donation findDonation(int id) {
        for (Donation d : Database.donations) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    private int safeIntInput() {
        while (!sc.hasNextInt()) {
            System.out.println("Enter valid number:");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine(); // remove new line
        return value;
    }
}
