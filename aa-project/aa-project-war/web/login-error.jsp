<%-- 
    Document   : login-error.jsp
    Created on : Nov 11, 2018, 4:11:20 PM
    Author     : dylan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>Login-Error</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding" style="max-width:1564px">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Login</h3>
                <p>Make sure your login credentials are correct!</p>
                <!-- Login form -->
                <%@include file="WEB-INF/jspf/login-form.jspf" %>
            </div>
        </div>
    </body>
</html>
