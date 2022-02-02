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
            width: 980px;
            padding-top: 50px;
            margin: 0 auto;
            text-align: center;
            color: #142a3b;
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


<div class="manager-nav">
    <ul>

    </ul>
</div>

<div class="mainBody">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th id="id_HTML">ID</th>
            <th id="name_HTML">Name</th>
            <th id="surname_HTML">Surname</th>
            <th id="dateBirth_HTML">Date birth</th>
            <th id="email_HTML">Email</th>
            <th id="balance_HTML">Balance</th>
            <th id="blocked_HTML">Blocked</th>
            <th id="specialAccess_HTML">Special Access</th>
            <th id="action_HTML">Actions</th>
        </tr>
        </thead>
        <tbody>
        <!--   for (Todo todo: todos) {  -->
        <%--@elvariable id="listUser" type="java.util.List"--%>
        <c:forEach var="user" items="${listUser}">

            <tr>
                <td>
                    <c:out value="${user.id}" />
                </td>
                <td>
                    <c:out value="${user.name}" />
                </td>
                <td>
                    <c:out value="${user.surname}" />
                </td>
                <td>
                    <c:out value="${user.dateOfBirth}" />
                </td>
                <td>
                    <c:out value="${user.email}" />
                </td>
                <td>
                    <c:out value="${user.balance}" />
                </td>
                <td>
                    <c:out value="${user.blocked}" />
                </td>
                <td>
                    <c:out value="${user.getUserAccess().toString()}" />
                </td>
                <td style="font-size: large;"><a href="/home/blockSwitcher?id=<c:out value='${user.id}' />">
                    <c:choose>
                        <c:when test="${user.blocked == true}">
                            ◉
                        </c:when>
                        <c:otherwise>
                            〇
                        </c:otherwise>
                    </c:choose>
                </a>
            </tr>
        </c:forEach>
        <!-- } -->
        </tbody>

    </table>

    <ul id="pagination">
        <c:forEach begin="1" end="${paginationMax+1}" varStatus="loop">
            <c:if test="${loop.index == loop.begin || loop.index == loop.end || (loop.index >= page-3 && loop.index <= page+3)}">
                <li>
                    <a href="/home/usersList?page=<c:out value='${loop.index}' />">${loop.index}<br/></a>
                </li>
            </c:if>


        </c:forEach>
    </ul>
</div>
</body>
<script type="text/javascript">
    let lang=["home_HTML","loginOut_HTML",

        "usersList_HTML","tariffsList_HTML",

        "id_HTML","name_HTML","surname_HTML",
        "dateBirth_HTML","email_HTML","balance_HTML",
        "blocked_HTML","specialAccess_HTML","action_HTML",


        "addTariff_HTML",

        "id2_HTML","service_HTML","cost_HTML","daysOfTariff_HTML",
        "description_HTML","action2_HTML","change_HTML","delete_HTML"];
    let langEng =["Home","Login-Out",

        "Users","Tariffs",

        "ID","Name","Surname",
        "Date Of Birth","Email","Balance",
        "Blocked","Special Access","Action",


        "Add Tariff",

        "ID","Service","Cost","Days of Tariff",
        "Description","Action","Change","Delete"];
    let langUa =["Додому","Вийти",

        "Користувачі","Тарифи",

        "Номер","Ім'я","Фамілія",
        "Дата народження","Почта","Рахунок",
        "Заблокований","Спец. доступ","Дія",


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