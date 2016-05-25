<!DOCTYPE html>
<html>
<head>
    <title>Starter template</title>
    <link rel="stylesheet" href="css/uikit.min.css" />
    <!-- Almost Flat style -->
    <link rel="stylesheet" href="css/uikit.almost-flat.min.css" />
    <!-- Gradient style -->
    <!-- <link rel="stylesheet" href="css/uikit.gradient.min.css" /> -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="js/uikit.min.js"></script>
</head>
<body>
<nav class="uk-navbar">
    <div class="uk-container uk-container-center">
        <a href="\home" class="uk-navbar-brand">Practica 2</a>
        <ul class="uk-navbar-nav uk-hidden-small uk-navbar-attached">
            <li><a href="\home">Home</a></li>
        </ul>
        <div class="uk-navbar-flip">
            <a href="#my-id" class="uk-navbar-toggle uk-visible-small" data-uk-offcanvas="{target:'#my-id'}"></a>
        </div>
    </div>
</nav>

<div id="my-id" class="uk-offcanvas">
    <div class="uk-offcanvas-bar">
        <ul class="uk-nav uk-nav-offcanvas" data-uk-nav>
            <li><a href="\home">Home</a></li>
        </ul>
    </div>
</div>

<!-- Needed for padding at top of body, to look more like Bootstrap's example -->
<br>

<div class="uk-container uk-container-center">
    <form class="uk-form" action="/insertDB" method="post">

        <fieldset data-uk-margin>
            <legend>Agregar un nuevo estudiante</legend>
            <input name="matricula" type="number" placeholder="Matricula" min="0" max="99999999">
            <input name="nombre" type="text" placeholder="Nombre">
            <input name="apellidos" type="text" placeholder="Apellidos">
            <input name="telefono" type="number" placeholder="Telefono" min="0" max="9999999999">
            <button class="uk-button">Insert</button>
        </fieldset>

    </form>
</div>
</body>
</html>