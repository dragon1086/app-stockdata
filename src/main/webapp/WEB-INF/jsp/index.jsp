<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>빌드업 계산기</title>
<style>
html { color-scheme: light dark; }
body { width: 35em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
    <form action="buildup-calculate" method="get" name="calculateRequestFrom">
        <input type="text" name="companyName" size="50"><br>
        <input type="date" name="startDate"/><br>
        <input type="date" name="endDate"/><br>
        <input type="text" name="buildupAmount" size="50"><br>
        <input type="submit" value="전송" onclick="return">
    </form>

</body>
</html>
