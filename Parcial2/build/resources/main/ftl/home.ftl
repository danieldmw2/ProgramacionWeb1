<#import "master.ftl" as layout/>
<@layout.master title="Share your images with us!">

<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">

        <form method="GET" action="/upload">
            <button class="btn btn-primary">Crear Album</button>
        </form>

        <#list albumes as album>
            <img src="data:image/png;base64,${album.getImages().get(0).getBase()}"></a>
        </#list>

            <!-- Pager -->
            <ul class="pager">
                <li class="next">
                    <a href="/home?p=${page}">Older Posts →</a>
                </li>
            </ul>

        </div>
    </div>
</div>



</@layout.master>