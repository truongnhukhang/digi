package service;

import digi.db.Repository;
import digi.db.impl.BookingDB;
import digi.domain.Booking;
import digi.service.BookingService;
import digi.service.impl.BookingServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void given10EmptyRoomThenBookingShouldBookSuccessfully() throws Exception {
        Repository bookingRepo = new BookingDB(10);
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        String date = "2022-11-11";
        String name = "khang";
        String room = "1";
        bookingService.Book(name,date,room);
        List<Booking> bookingList = bookingRepo.findListBookingBy("khang");
        Assert.assertEquals(1,bookingList.size());
        Assert.assertEquals(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE),bookingList.get(0).getDate());
        Assert.assertEquals(1,bookingList.get(0).getRoom());
    }

    @Test
    public void given10EmptyRoomThenBookingWithRoom11ShouldReturnNotExistedRoomException() throws Exception {
        exception.expectMessage(BookingService.ROOM_NOT_EXISTED_ERROR);
        Repository bookingRepo = new BookingDB(10);
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        String date = "2022-11-11";
        String name = "khang";
        String room = "11";
        bookingService.Book(name,date,room);
    }

    @Test
    public void given10FullRoomThenBookingShouldReturnFullRoomException() throws Exception {
        exception.expectMessage(BookingService.FULL_ROOM_ERROR);
        Repository bookingRepo = new BookingDB(10);
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        String date = "2022-11-11";
        String name = "khang";
        for (int i = 0; i <= 10; i++) {
            bookingService.Book(name,date,String.valueOf(i));
        }
        bookingService.Book(name,date,"1");
    }

    @Test
    public void given1BookedRoomThenBookingThatBookedRoomShouldReturnAlreadyReserveException() throws Exception {
        exception.expectMessage(BookingService.ALREADY_RESERVE_ERROR);
        Repository bookingRepo = new BookingDB(10);
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        String date = "2022-11-11";
        String name = "khang";
        bookingService.Book(name,date,"1");
        bookingService.Book(name,date,"1");
    }

    @Test
    public void givenListBookedRoomThenGetAvailableRoomShouldReturnCorrespondingEmptyRoom() throws Exception {
        Repository bookingRepo = new BookingDB(10);
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        String date = "2022-11-11";
        String name = "khang";
        bookingService.Book(name,date,"1");
        bookingService.Book(name,date,"2");

        List<Integer> availableRoom = bookingService.GetAvailableRoom("2022-11-11");
        Integer[] expected = {0,3,4,5,6,7,8,9,10};
        Integer[] actual = availableRoom.toArray(new Integer[0]);
        Assert.assertArrayEquals(expected,actual);
    }

    @Test
    public void givenListBookedRoomThenGetBookingsOfShouldReturnCorrespondingBookings() throws Exception {
        Repository bookingRepo = new BookingDB(10);
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        String name = "khang";
        bookingService.Book(name,"2022-11-11","1");
        bookingService.Book(name,"2022-12-12","2");
        bookingService.Book(name,"2022-10-10","2");

        List<Booking> actual = bookingService.GetBookingsOf(name);
        Assert.assertEquals(3,actual.size());
        List<Booking> expect = Arrays.asList(
                new Booking(name,1,LocalDate.parse("2022-11-11",DateTimeFormatter.ISO_LOCAL_DATE)),
                new Booking(name,2,LocalDate.parse("2022-12-12",DateTimeFormatter.ISO_LOCAL_DATE)),
                new Booking(name,2,LocalDate.parse("2022-10-10",DateTimeFormatter.ISO_LOCAL_DATE))
        );

        Assert.assertEquals(expect.stream().map(Booking::TimeAndRoom).collect(Collectors.joining("-")),
                actual.stream().map(Booking::TimeAndRoom).collect(Collectors.joining("-")));
    }



}
