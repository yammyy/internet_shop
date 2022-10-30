package net.yammyy.units.goods;

public class GoodNonChoosableParameter implements GoodParameter {
    Parameter parameter;
    String parameterValue;

    @Override
    public int getID() {return parameter.getID();}

    @Override
    public String getName() {return parameter.getName();}

    @Override
    public String getValue() {return parameterValue;}

    @Override
    public void setParameter(Parameter parameter, int value) {
        this.parameter = parameter;
        parameterValue = String.valueOf(value);
    }

    @Override
    public void setParameter(Parameter parameter, double value) {
        this.parameter = parameter;
        parameterValue = String.valueOf(value);
    }

    @Override
    public void setParameter(Parameter parameter, String value) {
        this.parameter = parameter;
        parameterValue = value;
    }

    @Override
    public void setParameter(Parameter parameter, ChoosableParameterValue value) {
        this.parameter = parameter;
        parameterValue = value.getValue();
    }
}
