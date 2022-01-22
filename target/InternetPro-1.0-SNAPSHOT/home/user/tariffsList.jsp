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
            width: 980px;
            padding-top: 50px;
            margin: 0 auto;
            text-align: center;
            color: #142a3b;
        }


        table{
            color: #142a3b;
            border-collapse: collapse;
            background: white;
            box-shadow: 12px 12px 2px 1px rgba(58, 95, 111, .2);
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
        #disc_HTML{width: 100%}
        #pagination{
            display: block;
            padding: 0;
            list-style-type: none;
        }
        #pagination li{
            margin-right: 5px;
            padding: 10px;
            border: 1px solid black;
            width: 40px;
            height: 40px;
        }
        #pagination li:hover{
            cursor:pointer;
            color: red;
            border: 1px solid red;
        }
        #submit_HTML{
            font-size: large;
            color: #fff;
            left:0;
            top:60px;
            background-color: #3a6070;
            float: right;
            position: fixed;
        }
        #submit_HTML:hover{
            color: #ffffff;
            background-color: #29444f;
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

<form action="/home/downloadDocx" method="post">
    <input id="submit_HTML" type="submit" value="﹀" />
</form>

<div class="mainBody">
    <table class="table table-bordered2">
        <thead>
        <tr>
            <th id="id_HTML">ID</th>
            <th id="service_HTML">Service</th>
            <th id="cost_HTML">Cost</th>
            <th id="daysOfTariff_HTML">Days of Tariff</th>
            <th id="description_HTML">Description</th>
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
                <td>
                    <c:out value="${tariff.daysOfTariff}" />
                </td>
                <td id="disc_HTML">
                    <c:out value="${tariff.description[(language).intValue()]}" />
                </td>
                <td><a id="connect_HTML" href="/home/connectTariff?id=<c:out value='${tariff.id}' />">Connect</a>
            </tr>
        </c:forEach>
        <!-- } -->
        </tbody>

    </table>
    <ul id="pagination">
        <c:forEach begin="1" end="${paginationMax+1}" varStatus="loop">
            <c:if test="${loop.index == loop.begin || loop.index == loop.end || (loop.index >= page-3 && loop.index <= page+3)}">
                <li>
                    <a href="/home/tariffsList?page=<c:out value='${loop.index}' />">${loop.index}<br/></a>
                </li>
            </c:if>


        </c:forEach>
    </ul>
</div>

</body>
<script type="text/javascript">
    let lang=["home_HTML","loginOut_HTML",

        "userTariffsList_HTML","tariffsList_HTML",

        "balance_HTML",

        "id_HTML","service_HTML","cost_HTML","daysOfTariff_HTML",
        "description_HTML","action_HTML","connect_HTML"];
    let langEng =["Home","Login-Out",

        "Connected tariffs","Tariffs",

        "Balance:${userBalance}",

        "ID","Service","Cost","Days of Tariff",
        "Description","Action","Connect"];
    let langUa =["Додому","Вийти",

        "Під'єднанні тарифи","Тарифи",

        "Рахунок:${userBalance}",

        "Номер","Сервіс","Ціна","К-ть днів дії",
        "Опис","Дія","З'єднати"];
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