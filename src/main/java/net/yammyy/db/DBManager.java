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
        System.out.println("getInstance");
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
        String tName, fName, fID, fFilter;
        tName="category";
        fID="id";
        fName="value";
        fFilter="undercategory";
        String sql = "SELECT * FROM "+tName+" WHERE "+fFilter+" = ?";
        System.out.println("getAllCategories 2");
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
                Category entity_l=new Category(rs.getInt(fID), rs.getString(fName));
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
}
