<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="comment/customer/edit.do" modelAttribute="comment">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="painting" />
	<form:hidden path="creationMoment" />
	<jstl:if test="${comment.parent!=null}">
		<form:hidden path="parent" />
	</jstl:if>

	<acme:textbox code="comment.text" path="text" />

	<acme:submit code="comment.save" name="save" />
	<jstl:choose>
		<jstl:when test="${comment.parent==null}">
			<acme:cancel code="comment.cancel" url="painting/customer/details.do?paintingId=${comment.painting.id}" />
		</jstl:when>
		<jstl:otherwise>
			<acme:cancel code="comment.cancel" url="comment/customer/details.do?commentId=${comment.parent.id}" />
		</jstl:otherwise>
	</jstl:choose>

</form:form>
