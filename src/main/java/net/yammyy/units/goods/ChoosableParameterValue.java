package net.yammyy.units.goods;

public interface ChoosableParameterValue
{
    public int getID();
    public void setID (int _id);
    public String getValue();
    public void setValue (String _value);
    public void setParameter(int _id, String _value);
}
