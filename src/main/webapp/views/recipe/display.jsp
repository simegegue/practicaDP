
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${banner ne 'No banner'}">
	<center>
		<div align="center">
			<img alt="banner" src="${banner }" style="width:730px;height:92px">
		</div>
	</center>
</jstl:if>

<table id="row" class="table">
	
	<tbody>
		<tr>
			<td rowspan="10">
				<img src="${recipe.picture}" width="200" height="200" >
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code = "recipe.ticker"/>
			</th>
			<td>
				<jstl:out value="${recipe.ticker }" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="recipe.title" />
			</th>
			<td>
				<jstl:out value="${recipe.title }" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="recipe.summary" />
			</th>
			<td>
				<jstl:out value="${recipe.summary }" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="recipe.authoredMoment" />
			</th>
			<td>
				<fmt:formatDate value="${recipe.authoredMoment }" pattern="dd/MM/yyyy HH:mm" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="recipe.updateMoment" />
			</th>
			<td>
				<fmt:formatDate value="${recipe.updateMoment }" pattern="dd/MM/yyyy HH:mm" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="recipe.hint" />
			</th>
			<td>
				<jstl:out value="${recipe.hint }" />
			</td>
		</tr>
		<tr>
			<th>
				<spring:message code="recipe.author" />
			</th>
			<td>
				<jstl:out value="${recipe.user.name }" />
			</td>
		</tr>
		<tr bgcolor="lightgrey">
			<th>
				<spring:message code = "recipe.likes"/>
			</th>
			<td>
				<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
				<jstl:out value="${fn:length(recipe.relationLikes)}"/>
			</td>
		</tr>
		<tr bgcolor="lightgrey">
			<th>
				<spring:message code = "recipe.disLikes"/>
			</th>
			<td>
				<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
				<jstl:out value="${fn:length(recipe.relationDislikes)}"/>
			</td>
		</tr>
	</tbody>

</table>

<jstl:if test="${recipe.user.userAccount.username != pageContext.request.remoteUser}">
	<security:authorize access="hasAnyRole('USER','NUTRITIONIST')">
	<div  style="width:980px;height:40px">	
	
		<jstl:set var="containsD" value="false" />
		<jstl:forEach var="item" items="${recipe.relationDislikes}">
	  		<jstl:if test="${item.socialActor.userAccount.username eq pageContext.request.remoteUser}">
	    		<jstl:set var="containsD" value="true" />
	  		</jstl:if>
		</jstl:forEach>
	
	
		<jstl:choose>
			<jstl:when test="${containsD == true }">
				<input type="button" name="removedisLike"
					value="<spring:message code="recipe.undislike" />"
					onclick="javascript: window.location.replace('socialActor/dislike/delete.do?recipeId=${recipe.id}')" 
					style="float: right;padding: 5px 15px; margin: 0 3px 0 3px;" />
			</jstl:when>
			<jstl:otherwise>
				<input type="button" name="adddisLike"
					value="<spring:message code="recipe.dislike" />"
					onclick="javascript: window.location.replace('socialActor/dislike/create.do?recipeId=${recipe.id}')" 
					style="float: right;padding: 5px 15px; margin: 0 3px 0 3px;" />
			
			</jstl:otherwise>
		</jstl:choose>
		
			<jstl:set var="containsL" value="false" />
		<jstl:forEach var="itemL" items="${recipe.relationLikes}">
	  		<jstl:if test="${itemL.socialActor.userAccount.username eq pageContext.request.remoteUser}">
	    		<jstl:set var="containsL" value="true" />
	  		</jstl:if>
		</jstl:forEach>
	
	
		<jstl:choose>
			<jstl:when test="${containsL == true }">
				<input type="button" name="removedisLike"
					value="<spring:message code="recipe.unlike" />"
					onclick="javascript: window.location.replace('socialActor/like/delete.do?recipeId=${recipe.id}')" 
					style="float: right;padding: 5px 15px; margin: 0 3px 0 3px;" />
			</jstl:when>
			<jstl:otherwise>
				<input type="button" name="adddisLike"
					value="<spring:message code="recipe.like" />"
					onclick="javascript: window.location.replace('socialActor/like/create.do?recipeId=${recipe.id}')" 
					style="float: right;padding: 5px 15px; margin: 0 3px 0 3px;" />
			
			</jstl:otherwise>
		</jstl:choose>
		
	</div>
	</security:authorize>
</jstl:if>
<display:table pagesize="10" class="displaytag" keepStatus="true" name="quantities" requestURI="${ requestURI}" id="row">

	<spring:message code="recipe.ingredient.quantity" var="quantity"/>
	<display:column title="${quantity }" property="measure" />
	
	<spring:message code="recipe.ingredient.unit" var="unit"/>
	<display:column title="${unit }" property="unit.name" />
	
	<spring:message code="recipe.ingredient.name" var="name"/>
	<display:column title="${name }" property="ingredient.name" />
	
	<spring:message code="recipe.ingredient.description" var="description"/>
	<display:column title="${description }" property="ingredient.description" />
	
	<security:authorize access="hasRole('USER')">
		
		<jstl:if test="${(not empty user) && (recipe.user eq user) && (recipe.qualified eq false)}">
			<display:column>
				<a href="recipe/quantity/edit.do?quantityId=${row.id }" ><spring:message code="recipe.edit"/></a>
			</display:column>
		</jstl:if>
		
		<jstl:if test="${(not empty user) && (recipe.user eq user) && (recipe.qualified eq false)}">
			<display:column>
				<a href="recipe/quantity/delete.do?quantityId=${row.id }" ><spring:message code="recipe.delete"/></a>
			</display:column>
		</jstl:if>
		
	</security:authorize>
</display:table>
<security:authorize access="hasRole('USER')">
<jstl:if test="${recipe.user.userAccount.username == pageContext.request.remoteUser}">
<input type="button" name="addIngredient"
			value="<spring:message code="recipe.addIngredient" />"
			onclick="javascript: window.location.replace('recipe/quantity/addIngredient.do?recipeId=${recipe.id}')" />
</jstl:if>
</security:authorize>
<br/>
<display:table pagesize="10" class="displaytag" keepStatus="true" name="steps" requestURI="${requestURI }" id="row">
	
	<spring:message code="recipe.step.picture" var="pictureHeader"/>
	<display:column><img src="${row.picture}" width="60" height="60" ></display:column>
	
	<spring:message code="recipe.step.description" var="rsDescription" />
	<display:column title="${rsDescription }" property="description"/>
	
	<spring:message code="recipe.step.hint" var="rsHint" />
	<display:column title="${rsHint }" property="hint"/>
<security:authorize access="hasRole('USER')">	
	<jstl:if test="${recipe.user.userAccount.username == pageContext.request.remoteUser}">
	<display:column><a href="step/edit.do?stepId=${row.id }">
		<spring:message code="step.edit"/></a>
	</display:column>
	</jstl:if>
</security:authorize>
</display:table>
<security:authorize access="hasRole('USER')">
<jstl:if test="${recipe.user.userAccount.username == pageContext.request.remoteUser}">
<input type="button" name="addStep"
			value="<spring:message code="recipe.addStep" />"
			onclick="javascript: window.location.replace('step/create.do?recipeId=${recipe.id}')" />
<br/>
</jstl:if>
</security:authorize>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="comments" requestURI="${requestURI}" id="row">

	<spring:message code="recipe.comment.title" var="rcTitle"/>
	<display:column title="${rcTitle }" property="title"/>
	
	<spring:message code="recipe.comment.author" var="rcAuthor"/>
	<display:column title="${rcAuthor}">
		<jstl:choose>
		<jstl:when test="${row.nutritionist!=null}">
			<jstl:out value="${row.nutritionist.name}"/>
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${row.user.name}"/>
		</jstl:otherwise>
		</jstl:choose>
		</display:column>
	<spring:message code="recipe.comment.text" var="rcText"/>
	<display:column title="${rcText }" property="text"/>

	<spring:message code="recipe.comment.momentCreate" var="rcMomentCreate"/>
	<display:column title="${rcMomentCreate}" sortable="true"><fmt:formatDate value="${row.momentCreate }" pattern="dd/MM/yyyy HH:mm" /></display:column>
	
	<spring:message code="recipe.comment.stars" var="rcStars"/>
	<display:column title="${rcStars }" property="stars" />

<br/>
	
</display:table>

<security:authorize access="hasAnyRole('USER','NUTRITIONIST')">
	<input type="button" name="addComment"
			value="<spring:message code="recipe.addComment" />"
			onclick="javascript: window.location.replace('comment/create.do?recipeId=${recipe.id}')" />
<br/>
</security:authorize>
