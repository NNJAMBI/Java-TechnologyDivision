package models;

import java.util.ArrayList;
import java.util.Objects;

public class Staff {
private String description;
private int departmentId;
private int id;
private String roles;

public Staff(String description, String roles, int departmentId) {
    this.description = description;
    this.roles = roles;
    this.departmentId = departmentId;
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
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return id == staff.id &&
                Objects.equals(description, staff.description);


    }
    @Override
    public int hashCode() {
    return Objects.hash(description, id, roles);
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

}
