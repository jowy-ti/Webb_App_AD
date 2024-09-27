<%-- 
    Document   : menu
    Created on : 21 sept 2024, 17:24:37
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@page session="true"%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
        HttpSession sesion=request.getSession(false);
        if (sesion == null) response.sendRedirect("http://localhost:8080/practica2/error_out.jsp");
        %>
        <center>
            <h1>MENU</h1>
        </center>
        <a href="http://localhost:8080/practica2/registrarImagen.jsp">Registrar imagen</a>
        
        <a href="http://localhost:8080/practica2/buscarImagen.jsp">Buscar imagen</a>
    </body>
</html>
