<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/JSPF/directive/page.jspf" %>
<%@ include file="/WEB-INF/JSPF/directive/taglib.jspf" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Tariff</title>
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
    <%@ include file="/WEB-INF/JSPF/navigation.jspf" %>
</header>


<main class="mt-1">
    <div class="container-fluid w-75 p-3 text-light d-flex justify-content-center">
        <form class="p-3 bg-dark rounded" action="/home/addTariff" method="post">
            <h1 id="addTariffLogo"><fmt:message key="add-tariff"/></h1>
            <div class="form-group p-1">

                <c:forEach var="service" items="${listService}">
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" name="services" id="${service.serviceName}" value="${service.id}">
                        <label class="form-check-label" for="${service.serviceName}">${service.serviceName}</label>
                    </div>
                </c:forEach>

            </div>

            <div class="form-group p-1">
                <label id="inputCostLabel" for="inputCost"><fmt:message key="table.tariff.cost"/></label>
                <input type="number" class="form-control" id="inputCost" placeholder="Enter cost" name="cost" min="0" required>
            </div>

            <div class="form-group p-1">
                <label id="daysOfTariffLabel" for="daysOfTariff"><fmt:message key="table.tariff.days"/></label>
                <input type="number" class="form-control" id="daysOfTariff" placeholder="Enter days of tariff" name="daysOfTariff" min="0" required>
            </div>

            <div class="form-group p-1">
                <label id="inputDescEngLabel" for="inputDescEng"><fmt:message key="desc-eng"/></label>
                <input type="text" class="form-control" id="inputDescEng" placeholder="Eng" name="descriptionENG" min="0" required>
            </div>

            <div class="form-group p-1">
                <label id="inputDescUaLabel" for="inputDescUa"><fmt:message key="desc-ua"/></label>
                <input type="text" class="form-control" id="inputDescUa" placeholder="Ua" name="descriptionUA" min="0" required>
            </div>

            <button type="submit" class="btn btn-primary ms-1" id="addTariffSubmitBtn"><fmt:message key="add-tariff.submit"/></button>
        </form>
    </div>
</main>


<%@ include file="/WEB-INF/JSPF/footer.jspf" %>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>
