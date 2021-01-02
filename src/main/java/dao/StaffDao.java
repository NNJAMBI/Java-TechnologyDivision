package dao;

import models.Staff;
import java.util.List;

public interface StaffDao {

    //LIST
    List<Staff> getAll();

    //CREATE
    void add(Staff staff);

    //READ
    Staff findById(int id);

    //UPDATE
    void update(int id, String content, int departmentId);

    //DELETE
    void deleteById(int id);
    void clearAllStaff();

}
