package com.gmail.lagoland.help.LVLearning;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
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

    String noCoursesFound = "No courses were found matching that name... Check your config file and try again...";
    String invalidCourse = "Invalid Course... ";
    String tooMany = "Too many arguments entered... type ";
    String tooFew = "Too few arguments entered... type ";

    String checking = "Checking the list...";

    String noPerms = "You do not have permission to enroll in this course; pelase see course description for more details...";
    String confirmEnroll1 = "You enrolled in course: ";
    String confirmEnroll2 = " at school: ";
    String confirmEnroll3 = " for: $";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;
        Set<String> courses = plugin.getConfig().getConfigurationSection("Traits").getKeys(false);
        String[] coursesArray = courses.toArray(new String[courses.size()]);

        Set<String> uuids = plugin.getConfig().getConfigurationSection("Players").getKeys(false);
        String[] uuidsArray = uuids.toArray(new String[courses.size()]);

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

                    if(Arrays.asList(coursesArray).contains(strings[1].toLowerCase())){

                        String trait = strings[1].toLowerCase();

                        player.sendMessage("Found! " + trait);

                        String description = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "."+ "description");
                        String requirement = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "." + "requirement");
                        String courseCode = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "." + "course_code");
                        String school = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "." + "school");

                        player.sendMessage("Description: " + description);
                        player.sendMessage("Required Permission: " + requirement);
                        player.sendMessage("Course Code: " + courseCode);
                        player.sendMessage("School: " + school);
                        return  true;
                        }

                    else {
                        player.sendMessage(invalidCourse + noCoursesFound);
                            return true;
                        }
                }
                else if (strings[0].equalsIgnoreCase("enroll")) {

                    if(Arrays.asList(coursesArray).contains(strings[1].toLowerCase())){
                        //check to see if the player has the permission to enroll
                        if(4 > 1){

                            String uuid = player.getUniqueId().toString();
                            String playerName = player.getName();

                            //check to see if uuid exists on list
                            if(!Arrays.asList(uuidsArray).contains(uuid)){
                                plugin.getConfig().createSection("Players." + uuid);

                                //make a method to add this stuff with a command /lvl createprofile player_name so duplication is reduced
                                //change the strings like "Players." into variables
                                plugin.getConfig().createSection("Players." + uuid + "player_name");
                                plugin.getConfig().set("Players." + uuid + "player_name.", playerName);
                                plugin.getConfig().createSection("Players." + uuid + "." + "traits");
                                plugin.getConfig().createSection("Players." + uuid + "." + "enrolled_courses");
                                plugin.getConfig().createSection("Players." + uuid + "." + "ineligible_courses");
                                plugin.getConfig().createSection("Players." + uuid + "." + "tags");
                                plugin.getConfig().set("Players." + uuid + "tags", "profileAddedByConsole");

                                plugin.getConfig().set("Players." + uuid + "." + "enrolled_courses", strings[1].toLowerCase());
                                plugin.saveConfig();

                                player.sendMessage("Profile created for uuid: " + uuid);

                                String school = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "." + "school");
                                player.sendMessage(confirmEnroll1 + strings[1] + confirmEnroll2 + school + confirmEnroll3);
                            }

                            else if(Arrays.asList(uuidsArray).contains(uuid)){

                                plugin.getConfig().set("Players." + uuid + "." + "enrolled_courses", strings[1].toLowerCase());
                                plugin.saveConfig();

                                player.sendMessage("Profile created for uuid: " + uuid);

                                String school = plugin.getConfig().getString("Traits." + strings[1].toLowerCase() + "." + "school");
                                player.sendMessage(confirmEnroll1 + strings[1] + confirmEnroll2 + school + confirmEnroll3);
                            }
                            return true;
                        }
                        //if player does not have this permission, do this
                        else{
                            player.sendMessage(noPerms);
                            return true;
                        }

                    }

                    return true;
                }

                else if (strings[0].equalsIgnoreCase("ecourses")) {
                    return true;
                }

                else if (strings[0].equalsIgnoreCase("test")) {
                    return true;
                }

                //end of commands with 2 additional arguments
                else{
                    player.sendMessage(error);
                    return true;
                }
            }

            else if(strings.length == 3) {

                if (strings[0].equalsIgnoreCase("test")) {
                    return true;
                }

                //end of commands with 3 additional arguments
                else{
                    player.sendMessage(error);
                    return true;
                }
            }
            return true;
        }//if command = LVL code block end
        return true;
    }//on command end code block end
}

