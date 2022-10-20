package net.yammyy.units;

import net.yammyy.units.goods.Good;
import net.yammyy.units.users.User;

import java.util.Date;
import java.util.List;

class Status
{
    int id;
    String name;
    Date dateChange;
    User userChange;
}
public class Orders
{
    List<Order> goodsList;
    Delivery delivery;
    Status status;
    public List<Order> getOrder ()
    {
        return goodsList;
    }
}
