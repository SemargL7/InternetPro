<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/JSPF/directive/page.jspf" %>
<%@ include file="/WEB-INF/JSPF/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign-Up</title>
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

<c:if test = "${regWarning == true}">
    <div class="alert alert-warning alert-dismissible">
        <a href="/register" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong>Warning!</strong> This email isn`t available
    </div>
</c:if>

<main class="mt-1">
    <div class="container-fluid w-75 p-3 text-light d-flex justify-content-center">
        <form class="p-3 bg-dark rounded" action="/home/register" method="post">
            <h1 id="signUpLogo"><fmt:message key="sign-up.logo"/></h1>
            <div class="form-group">
                <label id="signUpInputNameLabel" for="signUpInputName"><fmt:message key="name"/> </label>
                <input type="text" class="form-control" id="signUpInputName" placeholder="Enter name" name="name" pattern="[A-Za-z]{1,32}" required>
            </div>
            <div class="form-group">
                <label id="signUpInputSurnameLabel" for="signUpInputSurname"><fmt:message key="surname"/></label>
                <input type="text" class="form-control" id="signUpInputSurname" placeholder="Enter surname" name="surname" pattern="[A-Za-z]{1,32}" required>
            </div>
            <div class="form-group">
                <label id="signUpInputEmailLabel" for="signUpInputEmail"><fmt:message key="email"/></label>
                <input type="email" class="form-control" id="signUpInputEmail" aria-describedby="emailHelp" placeholder="Enter email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
            </div>
            <div class="form-group">
                <label id="signUpInputBirthLabel" for="signUpInputBirth"><fmt:message key="birth"/></label>
                <input type="date" class="form-control" id="signUpInputBirth" placeholder="Date of Birth" name="dataOfBirth" min='1900-01-01' max='2100-01-01' required>
            </div>

            <div class="form-group">
                <label id="signUpInputBlockStatusLabel" for="signUpInputBlockStatus"><fmt:message key="account.user-block-status"/></label>
                <select id="signUpInputBlockStatus" class="form-control" name="blocked" >
                    <option><fmt:message key="account.blocked"/></option>
                    <option><fmt:message key="account.unblocked"/></option>
                </select>
            </div>

            <div class="form-group">
                <label id="signUpInputAccessStatusLabel" for="signUpInputBlockStatus"><fmt:message key="account.user-status"/></label>
                <select id="signUpInputAccessStatus" class="form-control" name="access" >
                    <option>User</option>
                    <option>Manager</option>
                </select>
            </div>


            <div class="form-group">
                <label id="signUpInputPasswordLabel" for="signUpInputPassword"><fmt:message key="password"/></label>
                <input type="password" class="form-control" id="signUpInputPassword" placeholder="Password" name="password" required>
            </div>

            <button type="submit" class="btn btn-primary mt-1" id="signUpSubmitBtn"><fmt:message key="sign-up.submit"/></button>
        </form>
    </div>

</main>

<%@ include file="/WEB-INF/JSPF/footer.jspf" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">

</script>

<script type="text/javascript">
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("signUpInputBirth").setAttribute("max", today);
</script>

</body>
</html>
