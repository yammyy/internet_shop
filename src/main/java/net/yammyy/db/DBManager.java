package net.yammyy.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;
import java.util.Date;

import net.yammyy.units.goods.*;
import net.yammyy.units.goods.Currency;
import net.yammyy.units.order.Order;
import net.yammyy.units.order.Orders;
import net.yammyy.units.order.Status;
import net.yammyy.units.users.Reason;
import net.yammyy.units.users.Type;
import net.yammyy.units.users.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

class DBGeneral {
    private Map<Integer, Language> languageMap;
    private Map<Integer, Currency> currencyMap;

    void initializeDBGeneral(Connection connection) throws SQLException {
        initializeLanguages(connection);
        initializeCurrencies(connection);
    }

    private void initializeCurrencies(Connection connection) throws SQLException {
        String thisName = "initializeCurrencies";
        currencyMap = new TreeMap<>();
        String sql = "SELECT * FROM " + GoodsFields.CURRENCY_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int currencyID = resultSet.getInt(GoodsFields.CURRENCY_ID);
                Currency oneCurrency = new Currency(currencyID, resultSet.getString(GoodsFields.CURRENCY_NAME),
                        resultSet.getString(GoodsFields.CURRENCY_ABBR));
                currencyMap.put(currencyID, oneCurrency);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getErrorCode() + " | " + _e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void initializeLanguages(Connection connection) throws SQLException {
        String thisName = "initializeLanguages";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        languageMap = new TreeMap<>();
        String sql = "SELECT * FROM " + GoodsFields.LANGUAGE_TABLE;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            System.out.println(thisName + LogMessages.SQL_CODE + sql);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int id_l = resultSet.getInt(GoodsFields.LANGUAGE_ID);
                Language language_l = new Language(id_l, resultSet.getString(GoodsFields.LANGUAGE_NAME),
                        resultSet.getString(GoodsFields.LANGUAGE_ABBR));
                languageMap.put(id_l, language_l);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getSQLState() + " " + _e.getErrorCode());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    List<Language> getLanguages() {
        return new ArrayList<>(languageMap.values());
    }

    List<Currency> getCurrencies() {
        return new ArrayList<>(currencyMap.values());
    }

    Language getLanguageByID(int _id) {
        return languageMap.get(_id);
    }

    Currency getCurrencyByID(int _id) {
        return currencyMap.get(_id);
    }
}

class DBGoods {
    private Map<Integer, ChoosableParameterValue> categoryMap;
    private Map<Integer, ChoosableParameterValue> colorMap;
    private Map<Integer, ChoosableParameterValue> brandMap;
    private Map<Integer, Parameter> parameterMap;
    private Map<Integer, Good> goodMap;

    void initializeDBGoods(Connection connection) throws SQLException {
        initializeCategories(connection);
        initializeColors(connection);
        initializeBrands(connection);
        initializeOtherParameters(connection);
        initializeGoods(connection);
    }

    private void initializeGoods(Connection connection) throws SQLException {
        String thisName = "initializeGoods";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "SELECT * FROM " + GoodsFields.GOODS_TABLE + " WHERE " + GoodsFields.GOOD_ALREADY_SOLD + " <> ?";
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        goodMap = new TreeMap<>();
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, 0);
            resultSet = preparedStatement.executeQuery();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int entityId = resultSet.getInt(GoodsFields.GOOD_ID);
                Good good = new Good(entityId, resultSet.getString(GoodsFields.GOOD_NAME));
                good.setDescription(resultSet.getString(GoodsFields.GOOD_DESCRIPTION));
                good.setPrice(resultSet.getFloat(GoodsFields.GOOD_PRICE));
                good.setDateWhenAdded(new Date(resultSet.getDate(GoodsFields.GOOD_DATE_WHEN_ADDED).getTime()));
                goodMap.put(entityId, good);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        System.out.println(thisName + " join parameters from another table");
        setGoodsParameters(connection);
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void setGoodsParameters(Connection connection) throws SQLException {
        String thisName = "setGoodsParameters";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "SELECT t1.*,t2." + GoodsFields.PARAMETER_TYPE_OUTER + " FROM " + GoodsFields.PARAMETER_TABLE +
                " t1 join " + GoodsFields.PARAMETER_TYPE_TABLE + " t2 on t1.type=t2.id";
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int goodId = resultSet.getInt(GoodsFields.PARAMETER_ID);
                Good good = goodMap.get(goodId);
                int paramId = resultSet.getInt(GoodsFields.PARAMETER_TYPE);
                if (paramId == 5) {
                    good.addCategory(categoryMap.get(resultSet.getInt(GoodsFields.PARAMETER_VALUE)));
                } else {
                    GoodParameter goodParameter;
                    if (resultSet.getString(GoodsFields.PARAMETER_TYPE_OUTER).length() > 0) {
                        goodParameter = new GoodChoosableParameter();
                        goodParameter.setParameter(parameterMap.get(paramId),
                                resultSet.getInt(GoodsFields.PARAMETER_VALUE));
                    } else {
                        goodParameter = new GoodNonChoosableParameter();
                        goodParameter.setParameter(parameterMap.get(paramId),
                                resultSet.getDouble(GoodsFields.PARAMETER_VALUE));
                    }
                    good.setParam(goodParameter);
                }
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private <T extends ChoosableParameterValue> void initializeChoosableParameterValues(Connection connection,
                                                                                        Class<T> type,
                                                                                        String tableName,
                                                                                        String fieldID,
                                                                                        String fieldValue) throws SQLException {
        String thisName = "initialize" + type.getSimpleName();
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        Map<Integer, ChoosableParameterValue> parametersMap = null;
        if (type == Category.class) {
            categoryMap = new TreeMap<>();
            parametersMap = categoryMap;
        } else if (type == Color.class) {
            colorMap = new TreeMap<>();
            parametersMap = colorMap;
        } else if (type == Brand.class) {
            brandMap = new TreeMap<>();
            parametersMap = brandMap;
        }
        System.out.println(thisName + " Get storage that needed");
        String sql = "SELECT * FROM " + tableName;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                ChoosableParameterValue oneParameter = type.getDeclaredConstructor().newInstance();
                oneParameter.setParameter(resultSet.getInt(fieldID), resultSet.getString(fieldValue));
                parametersMap.put(resultSet.getInt(fieldID), oneParameter);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            System.out.println(thisName + LogMessages.ERROR_MESSAGE + e.getMessage());
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void initializeBrands(Connection connection) throws SQLException {
        initializeChoosableParameterValues(connection, Category.class, GoodsFields.CATEGORY_TABLE,
                GoodsFields.CATEGORY_ID, GoodsFields.CATEGORY_NAME);
    }

    private void initializeColors(Connection connection) throws SQLException {
        initializeChoosableParameterValues(connection, Color.class, GoodsFields.COLOR_TABLE, GoodsFields.COLOR_ID,
                GoodsFields.COLOR_NAME);
    }

    private void initializeCategories(Connection connection) throws SQLException {
        initializeChoosableParameterValues(connection, Category.class, GoodsFields.CATEGORY_TABLE,
                GoodsFields.CATEGORY_ID, GoodsFields.CATEGORY_NAME);
    }

    private void initializeOtherParameters(Connection connection) throws SQLException {
        String thisName = "initializeNonChoosableParameters";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        parameterMap = new TreeMap<>();
        String sql = "SELECT * FROM " + GoodsFields.PARAMETER_TYPE_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                Parameter parameter = new Parameter();
                parameter.setParameter(resultSet.getInt(GoodsFields.PARAMETER_TYPE_ID),
                        resultSet.getString(GoodsFields.PARAMETER_TYPE_NAME));
                parameterMap.put(resultSet.getInt(GoodsFields.PARAMETER_TYPE_ID), parameter);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    List<ChoosableParameterValue> getAllCategories() {
        return new ArrayList<>(categoryMap.values());
    }

    List<ChoosableParameterValue> getAllColors() {
        return new ArrayList<>(categoryMap.values());
    }

    List<ChoosableParameterValue> getAllBrands() {
        return new ArrayList<>(brandMap.values());
    }

    List<Parameter> getAllParameters() {
        return new ArrayList<>(parameterMap.values());
    }

    List<Good> getAllGoods() {
        return new ArrayList<>(goodMap.values());
    }

    List<Good> getGoodByCategory(int categoryID) {
        List<Good> result = new ArrayList<>();
        Iterator entryIterator = goodMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIterator.next();
            if (((Good) entry.getValue()).hasCategory(categoryID)) {
                result.add((Good) entry.getValue());
            }
        }
        return result;
    }

    List<Good> getGoodByParameter(int parameterID) {
        List<Good> result = new ArrayList<>();
        Iterator entryIterator = goodMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIterator.next();
            if (((Good) entry.getValue()).hasCategory(parameterID)) {
                result.add((Good) entry.getValue());
            }
        }
        return result;
    }

    List<Good> getGoodByParameters(int parameterId, int value) {
        return null;
    }

    Good getGoodByID(int id) {
        return goodMap.get(id);
    }

    public boolean insertNewGoodToList(Connection connection, int userId, int orderId, int goodID, int quantity) throws SQLException {
        String thisName = "insertGoodToCart";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            System.out.println(thisName + LogMessages.START_TRANSACTION);
            connection.setAutoCommit(false);
            int iso_l = connection.getTransactionIsolation();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String sql = "INSERT IGNORE INTO " + OrdersFields.ORDERS_TABLE + " (" + OrdersFields.ORDERS_ORDER_ID + ","
                    + " " + OrdersFields.ORDERS_USER_ID + ", " + OrdersFields.ORDERS_DELIVERY_TYPE + ") VALUES (?, ?,"
                    + " ?);";
            System.out.println(thisName + LogMessages.SQL_CODE + sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            sql = "INSERT INTO " + OrdersFields.ORDER_LIST_TABLE + "(" + OrdersFields.ORDER_LIST_USER_ID + ", " + OrdersFields.ORDER_LIST_ORDER_ID + ", " + OrdersFields.ORDER_LIST_GOOD_ID + ", " + OrdersFields.ORDER_LIST_QUANTITY + ") VALUES (?,?,?,?);";
            System.out.println(thisName + LogMessages.SQL_CODE + sql);
            PreparedStatement sta2 = connection.prepareStatement(sql);
            sta2.setInt(1, userId);
            sta2.setInt(2, orderId);
            sta2.setInt(3, goodID);
            sta2.setInt(4, quantity);
            sta2.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            connection.commit();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(iso_l);
            result = true;
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getErrorCode() + " | " + _e.getSQLState());
            connection.rollback();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return result;
    }

    public boolean deleteGoodFromList(Connection connection, int userId, int orderId, int goodID) throws SQLException {
        String thisName = "deleteGoodFromCart";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "DELETE FROM " + OrdersFields.ORDER_LIST_TABLE + " WHERE " + OrdersFields.ORDER_LIST_USER_ID +
                "=? and " + OrdersFields.ORDER_LIST_ORDER_ID + "=? and " + OrdersFields.ORDER_LIST_GOOD_ID + "=?;";
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            System.out.println(thisName + LogMessages.SQL_CODE + sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, orderId);
            preparedStatement.setInt(3, goodID);
            preparedStatement.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            result = true;
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getErrorCode() + " | " + _e.getSQLState());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return result;
    }

    public boolean updateGoodInList(Connection connection, int userId, int orderId, int goodID, int newQuantity) throws SQLException {
        String thisName = "updateGoodInCart";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql =
                "UPDATE " + OrdersFields.ORDER_LIST_TABLE + " SET " + OrdersFields.ORDER_LIST_QUANTITY + "=? " +
                        "WHERE " + OrdersFields.ORDER_LIST_USER_ID + "=? and " + OrdersFields.ORDER_LIST_ORDER_ID +
                        "=? and " + OrdersFields.ORDER_LIST_GOOD_ID + "=?;";
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            System.out.println(thisName + LogMessages.SQL_CODE + sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, orderId);
            preparedStatement.setInt(4, goodID);
            preparedStatement.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            result = true;
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getErrorCode() + " | " + _e.getSQLState());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return result;
    }

    public boolean updateGoodsInList(Connection connection, int loginedUserId, int orderId, int newOrderID,
                                     Date date) throws SQLException {
        String thisName = "updateGoodsInList";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            System.out.println(thisName + LogMessages.START_TRANSACTION);
            connection.setAutoCommit(false);
            int iso_l = connection.getTransactionIsolation();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String sql = "INSERT IGNORE INTO " + OrdersFields.ORDERS_TABLE + " (" + OrdersFields.ORDERS_ORDER_ID + ","
                    + " " + OrdersFields.ORDERS_USER_ID + ", " + OrdersFields.ORDERS_DELIVERY_TYPE + ") VALUES (?, ?,"
                    + " ?);";
            System.out.println(thisName + LogMessages.SQL_CODE + sql + " " + newOrderID);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newOrderID);
            preparedStatement.setInt(2, loginedUserId);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            sql = "UPDATE " + OrdersFields.ORDER_LIST_TABLE + " SET " + OrdersFields.ORDER_LIST_ORDER_ID + "=? " +
                    "WHERE " + OrdersFields.ORDER_LIST_USER_ID + "=? and " + OrdersFields.ORDER_LIST_ORDER_ID + "=?;";
            System.out.println(thisName + LogMessages.SQL_CODE + sql + newOrderID + " " + loginedUserId + " " + orderId);
            PreparedStatement sta2 = connection.prepareStatement(sql);
            sta2.setInt(1, newOrderID);
            sta2.setInt(2, loginedUserId);
            sta2.setInt(3, orderId);
            sta2.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            sql = "INSERT INTO " + OrdersFields.ORDER_STATUSES_TABLE + " (" + OrdersFields.ORDER_STATUSES_ORDER_ID + ","
                    + " " + OrdersFields.ORDER_STATUSES_USER_ID + ", " + OrdersFields.ORDER_STATUSES_STATUS_ID + ", " +
                    OrdersFields.ORDER_STATUSES_DATE_GET + ") VALUES (?, ?,"
                    + " ?, ?);";
            System.out.println(thisName + LogMessages.SQL_CODE + sql + " " + newOrderID);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newOrderID);
            preparedStatement.setInt(2, loginedUserId);
            preparedStatement.setInt(3, 1);
            preparedStatement.setDate(3, (java.sql.Date) date);
            preparedStatement.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            connection.commit();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(iso_l);
            result = true;
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getErrorCode() + " | " + _e.getSQLState());
            connection.rollback();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return result;
    }

    public boolean insertNewGoodToList(Connection connection, int userId, int orderId, List<Order> orderList) throws SQLException {
        String thisName = "insertNewGoodToList";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            System.out.println(thisName + LogMessages.START_TRANSACTION);
            connection.setAutoCommit(false);
            int iso_l = connection.getTransactionIsolation();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String sql = "INSERT IGNORE INTO " + OrdersFields.ORDERS_TABLE + " (" + OrdersFields.ORDERS_ORDER_ID + ","
                    + " " + OrdersFields.ORDERS_USER_ID + ", " + OrdersFields.ORDERS_DELIVERY_TYPE + ") VALUES (?, ?,"
                    + " ?);";
            System.out.println(thisName + LogMessages.SQL_CODE + sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
            for (int i = 0; i < orderList.size(); i++) {
                sql = "DELETE FROM " + OrdersFields.ORDER_LIST_TABLE + " WHERE " + OrdersFields.ORDER_LIST_ORDER_ID +
                        "=? and " + OrdersFields.ORDER_LIST_USER_ID + "=? and " + OrdersFields.ORDER_LIST_GOOD_ID +
                        "=?;";
                System.out.println(thisName + LogMessages.SQL_CODE + sql);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setInt(3, orderList.get(i).getGood().getID());
                preparedStatement.executeUpdate();
                sql = "INSERT IGNORE INTO " + OrdersFields.ORDER_LIST_TABLE + " ( " + OrdersFields.ORDER_LIST_ORDER_ID +
                        ", " + OrdersFields.ORDER_LIST_USER_ID + ", " + OrdersFields.ORDER_LIST_GOOD_ID + ", " +
                        OrdersFields.ORDER_LIST_QUANTITY + ") VALUES (?, ?, ?, ?);";
                System.out.println(thisName + LogMessages.SQL_CODE + sql);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setInt(3, orderList.get(i).getGood().getID());
                preparedStatement.setInt(4, orderList.get(i).getQuantity());
                preparedStatement.executeUpdate();
            }
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            connection.commit();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(iso_l);
            result = true;
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getErrorCode() + " | " + _e.getSQLState());
            connection.rollback();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return result;
    }

}

class DBUsers {
    private Map<Integer, Type> roleMap;
    private Map<Integer, Reason> blockingReasonMap;
    private Map<Integer, User> userMap;
    private Map<Integer, Status> statusMap;

    void initializeDBUsers(Connection connection, DBGeneral dbGeneral, DBGoods dbGoods) throws SQLException {
        initializeBlockingReasons(connection);
        initializeUserRoles(connection);
        initializeStatuses(connection);
        initializeUsers(connection, dbGeneral, dbGoods);
    }

    private void initializeStatuses(Connection connection) throws SQLException {
        String thisName = "initializeStatuses";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        statusMap = new TreeMap<>();
        String sql = "SELECT * FROM " + OrdersFields.STATUSES_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int statusID = resultSet.getInt(OrdersFields.STATUSES_ID);
                Status status = new Status(statusID, resultSet.getString(OrdersFields.STATUSES_DESCRIPTION));
                statusMap.put(statusID, status);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void initializeUserRoles(Connection connection) throws SQLException {
        String thisName = "initializeUserRoles";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        roleMap = new TreeMap<>();
        String sql = "SELECT * FROM " + UsersFields.ROLES_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int roleID = resultSet.getInt(UsersFields.ROLES_ID);
                Type role = new Type(roleID, resultSet.getString(UsersFields.ROLES_NAME));
                roleMap.put(roleID, role);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        setSecureLinks(connection);
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void setSecureLinks(Connection connection) throws SQLException {
        String thisName = "getSecureURLs";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "SELECT * FROM " + UsersFields.SECURE_URLS_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int roleId = resultSet.getInt(UsersFields.SECURE_URLS_ROLE_ID);
                Type role = roleMap.get(roleId);
                role.addURL(resultSet.getString(UsersFields.SECURE_URLS_URL));
            }
            System.out.println(thisName + LogMessages.END_PROCEDURE);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void initializeBlockingReasons(Connection connection) throws SQLException {
        String thisName = "initializeReasons";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        blockingReasonMap = new TreeMap<>();
        String sql = "SELECT * FROM " + UsersFields.BLOCKED_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int reasonID = resultSet.getInt(UsersFields.BLOCKED_REASON_ID);
                Reason reason = new Reason(reasonID, resultSet.getString(UsersFields.BLOCKED_REASON_NAME));
                blockingReasonMap.put(reasonID, reason);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void initializeUsers(Connection connection, DBGeneral dbGeneral, DBGoods dbGoods) throws SQLException {
        String thisName = "initializeUsers";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        userMap = new TreeMap<>();
        String sql = "SELECT * FROM " + UsersFields.USERS_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE + sql);
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int userId = resultSet.getInt(UsersFields.USERS_ID);
                User user = new User(userId, resultSet.getString(UsersFields.USERS_LOGIN),
                        resultSet.getString(UsersFields.USERS_PASSWORD),
                        resultSet.getDate(UsersFields.USERS_REGISTRATION_DATE));
                user.setFIO(resultSet.getString(UsersFields.USERS_FAMILY_NAME),
                        resultSet.getString(UsersFields.USERS_NAME),
                        resultSet.getString(UsersFields.USERS_FATHER_NAME));
                user.setEmail(resultSet.getString(UsersFields.USERS_EMAIL));
                Type role = roleMap.get(resultSet.getInt(UsersFields.USERS_ROLE));
                user.setRole(role);
                boolean userBlocked = resultSet.getBoolean(UsersFields.USERS_BLOCKED);
                user.block(userBlocked);
                Language userLanguage =
                        dbGeneral.getLanguages().get(resultSet.getInt(UsersFields.USERS_STANDARD_LANGUAGE));
                user.setStandardLanguage(userLanguage);
                Currency userCurrency =
                        dbGeneral.getCurrencies().get(resultSet.getInt(UsersFields.USERS_STANDARD_CURRENCY));
                user.setStandardCurrency(userCurrency);
                userMap.put(userId, user);
            }
            System.out.println(thisName + LogMessages.SQL_SUCCESS);
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + "join blocking from blocking table");
        setUserBlocking(connection);
        System.out.println(thisName + "join orders from orders table");
        setUserOrders(connection, dbGoods);
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void setUserOrders(Connection connection, DBGoods dbGoods) throws SQLException {
        String thisName = "setUserOrders";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "SELECT * FROM " + OrdersFields.ORDERS_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int userID = resultSet.getInt(OrdersFields.ORDERS_USER_ID);
                User user = userMap.get(userID);
                int orderID = resultSet.getInt(OrdersFields.ORDERS_ORDER_ID);
                user.addNewOrder(new Orders(orderID), orderID);
            }
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        setOrdersGoods(connection, dbGoods);
        setOrderStatuses(connection);
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void setOrderStatuses(Connection connection) throws SQLException {
        String thisName = "setOrderStatuses";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "SELECT * FROM " + OrdersFields.ORDER_STATUSES_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int userID = resultSet.getInt(OrdersFields.ORDER_STATUSES_USER_ID);
                User user = userMap.get(userID);
                int orderID = resultSet.getInt(OrdersFields.ORDER_STATUSES_ORDER_ID);
                int statusID = resultSet.getInt(OrdersFields.ORDER_STATUSES_STATUS_ID);
                Date statusDate = new Date(resultSet.getDate(OrdersFields.ORDER_STATUSES_DATE_GET).getTime());
                user.setStatus(orderID, statusMap.get(statusID), statusDate);
            }
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void setOrdersGoods(Connection connection, DBGoods dbGoods) throws SQLException {
        String thisName = "setOrdersGoods";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "SELECT * FROM " + OrdersFields.ORDER_LIST_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int userID = resultSet.getInt(OrdersFields.ORDER_LIST_USER_ID);
                User user = userMap.get(userID);
                int orderID = resultSet.getInt(OrdersFields.ORDER_LIST_ORDER_ID);
                int goodID = resultSet.getInt(OrdersFields.ORDER_LIST_GOOD_ID);
                int quantity = resultSet.getInt(OrdersFields.ORDER_LIST_QUANTITY);
                user.addGoodToList(orderID, dbGoods.getGoodByID(goodID), quantity);
            }
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    private void setUserBlocking(Connection connection) throws SQLException {
        String thisName = "setUserBlocking";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        String sql = "SELECT * FROM " + UsersFields.USERS_BLOCKING_TABLE;
        System.out.println(thisName + LogMessages.SQL_CODE);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            while (resultSet.next()) {
                int userID = resultSet.getInt(UsersFields.USERS_BLOCKING_USER_ID);
                User user = userMap.get(userID);
                user.setBlockingReason(blockingReasonMap.get(resultSet.getInt(UsersFields.USERS_BLOCKING_REASON)),
                        new java.util.Date(resultSet.getDate(UsersFields.USERS_BLOCKING_DATE).getTime()));
            }
        } catch (SQLException e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + e.getErrorCode() + " | " + e.getSQLState());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    Reason getReasonByID(int id) {
        return blockingReasonMap.get(id);
    }

    Type getRoleByID(int id) {
        return roleMap.get(id);
    }

    Map<Integer, Type> getAllRoles() {
        return roleMap;
    }

    User checkUser(String login, String password) {
        String thisName = "checkUser";
        User user = new User(0, login, password);
        System.out.println(thisName + " user exists?");
        boolean isUser = userMap.containsValue(user);
        if (isUser) {
            int userId =
                    userMap.entrySet().stream().filter(entry -> user.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().get();
            System.out.println(thisName + " user_id: " + userId);
            if (userMap.get(userId).getPassword().equals(password)) {
                return userMap.get(userId);
            }
        }
        System.out.println(thisName + " user doesn't exist.");
        return null;
    }

    User findUserByID(int id) {return userMap.get(id);}

    List<User> getAllUsers() {return new ArrayList<>(userMap.values());}

    boolean updateUser(int id, User user, Connection connection) throws SQLException {
        String thisName = "updateUser";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        PreparedStatement preparedStatement = null;
        try {
            System.out.println(thisName + LogMessages.START_TRANSACTION);
            connection.setAutoCommit(false);
            int iso_l = connection.getTransactionIsolation();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String sql = "UPDATE user " +
                    //       1        2    3   4    5       6          7        8        9
                    "SET pwd=?, email=?, F=?, I=?, O=?, role=?, stdcur=?, stdln=?, blocked=? " + "WHERE ID=?"; //10
            System.out.println(thisName + LogMessages.SQL_CODE + sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getFamilyName());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getFatherName());
            preparedStatement.setInt(6, user.getRole().getID());
            preparedStatement.setInt(7, user.getStandardCurrency().getID());
            preparedStatement.setInt(8, user.getStandardLanguage().getID());
            preparedStatement.setInt(9, user.getLastBlockingReason().getReasonID());
            preparedStatement.setInt(10, id);
            preparedStatement.executeUpdate();
            System.out.println(thisName + LogMessages.SQL_EXECUTE);
            if (user.getIsBlocked()) {
                sql = "INSERT INTO user_blocking (u_id, reason, dt) VALUES (?,?,SYSDATE())";
                System.out.println(thisName + LogMessages.SQL_CODE + sql);
                PreparedStatement sta2 = connection.prepareStatement(sql);
                sta2.setInt(1, id);
                sta2.setInt(2, user.getLastBlockingReason().getReasonID());
                sta2.executeUpdate();
                System.out.println(thisName + LogMessages.SQL_EXECUTE);
            }
            connection.commit();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(iso_l);
            return true;
        } catch (SQLException _e) {
            System.out.println(thisName + LogMessages.SQL_ERROR + _e.getErrorCode() + " | " + _e.getSQLState());
            connection.rollback();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return false;
    }

    void refreshUser(int id, User user) {
        User userL = findUserByID(id);
        userL.setPassword(user.getPassword());
        userL.setEmail(user.getEmail());
        userL.setFIO(user.getFamilyName(), user.getName(), user.getFatherName());
        userL.setRole(user.getRole());
        userL.setStandardCurrency(user.getStandardCurrency());
        userL.setStandardLanguage(user.getStandardLanguage());
        userL.setBlockingReasons(user.getIsBlocked(), user.getWhyBlocked());
    }

    public Status getStatus(int statusID) {
        return statusMap.get(statusID);
    }
}

public class DBManager {
    public static DBManager inst;
    private final DataSource ds;
    private DBGeneral dbGeneral;
    private DBGoods dbGoods;
    private DBUsers dbUsers;

    public static synchronized DBManager getInstance() {
        if (inst == null) {
            try {
                inst = new DBManager();
            } catch (NamingException | SQLException e) {
                System.out.println("getInstance " + LogMessages.ERROR_MESSAGE + e.getMessage());
            }
        }
        return inst;
    }

    private DBManager() throws NamingException, SQLException {
        String thisName = "DBManager";
        System.out.println(thisName + LogMessages.START_PROCEDURE);
        Context initContext = new InitialContext();
        System.out.println(thisName + " Initialize context");
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        System.out.println(thisName + " Lookup for env");
        ds = (DataSource) envContext.lookup("jdbc/dataSource");
        System.out.println(thisName + " Lookup for dataSource " + ds);
        System.out.println(thisName + " Initialize general");
        dbGeneral = new DBGeneral();
        dbGeneral.initializeDBGeneral(ds.getConnection());
        System.out.println(thisName + " Initialize goods");
        dbGoods = new DBGoods();
        dbGoods.initializeDBGoods(ds.getConnection());
        System.out.println(thisName + " Initialize users");
        dbUsers = new DBUsers();
        dbUsers.initializeDBUsers(ds.getConnection(), dbGeneral, dbGoods);
        System.out.println(thisName + " All was initialized");
        System.out.println(thisName + LogMessages.END_PROCEDURE);
    }

    public Connection getConnection() throws SQLException {return ds.getConnection();}

    public List<Language> getLanguages() {
        return dbGeneral.getLanguages();
    }

    public Language getLanguageByID(int _id) {return dbGeneral.getLanguageByID(_id);}

    public List<Currency> getCurrencies() {return dbGeneral.getCurrencies();}

    public Currency getCurrencyByID(int _id) {return dbGeneral.getCurrencyByID(_id);}

    public Good getGoodByID(int _id) {return dbGoods.getGoodByID(_id);}

    public List<ChoosableParameterValue> getAllCategories() {return dbGoods.getAllCategories();}

    public List<ChoosableParameterValue> getAllColors() {return dbGoods.getAllColors();}

    public List<ChoosableParameterValue> getAllBrands() {
        return dbGoods.getAllBrands();
    }

    public List<Parameter> getAllParameters() {
        return dbGoods.getAllParameters();
    }

    public List<Good> getGoods() {
        return dbGoods.getAllGoods();
    }

    public List<Good> getGoods(int _parameter, int _value) {
        return dbGoods.getGoodByCategory(_value);
    }

    public Reason getReasonByID(int _id) {
        return dbUsers.getReasonByID(_id);
    }

    public Type getRoleByID(int _id) {
        return dbUsers.getRoleByID(_id);
    }

    public Map<Integer, Type> getRoles() {
        return dbUsers.getAllRoles();
    }

    public User findUser(String _login, String _password) {return dbUsers.checkUser(_login, _password);}

    public User findUser(int _id) {
        return dbUsers.findUserByID(_id);
    }

    public List<User> getUsers() {
        return dbUsers.getAllUsers();
    }

    public boolean updateUser(int _id, User _user) throws SQLException {
        boolean result = dbUsers.updateUser(_id, _user, ds.getConnection());
        if (result) {
            dbUsers.refreshUser(_id, _user);
        }
        return result;
    }

    public List<Orders> getOrders() {return null;}

    public Good findGood(int _id) {return dbGoods.getGoodByID(_id);}

    public boolean storeGoodToCart(User loginedUser, Good good, int quantity) throws SQLException {
        return dbGoods.insertNewGoodToList(ds.getConnection(), loginedUser.getId(), 2, good.getID(), quantity);
    }

    public boolean removeGoodFromCart(User loginedUser, int orderId, Good good) throws SQLException {
        return dbGoods.deleteGoodFromList(ds.getConnection(), loginedUser.getId(), 2, good.getID());
    }

    public boolean updateGoodInCart(User loginedUser, Good good, int newQuantity) throws SQLException {
        return dbGoods.updateGoodInList(ds.getConnection(), loginedUser.getId(), 2, good.getID(), newQuantity);
    }

    public boolean orderFromCart(User loginedUser, Orders cart, int newOrderID, Date date) throws SQLException {
        return dbGoods.updateGoodsInList(ds.getConnection(), loginedUser.getId(), 2, newOrderID, date);
    }

    public boolean insertGoodsToList(User loginedUser, int orderId, List<Order> orderList) throws SQLException {
        return dbGoods.insertNewGoodToList(ds.getConnection(), loginedUser.getId(), 2, orderList);
    }

    public boolean insertGoodToList(User loginedUser, int orderId, Good good) throws SQLException {
        return dbGoods.insertNewGoodToList(ds.getConnection(), loginedUser.getId(), 1, good.getID(), 1);
    }

    public Status getStatus(int statusID) {
        return dbUsers.getStatus(statusID);
    }
}