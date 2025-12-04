package foodmanagementsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Database class holds all system data in-memory.
 * 
 * Requirement: Uses Collection Framework instead of real database.
 * 
 * Responsibilities:
 * - Store Users
 * - Store Donations
 * - Store Recipient Needs
 * - Store simple Logs (audit trail)
 */
public class Database {

    public static List<User> users = new ArrayList<>();
    public static List<Donation> donations = new ArrayList<>();
    public static List<RecipientNeed> recipientNeeds = new ArrayList<>();
    public static List<String> activityLogs = new ArrayList<>();

    /** Logs system activities for audit / reporting */
    public static void log(String message) {
        activityLogs.add(message);
    }
}
