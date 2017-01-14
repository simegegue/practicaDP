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

<display:table pagesize="5" class="displaytag" keepStatus="true" name="recipes" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<spring:message code="recipe.picture" var="pictureHeader"/>
	<display:column><img src="${row.picture}" width="60" height="60" ></display:column>

	<spring:message code="recipe.ticker" var="titckerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="true" />

	<spring:message code="recipe.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />

	<spring:message code="recipe.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" sortable="false"  />

	<spring:message code="recipe.authoredMoment" var="authoredMomentHeader" />
	<display:column title="${authoredMomentHeader}"	sortable="true"><fmt:formatDate value="${row.authoredMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>
	
	<spring:message code="recipe.updateMoment" var="updateMomentHeader"/>
	<display:column title="${updateMomentHeader}" sortable="false"><fmt:formatDate value="${row.updateMoment }" pattern="dd/MM/yyyy HH:mm" /></display:column>
	
	<spring:message code="recipe.categories" var="user"/>
	<display:column title="${user}" sortable="false">${nomCategories[row] }</display:column>
	
	<spring:message code="recipe.hint" var="hintHeader"/>
	<display:column property="hint" title="${hintHeader}" sortable="false"/>
	
</display:table>