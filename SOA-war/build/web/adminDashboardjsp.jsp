<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        <title>SOA : Admin Dashboard</title>
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
                <h1>Hello ${sessionScope['user'].getFirstName()}</h1>              
                <div id="message">
                    ${message}
                </div>
                <div>
                    <h2>Authentication Requests</h2>
                    <table>
                        <c:forEach items="${requestScope['authenticationRequests']}" var="authRequest">
                            <tr>
                                <td>User: <c:out value="${authRequest.getUser().getId()}"/></td>
                                <td>
                                    <form action="AdminDashboard" method="post">
                                        <input type="hidden" name="authId" value="<c:out value="${authRequest.getId()}"/>">
                                        <input type="submit" name="form" value="accept" class="accept">
                                        <input type="submit" name="form" value="deny" class="deny">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>              
                </div>
                <div>
                    <h2>Reactivation Requests</h2>
                    <table>
                        <c:forEach items="${requestScope['reactivationRequests']}" var="reacRequest">
                            <tr>
                                <td>User: <c:out value="${reacRequest.getUser().getId()}"/></td>
                                <td>
                                    <form action="AdminDashboard" method="post">
                                        <input type="hidden" name="reacId" value="<c:out value="${reacRequest.getId()}"/>">
                                        <input type="submit" name="form" value="accept" class="accept">
                                        <input type="submit" name="form" value="deny" class="deny">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
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
