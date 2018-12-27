<%-- 
    Document   : confirm-oder.jsp
    Created on : Oct 29, 2018, 6:47:03 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="<c:url value='css/w3.css' />">
        <link rel="stylesheet" href="<c:url value='css/style.css' />">
        <title>Confirm</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding container">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Confirm your order below</h3>
                <form class="w3-container w3-card-4" method="post" action="<c:url value='order' />">
                    Play: <c:out value="${sessionScope.orderedPlay.getName()}" />

                    <c:if test="${fn:length(sessionScope.freeSeats) > 0}">
                        <h4>Free seats:</h4>
                    </c:if>
                    <c:forEach var = "entry" items = "${sessionScope.freeSeats}">
                        <p>seat ID= <c:out value="${entry.getId()}" /></p>
                    </c:forEach>
                        
                    <c:if test="${fn:length(sessionScope.reserveSeats) > 0}">
                        <h4>Reserve seats:</h4>
                    </c:if>
                    <c:forEach var = "entry" items = "${sessionScope.reserveSeats}">
                        <p>seat ID= <c:out value="${entry.getId()}" /></p>
                    </c:forEach>
                    
                    <c:if test="${fn:length(sessionScope.occupySeats) > 0}">
                        <h4>Occupy seats:</h4>
                    </c:if>
                    <c:forEach var = "entry" items = "${sessionScope.occupySeats}">
                        <p>seat ID= <c:out value="${entry.getId()}" /></p>
                    </c:forEach>
                       
                    <c:if test="${fn:length(sessionScope.occupySeats) > 0}">
                        <p>Total price: <c:out value="${sessionScope.totalPriceOccupied}" /> &euro;</p>
                    </c:if>
                      
                    <label>Name:</label>
                    <input type="text" name="buyer" />
                        
                    <input type="hidden" name="nextOrderState" value="generateTickets" />
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> CONFIRM ORDER
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
