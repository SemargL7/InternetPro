<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>
    <style type="text/css">
        body {
            color: #B1D0E0;
            background-color: #1A374D;
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
            background-color: #6998AB;
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
            color: #fff;
            display: block;
            height: 50px;
            padding: 0 30px;
            text-transform: uppercase;
            text-decoration: none;
            line-height: 50px;
        }
        ul li a:hover {
            background: #406882;
        }
        .mainBody{
            padding-top: 50px;
            margin: 0;
            color: #142a3b;
        }
        #submit_HTML{
            font-size: large;
            color: #fff;
            background-color: #3a6070;
            width: 100px;
        }

        table{
            border-collapse: collapse;
        }
        table th{
            text-align: left;
            background-color: #3a6070;
            color:#FFF;
            padding: 4px 30px 4px 8px;
        }

        table td{
            border: 1px solid #e3e3e3;
            padding: 4px 8px;
        }


        .loginForm{
            width: 290px;
            height: 180px;
            margin: auto;
            border: 3px solid #6202f3;
            padding: 10px;
            background-color: #B1D0E0;
            border-radius: 5px 20px 5px;
        }

        .meme{
            width: 100%;
            height: 50%;
            background-size:cover;
            display: block;
            position: absolute;
            bottom: -10px;
            left: 0;
        }
        .meme.active{
            opacity: 0.0;
            transform: scale(0.9);
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
        <li><a id="login_HTML" href="/login">Login</a></li>
        <li><a id="register_HTML" href="/register">Register</a></li>
    </ul>
    <ul class="right-nav">
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
                    <td><input type="email" name="email" required/></td>
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