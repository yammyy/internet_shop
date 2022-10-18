package net.yammyy.units.goods;

import java.util.ArrayList;
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
    Map<Integer,GoodParameter> parameters;
    public Good (int _id, String _name)
    {
        id=_id;
        name=_name;
    }
    public int getID ()
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
    public String getParameter(int _id){return parameters.get(_id).getValue();}
    public void setParams (Map<Integer, GoodParameter> _goodParameters){parameters=_goodParameters;}
    public List<String> getCategories()
    {
        List<String> res=new ArrayList<>();
        for (int i=0;i<categories.size();i++){res.add(categories.get(i).getValue());}
        return res;
    }
    public void setCategories (List<Category> _goodCategories){categories=_goodCategories;}
}
