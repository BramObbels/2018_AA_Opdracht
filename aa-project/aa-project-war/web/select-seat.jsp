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
            <ul>
                <c:forEach var = "entry" items = "${sessionScope.seats}">
                    <li><input type="checkbox" name="selectedSeatIds" value="${entry.value.getId()}">
                        [<c:out value="${entry.key.getRow()}" />, <c:out value="${entry.key.getColumn()}" />]: 
                        seat ID= <c:out value="${entry.value.getId()}" /> STATE=<c:out value="${entry.value.getStatus()}" />
                    </li>
                </c:forEach>
            </ul>
            <input type="submit" name="submit" value="Select seats">
        </form>
    </body>
</html>
