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

<form:form action="bid/customer/edit.do" modelAttribute="bidForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="auction"/>

	<acme:textbox code="bid.moneyAmount" path="moneyAmount" />
	
	<acme:submit code="bid.save" name="save" />
	<jstl:if test="${bidForm.id != 0}">
		<acme:submitDelete code="bid.delete" name="delete" confirm="bid.confirm.delete" />
	</jstl:if>
	<acme:cancel code="bid.cancel" url="auction/customer/details.do?auctionId=${bidForm.auction.id}" />
	
</form:form>
