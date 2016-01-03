package tlktools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class TLKTools {

    static ArrayList<String[]> army = new ArrayList<>();
    static ArrayList<String[]> allArmies = new ArrayList<>();
    static List<String> armyViews = new ArrayList<>();
    static List<String> aInfo = new ArrayList<>();
    static List<WebElement> armies = new ArrayList<>();
    static List<String> trainingRun = new ArrayList<>();

    static WebDriver driver = new HtmlUnitDriver(true);
    static String baseUrl = "http://lastknights.com";
    static String armyTable = "headquarters_nowidth";
    static String uName;
    static String pWord;

    public static void main(String[] args) throws Exception {
        runProg(); // Starts Program
        //printListOfStringArrays(allArmies);
        //System.out.println("Next Prints");
        //printListOfStringArrays(Army.tableList(4));
        //Army.tableList(0);
        //printListOfStrings(aInfo);
        for (int i = 1; i <= armies.size(); i++) {
            GeneralArmyInfo.generateTable(Army.tableList(i)); // Populates a table with all info
        }
    }

    public static void printListOfStringArrays(List<String[]> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(Arrays.toString(list.get(i)));
        }
    }

    public static void printListOfStrings(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    /**
     * Runs the program
     */
    public static void runProg() {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        //Logs the user into the game
        //Login.login();

        //Clicks into Castle and stores standing army info into armies array
        Army.loadInstance();
        //System.out.println(army.size());
        //System.out.println(armyViews.size());
        //System.out.println(aInfo.size());
        //System.out.println(armies.size());
        //System.out.println(allArmies.size());

    }

}
