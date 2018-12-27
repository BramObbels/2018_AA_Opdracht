<%-- 
    Document   : account.jsp
    Created on : Oct 29, 2018, 6:43:24 PM
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
        <title>Account</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding container">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Account manager</h3>
            </div>
            Welcome <c:out value="${sessionScope.username}" />! You can find your tickets below:
      
            <table class="w3-table">
                <tr>
                    <th>TICKET NUMBER</th>
                    <th>PLAY</th>
                    <th>SEAT ROW</th>
                    <th>SEAT COLUMN</th>
                </tr>


                <c:forEach var = "c" items = "${sessionScope.Collections}">
                <tr>
                    <td>${c.getTicket().getId()}</td>
                    <td>${c.getPlay().getName()}</td>
                    <td>${c.getSeat().getRowNumber()}</td>
                    <td>${c.getSeat().getColumnNumber()}</td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
