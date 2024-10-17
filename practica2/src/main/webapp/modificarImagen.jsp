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
        <h1>Modificar Imagen</h1>
        <c:set var="filename" value="${request.getParameter("filename")}" />
        <form action = "modificarImagen" method = "POST" enctype="multipart/form-data">
            <img src="uploads/${filename}" alt="${filename}" style="width:500px;height:300px;">
            <br/>
            <label for="title">Título Nuevo:</label>
            <input type="text" id="title" name="title" />
            <br/>
            <label for="description">Descripción Nueva:</label>
            <input type="text" id="description" name="description" />
            <br/>
            <label for="keywords">Palabras Clave Nuevas:</label>
            <input type="text" id="keywords" name="keywords" />
            <br/>
            <label for="author">Autor Nuevo:</label>
            <input type="text" id="author" name="author" />
            <br/>
            <label for="capture_date">Fecha de Captura Nueva:</label>
            <input type="text" id="capture_date" name="capture_date" />
            <br/>
            Seleccione la nueva imagen:
            <input type="file" name="file">
            <br/>
            <button type="submit">Modificar</button>
            <br/>
            <br/>
            <a href="buscarImagen.jsp">Return</a>
        </form>
    </body>
</html>
