<%@page import="net.yammyy.units.goods.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<ul>
        <%ArrayList<Category> categoriesList_l =
            (ArrayList<Category>)request.getAttribute("data");
        for(Category category:categoriesList_l)
        { %>
            <li>
                <a href=""><div visibility: hidden><%=category.getId()%></div><%=category.getName()%></a>
            </li>
     <% } %>

</ul>