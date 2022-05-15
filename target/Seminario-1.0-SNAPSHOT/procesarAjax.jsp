<%-- 
    Document   : procesarAjax
    Created on : 12/05/2022, 10:18:33 a.m.
    Author     : Diana Lucia Figueroa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Gramatica"%>
<%@page import="modelo.Palabra"%>
<jsp:useBean id="negocio" class="controlador.Normalizacion" scope="session"></jsp:useBean>
<% 
    String variableTerminal = request.getParameter("variableTerminal");
    String variableNoTerminal = request.getParameter("variableNoTerminal");
    String variableInicial = request.getParameter("variableInicial");
    String producciones = request.getParameter("producciones");
    
    //negocio.crearGramatica(variableTerminal, variableNoTerminal , variableInicial, producciones);  
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Normalización</title>
    </head>
    <body class="bg-primary">   
        <div id="botonesGramatica" class="col-6">
                                
            <div class="row">
                <input type="submit" value="Eliminar Variables Inutiles" class="btn btn-success">     
            </div></br>

            <div class="row">
                <input type="submit" value="Eliminar Variables Inalcanzables" class="btn btn-success">                                   
            </div></br>

            <div class="row">
                <input type="submit" value="Eliminar Variables Nulas" class="btn btn-success">                                   
            </div></br>

            <div class="row">
                <input type="submit" value="Eliminar Variables Unitarias" class="btn btn-success">
            </div></br>
            
        </div>
        
    </body>
</html>
