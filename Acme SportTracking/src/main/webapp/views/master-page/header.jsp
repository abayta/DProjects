<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme SportTracking Co., Inc." />
</div>

<div>
	<ul id="jMenu">	

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="event/list-available.do"><spring:message code="master.page.events" /></a></li>
			<li><a href="event/list-available-onGoing.do"><spring:message code="master.page.events.onGoing" /></a></li>
			<li><a href="event/list-all-order.do"><spring:message code="master.page.events.all.order" /></a></li>
			<li><a href="route/list-all-order.do"><spring:message code="master.page.route.all.order" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li><a href="security/administrator/create.do"><spring:message code="master.page.register.administrator" /></a></li>
					<li><a href="security/participant/create.do"><spring:message code="master.page.register.participant" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.admin" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="event/administrator/list-available.do"><spring:message code="master.page.events" /></a></li>
					<li><a href="event/administrator/list-created.do"><spring:message code="master.page.my-events" /></a></li>
					<li><a href="event/administrator/list-available-onGoing.do"><spring:message code="master.page.events.onGoing" /></a></li>
					<li><a href="event/administrator/list-created-onGoing.do"><spring:message code="master.page.my-events.onGoing" /></a></li>
					<li><a href="event/administrator/create.do"><spring:message code="master.page.createAEvent" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.route" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="route/administrator/list-created-order.do"><spring:message code="master.page.my.routes" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.calculate" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/calculate.do"><spring:message code="master.page.calculate.rating" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.profile.logout" /> </a></li>											
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('PARTICIPANT')">
			<li><a class="fNiv"><spring:message	code="master.page.participant" /></a>
				<ul>
					<li><a href="event/participant/list-actives.do"><spring:message code="master.page.events"/></a></li>
					<li><a href="event/participant/listOnGoing.do"><spring:message code="master.page.events.onGoing"/></a></li>
					<li><a href="event/participant/list-joined.do"><spring:message code="master.page.events.joined"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.assessment" /></a>
				<ul>
					<li><a href="assessmentRoute/participant/list.do"><spring:message code="master.page.assessment"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.profile.logout" /> </a></li>											
				</ul>
			</li>
		</security:authorize>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

