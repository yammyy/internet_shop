<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="webjars/bootstrap/4.6.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="webjars/font-awesome/6.1.2/css/all.min.css">
        <!-- End -->
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
    <!-- jQuery first, then Popper.js, then Bootstrap JS. -->
    <!-- build:js js/main.js -->
    <script src="webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="webjars/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="webjars/bootstrap/4.6.2/js/bootstrap.min.js"></script>
    <script src="js/scripts.js"></script>
    <!-- endbuild -->
    <%@include  file="login.html" %>
    <%@include  file="navbar.html" %>
    <div id="mainContent" class="container">
        <div class="row">
            <div class="col-2 main-container">
                <div id="resultCategory"></div>
                <script>
                    // Send the data using post
                    var posting = $.post( "/v3/categories" );
                    // Put the results in a div
                    posting.done(function( data ) {
                        $( "#resultCategory" ).empty().append( data );
                    });
                </script>
            </div>
            <div class="col-10">
                <div class="container" id="resultGoods"></div>
                <script>
                    // Send the data using post
                    var posting = $.post( "/v3/goods" );
                    // Put the results in a div
                    posting.done(function( data ) {
                        $( "#resultGoods" ).empty().append( data );
                    });
                </script>
            </div>
        </div>
    </div>
    </body>
</html>