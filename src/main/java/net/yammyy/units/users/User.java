package net.yammyy.units.users;

import net.yammyy.units.Delivery;
import net.yammyy.units.goods.Good;
import net.yammyy.units.Orders;
import net.yammyy.units.Spam;
import sun.util.locale.LanguageTag;

import java.util.Currency;
import java.util.Date;
import java.util.List;

class Type
{
    int id;
    String name;
}
public class User
{
    int id;
    String login;
    String pwd;
    String familyName;
    String name;
    String fatherName;
    Type type;
    Date regDate;
    Currency stdCurrency;
    LanguageTag stdLanguage;
    List<Good> favorites;
    Delivery delivery;
    Spam spam;
    List<Orders> ordersList;
    Orders cart;
    public User (int _id, String _login)
    {
        id=_id;
        login=_login;
    }
    public int getId()
    {
        return id;
    }
    public String getLogin() { return login; }
}
