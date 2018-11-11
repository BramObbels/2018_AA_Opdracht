<%-- 
    Document   : landing.jsp
    Created on : Oct 29, 2018, 6:44:36 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <h1>Welcome to TicketMaster!</h1>
        <p>In this application you can order your tickets for our upcoming plays. Click on 'order' to proceed.</p>
        <p>If you would like to check your previous orders, you can enter the 'members area' using your username and password.</p>
        <p>Managing the application can be done in the 'management area'.</p>
    </body>
</html>
