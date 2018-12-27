<%-- 
    Document   : create
    Created on : 22-dec-2018, 15:26:41
    Author     : Bram
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>Create Account</title>
    </head>
    <body>
        <!-- Menu -->
        <%@include file="WEB-INF/jspf/menu.jspf" %>
        <div class="w3-content w3-padding" style="max-width:1564px">

            <!-- Project Section -->
            <div class="w3-container w3-padding-32">
                <h3 class="w3-border-bottom w3-border-light-grey w3-padding-16">CREATE NEW ACCOUNT</h3>
                <c:out value="${sessionScope.recall}" />
                <form method="post" action="<c:url value='create' />">
                    <input class="w3-input w3-section w3-border" type="text" name="username" placeholder="USERNAME"><label>
                    <input class="w3-input w3-section w3-border" type="password" name="password" placeholder="WACHTWOORD">
                    <input type="hidden" name="nextState" value="confirm">
                    <button class="w3-button w3-black w3-section" type="submit">
                        <i class="fa fa-paper-plane"></i> CREATE
                    </button>
                </form>
            </div>
        </div>
    </body>
</html>
