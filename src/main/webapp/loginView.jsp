<%@page import="net.yammyy.units.users.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Login</title>
   </head>
   <body>
      <h3>Login Page</h3>
      <p style="color: red;"><%=request.getAttribute("errorMessage")%></p>

      <form method="POST" action=<%=request.getContextPath()%>/loginView>
         <input type="hidden" name="redirectId" value=<%=request.getParameter("redirectId")%> />
         <table border="0">
            <tr>
               <td>User Name</td>
               <td><input type="text" name="userName" value= <%((User)request.getAttribute("user")).getLogin();%> > </td>
            </tr>
            <tr>
               <td>Password</td>
               <td><input type="password" name="password" value="" /> </td>
            </tr>

            <tr>
               <td colspan ="2">
                  <input type="submit" value= "Submit" />
                  <a href=<%=request.getContextPath()%>>Cancel</a>
               </td>
            </tr>
         </table>
      </form>

      <p style="color:blue;">Login with:</p>

      employee1/123 <br>
      manager1/123



   </body>
</html>