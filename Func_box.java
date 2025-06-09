package com.demo.apitest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Func_box {
    private final WebDriver driver;
    private WebElement we;
    private WebElement ces;
    private WebElement wc;
    private List<WebElement> ges;
    private final Logger log = LogManager.getLogger("");
    private final File my_path = new File(System.getProperty("user.dir"));
    private ExtentReports exReport;
    private ExtentTest exTest;
    private final String CREATE_DATA_STREAM = "\\src\\main\\resources\\Log\\checklist\\summary.xls";

    public Func_box(WebDriver driver) {
        this.driver = driver;
    }

    // Start > Extent report
    public void start_exReport(String ExBox) {
        String START_EXREPORT = "\\src\\main\\resources\\Log\\report\\" + "ExReport" + ExBox + ".html";
        exReport = new ExtentReports(my_path + START_EXREPORT);
        exTest = exReport.startTest("Boot Test");
    }

    // End > Extent report
    public void close_exReport() {
        exReport.endTest(exTest);
        exReport.flush();
    }

    // Log message[S]
    public void log_message(String class_name, String info) {
        // log4j
        log.info("{} > {}", class_name, info);
        // extentreports
        exTest.log(LogStatus.INFO, class_name + " > " + info);
        // TestNG output
        Reporter.log("[S]ReportLog >> " + class_name + " > " + info, true);
    }

    // Date time
    public String date_time() {
        DateFormat dateformat = new SimpleDateFormat("yyMMdd_HHmmss");
        Date my_date = new Date();
        return dateformat.format(my_date);
    }

    // Take screenshot
    public String take_screenshot(String file_name, String pass_fail, String save_folder) throws Exception {
        file_name = pass_fail + file_name + "_" + date_time() + ".png";
        String TAKE_SCREENSHOT = "\\src\\main\\resources\\Screenshot\\Image\\" + save_folder + "\\";
        String file_path = my_path + TAKE_SCREENSHOT;
        File src_file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src_file, new File(file_path + file_name));
        return file_path + file_name;
    }

    // Add screenshot > extent report
    public void extent_screenshot(String path) {
        String img_path = exTest.addScreenCapture(path);
        exTest.log(LogStatus.FAIL, "[FAIL]", img_path);
    }

    // Mouse action
    /*public void mouse_action(WebElement item) {
        Actions ac = new Actions(driver);
        ac.moveToElement(item).click().perform();
    }*/

    // Wait element
    public WebElement wait_element(String type, String path, String msg, int wait_sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(wait_sec));
        try {
            switch (type) {
                case "id" -> we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(path)));
                case "name" -> we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(path)));
                case "class" -> we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(path)));
                case "xpath" -> we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
            }
        } catch (Exception e) {
            log_message(this.getClass().getName(), "API Not Found!" + " >> " + msg);
            return null; // keep running
        }
        return we;
    }

    // Wait clickable
    public WebElement wait_clickable(WebElement item, String msg, int wait_sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(wait_sec));
        try {
            wc = wait.until(ExpectedConditions.elementToBeClickable(item));
        } catch (Exception e) {
            log_message(this.getClass().getName(), "API Not Clickable!" + " >> " + msg);
            return null; // keep running
        }
        return wc;
    }

    // Find element
    public WebElement find_element(String type, String path, String msg) {
        try {
            switch (type) {
                case "id" -> ces = driver.findElement(By.id(path));
                case "name" -> ces = driver.findElement(By.name(path));
                case "class" -> ces = driver.findElement(By.className(path));
                case "xpath" -> ces = driver.findElement(By.xpath(path));
            }
        } catch (Exception e) {
            log_message(this.getClass().getName(), "API Not Found!" + " >> " + msg);
            return null; // keep running
        }
        return ces;
    }

    // Find elements
    public List<WebElement> find_elements(String type, String path, String msg) {
        try {
            switch (type) {
                case "id" -> ges = driver.findElements(By.id(path));
                case "name" -> ges = driver.findElements(By.name(path));
                case "class" -> ges = driver.findElements(By.className(path));
                case "xpath" -> ges = driver.findElements(By.xpath(path));
            }
        } catch (Exception e) {
            log_message(this.getClass().getName(), "APIs Not Found!" + " >> " + msg);
            return null; // keep running
        }
        return ges;
    }

    // JS click
    public void js_click(WebElement parent_menu) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", parent_menu);
    }

    // Update data
    public void update_data(int test_step, String auto_result) throws IOException {
//        FileInputStream fs = new FileInputStream(my_path + "\src\main\resources\Log\checklist\summary.xls");
        FileInputStream fs = new FileInputStream(my_path + CREATE_DATA_STREAM);

        HSSFWorkbook workbook = new HSSFWorkbook(fs);
        HSSFSheet sheet = workbook.getSheet("ENG");
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        HSSFRow row;
        HSSFCell cell;

        // Input data
        row = sheet.getRow(test_step);
        cell = row.createCell(4);

        // Set "Pass" to GREEN color
        if (auto_result.equals("Pass")) {
            font.setColor((short) 50);
        }

        if (auto_result.equals("Fail")) {
            font.setColor((short) 2);
        }

        titleStyle.setFont(font);
        cell.setCellStyle(titleStyle);
        cell.setCellValue(auto_result);

        // Auto column
        for (int auto_column = 0; auto_column < Objects.requireNonNull(row).getLastCellNum(); auto_column++) {
            sheet.autoSizeColumn(auto_column);
        }
        // Write data
        FileOutputStream out = new FileOutputStream(my_path + CREATE_DATA_STREAM);

        workbook.write(out);
        out.close();
        workbook.close();
    }

}
