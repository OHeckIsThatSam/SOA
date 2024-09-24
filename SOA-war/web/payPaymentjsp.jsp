<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        <title>SOA : Pay</title>
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
                <h1>Pay</h1>
                <div id="formContainer">
                    <div>
                        Amount: ${payment.getAmount()}
                    </div>
                    <form action="Pay" method="post">
                        <input type="hidden" name="paymentId" value="${payment.getId()}">
                        <label for="cardHolderName">Card Holder's Name:</label><br>
                        <input type="text" name="cardHolderName" id="cardHolderName" class="input"><br>
                        <label for="cardNumber">Card Number:</label><br>
                        <input type="text" name="cardNumber" id="cardNumber" class="input"><br>
                        <label for="expiryDate">Expiry Date:</label><br>
                        <input type="text" name="expiryDate" id="expiryDate" class="input"><br>
                        <label for="securityCode">Security Code:</label><br>
                        <input type="text" name="securityCode" id="securityCode" class="input"><br>
                        <input type="submit" value="Pay">
                    </form>
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
