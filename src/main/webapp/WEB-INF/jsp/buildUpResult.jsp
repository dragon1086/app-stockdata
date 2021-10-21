<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<body>

<div class="container">
    <h1>빌드업 결과</h1>
    <p></p>
    <div>
        <c:choose>
            <c:when test="${isError== 'true'}">
                <h1>${errorMessage}</h1>
            </c:when>
            <c:otherwise>
                <h1>수익률 : <fmt:formatNumber value="${earningRate}" pattern="#,###.00" />%</h1>
                <h1>수익금액 : <fmt:formatNumber value="${earningAmount}" pattern="#,###" />원</h1>
                <h1>총 투입금액 : <fmt:formatNumber value="${sumOfPurchaseAmount}" pattern="#,###" /></h1>
                <h1>현재 평가금액 : <fmt:formatNumber value="${totalAmount}" pattern="#,###" />원</h1>
            </c:otherwise>
        </c:choose>
    </div>

</div>

</body>
</html>