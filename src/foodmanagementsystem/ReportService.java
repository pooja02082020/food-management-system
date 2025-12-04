package foodmanagementsystem;

import java.util.HashMap;
import java.util.Map;

/**
 * ReportService prints analytics for the admin.
 *
 * Demonstrates:
 * - HashMap usage
 * - Iteration
 * - Simple statistics
 */
public class ReportService {

    public void showReports() {
        System.out.println("\n=== SYSTEM REPORTS ===");
        totalDonations();
        donationCountPerDonor();
        donationCountPerRecipient();
        statusSummary();
        mostDonatedFoodItem();
    }

    /** Total donations made */
    private void totalDonations() {
        System.out.println("Total Donations: " + Database.donations.size());
    }

    /** Donations made by each donor */
    private void donationCountPerDonor() {
        System.out.println("\nDonations per Donor:");
        Map<Integer, Integer> countMap = new HashMap<>();

        for (Donation d : Database.donations) {
            countMap.put(d.getDonorId(), countMap.getOrDefault(d.getDonorId(), 0) + 1);
        }

        for (var entry : countMap.entrySet()) {
            User u = findUser(entry.getKey());
            System.out.println("Donor " + u.getName() + " => " + entry.getValue());
        }
    }

    /** Donations assigned per recipient */
    private void donationCountPerRecipient() {
        System.out.println("\nDonations per Recipient:");
        Map<Integer, Integer> countMap = new HashMap<>();

        for (Donation d : Database.donations) {
            if (d.getRecipientId() != null) {
                countMap.put(d.getRecipientId(), countMap.getOrDefault(d.getRecipientId(), 0) + 1);
            }
        }

        if (countMap.isEmpty()) {
            System.out.println("No donations assigned.");
            return;
        }

        for (var entry : countMap.entrySet()) {
            User u = findUser(entry.getKey());
            System.out.println("Recipient " + u.getName() + " => " + entry.getValue());
        }
    }

    /** Donation status distribution */
    private void statusSummary() {
        System.out.println("\nDonation Status Breakdown:");

        int pending = 0, approved = 0, assigned = 0, delivered = 0, rejected = 0;

        for (Donation d : Database.donations) {
            switch (d.getStatus()) {
                case "PENDING" -> pending++;
                case "APPROVED" -> approved++;
                case "ASSIGNED" -> assigned++;
                case "DELIVERED" -> delivered++;
                case "REJECTED" -> rejected++;
            }
        }

        System.out.println("Pending: " + pending);
        System.out.println("Approved: " + approved);
        System.out.println("Assigned: " + assigned);
        System.out.println("Delivered: " + delivered);
        System.out.println("Rejected: " + rejected);
    }

    /** Most frequently donated item */
    private void mostDonatedFoodItem() {
        System.out.println("\nMost Frequent Donation:");

        if (Database.donations.isEmpty()) {
            System.out.println("No donations yet.");
            return;
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (Donation d : Database.donations) {
            map.put(d.getFoodItem(), map.getOrDefault(d.getFoodItem(), 0) + 1);
        }

        String bestItem = null;
        int max = 0;

        for (var e : map.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                bestItem = e.getKey();
            }
        }

        System.out.println(bestItem + " donated " + max + " times.");
    }

    private User findUser(int id) {
        for (User u : Database.users) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}
