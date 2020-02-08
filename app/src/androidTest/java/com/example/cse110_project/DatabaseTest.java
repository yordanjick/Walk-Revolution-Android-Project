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
public class DatabaseTest {
    private String debugTag = "stdout";
    private RouteEntryDAO routeEntryDAO;
    private RouteEntryDatabase routeEntryDatabase;

    @Before
    public void connectDb() {
        // Set debug database flag
        RouteEntryDatabase.DEBUG_DATABASE = false;
        Context context = ApplicationProvider.getApplicationContext();
        routeEntryDatabase = RouteEntryDatabase.getDatabase(context);
        routeEntryDAO = routeEntryDatabase.getRouteEntryDAO();
    }

    @After
    public void closeDb() throws IOException {
        RouteEntryDatabase.closeDateBase();
    }

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
        assertEquals(numRecord, names.length);

        RouteEntry[] entries = routeEntryDAO.getAllRoutes();
        lastChar = 'a';
        for(RouteEntry r: entries) {
            assertTrue(r.getRouteName().charAt(0) >= lastChar);
            if(r.getRouteName().charAt(0) > lastChar) lastChar = r.getRouteName().charAt(0);
        }
    }
}
