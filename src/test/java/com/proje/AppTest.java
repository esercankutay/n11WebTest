package com.proje;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest 
{
    WebDriver mDriver;
    Logger mLogger = LogManager.getLogger(AppTest.class);


    @Before
    public void setupTest(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        mDriver = new ChromeDriver();
        mDriver.manage().window().maximize();
        mLogger.info("Test başladı");
    }
    @Test
    public void n11Test()
    {

        // https://www.n11.com/ adresini aç / Sayfaya özgü title ile test et
        mDriver.navigate().to("https://www.n11.com/");
        Assert.assertEquals("n11.com - Alışverişin Uğurlu Adresi" , mDriver.getTitle());
        mLogger.info("Anasayfa açıldı");

        // "Giriş yap" linkine tıkla / Login sınıfı fonksiyonu ile login olmayı dene / Sonucu test et
        mDriver.findElement(By.className("btnSignIn")).click();
        Login mLogin = new Login("n11odev@cankutayeser.com","*-123qweasd*-" , mDriver);
        mLogin.tryToLogin();
        Assert.assertEquals("Hesabım" , mDriver.findElement(By.xpath("//div[@class='myAccount']/a[1]")).getAttribute("title"));
        mLogger.info("Giriş yapıldı");

        // Iphone anahtarını Search sınıfı fonksiyonu ile ara / Sonucu url ile test et
        Search mSearch = new Search("Iphone" , mDriver);
        mSearch.tryToSearch();
        Assert.assertEquals("Iphone" , mDriver.getCurrentUrl().replace("https://www.n11.com/arama?q=",""));
        mLogger.info("Iphone kelimesi arandı");

        // Pagination aşağıda olduğu için sayfayı aşağı kaydır / 2.sayfa öğesine tıkla / Sonucu url ile test et
        ((JavascriptExecutor) mDriver).executeScript("window.scrollTo(0,document.body.scrollHeight);");
        mDriver.findElement(By.xpath("//div[@class='pagination']/a[2]")).click();
        Assert.assertEquals("2",mDriver.getCurrentUrl().replace("https://www.n11.com/arama?q=Iphone&pg=",""));
        mLogger.info("Sonuçlar sayfasından 2. sayfa açıldı");

        // Sonuclardan 3.yü favorilere ekle / Hesabım menüsüne yönel / Tıklanacak elementin görünmesini bekle / Tıkla
        // Açılan sayfada favori listesine tıkla / Sonucu url ile test et
        mDriver.findElement(By.xpath("//section[@class='group listingGroup resultListGroup']/div[2]/ul/li[3]/div/div[2]/span[2]")).click();
        mLogger.info("3. ürün favorilere eklendi");
        Actions mAction = new Actions(mDriver);
        mAction.moveToElement(mDriver.findElement(By.xpath("//div[@class='myAccount']/a[1]"))).build().perform();
        WebDriverWait wait = new WebDriverWait(mDriver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='İstek Listem / Favorilerim']")));
        mAction.moveToElement(mDriver.findElement(By.xpath("//a[text()='İstek Listem / Favorilerim']"))).click().build().perform();
        mDriver.findElement(By.xpath("//h4[@class='listItemTitle']")).click();
        Assert.assertEquals("https://www.n11.com/hesabim/favorilerim", mDriver.getCurrentUrl());
        mLogger.info("Favoriler sayfası açıldı");

        //Eklenen ürünü xpath ile bulup sil / Bilgi onayına tıklamaya üşendiğin için sayfayı yenile :D / Sayfadan silindiğini boş sayfa sınıfı(emptyWatchList) ile test et
        mDriver.findElement(By.xpath("//div[@class='group listingGroup wishListGroup']/div[2]/ul/li/div/div[3]/span")).click();
        mDriver.navigate().refresh();
        Assert.assertEquals("emptyWatchList hiddentext",mDriver.findElement(By.xpath("//div[@id='watchList']/div")).getAttribute("class"));
        mLogger.info("Eklenen ürün favorilerden silindş");

        //Hesabım menüsüne yönel / Tıklanacak elementin görünmesini bekle / Tıkla
        mAction.moveToElement(mDriver.findElement(By.xpath("//div[@class='myAccount']/a[1]"))).build().perform();
        wait = new WebDriverWait(mDriver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Çıkış Yap']")));
        mAction.moveToElement(mDriver.findElement(By.xpath("//a[text()='Çıkış Yap']"))).click().build().perform();
        mLogger.info("Çıkış yapıldı");

    }
    @After
    public void endTest(){
        mDriver.close();
        mDriver.quit();
        mLogger.info("Test tamamlandı");
    }
}
