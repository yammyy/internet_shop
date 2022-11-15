package net.yammyy.db;

public interface OrdersFields {
    public static final String ORDERS_TABLE="orders";
    public static final String ORDERS_USER_ID="u_id";
    public static final String ORDERS_ORDER_ID="id";
    public static final String ORDERS_DELIVERY_TYPE="delivery_type";

    public static final String ORDER_LIST_TABLE="order_list";
    public static final String ORDER_LIST_USER_ID = "u_id";
    public static final String ORDER_LIST_ORDER_ID="order_id";
    public static final String ORDER_LIST_GOOD_ID="good_id";
    public static final String ORDER_LIST_QUANTITY="quant";

    public static final String ORDER_STATUSES_TABLE ="order_status";
    public static final String ORDER_STATUSES_USER_ID="u_id";
    public static final String ORDER_STATUSES_STATUS_ID="status_id";
    public static final String ORDER_STATUSES_ORDER_ID="order_id";
    public static final String ORDER_STATUSES_DATE_GET="dt";
    public static final String STATUSES_TABLE = "order_status_desc";
    public static final String STATUSES_ID = "ID";
    public static final String STATUSES_DESCRIPTION = "status";
}
