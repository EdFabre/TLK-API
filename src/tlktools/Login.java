package tlktools;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static tlktools.TLKTools.driver;

/**
 * This class handles logging into the TLK game, and clicking on various pages.
 *
 * @author EDDY
 */
public class Login extends TLKTools {

    static String baseUrl = "http://lastknights.com";

    /**
     * Prompts user for their credentials then logs them in. Credentials are not
     * saved anywhere!
     *
     */
    public static boolean login(String uName, String pWord) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        /* Finds textbox elements and Login Button */
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement button = driver.findElement(By.name("login"));

        /* Logs into the game */
        try {
            Scanner scanner = new Scanner(System.in);  // Reading from System.in
            //System.out.print("Username: ");
            //uName = scanner.nextLine();
            //System.out.print("Password: ");
            //pWord = scanner.nextLine();
            username.clear();
            username.sendKeys(uName);
            password.clear();
            password.sendKeys(pWord);
            button.click();
        } catch (Throwable e) {
            //System.out.println("There was a problem retry?" + e.getMessage());
        }
        if (successfulLogin()) {
            System.out.println("Login Successful");
            return true;
        } else {
            System.out.println("Incorrect Login! Please retry.");
            return false;
            //login();
        }

    }

    /**
     * Checks for successful login
     *
     * @return
     */
    public static boolean successfulLogin() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        boolean exists = driver.findElements(By.className("login-error")).isEmpty();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return exists;
    }

    /**
     * Navigates to Home page.
     */
    public static void goToHomePage() {
        driver.get(baseUrl);
    }

    /**
     * Goes to the specific location on TLK.
     * @param loc 
     */
    public static void goTo(String loc) {
        String path = baseUrl+getVirtualPath(loc);
        // Enters Castle page
        driver.get(path);
    }
    
    /**
     * Retrieves virtual path of page within TLK.
     * @param page
     * @return 
     */
    public static String getVirtualPath(String page) {
        switch (page) {
            case "Castle":
                return "/index.php?loc=castle";
            case "Border":
                return "/index.php?loc=border";
            case "Hospital":
                return "/index.php?loc=hospital";
            case "Barracks":
                return "/index.php?loc=barracks";
            case "Armory":
                return "/index.php?loc=armory";
            case "Tournament":
                return "/index.php?loc=tournament";
            case "Headquarters":
                return "/index.php?loc=hq";
        }
        return "";
    }
    
}
