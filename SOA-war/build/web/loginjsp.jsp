<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        <title>SOA : Login</title>
    </head>
    <body>
        <div id="pageContainer">
            <header>
                <div class="logoContainer">
                </div>
                <nav id="nav">
                    <ul id="hamburger">
                        <li><a href="Home">Home</a></li>
                        <li><a href="Login">Login</a></li>
                        <li><a href="Dashboard">Dashboard</a></li>
                        <li><a href="CreateListing">Create Listing</a></li>
                        <li><a href="AdminDashboard">Admin Dashboard</a></li>
                    </ul>
                </nav>
            </header>
        
        <div id="content">
            <section>
                <h1>Login</h1>
                
                <div id="message">
                    ${message}
                </div>
                
                <div id="formContainer">
                    <form action="Login" method="post">
                    <label for="username">Username: </label><br>
                    <input type="text" id="username" name="username" class="input"><br>
                    <label for="password">Password: </label><br>
                    <input type="text" id="password" name="password" class="input"><br>
                    <input type="submit" value="Login" class="submit">
                </form>
                </div>
                <div id="registerLink">
                    <a href="Register">Not got an account? Register here!</a>
                </div>
            </section>
        </div>
    
        <footer>
            <div id="socialsContainer">
            </div>
            <div>
                <a href="#">Contact/Find Us</a>
            </div>
            <div>
                <p id="copyright">Copyright &copy; of SOA 2022</p>  
            </div>
        </footer>
    </div>
    </body>
</html>
