package digi.main;

import digi.db.Repository;
import digi.db.impl.BookingDB;
import digi.service.BookingService;
import digi.service.impl.BookingServiceImpl;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    int numberOfRoom = 10;
    if(args.length>0) {
      try {
        numberOfRoom = Integer.parseInt(args[0]);
      } catch (NumberFormatException ex) {
        System.out.println("Error when parse numberOfRoom , we will use numberOfRoom is 10");
      }
    }
    Repository repository = new BookingDB(numberOfRoom);
    BookingService bookingService = new BookingServiceImpl(repository);

    Scanner ins = new Scanner(System.in);
    while (ins.hasNext()) {
      String command = ins.nextLine();
    }


  }
}
