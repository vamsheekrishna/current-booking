package com.currentbooking.home.dummy;

import java.util.ArrayList;
import java.util.List;

public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    public static void createDummyItem(int id, int imageID, String title) {
        addItem(new DummyItem( id, imageID, title));
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final int id;
        public final int image;
        public final String title;

        DummyItem(int id, int image, String title) {
            this.image = image;
            this.title = title;
            this.id = id;
        }
    }
    public enum ServicesID {
        Mobile,
        Advance_Ticket_Booking,
        Insurance,
        Current_Bus_Booking,
        Hotel_Booking,
        Car_Rental,
        Cash_Cards,
        DTH,
        Domestic_Money_Transfer,
        E_Pay_Later,
        Electricity,
        Gold_Loan
    }
}
