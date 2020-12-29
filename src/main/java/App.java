import java.util.*;
import java.util.function.ObjDoubleConsumer;

import spark.ModelAndView;


import models.Departments;
import models.Staff;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.management.openmbean.CompositeData;
import javax.naming.Name;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

//show all Staff and departments
        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

//get:show form to create a new department
        get("/departments/new",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "departments-form-hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new department
        post("/departments/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String description = request.queryParams("description");
            Departments newDepartment = new Departments(description);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
