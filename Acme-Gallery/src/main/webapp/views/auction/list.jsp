<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table keepStatus="true" name="auctions" id="row" pagesize="5"
	class="displaytag" requestURI="${requestURI}">

	<spring:message code="auction.painting" var="painting" />
	<display:column property="painting.title" title="${painting}"
		sortable="true" />

	<spring:message code="auction.startingPrice" var="startingPrice" />
	<display:column property="startingPrice" title="${startingPrice}"
		sortable="true" />

	<spring:message code="auction.startPeriod" var="startPeriod" />
	<display:column property="startPeriod" title="${startPeriod} "
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>

	<spring:message code="auction.endPeriod" var="endPeriod" />
	<display:column property="endPeriod" title="${endPeriod}"
		sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>

	<display:column>
		<security:authorize access="hasRole('ADMIN')">
			<a href="auction/administrator/details.do?auctionId=${row.id}"> <spring:message
					code="auction.details" />
			</a>
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<a href="auction/customer/details.do?auctionId=${row.id}"> <spring:message
					code="auction.details" />
			</a>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<a href="auction/details.do?auctionId=${row.id}"> <spring:message
					code="auction.details" />
			</a>
		</security:authorize>
	</display:column>

	<security:authorize access="hasRole('ADMIN')">
	</security:authorize>

</display:table>
