package digi.main;

import digi.db.Repository;
import digi.db.impl.BookingDB;
import digi.domain.Booking;
import digi.service.BookingService;
import digi.service.impl.BookingServiceImpl;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static digi.service.BookingService.DATE_PATTERN;

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

    mainScreen();

    Scanner ins = new Scanner(System.in);
    label:
    while (ins.hasNext()) {
      String command = ins.nextLine();
      switch (command) {
        case "1":
          HandleBooking(bookingService, ins);
          break;
        case "2":
          handleAvailableRooms(bookingService, ins);
          break;
        case "3":
          handleListBookingOfCustomer(bookingService, ins);
          break;
        default:
          System.out.println("See your later");
          break label;
      }
      mainScreen();
    }


  }

  private static void handleListBookingOfCustomer(BookingService bookingService, Scanner ins) {
    System.out.println("-------");
    System.out.print("Input customer name :");
    String customer = ins.nextLine();
    List<Booking> bookings = bookingService.GetBookingsOf(customer);
    System.out.println("Bookings of "+customer);
    System.out.println(bookings.stream().map(Booking::TimeAndRoom).collect(Collectors.joining("\n")));
    System.out.println("-------");
  }

  private static void handleAvailableRooms(BookingService bookingService, Scanner ins) {
    System.out.println("-------");
    System.out.print("Input your date (dd-MM-yyyy) : ");
    String dateStr = ins.nextLine();
    try {
      List<Integer> availableRoom = bookingService.GetAvailableRoom(dateStr);
      System.out.println("available rooms : "+availableRoom.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    } catch (Exception ex) {
      if (ex instanceof DateTimeParseException) {
        System.out.println("Error : Please input right date format ("+DATE_PATTERN+") ");
      } else {
        System.out.println(ex.getMessage());
      }
    }
    System.out.println("-------");
  }


  private static void HandleBooking(BookingService bookingService, Scanner ins) {
    System.out.println("-------");
    System.out.print("Input your date ("+DATE_PATTERN+") : ");
    String dateStr = ins.nextLine();
    System.out.print("Input your Name : ");
    String name = ins.nextLine();
    System.out.print("Input your selected room :");
    String roomStr = ins.nextLine();
    try {
      bookingService.Book(name, dateStr, roomStr);
    } catch (Exception e) {
      if (e instanceof DateTimeParseException) {
        System.out.println("Error : Please input right date format ("+DATE_PATTERN+") ");
      } else if(e instanceof NumberFormatException) {
        System.out.println("Error : Room must be a number");
      } else {
        System.out.println("Error : " + e.getMessage());
      }
    }
    System.out.println("-------");
  }

  private static void mainScreen() {
    System.out.println("Welcome to Digi hotel");
    System.out.println("Type 1,2,3 to choose manage your hotel, other button will exit application");
    System.out.println("1. Make a booking ");
    System.out.println("2. List available rooms in a date ");
    System.out.println("3. List bookings made by a user ");
    System.out.print("Enter your choice : ");
  }
}
