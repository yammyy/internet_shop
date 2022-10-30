<%@page import="net.yammyy.units.users.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%User user = (User)request.getAttribute("logined_user");%>
<div class="container">
    <div class="row">
        <div class="col-12">
            <img src="<%=user.getPhoto()%>">
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-4">
           <%=user.getName()%>
        </div>
        <div class="col-4">
            <%=user.getFamilyName()%>
        </div>
        <div class="col-4">
            <%=user.getFatherName()%>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <%=user.getLogin()%>
        </div>
        <div class="col-6">
            <%=user.getPassword()%>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-8">
            <%=user.getRole().getName()%>
        </div>
        <div class="col-4">
            <%=user.getLastBlockingReason().getReason()%>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <%=user.getRegistrationDate()%>
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <%=user.getStandardLanguage().getName()%>
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <%=user.getStandardCurrency().getName()%>
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <a href="">Редактировать</a>
        </div>
        <div class="col-6">
            Удалить профиль
        </div>
    </div>
</div>