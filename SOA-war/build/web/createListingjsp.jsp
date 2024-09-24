<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        <title>SOA : Create Listing</title>
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
                <h1>Create Listing</h1>
                <div id="formContainer">
                    <form action="CreateListing" method="post">
                        <label for="title">Title: </label><br>
                        <input type="text" id="title" name="title" class="input"><br>
                        <label for="condition">Condition: </label><br>
                        <select name="condition" id="condition" class="input">
                            <option value="New">New</option>
                            <option value="Never used">Never used</option>
                            <option value="Used">Used</option>
                            <option value="Broken">Broken</option>
                        </select><br>
                        <label for="description">Description: </label><br>
                        <textarea rows = "5" cols = "20" id="description" name="description" class="input"></textarea><br>
                        <label for="startPrice">Start Price: </label><br>
                        <input type="text" id="startPrice" name="startPrice" class="input"><br>
                        <label for="duration">Duration: </label><br>
                        <input type="date" id="duration" name="duration" class="input"><br>
                        <input type="submit" value="Register" class="submit">
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
