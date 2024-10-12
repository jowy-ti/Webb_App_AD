<%-- 
    Document   : registrarImagen
    Created on : 27 sept 2024, 10:14:27
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
        <h1>Registrar Imagen</h1>
        <form action = "registrarImagen" method = "POST" enctype="multipart/form-data">
            
            <label for="title">Título:</label>
            <input type="text" id="title" name="title" />
            <br/>
            <label for="description">Descripción:</label>
            <input type="text" id="description" name="description" />
            <br/>
            <label for="keywords">Palabras Clave:</label>
            <input type="text" id="keywords" name="keywords" />
            <br/>
            <label for="author">Autor:</label>
            <input type="text" id="author" name="author" />
            <br/>
            <label for="capture_date">Fecha de Captura:</label>
            <input type="text" id="capture_date" name="capture_date" />
            <br/>
            Seleccione la imagen:
            <input type="file" name="file">
            <br/>
            <button type="submit">Registrar</button>
        </form>
    </body>
</html>
