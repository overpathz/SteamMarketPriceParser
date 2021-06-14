package app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SteamParser {
    public static WebDriver driver;
    public static final String URL = "https://steamcommunity.com/market/listings/730/AK-47%20%7C%20Blue%20Laminate%20%28Well-Worn%29";

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "E:\\programms\\chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[7]/div[2]/div[2]/div[4]/div[1]/div[3]/div[4]/div[1]/div[1]/div[2]/span[2]")));


        clickMoreDetailsElement();
        printTextElement("id", "largeiteminfo_item_name");
        printTextElement("id", "market_commodity_buyrequests");
        printTextElement("id", "market_commodity_buyreqeusts_table");

        double averagePrice = findAveragePrice();

        System.out.println("\nAverage USD price: " + averagePrice);

    }

    public static void clickMoreDetailsElement() {
        driver.findElement(By.xpath("/html/body/div[1]/div[7]/div[2]/div[2]/div[4]/div[1]/div[3]/div[4]/div[1]/div[2]/span")).click();
    }

    public static void printTextElementByID(String id) {
        String element = driver.findElement(By.id(id)).getText();
        System.out.println(element);
    }

    public static void printTextElement(String by, String value) {
        String element;
        switch (by) {
            case "xpath":
                element = driver.findElement(By.xpath(value)).getText();
                System.out.println(element);
                break;

            case "id":
                element = driver.findElement(By.id(value)).getText();
                System.out.println(element);
                break;

            case "class":
                element = driver.findElement(By.className(value)).getText();
                System.out.println(element);
                break;
        }
    }

    public static double findAveragePrice() throws InterruptedException {
        int maxPaginationNumber = Integer.parseInt(driver.findElement(By.xpath("/html/body/div[1]/div[7]/div[2]/div[2]/div[4]/div[1]/div[3]/div[4]/div[3]/div[1]/span[2]/span[7]")).getText());

        double avgPrice = 0;

        List<String> rawPrices = new ArrayList<>();

        for (int i = 0; i < maxPaginationNumber; i++) {
            List<WebElement> results = driver.findElements(By.cssSelector(".market_listing_price"));

            results.stream()
                    .filter(x->x.getText().contains("$"))
                    .forEach(x-> {
                        System.out.println(x.getText());
                        rawPrices.add(x.getText());
                    });

            driver.findElement(By.id("searchResults_btn_next")).click();
            Thread.sleep(2000);
        }


        System.out.println(rawPrices);
        List<String> otherRaw = rawPrices.stream().map(x-> x.split(" ")[0].substring(1)).filter(x->!x.contains(",")).toList();
        System.out.println(otherRaw);

        avgPrice = otherRaw.stream().map(Double::parseDouble).reduce(Double::sum).get() / otherRaw.size();

        return avgPrice;
    }

}