package foodmanagementsystem;

import java.util.Scanner;

public class UserService {
    Scanner sc = new Scanner(System.in);

    public void userMenu(User u) {
        int choice;
        do {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. Donate Food");
            System.out.println("2. View My Donations");
            System.out.println("0. Logout");
            choice = sc.nextInt();

            switch(choice) {
                case 1 -> donateFood(u);
                case 2 -> viewMyDonations(u);
                case 0 -> System.out.println("Logged out.");
            }
        } while(choice != 0);
    }

    void donateFood(User u) {
        System.out.print("Enter food description: ");
        sc.nextLine();
        String food = sc.nextLine();

        int donationId = Database.donations.size() + 1;
        Database.donations.add(new Donation(donationId, u.id, food));
        System.out.println("Donation requested!");
    }

    void viewMyDonations(User u) {
        System.out.println("\n--- My Donations ---");
        for(Donation d : Database.donations){
            if(d.donorId == u.id)
                System.out.println(d.id + " - " + d.food + " - " + d.status);
        }
    }
}
