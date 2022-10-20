package net.yammyy.units.users;

import net.yammyy.units.Delivery;
import net.yammyy.units.Order;
import net.yammyy.units.goods.Currency;
import net.yammyy.units.Orders;
import net.yammyy.units.Spam;
import net.yammyy.units.goods.Language;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    Language stdLanguage;
    boolean is_blocked;
    Reason why_blocked;
    Delivery delivery;
    Spam spam;
    Map<Integer, Orders> ordersList;
    public User (int _id, String _login, String _password)
    {
        id=_id;
        login=_login;
        pwd=_password;
        regDate=new Date();
    }
    public User (int _id, String _login, String _password,Date _regDate)
    {
        id=_id;
        login=_login;
        pwd=_password;
        regDate=_regDate;
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
    public String getPassword(){return pwd;}
    public void setPassword(String _password){pwd=_password;}
    public Type getRole(){return type;}
    public void setRole(Type _role){type=_role;}
    public String getPhoto ()
    {
        return "img/img_avatar.png";
    }
    public String getFamilyName (){return familyName;}
    public String getName (){return name;}
    public String getFamilyAndName (){return familyName+" "+name;}
    public String getFatherName(){return fatherName;}
    public void setStandardLanguage(Language _lng){stdLanguage=_lng;}
    public Language getStandardLanguage (){return stdLanguage;}
    public void setStandardCurrency(Currency _cur){stdCurrency=_cur;}
    public Currency getStandardCurrency (){return stdCurrency;}
    public Date getRegistrationDate(){return regDate;}
    public Reason getBlockingReason()
    {
        if (is_blocked){return why_blocked;}
        else {return new Reason(0,"");}
    }
    public void setBlockingReason(boolean _is_blocked, Reason _why_blocked)
    {
        is_blocked=_is_blocked;
        why_blocked=_why_blocked;
    }
    public List<Order> getFavorites(){return ordersList.get(1).getOrder();}
    public Map<Integer,Orders> getOrdersList(){return ordersList;}
    public List<Order> getCart(){return ordersList.get(0).getOrder();}

}
