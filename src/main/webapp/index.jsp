<%-- 
    Document   : index
    Created on : 9/05/2022, 4:26:07 p.m.
    Author     : Diana Lucia Figueroa
--%>

<%@page import="modelo.Gramatica"%>
<%@page import="modelo.Palabra"%>
<%@page import="controlador.Normalizacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="../../../../../Seminario II/Segunda Entrega/unoCero (1)/unoCero/css/index.css" rel="stylesheet" type="text/css"/>
<jsp:useBean id="negocio" class="controlador.Normalizacion" scope="session"></jsp:useBean>
<% 

    Gramatica gramatica = (Gramatica) request.getSession().getAttribute("gramatica");
    Normalizacion normalizacion = (Normalizacion) request.getSession().getAttribute("normalizacion");

    String inicial = "A";
    String terminales = "0,1,2";
    String noTerminales = "A,B,C,D,E,F,G,H";
    String sigma = "A->B1CD/GF/BDG/1"+
                        " B->CDE/DCE/F1/0/λ"+
                        " C->DE/E/F/DE0E1/2"+
                        " D->B/CDE/DD1/0"+
                        " E->B1B2B/DE2/1"+
                        " F->DBEE2/F1/2"+
                        " H->BCD1/2";

    //String sigma2 = (String) request.getSession().getAttribute("sigma");

    /*if(gramatica!=null)
        System.out.println(":index.jsp -------> gramatica.getSigma()="+gramatica.getSigma().toString());
    else
        System.out.println("gramatica nula");*/

    
%>
<!DOCTYPE html>
<html lang="en">
<head>
    
    <title>soluciones UnoCero</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/aos.css">

    <!-- MAIN CSS -->
    <link rel="stylesheet" href="css/tooplate-gymso-style.css">
    <link rel="stylesheet" href="css/index.css">

</head>

<body data-spy="scroll" data-target="#navbarNav" data-offset="50">

    <!-- MENU BAR -->
    <nav class="navbar navbar-expand-lg fixed-top">
        <div class="container">

            <a class="navbar-brand" href="index.html">Soluciones UnoCero</a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-lg-auto">
                    <li class="nav-item">
                        <a href="#home" class="nav-link smoothScroll">Inicio</a>
                    </li>

                    <li class="nav-item">
                        <a href="#about" class="nav-link smoothScroll">Conceptos</a>
                    </li>

                    <li class="nav-item">
                        <a href="#normalizacion" class="nav-link smoothScroll">Normalización</a>
                    </li>

                    <li class="nav-item">
                        <a href="#chomsky" class="nav-link smoothScroll">Chomsky</a>
                    </li>

                    <li class="nav-item">
                        <a href="#contact" class="nav-link smoothScroll">Greibach</a>
                    </li>

                    <li class="nav-item">
                        <a href="#ejercicio" class="nav-link smoothScroll">Ejercicio</a>
                    </li>
                </ul>

                <!-- <ul class="social-icon ml-lg-3">
                    <li><a href="https://fb.com/tooplate" class="fa fa-facebook"></a></li>
                    <li><a href="#" class="fa fa-twitter"></a></li>
                    <li><a href="#" class="fa fa-instagram"></a></li>
                </ul> -->
            </div>

        </div>
    </nav>


    <!-- HERO -->
    <section class="hero d-flex flex-column justify-content-center align-items-center" id="home">

        <div class="bg-overlay"></div>

        <div class="container">
            <div class="row">

                <div class="col-lg-8 col-md-10 mx-auto col-12">
                    <div class="hero-text mt-5 text-center">

                        <h1 class="text-white" data-aos="fade-up" data-aos-delay="50">BIENVENIDOS
                        </h1>
                        <h2 class="text-white" data-aos="fade-up" data-aos-delay="300">En esta pagina web encontrarán
                            todo lo relacionado con los conceptos de normalización de una gramatica, Forma Normal de
                            chomsky y Forma Normal de greibach. Además se dispondrán de una herramienta para el
                            desarrollo de ejercicios de normalizacion de gramaticas al momento de llevarlas a chomsky y
                            greibach.</h2>

                        <a href="#about" class="btn custom-btn bordered mt-3" data-aos="fade-up"
                            data-aos-delay="100">leer mas</a>
                    </div>
                </div>

            </div>
        </div>
    </section>

    <!-- ABOUT -->
    <section class="about section vh-100 vw-100 " id="about">

        <div class="container text-light">

            <div class="row text-dark dflex justify-content-center mb-4">
                <h2>Conceptos Básicos</h2>
            </div>

            <div class="row">

                <div class="col-3">
                    <div class="card h-100 corners">
                        <img src="https://tuataras.net/wp-content/uploads/Persona-programando.png"
                            class="card-img-top corners-top" alt="">
                        <div class="card-body bg-dblue corners-bottom">
                            <h5 class="card-title">Sistema</h5>
                            <p class="card-text text-light">Conjunto ordenado de normas y procedimientos que regulan el
                                funcionamiento de un grupo o colectividad.</p>
                        </div>
                    </div>
                </div>

                <div class="col-3">
                    <div class="card h-100 corners">
                        <img src="https://piperlab.es/wp-content/uploads/2020/09/lenguajes-de-programacion.jpg"
                            class="card-img-top corners-top h-50" alt="">
                        <div class="card-body bg-sblue corners-bottom">
                            <h5 class="card-title">Lenguaje</h5>
                            <p class="card-text text-light">Conjunto ordenado de normas y procedimientos que regulan el
                                funcionamiento de un grupo o colectividad.</p>
                        </div>
                    </div>
                </div>

                <div class="col-3">

                    <div class="card h-100 corners">
                        <img src="https://rokket-website-file-manager.s3.amazonaws.com/photos/shares/2_-Blog-Banner-Template.jpg"
                            class="card-img-top corners-top h-50" alt="">
                        <div class="card-body bg-sblue corners-bottom">
                            <h5 class="card-title">Gramatica</h5>
                            <p class="card-text text-light">Conjunto ordenado de normas y procedimientos que regulan el
                                funcionamiento de un grupo o colectividad.</p>
                        </div>
                    </div>

                </div>

                <div class="col-3">
                    <div class="card h-100 corners">
                        <img src="https://img.freepik.com/vector-gratis/fuente-cyber-tech-alfabeto-vectorial-estilo-esquema-contorno-letras-numeros-producto-digital-logotipo-sistema-seguridad-pancarta-monograma-poster-diseno-tipografico_159025-810.jpg"
                            class="card-img-top corners-top h-50" alt="">
                        <div class="card-body bg-blue corners-bottom">
                            <h5 class="card-title">Alfabeto</h5>
                            <p class="card-text text-light">Conjunto ordenado de normas y procedimientos que regulan el
                                funcionamiento de un grupo o colectividad.</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </section>


    <!-- CLASS -->  
    <section class="about section vh-100 vw-100" id="normalizacion">

        <div class="container">

            <div class="row text-dark dflex justify-content-center mb-5">
                <h2>Normalizacion</h2>
            </div>

            <div class="row">
                <div class="col-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">¿Que es normalización?</h5>
                            <p class="card-text">
                            <ul>
                                <li>Eliminar producciones inutiles</li>
                                <li>Eliminar producciones inalcanzables</li>
                                <li>Eliminr producciones nulas</li>
                                <li>Eliminar producciones unitarias</li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="col-8">
                    <div class="row">
                        <iframe class="w-100" height="315" src="https://www.youtube.com/embed/Jcr_3z8WbcA"
                            title="YouTube video player" frameborder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowfullscreen></iframe>
                    </div>

                    <div class="row">
                        <div class="d-flex mt-3">
                            <p class="">Pueder normalizar la gramatica que quieras aqui</p>
                            <button class="btn btn-primary">Ejercicio</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>


    <section class="about section vh-100 vw-100" id="chomsky">
        <div class="container">

            <div class="row text-dark dflex justify-content-center mb-4">
                <h2>Chomsky</h2>
            </div>

            <div class="row">
                
                <div class="col-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Chomsky</h5>
                            <p class="card-text">
                                Una gramática formal está en Forma normal de Chomsky si todas sus reglas de
                                producciónson de alguna de las siguientes formas: <br> <br>  A->BC <br> A->ALPHA <br> <br> Donde A, B, C son simbolos no terminales (o variables) y ? es un simbolo terminal. DondeA,B,C son simbolos no terminales (o variables) y ? es un simbolo terminal.
                            </p> 
                        </div>
                    </div>
                </div>

                <div class="col-8">
                    <div class="row">
                        <iframe class="w-100" height="400" src="https://www.youtube.com/embed/Jcr_3z8WbcA"
                            title="YouTube video player" frameborder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                            allowfullscreen></iframe>
                    </div>

                    <div class="row">
                        <div class="d-flex mt-3">
                            <p class="">Pueder normalizar la gramatica que quieras aqui</p>
                            <button class="btn btn-primary">Ejercicio</button>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </section>

    <!-- Ejercicio -->
    <section class="about section vh-100 vw-100" id="ejercicio">
        <div class="container">
            <div class="row">
                
                <div class="col-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Registrar Gramatica</h5>
                            
                            <form id="frmGramatica" name="frmGramatica" 
                              action="GuardarGramatica.do" method="post">

                              <div class="form-group">
                                    <label for="variableInicial" class="form-label"> Variable Inicial: </label>
                                    <input type="text" name="variableInicial" id="variableInicial" placeholder="Digite su variable Inicial" required class="form-control" value="<%= inicial!=null?inicial:"" %>">
                                </div>

                                <div class="form-group">
                                    <label for="variableTerminal"  class="form-label">Variables terminales: </label>
                                    <input type="text" name="variableTerminal" id="variableTerminal" placeholder="Digite su variable terminal" required class="form-control" value="<%= terminales!=null?terminales:"" %>">
                                </div>

                                <div class="form-group">
                                    <label for="variablesNoTerminales" class="form-label">Variables no terminales: </label>
                                    <input type="text" name="variablesNoTerminales" id="variablesNoTerminales" placeholder="Digite sus variables no terminales" required class="form-control" value="<%= noTerminales!=null?noTerminales:"" %>">
                                </div>
                                
                                <div class="form-group">
                                    <label for="producciones" class="form-label">Sigma: </label>
                                    <input type="text" name="producciones" id="producciones" placeholder="Digite las Producciones" required class="form-control" value="<%= sigma!=null?sigma:"" %>">
                                </div>
                                
                                <div class="form-group">
                                    <input type="submit" value="Guardar Gramatica" class="btn btn-success">
                                    <input type="button" value="Cancelar" onclick="location.href='#home'" class="btn btn-success">                       
                                </div>

                            </form>
                        </div>
                    </div>
                </div>


                <div class="col-4">
                
                    <div id="opcionesGramatica">
                        <div id="eliminarVariablesInutilesId"> 
                            <%-- <%=sigma2!=null? sigma2 : ""%>  --%>
                            <%=gramatica!=null && gramatica.sigmaToString()!=null? gramatica.sigmaToString() : ""%>
                            <%-- <%=gramatica!=null && gramatica.getSigma().toString()!=null? gramatica.getSigma().toString() : ""%> --%>
                        </div>
                        <div id="EliminarVariablesInalcanzables"> </div> 
                        <div id="EliminarVariablesNulas"> </div> 
                        <div id="EliminarVariablesUnitarias"> </div>
                    </div> 
                
                </div>

                <div class="col-4 ">
                
                    <form action="EliminarVariablesInutiles.do" method="post">
                        <input type="submit" class="btn btn-primary d-block" value="Eliminar variables Inutiles">
                    </form>

                    <form action="EliminarVariablesInalcanzables.do" method="post">
                        <input type="submit" class="btn btn-primary d-block" value="Eliminar variables Inalcanzables">
                    </form>
                
                </div>

                <%-- <form action="EliminarVariablesInutiles.do" method="post">
                    
                        <input type="submit" class="btn btn-primary d-block" value="Eliminar variables Inutiles">
                        <button class="btn btn-primary d-block mt-2">Eliminar variables Inalcanzables</button>
                        <button class="btn btn-primary d-block mt-2">Eliminar variables Nulas</button>
                        <button class="btn btn-primary d-block mt-2">Eliminar variables Unitarias</button>
                    
                </form> --%>

            </div>
        </div>
    </section>
    
    <section>
        
    </section>

    <!-- SCRIPTS -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/aos.js"></script>
    <script src="js/smoothscroll.js"></script>
    <script src="js/custom.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>

        function eliminarInutiles(){
        
        }
    
        $(function(){
            
            

        });

        
        function nada(){
            console.log("hola soy nada");
        }

    </script>

</body>

</html>