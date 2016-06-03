<#import "master.ftl" as layout/>
<@layout.master title="Welcome! Please make yourselves at home.">
<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">

        <form method="GET" action="/articleCreation">
            <button class="btn btn-primary">Crear Articulo</button>
        </form>

        <hr>
        <!-- Not yet implemented el ordenar por las fechas. Ademas la lista necesita las entidades.-->
        <#list articulos as articulo>
            <form method="GET" action="/post">
                <div class="post-preview">
                    <p class="post-meta">Posted by <a>${articulo.autor.username}</a> on ${articulo.fecha}</p>


                    <h2 class="post-title">
                        ${articulo.titulo}
                    </h2>
                    <i class="post-subtitle">
                        ${articulo.cuerpo?substring(0,70) + "..."}
                    </i>
                    <br />

                    <button name="readmore" value="${articulo.id}">Read More <span class="glyphicon glyphicon-chevron-right"></span></button>

                    <h4>Tags:</h4>
                    <ul class="list-inline list-tags">
                        <#list articulo.listaEtiquetas as etiqueta>
                            <li><button class="btn-danger"><span class="btn-xs">${etiqueta.etiqueta}</span></button></li>
                        </#list>
                    </ul>

                    <button class="btn btn-primary">Editar Articulo</button>
                    <button name="borrarArticulo" value="${articulo.id}" class="btn btn-primary">Eliminar Articulo</button>

                </div>
                <hr>
            </form>


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
</@layout.master>