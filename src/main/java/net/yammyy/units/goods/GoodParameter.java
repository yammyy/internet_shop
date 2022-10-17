package net.yammyy.units.goods;

public interface GoodParameter
{
    public int getID();
    public String getName();
    public String getValue();
    public void setParameter(Parameter _parameter,int _value);
    public void setParameter(Parameter _parameter,double _value);
    public void setParameter(Parameter _parameter,String _value);
    public void setParameter(Parameter _parameter,ChoosableParameterValue _value);
}
