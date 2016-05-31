<!DOCTYPE html>
<html lang="en"><head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${title}</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/clean-blog.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<!-- Navigation -->
<nav class="navbar navbar-default navbar-custom navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form method="GET" action="/home">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="/home"><span class="glyphicon glyphicon-home"></span> Home</a>
                    </li>
                    <li>
                        <!-- Not yet implemented -->
                        <a href="/registro"><span class="glyphicon glyphicon-user"></span> ¡Regístrate!</a>
                    </li>
                    <li>
                        <!-- Not yet implemented -->
                        <a href="iniciarSesion"><span class="glyphicon glyphicon-log-in"></span> Iniciar Sesión</a>
                    </li>
                </ul>
            </form>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Page Header -->
<!-- Set your background image for this header on the line below. -->
<header class="intro-header" style="background-image: url('img/home-bg.jpg')">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="site-heading">
                    <h1>${intro}</h1>
                    <hr class="medium">

                    <span class="subheading">A place where the CS community can share their thoughts with the world</span>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
            <#list items as item>
                <div class="post-preview">
                    <p class="post-meta">Posted by <a href="#">${user}</a> on ${date}</p>

                    <h2 class="post-title">
                        Man must explore, and this is exploration at its greatest
                    </h2>
                    <i class="post-subtitle">
                        ${summary?substring(0,70) + "..."}
                    </i>
                    <br />

                    <form method="GET" action="/post">
                        <button class="btn btn-primary">Read More <span class="glyphicon glyphicon-chevron-right"></span></button>
                    </form>
                </div>
                <hr>
            </#list>

            <!-- Pager -->
            <ul class="pager">
                <li class="next">
                    <a href="#">Older Posts →</a>
                </li>
            </ul>
        </div>
    </div>
</div>

<hr>

<!-- Footer -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <p class="copyright text-muted">Copyright © Computer Science's hideout 2016</p>
            </div>
        </div>
    </div>
</footer>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>





</body></html>