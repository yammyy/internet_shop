package net.yammyy.units.users;

import net.yammyy.units.goods.Good;
import net.yammyy.units.order.*;
import net.yammyy.units.goods.Currency;
import net.yammyy.units.goods.Language;

import java.util.*;

public class User {
    int id;
    String login;
    String password;
    String familyName;
    String name;
    String fatherName;
    String email;
    Type role;
    Date registrationDate;
    Currency stdCurrency;
    Language stdLanguage;
    boolean isBlocked;
    List<UserReason> whyBlocked;
    Delivery delivery;
    Spam spam;
    Map<Integer, Orders> ordersMap;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        registrationDate = new Date();
        whyBlocked = new ArrayList<>();
        ordersMap = new TreeMap<>();
    }

    public User(int id, String login, String password, Date registrationDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
        whyBlocked = new ArrayList<>();
        ordersMap = new TreeMap<>();
        Orders favorites = new Orders(1);
        ordersMap.put(1, favorites);
        Orders cart = new Orders(2);
        ordersMap.put(2, cart);
    }

    @Override
    public boolean equals(Object obj) {
        return ((User) obj).login.equals(login);
    }

    public int getId() {
        return id;
    }

    public String getLogin() {return login;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}

    public Type getRole() {return role;}

    public void setRole(Type role) {this.role = role;}

    public String getPhoto() {
        return "img/img_avatar.png";
    }

    public String getFamilyName() {return familyName;}

    public String getName() {return name;}

    public String getFamilyAndName() {return familyName + " " + name;}

    public String getFatherName() {return fatherName;}

    public void setStandardLanguage(Language language) {stdLanguage = language;}

    public Language getStandardLanguage() {return stdLanguage;}

    public void setStandardCurrency(Currency currency) {stdCurrency = currency;}

    public Currency getStandardCurrency() {return stdCurrency;}

    public Date getRegistrationDate() {return registrationDate;}

    public Reason getLastBlockingReason() {
        if (isBlocked) {
            int maxI = 0;
            Date maxDate = new Date(0);
            for (int i = 0; i < whyBlocked.size(); i++) {
                if (maxDate.before(whyBlocked.get(i).getDate())) {
                    maxDate = whyBlocked.get(i).getDate();
                    maxI = i;
                }
            }
            return whyBlocked.get(maxI).getReason();
        } else {
            return new Reason(0, "");
        }
    }

    public void setBlockingReasons(boolean isBlocked, List<UserReason> whyBlocked) {
        this.isBlocked = isBlocked;
        this.whyBlocked = whyBlocked;
    }

    public List<UserReason> getWhyBlocked() {return whyBlocked;}

    public boolean getIsBlocked() {return isBlocked;}

    public void setBlockingReason(Reason whyBlocked, Date blockingDate) {
        UserReason userReason = new UserReason(whyBlocked, blockingDate);
        this.whyBlocked.add(userReason);
    }

    public void setBlockingReason(boolean isBlocked, Reason whyBlocked) {
        this.isBlocked = isBlocked;
        UserReason userReason = new UserReason(whyBlocked, new Date());
        this.whyBlocked.add(userReason);
    }

    public void block(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public List<Order> getFavorites() {return ordersMap.get(1).getOrder();}

    public List<Orders> getOrdersList() {return new ArrayList<>(ordersMap.values());}

    public Map<Integer, Orders> setOrdersList() {return ordersMap;}

    public List<Order> getCart() {return ordersMap.get(2).getOrder();}

    public void storeGoodToCart(Good good, int quantity) {
        Orders cart = ordersMap.get(2);
        cart.addGood(good, quantity);
    }

    public void addGoodToList(int orderID,Good good, int quantity) {
        Orders cart = ordersMap.get(orderID);
        cart.addGood(good, quantity);
    }

    public int hasMoreGoodInCart(Good good, int quantity) {
        Orders cart = ordersMap.get(2);
        return cart.cartHasMoreGood(good, quantity);
    }

    public int countGoodInCart(Good good) {
        Orders cart = ordersMap.get(2);
        return cart.cartHasMoreGood(good, 0);
    }

    public Orders getGoodsInList(int orderId) {return ordersMap.get(orderId);}

    public void removeGoodFromCart(Good good, int quantity) {
        Orders cart = ordersMap.get(2);
        cart.removeGood(good, quantity);
    }

    public void setFIO(String familyName, String name, String fatherName) {
        this.familyName = familyName;
        this.name = name;
        this.fatherName = fatherName;
    }

    public void setEmail(String email) {this.email = email;}
    public int getMaxOrderID() {return Collections.max(ordersMap.keySet());}

    public void addNewOrder(Orders orders, int order_id) {
        ordersMap.put(order_id,orders);
    }

    public void clearCart() {
        ordersMap.remove(2);
        ordersMap.put(2,new Orders(2));
    }

    public void addGoodsToList(int orderId, List<Order> orderList) {
        Orders list=ordersMap.get(orderId);
        for (int i=0;i<orderList.size();i++)
        {
            list.addOrder(orderList.get(i));
        }
    }

    public void setStatus(int orderID, Status status, Date statusDate) {
        Orders list=ordersMap.get(orderID);
        list.setStatus(status,statusDate);
    }
}
