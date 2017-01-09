package com.gmail.lagoland.help.LVLearning;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.gmail.lagoland.help.LVLearning.MySQL.console;

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
    String cmdLabel_add = "add";
    String cmdLabel_edit = "edit";
    String cmdVarExample = " <additional_argument(s)>";
    String lib = "Please visit your local library to learn more...";

    String usage = "/LVL ";
    String error = "Invalid command..." + "Please refer to '/LVL help'...";

    String noCoursesFound = "No courses were found matching that name... Check your config file and try again...";
    String invalidCourse = "Invalid Course... ";
    String tooMany = "Too many arguments entered... type ";
    String tooFew = "Too few arguments entered... type ";

    String checking = "Checking the list...";
    String coursesInclude = "Course codes include: ";

    String noPerms = "You do not have permission to enroll in this course; pelase see course description for more details...";
    String confirmEnroll1 = "You enrolled in course: ";
    String confirmEnroll2 = " at school: ";
    String confirmEnroll3 = " for: $";

    String dot = ".";
    String p = "Players";
    String pN = "player_name";
    String t = "traits";
    String eC = "enrolled_courses";
    String tED = "test_eligible_date";
    String iC = "ineligible_courses";
    String eD = "eligible_date";
    String tags = "tags";
    String T = "Traits";
    String d = "description";
    String r = "requirement";
    String perm = "permissions";
    String eCor = "exam_coordinates";
    String x = "x";
    String y = "y";
    String z = "z";
    String cC = "course_code";
    String aL = "attempt_limit";
    String s = "school";
    String sT = "study_time";

    String pluginConsoleDescription = "\247c[\2476LVLearning-SQL Server Connection\247c]";
    String pluginDataConnection = "\247 Attempting to connected to SQL server to access information...";
    String pluginDataConnectionCompleted = "\247 Returning Information";
    String pluginDataConnectionFail = "\247 Connection failed...";


    String consoleAddedTag = "profile_created_by_console";

    String errorCourseOnList = "Error, course on list...";

    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    //if somone types a command, this code block will be executed
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //defining the command sender to be a player; from here, we can get attributes from this player via "player."
        Player player = (Player) commandSender;

        //if the command root = "LVL" with case ignored, this code block will be executed
        if (command.getName().equalsIgnoreCase("LVL")) {

            //must check length of command before checking any other conditions about the command entered;
            //if there are no additionall arguments entered after "/lvl" the code will execute the following block of code
            if (strings.length == 0) {

                //here, I am establishing a list of strings to send to the player
                List<String> messageSuiteNoArgs = new ArrayList<String>();

                //here, I am adding to my list of strings
                messageSuiteNoArgs.add(versionNumber);
                messageSuiteNoArgs.add(pluginName);
                messageSuiteNoArgs.add(description);
                messageSuiteNoArgs.add(commands);

                //here, I am singling every string in my list out
                for (String item : messageSuiteNoArgs) {

                    //here, I am sending the player the string for every item that I placed into the list
                    player.sendMessage(item);
                }

                return true;
            }
            //if there is one argument after "/lvl," the command will execute the code block
            else if (strings.length == 1) {

                //if the 1st argument after "/lvl" = "help," then the following code block will be executed
                if (strings[0].equalsIgnoreCase("help")) {

                    //here, I have created a list that will hold strings
                    List<String> messageUsage = new ArrayList<String>();

                    //here, I have added to the list of strings; these are the list of commands that I will add
                    messageUsage.add(cmdLabel_help);
                    messageUsage.add(cmdLabel_courses);
                    messageUsage.add(cmdLabel_course);
                    messageUsage.add(cmdLabel_enroll);
                    messageUsage.add(cmdLabel_ecourses);
                    messageUsage.add(cmdLabel_test);
                    messageUsage.add(cmdLabel_set);
                    messageUsage.add(cmdLabel_pass);
                    messageUsage.add(cmdLabel_fail);
                    messageUsage.add(cmdLabel_add);
                    messageUsage.add(cmdLabel_edit);

                    //this will send a message to the player "Commands include:"
                    player.sendMessage("Commands include:");

                    //for every entry into the list explaining the available commands, it will display it to the player
                    for (String itemMU : messageUsage) {
                        player.sendMessage(usage + itemMU + cmdVarExample);
                    }
                    //this message explains where the user can go to get more help on using the plugin
                    //will make this configurable from the config file
                    player.sendMessage(lib);

                    return true;
                }
                //working
                else if (strings[0].equalsIgnoreCase("courses")) {

                    //check to see if course has values
                    //check to see if the course exists
                    //create default information
                    //If course does not exist, add the course to the table using the default information
                    try {
                        console.sendMessage(pluginConsoleDescription + pluginDataConnection);
                        PreparedStatement selectAvailableCourses = MySQL.getConnection().prepareStatement("SELECT course_code From avail ");
                        ResultSet rs = selectAvailableCourses.executeQuery();
                        console.sendMessage(pluginConsoleDescription + pluginDataConnectionCompleted);

                        player.sendMessage(checking);
                        player.sendMessage(coursesInclude);

                        while (rs.next()) {

                            player.sendMessage("- " + rs.getString("course_code"));

                        }
                    } catch (Exception e) {
                        console.sendMessage(pluginConsoleDescription + pluginDataConnectionFail);
                        e.printStackTrace();


                        return true;
                    }
                }

                else if (strings[0].equalsIgnoreCase("course")) {

                        player.sendMessage(tooFew + usage + cmdLabel_course + cmdVarExample);
                        return true;
                    }
                }

            else if (strings.length == 2) {

                if (strings[0].equalsIgnoreCase("course")) {
                    return true;
                } else if (strings[0].equalsIgnoreCase("enroll")) {
                    return true;
                } else if (strings[0].equalsIgnoreCase("ecourses")) {
                    return true;
                } else if (strings[0].equalsIgnoreCase("test")) {
                    return true;
                } else if (strings[0].equalsIgnoreCase("add")) {

                    try {
                        PreparedStatement checkingForCourse = MySQL.getConnection().prepareStatement("SELECT" + " course_code " +
                                "From" + " avail " + "WHERE" + " course_code " + "LIKE" + " ? " + ";");
                        checkingForCourse.setString(1, strings[1].toLowerCase());
                        ResultSet rs = checkingForCourse.executeQuery();
                        player.sendMessage(pluginName + pluginDataConnection);
                        player.sendMessage(pluginName + pluginDataConnectionCompleted);
                        List<String> rsList = new ArrayList<>();

                        while(rs.next()){
                            rsList.add(rs.getString("course_code"));
                        }
                        if (!rsList.contains(strings[1].toLowerCase())) {

                            try {
                                String preparedQueryAdd = "INSERT INTO lvlearning.avail (course_code) VALUES (?);";
                                console.sendMessage(preparedQueryAdd);
                                PreparedStatement addToCourseList = MySQL.getConnection().prepareStatement(preparedQueryAdd);
                                addToCourseList.setString(1, strings[1].toLowerCase());
                                addToCourseList.executeUpdate();
                                player.sendMessage(pluginName + pluginDataConnection);
                                player.sendMessage(pluginName + pluginDataConnectionCompleted);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        else {
                            player.sendMessage(errorCourseOnList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }//end of add code block

                return true;
            }

            else if (strings.length == 3) {

                if (strings[0].equalsIgnoreCase("test")) {
                    return true;
                }
                return true;
            }//end of commands with 3 additional arguments

            else {
                player.sendMessage(error);
                return true;
            }
                return true;
            }//if command = LVL code block end
        return true;
        }
    }

