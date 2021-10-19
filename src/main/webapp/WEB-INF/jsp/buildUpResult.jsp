<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<body>

<div class="container">
    <h1>빌드업 결과</h1>
    <div>
        <c:choose>
            <c:when test="${isError== 'true'}">
                <h1>${errorMessage}</h1>
            </c:when>
            <c:otherwise>
                <h1>수익률 : ${earningRate}%</h1>
                <h1>수익금액 : ${earningAmount}원</h1>
                <h1>현재 평가금액 : ${totalAmount}원</h1>
            </c:otherwise>
        </c:choose>
    </div>

</div>

</body>
</html>