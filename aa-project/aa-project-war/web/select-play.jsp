<%-- 
    Document   : select-play.jsp
    Created on : Oct 29, 2018, 6:45:35 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Play selection</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <h1>Select an upcoming play to continue</h1>
        <form method="post" action="<c:url value='order' />">
            <ul>
                <c:forEach var = "p" items = "${requestScope.upcomingPlays}">
                    <li><input type="radio" name="selectedPlayId" required value="${p.getId()}"> <c:out value="${p.getName()}"/> (<c:out value="${p.getDate()}"/>) <c:out value="${p.getBasicPrice()}"/>+<c:out value="${p.getRankFee()}"/>&euro;</li>
                </c:forEach>
            </ul>
            <input type="hidden" name="nextState" value="selectSeat">
            <input type="submit" name="submit" value="Select play">
        </form>
    </body>
</html>
