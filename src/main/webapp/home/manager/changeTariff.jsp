<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 29.12.2021
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change tariff</title>
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
            color: #142a3b;
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


        .changeTariffForm{
            width: 290px;
            height: 150px;
            margin: auto;
            border: 3px solid #6202f3;
            padding: 10px;
            background-color: #B1D0E0;
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
    <div class="changeTariffForm">
        <form action="/home/updateTariff" method="post">
            <table style="with: 80%">
                <input type="hidden" name="id" value="<c:out value='${tariff.id}' />" />
                <tr>
                    <td id="cost_HTML">Cost</td>
                    <td><input type="number" name="cost" required/></td>
                </tr>
                <tr>
                    <td id="days_HTML">Days</td>
                    <td><input type="number" name="daysOfTariff" required/></td>
                </tr>
                <tr>
                    <td id="descriptionENG_HTML">Description ENG</td>
                    <td><input type="text" name="descriptionENG" required/></td>
                </tr>
                <tr>
                    <td id="descriptionUA_HTML">Description UA</td>
                    <td><input type="text" name="descriptionUA" required/></td>
                </tr>
            </table>
            <input id="submit_HTML" type="submit" value="⇨" />
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    let lang=["home_HTML","usersList_HTML","tariffsList_HTML","loginOut_HTML","cost_HTML","days_HTML","descriptionENG_HTML","descriptionUA_HTML"];
    let langEng =["Home","Users","Tariffs","Login-out","Cost","Days","Description ENG","Description UA"];
    let langUa = ["Додому","Користувачі","Тарифи","Вийти","Ціна","Днів","Опис Англ.","Опис Укр."];
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
