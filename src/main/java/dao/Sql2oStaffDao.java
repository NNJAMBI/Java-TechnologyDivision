package dao;

import models.Staff;
import org.sql2o.*;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oStaffDao implements StaffDao { //implementing our interface

    private final Sql2o sql2o;

    public Sql2oStaffDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

   @Override
    public void add(Staff staff) {
       String sql = "INSERT INTO staff (description, departmentId, staff_id, roles) VALUES (:description, :departmentId, :staff_id, :roles)";
       try(Connection con = sql2o.open()){
           int id = (int) con.createQuery(sql, true)
                   .bind(staff)
                   .executeUpdate()
                   .getKey();
           staff.setId(id);
       } catch (Sql2oException ex) {
           System.out.println(ex);
       }
   }

   @Override
    public List<Staff> getAll() {
       try(Connection con = sql2o.open()) {
           return con.createQuery("SELECT * FROM staff")
                   .executeAndFetch(Staff.class);
       }
   }

    @Override
    public Staff findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Staff.class);
        }
    }


    @Override
    public void update(int id, String newDescription, int newDepartmentId, String staff_id, String roles){
        String sql = "UPDATE staff SET (description, departmentId, staff_id, roles) = (:description, :departmentId,:staff_id, :roles) WHERE id=:id";   //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("departmentId", newDepartmentId)
                    .addParameter("id", id)
                    .addParameter("staff_id", staff_id)
                    .addParameter("roles", roles)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from staff WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllStaff() {
        String sql = "DELETE from staff";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}
