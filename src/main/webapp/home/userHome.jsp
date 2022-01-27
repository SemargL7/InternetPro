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
            padding-top: 50px;
            margin: 0;
            color: #ffffff;
        }

        .profileForm{
            text-align: center;
            color: white;
            background-color: rgba(154, 154, 154, 0.34);
            width: 290px;
            height: 180px;
            margin: auto;
            padding: 10px;
            border-radius: 5px 20px 5px;
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
        }
        table td input{
            border-radius: 2%;
            border: 1px solid rgba(255, 255, 255, 0.41);
            background-color: rgba(255, 255, 255, 0.29);
            color: white;
            transition: transform .1s;
        }
        table td input:hover{
            transform: scale(1.1);
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
    <div class="profileForm">
        <form action="/home/saveProfile" method="post">
            <table style="with: 80%">
                <tr>
                    <td id="status_HTML">Status</td>
                    <td class="info">Manager</td>
                    <td class="changeInfo">Manager</td>
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
                    <td class="info"> <input style="width: 24px" id="change_HTML" onclick="clickOnChangeBut()" type="button" value="⚙" /></td>
                    <td class="changeInfo"><input id="submit_HTML" type="submit" value="⇨" /></td>
                </tr>
            </table>
        </form>
    </div>
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
