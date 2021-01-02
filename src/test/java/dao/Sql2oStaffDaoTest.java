package dao;

import models.Staff;
import org.sql2o.*;
import org.junit.*;

import java.awt.*;

import static org.junit.Assert.*;


public class Sql2oStaffDaoTest {
    private Sql2oStaffDao staffDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        staffDao = new Sql2oStaffDao(sql2o);
        conn = sql2o.open();
    }
        @After
        public void tearDown() throws Exception {
            conn.close();
        }

    @Test
    public void addingStaffSetsId() throws Exception{
        Staff staff = setupNewStaff();
        int originalStaffId = staff.getId();
        staffDao.add(staff);
        assertNotEquals(originalStaffId, staff.getId());
    }

    @Test
    public void existingStaffCanBeFoundById() throws Exception {
        Staff staff = setupNewStaff();
        staffDao.add(staff);
        Staff foundStaff = staffDao.findById(staff.getId());
        assertEquals(staff, foundStaff);
    }

    @Test
    public void addedStaffsAreReturnedFromgetAll() throws Exception{
        Staff staff = setupNewStaff();
        staffDao.add(staff);
        assertEquals(1, staffDao.getAll().size());
    }

    @Test
    public void noStaffReturnsEmptyList() throws Exception{
        assertEquals(0, staffDao.getAll().size());
    }

    @Test
    public void updateChangesStaffContent() throws Exception {
        String initialDescription = "Nancy Karanja";
        Staff staff = setupNewStaff();
        staffDao.add(staff);
        staffDao.update(staff.getId(), "James Mbugua", 1);
        Staff updatedStaff = staffDao.findById(staff.getId());
        assertNotEquals(initialDescription, updatedStaff.getDescription());
    }

    @Test
    public void deleteByIdDeletesCorrectStaff() throws Exception{
        Staff staff = setupNewStaff();
        staffDao.add(staff);
        staffDao.deleteById(staff.getId());
        assertEquals(0, staffDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception{
        Staff staff = setupNewStaff();
        Staff otherStaff = new Staff("James Mbugua", 2);
        staffDao.add(staff);
        staffDao.add(otherStaff);
        int daoSize = staffDao.getAll().size();
        staffDao.clearAllStaff();
        assertTrue(daoSize > 0 && daoSize> staffDao.getAll().size());
    }

    @Test
    public void categoryIdIsReturnedCorrectly() throws Exception {
        Staff staff = setupNewStaff();
        int originalDepId = staff.getDepartmentId();
        staffDao.add(staff);
        assertEquals(originalDepId, staffDao.findById(staff.getId()).getDepartmentId());
    }

    public Staff setupNewStaff(){
        return new Staff("Nancy Karanja", 1);
    }


}