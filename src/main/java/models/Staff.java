package models;

import java.util.ArrayList;
import java.util.Objects;

public class Staff {
    private String description;
    private int departmentId;
    private int id;
    private String roles;
    private String staff_id;



    public Staff(String description, String roles, int departmentId, String staff_id) {
        this.description = description;
        this.roles = roles;
        this.departmentId = departmentId;
        this.staff_id = staff_id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return departmentId == staff.departmentId &&
                id == staff.id &&
                description.equals(staff.description) &&
                roles.equals(staff.roles) &&
                staff_id.equals(staff.staff_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, departmentId, id, roles, staff_id);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRoles(String roles) {
        this.roles = roles;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getRoles() {
        return roles;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_id() {
        return staff_id;
    }
}
