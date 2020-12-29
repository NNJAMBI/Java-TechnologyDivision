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

}
