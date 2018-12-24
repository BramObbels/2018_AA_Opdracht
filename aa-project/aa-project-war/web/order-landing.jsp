<%-- 
    Document   : order-landing.jsp
    Created on : Oct 29, 2018, 6:45:35 PM
    Author     : Dylan Van Assche
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- date formatting -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="<c:url value='css/w3.css' />">
        <link rel="stylesheet" href="<c:url value='css/style.css' />">
        <title>Play Selection</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding container">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">Welcome to the ticket order process</h3>
                <p>If you want to connect your tickets to your account, please click the button below:</p>
                <form class="w3-container" method="post" action="<c:url value='order-account' />">
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> CONNECT ACCOUNT TO TICKETS
                    </button>
                </form>
                <p>If you want to continue without an account, click the button below:</p>
                <form class="w3-container" method="post" action="<c:url value='order' />">
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> CONTINUE WITHOUT AN ACCOUNT
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
