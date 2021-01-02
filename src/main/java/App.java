import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.function.ObjDoubleConsumer;

import dao.Sql2oDepartmentsDao;
import dao.Sql2oStaffDao;
import models.Departments;
import models.Staff;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oStaffDao staffDao = new Sql2oStaffDao(sql2o);
        Sql2oDepartmentsDao departmentsDao = new Sql2oDepartmentsDao(sql2o);

//show all Staff in all departments and all departments
        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<Departments> allDepartments = departmentsDao.getAll();
            model.put("departments", allDepartments);
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

        //get:delete all departments and all staff
        get("/departments/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:remove all staff
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific department and all the staff in it
        get("/departments/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToFind = Integer.parseInt((request.params("id")));
            return new ModelAndView(model, "departments-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get:show a form to update a department
        get("/departments/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editDepartment", true);
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post:process a form to update a department
        post("/departments/:id", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            int idOfDepartmentToEdit = Integer.parseInt(request.params("id"));
            String newName = request.queryParams("newDepartmentName");
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get:delete an individual staff
        get("/departments/department_id/staff/:staff_id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToDelete = Integer.parseInt(request.params("staff_id"));
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:show an individual staff that is nested in a department
        get("/departments/:department_id/staff/:staff_id", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            int idOfStaffToFind = Integer.parseInt(request.params("Staff_id"));
            int idOfDepartmentToFind = Integer.parseInt("category_id");
            return new ModelAndView(model, "staff-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get:show a form to update a staff
        get("/staff/:id/edit", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            model.put("editTask", true);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post:process a form to update a staff
        post("/staff/:id", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            int staffToEditId = Integer.parseInt(request.params("id"));
            String newContent = request.queryParams("description");
            int newDepartmentId = Integer.parseInt(request.queryParams("departmentId"));
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
