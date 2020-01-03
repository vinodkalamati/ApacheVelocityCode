<%--
  Created by IntelliJ IDEA.
  User: vinod.kalamati
  Date: 12/20/2019
  Time: 3:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

    <style><%@include file="/WEB-INF/css/style.css"%></style>


    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

    <title>Title</title>
    <script type="text/javascript"><%@include file="/WEB-INF/js/script.js"%></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>


</head>
<body>


<nav class="navbar navbar-inverse" style="background-color: darkslategrey">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                ComingSoon..
            </a>
        </div>
    </div>
</nav>

<div class="jumbotron" style="margin: 5% 10%;background-color: darkslategrey">
    <div class="text-center" style="display: block">

    <form  action="/upload" onsubmit="return validate(this);" method="POST" enctype="multipart/form-data" id="uploadedFiles">
        <label class="label-info">contentFile
        <input class="form-control" type="file" id="file-upload" name="contentFile" required>
        </label>
        <label class="label-info">propertyFile
        <input class="form-control" type="file" id="file-upload2" name="propertiesFile" required>
        </label>
        <input type="submit" class="btn btn-default" value="Upload" />

    </form>

    <input type="submit"  class="btn-default btn" value="test"  ONCLICK="onTest()"/>
    <br />
    <br>

    <a href="/tempo"> <button class="btn btn-primary" id="downloadbutton">
        BrowserView
    </button></a>

    <br>
    <br>
    <form action="/email">
        <input type="email"  name="emailId" />
        <input type="submit" class="btn btn-success" value="emailview">
    </form>

    </div>
</div>

<div id="loader" style="display:none;"></div>


<c:if test="flag">
<div class="alert">
    <span class="closebtn"  onclick="this.parentElement.style.display='none';">&times;</span>
    ${message}
</div>
</c:if>
</body>
</html>
