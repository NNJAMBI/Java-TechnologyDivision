package dao;

import models.Staff;
import java.util.List;

public interface StaffDao {

    //LIST
    List<Staff> getAll();

    //CREATE
    String add(Staff staff);

    //READ
    Staff findById(int id);

    //UPDATE
    String update(int id, String content, int departmentId, String staff_id, String roles);

    //DELETE
    void deleteById(int id);
    void clearAllStaff();

}
