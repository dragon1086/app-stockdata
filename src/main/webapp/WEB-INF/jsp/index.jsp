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
        <h1>오리지널 빌드업 시뮬레이션 계산기</h1>
        <p>데이터는 2000.01.01 ~ 2021.10.15 까지 있습니다. (수정주가 반영되어 있습니다.)</p>
        기업 이름 : <input type="text" name="companyName" size="50"><br>
        시작 날짜 : <input type="date" name="startDate"/><br>
        매도 날짜 : <input type="date" name="endDate"/><br>
        빌드업 금액 : <input type="text" name="buildupAmount" size="50"><br>
        <input type="submit" value="전송" onclick="return">
    </form>

</body>
</html>
