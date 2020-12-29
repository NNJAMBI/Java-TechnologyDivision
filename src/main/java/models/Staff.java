package models;

import java.util.ArrayList;
import java.util.Objects;

public class Staff {
private String description;
private int departmentId;
private int id;

public Staff(String description, int departmentId) {
    this.description = description;
    this.departmentId = departmentId;
}

public int getDepartmentId() {
    return departmentId;
}

public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
}

@Override
    public int hashCode() {
    return Objects.hash(description, id);
}
public void setDescription(String description) {
    this.description = description;
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
}