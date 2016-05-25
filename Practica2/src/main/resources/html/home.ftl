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
            <li><a href="\insert">Nuevos Estudiantes</a></li>
        </ul>
        <div class="uk-navbar-flip">
            <a href="#my-id" class="uk-navbar-toggle uk-visible-small" data-uk-offcanvas="{target:'#my-id'}"></a>
        </div>
    </div>
</nav>

<div id="my-id" class="uk-offcanvas">
    <div class="uk-offcanvas-bar">
        <ul class="uk-nav uk-nav-offcanvas" data-uk-nav>
            <li><a href="\insert">Nuevos Estudiantes</a></li>
        </ul>
    </div>
</div>

<!-- Needed for padding at top of body, to look more like Bootstrap's example -->
<br>

<div class="uk-container uk-container-center">
    ${table}
</div>
</body>
</html>