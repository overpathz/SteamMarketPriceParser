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
    public static void main(String[] args) throws InterruptedException {
        String NULP_URL = "https://steamcommunity.com/market/listings/730/AK-47%20%7C%20Blue%20Laminate%20%28Well-Worn%29";

        System.setProperty("webdriver.chrome.driver", "E:\\programms\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get(NULP_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[7]/div[2]/div[2]/div[4]/div[1]/div[3]/div[4]/div[1]/div[1]/div[2]/span[2]")));

        driver.findElement(By.xpath("/html/body/div[1]/div[7]/div[2]/div[2]/div[4]/div[1]/div[3]/div[4]/div[1]/div[2]/span")).click();

        String weaponTitle = driver.findElement(By.id("largeiteminfo_item_name")).getText();
        System.out.println(weaponTitle);
        System.out.println();

        String reqToBuyStartedPrice = driver.findElement(By.id("market_commodity_buyrequests")).getText();
        System.out.println(reqToBuyStartedPrice);
        System.out.println();

        WebElement tableWithPrices = driver.findElement(By.id("market_commodity_buyreqeusts_table"));
        System.out.println(tableWithPrices.getText());

        Integer maxPaginNumber = Integer.parseInt(driver.findElement(By.xpath("/html/body/div[1]/div[7]/div[2]/div[2]/div[4]/div[1]/div[3]/div[4]/div[3]/div[1]/span[2]/span[7]")).getText());

        double avgPrice = 0;
        List<String> rawPrices = new ArrayList<>();

        for (int i = 0; i < maxPaginNumber; i++) {
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

        List<String> otherRaw = rawPrices.stream().map(x-> x.split(" ")[0]).toList();
        System.out.println(otherRaw);

    }
}
