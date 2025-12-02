package foodmanagementsystem;

public class User {
    int id;
    String name;
    String role;  // ADMIN / DONOR / RECIPIENT
    String status; // PENDING / APPROVED / REJECTED

    public User(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.status = "PENDING";
    }
}
