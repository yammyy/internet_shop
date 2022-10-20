package net.yammyy.units.users;

public class Reason
{
    int id;
    String name;
    public Reason (int _id, String _name)
    {
        id=_id;
        name=_name;
    }
    public String getReason(){return name;}
}
