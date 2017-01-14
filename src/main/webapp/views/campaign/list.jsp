<%--
 * list.jsp
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

<display:table pagesize="5" class="displaytag" keepStatus="true" name="campaigns" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->

	<spring:message code="campaign.startDate" var="startDateHeader" />
	<display:column title="${startDateHeader}" sortable="true" ><fmt:formatDate value="${row.startDate }" pattern="dd/MM/yyyy" /></display:column>

	<spring:message code="campaign.endDate" var="endDateHeader" />
	<display:column title="${endDateHeader}" sortable="true" ><fmt:formatDate value="${row.endDate }" pattern="dd/MM/yyyy" /></display:column>
	
	<security:authorize access="hasRole('SPONSOR')">
	
		<spring:message code="campaign.banner" />
		<display:column>
			<a href="campaign/sponsor/banner/list.do?campaignId=${row.id}"><spring:message code="campaign.banner"/></a>
		</display:column>
		
		<spring:message code="campaign.delete" />
		
		<display:column>
			<jstl:if test="${current.before(row.startDate) }">
				<a href="campaign/sponsor/delete.do?campaignId=${row.id }"><spring:message code="campaign.delete" /></a>
			</jstl:if>
		</display:column>
		
		<spring:message code="campaign.edit" />
		<display:column>
			<jstl:if test="${current.before(row.startDate) }">
				<a href="campaign/sponsor/edit.do?campaignId=${row.id }"><spring:message code="campaign.edit" /></a>
			</jstl:if>
		</display:column>
		
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:choose>
				<jstl:when test="${row.starred}">
					<a href="administrator/campaign/unstarred.do?campaignId=${row.id}">
						<spring:message code="campaign.unstarred"/>
					</a>
				</jstl:when>
				<jstl:otherwise>
					<a href="administrator/campaign/starred.do?campaignId=${row.id}">
						<spring:message code="campaign.starred"/>
					</a>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
		<spring:message code="campaign.monthlyBill.create"/>
		<display:column>
			<a href="administrator/monthlybill/create.do?campaignId=${row.id }"><spring:message code="campaign.monthlyBill.create"/></a>
		</display:column>
	</security:authorize>
	
</display:table>