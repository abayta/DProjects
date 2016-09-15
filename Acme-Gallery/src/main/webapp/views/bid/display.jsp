<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<b><spring:message code="bid.customer" />:</b>
<jstl:out value="${bid.customer.name}"></jstl:out>
<br />

<b><spring:message code="bid.creationMoment" />:</b>
<fmt:formatDate value="${bid.creationMoment}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="bid.moneyAmount" />:</b>
<jstl:out value="${bid.moneyAmount}"></jstl:out>
<br />
<br />
<security:authorize access="hasRole('CUSTOMER')">
	<security:authentication property="principal.username" var="username"/>
	<jstl:if test="${bid.getAuction().getCreator().getUserAccount().getUsername().equals(username)}">
		<acme:cancel code="painting.cancel" url="/auction/customer/details.do?auctionId=${bid.auction.id}" />
	</jstl:if>
	<jstl:if test="${bid.getAuction().getCreator().getUserAccount().getUsername().equals(username)}">
		
	</jstl:if>
</security:authorize>
