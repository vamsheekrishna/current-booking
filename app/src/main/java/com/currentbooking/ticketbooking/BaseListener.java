package com.currentbooking.ticketbooking;

import android.app.Dialog;

public interface BaseListener {
    void showHamburgerIcon(boolean b);
    void showBadge(boolean b);
    void updateBadgeCount(Dialog dialog, boolean b);
}
