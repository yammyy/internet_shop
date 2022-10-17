package net.yammyy.units.goods;

public class GoodChoosableParameter implements GoodParameter
{
    Parameter parameter;
    ChoosableParameterValue parameterValue;
    @Override public int getID (){return parameter.getID();}
    @Override public String getName (){return parameter.getName();}
    @Override public String getValue (){return parameterValue.getValue();}
    @Override public void setParameter (Parameter _parameter, int _value)
    {
        parameter=_parameter;
        parameterValue=null;
    }
    @Override public void setParameter (Parameter _parameter, double _value)
    {
        parameter=_parameter;
        parameterValue=null;
    }
    @Override public void setParameter (Parameter _parameter, String _value)
    {
        parameter=_parameter;
        parameterValue=null;
    }
    @Override public void setParameter (Parameter _parameter, ChoosableParameterValue _value)
    {
        parameter=_parameter;
        parameterValue=_value;
    }
}
