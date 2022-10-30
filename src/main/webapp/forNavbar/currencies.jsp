<%@page import="net.yammyy.units.goods.Currency"%>
<%@page import="java.util.ArrayList"%>
<div class="dropdown">
    <a href="#">UAH</a>
    <ul>
        <div class="dropdown-content">
            <%ArrayList<Currency> cr_l =
                    (ArrayList<Currency>)request.getAttribute("currencies");
                for(Currency cr:cr_l)
                { %>
                    <li><a href="#"><%=cr.getAbbreviation()%></a></li>
             <% } %>
         </div>
    </ul>
</div>