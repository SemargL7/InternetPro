<%@ page import="com.finalproject.internetpro.dao.DAOrealisation.DAOUser" %>
<%@ page import="com.finalproject.internetpro.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        input {
            width: 170px;
        }
        table{
            color: #142a3b;
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
        .registerForm{
            width: 290px;
            height: 270px;
            margin: auto;
            border: 3px solid #6202f3;
            padding: 10px;
            background-color: #B1D0E0;
            border-radius: 5px 20px 5px;

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
<div class="mainBody">
    <div class="registerForm">
        <h1 id="registerForm_HTML">Register Form</h1>
        <form action="/register/reg" method="post">
                <table style="with: 80%">
                <tr>
                    <td id="name_HTML">Name</td>
                    <td><input type="text" name="name" /></td>
                </tr>
                <tr>
                    <td id="surname_HTML">Surname</td>
                    <td><input type="text" name="surname" /></td>
                </tr>
                <tr>
                    <td id="email_HTML">Email</td>
                    <td><input type="email" name="email" /></td>
                </tr>
                <tr>
                    <td id="password_HTML">Password</td>
                    <td><input type="password" name="password" /></td>
                </tr>
                <tr>
                    <td id="dateOfBirth_HTML">Date of Birth</td>
                    <td><input id="dateField" type="date" name="dataOfBirth" min='1900-01-01' max='2100-01-01'/></td>
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
