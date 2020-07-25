package com.currentbooking.ticketbooking;

import android.content.Context;

public interface BaseListener {
    void showHamburgerIcon(boolean b);
    void showBadge(boolean b);
    void updateBadgeCount();
    void goToLogout(Context context);
}
