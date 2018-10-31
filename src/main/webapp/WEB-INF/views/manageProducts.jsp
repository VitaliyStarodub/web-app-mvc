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
        <div class="row mb-3">
            <div class="col-md-2">
                <h4>Product Name</h4>
            </div>
            <div class="col-md-2">
                <h4>Price</h4>
            </div>
            <div class="col-md-2">
                <h4>Description</h4>
            </div>
            <div class="col-md-2">
                <h4>Category Name</h4>
            </div>
        </div>
        <c:forEach var="p" items="${products}">
            <div class="row">
                <div class="col-md-2">
                    <h3><c:out value="${p.name}"/></h3>
                </div>
                <div class="col-md-2">
                    <h3><c:out value="${p.price}"/></h3>
                </div>
                <div class="col-md-2">
                    <h3><c:out value="${p.description}"/></h3>
                </div>
                <div class="col-md-2">
                    <h3><c:out value="${p.category.name}"/></h3>
                </div>
                <div class="col-md-4">
                    <a href="<c:url value="/servlet/admin/edit-product?p_id=${p.id}"/>" class="btn btn-outline-warning my-2 my-sm-0 ml-2">Edit</a>
                    <a href="<c:url value="/servlet/admin/delete-product?p_id=${p.id}"/>" class="btn btn-outline-danger my-2 my-sm-0 ml-2">Delete</a>
                </div>
            </div>
        </c:forEach>

        <div>
            <c:if test="${msg_del}">
                <h5 style="color:red">The product was deleted</h5>
            </c:if>
        </div>

    </div>

    <a href="<c:url value="/servlet/admin/add-product"/>" class="btn btn-outline-success my-2 my-sm-0">Add Product</a>


</div>

</body>
</html>
