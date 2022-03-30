<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/JSPF/directive/page.jspf" %>
<%@ include file="/WEB-INF/JSPF/directive/taglib.jspf" %>


<!DOCTYPE html>
<html>
<head>
    <title>Sign-In</title>
    <link rel = "icon" href =
            "https://img.icons8.com/fluency-systems-filled/48/000000/create-icon.png"
          type = "image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

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


<c:if test = "${logWarning == true}">
    <div class="alert alert-warning alert-dismissible">
        <a href="/login" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong>Warning!</strong> Wrong email or password.
    </div>
</c:if>

<main class="mt-1">
    <div class="container-fluid w-75 p-3 text-light d-flex justify-content-center">
        <form class="p-3 bg-dark rounded" action="/login" method="post">
            <h1 id="signInLogo"><fmt:message key="sign-in.logo"/> </h1>
            <div class="form-group">
                <label id="signInInputEmailLabel" for="signInInputEmail"><fmt:message key="sign-in.email"/></label>
                <input type="email" class="form-control" id="signInInputEmail" aria-describedby="emailHelp" placeholder="Enter email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
            </div>
            <div class="form-group">
                <label id="signInInputPasswordLabel" for="signInInputPassword"><fmt:message key="sign-in.password"/></label>
                <input type="password" class="form-control" id="signInInputPassword" placeholder="Password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary mt-1" id="signInSubmitBtn"><fmt:message key="sign-in.submit"/></button>
        </form>
    </div>
</main>


<%@ include file="/WEB-INF/JSPF/footer.jspf" %>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>

</body>
</html>