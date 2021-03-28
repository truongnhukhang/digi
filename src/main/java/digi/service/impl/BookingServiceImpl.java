package digi.service.impl;

import digi.db.Repository;
import digi.domain.Booking;
import digi.response.StatusCode;
import digi.service.BookingService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BookingServiceImpl implements BookingService {
  private Repository repository;

  public BookingServiceImpl(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void Book(String name, String date, String room) throws Exception {
    LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    int roomInt = Integer.parseInt(room);
    Booking booking = new Booking();
    booking.setDate(localDate);
    booking.setGuestName(name);
    booking.setRoom(roomInt);
    int createResult = repository.createBooking(booking);
    if(createResult== StatusCode.FULL_ROOM) {
      throw new Exception(BookingService.FULL_ROOM_ERROR);
    }
    if (createResult==StatusCode.ALREADY_RESERVE) {
      throw new Exception(BookingService.ALREADY_RESERVE_ERROR);
    }
    if (createResult==StatusCode.ROOM_NOT_EXISTED) {
      throw new Exception(BookingService.ROOM_NOT_EXISTED_ERROR);
    }
  }

  @Override
  public List<Integer> GetAvailableRoom(String date) throws DateTimeParseException {
    LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    return repository.findAvailableRoomBy(localDate);
  }

  @Override
  public List<Booking> GetBookingsOf(String guessName) {
    return repository.findListBookingBy(guessName);
  }
}
