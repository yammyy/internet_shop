package net.yammyy.units.order;

import net.yammyy.units.goods.Good;

public class Order
{
    Good good;
    int quantity;
    public Order(Good good, int quantity){
        this.good=good;
        this.quantity=quantity;
    }
    public void setGood(Good _good){good=_good;}
    public Good getGood(){return good;}
    public void setQuantity(int _quantity){quantity=_quantity;}
    public int getQuantity(){return quantity;}
}
