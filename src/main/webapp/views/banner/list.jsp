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
<form:hidden path="campaignId" />
<display:table pagesize="5" class="displaytag" keepStatus="true" name="banners" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<spring:message code="banner.image" var="imageHeader"/>
	<display:column><img src="${row.image}" width="60" height="60" ></display:column>

	<spring:message code="banner.maxDisplay" var="maxDisplayHeader" />
	<display:column property="maxDisplay" title="${maxDisplayHeader}" sortable="true" />

	<spring:message code="banner.displayed" var="displayedHeader" />
	<display:column property="displayed" title="${displayedHeader}" sortable="true" />
	
</display:table>
<br/>
<div>
	<a href="campaign/sponsor/banner/create.do?campaignId=${campaignId }"><spring:message code="banner.add" /></a>
</div>