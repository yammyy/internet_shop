package net.yammyy.units.goods;

public class Currency {
    int id;
    String name;
    String abbreviation;

    public Currency(int currencyID, String currencyName, String currencyABBR) {
        id = currencyID;
        name = currencyName;
        abbreviation = currencyABBR;
    }

    public int getID() {return id;}

    public String getName() {return name;}

    public String getAbbreviation() {return abbreviation;}
}
