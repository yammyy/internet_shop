package net.yammyy.utils;

import net.yammyy.units.goods.Good;
import net.yammyy.units.order.Order;
import net.yammyy.units.users.User;
import net.yammyy.utils.HTMLLinks;

import javax.servlet.http.HttpSession;
import java.util.*;

public class AppUtils {
    private static int REDIRECT_ID = 0;
    private static final Map<Integer, String> id_uri_map = new HashMap<Integer, String>();
    private static final Map<String, Integer> uri_id_map = new HashMap<String, Integer>();

    // Сохранить информацию пользователя в Session.
    public static void storeLoginedUser(HttpSession _session, User _loginedUser) {
        _session.setAttribute(HTMLLinks.PARAMETER_SESSION_USER, _loginedUser);
    }

    // Получить информацию пользователя, сохраненную в Session.
    public static User getLoginedUser(HttpSession _session) {
        User loginedUser_l = (User) _session.getAttribute(HTMLLinks.PARAMETER_SESSION_USER);
        return loginedUser_l;
    }

    public static int storeRedirectAfterLoginUrl(HttpSession _session, String _requestUri) {
        Integer id = uri_id_map.get(_requestUri);
        if (id == null) {
            id = REDIRECT_ID++;
            uri_id_map.put(_requestUri, id);
            id_uri_map.put(id, _requestUri);
            return id;
        }
        return id;
    }

    public static String getRedirectAfterLoginUrl(HttpSession _session, int _redirectID) {
        String url = id_uri_map.get(_redirectID);
        if (url != null) {
            return url;
        }
        return null;
    }

    // Добавить товар в корзину в Session.
    public static void storeGoodToSessionCart(HttpSession httpSession, Good good, int quantity) {
        Map<Integer, Order> cart = (Map<Integer, Order>) httpSession.getAttribute(HTMLLinks.PARAMETER_SESSION_CART);
        if (cart == null) {
            cart = new TreeMap<>();
            httpSession.setAttribute(HTMLLinks.PARAMETER_SESSION_CART, cart);
        }
        System.out.println("storeGoodToSessionCart " + cart.size() + " " + good);
        Order newOrder = cart.get(good.getID());
        if (newOrder == null) {
            newOrder = new Order(good, quantity);
        } else {
            newOrder.setQuantity(newOrder.getQuantity() + quantity);
        }
        cart.put(good.getID(), newOrder);
        httpSession.setAttribute(HTMLLinks.PARAMETER_SESSION_CART, cart);
    }

    // Удалить товар из корзины в Session.
    public static void removeGoodFromSessionCart(HttpSession session, Good good, int quantity) {
        Map<Integer, Order> cart = (Map<Integer, Order>) session.getAttribute(HTMLLinks.PARAMETER_SESSION_CART);
        if (cart == null) {
            return;
        }
        Order deletedOrder = cart.get(good.getID());
        if (deletedOrder == null) {
            return;
        }
        int newQuantity = deletedOrder.getQuantity() - quantity;
        if (newQuantity <= 0) {
            cart.remove(good.getID());
            if (cart.isEmpty()) {
                cart = null;
            }
            session.setAttribute(HTMLLinks.PARAMETER_SESSION_CART, cart);
            return;
        }
        deletedOrder.setQuantity(newQuantity);
    }

    public static List<Order> getGoodFromSessionCart(HttpSession _session) {
        System.out.println("getGoodFromSessionCart" + 1);
        Map<Integer, Order> cart_l = (Map<Integer, Order>) _session.getAttribute(HTMLLinks.PARAMETER_SESSION_CART);
        System.out.println("getGoodFromSessionCart" + 2);
        if (cart_l == null || cart_l.isEmpty()) {
            return new ArrayList<>();
        }
        System.out.println("getGoodFromSessionCart" + 3 + " " + cart_l.size());
        return new ArrayList<>(cart_l.values());
    }

    public static boolean hasGoodInCart(HttpSession httpSession, int goodID) {
        System.out.println("getGoodFromSessionCart" + 1);
        Map<Integer, Order> cart = (Map<Integer, Order>) httpSession.getAttribute(HTMLLinks.PARAMETER_SESSION_CART);
        System.out.println("getGoodFromSessionCart" + 2);
        if (cart == null || cart.isEmpty()) {
            return false;
        }
        System.out.println("getGoodFromSessionCart" + 3 + " " + cart.size());
        return cart.containsKey(goodID);
    }
}