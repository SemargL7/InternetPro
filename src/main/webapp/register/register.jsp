<%@ page import="com.finalproject.internetpro.dao.DAOrealisation.DAOUser" %>
<%@ page import="com.finalproject.internetpro.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
    <style type="text/css">
        body {
            color: #B1D0E0;
            background: url("https://www.teahub.io/photos/full/164-1647689_wallpaper-montreal-canada-night-city-dark-city-wallpaper..jpg");
            background-size: cover;
        }

        nav{
            width: 100%;
            margin: -10px auto;
            float: left;
        }
        nav:before {
            content: '';
            display: block;
            height: 50px;
            width: 100%;
            position: absolute;
            left:0;
            z-index: -1;
        }
        .right-nav{
            float: right;
        }
        ul{
            margin: 0;
            padding: 0;
            list-style: none;
        }
        ul li
        {
            float:left;
        }
        ul li a{
            border-radius: 10% 30% 50% 70%;
            color: #fff;
            display: block;
            height: 50px;
            padding: 0 30px;
            text-transform: uppercase;
            text-decoration: none;
            line-height: 50px;
            transition: transform .1s;
        }
        ul li a:hover {
            background-color: rgba(122, 110, 110, 0.26);
            transform: scale(1.1);
        }
        .mainBody{
            padding-top: 15%;
            margin: 0 auto;
            color: #142a3b;
        }
        #submit_HTML{
            font-size: large;
            color: #fff;
            background-color: rgba(0, 0, 0, 0);
            border: rgba(0,0,0,0);
            width: 50px;
            transition: transform .1s;
        }
        #submit_HTML:hover{
            transform: scale(1.1);
            background-color: rgba(122, 110, 110, 0.26);
        }
        table{
            border-collapse: collapse;
        }
        table th{
            text-align: center;
            background-color: #FFF;
            color:#FFF;
            padding: 4px 30px 4px 8px;
        }
        table td{
            width: 100px;
            border: 1px solid rgba(0, 0, 0, 0);
            padding: 4px 8px;
            color: white;
            text-align: center;
        }
        table td input{
            border-radius: 2%;
            border: 1px solid rgba(255, 255, 255, 0.41);
            background-color: rgba(255, 255, 255, 0.29);
            color: white;
        }
        .registerForm{
            text-align: center;
            color: white;
            width: 290px;
            height: 270px;
            margin: auto;
            transition: transform .5s;
            padding: 10px;
            border-radius: 5px 20px 5px;
        }
        .registerForm:hover{
            transform: scale(1.05);
        }
        .alert {
            padding: 20px;
            background-color: #f44336;
            color: white;
            width: 300px;
            position: fixed;
            bottom: 10px;
        }
    </style>
</head>
<body>
<nav>
    <ul>
        <li><a id="home_HTML" href="/home">Home</a></li>
    </ul>
    <ul class="right-nav">
        <li><a id="login_HTML" href="/login">Login</a></li>
        <li><a id="register_HTML" href="/register">Register</a></li>
        <li><a id="changeLanguage_HTML" href="/changeLanguage">ENG/UA</a></li>
    </ul>
</nav>

<c:if test = "${regWarning == true}">
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        This email isn`t available
    </div>
</c:if>

<div class="mainBody">
    <div class="registerForm">
        <h1 id="registerForm_HTML">Register Form</h1>
        <form action="/register/reg" method="post">
                <table style="with: 80%">
                <tr>
                    <td id="name_HTML">Name</td>
                    <td><input type="text" name="name" pattern="[A-Za-z]{1,32}" required/></td>
                </tr>
                <tr>
                    <td id="surname_HTML">Surname</td>
                    <td><input type="text" name="surname" pattern="[A-Za-z]{1,32}" required/></td>
                </tr>
                <tr>
                    <td id="email_HTML">Email</td>
                    <td><input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required/></td>
                </tr>
                <tr>
                    <td id="password_HTML">Password</td>
                    <td><input type="password" name="password" required/></td>
                </tr>
                <tr>
                    <td id="dateOfBirth_HTML">Date of Birth</td>
                    <td><input id="dateField" type="date" name="dataOfBirth" min='1900-01-01' max='2100-01-01' required/></td>
                </tr>
            </table>
            <input id="submit_HTML" type="submit" value="⇨" />
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("dateField").setAttribute("max", today);


    let lang=["home_HTML","login_HTML","register_HTML","registerForm_HTML","name_HTML","surname_HTML","email_HTML","password_HTML","dateOfBirth_HTML","submit_HTML"];
    let langEng =["Home","Login","Register","Register","Name","Surname","Email","Password","Date of Birth","Submit"];
    let langUa =["Дім","Війти","Реєстрація","Реєстрація","І'мя","Фамілія","Почта","Пароль","Дата народження","Подати"];
    let language = parseInt('${(language).intValue()}');

    if(language == 2)
        for(i = 0; i <lang.length;i++) {
            document.getElementById(lang[i]).textContent = langUa[i];
        }
    else
        for(i = 0; i <lang.length;i++) {

            document.getElementById(lang[i]).textContent = langEng[i];
        }
</script>
</html>
