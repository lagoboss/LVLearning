package com.gmail.lagoland.help.Database;

import com.gmail.lagoland.help.LVLearning.Main;

import java.util.logging.Level;

public class Error {
    public static void execute(Main plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(Main plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
