import java.util.*;
import java.util.List;
import java.util.Map;

import dao.Sql2oDepartmentsDao;
import dao.Sql2oStaffDao;
import models.Departments;
import models.Staff;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("port") != null) {
            return Integer.parseInt(processBuilder.environment().get("port"));
        }
        return 4567; //return default localhost port if Heroku-port is not set
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/technologydivision";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oStaffDao staffDao = new Sql2oStaffDao(sql2o);
        Sql2oDepartmentsDao departmentsDao = new Sql2oDepartmentsDao(sql2o);

//show all Staff in all departments and all departments
        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<Departments> allDepartments = departmentsDao.getAll();
            model.put("departments", allDepartments);
            List<Staff> staff = staffDao.getAll();
            model.put("staff", staff);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

//get:show form to create a new department
        get("/departments/new",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> departments = departmentsDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "departments-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new department
        post("/departments/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            Departments newDepartment = new Departments(name);
            departmentsDao.add(newDepartment);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:show add a new staff form
        get("staff/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> departments = departmentsDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post:process add new staff form
        post("/staff", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> allDepartments = departmentsDao.getAll();
            model.put("departments", allDepartments);
            String description = request.queryParams("description");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            Staff newStaff = new Staff(description, departmentId);
            staffDao.add(newStaff);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:delete all departments and all staff
        get("/departments/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            departmentsDao.clearAllDepartments();
            staffDao.clearAllStaff();
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:remove all staff
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            staffDao.clearAllStaff();
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific department and all the staff in it
        get("/departments/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToFind = Integer.parseInt((request.params("id")));
            Departments foundDepartment = departmentsDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartment);
            List<Staff> allStaffByDepartment = departmentsDao.getAllStaffByDepartments(idOfDepartmentToFind);
            model.put("staff", allStaffByDepartment);
            model.put("departments", departmentsDao.getAll());
            return new ModelAndView(model, "departments-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get:show a form to update a department
        get("/departments/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editDepartment", true);
            Departments departments = departmentsDao.findById(Integer.parseInt(request.params("id")));
            model.put("departments", departments);
            model.put("departments", departmentsDao.getAll());
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post:process a form to update a department
        post("/departments/:id", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            int idOfDepartmentToEdit = Integer.parseInt(request.params("id"));
            String newName = request.queryParams("newDepartmentName");
            departmentsDao.update(idOfDepartmentToEdit, newName);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get:delete an individual staff
        get("/departments/department_id/staff/:staff_id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToDelete = Integer.parseInt(request.params("staff_id"));
            staffDao.deleteById(idOfStaffToDelete);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:show an individual staff that is nested in a department
        get("/departments/:department_id/staff/:staff_id", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            int idOfStaffToFind = Integer.parseInt(request.params("Staff_id"));
            Staff foundStaff = staffDao.findById(idOfStaffToFind);
            int idOfDepartmentToFind = Integer.parseInt("category_id");
            Departments foundDepartment = departmentsDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartment);
            model.put("staff", foundStaff);
            model.put("departments", departmentsDao.getAll());
            return new ModelAndView(model, "staff-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get:show a form to update a staff
        get("/staff/:id/edit", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            List<Departments> allDepartments = departmentsDao.getAll();
            model.put("departments", allDepartments);
            Staff staff = staffDao.findById(Integer.parseInt(request.params("id")));
            model.put("staff", staff);
            model.put("editTask", true);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post:process a form to update a staff
        post("/staff/:id", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            int staffToEditId = Integer.parseInt(request.params("id"));
            String newContent = request.queryParams("description");
            int newDepartmentId = Integer.parseInt(request.queryParams("departmentId"));
            staffDao.update(staffToEditId, newContent, newDepartmentId);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
