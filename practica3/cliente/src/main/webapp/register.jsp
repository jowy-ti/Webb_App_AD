<%-- 
    Document   : register
    Created on : 20 sept 2024, 19:37:54
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
        <h1>REGISTER</h1>
        <form action="register" method="post">
            <ul>
                <label for="id_usuario">Usuario:</label>
                <input type="text" id="id_usuario" name="id_usuario" />
                
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" />
                              
                <label for="confirmPass">Confirma la contraseña:</label>
                <input type="password" id="confirmPass" name="confirmPass" />
                
                <button type="submit">Register</button>
            </ul>
        </form>
        <a href="login.jsp">Login</a>
    </body>
</html>
