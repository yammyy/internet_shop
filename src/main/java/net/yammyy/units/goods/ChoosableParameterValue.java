package net.yammyy.units.goods;

public interface ChoosableParameterValue {
    public int getID();

    public void setID(int id);

    public String getValue();

    public void setValue(String value);

    public void setParameter(int id, String value);
}
