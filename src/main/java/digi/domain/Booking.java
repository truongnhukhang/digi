package digi.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
   String guestName;
   int room;
   LocalDate date;

   public Booking() {
   }

   public Booking(String guestName, int room, LocalDate date) {
      this.guestName = guestName;
      this.room = room;
      this.date = date;
   }

   public String getGuestName() {
      return guestName;
   }

   public void setGuestName(String guestName) {
      this.guestName = guestName;
   }

   public int getRoom() {
      return room;
   }

   public void setRoom(int room) {
      this.room = room;
   }

   public LocalDate getDate() {
      return date;
   }

   public void setDate(LocalDate date) {
      this.date = date;
   }

   public String TimeAndRoom() {
      return "Date : " + this.date
              .format(DateTimeFormatter.ISO_LOCAL_DATE)+", "+"Room : "+this.room;
   }
}
