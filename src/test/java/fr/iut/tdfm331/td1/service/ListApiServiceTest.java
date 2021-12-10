package fr.iut.tdfm331.td1.service;

import fr.iut.tdfm331.td1.model.Employee;
import fr.iut.tdfm331.td1.model.Meeting;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * Unit test file to test ListApiService class
 */
public class ListApiServiceTest {

    private ListApiService service;

    @Before
    public void setupService() {
        service = new ListApiService();
    }

    /**
     * Test to check if list of Meeting is ∞correctly generated
     */
    @Test
    public void getListMeetingWithSuccess() {
        List<Meeting> listMeetings = service.getListMeetings();
        List<Meeting> expectedListMeetings = ListMeetingsGenerator.LIST_MEETINGS;
        assertThat(listMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedListMeetings.toArray()));
    }

    /**
     * Test to check it list of Employee is correctly generated
     */
    @Test
    public void getListEmployeeWithSuccess() {
        List<Employee> listEmployees = service.getListEmployees();
        List<Employee> expectedListEmployees = ListEmployeesGenerator.LIST_EMPLOYEES;
        assertThat(listEmployees, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedListEmployees.toArray()));

    }

    /**
     * Test to check if a new Meeting object is correctly added to the list
     */
    @Test
    public void addNewMeetingWithSuccess() {

        // Create list Employee
        List<Employee> listEmployees = Arrays.asList(new Employee("Baptiste", "baptiste@lamzone.com", 4),
                new Employee("Fanny", "fanny@lamzone.com", 10),
                new Employee("Vincent", "vincent@lamzone.com", 22));

        // Create list Meeting
        Meeting newMeeting = new Meeting("Réunion d'avancement",
                "Planck",
                "12/11/20",
                "15:30",
                "16:00",
                "Revues des dernières actions",
                listEmployees);

        // Add Meeting
        service.addMeeting(newMeeting);
        Assert.assertTrue(service.getListMeetings().contains(newMeeting));
    }

    /**
     * Test to check if a selected Meeting is correctly removed from list
     */
    @Test
    public void removeMeetingWithSuccess() {
        // Get first Meeting from list
        Meeting meetingToRemove = service.getListMeetings().get(0);
        service.getListMeetings().remove(meetingToRemove);
        Assert.assertFalse(service.getListMeetings().contains(meetingToRemove));
    }

    /**
     * Test to check if we can get an employee by name which is in a database
     */
    @Test
    public void findByNameWithSuccess() throws EmployeeNotFound {
        Assert.assertEquals(service.findByName("Arthur").getName(), "Arthur");
    }

    /**
     * Test to check if we can get an employee by name which doesn't exist in the database
     */
    @Test
    public void findByNameWithError() {
        try {
            service.findByName("Clément");
        } catch (EmployeeNotFound e){
            Assert.assertNotNull(e);
        }
    }

    /**
     * Test to check if the method removeMeeting is functional
     */
    @Test
    public void testremoveMeeting() {

        //Creation de nos employés
        List<Employee> listEmployees = Arrays.asList(new Employee("Clément", "clement@lamzone.com", 4),
                new Employee("Krysto", "krysto@lamzone.com", 10),
                new Employee("Bastien", "vincent@lamzone.com", 22));

        //Creeation de notre meeting
        Meeting meetingToRemove2 = new Meeting("Réunion pour domir",
                "Salle du g1",
                "10/12/21",
                "15:45",
                "17:45",
                "faut domir les gars.",
                listEmployees);

        service.addMeeting(meetingToRemove2);
        Assert.assertTrue(service.getListMeetings().contains(meetingToRemove2));

        service.removeMeeting(meetingToRemove2);
        Assert.assertFalse(service.getListMeetings().contains(meetingToRemove2));
    }
}
