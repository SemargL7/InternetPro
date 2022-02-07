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
    <meta charset="UTF-8">
    <title>Change Tariff</title>
    <link rel = "icon" href =
            "https://img.icons8.com/fluency-systems-filled/48/000000/create-icon.png"
          type = "image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style type="text/css">
        body{
            overflow-x: hidden;
            background-color: #30475E;
            opacity: 1;
            background: linear-gradient(135deg, #F0545455 25%, transparent 25%) -25px 0/ 50px 50px, linear-gradient(225deg, #F05454 25%, transparent 25%) -25px 0/ 50px 50px, linear-gradient(315deg, #F0545455 25%, transparent 25%) 0px 0/ 50px 50px, linear-gradient(45deg, #F05454 25%, #30475E 25%) 0px 0/ 50px 50px;
        }
        .links a{
            text-decoration: none;
            padding-right: 1%;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">

            <a class="navbar-brand" href="/">InternetPRO</a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${logUser.getUserAccess().toString().equals(\"MANAGER\")}">
                            <li class="nav-item">
                                <a class="nav-link active" id="users-nav-link" aria-current="page" href="/home/usersList">Users</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" id="manager-tariffs-nav-link" aria-current="page" href="/home/managerTariffsList">Tariffs</a>
                            </li>
                        </c:when>
                        <c:when test="${logUser.getUserAccess().toString().equals(\"USER\")}">
                            <li class="nav-item">
                                <a class="nav-link active" id="user-connected-tariff-nav-list" aria-current="page" href="/home/userTariffsList">My Tariffs</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" id="user-tariff-nav-link" aria-current="page" href="/home/tariffsList">Tariffs</a>
                            </li>
                        </c:when>
                    </c:choose>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <c:choose>
                        <c:when test="${logUser == null}">
                            <li class="nav-item">
                                <a class="nav-link active" id="login-nav-link" aria-current="page" href="/login">Sign In</a>
                            </li>
                        </c:when>
                        <c:when test="${logUser != null}">
                            <li class="nav-item">
                                <div class="dropdown">
                                    <a class="btn btn-dark dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                                            ${logUser.name}
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                        <li><a class="dropdown-item" id="acc-nav-link" href="/home">Account</a></li>
                                        <c:if test="${logUser.getUserAccess().toString() == \"USER\"}">
                                            <li><a class="dropdown-item" id="balance-nav-link" href="/home/balance">Balance:${logUser.balance}</a></li>
                                        </c:if>
                                        <li><a class="dropdown-item" id="login-out-nav-list" href="/home/logOut">Sign Out</a></li>
                                    </ul>
                                </div>
                            </li>
                        </c:when>
                    </c:choose>
                    <li class="nav-item">
                        <a class="nav-link active" id="lang-nav-link" aria-current="page" href="/changeLanguage" onmouseup="changeLanguage((language === 2)?1:2)">Eng/Ua</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>


<main class="mt-1">
    <div class="container-fluid w-75 p-3 text-light d-flex justify-content-center">
        <form class="p-3 bg-dark rounded" action="/home/updateTariff" method="post">
            <h1 id="changeTariffLogo">Edit Tariff</h1>

            <input type="hidden" name="id" value="<c:out value='${tariff.id}' />" />

            <div class="form-group p-1">
                <label id="inputCostLabel" for="inputCost">Cost</label>
                <input type="number" class="form-control" id="inputCost" placeholder="Enter cost" name="cost" min="0" value="${tariff.cost}" required>
            </div>

            <div class="form-group p-1">
                <label id="daysOfTariffLabel" for="daysOfTariff">Days</label>
                <input type="number" class="form-control" id="daysOfTariff" placeholder="Enter days of tariff" name="daysOfTariff" min="0" value="${tariff.daysOfTariff}" required>
            </div>

            <div class="form-group p-1">
                <label id="inputDescEngLabel" for="inputDescEng">Description Eng</label>
                <input type="text" class="form-control" id="inputDescEng" placeholder="Eng" name="descriptionENG" min="0" value="${tariff.description[(1).intValue()]}" required>
            </div>

            <div class="form-group p-1">
                <label id="inputDescUaLabel" for="inputDescUa">Description Ua</label>
                <input type="text" class="form-control" id="inputDescUa" placeholder="Ua" name="descriptionUa" min="0" value="${tariff.description[(2).intValue()]}" required>
            </div>

            <button type="submit" class="btn btn-primary ms-1" id="changeTariffSubmitBtn">Submit</button>
        </form>
    </div>
</main>


<footer class="bg-dark text-center text-lg-start text-light mt-auto">
    <div class="container p-2">
        <div class="row">
            <div class="links">
                <h5>project made by <small>Roman Dubil</small></h5>
                <a href="https://github.com/SemargL7">GitHub</a>
                <a href="https://t.me/DRRua">Telegram</a>
                <a href="#">Phone:+380984041302</a>
            </div>
        </div>
    </div>

    <div class="text-center p-1" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2021
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<script type="text/javascript">
    let lang=["users-nav-link","manager-tariffs-nav-link","user-connected-tariff-nav-list","user-tariff-nav-link","login-nav-link","acc-nav-link","balance-nav-link","login-out-nav-list","lang-nav-link",
        "changeTariffLogo","inputServiceLabel","inputCostLabel","daysOfTariffLabel","inputDescEngLabel","inputDescUaLabel","changeTariffSubmitBtn"
    ];
    let langEng =["Users","Tariffs","My Tariffs","Tariffs","Sign In","Account","Balance:${logUser.balance}","Sign Out","Eng/Ua",
        "Edit Tariff","Service","Cost","Days Of Tariff","Description Eng","Description Ua","Add"
    ];
    let langUa =["Користувачі", "Тарифи", "Мої тарифи", "Тарифи", "Вхід", "Обліковий запис", "Баланс:${logUser.balance}", "Вийти", "Укр/Анл",
        "Редагування тариф", "Послуга", "Вартість", "Дні тарифу", "Опис Англ", "Опис Укр", "Редагувати"
    ];
    let language = parseInt('${(language).intValue()}');
    function changeLanguage(id_lang){
        if(id_lang === 2) {
            for (i = 0; i < lang.length; i++) {
                var el = document.getElementById(lang[i]);
                if (el) el.textContent = langUa[i];
            }
        }
        else {
            for (i = 0; i < lang.length; i++) {
                var el = document.getElementById(lang[i]);
                if (el) el.textContent = langEng[i];
            }
        }
    }
    changeLanguage(language);
</script>
</body>
</html>
