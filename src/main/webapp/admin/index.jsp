<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>管理画面 | 高速バス予約</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
</head>
<body>
<%@ include file="/include/header.jspf" %>
<div class="container">
  <h1>サイト管理者画面</h1>

  <h2 class="all_company">企業アカウント一覧</h2>
  <table>
    <tr>
      <th>ID</th><th>会社名</th><th>ログインID</th><th>操作</th>
    </tr>
    <c:forEach var="c" items="${companies}">
      <tr>
        <td>${c.id}</td>
        <td>${c.companyName}</td>
        <td>${c.loginId}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline">
            <input type="hidden" name="action" value="deleteCompany">
            <input type="hidden" name="id" value="${c.id}">
            <button type="submit" class="btn" onclick="return confirm('本当に削除しますか？');">削除</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty companies}">
      <tr><td colspan="4">登録された企業はありません。</td></tr>
    </c:if>
  </table>

  <h2>新規企業アカウント作成</h2>
  <form class="inline" action="${pageContext.request.contextPath}/admin" method="post">
    <input type="hidden" name="action" value="createCompany">
    <div>
      <label>会社名</label><br>
      <input type="text" name="companyName" required>
    </div>
    <div>
      <label>ログインID</label><br>
      <input type="text" name="loginId" required>
    </div>
    <div>
      <label>パスワード</label><br>
      <input type="password" name="password" required>
    </div>
    <div><button type="submit">作成</button></div>
  </form>

  <hr>

  <h2>全バス便一覧</h2>
  <table>
    <tr>
      <th>ID</th><th>会社</th><th>出発地</th><th>到着地</th>
      <th>出発</th><th>到着</th><th>種別</th><th>空席/定員</th><th>操作</th>
    </tr>
    <c:forEach var="t" items="${trips}">
      <tr>
        <td>${t.id}</td>
        <td>${t.companyName}</td>
        <td>${t.fromArea}</td>
        <td>${t.toArea}</td>
        <td>${t.departureTime}</td>
        <td>${t.arrivalTime}</td>
        <td>${t.type}</td>
        <td>${t.availableSeats}/${t.capacity}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline">
            <input type="hidden" name="action" value="deleteTrip">
            <input type="hidden" name="id" value="${t.id}">
            <button type="submit" class="btn" onclick="return confirm('この便を削除しますか？');">削除</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty trips}">
      <tr><td colspan="9">登録されたバス便はありません。</td></tr>
    </c:if>
  </table>
  <h2>予約一覧</h2>
  <table>
    <tr>
      <th>ID</th>
      <th>便ID</th>
      <th>名前</th>
      <th>電話番号</th>
      <th>予約コード</th>
      <th>操作</th>
    </tr>
    <c:forEach var="r" items="${reservations}">
      <tr>
        <td>${r.id}</td>
        <td>${r.tripId}</td>
        <td>${r.name}</td>
        <td>${r.phone}</td>
        <td>${r.publicCode}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline">
            <input type="hidden" name="action" value="cancelReservation"/>
            <input type="hidden" name="id" value="${r.id}"/>
            <button type="submit"">キャンセル</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty reservations}">
      <tr><td colspan="6">予約はありません。</td></tr>
    </c:if>
  </table>
</div>
</body>
</html>