<#import "master.ftl" as layout/>
<!DOCTYPE html>
<@layout.master title="ListarUsuario">

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-10 col-md-11 col-sm-12 col-xs-12">
            <div class="panel-primary">
                <div class="panel-heading">Listar Estudiantes</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <table class="table table-hover table-responsive table-bordered table">
                                <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Nombre</th>
                                    <th>Apellidos</th>
                                    <th>Password</th>
                                    <th>Autor</th>
                                    <th>Admin</th>
                                </tr>
                                </thead>
                                <tbody>

                                    <#list users as e>
                                    <tr onclick="document.location='/zonaAdmin/deleteUser?id=${e.username}';">
                                        <div class="row">
                                            <div class="col-lg-9 col-md-9">
                                                <td id="username">${e.username}</td>
                                                <td id="nombre">${e.nombre}</td>
                                                <td id="apellidos">${e.apellidos}</td>
                                                <td id="password">${e.password}</td>
                                                <td id="autor">${e.autor?c}</td>
                                                <td id="admin">${e.administrator?c}</td>
                                            </div>
                                        </div>
                                    </tr>

                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</@layout.master>