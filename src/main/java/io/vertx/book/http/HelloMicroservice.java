package io.vertx.book.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HelloMicroservice extends AbstractVerticle {

    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.get("/").handler(this::hello);

        router.get("/:name").handler(this::hello);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }

    private void hello(RoutingContext context) {
        String message = "hello";
        if (context.pathParam("name") != null) {
            message += " " + context.pathParam("name");
        }

        JsonObject json = new JsonObject().put("message", message);
        context.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(json.encode());
    }
}

