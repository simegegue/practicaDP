<%--
 * browse.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Search for a user -->

<input type="text" value="" id="textSearch" />
<input type="button" id="buttonSearch"
	value="<spring:message code="user.search"/>" />
	
<script type="text/javascript">
	$(document).ready(function(){
		$("#buttonSearch").click(function(){
			window.location.replace('user/search.do?key=' + $("#textSearch").val());
		});
		
		$("#buttonSearch").onsubmit(function(){
			window.location.replace('user/search.do?key=' + $("#textSearch").val());
		});
	});
</script>
<!-- Listing grid -->
<fieldset>
		<legend align="left">
			<spring:message code="user.browse" />
		</legend>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="users" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true"/>
	
	<spring:message code="user.surname" var="surnameHeader"/>
	<display:column property="surname" title="${surnameHeader}" sortable="false"/>
	
	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false"/>
	
	<spring:message code="user.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="false"/>
	
	<spring:message code="user.postalAddress" var="postalAddressHeader"/>
	<display:column property="postalAddress" title="${postalAddressHeader}" sortable="false"/>
	
	<display:column>
		<a href="user/displayProfile.do?userId=${row.id }"><spring:message code="user.viewProfile" /></a>
	</display:column>
	<security:authorize access="hasRole('USER')">
		<display:column>
		<jstl:choose>
		<jstl:when test="${empty row.followers && row.userAccount.username!=pageContext.request.remoteUser}">
			<a href="user/userFollow.do?userId=${row.id }"><spring:message code="user.follow" /></a>
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="contains" value="false" />
			<jstl:forEach items="${row.followers}" var="follower">
				<jstl:if test="${follower.userAccount.username==pageContext.request.remoteUser}">
					<a href="user/userUnfollow.do?userId=${row.id }"><spring:message code="user.unfollow" /></a>
						<jstl:set var="contains" value="true" />
				</jstl:if>
			</jstl:forEach>
			<jstl:if test="${contains==false && row.userAccount.username!=pageContext.request.remoteUser}">
				<a href="user/userFollow.do?userId=${row.id }"><spring:message code="user.follow" /></a>
				
			</jstl:if>
		</jstl:otherwise>
		</jstl:choose>
		</display:column>		
	</security:authorize>
	
	<security:authorize access="hasRole('NUTRITIONIST')">
		<display:column>
		<jstl:choose>
		<jstl:when test="${empty row.followers && row.userAccount.username!=pageContext.request.remoteUser}">
			<a href="user/nutritionistFollow.do?userId=${row.id }"><spring:message code="user.follow" /></a>
		</jstl:when>
		<jstl:otherwise>
		<jstl:set var="contains" value="false" />
			<jstl:forEach items="${row.followers}" var="follower">
					<jstl:if test="${follower.userAccount.username==pageContext.request.remoteUser}">
						<a href="user/nutritionistUnfollow.do?userId=${row.id }"><spring:message code="nutritionist.unfollow" /></a>
						<jstl:set var="contains" value="true" />
					</jstl:if>
			</jstl:forEach>
			<jstl:if test="${contains==false && row.userAccount.username!=pageContext.request.remoteUser}">
					<a href="user/nutritionistFollow.do?userId=${row.id }"><spring:message code="user.follow" /></a>
			</jstl:if>
		</jstl:otherwise>
	</jstl:choose>
		</display:column>			
	</security:authorize>
</display:table>
</fieldset>
<fieldset>
		<legend align="left">
			<spring:message code="nutritionist.browse" />
		</legend>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="nutritionists" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	<spring:message code="nutritionist.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true"/>
	
	<spring:message code="nutritionist.surname" var="surnameHeader"/>
	<display:column property="surname" title="${surnameHeader}" sortable="false"/>
	
	<spring:message code="nutritionist.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="false"/>
	
	<spring:message code="nutritionist.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="false"/>
	
	<spring:message code="nutritionist.postalAddress" var="postalAddressHeader"/>
	<display:column property="postalAddress" title="${postalAddressHeader}" sortable="false"/>
	
	<display:column>
		<a href="nutritionist/displayProfile.do?userId=${row.id }"><spring:message code="nutritionist.viewProfile" /></a>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
		<jstl:choose>
		<jstl:when test="${empty row.followers && row.userAccount.username!=pageContext.request.remoteUser}">
			<a href="user/followNutritionist.do?userId=${row.id }"><spring:message code="user.follow" /></a>
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="contains" value="false" />
			<jstl:forEach items="${row.followers}" var="follower">
				<jstl:if test="${follower.userAccount.username==pageContext.request.remoteUser}">
					<a href="user/userUnfollow.do?userId=${row.id }"><spring:message code="user.unfollow" /></a>
						<jstl:set var="contains" value="true" />
				</jstl:if>
			</jstl:forEach>
			<jstl:if test="${contains==false && row.userAccount.username!=pageContext.request.remoteUser}">
				<a href="user/followNutritionist.do?userId=${row.id }"><spring:message code="user.follow" /></a>
				
			</jstl:if>
		</jstl:otherwise>
		</jstl:choose>
		</display:column>		
	</security:authorize>
	
	<security:authorize access="hasRole('NUTRITIONIST')">
		<display:column>
		<jstl:choose>
		<jstl:when test="${empty row.followers && row.userAccount.username!=pageContext.request.remoteUser}">
			<a href="user/followUser.do?userId=${row.id }"><spring:message code="user.follow" /></a>
		</jstl:when>
		<jstl:otherwise>
		<jstl:set var="contains" value="false" />
			<jstl:forEach items="${row.followers}" var="follower">
					<jstl:if test="${follower.userAccount.username==pageContext.request.remoteUser}">
						<a href="user/nutritionistUnfollow.do?userId=${row.id }"><spring:message code="nutritionist.unfollow" /></a>
						<jstl:set var="contains" value="true" />
					</jstl:if>
			</jstl:forEach>
			<jstl:if test="${contains==false && row.userAccount.username!=pageContext.request.remoteUser}">
					<a href="user/followUser.do?userId=${row.id }"><spring:message code="nutritionist.follow" /></a>
			</jstl:if>
		</jstl:otherwise>
	</jstl:choose>
		</display:column>			
	</security:authorize>
	
</display:table>
</fieldset>

