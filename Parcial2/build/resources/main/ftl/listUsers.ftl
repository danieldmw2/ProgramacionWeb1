<#import "master.ftl" as layout/>
<!DOCTYPE html>
<@layout.master title="ListarUsuario">

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-10 col-md-11 col-sm-12 col-xs-12">
            <div class="panel-primary">
                <div class="panel-heading">Listar Usuarios</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <table class="table table-hover table-responsive table-bordered table">
                                <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>E-mail</th>
                                    <th>Password</th>
                                    <th>Admin</th>
                                </tr>
                                </thead>
                                <tbody>

                                    <#list users as e>
                                    <tr onclick="document.location='/zonaAdmin/deleteUser?username=${e.username}';">
                                        <div class="row">
                                            <div class="col-lg-9 col-md-9">
                                                <td id="username">${e.username}</td>
                                                <td id="email">${e.email}</td>
                                                <td id="password">${e.password}</td>
                                                <td id="admin">${e.isAdministrator()?c}</td>
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