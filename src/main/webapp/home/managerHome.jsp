<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 29.12.2021
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager</title>
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
        }

        table tr:nth-child(odd) td{
            background-color: #e7edf0;
        }
    </style>
</head>
<body>
<nav>
    <ul>
        <li><a id="home_HTML" href="/home">Home</a></li>
        <li><a id="usersList_HTML" href="/home/usersList">Users</a></li>
        <li><a id="tariffsList_HTML" href="/home/managerTariffsList">Tariffs</a></li>
    </ul>
    <ul class="right-nav">
        <li><a id="loginOut_HTML" href="/home/loginOut">Login-out</a></li>
        <li><a id="changeLanguage_HTML" href="/changeLanguage">ENG/UA</a></li>
    </ul>
</nav>

<div class="mainBody">
Manager
</div>
</body>
<script type="text/javascript">
    let lang=["home_HTML","loginOut_HTML",

        "usersList_HTML","tariffsList_HTML",

        "id_HTML","name_HTML","surname_HTML",
        "dateBirth_HTML","email_HTML","balance_HTML",
        "blocked_HTML","specialAccess_HTML","action_HTML",
        "block_unblock_HTML",

        "addTariff_HTML",

        "id2_HTML","service_HTML","cost_HTML","daysOfTariff_HTML",
        "description_HTML","action2_HTML","change_HTML","delete_HTML"];
    let langEng =["Home","Login-Out",

        "Users","Tariffs",

        "ID","Name","Surname",
        "Date Of Birth","Email","Balance",
        "Blocked","Special Access","Action",
        "Block/Unblock",

        "Add Tariff",

        "ID","Service","Cost","Days of Tariff",
        "Description","Action","Change","Delete"];
    let langUa =["Додому","Вийти",

        "Користувачі","Тарифи",

        "Номер","Ім'я","Фамілія",
        "Дата народження","Почта","Рахунок",
        "Заблокований","Спец. доступ","Дія",
        "Заблок./Розблок.",

        "Додати тариф",

        "Номер","Сервіс","Ціна","К-ть днів дії",
        "Опис","Дія","Змінити","Видалити"];
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
