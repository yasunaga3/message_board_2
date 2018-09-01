<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	function confirmDestry() {
		if (confirm("本当に削除してよろしいですか？")) {
			document.forms[1].submit();
		}
	}
</script>

<c:import url="../layout/app.jsp">
	<c:param name="content">
		<h2>id：${message.id} のメッセージ編集ページ</h2>

		<form method="post" action="<c:url value='/update' />">
			<c:import url="_form.jsp" />
		</form>

		<p><a href="<c:url value='/index' />">一覧に戻る</a></p>
		<p><a href="#" onclick="confirmDestry();">このメッセージを削除する</a></p>

		<form method="post" action="<c:url value='/destroy' />">
			<input type="hidden" name="_token" value="${_token}" />
		</form>

	</c:param>
</c:import>