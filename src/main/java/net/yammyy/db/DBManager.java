package net.yammyy.db;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import net.yammyy.units.goods.*;
import net.yammyy.units.users.Reason;
import net.yammyy.units.users.Type;
import net.yammyy.units.users.User;

public class DBManager
{
    public static final String SETTINGS_FILE = "config.properties";
    public static Connection conn;
    public static DBManager inst;
    private Map<Integer,ChoosableParameterValue> categories;
    private Map<Integer,ChoosableParameterValue> colors;
    private Map<Integer,ChoosableParameterValue> brands;
    private Map<Integer,Parameter> params;
    private Map<Integer, Type> roles;
    private Map<Integer, Reason> blocking_reasons;
    private Map<Integer, User> users;

    public static synchronized DBManager getInstance() throws IOException, SQLException
    {
        System.out.println("getInstance Main");
        if (inst==null)
        {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("getInstance 1");
            inst=new DBManager();
            System.out.println("getInstance 2");
            Properties prop=new Properties();
            System.out.println("getInstance 3");
            System.out.println(new File(".").getAbsolutePath());
            prop.load(Files.newInputStream(Paths.get(SETTINGS_FILE)));
            System.out.println("getInstance 4");
            String url=prop.getProperty("connection.url");
            System.out.println("getInstance 5");
            conn=DriverManager.getConnection(url);
            System.out.println("getInstance 6");
            String sql = "USE efinal;";
            try
            {
                Statement sta=conn.createStatement();
                System.out.println("getInstance 7");
                boolean rs=sta.execute(sql);
                if (rs) {System.out.println("getInstance true");}
                else {System.out.println("getInstance false");}
                System.out.println("getInstance 8");
            }
            catch (SQLException _e) {System.out.println("error "+_e.getSQLState());}
            System.out.println("getInstance 9");
            inst.refreshAll();
            System.out.println("getInstance 10");
        }
        return inst;
    }
    public Map<Integer,ChoosableParameterValue> getAllCategories(){return categories;}
    public Map<Integer,ChoosableParameterValue> getAllColors(){return colors;}
    public Map<Integer,ChoosableParameterValue> getAllBrands(){return brands;}
    public Map<Integer, Parameter> getAllParameters(){return params;}
    public Map<Integer,Parameter> getAllGoodsParams(List<Good> _goods){return null;}
    public void refreshAll()
    {
        inst.refreshCategoriesValues();
        inst.refreshBrandsValues();
        inst.refreshColorsValues();
        inst.refreshParameters();
        inst.refreshBlockingReasonsValues();
        inst.refreshUsersValues();
    }
    private  <T extends ChoosableParameterValue> void refreshChoosableParameterValues (Class<T> type, String _tName, String _fID, String _fValue)
    {
        System.out.println("get"+_tName+" 1");
        Map<Integer,ChoosableParameterValue> params_l=null;
        if (type==Category.class){categories=new TreeMap<>(); params_l=categories;}
        else if (type==Color.class){colors=new TreeMap<>();params_l=colors;}
        else if (type==Brand.class){brands=new TreeMap<>();params_l=brands;}
        System.out.println("get"+_tName+" 2");
        String sql = "SELECT * FROM "+_tName;
        System.out.println("get"+_tName+" 3. SQL: "+sql);
        System.out.println("get"+_tName+" 4");
        try (Statement sta=conn.createStatement();ResultSet rs=sta.executeQuery(sql))
        {
            while (rs.next())
            {
                ChoosableParameterValue param_l = type.getDeclaredConstructor().newInstance();   // OK
                assert param_l!=null;
                param_l.setParameter(rs.getInt(_fID), rs.getString(_fValue));
                params_l.put(rs.getInt(_fID),param_l);
            }
        }
        catch (SQLException _e) {System.out.println("get"+_tName+" error "+_e.getSQLState()+" "+_e.getErrorCode());}
        catch (InvocationTargetException _e){System.out.println("get"+_tName+" error "+_e.getMessage());}
        catch (InstantiationException _e){System.out.println("get"+_tName+" error "+_e.getLocalizedMessage());}
        catch (IllegalAccessException _e){System.out.println("get"+_tName+" error "+_e.getMessage());}
        catch (NoSuchMethodException _e){System.out.println("get"+_tName+" error "+_e.getMessage());}
        System.out.println("get"+_tName+" 5");
    }
    public void refreshCategoriesValues (){
        refreshChoosableParameterValues(Category.class, GoodsFields.CATEGORY_TABLE,
                                        GoodsFields.CATEGORY_ID,
                                        GoodsFields.CATEGORY_NAME);
                                    }
    public void refreshColorsValues (){
        refreshChoosableParameterValues(Color.class, GoodsFields.COLOR_TABLE,
                                        GoodsFields.COLOR_ID,
                                        GoodsFields.COLOR_NAME);
                                }
    public void refreshBrandsValues (){
        refreshChoosableParameterValues(Brand.class, GoodsFields.BRAND_TABLE,
                                        GoodsFields.BRAND_ID,
                                        GoodsFields.BRAND_NAME);
                                }
    public void refreshParameters()
    {
        System.out.println("refreshParameters 1");
        params=new TreeMap<>();
        String sql = "SELECT * FROM "+GoodsFields.PARAMETER_TYPE_TABLE;
        System.out.println("refreshParameters 2. SQL: "+sql);
        try (Statement sta=conn.createStatement();ResultSet rs=sta.executeQuery(sql))
        {
            while (rs.next())
            {
                System.out.println("refreshParameters 2.1 "+rs.getInt(GoodsFields.PARAMETER_TYPE_ID)+rs.getString(GoodsFields.PARAMETER_TYPE_NAME));
                Parameter parameter_l=new Parameter();
                System.out.println("refreshParameters 2.2");
                parameter_l.setParameter(rs.getInt(GoodsFields.PARAMETER_TYPE_ID), rs.getString(GoodsFields.PARAMETER_TYPE_NAME));
                System.out.println("refreshParameters 2.3");
                params.put(rs.getInt(GoodsFields.PARAMETER_TYPE_ID),parameter_l);
                System.out.println("refreshParameters 2.4");
            }
        }
        catch (SQLException _e) {System.out.println("refreshParameters error "+_e.getSQLState()+" "+_e.getErrorCode());}
        System.out.println("refreshParameters 3");
    }
    public List<Good> getGoods()
    {
        System.out.println("getGoods 1");
        String sql = "SELECT * FROM "+GoodsFields.GOODS_TABLE + " WHERE "+GoodsFields.GOOD_ALREADY_SOLD + " = ?";
        System.out.println("getGoods 2 SQL: "+sql);
        PreparedStatement sta = null;
        System.out.println("getGoods 3");
        ResultSet rs=null;
        System.out.println("getGoods 4");
        List<Good> res=new ArrayList<>();
        System.out.println("getGoods 5");
        try
        {
            System.out.println("getGoods 5.1");
            sta=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("getGoods 5.2");
            sta.setString(1, "0");
            System.out.println("getGoods 5.3");
            rs=sta.executeQuery();
            System.out.println("getGoods 5.4");
            while (rs.next())
            {
                int entity_id=rs.getInt(GoodsFields.GOOD_ID);
                Good entity_l=new Good(entity_id,rs.getString(GoodsFields.GOOD_NAME));
                entity_l.setDescription(rs.getString(GoodsFields.GOOD_DESCRIPTION));
                entity_l.setPrice(rs.getFloat(GoodsFields.GOOD_PRICE));
                //Забираем параметры по конкретному товару
                entity_l.setParams(getGoodParameters(entity_id));
                //Забираем параметры по конкретному товару
                entity_l.setCategories(getGoodCategories(entity_id));
                res.add(entity_l);
            }
        }
        catch (SQLException _e){System.out.println("error "+_e.getErrorCode());}
        finally
        {
            try {assert rs!=null;rs.close();} catch (Exception e) { /* ignored */ }
            try {sta.close();} catch (Exception e) { /* ignored */ }
        }
        System.out.println("getGoods 6");
        return res;
    }
    private List<Category> getGoodCategories (int _goodID)
    {
        System.out.println("getGood#"+_goodID+"Category 1");
        String sql="SELECT type, value FROM goods_params WHERE good_id=? and type=5";
        System.out.println("getGood#"+_goodID+"Category 2 SQL: "+sql);
        PreparedStatement sta=null;
        System.out.println("getGood#"+_goodID+"Category 3");
        ResultSet rs=null;
        System.out.println("getGood#"+_goodID+"Category 4");
        List<Category> categoryList_l=new ArrayList<>();
        System.out.println("getGood#"+_goodID+"Category 5");
        try
        {
            System.out.println("getGood#"+_goodID+"Category 5.1");
            sta=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("getGood#"+_goodID+"Category 5.2");
            sta.setString(1, String.valueOf(_goodID));
            System.out.println("getGood#"+_goodID+"Category 5.3");
            rs=sta.executeQuery();
            System.out.println("getGood#"+_goodID+"Category 5.4");
            while (rs.next())
            {
                //Забираем категории товара
                int entity_id=rs.getInt(GoodsFields.PARAMETER_VALUE);
                categoryList_l.add((Category) categories.get(entity_id));
            }
        }
        catch (SQLException _e){System.out.println("error "+_e.getErrorCode());}
        finally
        {
            try {assert rs!=null;rs.close();} catch (Exception e) { /* ignored */ }
            try {sta.close();} catch (Exception e) { /* ignored */ }
        }
        System.out.println("getGood#"+_goodID+"Category 6");
        return categoryList_l;
    }
    private Map<Integer, GoodParameter> getGoodParameters (int _goodID)
    {
        System.out.println("getGood#"+_goodID+"Params 1");
        //Выбираем все параметры кроме категории. Категорию добавляем отдельно.
        String sql = "SELECT t1.type, t2.name, t1.value," +
                            "case WHEN(t2.from_table=\"\" or t2.from_table is null) then 0 ELSE 1 END as from_table" +
                     " FROM goods_params t1 JOIN params_types t2 on t1.type=t2.id" +
                     " WHERE good_id=? and type<>5";
        System.out.println("getGood#"+_goodID+"Params 2. SQL: "+sql);
        PreparedStatement sta = null;
        System.out.println("getGood#"+_goodID+"Params 3");
        ResultSet rs=null;
        System.out.println("getGood#"+_goodID+"Params 4");
        Map<Integer,GoodParameter> res=new TreeMap<>();
        System.out.println("getGood#"+_goodID+"Params 5");
        try
        {
            System.out.println("getGood#"+_goodID+"Params 5.1");
            sta=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("getGood#"+_goodID+"Params 5.2");
            sta.setInt(1, _goodID);
            System.out.println("getGood#"+_goodID+"Params 5.3");
            rs=sta.executeQuery();
            System.out.println("getGood#"+_goodID+"Params 5.4");
            while (rs.next())
            {
                //Забираем параметры товара
                GoodParameter parameter_l=null;
                int entity_id=rs.getInt(GoodsFields.PARAMETER_TYPE);
                boolean param_type=rs.getBoolean(GoodsFields.PARAMETER_TYPE_OUTER);
                if (param_type)
                {
                    parameter_l=new GoodChoosableParameter();
                    setChoosableParameter(parameter_l,params.get(entity_id),rs.getInt(GoodsFields.PARAMETER_VALUE));
                }
                else
                {
                    parameter_l=new GoodNonChoosableParameter();
                    setNonChoosableParameter(parameter_l,params.get(entity_id),rs.getDouble(GoodsFields.PARAMETER_VALUE));
                }
                res.put(entity_id,parameter_l);
            }
        }
        catch (SQLException _e){System.out.println("error "+_e.getErrorCode());}
        finally
        {
            try {assert rs!=null;rs.close();} catch (Exception e) { /* ignored */ }
            try {sta.close();} catch (Exception e) { /* ignored */ }
        }
        System.out.println("getGood#"+_goodID+"Params 6");
        return res;
    }
    private void setNonChoosableParameter(GoodParameter _goodParameter,Parameter _parameter,double _value)
    {
        _goodParameter.setParameter(_parameter,_value);
    }
    private void setChoosableParameter(GoodParameter _goodParameter,Parameter _parameter,int _value)
    {
        switch (_value){
            case 4:
                //Это бренд
                _goodParameter.setParameter(_parameter,brands.get(_value));
            break;
            case 6:
                //Это цвет
                _goodParameter.setParameter(_parameter,colors.get(_value));
            break;
        }

    }
    private void refreshUserRolesValues()
    {
        System.out.println("get UserRoles 1");
        roles=new TreeMap<>();
        System.out.println("get UserRoles 2");
        String sql = "SELECT * FROM "+UsersFields.ROLES_TABLE;
        System.out.println("get UserRoles 3. SQL: "+sql);
        System.out.println("get UserRoles 4");
        try (Statement sta=conn.createStatement();ResultSet rs=sta.executeQuery(sql))
        {
            while (rs.next())
            {
                int id_l=rs.getInt(UsersFields.ROLES_ID);
                Type role_l = new Type(id_l,rs.getString(UsersFields.ROLES_NAME));
                roles.put(id_l,role_l);
            }
        }
        catch (SQLException _e) {System.out.println("get UserRoles  error "+_e.getSQLState()+" "+_e.getErrorCode());}
        System.out.println("get UserRoles  5 "+roles.size());
        System.out.println("get UserURLs 1");
        sql = "SELECT * FROM "+UsersFields.SECURE_URLS_TABLE;
        System.out.println("get UserURLs 3. SQL: "+sql);
        System.out.println("get UserURLs 4");
        try (Statement sta=conn.createStatement();ResultSet rs=sta.executeQuery(sql))
        {
            while (rs.next())
            {
                int role_id=rs.getInt(UsersFields.SECURE_URLS_ROLE_ID);
                Type role_l = roles.get(role_id);
                role_l.addURL(rs.getString(UsersFields.SECURE_URLS_URL));
                System.out.println("get UserURLs 4.1 "+rs.getString(UsersFields.SECURE_URLS_URL));
            }
        }
        catch (SQLException _e) {System.out.println("get UserRoles  error "+_e.getSQLState()+" "+_e.getErrorCode());}
        System.out.println("get UserURLs 5 "+roles.size());
    }
    private void refreshBlockingReasonsValues()
    {
        System.out.println("get BlockingReasons 1");
        blocking_reasons=new TreeMap<>();
        System.out.println("get BlockingReasons 2");
        String sql = "SELECT * FROM "+UsersFields.BLOCKED_TABLE+" WHERE ID<>0";
        System.out.println("get BlockingReasons 3. SQL: "+sql);
        System.out.println("get BlockingReasons 4");
        try (Statement sta=conn.createStatement();ResultSet rs=sta.executeQuery(sql))
        {
            System.out.println("get BlockingReasons 4.1");
            while (rs.next())
            {
                System.out.println("get BlockingReasons 4.2");
                int id_l=rs.getInt(UsersFields.BLOCKED_REASON_ID);
                System.out.println("get BlockingReasons 4.3");
                Reason blocking_reason_l = new Reason(id_l, rs.getString(UsersFields.BLOCKED_REASON_NAME));
                System.out.println("get BlockingReasons 4.4");
                blocking_reasons.put(id_l,blocking_reason_l);
                System.out.println("get BlockingReasons 4.5");
            }
        }
        catch (SQLException _e) {System.out.println("get BlockingReasons  error "+_e.getSQLState()+" "+_e.getErrorCode());}
        System.out.println("get BlockingReasons  5");
    }
    public void refreshUsersValues()
    {
        refreshUserRolesValues();
        refreshBlockingReasonsValues();
        System.out.println("get Users 1");
        users=new TreeMap<>();
        System.out.println("get Users 2");
        String sql = "SELECT * FROM "+UsersFields.USERS_TABLE;
        System.out.println("get Users 3. SQL: "+sql);
        System.out.println("get Users 4");
        try (Statement sta=conn.createStatement();ResultSet rs=sta.executeQuery(sql))
        {
            System.out.println("get Users 4.1");
            while (rs.next())
            {
                System.out.println("get Users 4.2");
                int id_l=rs.getInt(UsersFields.USERS_ID);
                System.out.println("get Users 4.3");
                User user_l=new User(id_l,rs.getString(UsersFields.USERS_LOGIN),rs.getString(UsersFields.USERS_PASSWORD));
                System.out.println("get Users 4.4");
                Type role=roles.get(rs.getInt(UsersFields.USERS_ROLE));
                System.out.println("get Users 4.5");
                user_l.setRole(role);
                System.out.println("get Users 4.6");
                users.put(id_l,user_l);
                System.out.println("get Users 4.7");
            }
        }
        catch (SQLException _e) {System.out.println("get Users  error "+_e.getSQLState()+" "+_e.getErrorCode());}
        System.out.println("get Users  5");
    }
    // Find a User by userName and password.
    public User findUser(String _login, String _password)
    {
        User user_l=new User(0,_login,_password);
        boolean is_user=users.containsValue(user_l);
        int user_id=users.entrySet().stream().filter(entry -> user_l.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().get();
        if (is_user && users.get(user_id).getPassword().equals(_password)){return users.get(user_id);}
        return null;
    }
    public Map<Integer,Type> getRoles(){System.out.println(roles.size());return roles;}
    public Map<Integer, User> getUsers(){return users;}
}
