<#import "master.ftl" as layout/>
<@layout.master title="Create your own article here!">


<!-- Page Content -->
<div class="container">

    <div class="row">
        <!-- Blog Post Content Column -->
        <div class="col-lg-8">

            <form role="form" method="POST" action="/modificarArticuloPost" class="inline">

                <div class="post-preview">
                    <h3 class="post-title">En esta página se puede editar el contenido de tus articulos:</h3>
                </div>

                <hr>

                <div class="row">
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        Titulo:
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <input name="titulo" value="" type="text" required>
                    </div>
                </div>
                <br />
                <div class="row">
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        Cuerpo:
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <textarea name="cuerpo" rows="4" cols="50" ></textarea>
                    </div>
                </div>
                <br />
                <div class="row">
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        Tags:
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">

                        <input name="etiquetas" value="" type="text" required>
                    </div>
                </div>

                <hr>
                <div class="row">
                    <div class="col-lg-offset-1 col-lg-3 col-md-offset-1 col-md-2 col-sm-offset-6 col-sm-4 col-xs-12">
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-user"></span> Submit</button>
                    </div>
                </div>

            </form>

        </div>

    </div>

</div>


</@layout.master>