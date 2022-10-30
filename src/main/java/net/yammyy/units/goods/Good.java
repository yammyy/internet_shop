package net.yammyy.units.goods;

import java.util.*;

public class Good {
    int id;
    String name;
    String description;
    double price;
    Date dt;
    List<Category> categories;
    Map<Integer, GoodParameter> parameters;

    public Good(int goodID, String goodName) {
        id = goodID;
        name = goodName;
        categories = new ArrayList<>();
        parameters = new TreeMap<>();
    }

    public int getID() {
        return id;
    }

    public String getName() {return name;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String goodDescription) {
        description = goodDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double goodPrice) {
        price = goodPrice;
    }

    public String getParameter(int id) {return parameters.get(id).getValue();}

    public void setParams(Map<Integer, GoodParameter> goodParameterMap) {parameters = goodParameterMap;}

    public void setParam(GoodParameter goodParameter) {parameters.put(goodParameter.getID(), goodParameter);}

    public List<String> getCategories() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            result.add(categories.get(i).getValue());
        }
        return result;
    }

    public void setCategories(List<Category> goodCategories) {categories = goodCategories;}

    public void addCategory(ChoosableParameterValue goodCategory) {categories.add((Category) goodCategory);}

    public boolean hasCategory(int categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getID() == categoryId) {
                return true;
            }
        }
        return false;
    }

    public void setDateWhenAdded(Date dateWhenAdded) {dt = dateWhenAdded;}
}
