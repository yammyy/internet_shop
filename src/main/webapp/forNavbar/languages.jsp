<%@page import="net.yammyy.units.goods.Language"%>
<%@page import="java.util.ArrayList"%>
<div class="dropdown">
    <a href="#">RU</a>
    <ul>
        <div class="dropdown-content">
            <%ArrayList<Language> lng_l =
                    (ArrayList<Language>)request.getAttribute("languages");
                for(Language lng:lng_l)
                { %>
                    <li><a href="#"><%=lng.getAbbr()%></a></li>
             <% } %>
         </div>
    </ul>
</div>