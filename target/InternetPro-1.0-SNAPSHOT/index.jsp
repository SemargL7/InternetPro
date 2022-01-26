<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<!DOCTYPE html>
<html>
<head>
    <title>Today: <m:today/></title>
    <style type="text/css">
        body {
            color: #B1D0E0;
            background-color: #1A374D;
        }
        .mainBody{
            padding-top: 50px;
            margin: 0;
            list-style: none;
            text-transform: uppercase;
            text-decoration: none;
        }
        .startForm{
            width: 300px;
            height: 300px;
            margin: auto;
            text-align: center;
            border-radius: 5px 75px 5px;
            box-shadow: 10px 5px 5px #6998AB;
            background: #B1D0E0;
        }
        .elements{
            padding-top: 100px;
            margin-left: auto;
            margin-right: auto;
            width: 100px;
            height: 100px;
        }
        .elements a{
            text-align: center;
            text-transform: uppercase;
            text-decoration: none;
            height: 50px;
            width: 100px;
            display: block;
            background: #6998AB;
            color: black;
            margin: 3px;
            text-transform: uppercase;
            text-decoration: none;
            line-height: 50px;
            border-radius: 5%;
        }
        .elements a:hover{
            background: #406882;
        }
    </style>
</head>
<body>

<h2>Today: <m:today/></h2>

<div class="mainBody">
    <div class="startForm">
        <div class="elements">
            <li><a id="login_HTML" href="/login">Login</a></li>
            <li><a id="register_HTML" href="/register">Register</a></li>
        </div>
    </div>
</div>

</body>
</html>