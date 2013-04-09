
package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {
    public static void main(String[] args) {
        WebDriver driver = new HtmlUnitDriver();

        loginSucces(driver);
        loginFaill(driver);
        createUserSucces(driver);
        createUserAndLogin(driver);
        
    }
    
    private static void login(WebDriver driver, String username, String password){
        driver.get("http://localhost:8080");
        System.out.println( driver.getPageSource() );
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click();         
        System.out.println("==");        
        System.out.println( driver.getPageSource() );
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();
        System.out.println("==");
        System.out.println( driver.getPageSource() );
    }
    
    private static void createUser(WebDriver driver, String username, String password
            , String passWordConfirmation){
        driver.get("http://localhost:8080");
        System.out.println( driver.getPageSource() );
        WebElement element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
        
        System.out.println("==");
        
        System.out.println( driver.getPageSource() );
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(passWordConfirmation);
        element = driver.findElement(By.name("add"));
        element.submit();
        
        System.out.println("==");
        System.out.println( driver.getPageSource() );
    }
    
    private static void loginSucces(WebDriver driver){
        login(driver, "pekka", "akkep");
    }
    
    private static void loginFaill(WebDriver driver){
        login(driver, "pekka", "wrong");
    }
    
    private static void createUserSucces(WebDriver driver){
        createUser(driver, "matti", "1ttammatt1", "1ttammatt1");
    }
    
    private static void createUserAndLogin(WebDriver driver){
        String user = "newUser";
        String pass = "pa55word";
        createUser(driver, user, pass, pass);
        login(driver, user, pass);
    }
}
