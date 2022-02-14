<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/JSPF/directive/page.jspf" %>
<%@ include file="/WEB-INF/JSPF/directive/taglib.jspf" %>
<html>
<head>
  <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@600;900&display=swap" rel="stylesheet">
  <script src="https://kit.fontawesome.com/4b9ba14b0f.js" crossorigin="anonymous"></script>
  <link rel = "icon" href =
          "https://img.icons8.com/fluency-systems-filled/48/000000/create-icon.png"
        type = "image/x-icon">
  <style>
    body{
      overflow-x: hidden;
      background-color: #30475E;
      opacity: 1;
      background: linear-gradient(135deg, #F0545455 25%, transparent 25%) -25px 0/ 50px 50px, linear-gradient(225deg, #F05454 25%, transparent 25%) -25px 0/ 50px 50px, linear-gradient(315deg, #F0545455 25%, transparent 25%) 0px 0/ 50px 50px, linear-gradient(45deg, #F05454 25%, #30475E 25%) 0px 0/ 50px 50px;
    }

    .mainbox {

      margin: auto;
      height: 600px;
      width: 600px;
      position: relative;
    }

    .err {
      color: #ffffff;
      font-family: 'Nunito Sans', sans-serif;
      font-size: 11rem;
      position:absolute;
      left: 20%;
      top: 8%;
    }

    .far {
      position: absolute;
      font-size: 8.5rem;
      left: 42%;
      top: 15%;
      color: #ffffff;
    }

    .err2 {
      color: #ffffff;
      font-family: 'Nunito Sans', sans-serif;
      font-size: 11rem;
      position:absolute;
      left: 68%;
      top: 8%;
    }

    .msg {
      text-align: center;
      font-family: 'Nunito Sans', sans-serif;
      font-size: 1.6rem;
      position:absolute;
      left: 16%;
      top: 45%;
      width: 75%;
    }

    a {
      text-decoration: none;
      color: white;
    }

    a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div class="mainbox">
  <div class="err">4</div>
  <i class="far fa-question-circle fa-spin"></i>
  <div class="err2">4</div>
  <div class="msg">Maybe this page moved? Got deleted? Is hiding out in quarantine? Never existed in the first place?<p>Let's go <a href="/home">home</a> and try from there.</p></div>
</div>
</body>
</html>
