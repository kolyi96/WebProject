<%@page import="servlet.SelectTable"%>
<%@page import="controller.JpaController"%>
<%@page import="java.util.Formatter"%>
<%@page import="javax.swing.table.TableModel"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.apache.jasper.runtime.JspWriterImpl"%>
<%@page import="java.io.Writer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%!
		JpaController cont = new JpaController();
		public String showFlat(){
			TableModel model = cont.getModel("Flat");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < model.getRowCount(); i++) {
				String line = String.format("|%-3d|%-33s|%-4d|%-10.2f|%-5d|%-12.2f|", model.getValueAt(i, 0),
			model.getValueAt(i, 1), model.getValueAt(i, 2), model.getValueAt(i, 3), model.getValueAt(i, 4),model.getValueAt(i, 5));
				line = line.replaceAll(" ", "&nbsp;");
				sb.append("<option value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");
			}
			return sb.toString();
		}
		
		public String showClient(){
			TableModel model = cont.getModel("Client");
	 		StringBuilder sb = new StringBuilder();
			for (int i = 0; i < model.getRowCount(); i++) {
				String line = String.format("|%-3d|%-12s|%-10s|%-20s|%-13s|%-9s|", model.getValueAt(i, 0),model.getValueAt(i, 1), model.getValueAt(i, 2), model.getValueAt(i, 3), model.getValueAt(i, 4),model.getValueAt(i, 5));
				line = line.replaceAll(" ", "&nbsp;");
				sb.append("<option value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");
			}
			return sb.toString();
		}
	%>
	<% 
		session.setAttribute("controller", new JpaController());
		String className = (String)session.getAttribute("className");
		String action = (String)session.getAttribute("action");
	%>
	<meta http-equiv="Content-Type" charset="UTF-8" content="text/html; charset=UTF-8">
	<title>Веб-приложение</title>
	<link rel="stylesheet" href="style.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	<script type="text/javascript">
	<%
	if(className == null){
		session.setAttribute("className", "Empty");
		className = "Empty";
	}
	%>
	var className = "<%=className%>";
	function showTable(obj,action){
		if(obj != '')
			className = obj;
		if(className == "Empty")
			$("#table").replaceWith("<div id='table' align='center'>Виберіть таблицю!!!</div>"); 
		else{
			$.ajax({
		        type: "GET",
		        processData: false,
	            url: "SelectTable",
		        data: "className="+className+"&action="+action,
	            success: function(result){
	            	$("#table").replaceWith("<div id='table'>"+result+"</div>");                                 
	            }, 
	       });
		}
	}
	function addRow(){
		$("input").val("");
		if(className == "Empty")
			$('#table').text('Для начала выберите таблицу!');
		else if(className == "Flat")
			$('#flatBlock').dialog('open');
		else if(className == "Order")
			$('#orderBlock').dialog('open');
		else if(className == "Manager")
			$('#managerBlock').dialog('open');
		else if(className == "Client")
			$('#clientBlock').dialog('open');
	}
	function changeRow(id){
		$.ajax({
	        type: "GET",
	        processData: false,
            url: "ChangeRow",
	        data: "className="+className+"&id="+id,
            success: function(result){
            	var obj = jQuery.parseJSON(result);
            	if(className == "Flat"){
            		$('#flatBlock input[name="id"]').val(obj[0]);
            		$('#flatBlock input[name="address"]').val(obj[1]);
            		$('#flatBlock input[name="number"]').val(obj[2]);
            		$('#flatBlock input[name="square"]').val(obj[3]);
            		$('#flatBlock input[name="countRoom"]').val(obj[4]);
            		$('#flatBlock input[name="price"]').val(obj[5]);
        			$('#flatBlock').dialog('open');
            	}
        		else if(className == "Order"){
        			$('#orderBlock input[name="id"]').val(obj[0]);
        			$('#orderBlock').dialog('open');
        		}
        		else if(className == "Manager"){
        			$('#managerBlock input[name="id"]').val(obj[0]);
            		$('#managerBlock input[name="surname"]').val(obj[1]);
            		$('#managerBlock input[name="name"]').val(obj[2]);
            		$('#managerBlock input[name="phone"]').val(obj[3]);
            		$('#managerBlock input[name="email"]').val(obj[4]);
        			$('#managerBlock').dialog('open');
        		}
        		else if(className == "Client"){
        			$('#clientBlock input[name="id"]').val(obj[0]);
            		$('#clientBlock input[name="surname"]').val(obj[1]);
            		$('#clientBlock input[name="name"]').val(obj[2]);
            		$('#clientBlock input[name="phone"]').val(obj[3]);
            		$('#clientBlock input[name="email"]').val(obj[4]);
            		$('#clientBlock input[name="passport"]').val(obj[5]);
        			$('#clientBlock').dialog('open');
        		}
            }, 
       });
	}
	$(document).ready(function() {
		$("#flatBlock,#clientBlock,#orderBlock,#managerBlock").dialog({autoOpen:false, modal:true, resizable:false, show:'fade', draggable : false});
		$("#flatBlock").dialog({title:'Квартира',width:400});
		$("#clientBlock").dialog({title:'Клієнт',width:400});
		$("#orderBlock").dialog({title:'Замовлення',width:650});
		$("#managerBlock").dialog({title:'Менеджер',width:650});
		$("#add,#edit,#delete").mouseenter(function(event) {
			var id = "#" + $(this).attr('id');
			$(id).css("color", "yellowgreen");
			$(id).css("background-color", "black");
		});
		$("#add,#edit,#delete").mouseleave(function() {
			var id = "#" + $(this).attr('id');
			$(id).css("background-color", "#222");
			$(id).css("color", "#9d9d9d");
		});
		showTable(className,"<%=action%>");
	});
	</script>
</head>
<body>
	<header>
		<h1>Web-додаток</h1>
		<h2>БД "Продаж квартир"</h2>
	</header>
	<nav class="page-navigation">
		<div class="container">
			<ul>
				<li><a id="add" href="#" onclick="addRow();">Додати</a></li>
				<li><a id="edit" href="#" onclick="showTable('','Change');">Редагувати</a></li>
				<li><a id="delete" href="#" onclick="showTable('','Delete');">Видалити</a></li>
			</ul>
		</div>
	</nav>
	<div id="flatBlock" hidden="true">
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<div>Адреса<input type="text" name="address"></div>
			<div>№ квартири<input type="number" name="number"></div>
			<div>Площа<input type="text" name="square"></div>
			<div>Кіль. кімнат<input type="number" name="countRoom"></div>
			<div>Ціна<input type="text" name="price"></div>
			<input name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="clientBlock" hidden="true">
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<div>Прізвище<input type="text" name="surname"></div>
			<div>Ім'я<input type="text" name="name"></div>
			<div>Ел.пошта<input type="text" name="email"></div>
			<div>Телефон<input type="text" name="phone"></div>
			<div>Номер паспорта<input type="text" name="passport"></div>
			<input name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="orderBlock" hidden="true">
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<p>Виберіть квартиру</p>
			<pre>  ID               Адреса               №кв.  Площа     Кімн.    Ціна</pre>
			<select name="selectFlat" style="font-family: monospace">
				<% out.write(showFlat());%>
			</select>
			<p>Виберіть клієнта</p>
			<pre>  ID   Прізвище       Ім'я         Ел.пошта         Телефон      Паспорт</pre>
			<select name="selectClient" style="font-family: monospace">
				<% out.write(showClient());%>
			</select> 
			<input class="Ok" name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="managerBlock" hidden="true">
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<div>Прізвище<input type="text" name="surname"></div>
			<div>Ім'я<input type="text" name="name"></div>
			<div>Ел.пошта<input type="text" name="email"></div>
			<div>Телефон<input type="text" name="phone"></div>
			<p>Виберіть квартиру</p>
			<pre>  ID               Адреса               №кв.   Площа   Кімн.      Ціна</pre>
			<select name="selectFlat" style="font-family: monospace">
				<% out.write(showFlat());%>
			</select> 
			<input class="Ok" name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="block">
		<div class="choose">
			<div>
				<ul>
					Виберіть таблицю для відображення:
					<li><a id="selectFlat" href="#" onclick='showTable("Flat");'>Квартири</a></li>
					<li><a id="selectOrder" href="#" onclick='showTable("Order");'>Замовлення</a></li>
					<li><a id="selectClient" href="#" onclick='showTable("Client");'>Клієнти</a></li>
					<li><a id="selectManager" href="#" onclick='showTable("Manager");'>Менеджери</a></li>
				</ul>
			</div>
		</div>
		<div id="table"></div>
	</div>
	<footer>2017</footer>
</body>
</html>