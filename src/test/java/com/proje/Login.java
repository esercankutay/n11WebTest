package com.proje;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {

    String mUserName,mPassword;
    WebDriver mDriver;
    By username = By.name("email");
    By password = By.name("password");
    By button = By.name("returnUrl");

    public Login(String username, String password, WebDriver driver) {
        this.mUserName = username;
        this.mPassword = password;
        this.mDriver = driver;
    }

    public void tryToLogin(){
        mDriver.findElement(username).sendKeys(mUserName);
        mDriver.findElement(password).sendKeys(mPassword);
        mDriver.findElement(button).submit();
    }
}
