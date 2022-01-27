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
            width: 980px;
            padding-top: 50px;
            margin: 0 auto;
            text-align: center;
            color: #142a3b;
        }
        #submit_HTML{
            font-size: large;
            color: #fff;
            background-color: rgba(0, 0, 0, 0);
            border: rgba(0,0,0,0);
            width: 50px;
            transition: transform .1s;
            float: right;
            position: fixed;
            left:0;
            top:60px;
        }
        #submit_HTML:hover{
            transform: scale(1.1);
            background-color: rgba(122, 110, 110, 0.26);
        }

        .table-bordered{
            display: block;
        }

        table{
            background-color: rgba(154, 154, 154, 0.34);
            border-collapse: collapse;


        }
        table th{
            width: 100px;
            text-align: center;
            color:#FFF;
            padding: 4px 30px 4px 8px;
            border: 1px solid rgba(98, 98, 98, 0.65);

            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 100px;
        }

        table td{
            width: 100px;
            border: 1px solid rgba(98, 98, 98, 0.65);
            padding: 4px 8px;
            color: white;

            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 90px;
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

<form action="/home/addTariff" method="post">
    <input id="submit_HTML" type="submit" value="✚" />
</form>
<div class="mainBody">
    <div class="form">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th id="id_HTML">ID</th>
                <th id="service_HTML">Service</th>
                <th id="cost_HTML">Cost</th>
                <th id="daysOfTariff_HTML">Days of Tariff</th>
                <th id="description_HTML" style="width: 380px;max-width: 370px;">Description</th>
                <th id="action_HTML">Action</th>
            </tr>
            </thead>
            <tbody>
            <!--   for (Todo todo: todos) {  -->
            <%--@elvariable id="listUser" type="java.util.List"--%>
            <c:forEach var="tariff" items="${listTariff}">

                <tr>
                    <td>
                        <c:out value="${tariff.id}" />
                    </td>
                    <td>
                        <c:out value="${tariff.service.getServiceName()}" />
                    </td>
                    <td>
                        <c:out value="${tariff.cost}" />
                    </td>
                    <td >
                        <c:out value="${tariff.daysOfTariff}" />
                    </td>
                    <td style="width: 380px;max-width: 370px;">
                        <c:out value="${tariff.description[(language).intValue()]}" />
                    </td>
                    <td style="font-size: large;"><a id="change_HTML" href="/home/changeTariff?id=<c:out value='${tariff.id}' />">⚙</a> &nbsp;&nbsp;&nbsp;&nbsp; <a id="delete_HTML" href="/home/deleteTariff?id=<c:out value='${tariff.id}' />">🗑</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>
        </table>
        <ul id="pagination">
            <c:forEach begin="1" end="${paginationMax+1}" varStatus="loop">
                <c:if test="${loop.index == loop.begin || loop.index == loop.end || (loop.index >= page-3 && loop.index <= page+3)}">
                    <li>
                        <a href="/home/managerTariffsList?page=<c:out value='${loop.index}' />">${loop.index}<br/></a>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>

</body>
<script type="text/javascript">


    let lang=["home_HTML","usersList_HTML","tariffsList_HTML",
        "loginOut_HTML",

        "id_HTML","service_HTML",
        "cost_HTML","daysOfTariff_HTML",
        "description_HTML","action_HTML"];
    let langEng =["Home","Users","Tariffs",
        "Login-Out",

        "ID","Service",
        "Cost","Days of Tariffs",
        "Description","Action"];
    let langUa =["Додому","Користувачі","Тарифи",
        "Вийти",

        "Номер","Сервіс",
        "Ціна","К-ть днів дії",
        "Опис","Дія"];


    let language = parseInt('${(language).intValue()}');

    if(language == 2) {
        for (i = 0; i < lang.length; i++) {
            document.getElementById(lang[i]).textContent = langUa[i];
        }
    }
    else {
        for (i = 0; i < lang.length; i++) {
            document.getElementById(lang[i]).textContent = langEng[i];
        }
    }
</script>
</html>