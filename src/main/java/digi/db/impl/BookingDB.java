package digi.db.impl;

import digi.db.Repository;
import digi.domain.Booking;
import digi.response.StatusCode;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BookingDB implements Repository {
  private int numberOfRoom = 0;
  private List<Booking> bookings;
  private Map<LocalDate, Set<Integer>> dateIndex;
  private Map<String,Set<Integer>> guessNameIndex;
  private Lock readLock;
  private Lock writeLock;
  public BookingDB(int numberOfRoom) {
    this.numberOfRoom = numberOfRoom;
    bookings = new ArrayList<>();
    dateIndex = new TreeMap<>();
    guessNameIndex = new HashMap<>();
    ReadWriteLock lock = new ReentrantReadWriteLock();
    readLock = lock.readLock();
    writeLock = lock.writeLock();
  }

  @Override
  public int createBooking(Booking booking) {
    writeLock.lock();
    try {
      LocalDate bookingDate = booking.getDate();
      Set<Integer> indexes = dateIndex.get(bookingDate);
      if(indexes!=null) {
        if(indexes.size()==numberOfRoom) {
          return StatusCode.FULL_ROOM;
        }
        for (Integer index : indexes) {
          Booking oldBooking = bookings.get(index);
          if (oldBooking.getRoom() == booking.getRoom()) {
            return StatusCode.ALREADY_RESERVE;
          }
        }
      } else {
        indexes = new HashSet<>();
      }
      bookings.add(booking);
      int index = bookings.size()-1;
      indexes.add(index);
      dateIndex.put(bookingDate,indexes);
      guessNameIndex.computeIfAbsent(booking.getGuestName(),key->new HashSet<>()).add(index);
    } finally {
      writeLock.unlock();
    }
    return StatusCode.SUCCESS;
  }


  @Override
  public boolean[] findAvailableRoomBy(LocalDate bookingDate) {
    readLock.lock();
    boolean[] availableRooms = new boolean[numberOfRoom];
    Arrays.fill(availableRooms, true);
    try {
      Set<Integer> indexes = dateIndex.get(bookingDate);
      if(indexes!=null) {
        for(Integer index : indexes) {
          availableRooms[bookings.get(index).getRoom()] = false;
        }
      }
    } finally {
      readLock.unlock();
    }
    return availableRooms;
  }

  @Override
  public List<Booking> findListBookingBy(String guessName) {
    readLock.lock();
    List<Booking> bookings = new ArrayList<>();
    try {
      Set<Integer> indexes = guessNameIndex.get(guessName);
      if(indexes!=null) {
        for(Integer index : indexes) {
          bookings.add(this.bookings.get(index));
        }
      }
    } finally {
      readLock.unlock();
    }
    return bookings;
  }
}
