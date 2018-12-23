<%-- 
    Document   : confirm-oder.jsp
    Created on : Oct 29, 2018, 6:47:03 PM
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
        <title>Confirm</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding container">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Confirm your order below</h3>
                <div class="w3-card-4">
                    <form class="w3-container" method="post" action="<c:url value='order' />">
                        Play: <c:out value="${sessionScope.orderedPlay.getName()}" />

                        <c:forEach var = "entry" items = "${sessionScope.orderedSeats}">
                            <p>seat ID= <c:out value="${entry.getId()}" /></p>
                        </c:forEach>
                        <button class="w3-button w3-black w3-section" type="submit">
                            <i class="fa fa-paper-plane"></i> CONFIRM ORDER
                        </button>
                    </form>
                    
                    <p>If you want to connect your tickets to your account, please click the button below:</p>
                    <form class="w3-container" method="post" action="<c:url value='order-account' />">
                        <button class="w3-button w3-black w3-section" type="submit">
                            <i class="fa fa-paper-plane"></i> LOGIN &amp; CONFIRM ORDER
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
