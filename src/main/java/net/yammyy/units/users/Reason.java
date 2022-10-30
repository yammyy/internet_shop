package net.yammyy.units.users;

public class Reason {
    int id;
    String name;

    public Reason(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getReason() {return name;}

    public int getReasonID() {return id;}
}
