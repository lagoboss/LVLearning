package com.gmail.lagoland.help.LVLearning;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;

/**
 * Created by lake.smith on 12/25/2016.
 */
public class Main extends JavaPlugin{
    public static Economy econ = null;

    @Override
    public void onDisable() {

        saveConfig();
        MySQL.disconnect();
    }

    @Override
    public void onEnable() {

        this.getConfig().options().copyDefaults(true);
        saveConfig();

        //this.getCommand("LVL").setExecutor(new LVL(this));

        String h = this.getConfig().get("host").toString();
        String p = this.getConfig().get("port").toString();
        String d = this.getConfig().get("database").toString();
        String u = this.getConfig().get("username").toString();
        String pass = this.getConfig().get("password").toString();

        MySQL.connect(h, p, d, u, pass);

        try {PreparedStatement psEnrolls = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS enrolls (player_uuid VARCHAR(255),player_name VARCHAR(255),course_name VARCHAR(255), test_attempts INT(2), enroll_date INT(255), sessionID VARCHAR(255), PRIMARY KEY (sessionID))");
        psEnrolls.executeUpdate();} catch (Exception e){
            e.printStackTrace();
        }

        try {PreparedStatement psInElg = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS inElg (player_uuid VARCHAR(255),player_name VARCHAR(255),course_name VARCHAR(255), date_eli INT(255), sessionID VARCHAR(255), PRIMARY KEY (sessionID))");
            psInElg.executeUpdate();} catch (Exception e){
            e.printStackTrace();
        }

        try {PreparedStatement psCompleteCourses = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS completed (player_uuid VARCHAR(255),player_name VARCHAR(255),course_name VARCHAR(255), date_passed INT(255), sessionID VARCHAR(255), PRIMARY KEY (sessionID))");
            psCompleteCourses.executeUpdate();} catch (Exception e){
            e.printStackTrace();
        }

        try {PreparedStatement psAvail = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS avail (course_name VARCHAR(255), description VARCHAR(255),permissionReq VARCHAR(255), permissionAdd VARCHAR(255), x FLOAT, y FLOAT, z FLOAT, course_code VARCHAR(255), attempt_limt INT(255), school_name VARCHAR(255), study_time INT(255), addedby_uuid VARCHAR(255), active_status BOOLEAN, last_updatedByUUID VARCHAR(255), PRIMARY KEY (course_code))");
            psAvail.executeUpdate();} catch (Exception e){
            e.printStackTrace();
        }

        try {PreparedStatement psSchools = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS enrolls (school_name VARCHAR(255), school_city VARCHAR(255), school_type VARCHAR(255), x FLOAT, y FLOAT, z FLOAT, PRIMARY KEY (school_name))");
            psSchools.executeUpdate();} catch (Exception e){
            e.printStackTrace();
        }

    }



    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}