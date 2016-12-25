package com.gmail.lagoland.help.LVLearning;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by lake.smith on 12/25/2016.
 */
public class Main extends JavaPlugin{
    public static Economy econ = null;

    @Override
    public void onDisable() {

        saveConfig();
    }

    @Override
    public void onEnable() {

        this.getConfig().options().copyDefaults(true);
        saveConfig();

        this.getCommand("LVL").setExecutor(new LVL(this));

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