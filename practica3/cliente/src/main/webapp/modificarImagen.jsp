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
            <%--
            <img src="uploads/${filename}" alt="${filename}" style="width:500px;height:300px;">
            --%>
            <br/>
            <p>Título: ${sessionScope.title}</p>
            <label for="title">Título Nuevo:</label>
            <input type="text" id="title" name="title" />
            <br/>
            <p>Descripción: ${sessionScope.description}</p> 
            <label for="description">Descripción Nueva:</label>
            <input type="text" id="description" name="description" />
            <br/>
            <p>Palabras Clave: ${sessionScope.keywords}</p>
            <label for="keywords">Palabras Clave Nuevas:</label>
            <input type="text" id="keywords" name="keywords" />
            <br/>
            <p>Autor: ${sessionScope.author}</p>
            <label for="author">Autor Nuevo:</label>
            <input type="text" id="author" name="author" />
            <br/>
            <p>Fecha de Captura: ${sessionScope.date}</p>
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
            
        <% 
            // Eliminar siempre los atributos de la sesión después de mostrarlos
            session.removeAttribute("title");
            session.removeAttribute("description");
            session.removeAttribute("author");
            session.removeAttribute("keywords");
            session.removeAttribute("date");
            session.removeAttribute("creator");
        %>
    </body>
</html>
