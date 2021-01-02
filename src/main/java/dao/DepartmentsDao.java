package dao;

import models.Departments;
import models.Staff;
import java.util.List;

public interface DepartmentsDao {

    //LIST
    List<Departments> getAll();

    //CREATE
    void add (Departments departments);

    //READ
    Departments findById(int id);
    List<Staff> getAllStaffByDepartments(int departmentId);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllDepartments();
}
