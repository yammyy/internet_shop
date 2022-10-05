package net.yammyy.units.goods;

public class Brand implements ChoosableParameterValue
{
    int brandID;
    String brandName;
    @Override public int getID (){return brandID;}
    @Override public void setID (int _id){brandID=_id;}
    @Override public String getValue (){return brandName;}
    @Override public void setValue (String _value){brandName=_value;}
    @Override public void setParameter (int _id, String _value)
    {
        brandID=_id;
        brandName=_value;
    }
}
