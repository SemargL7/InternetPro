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
    <title>Add tariff</title>
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
        table td select{
            border-radius: 2%;
            border: 1px solid rgba(255, 255, 255, 0.41);
            background-color: rgba(255, 255, 255, 0.29);
            color: white;
        }

        .addTariffForm{
            color: white;
            text-align: center;
            width: 290px;
            height: 180px;
            margin: auto;
            border: 3px solid rgba(0, 0, 0, 0);
            padding: 10px;
        }
        #serviceSelect_HTML{
            border: 1px solid;
            padding: 4px 8px;
            width: 177px;
        }
        option{
            background-color: rgba(0, 0, 0, 0.25);
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
    <div class="addTariffForm">
        <h1 id="addTariff_HTML">Add Tariff</h1>
        <form action="/home/addedTariff" method="post">
            <table style="with: 80%">
                <tr>
                    <td id="service_HTML">Service</td>
                    <td>
                        <select name="serviceId" id="serviceSelect_HTML">
                            <c:forEach var="service" items="${listService}">
                            <option value="<c:out value="${service.id}" />"><c:out value="${service.serviceName}" /></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td id="cost_HTML">Cost</td>
                    <td><input type="number" name="cost" min="0" required/></td>
                </tr>
                <tr>
                    <td id="days_HTML">Days</td>
                    <td><input type="number" name="daysOfTariff" min="0" required/></td>
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
    let lang=       ["home_HTML","usersList_HTML","tariffsList_HTML","loginOut_HTML","service_HTML","cost_HTML","days_HTML","descriptionENG_HTML","descriptionUA_HTML","addTariff_HTML"];
    let langEng =   ["Home","Users","Tariffs","Login-out","Service","Cost","Days","Description ENG","Description UA","Add Tariff"];
    let langUa =    ["Додому","Користувачі","Тарифи","Вийти","Сервіс","Ціна","Днів","Опис Англ.","Опис Укр.","Додати тариф"];
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
