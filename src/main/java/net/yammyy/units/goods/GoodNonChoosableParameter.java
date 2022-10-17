package net.yammyy.units.goods;

public class GoodNonChoosableParameter implements GoodParameter
{
    Parameter parameter;
    String parameterValue;
    @Override public int getID (){return parameter.getID();}
    @Override public String getName (){return parameter.getName();}
    @Override public String getValue (){return parameterValue;}
    @Override public void setParameter (Parameter _parameter, int _value)
    {
        parameter=_parameter;
        parameterValue=String.valueOf(_value);
    }
    @Override public void setParameter (Parameter _parameter, double _value)
    {
        parameter=_parameter;
        parameterValue=String.valueOf(_value);
    }
    @Override public void setParameter (Parameter _parameter, String _value)
    {
        parameter=_parameter;
        parameterValue=_value;
    }
    @Override public void setParameter (Parameter _parameter, ChoosableParameterValue _value)
    {
        parameter=_parameter;
        parameterValue=_value.getValue();
    }
}
