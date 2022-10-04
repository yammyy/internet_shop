<%@page import="net.yammyy.units.goods.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="container">
    <div class="row">
        <%ArrayList<Category> std =
            (ArrayList<Category>)request.getAttribute("data");
        for(Category s:std)
        { %>
            <div class="col col-xl-2 col-lg-3 col-md-4 col-sm-6">
                <a href=""><%=s.getId()%>
                <%=s.getName()%></a>
            </div>
     <% } %>

    </div>
</div>