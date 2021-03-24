package digi.db;

import digi.domain.Booking;

import java.time.LocalDate;
import java.util.List;

public interface Repository {
  public int createBooking(Booking booking);
  public boolean[] findAvailableRoomBy(LocalDate bookingDate);
  public List<Booking> findListBookingBy(String guessName);
}
