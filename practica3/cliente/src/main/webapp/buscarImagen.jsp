<%-- 
    Document   : buscarImagen
    Created on : 4 oct 2024, 10:23:22
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="jakarta.servlet.http.HttpServletRequest;" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
        <h1>Busqueda</h1>
            <c:if test="${not empty param.election}">
                <c:set var="election" value="${param.election}" scope="session" />
            </c:if>
            <form action="BuscarImagen" method="GET">
                <ul>
                    <c:if test="${sessionScope.election == 'id'}">
                    <label for="id">Id:</label>
                    <input type="text" id="id" name="id" />
                    <br/>
                    </c:if>
                    <c:if test="${sessionScope.election == 'title' or sessionScope.election == 'title_author'}">
                    <label for="title">Título:</label>
                    <input type="text" id="title" name="title" />
                    <br/>
                    </c:if>
                    <c:if test="${sessionScope.election == 'author' or sessionScope.election == 'title_author'}">
                    <label for="author">Autor:</label>
                    <input type="text" id="author" name="author" />
                    <br/>
                    </c:if>
                    <c:if test="${sessionScope.election == 'keywords'}">
                    <label for="keywords">Palabras clave:</label>
                    <input type="text" id="keywords" name="keywords" />
                    <br/>
                    </c:if>
                    <c:if test="${sessionScope.election == 'date'}">
                    <label for="date">Fecha:</label>
                    <input type="text" id="date" name="date" />
                    <br/>
                    </c:if>
                    <button type="submit">Search</button>

                    <br>

                </ul>
                <br/>
                <br/>
                <a href="eleccionBusqueda.jsp">Return</a>
            </form>
            
            <c:if test="${not empty images}">
                
                <c:forEach var="imagen" items="${images}">
                    <p> Id: ${imagen.id} </p>
                    <p> Título: ${imagen.title} </p>
                    <p> Autor: ${imagen.author} </p>
                    <p> Palabras clave: ${imagen.keywords} </p>
                    <p> Fecha de creación: ${imagen.date} </p>
                    <p> Creador: ${imagen.creator} </p>

                    <c:if test="${sessionScope.user == imagen.creator}">
                        <c:set var="id" value="${imagen.id}" scope="session" />
                        <c:set var="title" value="${imagen.title}" scope="session" />
                        <c:set var="description" value="${imagen.description}" scope="session" />
                        <c:set var="keywords" value="${imagen.keywords}" scope="session" />
                        <c:set var="author" value="${imagen.author}" scope="session" />
                        <c:set var="date" value="${imagen.date}" scope="session" />
                        <c:set var="creator" value="${imagen.creator}" scope="session" />
                        <form action="eliminarImagen.jsp" method="POST">
                            <button type="submit">Eliminar</button>
                        </form>
                        <form action="modificarImagen.jsp" method="POST">
                            <button type="submit">Modificar</button>
                        </form>
                    </c:if>
                    <br> <br> <br>
                </c:forEach>
            </c:if>
    </body>
</html>
