package net.yammyy.units.goods;

public class Parameter {
    int parameterID;
    String parameterName;

    public int getID() {return parameterID;}

    public void setID(int id) {parameterID = id;}

    public String getName() {return parameterName;}

    public void setName(String value) {parameterName = value;}

    public void setParameter(int id, String value) {
        parameterID = id;
        parameterName = value;
    }
}
