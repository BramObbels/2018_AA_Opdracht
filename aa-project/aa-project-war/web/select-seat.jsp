<%-- 
    Document   : select-seat.jsp
    Created on : Oct 29, 2018, 6:46:29 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seat selection</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <h1>Select the seats for the chosen play</h1>
        <form method="post" action="<c:url value='order' />">
            <input type="hidden" name="nextState" value="confirmOrder">
            <input type="submit" name="submit" value="Select seats">
        </form>
    </body>
</html>
