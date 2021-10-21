<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>빌드업 계산기</title>
<style>
html { color-scheme: light dark; }
body { width: 50em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
    <form action="buildup-calculate" method="get" name="calculateRequestFrom">
        <h1>오리지널 빌드업 시뮬레이션 계산기</h1>
        <p></p>
        <p>매일 종가로 주식을 매수하면, 투입금액 대비 얼마나 수익날 지 계산하는 프로그램입니다.</p>
        <p></p>
        <p>데이터는 지금 2017.10.13 ~ 2021.10.15 까지 있습니다. (수정주가 반영되어 있습니다.)</p>
        <p>차분히 템포조절하며 데이터를 2000년까지 쌓을 예정입니다.. 모두 성투하십쇼!</p>
        기업 이름 : <input type="text" name="companyName" size="50"><br>
        시작 날짜 : <input type="date" name="startDate"/><br>
        매도 날짜 : <input type="date" name="endDate"/><br>
        빌드업 금액 : <input type="text" name="buildupAmount" size="50"><br>
        <input type="submit" value="전송" onclick="return">
    </form>

</body>
</html>
