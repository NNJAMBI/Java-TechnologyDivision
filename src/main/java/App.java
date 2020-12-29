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
            return new ModelAndView(model, "departments-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new department
        post("/departments/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            Departments newDepartment = new Departments(name);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:show add a new staff form
        get("staff/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post:process add new staff form
        post("/staff", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String description = request.queryParams("description");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            Staff newStaff = new Staff(description, departmentId);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
