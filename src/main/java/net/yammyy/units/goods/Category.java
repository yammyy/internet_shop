package net.yammyy.units.goods;

public class Category implements ChoosableParameterValue
{
    int categoryID;
    String categoryName;
    @Override public int getID (){return categoryID;}
    @Override public void setID (int _id){categoryID=_id;}
    @Override public String getValue (){return categoryName;}
    @Override public void setValue (String _value){categoryName=_value;}
    @Override public void setParameter (int _id, String _value)
    {
        categoryID=_id;
        categoryName=_value;
    }
}
