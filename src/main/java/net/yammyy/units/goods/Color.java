package net.yammyy.units.goods;

public class Color implements ChoosableParameterValue
{
    int colorID;
    String colorName;
    @Override public int getID (){return colorID;}
    @Override public void setID (int _id){colorID=_id;}
    @Override public String getValue (){return colorName;}
    @Override public void setValue (String _value){colorName=_value;}
    @Override public void setParameter (int _id, String _value)
    {
        colorID=_id;
        colorName=_value;
    }
}
