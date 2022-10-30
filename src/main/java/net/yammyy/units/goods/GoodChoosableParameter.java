package net.yammyy.units.goods;

public class GoodChoosableParameter implements GoodParameter {
    Parameter parameter;
    ChoosableParameterValue parameterValue;

    @Override
    public int getID() {return parameter.getID();}

    @Override
    public String getName() {return parameter.getName();}

    @Override
    public String getValue() {return parameterValue.getValue();}

    @Override
    public void setParameter(Parameter parameter, int value) {
        this.parameter = parameter;
        parameterValue = null;
    }

    @Override
    public void setParameter(Parameter parameter, double value) {
        this.parameter = parameter;
        parameterValue = null;
    }

    @Override
    public void setParameter(Parameter parameter, String value) {
        this.parameter = parameter;
        parameterValue = null;
    }

    @Override
    public void setParameter(Parameter parameter, ChoosableParameterValue value) {
        this.parameter = parameter;
        parameterValue = value;
    }
}
