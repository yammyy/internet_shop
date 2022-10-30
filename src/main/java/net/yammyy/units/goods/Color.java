package net.yammyy.units.goods;

public class Color implements ChoosableParameterValue {
    int colorID;
    String colorName;

    @Override
    public int getID() {return colorID;}

    @Override
    public void setID(int id) {colorID = id;}

    @Override
    public String getValue() {return colorName;}

    @Override
    public void setValue(String value) {colorName = value;}

    @Override
    public void setParameter(int id, String value) {
        colorID = id;
        colorName = value;
    }
}
