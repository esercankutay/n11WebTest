package com.proje;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Search {
    WebDriver mDriver;
    String mKeyword;
    By keyword = By.id("searchData");
    By button = By.className("searchBtn");
    public Search(String keyword, WebDriver driver) {
        this.mKeyword = keyword;
        this.mDriver = driver;
    }

    public void tryToSearch(){
        mDriver.findElement(keyword).sendKeys(mKeyword);
        mDriver.findElement(button).click();
    }
}
