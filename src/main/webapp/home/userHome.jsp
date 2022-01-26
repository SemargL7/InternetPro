<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 29.12.2021
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
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

        table{
            border-collapse: collapse;
            background: white;
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
            color: #1A374D;
        }

        .active{
            visibility: hidden;
            text-indent: -9999px;
        }
    </style>
</head>
<body>
<nav>
    <ul>
        <li><a id="home_HTML" href="/home">Home</a></li>
        <li><a id="userTariffsList_HTML" href="/home/userTariffsList">Connected Tariffs</a></li>
        <li><a id="tariffsList_HTML" href="/home/tariffsList">All Tariffs</a></li>
    </ul>
    <ul class="right-nav">
        <li><a id="balance_HTML" href="/home/balance">Balance:${userBalance}</a></li>
        <li><a id="loginOut_HTML" href="/home/loginOut">Login-out</a></li>
        <li><a id="changeLanguage_HTML" href="/changeLanguage">ENG/UA</a></li>
    </ul>
</nav>

<div class="mainBody">
    <form action="/home/saveProfile" method="post">
        <table style="with: 80%">
            <tr>
                <td id="status_HTML">Status</td>
                <td class="info">User</td>
                <td class="changeInfo">User</td>
            </tr>
            <tr>
                <td id="name_HTML">Name</td>
                <td class="info">${user.name}</td>
                <td class="changeInfo"><input type="text" name="user_name" value="${user.name}" required/></td>
            </tr>
            <tr>
                <td id="surname_HTML">Surname</td>
                <td class="info">${user.surname}</td>
                <td class="changeInfo"><input type="text" name="user_surname" value="${user.surname}" required/></td>
            </tr>
            <tr>
                <td id="email_HTML">Email</td>
                <td class="info">${user.email}</td>
                <td class="changeInfo"><input type="email" name="user_email" value="${user.email}" required/></td>
            </tr>
            <tr>
                <td id="password_HTML">Password</td>
                <td class="info">${user.password}</td>
                <td class="changeInfo"><input type="text" name="user_password" value="${user.password}" required/></td>
            </tr>
            <tr>
                <td id="action_HTML">Action</td>
                <td class="info"> <span onclick="clickOnChangeBut()" >⚙</span></td>
                <td class="changeInfo"><input id="submit_HTML" type="submit" value="⇨" /></td>
            </tr>
        </table>
    </form>
</div>
</body>
</body>
<script type="text/javascript">

    for (const x of document.getElementsByClassName("changeInfo")) {
        x.classList.add("active")
    }
    for (const x of document.getElementsByClassName("info")) {
        x.classList.remove("active")
    }
    function clickOnChangeBut() {
        for (const x of document.getElementsByClassName("changeInfo")) {
            x.classList.remove("active")
        }
        for (const x of document.getElementsByClassName("info")) {
            x.classList.add("active")
        }
    }




    let lang=["home_HTML","loginOut_HTML",

        "balance_HTML",

        "userTariffsList_HTML",
        "tariffsList_HTML",

        "status_HTML","name_HTML","surname_HTML","email_HTML","password_HTML","save_HTML"

        ];
    let langEng =["Home","Login-Out",

        "Balance:${userBalance}",

        "Connected Tariffs",
        "All tariffs",

        "Status","Name","Surname","Email","Password","Save"

        ];
    let langUa =["Додому","Вийти",

        "Рахунок:${userBalance}",

        "Підключені тарифи",
        "Всі тарифи",

        "Статус","Ім'я","Фамілія","Почта","Пароль","Зберегти"

        ];
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
