package net.yammyy.units.users;

import java.util.ArrayList;
import java.util.List;

public class Type
{
    int id;
    String name;
    List<String> urls;
    public Type (int _id, String _name)
    {
        id=_id;
        name=_name;
        urls=new ArrayList<>();
    }
    public int getID(){return id;}
    public String getName(){return name;}
    public void addURL(String _url){urls.add(_url);}
    public boolean hasURL(String _url){return urls.contains(_url);}
}
