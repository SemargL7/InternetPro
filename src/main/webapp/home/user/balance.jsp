<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 30.12.2021
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Balance</title>
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

        .balanceFrom{
            width: 290px;
            height: 80px;
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
    <div class="balanceFrom">
        <form action="/home/depositBalance" method="post">
            <table style="with: 80%">
                <tr>
                    <td id="depositMoney_HTML">Deposit money:</td>
                    <td><input type="number" name="balance" /></td>
                </tr>
            </table>
            <input id="submit_HTML" type="submit" value="⇨" />
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    let lang=["home_HTML","loginOut_HTML","depositMoney_HTML","submit_HTML"];
    let langEng =["Home","Login-out","Deposit money","Submit"];
    let langUa =["Додому","Вийти","Сума поповнення","Подати"];
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
