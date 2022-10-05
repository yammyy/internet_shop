package net.yammyy.units.goods;

public class Parameter
{
    int parameterID;
    String parameterName;
    public int getID (){return parameterID;}
    public void setID (int _id) {parameterID=_id;}
    public String getName () {return parameterName;}
    public void setName (String _value) {parameterName=_value;}
    public void setParameter (int _id, String _value)
    {
        parameterID=_id;
        parameterName=_value;
    }
}
