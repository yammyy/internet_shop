package net.yammyy.units.goods;

public class Category implements ChoosableParameterValue {
    int categoryID;
    String categoryName;

    @Override
    public int getID() {return categoryID;}

    @Override
    public void setID(int id) {categoryID = id;}

    @Override
    public String getValue() {return categoryName;}

    @Override
    public void setValue(String value) {categoryName = value;}

    @Override
    public void setParameter(int id, String value) {
        categoryID = id;
        categoryName = value;
    }
}
