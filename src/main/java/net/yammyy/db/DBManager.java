package net.yammyy.db;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.yammyy.units.goods.*;

public class DBManager
{
    public static final String SETTINGS_FILE = "config.properties";
    public static Connection conn;
    public static DBManager inst;
    private List<ChoosableParameterValue> categories;
    private List<ChoosableParameterValue> colors;
    private List<ChoosableParameterValue> brands;
    private List<Parameter> params;

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
            inst.refreshCategoriesValues();
            inst.refreshBrandsValues();
            inst.refreshColorsValues();
            inst.refreshParameters();
            System.out.println("getInstance 10");
        }
        return inst;
    }
    public List<ChoosableParameterValue> getAllCategories(){return categories;}
    public List<ChoosableParameterValue> getAllColors(){return colors;}
    public List<ChoosableParameterValue> getAllBrands(){return brands;}

    private  <T extends ChoosableParameterValue> void refreshChoosableParameterValues (Class<T> type, String _tName, String _fID, String _fValue)
    {
        System.out.println("get"+_tName+" 1");
        List<ChoosableParameterValue> params_l=null;
        if (type==Category.class){categories=new ArrayList<>();params_l=categories;}
        else if (type==Color.class){colors=new ArrayList<>();params_l=colors;}
        else if (type==Brand.class){brands=new ArrayList<>();params_l=brands;}
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
                params_l.add(param_l);
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
        params=new ArrayList<>();
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
                params.add(parameter_l);
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
                Good entity_l=new Good(rs.getInt(GoodsFields.GOOD_ID),rs.getString(GoodsFields.GOOD_NAME));
                entity_l.setDescription(rs.getString(GoodsFields.GOOD_DESCRIPTION));
                entity_l.setPrice(rs.getFloat(GoodsFields.GOOD_PRICE));
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
}
