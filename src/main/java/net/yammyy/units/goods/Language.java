package net.yammyy.units.goods;

public class Language {
    int id;
    String name;
    String abbreviation;

    public Language(int id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public int getID() {return id;}

    public String getName() {return name;}

    public String getAbbreviation() {return abbreviation;}
}
