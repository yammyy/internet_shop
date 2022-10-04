<%@page import="net.yammyy.units.goods.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<ul>
        <%ArrayList<Category> std =
            (ArrayList<Category>)request.getAttribute("data");
        for(Category s:std)
        { %>
            <li>
                <a href=""><%=s.getId()%>
                <%=s.getName()%></a>
            </li>
     <% } %>

</ul>