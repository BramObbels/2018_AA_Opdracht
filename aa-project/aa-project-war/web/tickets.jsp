<%-- 
    Document   : tickets.jsp
    Created on : Nov 12, 2018, 9:25:03 AM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tickets</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <h1>Tickets</h1>
        <p>
            If you have an account or want to create one, you can do that below. 
            Without an account you won't be able to access your ticket codes every again. 
            If you don't want to create an account, print this page and keep it in a save place.
        </p>
        <form method="post" action="<c:url value='/' />">
            <ul>
                <c:forEach var = "t" items = "${sessionScope.generatedTickets}">
                    <li>${t.getId()}</li>
                </c:forEach>
            </ul>
            <input type="hidden" name="nextState" value="orderFinished">
            <input type="submit" name="submit" value="Finish">
        </form>
    </body>
</html>
