package dao;

import models.Departments;
import models.Staff;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class Sql2oDepartmentsDaoTest {
    private static Sql2oDepartmentsDao departmentsDao;
    private static Sql2oStaffDao staffDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        //String connectionString = "jdbc:postgresql://localhost:5432/technologydivision_test";
        //Sql2o sql2o = new Sql2o(connectionString, "postgres", "admin");
        String connectionString ="jdbc:postgres://ec2-3-208-50-226.compute-1.amazonaws.com:5432/dcpqq9n6c4677t";
        Sql2o sql2o = new Sql2o(connectionString, "jldbzipmnofvzf", "1f1f65b181c9bb637a9a47187150974e4f98a171629a19c94edec1afcbec9840");
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        staffDao = new Sql2oStaffDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentsDao.clearAllDepartments();           // clear all departments after every test
        staffDao.clearAllStaff();                    // clear all staff after every test
    }

    @AfterClass                                     //run once after all tests in this file completed
    public static void shutDown() throws Exception{
        conn.close();                               // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }

    @Test
    public void addingDepartmentSetsId() throws Exception {
        Departments departments = setupNewDepartment();
        int originalDepartmentId = departments.getId();
        departmentsDao.add(departments);
        assertNotEquals(originalDepartmentId, departments.getId());
    }

    @Test
    public void existingDepartmentsCanBeFoundById() throws Exception {
        Departments departments = setupNewDepartment();
        departmentsDao.add(departments);
        Departments foundDepartment = departmentsDao.findById(departments.getId());
        assertEquals(departments, foundDepartment);
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        Departments departments = setupNewDepartment();
        departmentsDao.add(departments);
        assertEquals(1, departmentsDao.getAll().size());
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentsDao.getAll().size());
    }

    @Test
    public void updateChangesDepartmentsContent() throws Exception {
        String initialDescription = "Quality Assurance";
        Departments departments = new Departments (initialDescription);
        departmentsDao.add(departments);
        departmentsDao.update(departments.getId(),"Tibco");
        Departments updatedDepartment = departmentsDao.findById(departments.getId());
        assertNotEquals(initialDescription, updatedDepartment.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Departments departments = setupNewDepartment();
        departmentsDao.add(departments);
        departmentsDao.deleteById(departments.getId());
        assertEquals(0, departmentsDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllDepartments() throws Exception {
        Departments departments = setupNewDepartment();
        Departments otherDepartment = new Departments("Tibco");
        departmentsDao.add(departments);
        departmentsDao.add(otherDepartment);
        int daoSize = departmentsDao.getAll().size();
        departmentsDao.clearAllDepartments();
        assertTrue(daoSize > 0 && daoSize > departmentsDao.getAll().size());
    }

    @Test
    public void getAllStaffByDepartmentReturnsStaffCorrectly() throws Exception {
        Departments departments = setupNewDepartment();
        departmentsDao.add(departments);
        int departmentsId = departments.getId();
        Staff newStaff = new Staff("Nancy Karanja", "Developer", departmentsId, "EK001");
        Staff otherStaff = new Staff("James Mbugua", "Developer", departmentsId, "EK002");
        Staff thirdStaff = new Staff("Joyce Wambui", "Developer", departmentsId, "EK003");
        staffDao.add(newStaff);
        staffDao.add(otherStaff);
        assertEquals(2, departmentsDao.getAllStaffByDepartments(departmentsId).size());
        assertTrue(departmentsDao.getAllStaffByDepartments(departmentsId).contains(newStaff));
        assertTrue(departmentsDao.getAllStaffByDepartments(departmentsId).contains(otherStaff));
        assertFalse(departmentsDao.getAllStaffByDepartments(departmentsId).contains(thirdStaff));
    }

    // helper method
    public Departments setupNewDepartment(){
        return new Departments("Quality Assurance");
    }
}
