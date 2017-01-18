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
	name="reviews" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->

	<spring:message code="review.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<spring:message code="review.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="false" />
	
	<spring:message code="review.valoration" var="valorationHeader" />
	<display:column property="valoration" title="${valorationHeader}" sortable="true" />
	
	<spring:message code="review.authoredMoment" var="authoredMomentHeader" />
	<display:column title="${authoredMomentHeader}"	sortable="true"><fmt:formatDate value="${row.authoredMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>
	
	<spring:message code="review.critic" var="criticHeader" />
	<display:column property="critic" title="${criticHeader}" sortable="true" />
	
	<spring:message code="review.recipe" var="recipeHeader" />
	<display:column property="recipe" title="${recipeHeader}" sortable="true" />
	
	<jstl:if test="${review.critic.userAccount.username != pageContext.request.remoteUser}">
	<display:column>
		<a href="review/edit.do?reviewId=${row.id}"><spring:message code="review.edit"/></a>
	</display:column>
	</jstl:if>
</display:table>
<br/>
		<input type="button" name="create"
				value="<spring:message code="review.create" />"
				onclick="javascript: window.location.replace('review/create.do')" />
