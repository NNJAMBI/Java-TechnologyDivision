package models;

import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.TestCase;

public class DepartmentsTest {
    @Test
    public void NewDepartmentGetsCreatedCorrectlyCreate_True() throws Exception{
        Departments departments = new Departments("Tibco");
        assertEquals(true, departments instanceof Departments);
    }

}