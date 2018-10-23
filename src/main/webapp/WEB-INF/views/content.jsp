<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mate</title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>

    <style>
        .container2 {
            margin-top: 100px;
            margin-left: 40px;
        }
    </style>

</head>
<body>

<%@include file="header.jsp" %>

<div class="container2">

    <div class="mt-5">
        <a href="<c:url value="/servlet/admin/categories"/>"><h3>Manage Categories</h3></a>
        <a href="<c:url value="/servlet/admin/products"/>"><h3>Manage Products</h3></a>
    </div>


</div>
</body>

</html>