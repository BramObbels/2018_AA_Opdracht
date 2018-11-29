<%-- 
    Document   : confirm-oder.jsp
    Created on : Oct 29, 2018, 6:47:03 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order confirmation</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <h1>Confirm your order below</h1>
        <form method="post" action="<c:url value='order' />">
            Play: <c:out value="${sessionScope.orderedPlay.getName()}" />
            <ul>
                <c:forEach var = "entry" items = "${sessionScope.orderedSeats}">
                    <li>seat ID= <c:out value="${entry.getId()}" /></li>
                </c:forEach>
            </ul>
            <input type="submit" name="submit" value="Confirm order">
        </form>
    </body>
</html>
