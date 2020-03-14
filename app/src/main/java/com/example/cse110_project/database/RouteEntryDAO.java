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
    public RouteEntry[] getRoute(String routeName);

    @Query("SELECT * FROM routes WHERE id=:routeId")
    public RouteEntry getRoute(int routeId);

    @Query("SELECT * FROM routes WHERE time >= 0 ORDER BY year DESC, month DESC, date DESC LIMIT 1")
    public RouteEntry[] getMostRecentUpdatedRoute();

    @Query("UPDATE routes SET date = :date, month = :month, year = :year, time = :timeInSeconds" +
            ", steps = :steps, distance = :distanceInMiles WHERE id = :id")
    public void updateRouteWithData(int id, int date, int month, int year, long timeInSeconds
            , long steps, double distanceInMiles);

    @Query("DELETE FROM routes")
    public void clearRoutes();
}
