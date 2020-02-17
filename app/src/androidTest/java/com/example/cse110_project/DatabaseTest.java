package com.example.cse110_project;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class  DatabaseTest {
    private String debugTag = "stdout";
    private RouteEntryDAO routeEntryDAO;
    private RouteEntryDatabase routeEntryDatabase;

    @Before
    public void connectDb() {
        // Set debug database flag
        RouteEntryDatabase.DEBUG_DATABASE = true;
        Context context = ApplicationProvider.getApplicationContext();
        routeEntryDatabase = RouteEntryDatabase.getDatabase(context);
        routeEntryDAO = routeEntryDatabase.getRouteEntryDAO();
        routeEntryDAO.clearRoutes();
    }

    @After
    public void closeDb() throws IOException {
        RouteEntryDatabase.closeDateBase();
    }

    // Test for database
    @Test
    public void insertBasicRouteInfo() throws Exception {
        String routeName = "Run to LA";
        RouteEntry route = new RouteEntry(routeName, "San Diego");
        route.setSteps(300000);
        route.setDistance(152);
        routeEntryDAO.insertRoute(route);

        RouteEntry[] entries = routeEntryDAO.getRoute(routeName);
        assertEquals(1, entries.length);
        assertTrue(entries[0].equals(route));
    }

    @Test
    public void insertPartRouteInfo() {
        String routeName = "Run to SF";
        RouteEntry route = new RouteEntry(routeName, "San Diego");
        route.setSteps(600000);
        route.setDistance(650);
        route.setFavorite(0);
        route.setLevel(1);
        route.setNote("I don't recommend anyone trying it...");
        routeEntryDAO.insertRoute(route);

        RouteEntry[] entries = routeEntryDAO.getRoute(routeName);
        assertEquals(1, entries.length);
        assertTrue(entries[0].equals(route));
    }

    @Test
    public void insertDuplicateRouteName() {
        String routeName = "RunDup";
        RouteEntry route1 = new RouteEntry(routeName, "San Diego");
        routeEntryDAO.insertRoute(route1);
        RouteEntry route2 = new RouteEntry(routeName, "San Diego");
        routeEntryDAO.insertRoute(route2);

        RouteEntry[] entries = routeEntryDAO.getAllRoutes();
        assertEquals(2, entries.length);
        entries = routeEntryDAO.getRoute(routeName);
        assertEquals(2, entries.length);
    }

    @Test
    public void getAlphabetOrder() {
        int numRecord = 26*3;
        for(int i = 0; i < numRecord; i++) {
            char c = (char)('a' + (int)(Math.random()*26));
            String routeName = "" + c;
            RouteEntry route = new RouteEntry(routeName, "SD");
            routeEntryDAO.insertRoute(route);
        }

        String[] names = routeEntryDAO.getAllRouteNames();
        char lastChar = 'a';
        for(String s: names) {
            assertTrue(s.charAt(0) >= lastChar);
            if(s.charAt(0) > lastChar) lastChar = s.charAt(0);
        }

        RouteEntry[] entries = routeEntryDAO.getAllRoutes();
        lastChar = 'a';
        for(RouteEntry r: entries) {
            assertTrue(r.getRouteName().charAt(0) >= lastChar);
            if(r.getRouteName().charAt(0) > lastChar) lastChar = r.getRouteName().charAt(0);
        }
    }

    @Test
    public void getMostRecentRoute() {
        RouteEntry[] entries = routeEntryDAO.getMostRecentUpdatedRoute();
        assertEquals(0, entries.length);

        String routeName = "RunTest";
        RouteEntry route1 = new RouteEntry(routeName, "San Diego");
        route1.setYear(2019);route1.setMonth(2);route1.setDate(10);
        route1.setTime(2000);
        routeEntryDAO.insertRoute(route1);
        RouteEntry route2 = new RouteEntry(routeName, "San Diego");
        route2.setYear(2019);route2.setMonth(2);route2.setDate(13);
        routeEntryDAO.insertRoute(route2);

        entries = routeEntryDAO.getMostRecentUpdatedRoute();
        assertEquals(1, entries.length);
        assertTrue(route1.equals(entries[0]));

        RouteEntry route3 = new RouteEntry(routeName, "San Diego");
        route3.setYear(2019);route3.setMonth(2);route3.setDate(13);
        route3.setTime(20000);
        routeEntryDAO.insertRoute(route3);

        entries = routeEntryDAO.getMostRecentUpdatedRoute();
        assertEquals(1, entries.length);
        assertTrue(route3.equals(entries[0]));
    }

    @Test
    public void updateRoute() {
        String routeName = "RunTest";
        RouteEntry route1 = new RouteEntry(routeName, "San Diego");
        route1.setYear(2019);route1.setMonth(2);route1.setDate(10);
        routeEntryDAO.insertRoute(route1);

        RouteEntry[] entries = routeEntryDAO.getAllRoutes();
        assertEquals(1, entries.length);
        route1 = entries[0];
        assertTrue(route1.getYear() == 2019);
        assertTrue(route1.getMonth() == 2);
        assertTrue(route1.getDate() == 10);
        assertTrue(route1.getTime() == -1);
        assertTrue(route1.getSteps() == -1);
        assertTrue(route1.getDistance() < 0);
        int id = route1.getId();

        // Update data
        routeEntryDAO.updateRouteWithData(id, 15,2,2020,1000,200,1.2);

        route1 = routeEntryDAO.getRoute(id);
        assertTrue(route1.getYear() == 2020);
        assertTrue(route1.getMonth() == 2);
        assertTrue(route1.getDate() == 15);
        assertTrue(route1.getTime() == 1000);
        assertTrue(route1.getSteps() == 200);
        assertTrue(route1.getDistance() == 1.2);
    }
}
