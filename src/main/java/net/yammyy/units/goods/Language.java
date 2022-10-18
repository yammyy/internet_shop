package net.yammyy.units.goods;

public class Language
{
    int id;
    String name;
    String abbr;
    public Language(int _id,String _name,String _abbr)
    {
        id=_id;
        name=_name;
        abbr=_abbr;
    }
    public int getID(){return id;}
    public String getName(){return name;}
    public String getAbbr(){return abbr;}
}
