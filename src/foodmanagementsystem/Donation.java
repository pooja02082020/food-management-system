package foodmanagementsystem;

public class Donation {
    int id;
    int donorId;
    String food;
    String status; // PENDING / APPROVED / REJECTED / ASSIGNED / DELIVERED

    public Donation(int id, int donorId, String food) {
        this.id = id;
        this.donorId = donorId;
        this.food = food;
        this.status = "PENDING";
    }
}
