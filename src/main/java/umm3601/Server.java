package umm3601;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import umm3601.ToDos.todoController;
import umm3601.user.UserController;

import java.io.IOException;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) throws IOException {
        staticFiles.location("/public");
        Gson gson = new Gson();
        UserController userController = new UserController();
        todoController todoController = new todoController();

        // Simple example route
        get("/hello", (req, res) -> "Hello World");

        // Redirects for the "home" page
        redirect.get("", "/");
        redirect.get("/", "/index.html");

        // Redirect for the "about" page
        redirect.get("/about", "/about.html");

        // Redirect for the Users Form
        redirect.get("/users", "/users.html");

        //Redirects users when they access the kitten page
        redirect.get("/kittens", "/kittens.html");

        //Redirects users when they access the todo page
        redirect.get("/todo","todo.html");

        // List users
        get("api/users", (req, res) -> {
            res.type("application/json");
            return wrapInJson("users", gson.toJsonTree(userController.listUsers(req.queryMap().toMap())));
        });

        // See specific user
        get("api/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params("id");
            return gson.toJson(userController.getUser(id));
        });

        //List todos
        get("api/todos" , (req, res) -> {
            res.type("application/json");
            return wrapInJson("todos", gson.toJsonTree(todoController.listTodo(req.queryMap().toMap())));
        });

        //See a specified todo
        get("api/todos/:id", (req,res) -> {
            res.type("application/json");
            String id = req.params("id");
            return gson.toJson(todoController.getID(id));
        });

    }

    public static JsonObject wrapInJson(String name, JsonElement jsonElement) {
        JsonObject result = new JsonObject();
        result.add(name, jsonElement);
        return result;
    }

}
