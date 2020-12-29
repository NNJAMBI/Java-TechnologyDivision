package models;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import junit.framework.TestCase;

public class StaffTest  {
    @Test
    public void NewStaffGetsCreatedCorrectlyCreate_true() throws Exception{
        Staff staff = setUpNewStaff();
        assertEquals(true, staff instanceof Staff);
    }

    @Test
    public void StaffInstantiatesWithDescription_true() throws Exception{
        Staff staff = setUpNewStaff();
        assertEquals("Nancy Karanja", staff.getDescription());
    }

    //helper methods
    public Staff setUpNewStaff() {
        return new Staff("Nancy Karanja", 1);
    }
}