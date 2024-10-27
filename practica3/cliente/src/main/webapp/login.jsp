<%-- 
    Document   : login
    Created on : 20 sept 2024, 10:21:56
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>LOGIN</h1>
        <form action="login" method="POST">
            <ul>
                <label for="id_usuario">Usuario:</label>
                <input type="text" id="id_usuario" name="id_usuario" />
                
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" />
                
                <button type="submit">Entrar</button>
            </ul>
        </form>
        <a href="register.jsp">Registrar</a>
    </body>
</html>
