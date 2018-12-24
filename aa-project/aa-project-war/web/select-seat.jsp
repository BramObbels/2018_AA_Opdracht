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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
                    <table>
                        <c:forEach var = "seatsRow" items = "${sessionScope.seats}">
                            <tr>
                                <c:forEach var = "seat" items = "${seatsRow}">
                                    <td>
                                        <input type="hidden" name="seatId" value="${seat.getId()}">
                                        <select name="seatAction">
                                            <option value="nothing">Nothing</option>
                                            <option value="free">Free seat</option>
                                            <option value="reserve">Reserve seat</option>
                                            <option value="occupy">Occupy seat</option>
                                        </select> 
                                        <label>
                                            [<c:out value="${seat.getRowNumber()}" />, 
                                            <c:out value="${seat.getColumnNumber()}" />] 
                                            rank= <c:out value="${seat.getRank()}" />
                                        </label>
                                    </td>   
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> SELECT SEAT
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
