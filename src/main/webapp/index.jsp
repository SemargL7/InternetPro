
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>

<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/JSPF/directive/page.jspf" %>
<%@ include file="/WEB-INF/JSPF/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Today: <m:today/></title>
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
        main .container-fluid{
            background-color: rgba(40, 44, 52, 0.95);
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

<main class="mt-1 h-100">
    <div class="container-fluid w-75 text-light">
        <h2>Today: <m:today/></h2>
    </div>

</main>

<%@ include file="/WEB-INF/JSPF/footer.jspf" %>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<script type="text/javascript">

    let lang=["users-nav-link","manager-tariffs-nav-link","user-connected-tariff-nav-list","user-tariff-nav-link","login-nav-link","acc-nav-link","balance-nav-link","login-out-nav-list","lang-nav-link"];
    let langEng =["Users","Tariffs","My Tariffs","Tariffs","Sign In","Account","Balance:${logUser.balance}","Sign Out","Eng/Ua"];
    let langUa =["Користувачі", "Тарифи", "Мої тарифи", "Тарифи", "Вхід", "Обліковий запис", "Баланс:${logUser.balance}", "Вийти", "Укр/Анл"];
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