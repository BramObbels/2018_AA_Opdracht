<%-- 
    Document   : landing.jsp
    Created on : Oct 29, 2018, 6:44:36 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>Home</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        
        <!-- Page content -->
        <div class="w3-content w3-padding" style="max-width:1564px">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Welcome to D&amp;B Concerts!</h3>
                <p>In this application you can order your tickets for our upcoming plays. Click on 'order' to proceed.</p>
                <p>If you would like to check your previous orders, you can enter the 'members area' using your username and password.</p>
                <p>Managing the application can be done in the 'management area'.</p>
            </div>
        </div>
    </body>
</html>
