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

<b><spring:message code="auction.painting" />:</b>
<jstl:out value="${auction.painting.title}"></jstl:out>
<br />

<b><spring:message code="auction.painting.author" />:</b>
<jstl:out value="${auction.painting.author}"></jstl:out>
<br />

<b><spring:message code="auction.startingPrice" />:</b>
<jstl:out value="${auction.startingPrice}"></jstl:out>
<br />
<br />

<b><spring:message code="auction.creator" />:</b>
<jstl:out value="${auction.creator.name}"></jstl:out>
<br />

<b><spring:message code="auction.startPeriod" />:</b>
<fmt:formatDate value="${auction.startPeriod}" pattern="dd/MM/yyyy HH:mm"/>
<br />

<b><spring:message code="auction.endPeriod" />:</b>
<fmt:formatDate value="${auction.endPeriod}" pattern="dd/MM/yyyy HH:mm"/>
<br />
<br />
	

<security:authorize access="hasRole('CUSTOMER')">
	<security:authentication property="principal.username" var="username"/>
	<jstl:choose>
	<jstl:when test="${!auction.getCreator().getUserAccount().getUsername().equals(username)}">
		<jstl:choose>
			<jstl:when test="${hasBid}">
				<a href="bid/customer/edit.do?auctionId=${auction.id}"><spring:message code="auction.bid.edit" /></a>
			</jstl:when>
			<jstl:otherwise>
				<a href="bid/customer/create.do?auctionId=${auction.id}"><spring:message code="auction.bid" /></a>
			</jstl:otherwise>
		</jstl:choose>
	</jstl:when>
	<jstl:otherwise>
		
		<jstl:if test="${auction.getBids().isEmpty() && notStarted}">
			<a href="auction/customer/edit.do?auctionId=${auction.id}"><spring:message code="auction.edit" /></a>
		</jstl:if>
		<br />
		<br />
		
		<display:table keepStatus="true" name="bids" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
			
			<spring:message code="auction.bid.creationMoment" var="creationMoment" />
			<display:column property="creationMoment" title="${creationMoment}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
			
			<spring:message code="auction.bid.moneyAmount" var="moneyAmount" />
			<display:column property="moneyAmount" title="${moneyAmount}" sortable="true" />
			
			<display:column>
				<a href="bid/customer/details.do?bidId=${row.id}"> <spring:message code="auction.details" /></a>
			</display:column>
			
			<jstl:if test="${end && row.getAuction().getWinner()==null}">
				<display:column>
					<a href="auction/customer/choose.do?bidId=${row.id}&auctionId=${row.auction.id}"><spring:message code="auction.choose" /></a>
				</display:column>
			</jstl:if>
		</display:table>
	</jstl:otherwise>
	</jstl:choose>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<display:table keepStatus="true" name="bids" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
			
		<spring:message code="auction.bid.creationMoment" var="creationMoment" />
		<display:column property="creationMoment" title="${creationMoment}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
		
		<spring:message code="auction.bid.moneyAmount" var="moneyAmount" />
		<display:column property="moneyAmount" title="${moneyAmount}" sortable="true" />
		
	</display:table>
</security:authorize>
