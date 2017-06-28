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
		JpaController controller = SelectTable.getController();
		TableModel model;
		
		public String showFlat(String id_flat){
			StringBuilder sb = new StringBuilder();
			model = controller.getModel("Flat");
			for (int i = 0; i < model.getRowCount(); i++) {
				String line = String.format("|%-3d|%-33s|%-4d|%-10.2f|%-5d|%-12.2f|", model.getValueAt(i, 0),
			model.getValueAt(i, 1), model.getValueAt(i, 2), model.getValueAt(i, 3), model.getValueAt(i, 4),model.getValueAt(i, 5));
				line = line.replaceAll(" ", "&nbsp;");
				if(id_flat != null){
					if(id_flat.equals(String.valueOf(model.getValueAt(i, 0))))
						sb.append("<option selected value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");
					else
						sb.append("<option value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");			
				}
				else{
					sb.append("<option value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");
				}
			}
			return sb.toString();
		}
		
		public String showClient(String id_client){
			StringBuilder sb = new StringBuilder();
			model = controller.getModel("Client");
			for (int i = 0; i < model.getRowCount(); i++) {
				String line = String.format("|%-3d|%-12s|%-10s|%-20s|%-13s|%-9s|", model.getValueAt(i, 0),model.getValueAt(i, 1), model.getValueAt(i, 2), model.getValueAt(i, 3), model.getValueAt(i, 4),model.getValueAt(i, 5));
				line = line.replaceAll(" ", "&nbsp;");
				if(id_client != null){
					if(id_client.equals(String.valueOf(model.getValueAt(i, 0))))
						sb.append("<option selected value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");
					else
						sb.append("<option value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");			
				}
				else{
					sb.append("<option value="+model.getValueAt(i, 0)+"><tt>"+line+"</tt></option>");
				}
			}
			return sb.toString();
		}
	%>
	<% 
		String table = (String)request.getAttribute("table");
		String className = (String)request.getAttribute("className");
		String act = (String)request.getAttribute("act");
		String str = (String)request.getAttribute("str");
		String id_flat = (String)request.getAttribute("id_flat");
		String id_client = (String)request.getAttribute("id_client");
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Web-додаток</title>
	<link rel="stylesheet" href="style.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
	<%
		if (act != null && className != null) {
			if (act.equals("add"))
				out.write("document.getElementById('select" + className + "').click();");
			else if (act.equals("delete"))
				out.write("document.getElementById('delete" + className + "').click();");
			else if (act.equals("update"))
				out.write("document.getElementById('change" + className + "').click();");
	}%>
	$("#flatBlock,#clientBlock,#orderBlock,#managerBlock").dialog({autoOpen:false, modal:true, resizable:false, show:'fade', draggable : false});
	$("#flatBlock").dialog({title:'Квартира',width:400});
	$("#clientBlock").dialog({title:'Клієнт',width:400});
	$("#orderBlock").dialog({title:'Замовлення',width:650});
	$("#managerBlock").dialog({title:'Менеджер',width:650});
						$("#addTab a").click(function(event) {
							block = $(this).attr('href');
							$(block).dialog('open');	
						});
						$("#add,#edit,#delete").mouseenter(function(event) {
							var id = "#" + $(this).attr('id');
							$(id).css("color", "yellowgreen");
							$(id).css("background-color", "black");
							var s = id + "Tab";
							$(s).slideDown(200, function() {
								$(s).css("height", "30px");
							});
						});
						$("#add,#edit,#delete").mouseleave(function() {
							var id = "#" + $(this).attr('id');
							var s = id + "Tab";
							if (!$(s).is(":hover")) {
								$(s).slideUp(200);
								$(id).css("background-color", "#222");
								$(id).css("color", "#9d9d9d");
							}
						});
						$("#addTab,#editTab,#deleteTab").mouseleave(function() {
							var id = "#" + $(this).attr('id');
							var s = id.substr(0, id.length - 3);
							if (!$(s).is(":hover")) {
								$(id).slideUp(200);
								$(s).css("background-color", "#222");
								$(s).css("color", "#9d9d9d");
							}
						});
						<%
						if (act != null && className != null) {
								if(str != null){
									out.write(str);
								}
						}%>
					});
	</script>
</head>
<body>
	<header>
		<h1>Web-додаток</h1>
		<h2>БД "Продаж квартир"</h2>
	</header>
	<nav class="page-navigation">
		<div>
			<ul>
				<li><a id="add" href="#">Додати</a></li>
				<li><a id="edit" href="#">Редагувати</a></li>
				<li><a id="delete" href="#">Видалити</a></li>
			</ul>
		</div>
	</nav>
	<div id="addTab" class="page-navigationOne" hidden>
		<div class="container">
			<ul>
				<li><a href="#flatBlock">Квартира</a></li>
				<li><a href="#orderBlock">Замовлення</a></li>
				<li><a href="#clientBlock">Кліент</a></li>
				<li><a href="#managerBlock">Менеджер</a></li>
			</ul>
		</div>
	</div>
	<div id="editTab" class="page-navigationOne" hidden>
		<div class="container">
			<ul>
				<li><a id="changeFlat"
					href="SelectTable?className=Flat&act=ChangeAction">Квартира</a></li>
				<li><a id="changeOrder"
					href="SelectTable?className=Order&act=ChangeAction">Замовлення</a></li>
				<li><a id="changeClient"
					href="SelectTable?className=Client&act=ChangeAction">Кліент</a></li>
				<li><a id="changeManager"
					href="SelectTable?className=Manager&act=ChangeAction">Менеджер</a></li>
			</ul>
		</div>
	</div>
	<div id="deleteTab" class="page-navigationOne" hidden>
		<div class="container">
			<ul>
				<li><a id="deleteFlat"
					href="SelectTable?className=Flat&act=DeleteAction">Квартира</a></li>
				<li><a id="deleteOrder"
					href="SelectTable?className=Order&act=DeleteAction">Замовлення</a></li>
				<li><a id="deleteClient"
					href="SelectTable?className=Client&act=DeleteAction">Кліент</a></li>
				<li><a id="deleteManager"
					href="SelectTable?className=Manager&act=DeleteAction">Менеджер</a></li>
			</ul>
		</div>
	</div>
	<div id="flatBlock" hidden>
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<div>Адреса<input type="text" name="address"></div>
			<div>№ квартири<input type="number" name="number"></div>
			<div>Площа<input type="text" name="square"></div>
			<div>Кіль. кімнат<input type="number" name="countRoom"></div>
			<div>Ціна<input type="text" name="price"></div>
			<input type="hidden" name="className" value="Flat"> 
			<input name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="clientBlock" hidden>
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<div>Прізвище<input type="text" name="surname"></div>
			<div>Ім'я<input type="text" name="name"></div>
			<div>Ел.пошта<input type="text" name="email"></div>
			<div>Телефон<input type="text" name="phone"></div>
			<div>Номер паспорта<input type="text" name="passport"></div>
			<input type="hidden" name="className" value="Client"> 
			<input name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="orderBlock" hidden>
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<p>Виберіть квартиру</p>
			<pre>  ID               Адреса               №кв.  Площа     Кімн.    Ціна</pre>
			<select name="selectFlat" style="font-family: monospace">
				<% out.write(showFlat(id_flat));%>
			</select>
			<p>Виберіть клієнта</p>
			<pre>  ID   Прізвище       Ім'я         Ел.пошта         Телефон      Паспорт</pre>
			<select name="selectClient" style="font-family: monospace">
				<% out.write(showClient(id_client));%>
			</select> 
			<input type="hidden" name="className" value="Order">
			<input class="Ok" name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="managerBlock" hidden>
		<form action="AddRow" method="GET">
			<input type="hidden" name="id"> 
			<div>Прізвище<input type="text" name="surname"></div>
			<div>Ім'я<input type="text" name="name"></div>
			<div>Ел.пошта<input type="text" name="email"></div>
			<div>Телефон<input type="text" name="phone"></div>
			<p>Виберіть квартиру</p>
			<pre>  ID               Адреса               №кв.   Площа   Кімн.      Ціна</pre>
			<select name="selectFlat" style="font-family: monospace">
				<% out.write(showFlat(id_flat));%>
			</select> 
			<input type="hidden" name="className" value="Manager"> 
			<input class="Ok" name="accept" type="submit" value="OK">
		</form>
	</div>
	<div id="block">
		<div class="choose">
			<div>
				<ul>
					Виберіть таблицю для відображення:
					<li><a id="selectFlat" href="SelectTable?className=Flat&act=AddAction">Квартири</a></li>
					<li><a id="selectOrder" href="SelectTable?className=Order&act=AddAction">Замовлення</a></li>
					<li><a id="selectClient" href="SelectTable?className=Client&act=AddAction">Клієнти</a></li>
					<li><a id="selectManager" href="SelectTable?className=Manager&act=AddAction">Менеджери</a></li>
				</ul>
			</div>
		</div>
		<div id="table">
			<%if(table != null)
				out.write(table.toString());
			%>
		</div>
	</div>
	<footer> 2017 </footer>
</body>
</html>