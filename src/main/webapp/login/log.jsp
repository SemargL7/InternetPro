<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel = "icon" href =
            "https://img.icons8.com/fluency-systems-filled/48/000000/create-icon.png"
          type = "image/x-icon">
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
        }
        table td input{
            border-radius: 2%;
            border: 1px solid rgba(255, 255, 255, 0.41);
            background-color: rgba(255, 255, 255, 0.29);
            color: white;
        }


        .loginForm{
            text-align: center;
            color: white;
            width: 290px;
            height: 180px;
            margin: auto;
            transition: transform .5s;
            padding: 10px;
            border-radius: 5px 20px 5px;
        }
        .loginForm:hover{
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


<c:if test = "${logWarning == true}">
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        Wrong email or password
    </div>
</c:if>

<div class="mainBody">
    <div class="loginForm">
        <h1 id="login2_HTML">Login</h1>
        <form action="/login/log" method="post">
            <table style="with: 80%">
                <tr>
                    <td id="email_HTML">Email</td>
                    <td><input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required/></td>
                </tr>
                <tr>
                    <td id="password_HTML">Password</td>
                    <td><input type="password" name="password" required/></td>
                </tr>
            </table>
            <input id="submit_HTML" type="submit" onmouseup="hoverOnOutBut()" value="⇨" />
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    let lang=["home_HTML","login_HTML","register_HTML","login2_HTML","email_HTML","password_HTML","submit_HTML"];
    let langEng =["Home","Login","Register","Login","Email","Password","Submit"];
    let langUa =["Дім","Вхід","Реєстрація","Вхід","Почта","Пароль","Подати"];
    let language = parseInt('${(language).intValue()}');

    if(language === 2)
        for(i = 0; i <lang.length;i++) {
            document.getElementById(lang[i]).textContent = langUa[i];
        }
    else
        for(i = 0; i <lang.length;i++) {
            document.getElementById(lang[i]).textContent = langEng[i];
        }
</script>
</html>