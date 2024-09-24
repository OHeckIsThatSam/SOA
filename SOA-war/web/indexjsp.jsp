<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        <title>SOA : Home</title>
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
        </div>
        <div id="content">
            <section>
                <h1>Home</h1>              
                <p>User: ${(sessionScope['user'] != null ? sessionScope['user'].getFirstName() : 'Please login')}</p>
                <div>
                    <h2>Top Listing</h2>
                    <div>
                    <c:choose>
                        <c:when test="${listing.getId() != null}">
                            <a href="ViewListing?listingId=${listing.getId()}">${listing.getTitle()}</a>
                        </c:when>
                        <c:otherwise>
                            There are no listings :(
                        </c:otherwise>
                    </c:choose>    
                    </div>
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
    
    </body>
</html>
