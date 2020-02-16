package com.example.cse110_project.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("routeName")}, tableName = "routes")
public class RouteEntry {
    public static final String[] RUN_VAL = {"Loop", "Out-and-Back"};
    public static final String[] TERRAIN_VAL = {"Flat", "Hilly"};
    public static final String[] ROAD_TYPE_VAL = {"Streets", "Trail"};
    public static final String[] ROAD_CONDITION_VAL = {"Even Surface", "Uneven Surface"};
    public static final String[] LEVEL_VAL = {"Easy", "Moderate", "Difficult"};
    public static final String[] FAVORITE_VAL = {"Yes"};

    @PrimaryKey(autoGenerate = true)
    private int id;

    public RouteEntry(String routeName, String startPoint) {
        this.routeName = routeName;
        this.startPoint = startPoint;
    }

    // Basic route information
    private String routeName;
    private String startPoint;
    private int date, month, year;

    // Auto generated from API
    private long steps = -1;
    private double distance = -1;
    private long time = -1;

    // Feature data, can omit
    private int run = -1;
    private int terrain = -1;
    private int roadType = -1;
    private int roadCondition = -1;
    private int level = -1;
    private int favorite = -1;
    private String note = "";

    public int getId() {
        return id;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public long getSteps() {
        return steps;
    }

    public double getDistance() {
        return distance;
    }

    public int getRun() {
        return run;
    }

    public int getTerrain() {
        return terrain;
    }

    public int getRoadType() {
        return roadType;
    }

    public int getRoadCondition() {
        return roadCondition;
    }

    public int getLevel() {
        return level;
    }

    public int getFavorite() {
        return favorite;
    }

    public String getNote() {
        return note;
    }

    public long getTime() {
        return time;
    }

    public int getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // This is for database use. It is automatically set. Don't set id directly.
    public void setId(int id) {
        this.id = id;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public void setTerrain(int terrain) {
        this.terrain = terrain;
    }

    public void setRoadType(int roadType) {
        this.roadType = roadType;
    }

    public void setRoadCondition(int roadCondition) {
        this.roadCondition = roadCondition;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    @NonNull
    // For debug purpose
    public String toString() {
        return id + ": " + routeName + " " + startPoint + "(" + distance + ": " + steps + ": " + time + ")" + "\n"
                + "Date: " + month + "/" + date + "/" + year + " " + time + "\n"
                + "Run: " + (run == -1? "N/A": RUN_VAL[run]) + " Terrain: " + (terrain == -1? "N/A": TERRAIN_VAL[terrain])
                + " Road: " + (roadType == -1? "N/A": ROAD_TYPE_VAL[roadType]) + " Road Condition: " + (roadCondition == -1? "N/A": ROAD_CONDITION_VAL[roadCondition])
                + " Level: " + (level == -1? "N/A": LEVEL_VAL[level]) + " Favorite: " + (favorite == -1? "N/A": FAVORITE_VAL[favorite]) + "\n"
                + " Note: " + note;
    }

    // For test purpose
    public boolean equals(RouteEntry r) {
        if(r == null) return false;
        return this.routeName.equals(r.routeName) && this.startPoint.equals(r.startPoint)
                && this.month == r.month && this.date == r.date && this.year == r.year
                && this.steps == r.steps && this.distance == r.distance && this.time == r.time
                && this.run == r.run && this.terrain == r.terrain && this.roadType == r.roadType
                && this.roadCondition == r.roadCondition && this.level == r.level
                && this.favorite == r.favorite && this.note.equals(r.note);
    }
}
