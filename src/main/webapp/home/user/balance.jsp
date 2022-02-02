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

        .balanceFrom{
            text-align: center;
            width: 290px;
            height: 80px;
            margin: auto;
            border: 3px solid rgba(0, 0, 0, 0);
            padding: 10px;
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
        <li><a id="balance_HTML" href="/home/balance">Balance:${logUser.balance}</a></li>
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
                    <td><input type="number" name="balance" min="0" /></td>
                </tr>
            </table>
            <input id="submit_HTML" type="submit" value="⇨" />
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    let lang=["home_HTML","loginOut_HTML","depositMoney_HTML","submit_HTML","balance_HTML"];
    let langEng =["Home","Login-out","Deposit money","Submit","Balance:${logUser.balance}"];
    let langUa =["Додому","Вийти","Сума поповнення","Подати","Рахунок:${logUser.balance}"];
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
