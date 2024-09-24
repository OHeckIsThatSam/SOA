<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        <title>SOA : Dashboard</title>
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
                    
                <h2>Your details</h2>
                <div id="userDetails">
                    <ul>
                        <li>First name: ${sessionScope['user'].getFirstName()}</li>
                        <li>Last name: ${sessionScope['user'].getLastName()}</li>
                        <li>Email: ${sessionScope['user'].getEmail()}</li>
                        <li>Phone number: ${sessionScope['user'].getPhoneNumber()}</li>
                        <li>Address: ${sessionScope['user'].getAddress()}</li>
                    </ul>
                </div>
                <div id="message">
                    ${message}
                </div>
                <c:if test="${sessionScope['user'].isUnauthorised()}">
                <h2>Request Authentication</h2>
                <div>
                    Request
                    <form action="Dashboard" method="post">
                        
                        <input type="submit" value="Send" name="authentication" class="submit">
                    </form>
                </div>
                </c:if>
                <c:if test="${sessionScope['user'].isDeactivated()}">
                <h2>Request Reactivation</h2>
                <div>
                    Request
                    <form action="Dashboard" method="post">
                        <input type="submit" value="Send" name="reactivation" class="submit">
                    </form>
                </div>
                </c:if>
                <c:if test="${!payments.isEmpty()}">
                    <h2>Outstanding Payments</h2>
                    <c:forEach items="${payments}" var="payment">
                        <div>
                            Amount: ${payment.getAmount()}
                            Due by: ${payment.getDue()}
                            <form action="Pay">
                                <input type="hidden" name="paymentId" value="${payment.getId()}">
                                <input type="submit" value="Pay">
                            </form>
                        </div>
                    </c:forEach>
                </c:if>
                
                <div>
                    
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
