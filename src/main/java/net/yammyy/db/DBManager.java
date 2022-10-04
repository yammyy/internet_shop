package net.yammyy.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.yammyy.units.*;
import net.yammyy.units.goods.*;

public class DBManager
{
    public static final String SETTINGS_FILE = "config.properties";
    public static Connection conn;
    public static DBManager inst;
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
            prop.load(new FileInputStream(SETTINGS_FILE));
            System.out.println("getInstance 4");
            String url=prop.getProperty("connection.url");
            System.out.println("getInstance 5");
            inst.conn=DriverManager.getConnection(url);
            System.out.println("getInstance 6");
            String sql = "USE efinal;";
            try
            {
                Statement sta=inst.conn.createStatement();
                System.out.println("getInstance 7");
                boolean rs=sta.execute(sql);
                if (rs) {System.out.println("getInstance true");}
                else {System.out.println("getInstance false");}
                System.out.println("getInstance 8");
            }
            catch (SQLException _e) {System.out.println("error "+_e.getSQLState());}
            System.out.println("getInstance 9");
        }
        return inst;
    }
    public List<Category> getAllCategories()
    {
        System.out.println("getAllCategories 1");
        String sql = "SELECT * FROM "+GoodsFields.CATEGORY_TABLE+" WHERE "+GoodsFields.PARENT_CATEGORY+" = ? ORDER BY "+GoodsFields.CATEGORY_NAME;
        System.out.println("getAllCategories 2 SQL: "+sql);
        PreparedStatement sta = null;
        System.out.println("getAllCategories 3");
        ResultSet rs=null;
        System.out.println("getAllCategories 4");
        List<Category> res=new ArrayList<>();
        System.out.println("getAllCategories 5");
        try
        {
            sta=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sta.setString(1, "0");
            rs=sta.executeQuery();
            while (rs.next())
            {
                Category entity_l=new Category(rs.getInt(GoodsFields.CATEGORY_ID), rs.getString(GoodsFields.CATEGORY_NAME));
                res.add(entity_l);
            }
        }
        catch (SQLException _e){
            System.out.println("error "+_e.getErrorCode());}
        finally
        {
            try {rs.close();} catch (Exception e) { /* ignored */ }
            try {sta.close();} catch (Exception e) { /* ignored */ }
        }
        System.out.println("getAllCategories 6");
        return res;
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
            try {rs.close();} catch (Exception e) { /* ignored */ }
            try {sta.close();} catch (Exception e) { /* ignored */ }
        }
        System.out.println("getGoods 6");
        return res;
    }
}
