<#macro master title="Hello">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${title}</title>

    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css">

</head>

<body style="padding-top: 65px">

<div class="container">
    <div class="row">
        <nav class="navbar navbar-inverse navbar-custom navbar-fixed-top">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header page-scroll">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>


                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <form method="GET" action="/home">
                        <ul class="nav navbar-nav navbar-left">
                            <li>
                                <a href="/home"> CrossImage </a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="/zonaAdmin"><span class="glyphicon glyphicon-floppy-save"></span> Administración</a>
                            </li>
                            <li>
                                <a href="/sign-up"><span class="glyphicon glyphicon-user"></span> ¡Regístrate!</a>
                            </li>
                            <li>
                                <script language="javascript">
                                    document.forms["myForm"].submit();
                                </script>
                                <#if iniciarSesion == "Cerrar Sesión">
                                    <script language="javascript">

                                        function DoPost(){
                                            $.post("/logout");  //Your values here..
                                        }

                                    </script>
                                        <a href="javascript:DoPost()" ><span class="glyphicon glyphicon-log-out"></span> ${iniciarSesion}</a>
                                <#else>
                                    <a href="/sign-in"><span class="glyphicon glyphicon-log-in"></span> ${iniciarSesion}
                                    </a>
                                </#if>
                            </li>
                        </ul>
                    </form>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->
        </nav>
    </div>
</div>


<!-- Page Header -->
<!-- Set your background image for this header on the line below. -->
<header class="intro-header">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="site-heading">
                </div>
            </div>
        </div>
    </div>
</header>

    <#nested />

<!-- Bootstrap Core JavaScript -->
<script src="/js/bootstrap.min.js"></script>

</body>
</html>
</#macro>