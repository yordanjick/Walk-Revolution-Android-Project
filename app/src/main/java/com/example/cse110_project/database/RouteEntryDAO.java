package com.example.cse110_project.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RouteEntryDAO {
    @Insert
    public void insertRoute(RouteEntry routeEntry);

    @Query("SELECT * FROM routes ORDER BY routeName ASC, startPoint ASC")
    public RouteEntry[] getAllRoutes();

    @Query("SELECT routeName FROM routes ORDER BY routeName ASC")
    public String[] getAllRouteNames();

    @Query("SELECT * FROM routes WHERE routeName=:routeName")
    public RouteEntry[] getRouteEntries(String routeName);
}
