package net.yammyy.units.goods;

public class Category
{
    int id;
    String name;
    public Category (int _id, String _name)
    {
        id=_id;
        name=_name;
    }
    public int getId()
    {
        return id;
    }
    public String getName() { return name; }
}
