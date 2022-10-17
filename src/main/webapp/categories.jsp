<%@page import="net.yammyy.units.goods.ChoosableParameterValue"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="css/categories.css">
<ul>
        <%Map<Integer,ChoosableParameterValue> allCategories_l =
            (Map<Integer,ChoosableParameterValue>)request.getAttribute("data");

        for (Map.Entry<Integer, ChoosableParameterValue> category : allCategories_l.entrySet())
        { %>
            <li>
                <a href=""><div visibility: hidden><%=category.getValue().getID()%></div><%=category.getValue().getValue()%></a>
            </li>
     <% } %>

</ul>