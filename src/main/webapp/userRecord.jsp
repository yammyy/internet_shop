<%@page import="net.yammyy.units.users.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
      <h1>Displaying Student List</h1>
      <table border ="1" width="500" align="center" id="content">
         <tr bgcolor="00FF7F">
          <th><b>Student Name</b></th>
          <th><b>Student Age</b></th>
          <th><b>Course Undertaken</b></th>
         </tr>
        <%-- Fetching the attributes of the request object
             which was previously set by the servlet
              "UserServlet.java"
        --%>
        <%ArrayList<User> std =
            (ArrayList<User>)request.getAttribute("data");
        for(User s:std){%>
        <%-- Arranging data in tabular form
        --%>
            <tr>
                <td><%=s.getId()%></td>
                <td><%=s.getLogin()%></td>
                <td><%=s.getLogin()%></td>
            </tr>
            <%}%>
        </table>
        <hr/>