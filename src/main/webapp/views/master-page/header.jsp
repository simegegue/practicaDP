<%--
 * header.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme Pad-Thai Co., Inc." height="250px"/>
</div>

<div>
	<ul id="jMenu">
	
		<li><a class="fNiv"><spring:message	code="master.page.list" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="masterClass/browse.do"><spring:message code="master.page.masterClass.browse" /></a></li>	
				<li><a href="recipe/browse.do"><spring:message code="master.page.recipe.browse" /></a></li>
				<security:authorize access="hasRole('USER')">
					
				</security:authorize>
				<security:authorize access="hasRole('NUTRITIONIST')">
						<li><a href="user/followingN.do"><spring:message code="master.page.user.follow" /></a></li>
						<li><a href="recipe/stream.do"><spring:message code="master.page.recipe.stream" /></a></li>
				</security:authorize>
				<li><a href="user/browse.do"><spring:message code="master.page.user.browse" /></a></li>		
				<li><a href="contest/browse.do"><spring:message code="master.page.contest.browse" /></a></li>		
			</ul>
		</li>
		
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a href="cook/register.do"><spring:message code="master.page.administrator.cook.register" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.administrator.contest" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/contest/list.do"><spring:message code="master.page.administrator.contest.list" /></a></li>
					<li><a href="administrator/contest/create.do"><spring:message code="master.page.administrator.contest.create" /></a></li>				
				</ul>
			</li>
			<li><a href="administrator/category/list.do"><spring:message code="master.page.administrator.category.list" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.administrator.campaign" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/campaign/list.do"><spring:message code="master.page.administrator.campaign.list" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.administrator.monthlybill" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/monthlybill/listall.do"><spring:message code="master.page.administrator.monthlybill.listAll" /></a></li>
					<li><a href="administrator/monthlybill/listunpaid.do"><spring:message code="master.page.administrator.monthlybill.unpaid" /></a></li>				
				</ul>
			</li>
			<li><a href="administrator/fee/edit.do"><spring:message code="master.page.administrator.fee.edit" /></a></li>
			<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
			<li><a href="administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>
			<li><a href="administrator/spamWord/list.do"><spring:message code="master.page.administrator.spamWord.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('COOK')">
			<li><a class="fNiv"><spring:message	code="master.page.cook" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="cook/masterClass/browse.do"><spring:message code="master.page.cook.masterClass.browse" /></a></li>
					<li><a href="cook/masterClass/create.do"><spring:message code="master.page.cook.masterClass.create" /></a></li>					
				</ul>
			</li>
			<li><a href="cook/edit.do"><spring:message code="master.page.cook.edit" /></a></li>			
		</security:authorize>
				
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user.edit" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/edit.do"><spring:message code="master.page.user.edit" /></a></li>	
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="recipe/listing.do"><spring:message code="master.page.recipe.listing" /></a></li>
					<li><a href="recipe/create.do"><spring:message code="master.page.recipe.create" /></a></li>
					<li><a href="user/following.do"><spring:message code="master.page.user.follow" /></a></li>
					<li><a href="recipe/stream.do"><spring:message code="master.page.recipe.stream" /></a></li>
				</ul>
			</li>
				</security:authorize>		
				
		<security:authorize access="hasRole('NUTRITIONIST')">
			<li><a href="nutritionist/edit.do"><spring:message code="master.page.nutritionist.edit" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.nutritionist.curricula" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="nutritionist/curricula/display.do"><spring:message code="master.page.nutritionist.curricula.display" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.nutritionist.ingredient" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="nutritionist/ingredient/list.do"><spring:message code="master.page.nutritionist.ingredient.list" /></a></li>
					<li><a href="nutritionist/ingredient/create.do"><spring:message code="master.page.nutritionist.ingredient.create" /></a></li>	
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.nutritionist.property" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="nutritionist/property/list.do"><spring:message code="master.page.nutritionist.property.list" /></a></li>
					<li><a href="nutritionist/property/create.do"><spring:message code="master.page.nutritionist.property.create" /></a></li>	
				</ul>
			</li>
		</security:authorize>		
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.campaigns" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="campaign/sponsor/list.do"><spring:message code="master.page.sponsor.campaign.list" /></a></li>
					<li><a href="campaign/sponsor/create.do"><spring:message code="master.page.sponsor.campaign.create" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.monthlyBills" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsor/monthlybill/listall.do"><spring:message code="master.page.sponsor.monthllyBills.listAll" /></a></li>
					<li><a href="sponsor/monthlybill/listunpaid.do"><spring:message code="master.page.sponsor.monthllyBills.listUnpaid" /></a></li>					
				</ul>
			</li>
			<li><a href="sponsor/edit.do"><spring:message code="master.page.sponsor.edit" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="sponsor/register.do"><spring:message code="master.page.actor.registerSponsor" /></a></li>
			<li><a class="fNiv" href="user/register.do"><spring:message code="master.page.user.register" /></a></li>
			<li><a class="fNiv" href="nutritionist/register.do"><spring:message code="master.page.nutritionist.register" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"><spring:message code="master.page.message.messages" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="folder/actor/listall.do"><spring:message code="master.page.message.list"/></a></li>
					<li><a href="message/actor/send.do"><spring:message code="master.page.message.send"/></a></li>
				</ul>
			</li>
			<li><a href="socialIdentity/browse.do"><spring:message code="master.page.socialIdentity.browse" /></a></li>
		
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

