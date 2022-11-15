package net.yammyy.units.order;

import net.yammyy.units.users.User;

import java.util.Date;

public class OrderStatus {
    Status status;
    Date dateChange;
    User userChange;

    public OrderStatus(Status status) {this.status = status;}

    public OrderStatus(Status status, Date dateChange) {
        this.status = status;
        this.dateChange = dateChange;
    }

    public int getID() {return status.getID();}

    public String getName() {return status.getName();}

    public Date getDateChange() {return dateChange;}

    public User getUserWhoChangedStatus() {return userChange;}

}
