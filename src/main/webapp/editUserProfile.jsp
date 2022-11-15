<%@ taglib prefix="lngprefix" uri="/WEB-INF/languages.tld"%>
<%@ taglib prefix="curprefix" uri="/WEB-INF/currencies.tld"%>
<%@ taglib prefix="roleprefix" uri="/WEB-INF/roles.tld"%>
<%@page import="net.yammyy.units.users.User"%><%@page import="net.yammyy.units.users.Type"%>
<%@page import="net.yammyy.units.users.UserReason"%><%@page import="net.yammyy.units.users.Reason"%>
<%@page import="net.yammyy.units.goods.Currency"%><%@page import="net.yammyy.units.goods.Language"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%User s = (User) request.getAttribute("user");%>
<!DOCTYPE html>
<html>
   <head><meta http-equiv="content-type" content="text/html; charset=utf-8"/>
      <title>Access Denied</title>
   </head>
   <body>
<h1>Edit user</h1>
      <form method="POST" action=<%=request.getContextPath()%>/userRecord>
        <table border ="1" width="500" align="center" id="content">
            <tr bgcolor="00FF7F">
                <th><b></b></th>
                <th><b>ID</b></th>
                <th><b>login</b></th>
                <th><b>Name</b></th>
                <th><b>Password</b></th>
                <th><b>Email</b></th>
                <th><b>Role</b></th>
                <th><b>Standard language</b></th>
                <th><b>Standard currency</b></th>
                <th><b></b></th>
            </tr>
            <tr>
                <td>Old</td>
                <td><%=s.getId()%></td>
                <td><%=s.getLogin()%></td>
                <td><%=s.getFamilyAndName()%> <%=s.getFatherName()%></td>
                <td><%=s.getPassword()%></td>
                <td><%=s.getEmail()%></td>
                <td><%=s.getRole().getName()%></td>
                <td><%=s.getStandardLanguage().getAbbr()%></td>
                <td><%=s.getStandardCurrency().getAbbr()%></td>
                <td><%=s.getLastBlockingReason().getReason()%></td>
            </tr>
            <tr>
                <td>New</td>
                <td>
                    <%=s.getId()%>
                    <input type="hidden" name="userID" value=<%=s.getId()%>>
                </td>
                <td><%=s.getLogin()%></td>
                <td>
                    <input type="text" name="userFamily" value=<%=s.getFamilyName()%>>
                    <input type="text" name="userName" value=<%=s.getName()%>>
                    <input type="text" name="userFather" value=<%=s.getFatherName()%>>
                </td>
                <td><input type="text" name="userPassword" value=<%=s.getPassword()%>></td>
                <td><input type="text" name="userEmail" value=<%=s.getEmail()%>></td>
                <td><select required name="userRole">
                        <roleprefix:PrintOptionRolesTag />
                    </select>
                </td>
                <td><select required name="userLanguage">
                        <lngprefix:PrintOptionLanguagesTag />
                    </select>
                </td>
                <td><select required name="userCurrency">
                        <curprefix:PrintOptionCurrenciesTag />
                    </select>
                </td>
                <td><select required name="userBlocking">
                        <option value="0" selected>Разблокирован</option>
                        <option value="1">Заблокирован</option>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" value= "Submit" />
    </form>

<hr/>
</body>
</html>