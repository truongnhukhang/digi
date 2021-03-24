package digi.service.impl;

import digi.db.Repository;
import digi.domain.Booking;
import digi.response.StatusCode;
import digi.service.BookingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BookingServiceImpl implements BookingService {
  private Repository repository;

  public BookingServiceImpl(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void Book(String name, String date, int room) throws Exception {
    LocalDate localDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).toLocalDate();
    Booking booking = new Booking();
    booking.setDate(localDate);
    booking.setGuestName(name);
    booking.setRoom(room);
    int createResult = repository.createBooking(booking);
    if(createResult== StatusCode.FULL_ROOM) {
      throw new Exception("FULL_ROOM");
    }
    if (createResult==StatusCode.ALREADY_RESERVE) {
      throw new Exception("ALREADY_RESERVE");
    }
  }

  @Override
  public boolean[] GetAvailableRoom(String date) throws DateTimeParseException {
    LocalDate localDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).toLocalDate();
    return repository.findAvailableRoomBy(localDate);
  }

  @Override
  public List<Booking> GetBookingOf(String guessName) {
    return repository.findListBookingBy(guessName);
  }
}
