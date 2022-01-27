<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>Login-out</title>
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
            padding-top: 15%;
            margin: auto;
            color: #142a3b;
        }
        .mainBody a{
            display: block;
            float: left;
            margin: 5px;
        }
        .ui-button{
            color: #fff;
            display: block;
            height: 50px;
            width: 30px;
            padding: 0 15px;
            text-transform: uppercase;
            text-decoration: none;
            line-height: 50px;
            transition: transform .1s;
        }
        .ui-button:hover{
            transform: scale(1.1);
            background-color: rgba(122, 110, 110, 0.26);
        }
        .logout{
            color: white;
            width: 150px;
            height: 150px;
            margin: auto;
            border: 3px solid rgba(0, 0, 0, 0);
            padding: 10px;
            border-radius: 5px 20px 5px;
            transition: transform .5s;
        }
        .logout:hover{
            transform: scale(1.05);
        }

        .meme{
            width: 256px;
            height: 144px;
            background-size:cover;
            display: block;
            position: fixed;
            bottom: -10px;
            left: 0;
        }
        .meme.active{
            opacity: 0.0;
            transform: scale(0.9);
            transition: all 0.5s ease;
        }
    </style>
</head>
<body>

<nav >
    <ul>
        <li><a id="home_HTML" href="/home">Home</a></li>
    </ul>
    <div class="right-nav">
        <ul>
            <li><a id="changeLanguage_HTML" href="/changeLanguage">ENG/UA</a></li>
        </ul>
    </div>
</nav>



<div class="mainBody">
    <div class="logout">
        <h2 id="sure_HTML">Are you sure?</h2>
        <a id="loginOutButton_HTML" class="ui-button" href="/home/logOut" onmouseover="hoverOnOutBut()" onmouseout="hoverOffOutBut()">Yes</a>
        <a id="backHomeButton_HTML" class="ui-button" href="/home">No</a>
    </div>
</div>

<img id="meme"class="meme" srcset="https://i.imgur.com/MbgBFKz.png">

<script type="text/javascript">

    let links = ["https://i.postimg.cc/wxy4KZzp/2.png","https://i.postimg.cc/4y6zfzwz/3.png","https://i.postimg.cc/tRWdNjyY/1.png","https://c.tenor.com/k4TgrD7WDaUAAAAC/the-rock-face.gif"];

    var audio = new Audio();
    audio.src = "http://codeskulptor-demos.commondatastorage.googleapis.com/descent/bomb.mp3";


    document.getElementById("meme").classList.add("active");
    function hoverOnOutBut() {
        document.getElementById("meme").classList.remove("active");
        audio.play();
    }
    function hoverOffOutBut() {
        document.getElementById("meme").classList.add("active");
        setTimeout(() => { document.getElementById("meme").setAttribute("srcset",links[Math.floor(Math.random()*links.length)]); }, 2300);

    }


    let lang=["home_HTML","sure_HTML","loginOutButton_HTML","backHomeButton_HTML"];
    let langEng =["Home","Are you sure?","Yes","No"];
    let langUa =["Додому","Ви впевнені?","Так","Ні"];
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
</body>
</html>
