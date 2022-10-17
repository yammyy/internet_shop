<%@page import="net.yammyy.units.goods.Good"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <div class="row">
        <div class="col-1"></div>
        <div class="col-5">

        </select>
        </div>
        <div class="col-6"></div>
    </div>
    <div class="row">
        <%ArrayList<Good> goods_l =
            (ArrayList<Good>)request.getAttribute("data");
        for(Good good:goods_l)
        { %>
            <div class="col col-xl-2 col-lg-3 col-md-4 col-sm-6">
                <a href=""><%=good.getId()%>
                <%=good.getName()%></a>
                <%=good.getDescription()%>
                <%=good.getPrice()%>
            </div>
     <% } %>

    </div>