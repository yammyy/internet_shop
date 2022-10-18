package net.yammyy.units.users;

import net.yammyy.units.Delivery;
import net.yammyy.units.goods.Good;
import net.yammyy.units.Orders;
import net.yammyy.units.Spam;
import sun.util.locale.LanguageTag;

import java.util.Currency;
import java.util.Date;
import java.util.List;

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
    boolean is_blocked;
    Reason why_blocked;
    List<Good> favorites;
    Delivery delivery;
    Spam spam;
    List<Orders> ordersList;
    Orders cart;
    public User (int _id, String _login, String _password)
    {
        id=_id;
        login=_login;
        pwd=_password;
    }
    @Override public boolean equals (Object obj)
    {
        return ((User) obj).login.equals(login);
    }
    public int getId()
    {
        return id;
    }
    public String getLogin() { return login; }
    public void setLogin(String _login){login=_login;}
    public String getPassword(){return pwd;}
    public void setPassword(String _password){pwd=_password;}
    public Type getRole(){return type;}
    public void setRole(Type _role){type=_role;}
    public String getPhoto ()
    {
        return "img/img_avatar.png";
    }
    public String getFIO ()
    {
        return familyName+" "+name;
    }
}
