package net.yammyy.db;

public interface GoodsFields {

    public static final String GOODS_TABLE = "goods";
    public static final String GOOD_ID = "id";
    public static final String GOOD_NAME = "name";
    public static final String GOOD_DESCRIPTION = "desc";
    public static final String GOOD_PRICE = "price";
    public static final String GOOD_DATE_WHEN_ADDED = "dt_added";
    public static final String GOOD_ALREADY_SOLD = "stock_left";
    public static final String GOOD_LANGUAGE = "lng";
    public static final String GOOD_CURRENCY = "currency";

    public static final String CATEGORY_TABLE = "category";
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_NAME = "value";
    public static final String PARENT_CATEGORY = "undercategory";

    public static final String BRAND_TABLE = "brand";
    public static final String BRAND_ID = "id";
    public static final String BRAND_NAME = "value";

    public static final String COLOR_TABLE = "color";
    public static final String COLOR_ID = "id";
    public static final String COLOR_NAME = "value";

    public static final String PARAMETER_TYPE_TABLE = "params_types";
    public static final String PARAMETER_TYPE_ID = "id";
    public static final String PARAMETER_TYPE_NAME = "name";
    public static final String PARAMETER_TYPE_OUTER = "from_table";

    public static final String PARAMETER_TABLE = "goods_params";
    public static final String PARAMETER_ID = "good_id";
    public static final String PARAMETER_TYPE = "type";
    public static final String PARAMETER_VALUE = "value";

    public static final String LANGUAGE_TABLE = "language";
    public static final String LANGUAGE_ID = "ID";
    public static final String LANGUAGE_NAME = "name";
    public static final String LANGUAGE_ABBR = "ABBR";

    public static final String CURRENCY_TABLE = "currency";
    public static final String CURRENCY_ID = "ID";
    public static final String CURRENCY_NAME = "name";
    public static final String CURRENCY_ABBR = "ABBR";
}
