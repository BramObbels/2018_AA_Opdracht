<%-- 
    Document   : tickets.jsp
    Created on : Nov 12, 2018, 9:25:03 AM
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
        <title>Tickets</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding container">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Tickets</h3>
                <p>
                    If you have an account or want to create one, you can do that below. 
                    Without an account you won't be able to access your ticket codes every again. 
                    If you don't want to create an account, print this page and keep it in a safe place.
                </p>
                <button class="w3-button w3-black w3-section printButton" onClick="window.print()"> 
                        <i class="fa fa-print"></i> PRINT
                </button>
                <form class="w3-container w3-card-4" method="post" action="<c:url value='/' />">
                    <ul>
                        <c:forEach var = "t" items = "${sessionScope.generatedTickets}">
                            <li>${t.getId()}</li>
                        </c:forEach>
                    </ul>
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> FINISH
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>