<%-- 
    Document   : menu
    Created on : 21 sept 2024, 17:24:37
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<<<<<<< HEAD
<%@page import="jakarta.servlet.http.HttpSession"%>

<% 
    HttpSession sesion = request.getSession(false);
    if (sesion.getAttribute("user") == null) response.sendRedirect("/practica2/error_out.jsp");
=======
<%@page import="jakarta.servlet.http.HttpSession" %>
<% 
    HttpSession sesion=request.getSession(false);
    if (sesion == null) response.sendRedirect("practica2/error_out.jsp");
>>>>>>> c751829 (si)
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <center>
            <h1>MENU</h1>
<<<<<<< HEAD
      
=======
       
>>>>>>> c751829 (si)
            <a href="/practica2/registrarImagen.jsp">Registrar imagen</a>
            <br/>
            <a href="/practica2/buscarImagen.jsp">Buscar imagen</a>
        </center>
    </body>
</html>
