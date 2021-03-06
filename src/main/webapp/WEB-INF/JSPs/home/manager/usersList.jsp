<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/JSPF/directive/page.jspf" %>
<%@ include file="/WEB-INF/JSPF/directive/taglib.jspf" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Manager</title>
    <link rel = "icon" href =
            "https://img.icons8.com/fluency-systems-filled/48/000000/create-icon.png"
          type = "image/x-icon">
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


<main class="mt-1">
    <div class="container-fluid w-75 p-3">

        <div class="d-flex flex-row-reverse">
            <form action="/home/register" method="get">
                <input class="btn btn-dark ms-1" title="Add User" type="submit" value="✚" />
            </form>
        </div>

        <table class="table table-light">
            <thead class="table-dark">
            <tr>
                <th scope="col">#</th>
                <th id="thName" scope="col"><fmt:message key="name"/></th>
                <th id="thSurname" scope="col"><fmt:message key="surname"/></th>
                <th id="thDate" scope="col"><fmt:message key="birth"/></th>
                <th id="thEmail" scope="col"><fmt:message key="email"/></th>
                <th id="thBalance" scope="col"><fmt:message key="table.user.balance"/></th>
                <th id="thBlocked" scope="col"><fmt:message key="table.user.blocked"/></th>
                <th id="thUserStatus" scope="col"><fmt:message key="table.user.status"/></th>
                <th id="thAction" scope="col"><fmt:message key="opportunity"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${listUser}">

                <tr>
                    <td>
                        <c:out value="${user.id}" />
                    </td>
                    <td>
                        <c:out value="${user.name}" />
                    </td>
                    <td>
                        <c:out value="${user.surname}" />
                    </td>
                    <td>
                        <c:out value="${user.dateOfBirth}" />
                    </td>
                    <td>
                        <c:out value="${user.email}" />
                    </td>
                    <td>
                        <c:out value="${user.balance}" />
                    </td>
                    <td>
                        <c:out value="${user.blocked}" />
                    </td>
                    <td>
                        <c:out value="${user.getUserAccess().toString()}" />
                    </td>
                    <td style="font-size: large;"><a class="btn btn-outline-dark" href="/home/blockSwitcher?id=<c:out value='${user.id}' />">
                        <c:choose>
                            <c:when test="${user.blocked == true}">
                                <fmt:message key="table.user.manager.action.block"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="table.user.manager.action.unblock"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <nav>
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="/home/usersList?page=<c:out value='${page>1?page-1:page}' />" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach begin="1" end="${paginationMax+1}" varStatus="loop">
                    <c:if test="${loop.index == loop.begin || loop.index == loop.end || (loop.index >= page-3 && loop.index <= page+3)}">
                        <li class="page-item">
                            <a class="page-link" href="/home/usersList?page=<c:out value='${loop.index}' />">${loop.index}<br/></a>
                        </li>
                    </c:if>
                </c:forEach>

                <li class="page-item">
                    <a class="page-link" href="/home/usersList?page=<c:out value='${paginationMax+1 >= page+1? page+1:page}' />" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</main>

<%@ include file="/WEB-INF/JSPF/footer.jspf" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>