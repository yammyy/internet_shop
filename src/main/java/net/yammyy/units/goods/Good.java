package net.yammyy.units.goods;

import java.util.Currency;
import java.util.List;
import java.util.Map;

public class Good
{
    int id;
    String name;
    String description;
    Currency currency;
    double price;
    List<Category> categories;
    List<GoodParameter> parameters;
    public Good (int _id, String _name)
    {
        id=_id;
        name=_name;
    }
    public int getId ()
    {
        return id;
    }
    public String getName () {return name;}
    public String getDescription ()
    {
        return description;
    }
    public void setDescription (String _description)
    {
        description=_description;
    }
    public double getPrice ()
    {
        return price;
    }
    public void setPrice (double _price)
    {
        price=_price;
    }
}
