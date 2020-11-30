import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class UITest {
    public static WhiskMain whiskPage;
    public static WebDriver driver;
    public String[] goodsArray = {"Milk", "Bread", "Eggs", "Chicken", "Cheese"};

    @Before
    public void setup() {

        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

        driver = new ChromeDriver();
        whiskPage = new WhiskMain(driver);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(ConfProperties.getProperty("whiskpage"));
        System.out.println("Bro is running");
    }


    @Test
    public void whiskTest1() {


        whiskPage.inputLogin(getRandomString() + ConfProperties.getProperty("email"));


        whiskPage.clickLoginBtn();
        System.out.println("We're logged in");


        whiskPage.clickShoppingBtn();
        whiskPage.createList();
        System.out.println("Let's go shopping");


        whiskPage.clickAddItemBtn();
        System.out.println("Gonna add some items");

        Arrays.stream(goodsArray).forEachOrdered(s -> whiskPage.inputGoods(s));
        System.out.println("Five items added");

        assertEquals(whiskPage.itemCount("shopping-list-item-name"), goodsArray.length);
        Arrays.stream(goodsArray).allMatch(s -> whiskPage.checkExist(s));

        System.out.println("All goods are in the cart");

        driver.quit();
        System.out.println("Bro shutdown");

    }

    @Test
    public void whiskTest2() {

        whiskPage.inputLogin(getRandomString() + ConfProperties.getProperty("email"));

        whiskPage.clickLoginBtn();
        System.out.println("We're logged in");


        whiskPage.clickShoppingBtn();
        whiskPage.createList();
        System.out.println("Let's go shopping");


        whiskPage.deleteList();
        System.out.println("Or don't");

        assertEquals(1, whiskPage.itemCount("shopping-lists-list-name"));
        System.out.println("We have only 1 (default) list");

        driver.close();
    }

    protected String getRandomString() {
        String CAPSCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder rand = new StringBuilder();
        Random rnd = new Random();
        while (rand.length() < 10) {
            int index = (int) (rnd.nextFloat() * CAPSCHARS.length());
            rand.append(CAPSCHARS.charAt(index));
        }
        String randStr = rand.toString();
        return randStr;

    }

    @AfterClass
    public static void close(){
        driver.quit();
    }

}