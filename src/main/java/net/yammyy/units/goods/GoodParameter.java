package net.yammyy.units.goods;

public interface GoodParameter {
    public int getID();

    public String getName();

    public String getValue();

    public void setParameter(Parameter parameter, int value);

    public void setParameter(Parameter parameter, double value);

    public void setParameter(Parameter parameter, String value);

    public void setParameter(Parameter parameter, ChoosableParameterValue value);
}
