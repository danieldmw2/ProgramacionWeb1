<#import "master.ftl" as layout/>
<@layout.master title="Post">

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Post Content Column -->
        <div class="col-lg-8">

            <!-- Blog Post -->

            <!-- Title -->
            <h1>${postTitle}</h1>

            <!-- Author -->
            <p class="lead">
                by <a>${autor}</a>
            </p>

            <hr>

            <!-- Date/Time -->
            <div class="post-preview">
                <p class="post-meta"><span class="glyphicon glyphicon-time"></span> Posted on ${fecha}</p>
            </div>
            <hr>

            <!-- Post Content -->
            <p class="lead">${contenido}</p>

            <form role="form" method="POST">
                <button type="submit" class="btn btn-primary" name="idArticulo" value="${readmore}" formaction="/valoracionArticulo"><span class="glyphicon glyphicon-thumbs-up"></span> ${likearticulo}</button>
                <button type="submit" class="btn btn-primary" name="idArticulo2" value="${readmore}" formaction="/valoracionArticulo"><span class="glyphicon glyphicon-thumbs-down"></span> ${dislikearticulo}</button>
            </form>

            <hr>

            <!-- Blog Comments -->

            <!-- Comments Form -->
            <div class="well">
                <h4>Leave a comment:</h4>
                <form role="form" method="POST" action="/agregarComentario">
                    <div class="form-group">
                        <textarea class="form-control" name="commentcontent" rows="3"></textarea>
                        <input type="hidden" name="readmore" value="${readmore}">
                    </div>
                    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-comment"></span> Post</button>
                </form>
            </div>

            <hr>

            <!-- Posted Comments -->

            <#list comentarios as c>
                <!-- Comment -->
                <div class="media">
                    <a class="pull-left" href="#">
                    </a>
                    <div class="media-body">
                        <h4 class="media-heading">${c.autor.username}
                        </h4>
                            ${c.comentario}
                        <form method="POST" role="form">
                            <input type="hidden" name="readmore" value="${readmore}">
                            <button type="submit" class="btn btn-primary" name="idComentario" value="${c.id}" formaction="/valoracionComentario"><span class="glyphicon glyphicon-thumbs-up"></span> ${c.likes}</button>
                            <button type="submit" class="btn btn-primary" name="idComentario2" value="${c.id}" formaction="/valoracionComentario"><span class="glyphicon glyphicon-thumbs-down"></span> ${c.dislikes}</button>
                        </form>
                    </div>
                </div>
            </#list>

        </div>

        <!-- Blog Sidebar Widgets Column -->
        <div class="col-md-4">

            <!-- Blog Categories Well -->
            <div class="well">
                <h4>Tags:</h4>
                <div class="row">
                    <div class="col-lg-6">
                        <ul class="list-inline list-tags">
                            <#list tags as tag>
                                <li><button class="btn-danger"><span class="btn-xs">${tag.etiqueta}</span></button></li>
                            </#list>
                        </ul>
                    </div>
                </div>
                <!-- /.row -->
            </div>

        </div>

    </div>
    <!-- /.row -->

    <hr>

</@layout.master>


