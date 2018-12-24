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
                    <p>Available seats:</p>
                    <table>
                        <c:forEach var = "seatsRow" items = "${sessionScope.seats}">
                            <tr>
                                <c:forEach var = "seat" items = "${seatsRow}">
                                    <td class="seat">
                                        <input type="hidden" name="seatStatus" value="${seat.getStatus()}">
                                        <input type="hidden" name="seatId" value="${seat.getId()}">
                                        <select name="seatAction">
                                            <option selected value="nothing">Nothing</option>
                                            
                                            <!-- Management special options -->
                                            <c:if test="${sessionScope.isManagement eq true}">
                                                <option value="free">Free seat</option>
                                                <option value="reserve">Reserve seat</option>
                                            </c:if>
                                                
                                            <!-- Occupation only possible if seat is free, except for management -->
                                            <c:if test="${(sessionScope.isManagement eq true) or (seat.getStatus() == 0)}">
                                                <option value="occupy">Occupy seat</option>
                                            </c:if>
                                        </select> 
                                        <label>
                                            [<c:out value="${seat.getRowNumber()}" />, 
                                            <c:out value="${seat.getColumnNumber()}" />] 
                                            <c:out value="${sessionScope.basicPrice + seat.getRank() * sessionScope.rankFee}" /> &euro;
                                        </label>
                                    </td>   
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                    <input type="hidden" name="nextOrderState" value="confirmOrder" />
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> SELECT SEAT
                    </button>
                </form>
            </div>
        </div>
        
        <!-- JS dynamic selection -->
        <script>
            var seats = document.getElementsByClassName("seat");
            console.log("Seats: " + JSON.stringify(seats));
            for (var i = 0; i < seats.length; ++i) {
                var item = seats[i];  
                var status = item.children[0].value; // hidden input field with status of the seat
                console.log(status);
                if(status == 0) {
                    item.className += " w3-green";
                }
                else if(status == 1) {
                    item.className += " w3-orange";
                }
                else if(status == 2) {
                    item.className += " w3-red";
                }
            }
        </script>
    </body>
</html>
