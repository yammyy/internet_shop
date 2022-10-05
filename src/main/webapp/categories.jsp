<%@page import="net.yammyy.units.goods.ChoosableParameterValue"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<ul>
        <%ArrayList<ChoosableParameterValue> categoriesList_l =
            (ArrayList<ChoosableParameterValue>)request.getAttribute("data");
        for(ChoosableParameterValue category:categoriesList_l)
        { %>
            <li>
                <a href=""><div visibility: hidden><%=category.getID()%></div><%=category.getValue()%></a>
            </li>
     <% } %>

</ul>