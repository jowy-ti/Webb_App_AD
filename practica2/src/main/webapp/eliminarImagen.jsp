<%-- 
    Document   : eliminarImagen
    Created on : 10 oct 2024, 16:49:05
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession"%>

<% 
    HttpSession sesion = request.getSession(false);
    if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Eliminar Imagen</h1>
        <form action = "eliminarImagen" method = "POST">
            <img src="uploads/${imagen}" alt="${imagen}" style="width:300px;height:150px;">
            <br/>
            Esta seguro que esta es la imagen que quiere eliminar?
            <br/>
            <button type="submit">Eliminar</button>
        </form>
    </body>
</html>
