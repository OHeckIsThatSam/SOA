<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        
        <title>SOA : ${listing.getTitle()}</title>
    </head>
    
    <body>
        <script>
        var countDownDate = new Date("${listing.getEndTime()}").getTime();
        
        // Update the count down every 1 second
        var x = setInterval(function() {

        // Get today's date and time
        var now = new Date().getTime();

        // Find the distance between now and the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Display the result in the element with id="demo"
        document.getElementById("countdown").innerHTML = "Time left: " + days + "d " + hours + "h "
        + minutes + "m " + seconds + "s ";

        if ((seconds == -1)) {
            location.reload();
        }
        }, 1000);
        </script>
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
                <h1>${listing.getTitle()}</h1>
                <div id="message">
                    ${message}
                </div>
                <div>
                    <c:choose>
                        <c:when test="${listing.getActive()}">
                            <div id="countdown">
                            </div>  
                        </c:when>
                        <c:otherwise>
                            <div>
                                Time Left: ENDED
                            </div>
                        </c:otherwise>
                    </c:choose>
                        
                    <p>
                        Condition: ${listing.getCondition()}
                    </p>
                    <p>
                        Highest bid: ${listing.getHighestBid()}
                    </p>
                    <c:if test="${listing.getActive()}">
                        <form action="Bid">
                            <input type="hidden" name="listingId" value="${listing.getId()}">
                            <input type="submit" value="Bid" />
                        </form>
                        <c:if test="${user.isAdmin()}">
                            <form action="ViewListing" method="post">
                                <input type="hidden" name="listingId" value="${listing.getId()}">
                                <input type="submit" name="end" id="end" value="End" />
                                <input type="submit" name="remove" class="deny" value="remove" />
                            </form>
                        </c:if>
                        
                    </c:if>
                    <p>
                        ${listing.getDescription()}
                    </p>
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
