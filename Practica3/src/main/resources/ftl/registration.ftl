<#import "master.ftl" as layout/>
<@layout.master title="Hello">

<!-- Page Content -->
<div class="container">

    <div class="row">
        <!-- Blog Post Content Column -->
        <div class="col-lg-8">

            <form role="form" action="post" class="inline">

                <div class="post-preview">
                    <h3 class="post-title">Para registrarse favor de llenar los campos que se presentan a continuacion (Campos con <span style="color: red">*</span> son mandatorios):</h3>
                </div>

                <hr>

                <div class="row">
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                        Nombre de Usuario:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <input name="username" type="text" required>
                    </div>
                </div>

                <div class="row">


                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                        Nombre:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                        <input name="name" type="text" required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                        Contraseña:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <input name="password" oninvalid="setCustomValidity('La cantidad de caracteres debe ser mayor a 0')" oninput="setCustomValidity('')" type="text" required>
                    </div>
                </div>

                <hr>
                <div class="row">
                    <div class="col-lg-offset-4 col-lg-2 col-md-offset-4 col-md-2 col-sm-offset-6 col-sm-4 col-xs-12">
                        <button type="submit" class="btn btn-primary"><span class="glyphicon gly"></span>Submit</button>
                    </div>
                </div>

            </form>

        </div>

    </div>

</div>

</@layout.master>