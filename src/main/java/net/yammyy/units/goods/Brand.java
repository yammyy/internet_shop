package net.yammyy.units.goods;

public class Brand implements ChoosableParameterValue {
    int brandID;
    String brandName;

    @Override
    public int getID() {return brandID;}

    @Override
    public void setID(int id) {brandID = id;}

    @Override
    public String getValue() {return brandName;}

    @Override
    public void setValue(String value) {brandName = value;}

    @Override
    public void setParameter(int id, String value) {
        brandID = id;
        brandName = value;
    }
}
