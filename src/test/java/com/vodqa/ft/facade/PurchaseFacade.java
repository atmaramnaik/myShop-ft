package com.vodqa.ft.facade;

import com.vodqa.ft.model.ShippingInfo;
import com.vodqa.ft.pages.*;
import com.vodqa.ft.pages.factory.PageFactory;
import com.vodqa.ft.strategy.CalulationService;
import org.openqa.selenium.WebDriver;
import java.util.Arrays;

public class PurchaseFacade {
    WebDriver driver;
    ProductsPage productsPage;
    ProductPage productPage;
    ReviewOrderPage reviewOrderPage;
    ShippingInfoPage  shippingInfoPage;
    SignInPage signInPage;

    public PurchaseFacade(WebDriver driver) {
        this.driver = driver;
        productsPage=PageFactory.resolve(driver,ProductsPage.class);
        productPage=PageFactory.resolve(driver,ProductPage.class);
        reviewOrderPage=PageFactory.resolve(driver,ReviewOrderPage.class);
        shippingInfoPage=PageFactory.resolve(driver,ShippingInfoPage.class);
        signInPage=PageFactory.resolve(driver,SignInPage.class);
    }

    public void purchaseItem(int item,String size,String color,String price, ShippingInfo shippingInfo){
                productsPage
                .navigate();

                productsPage.validateCategories(Arrays.asList("All","Food","Fruits","Electronics","Clothes Men","Clothes Women","Drinks","Groceries"));
                productsPage.clickNthProduct(item);
                productPage.setSize(size);
                productPage.setColor(color);
                productPage.validatePrice(price);
                productPage.buy();
                signInPage.continueAsGuest();
                shippingInfoPage.setShippingAndContinue(shippingInfo);
                reviewOrderPage.validateItemPrice(String.format("%.2f",Double.parseDouble(price)));
                reviewOrderPage.validateGiftPackingPrice(String.format("%.2f", CalulationService.calculateGift(Double.parseDouble(price))));
                reviewOrderPage.validateShippingPrice(String.format("%.2f", CalulationService.calculateShipping(Double.parseDouble(price))));
                if(shippingInfo.getCountry().equals("India")){
                    reviewOrderPage.validateTaxPrice(String.format("%.2f", CalulationService.calculateVatTax(Double.parseDouble(price),shippingInfo.getCountry())));
                } else{
                    reviewOrderPage.validateTaxPrice(String.format("%.2f", CalulationService.calculateSalesTax(Double.parseDouble(price),shippingInfo.getCountry())));
                }

        reviewOrderPage.placeOrder();
    }
}
