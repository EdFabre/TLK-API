package tlktools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import static tlktools.TLKTools.driver;

/**
 * This Class Handles crawling for Army info.
 *
 * @author EDDY
 */
public class Army extends TLKTools {

    static List<WebElement> tCols;
    

    /**
     * Simulates login sequence, then grabs information from various pages.
     */
    public static void loadInstance() {
        getCastleInfo();

        try {
            //Prints commanders and info within each standing army
            getArmies(0, false);
        } catch (IOException ex) {
            Logger.getLogger(TLKTools.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Simulates login sequence like overridden method, but will print the info
     * to console if set to true.
     *
     * @param printToConsole
     */
    public static void loadArmy(boolean printToConsole) {
        getCastleInfo();

        try {
            //Prints commanders and info within each standing army
            getArmies(0, printToConsole);
        } catch (IOException ex) {
            Logger.getLogger(TLKTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns formatted list to be used on table for given army.
     *
     * @param army
     * @return
     */
    public static List<String[]> tableList(int army) {
        
        List<String[]> listToReturn = new ArrayList<>();
        for (int i = 0; i < allArmies.size(); i++) {
            if (allArmies.get(i)[0].equals("Army: " + String.valueOf(army))) {
                listToReturn.add(allArmies.get(i + 2));
                for (int j = i + 3; j < allArmies.size(); j++) {
                    if (allArmies.get(j)[0].contains("Army:")) {
                        break;
                    }
                    listToReturn.add(allArmies.get(j));
                }
            } 
        }
        return listToReturn;
    }

    /**
     * This method clicks into the castle page and storing army information into
     * the global armies array.
     */
    public static void getCastleInfo() {
        Login.goTo("Castle");
        // Gathers information on number of standing armies
        armies.clear();
        List<WebElement> oddArmies = new ArrayList<>();
        List<WebElement> evenArmies = new ArrayList<>();

        try {
            oddArmies = driver.findElements(By.className("odd"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            evenArmies = driver.findElements(By.className("even"));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        while (!oddArmies.isEmpty() || !evenArmies.isEmpty()) {
            if (!oddArmies.isEmpty() && !evenArmies.isEmpty()) {
                armies.add(oddArmies.get(0));
                oddArmies.remove(0);
                armies.add(evenArmies.get(0));
                evenArmies.remove(0);
            }
            if (!oddArmies.isEmpty() && evenArmies.isEmpty()) {
                armies.add(oddArmies.get(0));
                oddArmies.remove(0);
            }
        }

    }

    /**
     * Prints the show_army tables for each standing army if armyInt of 0 is
     * entered. But will print individual armies specified by armyInt. True to
     * print armies to the console. For example, armyInt=1 returns first army in
     * castle and so forth.
     *
     * @param armyInt
     * @param printArmyToConsole
     */
    public static void getArmies(int armyInt, boolean printArmyToConsole) throws IOException {

        armyViews.clear();
        if (printArmyToConsole == true) {
            for (WebElement a : armies) {
                armyViews.add(a.findElement(
                        By.cssSelector("a[href*='show_army']"))
                        .getAttribute("href"));
            }
            if (armyInt <= 0) {
                for (int i = 0; i < armies.size(); i++) {
                    getArmy((i + 1), printArmyToConsole);
                }
            } else {
                getArmy(armyInt, printArmyToConsole);
            }
        } else {
            for (WebElement a : armies) {
                armyViews.add(a.findElement(
                        By.cssSelector("a[href*='show_army']"))
                        .getAttribute("href"));
            }
            if (armyInt <= 0) {
                for (int i = 0; i < armies.size(); i++) {
                    getArmy((i + 1), printArmyToConsole);
                }
            } else {
                getArmy(armyInt, printArmyToConsole);
            }
        }
    }

    /**
     * Helper for getArmies. Retrieves individual army.
     *
     * @param armyInt
     * @param printArmyToConsole
     * @throws IOException
     */
    public static void getArmy(int armyInt, boolean printArmyToConsole) throws IOException {
        //Login.goToHomePage();
        Army.getCastleInfo();
        if (printArmyToConsole == true) {
            if (armyInt < armyViews.size() + 1) {
                int i = armyInt - 1;
                armyInfo(i, printArmyToConsole);
                driver.get(armyViews.get(i));
                try {
                    pullArmyTable(getInnerHTML(armyTable), i);
                } catch (IOException ex) {
                    Logger.getLogger(Army.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Army Doesn't Exist!");
            }
        } else if (armyInt < armyViews.size() + 1) {
            int i = armyInt - 1;
            armyInfo(i, printArmyToConsole);
            driver.get(armyViews.get(i));
            try {
                pullArmyTable(getInnerHTML(armyTable), i);
            } catch (IOException ex) {
                Logger.getLogger(Army.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Army Doesn't Exist!");
        }
    }

    /**
     * ************************************************************************
     * Gets Army Info * 
     *************************************************************************
     */
    /**
     * Prints out relevant info of desired army.
     */
    public static void armyInfo(int army, boolean printArmyToConsole) {
        if (printArmyToConsole) {
            tCols = armies.get(army).findElements(By.tagName("td"));
            aInfo.add("Army Number: " + (army + 1) + "/" + Integer.toString(numOfArmies()));
            aInfo.add("Army Name: " + getArmyName());
            aInfo.add("Commander: " + getArmyCommander());
            aInfo.add("Location: " + getArmyCity());
            aInfo.add("Strength: " + Integer.toString(getArmyStrength()));
            aInfo.add("Size: " + Integer.toString(getArmySize()));
            aInfo.add("-----");
            for (String line : aInfo) {
                System.out.println(line);
            }
            tCols.clear();
        } else {
            tCols = armies.get(army).findElements(By.tagName("td"));
            aInfo.add("Army Number: " + (army + 1) + "/" + Integer.toString(numOfArmies()));
            aInfo.add("Army Name: " + getArmyName());
            aInfo.add("Commander: " + getArmyCommander());
            aInfo.add("Location: " + getArmyCity());
            aInfo.add("Strength: " + Integer.toString(getArmyStrength()));
            aInfo.add("Size: " + Integer.toString(getArmySize()));
            aInfo.add("-----");
            tCols.clear();
        }
    }

    /**
     * Returns the number of standing armies from castle page.
     *
     * @return
     */
    public static int numOfArmies() {
        return armies.size();
    }

    /**
     * Returns Commander of given army.
     *
     * @param army
     * @return
     */
    public static String getArmyCommander() {
        return tCols.get(1).getText();
    }

    /**
     * Returns Location of given army.
     *
     * @param army
     * @return
     */
    public static String getArmyCity() {
        return tCols.get(2).getText();
    }

    /**
     * Returns Size of given army.
     *
     * @param army
     * @return
     */
    public static int getArmySize() {
        return Integer.parseInt(tCols.get(3).getText());
    }

    /**
     * Returns Strength of given army.
     *
     * @param army
     * @return
     */
    public static int getArmyStrength() {
        return Integer.parseInt(tCols.get(4).getText());
    }

    /**
     * Returns Description of given army.
     *
     * @param army
     * @return
     */
    public static String getArmyName() {
        if (tCols.get(6).getText().equals("")) {
            return tCols.get(6).findElement(By
                    .name("purpose")).getAttribute("value");
        } else {
            return tCols.get(6).getText();
        }
    }

    /**
     * Retrieves innerHTML of table Class Name
     *
     * @param tableName
     * @return
     */
    public static String getInnerHTML(String tableName) {
        WebElement element = driver.findElement(By.className(tableName));
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", element);
    }

    /**
     * Pulls table given its innerHTML block of code. And populates Appropriate
     * Lists.
     *
     * @param innerHTML
     * @throws IOException
     */
    public static void pullArmyTable(String innerHTML, int whichArmy) throws IOException {
        army.clear();
        // TODO Auto-generated catch block
        String[] rowTxt;
        String source = "<table>" + innerHTML + "<table>";
        //System.out.println(source);
        Document doc = Jsoup.parse(source, "UTF-8");

        for (Element rowElmt : doc.getElementsByTag("tr")) {
            Elements cols = rowElmt.getElementsByTag("th");
            if (cols.isEmpty()) {
                cols = rowElmt.getElementsByTag("td");
            }
            rowTxt = new String[cols.size()];
            for (int i = 3; i < rowTxt.length; i++) {
                rowTxt[0] = onlineFormatter(cols.get(0).outerHtml());
                rowTxt[1] = rankFormatter(cols.get(2).text());
                rowTxt[2] = cols.get(2).text().replace(rowTxt[1], "");
                rowTxt[i] = cols.get(i).text();
            }
            army.add(rowTxt);
        }
        String[] s = {"Army: " + Integer.toString(whichArmy + 1)};
        allArmies.add(s);
        allArmies.addAll(army);
    }

    /**
     * Fixes online tabs from an image to String.
     *
     * @param imgPath
     * @return
     */
    public static String onlineFormatter(String imgPath) {
        if (imgPath.contains("online")) {
            return "ON";
        } else if (imgPath.contains("offline")) {
            return "OFF";
        } else {
            return "NUL";
        }
    }

    /**
     * Fixes ranks that are move than one word. ex. "Marshal of the
     * loadInstance".
     *
     * @param imgPath
     * @return
     */
    public static String rankFormatter(String imgPath) {

        if (imgPath.split(" ")[0].contains("Vice")) {
            return "Vice Marshal";
        } else if (imgPath.split(" ")[0].contains("Marshal")) {
            if (imgPath.contains("of")) {
                return "Marshal of the Army";
            }
            return "Marshal";
        } else {
            return imgPath.split(" ")[0];
        }
    }

    public static List<String[]> listFormatter(List<String[]> list) {
        List<String[]> newList = list;

        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).length != 6) {
                newList.remove(i);
                i--;
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            for (int j = 0; j < 6; j++) {
                if (newList.get(i)[j].contains("+")) {
                    newList.remove(i);
                    i--;
                }
            }
        }
        return newList;
    }
}
