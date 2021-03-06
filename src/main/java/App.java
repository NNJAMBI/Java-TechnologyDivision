import java.util.*;
import java.util.List;
import java.util.Map;

import dao.DepartmentsDao;
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
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        //String connectionString = "jdbc:postgresql://localhost:5432/technologydivision";
        String connectionString = "jdbc:postgresql://ec2-3-90-124-60.compute-1.amazonaws.com/d8upn63spugrrf";
        //Sql2o sql2o = new Sql2o(connectionString, "postgres", "admin");
        Sql2o sql2o = new Sql2o(connectionString, "yjbufbozhsqodl", "b13925f10ff4c29a33788eb91786c992dde5fcedd2adafe758a15fa1f2bbdc52");
        Sql2oStaffDao staffDao = new Sql2oStaffDao(sql2o);
        Sql2oDepartmentsDao departmentsDao = new Sql2oDepartmentsDao(sql2o);

//show all Staff in all departments and all departments
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<Departments> allDepartments = departmentsDao.getAll();
            model.put("departments", allDepartments);
            List<Staff> staff = staffDao.getAll();
            model.put("staff", staff);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

//get:show form to create a new department
        get("/departments/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> departments = departmentsDao.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "departments-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new department
        post("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            Departments newDepartments = new Departments(name);
            departmentsDao.add(newDepartments);
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
            String roles = request.queryParams("roles");
            String staff_id = request.queryParams("staff_id");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            Staff newStaff = new Staff(description, roles, departmentId, staff_id);
            String message = staffDao.add(newStaff);
            model.put("message", message);
           // response.redirect("/");
           return new ModelAndView(model,"staff-form.hbs");
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
        get("/staff/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            staffDao.clearAllStaff();
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific department and all the staff in it
        get("/departments/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            //System.out.println(request.params());
            int idOfDepartmentToFind = Integer.parseInt(request.params("id"));
            //System.out.println(idOfDepartmentToFind);
            Departments foundDepartments = departmentsDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartments);
            //System.out.println(foundDepartments.getId());
            List<Staff> allStaffByDepartments = departmentsDao.getAllStaffByDepartments(idOfDepartmentToFind);
            model.put("staff", allStaffByDepartments);
            model.put("departments", departmentsDao.getAll());
            return new ModelAndView(model, "departments-detail.hbs");
        }, new HandlebarsTemplateEngine());


        //get:show a form to update a department
        get("/departments/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editDepartments", true);
            Departments departments = departmentsDao.findById(Integer.parseInt(request.params("id")));
            model.put("department", departments);
            model.put("departments", departmentsDao.getAll());
            return new ModelAndView(model, "departments-form.hbs");
        }, new HandlebarsTemplateEngine());


        //post:process a form to update a department
        post("/departments/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int departmentId = Integer.parseInt(request.params("id"));
            String newName = request.queryParams("newDepartmentName");
            departmentsDao.update(departmentId, newName);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get:delete an individual staff
        get("/departments/:department_id/staff/:staff_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToDelete = Integer.parseInt(req.params("staff_id"));
            staffDao.deleteById(idOfStaffToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get:delete an individual department
        get("/departments/:departmentId/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfDepartmentToDelete = Integer.parseInt(req.params("departmentId"));
            departmentsDao.deleteById(idOfDepartmentToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get:show an individual staff that is nested in a department
        get("departments/:department_id/staff/:staff_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToFind = Integer.parseInt(req.params("staff_id")); //
            Staff foundStaff = staffDao.findById(idOfStaffToFind); //use it to find staff
            int idOfDepartmentToFind = Integer.parseInt(req.params("department_id"));
            Departments foundDepartments = departmentsDao.findById(idOfDepartmentToFind);
            model.put("department", foundDepartments);
            model.put("staff", foundStaff);
            model.put("departments", departmentsDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "staff-detail.hbs"); //individual staff page.
        }, new HandlebarsTemplateEngine());

        //get:show a form to update a staff
        get("/staff/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> allDepartments = departmentsDao.getAll();
            model.put("departments", allDepartments);
            Staff staff = staffDao.findById(Integer.parseInt(request.params("id")));
            model.put("staff", staff);
            model.put("editStaff", true);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());


        //post:process a form to update a staff
        post("/staff/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int staffToEditId = Integer.parseInt(request.params("id"));
            String newContent = request.queryParams("description");
            int newDepartmentsId = Integer.parseInt(request.queryParams("departmentId"));
            String staff_id = request.queryParams("staff_id");
            String roles = request.queryParams("roles");
            String message = staffDao.update(staffToEditId, newContent, newDepartmentsId, staff_id, roles);
            model.put("message", message);
            //response.redirect("/");
            //return null;
            return new ModelAndView(model,"staff-form.hbs");
        }, new HandlebarsTemplateEngine());
    }

}
