package com.vodqa.ft.pages;

import com.vodqa.ft.helpers.ElementHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    //Locators
    private By ddSize=By.id("size");
    private By ddColor=By.id("color");
    private By lblPrice=By.id("price");
    private By bBuy=By.id("buyButton");

    //Page Objects
    public Select getSize(){
        return ElementHelpers.getDropDownSaflyBy(driver,ddSize);
    }
    public Select getColor(){
        return ElementHelpers.getDropDownSaflyBy(driver,ddColor);
    }
    public WebElement getBuyButton(){
        return ElementHelpers.getWebElementSaflyBy(driver,bBuy);
    }
    public WebElement getPriceLable(){
        return ElementHelpers.getWebElementSaflyBy(driver,lblPrice);
    }

    //Interactions
    public void setSize(String size){
        getSize().selectByVisibleText(size);
    }
    public void setColor(String color){
        getColor().selectByVisibleText(color);
    }

    public void buy(){
        ElementHelpers.waitForVisibilityOfElement(driver,getBuyButton());
        getBuyButton().click();
    }

    //Validations
    public void validatePrice(String price){
        ElementHelpers.waitForTextSetForElement(driver,getPriceLable());
        assertThat(getPriceLable().getText(),is(price));
    }

}
