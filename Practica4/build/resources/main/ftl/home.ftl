<#import "master.ftl" as layout/>
<@layout.master title="Welcome! Please make yourselves at home.">
<!-- main.Practica3tica3 Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">

        <form method="GET" action="/articleCreation">
            <button class="btn btn-primary">Crear Articulo</button>
        </form>

        <hr>

        <#list articulos as articulo>
                <form method="GET" action="/post">
                    <div class="post-preview">
                        <p class="post-meta">Posted by <a>${articulo.autor.username}</a> on ${articulo.fecha}</p>

                        <h2 class="post-title">
                            ${articulo.titulo}
                        </h2>
                        <i class="post-subtitle">
                            <#assign cuerpo=articulo.cuerpo>
                            <#if cuerpo?length &lt; 70>
                                ${cuerpo}
                            <#else>
                                ${cuerpo?substring(0,70) + "..."}
                            </#if>
                        </i>
                        <br />

                        <button name="readmore" value="${articulo.id}">Read More <span class="glyphicon glyphicon-chevron-right"></span></button>

                        <h4>Tags:</h4>
                        <ul class="list-inline list-tags">
                            <#list articulo.listaEtiquetas as etiqueta>
                                <li><button name="idetiqueta" value="${etiqueta.etiqueta}" formaction="/mostrarArticulosEtiqueta" class="btn-danger"><span class="btn-xs">${etiqueta.etiqueta}</span></button></li>
                            </#list>
                        </ul>

                        <button name="modificarArticulo" formaction="/modificarArticulo" value="${articulo.id}" class="btn btn-primary">Editar Articulo</button>
                        <button name="borrarArticulo" formmethod="POST" formaction="/borrarArticuloPost" value="${articulo.id}" class="btn btn-primary">Eliminar Articulo</button>

                    </div>
                    <hr>
                </form>

        </#list>


            <!-- Pager -->
            <ul class="pager">
                <li class="next">
                    <button nameformaction="">Older Posts →</a>
                </li>
            </ul>
        </div>
    </div>
</div>

<hr>
</@layout.master>