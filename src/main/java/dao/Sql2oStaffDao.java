package dao;

import models.Staff;
import org.graalvm.compiler.debug.CloseableCounter;
import org.sql2o.*;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oStaffDao implements StaffDao{
   private final Sql2o sql2o;

   public Sql2oStaffDao(Sql2o sql2o) {
       this.sql2o = sql2o;
   }

   @Override
    public void add(Staff staff) {
       String sql = "INSERT INTO staff (description, departmentId) VALUES (:description, :departmentId)";
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
    public void update(int id, String newDescription, int newDepartmentId){
        String sql = "UPDATE staff SET (description, departmentId) = (:description, :departmentId) WHERE id=:id";   //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("departmentId", newDepartmentId)
                    .addParameter("id", id)
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
