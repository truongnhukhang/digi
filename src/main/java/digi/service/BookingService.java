package digi.service;

import digi.domain.Booking;

import java.util.List;

public interface BookingService {

  String ALREADY_RESERVE_ERROR = "This room already reserve,please choose another";
  String FULL_ROOM_ERROR = "All room have been booked, please choose another date";
  String ROOM_NOT_EXISTED_ERROR = "This room is not existed, please choose another";
  String DATE_PATTERN = "yyyy-MM-dd";

  public void Book(String name,String date,String room) throws Exception;
  public List<Integer> GetAvailableRoom(String date) throws Exception;
  public List<Booking> GetBookingsOf(String guessName);
}
