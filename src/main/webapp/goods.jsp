<%@page import="net.yammyy.units.Good"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <div id="loginModal" class="modal fade" role="dialog">
        <div class="modal-dialog modal-lg" role="content">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Login</h4>
                    <button type="button" class="close" id="loginModalClose">&times;</button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-row">
                            <div class="form-group col-sm-4"><label class="sr-only" for="exampleInputEmail3">Email address</label>
                                    <input type="email" class="form-control form-control-sm mr-1" id="exampleInputEmail3" placeholder="Enter email">
                            </div>
                            <div class="form-group col-sm-4">
                                <label class="sr-only" for="exampleInputPassword3">Password</label>
                                <input type="password" class="form-control form-control-sm mr-1" id="exampleInputPassword3" placeholder="Password">
                            </div>
                            <div class="col-sm-auto">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox">
                                    <label class="form-check-label"> Remember me
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <button type="reset" class="btn btn-secondary btn-sm ml-auto" id="loginModalCancel">Cancel</button>
                            <button type="submit" class="btn btn-primary btn-sm ml-1">Sign in</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

<div class="container" id="content">
    <div class="row">
        <%ArrayList<Good> std =
            (ArrayList<Good>)request.getAttribute("data");
        for(Good s:std)
        { %>
            <div class="col col-xl-2 col-lg-3 col-md-4 col-sm-6">
                <a href=""><%=s.getId()%>
                <%=s.getName()%></a>
            </div>
     <% } %>

    </div>
</div>