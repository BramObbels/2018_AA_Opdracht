<%-- 
    Document   : select-seat.jsp
    Created on : Oct 29, 2018, 6:46:29 PM
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
        <title>Select Seat</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding" style="max-width:1564px">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Select the seats for the chosen play</h3>
                <form class="w3-container w3-card-4" method="post" action="<c:url value='order' />">
                        <c:forEach var = "entry" items = "${sessionScope.seats}">
                            <p><input class="w3-check" type="checkbox" name="selectedSeatIds" value="${entry.value.getId()}"><label>
                                [<c:out value="${entry.key.getRow()}" />, <c:out value="${entry.key.getColumn()}" />]: 
                                seat ID= <c:out value="${entry.value.getId()}" /></label></p>
                        </c:forEach>
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> SELECT SEAT
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
