package digi.service;

import digi.domain.Booking;

import java.util.List;

public interface BookingService {
  public void Book(String name,String date,int room) throws Exception;
  public boolean[] GetAvailableRoom(String date);
  public List<Booking> GetBookingOf(String guessName);
}
