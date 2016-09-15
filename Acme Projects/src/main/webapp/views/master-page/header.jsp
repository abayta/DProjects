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
	<img src="images/logo.png" alt="Acme Projects Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.group" /></a>
				<ul>
					<li><a href="group/administrator/create.do"><spring:message code="master.page.createAGroup" /></a></li>
					<li><a href="group/administrator/list-available.do"><spring:message code="master.page.group.list-available" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>											
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.project" /></a>
				<ul>
					<li><a href="project/user/create.do"><spring:message code="master.page.project.create" /></a></li>
					<li><a href="project/user/list-available.do"><spring:message code="master.page.project.list-available" /></a></li>
					<li><a href="project/user/list-my-created.do"><spring:message code="master.page.project.list-my-created" /></a></li>
					<li><a href="project/user/list-vacancy.do"><spring:message code="master.page.project.list-vacancy" /></a></li>
					<li><a href="project/user/list-my-joined.do"><spring:message code="master.page.project.list-my-joined" /></a></li>
					<li><a href="project/user/list-my-followed.do"><spring:message code="master.page.project.list-my-followed-project" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.statusUpdate" /></a>
				<ul>
					<li><a href="statusUpdate/user/list-of-followed-projects.do"><spring:message code="master.page.statusUpdate.followedProject" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>											
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.project" /></a>
				<ul>
					<li><a href="project/list-available.do"><spring:message code="master.page.project.list-available" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li><a href="security/administrator/create.do"><spring:message code="master.page.register.administrator" /></a></li>
					<li><a href="security/user/create.do"><spring:message code="master.page.register.user" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.legal" /></a>
				<ul>
					<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
					<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

