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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<security:authorize access="isAuthenticated()">
					
	
	<spring:message code="socialIdentity.picture" var="pictureHeader" />
	<display:column> <img src="${row.picture}" width="60" height="60" ></display:column>
	
	<spring:message code="socialIdentity.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="true" />

	<spring:message code="socialIdentity.socialNetwork" var="socialNetworkHeader" />
	<display:column property="socialNetwork" title="${socialNetworkHeader}" sortable="false" />
	
	<spring:message code="socialIdentity.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}" sortable="true" />

	
	<display:column>
			<a href="socialIdentity/edit.do?socialIdentityId=${row.id}"><spring:message code="socialIdentity.edit" /></a>
	</display:column>
</security:authorize>
</display:table>

<input type="button" name="create"
			value="<spring:message code="socialIdentity.create" />"
			onclick="javascript: window.location.replace('socialIdentity/create.do')" />
<br />
