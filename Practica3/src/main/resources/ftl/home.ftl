<#import "master.ftl" as layout/>
<@layout.master title="Hello">
<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">

        <!-- Not yet implemented el ordenar por las fechas. Ademas la lista necesita las entidades.-->
        <#list items as i>
            <form method="GET" action="/post">
                <div class="post-preview">
                    <p class="post-meta">Posted by <a href="#">${user}</a> on ${date}</p>

                    <h2 class="post-title">
                        Man must explore, and this is exploration at its greatest
                    </h2>
                    <i class="post-subtitle">
                        ${summary?substring(0,70) + "..."}
                    </i>
                    <br />

                    <button class="btn btn-primary">Read More <span class="glyphicon glyphicon-chevron-right"></span></button>

                    <h4>Tags:</h4>

                    <ul class="list-inline list-tags">
                        <#list items as item>
                            <li><button class="btn-danger"><span class="btn-xs">${blogs}</span></button></li>
                        </#list>
                    </ul>

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