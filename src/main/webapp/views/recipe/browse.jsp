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

<!-- Search for a recipe -->

<input type="text" value="" id="textSearch" />
<input type="button" id="buttonSearch"
	value="<spring:message code="recipe.search"/>" />
	
<script type="text/javascript">
	$(document).ready(function(){
		$("#buttonSearch").click(function(){
			window.location.replace('recipe/search.do?key=' + $("#textSearch").val());
		});
		
		$("#buttonSearch").onsubmit(function(){
			window.location.replace('recipe/search.do?key=' + $("#textSearch").val());
		});
	});
</script>

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
	
	<spring:message code="recipe.hint" var="hintHeader"/>
	<display:column property="hint" title="${hintHeader}" sortable="false"/>
	
	
	<spring:message code="recipe.author" var="user"/>
	<display:column property="user.name" title="${user}" sortable="false"/>
	
	<spring:message code="recipe.categories" var="user"/>
	<display:column title="${user}" sortable="false">${nomCategories[row] }</display:column>
	
	<display:column>
		<a href="user/display.do?recipeId=${row.id}"><spring:message code="recipe.view.author" /></a>
	</display:column>
	
	<display:column>
		<a href="recipe/display.do?recipeId=${row.id}"><spring:message code="recipe.view" /></a>
	</display:column>
	
	
</display:table>

