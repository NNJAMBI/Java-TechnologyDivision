package dao;

import models.Staff;
import org.sql2o.*;
import org.junit.*;

import java.awt.*;

import static org.junit.Assert.*;


public class Sql2oStaffDaoTest {
    private static Sql2oStaffDao staffDao;
    private static Connection conn;

//    @BeforeClass
//    public static void setUp() throws Exception {
//        //String connectionString = "jdbc:postgresql://localhost:5432/technologydivision_test";
//        //Sql2o sql2o = new Sql2o(connectionString, "postgres", "admin");
//        String connectionString = "postgres://jldbzipmnofvzf:1f1f65b181c9bb637a9a47187150974e4f98a171629a19c94edec1afcbec9840@ec2-3-208-50-226.compute-1.amazonaws.com:5432/dcpqq9n6c4677t\n";
//        Sql2o sql2o = new Sql2o(connectionString, "jldbzipmnofvzf", "1f1f65b181c9bb637a9a47187150974e4f98a171629a19c94edec1afcbec9840");
//        staffDao = new Sql2oStaffDao(sql2o);
//        conn = sql2o.open();
//    }
//        @After
//        public void tearDown() throws Exception {
//            System.out.println("clearing database");
//            staffDao.clearAllStaff();
//        }
//
//        @AfterClass
//        public static void shutDown() throws Exception  {
//        conn.close();
//            System.out.println("connection closed");
//        }
//
//    @Test
//    public void addingStaffSetsId() throws Exception{
//        Staff staff = setupNewStaff();
//        int originalStaffId = staff.getId();
//        staffDao.add(staff);
//        assertNotEquals(originalStaffId, staff.getId());
//    }
//
//    @Test
//    public void existingStaffCanBeFoundById() throws Exception {
//        Staff staff = setupNewStaff();
//        staffDao.add(staff);
//        Staff foundStaff = staffDao.findById(staff.getId());
//        assertEquals(staff, foundStaff);
//    }
//
//    @Test
//    public void addedStaffsAreReturnedFromgetAll() throws Exception{
//        Staff staff = setupNewStaff();
//        staffDao.add(staff);
//        assertEquals(1, staffDao.getAll().size());
//    }
//
//    @Test
//    public void noStaffReturnsEmptyList() throws Exception{
//        assertEquals(0, staffDao.getAll().size());
//    }
//
//    @Test
//    public void updateChangesStaffContent() throws Exception {
//        String initialDescription = "Nancy Karanja";
//        Staff staff = setupNewStaff();
//        staffDao.add(staff);
//
//        staffDao.update(staff.getId(), "James Mbugua", 1, "EK001","Developer");
//        Staff updatedStaff = staffDao.findById(staff.getId());
//        assertNotEquals(initialDescription, updatedStaff.getDescription());
//    }
//
//    @Test
//    public void deleteByIdDeletesCorrectStaff() throws Exception{
//        Staff staff = setupNewStaff();
//        staffDao.add(staff);
//        staffDao.deleteById(staff.getId());
//        assertEquals(0, staffDao.getAll().size());
//    }
//
//    @Test
//    public void clearAllClearsAll() throws Exception{
//        Staff staff = setupNewStaff();
//        Staff otherStaff = new Staff("James Mbugua", "Developer", 2, "EK001");
//        staffDao.add(staff);
//        staffDao.add(otherStaff);
//        int daoSize = staffDao.getAll().size();
//        staffDao.clearAllStaff();
//        assertTrue(daoSize > 0 && daoSize> staffDao.getAll().size());
//    }
//
//    @Test
//    public void departmentIdIsReturnedCorrectly() throws Exception {
//        Staff staff = setupNewStaff();
//        int originalDepId = staff.getDepartmentId();
//        staffDao.add(staff);
//        assertEquals(originalDepId, staffDao.findById(staff.getId()).getDepartmentId());
//    }
//
//    public Staff setupNewStaff(){
//        return new Staff("Nancy Karanja",  "Developer", 1, "EK001");
//    }
//

}