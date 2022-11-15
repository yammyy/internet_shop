package net.yammyy.units.users;

import java.util.Date;

public class UserReason {
    Reason reason;
    Date dateBlocking;

    public UserReason(Reason reason, Date date) {
        this.reason = reason;
        dateBlocking = date;
    }

    public Reason getReason() {
        return reason;
    }

    public Date getDate() {
        return dateBlocking;
    }
}
