package com.example.cse110_project.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RouteEntry.class}, version = 1, exportSchema = false)
public abstract class RouteEntryDatabase extends RoomDatabase {
    public static final String DB_NAME = "routes.db";

    public static RouteEntryDatabase INSTANCE;
    public abstract RouteEntryDAO getRouteEntryDAO();
}
