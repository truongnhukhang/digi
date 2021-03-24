package digi.domain;

import java.time.LocalDate;

public class Booking {
   String guestName;
   int room;
   LocalDate date;

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
}
