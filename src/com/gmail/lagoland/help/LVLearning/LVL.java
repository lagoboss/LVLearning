package com.gmail.lagoland.help.LVLearning;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lake.smith on 12/25/2016.
 */
public class LVL implements CommandExecutor{

    private final Main plugin;

    public LVL(Main plugin) {

        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    //string bank:
    String versionNumber = "0.0A";
    String pluginName = "LVLearning";
    String description = "A plugin to manage courses and user credentials...";
    String commands = "run '/LVL help' for a list of commands...";

    String cmdLabel_help = "help";
    String cmdLabel_courses = "courses";
    String cmdLabel_course = "course";
    String cmdLabel_ecourses = "ecourses";
    String cmdLabel_enroll = "enroll";
    String cmdLabel_test = "test";
    String cmdLabel_pass = "pass";
    String cmdLabel_fail = "fail";
    String cmdLabel_set = "set";
    String cmdVarExample = " <additional_argument(s)>";
    String lib = "Please visit your local library to learn more...";

    String usage = "/LVL ";
    String error = "Invalid command..." + "Please refer to '/LVL help'...";

    String noCoursesFound = "No courses were found... Check your config file and try again...";
    String invalidCourse = "Invalid Course... ";
    String tooMany = "Too many arguments entered... type ";
    String tooFew = "Too few arguments entered... type ";

    String checking = "Checking the list...";

    public Boolean b;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;
        Set<String> courses = plugin.getConfig().getConfigurationSection("Traits").getKeys(false);

        if (command.getName().equalsIgnoreCase("LVL")){

            if(strings.length == 0){

                List<String> messageSuiteNoArgs = new ArrayList<String>();

                messageSuiteNoArgs.add(versionNumber);
                messageSuiteNoArgs.add(pluginName);
                messageSuiteNoArgs.add(description);
                messageSuiteNoArgs.add(commands);

                for (String item : messageSuiteNoArgs){

                    player.sendMessage(item);

                }

                return true;
            }

            else if(strings.length == 1){

                if(strings[0].equalsIgnoreCase("help")){
                    List<String> messageUsage = new ArrayList<String>();

                    messageUsage.add(cmdLabel_help);
                    messageUsage.add(cmdLabel_courses);
                    messageUsage.add(cmdLabel_course);
                    messageUsage.add(cmdLabel_enroll);
                    messageUsage.add(cmdLabel_ecourses);
                    messageUsage.add(cmdLabel_test);
                    messageUsage.add(cmdLabel_set);
                    messageUsage.add(cmdLabel_pass);
                    messageUsage.add(cmdLabel_fail);

                    player.sendMessage("Commands include:");

                    for (String itemMU : messageUsage){
                        player.sendMessage(usage + itemMU + cmdVarExample);
                    }

                    player.sendMessage(lib);

                    return true;
                }

                else if(strings[0].equalsIgnoreCase("courses")){

                    if(!courses.isEmpty()){
                        player.sendMessage("Course(s) include: ");

                        for (String course : courses){
                            player.sendMessage(course);
                        }
                        return true;
                    }
                    else
                        player.sendMessage(noCoursesFound);
                    return true;
                }

                else if(strings[0].equalsIgnoreCase("course")){

                    player.sendMessage(tooFew + usage + cmdLabel_course + cmdVarExample);
                    return true;
                }
            }

            else if(strings.length == 2) {

                if (strings[0].equalsIgnoreCase("course")) {

                    player.sendMessage(checking);

                    b = false;

                    for (String course : courses) {

                        if (strings[1].equalsIgnoreCase(course)) {

                            String trait = strings[1].toLowerCase();

                            player.sendMessage("Found! " + trait);

                            b = true;
                            //all trait names need to be lower case :/

                            String description = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "."+ "description");
                            String requirement = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "." + "requirement");
                            String courseCode = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "." + "course_code");

                            player.sendMessage("Description: " + description);
                            player.sendMessage("Required Permission: " + requirement);
                            player.sendMessage("Course Code: " + courseCode);

                        } else {
                            player.sendMessage("Searching...");
                        }
                    }

                    if (b = false) {
                        player.sendMessage(invalidCourse + noCoursesFound);

                        return true;
                    } else {
                        return true;
                    }
                }
                else{
                    player.sendMessage(error);
                }
            }
                return true;
        }
        return true;
    }
}

