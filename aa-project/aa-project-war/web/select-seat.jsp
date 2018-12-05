<%-- 
    Document   : select-seat.jsp
    Created on : Oct 29, 2018, 6:46:29 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
        <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<c:url value='css/font-awesome.min.css' />">
        <link rel="stylesheet" href="<c:url value='css/w3.css' />">
        <link rel="stylesheet" href="<c:url value='css/style.css' />">
        <title>Select Seat</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding container">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Select the seats for the chosen play</h3>
                <form class="w3-container w3-card-4" method="post" action="<c:url value='order' />">
                        <c:forEach var = "entry" items = "${requestScope.seats}">
                            <p><input class="w3-check" type="checkbox" name="selectedSeatIds" value="${entry.value.getId()}"><label>
                                [<c:out value="${entry.key.getRow()}" />, <c:out value="${entry.key.getColumn()}" />]: 
                                seat ID= <c:out value="${entry.value.getId()}" /></label></p>
                        </c:forEach>
                    <input type="hidden" name="nextState" value="confirmOrder">
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> SELECT SEAT
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
