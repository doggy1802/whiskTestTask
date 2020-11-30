import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WhiskMain {

    public WebDriver driver;
    public String listName;

    public WhiskMain(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    @FindBy(xpath = "//*[contains(@data-testid, 'UI_KIT_INPUT')]")
    private WebElement loginField;

    @FindBy(xpath = "//*[contains(@placeholder, 'Name your list')]")
    private WebElement listNameField;


    @FindBy(xpath = "//*[contains(@data-testid, 'auth-continue-button')]")
    private WebElement loginBtn;

    @FindBy(xpath = "//*[text()='Shopping']")
    private WebElement shoppingButton;


    @FindBy(xpath = "//*[contains(@data-testid, 'create-new-shopping-list-button')]")
    private WebElement createShoppingList;

    @FindBy(xpath = "//*[contains(@data-testid, 'create-new-shopping-list-create-button')]")
    private WebElement createShoppingListAccept;

    @FindBy(xpath = "//*[contains(@data-testid, 'vertical-dots-shopping-list-button')]")
    private WebElement threeDotsDown;

    @FindBy(xpath = "//*[contains(@data-testid, 'shopping-list-delete-menu-button')]")
    private WebElement deleteShoppingList;

    @FindBy(xpath = "//*[contains(@data-testid, 'confirm-delete-button')]")
    private WebElement deleteShoppingListAccept;

    @FindBy(xpath = "//*[contains(@data-testid, 'desktop-add-item-autocomplete')]")
    private WebElement addItem;


    public int itemCount(String name) {
        return driver.findElements(new By.ByXPath(String.format("//*[contains(@data-testid, '%s')]", name))).size();

    }

    public boolean checkExist(String element) {

        List<WebElement> results = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(new By.ByXPath("//*[contains(@data-testid, 'shopping-list-item-name')]")));

        return (
                results.stream()
                        .anyMatch(el -> el.getText().contains(element)));

    }


    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    public String getListName() {
        return listNameField.getText();
    }

    public void inputGoods(String login) {
        addItem.sendKeys(login);
        WebElement first = driver.findElement(By.className("sc-7v3pks"));
        first.click();
    }


    public void clickLoginBtn() {
        loginBtn.click();
    }

    public void clickAddItemBtn() {
        addItem.click();
    }

    public void createList() {
        createShoppingList.click();
        listName = getListName();
        createShoppingListAccept.click();
    }

    public void deleteList() {
        threeDotsDown.click();
        deleteShoppingList.click();
        deleteShoppingListAccept.click();
    }


    public void clickShoppingBtn() {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", shoppingButton);
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@data-testid, 'shopping-list-nav-link')]"))).click();
    }
}