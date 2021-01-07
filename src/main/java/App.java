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
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/technologydivision";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "admin");
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
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            Staff newStaff = new Staff(description, roles, departmentId);
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
            Map<String, Object>model = new HashMap<>();
           // System.out.println(request.params());
            int idOfDepartmentToEdit = Integer.parseInt(request.params("id"));
            System.out.println(idOfDepartmentToEdit);
            String newName = request.queryParams("name");
            departmentsDao.update(idOfDepartmentToEdit, newName);
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
            Map<String, Object>model = new HashMap<>();
            List<Departments> allDepartments = departmentsDao.getAll();
            model.put("departments", allDepartments);
            Staff staff = staffDao.findById(Integer.parseInt(request.params("id")));
            model.put("staff", staff);
            model.put("editStaff", true);
            return new ModelAndView(model, "staff-form.hbs");
        }, new HandlebarsTemplateEngine());


        //post:process a form to update a staff
        post("/staff/:id", (request, response) -> {
            Map<String, Object>model = new HashMap<>();
            int staffToEditId = Integer.parseInt(request.params("id"));
            String newContent = request.queryParams("description");
            int newDepartmentsId = Integer.parseInt(request.queryParams("departmentId"));
            staffDao.update(staffToEditId, newContent, newDepartmentsId);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
