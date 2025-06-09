package com.demo.apitest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;


public class Test_RTM_MU {
    // ----------------------------- V -----------------------------
    private Func_box mf;
    private WebDriver driver;
    private String FW_UI_Version;
    private String EXP_text;
    private String EXP_file;
    private String SonicOS_version;
    private int check_point_no = 0;
    private int reuse_step = 0;
    private final int sleep_15m = 900000;
    private final int sleep_14m = 840000;
    private final int sleep_10m = 600000;
    private final int sleep_7m = 420000;
    private final int sleep_5m = 300000;
    private final int sleep_4m = 240000;
    private final int sleep_3m = 180000;
    private final int wait_120s = 120;
    private final int wait_60s = 60;
    private final int wait_30s = 30;
    private final int wait_20s = 20;
    private final int wait_5s = 5;

    private final String save_folder = "DUT";
    // -------------------------------------------------------------

    @BeforeClass
    public void beforeClass() {
        // [S]ChromeDriver Settings
        System.setProperty(Data_box.chromeDriver_data[0], Data_box.chromeDriver_data[1]);
        ChromeOptions chromOptions = new ChromeOptions();
        chromOptions.addArguments(Data_box.Chrome_userData_TZ_box);
        chromOptions.addArguments("--lang=ja-JP");
        chromOptions.addArguments("--incognito");
        chromOptions.addArguments("--ignore-certificate-errors");
        chromOptions.addArguments("--disable-application-cache");
        chromOptions.addArguments("--disable-cache");

        // [S]Set browser to headless
//        chromOptions.addArguments("--headless");

        // [S]Set browser zoom
        chromOptions.addArguments("--force-device-scale-factor=2.30");

        driver = new ChromeDriver(chromOptions);
        mf = new Func_box(driver);
        mf.start_exReport("_RTM");
    }

    public void check_point_FW(String FW_UI_version, String FW_Check_version, int check_NO, String check_msg) throws Exception {
        // ----------------------------- V -----------------------------
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        // -------------------------------------------------------------

        // [C]Check Point
        mf.log_message(this.getClass().getName(), "----------------------------- CHECK POINT " + check_NO + " -----------------------------");
        mf.log_message(this.getClass().getName(), check_msg + "SonicOS " + FW_Check_version);
        if (FW_UI_version.contains(FW_Check_version)) {
            mf.update_data(check_NO, "Pass");
            mf.log_message(this.getClass().getName(), check_msg + GREEN + FW_UI_version + RESET);
            mf.log_message(this.getClass().getName(), GREEN + "PASS" + RESET);
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        } else {
            mf.update_data(check_NO, "Fail");
            mf.log_message(this.getClass().getName(), check_msg + RED + FW_UI_version + RESET);
            mf.log_message(this.getClass().getName(), RED + "FAIL" + RESET);
            mf.extent_screenshot(mf.take_screenshot(this.getClass().getName(), "[FAIL]cp" + check_point_no + "_", save_folder));
        }
        mf.log_message(this.getClass().getName(), "Check Point " + check_NO + " updated!");
    }

    public void check_point_UI(String check_info, int check_NO, String check_msg) throws Exception {
        // ----------------------------- V -----------------------------
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        String[] checkLists = {"OK", "成功", "購読済", "testuser", "参加済", "デバイスの登録", "N/A", "無線は利用できません", "Exit Safe Mode"};
        int gotIt = 0;
        // -------------------------------------------------------------

        // [C]Check Point
        mf.log_message(this.getClass().getName(), "----------------------------- CHECK POINT " + check_NO + " -----------------------------");
        for (String checkList : checkLists) {
            if (check_info.contains(checkList)) {
                // [D]Update data >> Excel
                mf.update_data(check_NO, "Pass");
                mf.log_message(this.getClass().getName(), GREEN + check_msg + RESET);
                mf.log_message(this.getClass().getName(), GREEN + "PASS" + RESET);
                mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
                gotIt = 1;
                break;
            }
        }

        if (gotIt == 0) {
            // [D]Update data >> Excel
            mf.update_data(check_NO, "Fail");
            mf.log_message(this.getClass().getName(), RED + "New string: " + check_info + RESET);
            mf.log_message(this.getClass().getName(), RED + "FAIL" + RESET);
            mf.extent_screenshot(mf.take_screenshot(this.getClass().getName(), "[FAIL]cp" + check_point_no + "_", save_folder));
        }
        mf.log_message(this.getClass().getName(), "Check Point " + check_NO + " updated!");
    }

    public void redirect_page(String login_port, String login_page, String goto_msg) {
        // ----------------------------- V -----------------------------
        String port = "";
        // -------------------------------------------------------------

        // [A]Redirect page
        switch (login_port) {
            case "X0" -> port = Data_box.baseUrl_TZ_box_X0;
            case "X1" -> port = Data_box.baseUrl_TZ_box_X1;
        }
        driver.get(port + login_page);
        mf.log_message(this.getClass().getName(), goto_msg);

        // [A]Close dialog
        // #####################################################################################################################################
        // 8.0.0
        if (SonicOS_version.substring(0, 9).contains("8")) dialog_close();
        // #####################################################################################################################################
    }

    public void dialog_close() {
        // [A]Click "Cancel" from "Automatic Firmware Updates"
        if (mf.wait_element("xpath", Data_box.Auto_FW_Update_Cancel_button, "Cancel 'Automatic Firmware Updates'...", wait_60s) != null) {
            mf.js_click(mf.wait_element("xpath", Data_box.Auto_FW_Update_Cancel_button, "Cancel 'Automatic Firmware Updates'...", wait_60s));
            mf.log_message(this.getClass().getName(), "Closing 'Automatic Firmware Updates'...");
        }

        // [A]Unregister banner
        if (mf.wait_element("xpath", Data_box.unregister_string, "No unregister banner, continue...", wait_5s) != null) {
            mf.js_click(mf.wait_element("xpath", Data_box.close_button, "unregister banner", wait_20s));
            mf.log_message(this.getClass().getName(), "Closing unregister banner...");
        }

        // [A]LM not available banner
        if (mf.wait_element("xpath", Data_box.LM_NA_string, "No LM not available banner, continue...", wait_5s) != null) {
            mf.js_click(mf.wait_element("xpath", Data_box.close_button, "LM banner", wait_20s));
            mf.log_message(this.getClass().getName(), "Closing LM not available banner...");
        }

        // [A]Turn on config mode if it is OFF
        if (mf.wait_element("xpath", Data_box.Config_OFF_button, "Non-config mode, continue...", wait_5s) != null) {
            mf.js_click(mf.wait_element("xpath", Data_box.Config_OFF_button, "config mode button", wait_20s));
            mf.js_click(mf.wait_element("xpath", Data_box.Preempt_OK_button, "config mode button", wait_20s));
            mf.log_message(this.getClass().getName(), "Switching to config mode...");
        }
    }

    public void login_UI(String login_pwd, String login_port) throws Exception {
        // ----------------------------- V -----------------------------
        String pwd = "";
        // -------------------------------------------------------------

        driver.get(login_port);
        mf.log_message(this.getClass().getName(), "Login to DUT...");

        switch (login_pwd) {
            case "sonicwall" -> pwd = Data_box.login_pwd_sonicwall_key;
            case "password" -> pwd = Data_box.login_pwd_password_key;
        }

        // [A]Enter "ユーザ名"
        mf.wait_element("xpath", Data_box.userName_field, "userName", wait_30s).clear();
        mf.wait_element("xpath", Data_box.userName_field, "userName", wait_30s).sendKeys(Data_box.login_name_key);
        // [A]Enter "パスワード"
        mf.wait_element("xpath", Data_box.login_password_field, "password", wait_20s).clear();
        mf.wait_element("xpath", Data_box.login_password_field, "password", wait_20s).sendKeys(pwd);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        // [A]Click "ログイン"
        mf.js_click(mf.wait_element("xpath", Data_box.login_button, "login", wait_20s));

        // [A]Preempt dialog
        if (mf.wait_element("xpath", Data_box.preempt_button, "No preempt dialog, continue...", wait_5s) != null) {
            mf.js_click(mf.wait_element("xpath", Data_box.preempt_button, "preempt", wait_20s));
            mf.log_message(this.getClass().getName(), "OK to preempt...");
        }

        // [A]Password change dialog
        if (mf.wait_element("xpath", Data_box.pwd_change_button, "No password change dialog, continue...", wait_5s) != null) {
            // [A]Password change
            mf.wait_element("xpath", Data_box.pwd_change_button, "password change button", wait_20s);
            mf.wait_element("xpath", Data_box.pwd_old_field, "old password field", wait_20s).clear();
            mf.wait_element("xpath", Data_box.pwd_old_field, "old password field", wait_20s).sendKeys(Data_box.login_pwd_password_key);
            mf.wait_element("xpath", Data_box.pwd_new_field, "new password field", wait_20s).clear();
            mf.wait_element("xpath", Data_box.pwd_new_field, "new password field", wait_20s).sendKeys(Data_box.login_pwd_sonicwall_key);
            mf.wait_element("xpath", Data_box.pwd_new_confirm_field, "new password confirm field", wait_20s).clear();
            mf.wait_element("xpath", Data_box.pwd_new_confirm_field, "new password confirm field", wait_20s).sendKeys(Data_box.login_pwd_sonicwall_key);
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
            mf.js_click(mf.wait_element("xpath", Data_box.pwd_change_button, "password change button", wait_20s));
            mf.log_message(this.getClass().getName(), "New password changed...");
            // [A]Setup Guide
            mf.js_click(mf.wait_element("xpath", Data_box.setup_guide_link, "setup guide > click here", wait_20s));
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
            mf.log_message(this.getClass().getName(), "Setup guide redirecting...");
        }

        // [A]Unregister banner
        if (mf.wait_element("xpath", Data_box.unregister_string, "No unregister banner, continue...", wait_5s) != null) {
            mf.js_click(mf.wait_element("xpath", Data_box.close_button, "unregister banner", wait_20s));
            mf.log_message(this.getClass().getName(), "Closing unregister banner...");
        }

        // [A]LM not available banner
        if (mf.wait_element("xpath", Data_box.LM_NA_string, "No LM not available banner, continue...", wait_5s) != null) {
            mf.js_click(mf.wait_element("xpath", Data_box.close_button, "LM banner", wait_20s));
            mf.log_message(this.getClass().getName(), "Closing LM not available banner...");
        }

        // [C]Check SonicOS version
        if (mf.wait_element("xpath", Data_box.SonicOS_Ver_TopLeft_string, "SonicOS version", wait_5s) != null) {
            SonicOS_version = mf.wait_element("xpath", Data_box.SonicOS_Ver_TopLeft_string, "SonicOS version", wait_5s).getText();
            mf.log_message(this.getClass().getName(), "Got SonicOS version: " + SonicOS_version);
        } else if (mf.wait_element("xpath", Data_box.SonicOS_Ver_Dashboard_string, "SonicOS version", wait_5s) != null) {
            SonicOS_version = mf.wait_element("xpath", Data_box.SonicOS_Ver_Dashboard_string, "SonicOS version", wait_5s).getText();
            mf.log_message(this.getClass().getName(), "Got SonicOS version: " + SonicOS_version);
        }
    }

    public void upload_boot(String FW_build, String FW_Check, String FW_Upload, String FW_msg_01, String FW_msg_02) throws Exception {
        // ----------------------------- V -----------------------------
        List<WebElement> Boot_FW_menu;
        String FW_check_msg_01 = "";
        String FW_check_msg_02 = "";
        // -------------------------------------------------------------

        switch (FW_msg_01) {
            case "Previous" -> FW_check_msg_01 = "Previous Firmware Version: ";
            case "Latest" -> FW_check_msg_01 = "Latest Firmware Version: ";
            case "Upgrade" -> FW_check_msg_01 = "Upgrade Firmware Version: ";
        }

        switch (FW_msg_02) {
            case "Factory" -> FW_check_msg_02 = "Restoring to factory defaults...";
            case "Current" -> FW_check_msg_02 = "Boot the firmware with current settings...";
        }

        // [A]Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'
        redirect_page("X0", Data_box.page_Firmware_and_Settings, "Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'");

        // [C]Wait for FW data
        mf.wait_element("xpath", Data_box.reboot_button, "Reboot button", wait_120s);
        mf.log_message(this.getClass().getName(), "FW data gets displayed in the list!");

        // [A]Need to upload FW?
        if (FW_Upload.equals("1")) {
            // [A]Upload Firmware
            mf.js_click(mf.wait_element("xpath", Data_box.FW_Upload_button, "Upload Firmware", wait_30s));
            mf.log_message(this.getClass().getName(), "Click on 'Upload Firmware'...");
            // [A]Backup of current settings?
            mf.js_click(mf.wait_element("xpath", Data_box.backupSettings_button, "BackupSettings_OK", wait_20s));
            mf.log_message(this.getClass().getName(), "Click on 'OK'...");
            // [A]Upload Firmware file
            mf.wait_element("xpath", Data_box.browse_button, "Browse", wait_20s);
            driver.findElement(By.id("files")).sendKeys(FW_build);
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
            mf.log_message(this.getClass().getName(), "Upload Firmware file...");
            // [A]Click "Upload"
            mf.js_click(mf.wait_element("xpath", Data_box.upload_button, "Upload", wait_20s));
            mf.log_message(this.getClass().getName(), "Click on 'Upload'...");

            // [C]Check success message "Firmware uploaded successfully..."
            if (mf.wait_element("xpath", Data_box.popup_success_string, "Upload Success", wait_120s) != null) {
                mf.log_message(this.getClass().getName(), "Success! Firmware uploaded successfully.");
                mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
            }

            // [A]Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'
            redirect_page("X0", Data_box.page_Firmware_and_Settings, "Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'");

            // [C]Wait for FW data
            mf.wait_element("xpath", Data_box.reboot_button, "Reboot button", wait_120s);
            mf.log_message(this.getClass().getName(), "FW data gets displayed in the list!");
        }

        // [A]API Not Found! >> Uploaded Firmware Version
        Thread.sleep(10000);

        // [C]Check "Firmware Version"
        switch (FW_Upload) {
            case "1" ->
                    FW_UI_Version = mf.wait_element("xpath", Data_box.uploaded_FW_string, "Uploaded Firmware Version", wait_120s).getText();
            case "0" ->
                    FW_UI_Version = mf.wait_element("xpath", Data_box.current_FW_string, "Current Firmware Version", wait_120s).getText();
        }

        // ----------------------------- CHECK POINT --------------------------------
        // [C]FW version (before boot)
        check_point_FW(FW_UI_Version, FW_Check, check_point_no += 1, FW_check_msg_01);
        // --------------------------------------------------------------------------

        // [A]Upload and boot FW.
        switch (FW_Upload) {
            case "1" -> {
                mf.wait_element("xpath", Data_box.upload_FW_reboot_button, "Reboot button", wait_30s).click();
                mf.log_message(this.getClass().getName(), "Click on Reboot button >> Upload FW");
            }
            case "0" -> {
                mf.wait_element("xpath", Data_box.current_FW_reboot_button, "Reboot button", wait_30s).click();
                mf.log_message(this.getClass().getName(), "Click on Reboot button >> Current FW");
            }
        }

        // [C]Check Upload with current or factory default
//        Thread.sleep(3000);

        // [A]Select boot with factory default or current settings
        switch (FW_msg_02) {
            case "Factory" -> {
                String bfm = mf.wait_element("xpath", Data_box.Factory_reboot_menu, "Click on Boot_FW_menu >> Factory default boot", wait_30s).getText();
                mf.wait_element("xpath", Data_box.Factory_reboot_menu, "Click on Boot_FW_menu >> Factory default boot", wait_30s).click();
                mf.log_message(this.getClass().getName(), "Click on Boot_FW_menu >> " + bfm);
            }
            case "Current" -> {
                String bfm = mf.wait_element("xpath", Data_box.Current_reboot_menu, "Click on Boot_FW_menu >> Current boot", wait_30s).getText();
                mf.wait_element("xpath", Data_box.Current_reboot_menu, "Click on Boot_FW_menu >> Current boot", wait_30s).click();
                mf.log_message(this.getClass().getName(), "Click on Boot_FW_menu >> " + bfm);
            }
        }
        mf.js_click(mf.wait_element("xpath", Data_box.boot_OK_button, "OK button", wait_20s));
        mf.log_message(this.getClass().getName(), FW_check_msg_02);
        Thread.sleep(3000);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);

        // [A]Wait factory default
        switch (FW_Upload) {
            case "1" -> reboot_timer(sleep_10m / 1000);
            case "0" -> reboot_timer(sleep_7m / 1000);
        }
    }

    public void reboot_timer(double set_time) throws InterruptedException {
        // ----------------------------- V -----------------------------
        int use_time = 0;
        // -------------------------------------------------------------

        // [A]Show total time
        System.out.println("Total Time: " + set_time / 60 + " mins");

        // [A]Time pass
        while (set_time > 0) {
            set_time--;
            use_time++;
            Thread.sleep(1000);
            System.out.println("Timer: " + use_time / 60 + " mins " + (use_time % 60) + " secs");
        }
        System.out.println("Total Time: " + use_time + " secs");
    }

    public void boot_check(String FW_msg) throws Exception {
        // ----------------------------- V -----------------------------
        String FW_check_ver = "";
        String FW_check_msg = "";
        // -------------------------------------------------------------

        switch (FW_msg) {
            case "Previous" -> {
                FW_check_ver = Data_box.FW_Check_TZ_box_Previous;
                FW_check_msg = "Previous Firmware Version: ";
            }
            case "Latest" -> {
                FW_check_ver = Data_box.FW_Check_TZ_box_Latest;
                FW_check_msg = "Latest Firmware Version: ";
            }
            case "Upgrade" -> {
                FW_check_ver = Data_box.FW_Check_TZ_box_Upgrade;
                FW_check_msg = "Upgrade Firmware Version: ";
            }
        }

        // [A]Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'
        redirect_page("X0", Data_box.page_Firmware_and_Settings, "Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'");

        // [C]Wait for FW data
        mf.wait_element("xpath", Data_box.reboot_button, "Reboot button", wait_120s);
        mf.log_message(this.getClass().getName(), "FW data gets displayed in the list!");

        // [C]Check "Current Firmware Version"
        FW_UI_Version = mf.wait_element("xpath", Data_box.current_FW_string, "Current Firmware Version", wait_20s).getText();

        // ----------------------------- CHECK POINT --------------------------------
        // [C]FW previous version (after boot)
        check_point_FW(FW_UI_Version, FW_check_ver, check_point_no += 1, FW_check_msg);
        // --------------------------------------------------------------------------
    }

    public void TSR_EXP() throws Exception {
        // [A]Config logout time to 777
        Logout_time_idle();

        // [A]Redirect to 'DEVICE' > 'Diagnostics' > 'Tech Support Report'
        redirect_page("X0", Data_box.page_Tech_Support_Report, "Redirect to 'DEVICE' > 'Diagnostics' > 'Tech Support Report'");

        // [A]Wait to avoid ElementClickInterceptedException
        Thread.sleep(45000);

        // [A]Click 'Download Tech Support Report'
        mf.wait_element("xpath", Data_box.Download_Tech_Support_Report_button, "'Download Tech Support Report' button", wait_30s).click();
        mf.log_message(this.getClass().getName(), "Click 'Download Tech Support Report' button...");
        // [A]Click 'OK'
        mf.wait_element("xpath", Data_box.Download_Tech_Support_Report_OK_button, "'OK' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'OK' button...");
        // [A]AutoIT > Click 'Save'
        Thread.sleep(50000);
        Runtime.getRuntime().exec(Data_box.Download_Tech_Support_Report_Open);
        Thread.sleep(50000);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "[Complete] > Download Tech Support Report");

        // [A]Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'
        redirect_page("X0", Data_box.page_Firmware_and_Settings, "Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'");

        // [C]Wait for FW data
        mf.wait_element("xpath", Data_box.reboot_button, "Reboot button", wait_120s);
        mf.log_message(this.getClass().getName(), "FW data gets displayed in the list!");

        // [A]Click 'Import/Export Configuration'
        mf.wait_element("xpath", Data_box.Import_Export_Configuration_button, "'Import/Export Configuration' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Import/Export Configuration' button...");

        // [A]Click 'Export Configuration'
        mf.wait_element("xpath", Data_box.Export_Configuration_button, "'Export Configuration' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Export Configuration' button...");

        // [C]Wait for EXP name string
        Thread.sleep(20000);

        // [A]Get e.g. '既定のファイル名は sonicwall-TZ 80-SonicOS 7.1.3-7015-R6965-j7044-2025-02-11T09:47:36.490Z.exp です'
        if (mf.wait_element("xpath", Data_box.EXP_FileName_Gen7_string, "[Gen7]EXP name, continue to search...", wait_20s) != null) {
            EXP_text = mf.wait_element("xpath", Data_box.EXP_FileName_Gen7_string, "[Gen7]EXP name, continue to search...", wait_20s).getText();
            mf.log_message(this.getClass().getName(), "Got EXP name for Gen7");
        } else {
            EXP_text = mf.wait_element("xpath", Data_box.EXP_FileName_Gen8_string, "EXP name for Gen8", wait_20s).getText();
            mf.log_message(this.getClass().getName(), "Got EXP name for Gen8");
        }
        EXP_file = EXP_new(EXP_text);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "Got EXP: " + EXP_file);

        // [A]Click 'Export'
        mf.wait_element("xpath", Data_box.Export_button, "'Export' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Export' button...");

        // [A]AutoIT > Click 'Save'
        Thread.sleep(50000);
        Runtime.getRuntime().exec(Data_box.Download_Tech_Support_Report_Open);
        Thread.sleep(50000);
        mf.log_message(this.getClass().getName(), "[Complete] > Export Configuration");

        // ----------------------------- CHECK POINT --------------------------------
        // [C]Download TSR and EXP (Previous)
        check_point_UI("成功", check_point_no += 1, "Completed downloading TSR and EXP.");
        // --------------------------------------------------------------------------
    }

    public String EXP_new(String EXP_txt) {
        int EXP_start = EXP_txt.indexOf("は");
        int EXP_end = EXP_txt.indexOf("で");
        return EXP_txt.substring(EXP_start + 1, EXP_end).trim().replaceAll(":", "_");
    }

    public void Logout_time_idle() throws Exception {
        // [A]Redirect to 'DEVICE' > 'Settings' > 'Administration'
        redirect_page("X0", Data_box.page_Administration, "Redirect to 'DEVICE' > 'Settings' > 'Administration'");
        // [A]Select 'Login/Multiple Admin'
        mf.wait_element("xpath", Data_box.login_MultipleAdmin_tab, "Tab 'Multiple Admin'", wait_30s).click();
        mf.log_message(this.getClass().getName(), "Select 'Login/Multiple Admin'");
        // [A]Logout time when idle
        mf.wait_element("xpath", Data_box.logout_time_idle_field, "Field 'Logout time when idle'", wait_30s).clear();
        mf.wait_element("xpath", Data_box.logout_time_idle_field, "Field 'Logout time when idle'", wait_30s).sendKeys("777");
        mf.log_message(this.getClass().getName(), "Enter logout time xxx when idle...");
        // [A]Click 'Accept'
        mf.wait_element("xpath", Data_box.Accept_button, "Button 'Accept'", wait_30s).click();
        mf.log_message(this.getClass().getName(), "Click 'Accept'");
        // [C]Wait for success message
        mf.wait_element("xpath", Data_box.popup_success_string, "success message", wait_30s);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "Got 'success' pop-up message!");
    }

    public void Network_Settings() throws Exception {
        // ----------------------------- V -----------------------------
        String Default_Gateway = "10.103.50.1";
        String DNS_Server_1 = "10.103.202.200";
        String DNS_Server_2 = "8.8.8.8";
        // -------------------------------------------------------------

        // [A]Redirect to 'NETWORK' > 'System' > 'Interfaces'
        redirect_page("X0", Data_box.page_Network_Interfaces, "Redirect to 'NETWORK' > 'System' > 'Interfaces'");

        // [A]Get X1 IP address
        String X1_IP = mf.wait_element("xpath", Data_box.X1_IP_string, "X1 IP address", wait_30s).getText();
        mf.log_message(this.getClass().getName(), "Got X1 IP address: " + X1_IP);

        // [A]Wait to avoid find elements error below
//        Thread.sleep(3000);

        // [A]Click X1 edit icon
        mf.js_click(mf.find_elements("xpath", Data_box.X1_edit_icon, "Edit icon").get(1));
        mf.log_message(this.getClass().getName(), "Click X1 edit icon...");

        // [A]Wait to avoid find elements error below
        Thread.sleep(3000);

        // [A]Selection box 'Mode / IP Assignment'
        mf.wait_element("xpath", Data_box.IP_Assignment, "Drop-down list 'Mode / IP Assignment'", wait_20s).click();
        // [A]Select option 'Static'
        mf.wait_element("xpath", Data_box.IP_Assignment_Static, "option 'Static'", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Select option 'Static'...");

        // [A]Enter X1 IP address
        mf.wait_element("xpath", Data_box.IP_Address_field, "'IP Address' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.IP_Address_field, "'IP Address' field", wait_20s).sendKeys(X1_IP);
        mf.log_message(this.getClass().getName(), "Enter X1 IP address...");

        // [A]Enter Default Gateway (Optional)
        mf.wait_element("xpath", Data_box.Default_Gateway_field, "'Default Gateway (Optional)' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.Default_Gateway_field, "'Default Gateway (Optional)' field", wait_20s).sendKeys(Default_Gateway);
        mf.log_message(this.getClass().getName(), "Enter Default Gateway (Optional)...");

        // [A]Enter DNS Server 1
        mf.wait_element("xpath", Data_box.DNS_Server_1_field, "'DNS Server 1' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.DNS_Server_1_field, "'DNS Server 1' field", wait_20s).sendKeys(DNS_Server_1);
        mf.log_message(this.getClass().getName(), "Enter DNS Server 1...");

        // [A]Enter DNS Server 2
        mf.wait_element("xpath", Data_box.DNS_Server_2_field, "'DNS Server 2' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.DNS_Server_2_field, "'DNS Server 2' field", wait_20s).sendKeys(DNS_Server_2);
        mf.log_message(this.getClass().getName(), "Enter DNS Server 2...");

        // [A]Enable "MANAGEMENT" & "USER LOGIN" options
        mf.wait_element("xpath", Data_box.HTTPS_option, "'HTTPS' option", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Enable 'HTTPS' option...");
        mf.wait_element("xpath", Data_box.Ping_option, "'Ping' option", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Enable 'Ping' option...");
        mf.wait_element("xpath", Data_box.SNMP_option, "'SNMP' option", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Enable 'SNMP' option...");
        mf.wait_element("xpath", Data_box.SSH_option, "'SSH' option", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Enable 'SSH' option...");

//        mf.wait_element("xpath", Data_box.UserLogin_HTTPS_option, "'User Login HTTPS' option", wait_20s).click();
        mf.js_click(mf.find_elements("xpath", Data_box.UserLogin_HTTPS_option, "'User Login HTTPS' option").get(1));
        mf.log_message(this.getClass().getName(), "Enable 'User Login HTTPS' option...");

        // [A]Click 'OK' button
        mf.wait_element("xpath", Data_box.Interface_OK_button, "'OK' button", wait_20s).click();
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "Click 'OK' button...");

        // [A]DNS Settings
        DNS_Settings();
    }

    public void DNS_Settings() throws Exception {
        // [A]Redirect to 'NETWORK' > 'DNS' > 'Settings'
        redirect_page("X0", Data_box.page_DNS_Settings, "Redirect to 'NETWORK' > 'DNS' > 'Settings'");

        // [A]Select 'Inherit DNS Settings Dynamically from WAN Zone' option
        mf.wait_element("xpath", Data_box.Inherit_DNS_option, "'Inherit DNS Settings...' option", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Select 'Inherit DNS Settings Dynamically from WAN Zone' option...");

        // [A]Click 'Accept' button
        mf.wait_element("xpath", Data_box.Accept_button, "'Accept' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Accept' button...");

        // [A]Get success message
        mf.wait_element("xpath", Data_box.popup_success_string, "'Success' message", wait_30s).click();
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "Got 'Success' message...");

        // [A]Wait for a little while
        Thread.sleep(3000);
    }

    @Test
//    @Test(enabled = false)
    public void test_Step01_Upload_FW() throws Exception {
        /*CHECK POINT 01
                - Check lang=ja-JP, incognito, licensed.
                - Check X0, X1 is able to be connected.
                - Check FW (Previous, Latest and Upgrade).*/
        mf.log_message(this.getClass().getName(), "********************************** test_Step01_Upload_FW **********************************");
        mf.log_message(this.getClass().getName(), "* Clear browser cache.                                                                    *");
        mf.log_message(this.getClass().getName(), "* Clear Cookies for 192.168.168.168 (TZ), 192,168.1.254 (NSa/NSsp), or all IP addresses.  *");
        mf.log_message(this.getClass().getName(), "* Connect a network cable from X0 (TZ) or MGMT (NSa/NSsp) to your management console.     *");
        mf.log_message(this.getClass().getName(), "* Connect a network cable from X1 to Internet.                                            *");
        mf.log_message(this.getClass().getName(), "*******************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 0;
        String initial_setup = "OK";
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [C]Initial setup
        mf.log_message(this.getClass().getName(), "Language set to Japanese done.");
        mf.log_message(this.getClass().getName(), "Interface X0, X1 is ok to connect.");
        mf.log_message(this.getClass().getName(), "Firmware (Previous, Latest and Upgrade) is good.");
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // ----------------------------- CHECK POINT 01 -----------------------------
        // [C]initial setup
        check_point_UI(initial_setup, check_point_no += 1, "Initial setup is done!");
        // --------------------------------------------------------------------------
    }

    @Test
//    @Test(enabled = false)
    public void test_Step02_Upload_FW() throws Exception {
        // CHECK POINT 02 - Check previous version (before factory default).
        mf.log_message(this.getClass().getName(), "***************************** test_Step02_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Upload and boot the previous release version with factory default settings.   *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 1;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
        login_UI("sonicwall", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [A]Upload and boot
        upload_boot(Data_box.TZ_box_Previous, Data_box.FW_Check_TZ_box_Previous, "1", "Previous", "Factory");
    }

    @Test
    public void test_Step03_Upload_FW() throws Exception {
        // CHECK POINT 03 - Check previous version (after factory default).
        mf.log_message(this.getClass().getName(), "******************************************** test_Step03_Upload_FW ********************************************");
        mf.log_message(this.getClass().getName(), "* Verify Firmware Version shows the previous release build on DEVICE > Settings > Firmware & Settings page.   *");
        mf.log_message(this.getClass().getName(), "***************************************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 2;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
        login_UI("password", Data_box.baseUrl_TZ_box_X0);
//        login_UI("sonicwall", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [C]Boot check FW
        boot_check("Previous");
    }

    @Test
    public void test_Step04_Upload_FW() throws Exception {
        // CHECK POINT 04, 20 - Check license activation.
        if (reuse_step == 0) {
            mf.log_message(this.getClass().getName(), "***************************** test_Step04_Upload_FW *****************************");
            mf.log_message(this.getClass().getName(), "* Register the product on License page.                                         *");
            mf.log_message(this.getClass().getName(), "*********************************************************************************");
        }

        // ----------------------------- V -----------------------------
        if (reuse_step == 0) check_point_no = 3;
        reuse_step = 0;
        String license_success = "";
        // -------------------------------------------------------------

        // [A]Register the unit
        for (int i = 0; i < 4; i++) {
            // [A]Redirect to 'DEVICE' > 'Settings' > 'Licenses'
            redirect_page("X0", Data_box.page_Licenses, "Redirect to 'DEVICE' > 'Settings' > 'Licenses'");
            // [A]Enter credentials info
            if (mf.wait_element("xpath", Data_box.MySonicWall_usr_field, "'MySonicWall にログイン' dialog", wait_30s) == null) {
                mf.wait_element("xpath", Data_box.MySonicWall_LOGIN_button, "'MySonicWall にログイン' button", wait_30s).click();
                mf.log_message(this.getClass().getName(), "[Workaround] >> Click 'MySonicWall にログイン' button.");
            }
            mf.wait_element("xpath", Data_box.MySonicWall_usr_field, "MySonicwall username", wait_20s).clear();
            mf.wait_element("xpath", Data_box.MySonicWall_usr_field, "MySonicwall username", wait_20s).sendKeys(Data_box.MySonicwall_usr_key);
            mf.log_message(this.getClass().getName(), "Enter MySonicwall username...");
            mf.wait_element("xpath", Data_box.MySonicWall_pwd_field, "MySonicwall password", wait_20s).clear();
            mf.wait_element("xpath", Data_box.MySonicWall_pwd_field, "MySonicwall password", wait_20s).sendKeys(Data_box.MySonicwall_pwd_key);
            mf.log_message(this.getClass().getName(), "Enter MySonicwall password...");
            mf.js_click(mf.wait_element("xpath", Data_box.MySonicWall_login_button, "Login button", 10));
            mf.log_message(this.getClass().getName(), "Click Login button...");
            // [C]Check registeration > success
            if (mf.wait_element("xpath", Data_box.popup_LM_success_string, "製品登録に成功しました", wait_120s) != null) {
                license_success = mf.wait_element("xpath", Data_box.popup_LM_success_string, "製品登録に成功しました", wait_120s).getText();
                mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
                mf.log_message(this.getClass().getName(), "Got string '製品登録に成功しました!'");
                i = 4;
            }
            // [C]Check registeration >> fail
            if (mf.wait_element("xpath", Data_box.Connection_failed_string, "'失敗しました。', continue...", wait_5s) != null) {
                if (i == 2) Network_Settings();
                if (i == 3)
                    mf.log_message(this.getClass().getName(), "失敗しました。接続の問題により登録に失敗しました。DNS 設定を確認し、再試行してください。");
            }
        }

        // [A]Close dialog
        // #####################################################################################################################################
        // 8.0.0
        if (SonicOS_version.substring(0, 9).contains("8")) dialog_close();
        // #####################################################################################################################################

        // [A]Wait for clickable to avoid error (ElementClickInterceptedException)
        Thread.sleep(50000);

        // [A]License sync
        if (mf.wait_element("xpath", Data_box.sync_button, "'Synchronize' button", wait_120s) != null) {
            mf.wait_element("xpath", Data_box.sync_button, "'Synchronize' button", wait_120s).click();
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
            mf.log_message(this.getClass().getName(), "Click 'Synchronize' button...");
            // [C]Wait success message
            if (mf.wait_element("xpath", Data_box.popup_success_string, "変更されました", wait_120s) != null) {
                mf.wait_element("xpath", Data_box.popup_success_string, "変更されました", wait_120s).getText();
                mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
                mf.log_message(this.getClass().getName(), "Success! Changes made.");
                mf.log_message(this.getClass().getName(), "License sync is done!");
            }
        }

        // ----------------------------- CHECK POINT 04 -----------------------------
        // [C]License activation
        check_point_UI(license_success, check_point_no += 1, "Success! Product registration successful.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step05_Upload_FW() throws Exception {
        // CHECK POINT 05 - Check newly created user.
        mf.log_message(this.getClass().getName(), "***************************** test_Step05_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Create a user “testuser”.                                                     *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 4;
        // -------------------------------------------------------------

        // [A]Redirect to 'DEVICE' > 'Users' > 'Local Users & Groups' > 'Local Users'
        redirect_page("X0", Data_box.page_Local_Users_Groups, "Redirect to 'DEVICE' > 'Users' > 'Local Users & Groups' > 'Local Users'");

        // [A]Click 'Add User'
        mf.js_click(mf.wait_element("xpath", Data_box.Add_User_button, "'Add User' button", wait_30s));
        mf.log_message(this.getClass().getName(), "Click 'Add User' button...");
        // [A]Enter name
        mf.wait_element("xpath", Data_box.Name_field, "'Name' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.Name_field, "'Name' field", wait_20s).sendKeys("testuser");
        mf.log_message(this.getClass().getName(), "Enter 'Name' field...");
        // [A]Enter password
        mf.wait_element("xpath", Data_box.User_Password_field, "'Password' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.User_Password_field, "'Password' field", wait_20s).sendKeys(Data_box.login_pwd_password_key);
        mf.log_message(this.getClass().getName(), "Enter 'Password' field...");
        // [A]Enter confirm password
        mf.wait_element("xpath", Data_box.Confirm_User_Password_field, "'Confirm Password' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.Confirm_User_Password_field, "'Confirm Password' field", wait_20s).sendKeys(Data_box.login_pwd_password_key);
        mf.log_message(this.getClass().getName(), "Enter 'Confirm Password' field...");
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        // [A]Click save
        mf.find_element("xpath", Data_box.Save_button, "'Save' button").click();
        mf.log_message(this.getClass().getName(), "Click 'Save' button...");

        // [C]Wait success message
        String changes_success = mf.wait_element("xpath", Data_box.popup_success_string, "変更されました", wait_30s).getText();

        // ----------------------------- CHECK POINT 05 -----------------------------
        // [C]Create user
        check_point_UI(changes_success, check_point_no += 1, "Success! Changes made.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step06_Upload_FW() throws Exception {
        // CHECK POINT 06 - Check TSR & EXP (Previous).
        mf.log_message(this.getClass().getName(), "***************************** test_Step06_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Attach TSR and EXP (Previous Version).                                  *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 5;
        // -------------------------------------------------------------

        // [A]Download TSR and EXP
        TSR_EXP();
    }

    @Test
    public void test_Step07_Upload_FW() throws Exception {
        // CHECK POINT 07 - Check latest version (before current settings).
        mf.log_message(this.getClass().getName(), "***************************** test_Step07_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Upload and boot the latest build with current settings.                       *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 6;
        // -------------------------------------------------------------

        // [A]Upload and boot
        upload_boot(Data_box.TZ_box_Latest, Data_box.FW_Check_TZ_box_Latest, "1", "Latest", "Current");
    }

    @Test
    public void test_Step08_Upload_FW() throws Exception {
        // CHECK POINT 08, 22 - Check unit registration (YES).
        if (reuse_step == 0) {
            mf.log_message(this.getClass().getName(), "***************************** test_Step08_Upload_FW *****************************");
            mf.log_message(this.getClass().getName(), "* Verify that the unit is registered on Home > Dashboard > System page.         *");
            mf.log_message(this.getClass().getName(), "*********************************************************************************");
        }

        // ----------------------------- V -----------------------------
        if (reuse_step == 0) check_point_no = 7;
        reuse_step = 0;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
        login_UI("sonicwall", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [A]Redirect to 'HOME' > 'Dashboard' > 'System'
        redirect_page("X0", Data_box.page_Dashboard_System, "Redirect to 'HOME' > 'Dashboard' > 'System'");

        // [C]Wait "Licensed"
        String register_success = mf.wait_element("xpath", Data_box.Licensed_string, "'Licensed' string", wait_30s).getText();
        mf.log_message(this.getClass().getName(), "Got message: " + register_success);

        // ----------------------------- CHECK POINT 08 -----------------------------
        // [C]Registration YES
        check_point_UI(register_success, check_point_no += 1, "Success! Product licensed successful.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step09_Upload_FW() throws Exception {
        // CHECK POINT 09 - Check latest version (after current settings).
        mf.log_message(this.getClass().getName(), "******************************************** test_Step09_Upload_FW ********************************************");
        mf.log_message(this.getClass().getName(), "* Verify Firmware Version shows the latest build on DEVICE > Settings > Firmware & Settings page.             *");
        mf.log_message(this.getClass().getName(), "***************************************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 8;
        // -------------------------------------------------------------

        // [C]Boot check FW
        boot_check("Latest");
    }

    @Test
    public void test_Step10_Upload_FW() throws Exception {
        // CHECK POINT 10 - Check user (testuser) exists.
        if (reuse_step == 0) {
            mf.log_message(this.getClass().getName(), "***************************** test_Step10_Upload_FW *****************************");
            mf.log_message(this.getClass().getName(), "* Verify that the user “testuser” exists.                                       *");
            mf.log_message(this.getClass().getName(), "*********************************************************************************");
        }

        // ----------------------------- V -----------------------------
        if (reuse_step == 0) check_point_no = 9;
        String user_success = "";
        reuse_step = 0;
        // -------------------------------------------------------------

        // [A]Redirect to 'DEVICE' > 'Users' > 'Local Users & Groups'
        redirect_page("X0", Data_box.page_Local_Users_Groups, "Redirect to 'DEVICE' > 'Users' > 'Local Users & Groups'");

        // [C]Find user "testuser"
        if (mf.wait_element("xpath", Data_box.Local_user_test, "user 'testuser'", wait_5s) != null) {
            user_success = mf.wait_element("xpath", Data_box.Local_user_test, "user 'testuser'", wait_20s).getText();
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
            mf.log_message(this.getClass().getName(), "Got user name: " + user_success);
        }

        // ----------------------------- CHECK POINT 10 -----------------------------
        // [C]Check user exist
        check_point_UI(user_success, check_point_no += 1, "Success! Found added user 'testuser'.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step11_Upload_FW() throws Exception {
        // CHECK POINT 11 - Check TSR & EXP (Upgraded).
        mf.log_message(this.getClass().getName(), "***************************** test_Step11_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Attach TSR and EXP below (Upgraded Version).                                  *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 10;
        // -------------------------------------------------------------

        // [A]Download TSR and EXP
        TSR_EXP();
    }

    @Test
    public void test_Step12_Upload_FW() throws Exception {
        // CHECK POINT 12 - Check each settings for internal wireless.
        mf.log_message(this.getClass().getName(), "************************************ test_Step12_Upload_FW ************************************");
        mf.log_message(this.getClass().getName(), "* Configure and enable internal wireless with DFS channel (5GHz a/n/ac, 80MHz, Channel 100).  *");
        mf.log_message(this.getClass().getName(), "***********************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 11;
        // -------------------------------------------------------------

        // [A]Redirect to 'DEVICE' > 'Internal Wireless' > 'Settings'
        redirect_page("X0", Data_box.page_Internal_Wireless_Settings, "Redirect to 'DEVICE' > 'Internal Wireless' > 'Settings'");

        // [A]Enable WLAN Radio
        mf.wait_element("xpath", Data_box.Enable_WLAN_Radio_button, "'Enable WLAN Radio' radio button", wait_30s);
        if (Objects.equals(mf.wait_element("xpath", Data_box.Enable_WLAN_Radio_button, "'Enable WLAN Radio' radio button", wait_20s).getAttribute("class"), "sw-toggle sw-toggle--right sw-toggle--regular sw-toggle--light sw-toggle--off")) {
            mf.wait_element("xpath", Data_box.Enable_WLAN_Radio_button, "'Enable WLAN Radio' radio button", wait_20s).click();
            mf.wait_element("xpath", Data_box.Confirm_button, "'Confirm' button", wait_20s).click();
            mf.log_message(this.getClass().getName(), "Enable WLAN Radio...");
        }

        // [A]Selection box 'Radio Mode'
        mf.wait_element("xpath", Data_box.Radio_Mode_listbox, "Selection box 'Radio Mode'", wait_20s).click();
        // [A]Select option '5GHz 802.11n/a/ac Mixed'
        mf.wait_element("xpath", Data_box.Mode_5GHz_n_a_ac_Mixed_option, "Radio Mode '5GHz 802.11n/a/ac Mixed'", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Select Radio Mode '5GHz 802.11n/a/ac Mixed'...");
        // [A]Selection box 'Radio Band'
        mf.wait_element("xpath", Data_box.Radio_Band_listbox, "Selection box 'Radio Band'", wait_20s).click();
        // [A]Select option 'Wide 80MHz Channel'
        mf.wait_element("xpath", Data_box.Band_Wide_80MHz_Channel_option, "Radio Band 'Wide 80MHz Channel'", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Select Radio Band 'Wide 80MHz Channel'...");
        // [A]Enable DFS Channels
        if (Objects.requireNonNull(mf.wait_element("xpath", Data_box.Enable_DFS_Channels_button, "'Enable DFS Channels' radio button", wait_20s).getAttribute("class")).equals("sw-toggle sw-toggle--right sw-toggle--regular sw-toggle--light sw-toggle--off")) {
            mf.wait_element("xpath", Data_box.Enable_DFS_Channels_button, "'Enable DFS Channels' radio button", wait_20s).click();
            mf.log_message(this.getClass().getName(), "Enable DFS Channels...");
        }
        // [A]Selection box 'Channel'
        mf.wait_element("xpath", Data_box.Channel_listbox, "Selection box 'Channel'", wait_20s).click();
        // [A]Select option 'Channel 100 (5500MHz*)'
        mf.wait_element("xpath", Data_box.Channel_100_5500MHz_option, "Channel 'Channel 100 (5500MHz*)'", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Select Channel 'Channel 100 (5500MHz*)'...");
        // [A]Edit SSID > 'localization'
        mf.wait_element("xpath", Data_box.SSID_field, "Edit 'SSID' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.SSID_field, "Edit 'SSID' field", wait_20s).sendKeys("localization");
        mf.log_message(this.getClass().getName(), "Edit SSID > 'localization'...");
        // [A]Click 'Accept'
        mf.wait_element("xpath", Data_box.Accept_button, "Click 'Accept'", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Accept'...");

        // [C]Wait success message
        String changes_success = mf.wait_element("xpath", Data_box.popup_success_string, "変更されました", wait_30s).getText();

        // ----------------------------- CHECK POINT 12 -----------------------------
        // [C]Enable internal wireless with DFS channel
        check_point_UI(changes_success, check_point_no += 1, "Success! Changes made.");
        // --------------------------------------------------------------------------

        // [A]Config wireless password
        // [A]Redirect to 'DEVICE' > 'Internal Wireless' > 'Security'
        redirect_page("X0", Data_box.page_Internal_Wireless_Security, "Redirect to 'DEVICE' > 'Internal Wireless' > 'Security'");
        // [A]Selection box "Authentication type"
        mf.wait_element("xpath", Data_box.Auth_type_listbox, "Selection box 'Authentication type'", wait_30s).click();
        // [A]Select option 'WPA2 – 自動 – PSK'
        mf.wait_element("xpath", Data_box.WPA2_Auto_PSK_option, "Authentication type 'WPA2 – Auto – PSK'", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Select Authentication type 'WPA2 – Auto – PSK'...");
        // [A]Config PSK password
        mf.wait_element("xpath", Data_box.PSK_Password_field, "PSK 'password' field", wait_20s).clear();
        mf.wait_element("xpath", Data_box.PSK_Password_field, "PSK 'password' field", wait_20s).sendKeys(Data_box.PSK_pwd_key);
        mf.log_message(this.getClass().getName(), "Change PSK password: " + "********");
        // [A]Click 'Accept'
        mf.wait_element("xpath", Data_box.Accept_button, "Button 'Accept'", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Accept'...");
    }

    @Test
    public void test_Step13_Upload_FW() throws Exception {
        // CHECK POINT 13 - Check client connection to FW.
        mf.log_message(this.getClass().getName(), "*********************************** test_Step13_Upload_FW ***********************************");
        mf.log_message(this.getClass().getName(), "* Connect to this unit from a client and verify that the client is connected to this unit.  *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 12;
        // -------------------------------------------------------------

        // [A]Redirect to 'DEVICE' > 'Internal Wireless' > 'Status'
        redirect_page("X0", Data_box.page_Internal_Wireless_Status, "Redirect to 'DEVICE' > 'Internal Wireless' > 'Status'");
        // [A]Select 'Station Status' tab
        mf.wait_element("xpath", Data_box.Station_Status_tab, "'Station Status' tab", wait_30s).click();

        // [C]Wait '参加済'
        int associated_entry = 0;
        String associated_success = "";
        while (associated_entry == 0) {
            // [A]Click 'Refresh'
            mf.wait_element("xpath", Data_box.Refresh_button, "'Refresh' button", wait_20s).click();
            // [A]Find '参加済'
            if (mf.wait_element("xpath", Data_box.Associated_string, "'参加済', go on searching...", wait_30s) != null) {
                associated_success = mf.wait_element("xpath", Data_box.Associated_string, "参加済", wait_30s).getText();
                mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
                mf.log_message(this.getClass().getName(), "Connected!");
                associated_entry = 1;
            }
        }

        // ----------------------------- CHECK POINT 13 -----------------------------
        // [C]Check client connected
        check_point_UI(associated_success, check_point_no += 1, "Success! Connected.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step14_Upload_FW() throws Exception {
        // CHECK POINT 14 - Check latest version (before factory default).
        mf.log_message(this.getClass().getName(), "***************************** test_Step14_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Boot the latest build with factory default settings on Management UI.         *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 13;
        // -------------------------------------------------------------

        // [A]Upload and boot
        upload_boot(Data_box.TZ_box_Latest, Data_box.FW_Check_TZ_box_Latest, "0", "Latest", "Factory");
    }

    @Test
    public void test_Step15_Upload_FW() throws Exception {
        // CHECK POINT 15 - Check unit registration (NO).
        mf.log_message(this.getClass().getName(), "***************************** test_Step15_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Verify that the unit is not registered on Home > Dashboard > System page.     *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 14;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
        login_UI("password", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [C]Wait "Device Entry"
        String device_entry = mf.wait_element("xpath", Data_box.Device_entry_button, "'Device Entry' button", wait_30s).getText();
        mf.log_message(this.getClass().getName(), "Got 'デバイスの登録' button!");

        // ----------------------------- CHECK POINT 14 -----------------------------
        // [C]Registration NO
        check_point_UI(device_entry, check_point_no += 1, "Confirmed unit is not registered.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step16_Upload_FW() throws Exception {
        // CHECK POINT 16 - Check user (testuser) not existed.
        mf.log_message(this.getClass().getName(), "***************************** test_Step16_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Verify that the user “testuser” does not exist.                              *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 15;
        // -------------------------------------------------------------

        // [A]Redirect to 'DEVICE' > 'Users' > 'Local Users & Groups'
        redirect_page("X0", Data_box.page_Local_Users_Groups, "Redirect to 'DEVICE' > 'Users' > 'Local Users & Groups'");

        // [C]Find user "testuser"
        if (mf.wait_element("xpath", Data_box.Local_user_test, "user 'testuser'", wait_20s) == null) {
            mf.log_message(this.getClass().getName(), "Didn't find user 'testuser'");
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        }

        // ----------------------------- CHECK POINT 16 -----------------------------
        // [C]Check user not exists
        check_point_UI("N/A", check_point_no += 1, "Confirmed user 'testuser' not exist.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step17_Upload_FW() throws Exception {
        // CHECK POINT 17 - Check internal wireless is disabled.
        mf.log_message(this.getClass().getName(), "***************************** test_Step17_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Verify that the internal wireless is disabled.                                *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 16;
        // -------------------------------------------------------------

        // [A]Redirect to 'DEVICE' > 'Internal Wireless' > 'Settings'
        redirect_page("X0", Data_box.page_Internal_Wireless_Settings, "Redirect to 'DEVICE' > 'Internal Wireless' > 'Settings'");

        // [C]Check wireless is not available
        String wireless_NA = mf.wait_element("xpath", Data_box.Wireless_NA_string, "wireless is not available", wait_30s).getText();
        mf.log_message(this.getClass().getName(), "Got string 'wireless is not available'.");

        // ----------------------------- CHECK POINT 17 -----------------------------
        // [C]Check internal wireless disabled
        check_point_UI(wireless_NA, check_point_no += 1, "Confirmed internal wireless is disabled.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step18_Upload_FW() throws Exception {
        // CHECK POINT 18 - Check import EXP (Upgraded).
        mf.log_message(this.getClass().getName(), "***************************** test_Step18_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Import the EXP file exported above (Upgraded) on Firmware and Settings page.  *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 17;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
//        login_UI("sonicwall", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [A]Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'
        redirect_page("X0", Data_box.page_Firmware_and_Settings, "Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'");

        // [A]Wait for clickable to avoid error
        Thread.sleep(10000);

        // [A]Click 'Import/Export Configuration'
        mf.wait_element("xpath", Data_box.Import_Export_Configuration_button, "'Import/Export Configuration' button", wait_60s).click();
        mf.log_message(this.getClass().getName(), "Click 'Import/Export Configuration'...");
        // [A]Click 'Export Configuration'
        mf.wait_element("xpath", Data_box.Import_Configuration_button, "'Import Configuration' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Export Configuration'...");

        // [A]Import file
        mf.wait_element("xpath", Data_box.browse_button, "Browse", wait_20s);

        // ++++++++++++++++++++++++++++++++++++++ Real ++++++++++++++++++++++++++++++++++++++
        driver.findElement(By.id("files")).sendKeys(Data_box.EXP_location + EXP_file);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "Got upgraded EXP file: " + EXP_file);
        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
//        String EXP_temp = "sonicwall-NSa 5700-SonicOS 7.1.3-7015-D6965-j24377-2025-05-20T06_47_02.285Z.exp";
//        driver.findElement(By.id("files")).sendKeys(Data_box.EXP_location + EXP_temp);
//        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
//        mf.log_message(this.getClass().getName(), "Got upgraded EXP file: " + EXP_temp);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [A]Click "Import"
        mf.js_click(mf.wait_element("xpath", Data_box.import_button, "Import", wait_20s));
        mf.log_message(this.getClass().getName(), "Click on 'Import'...");

        // [C]Check message after importing EXP
        String import_success = mf.wait_element("xpath", Data_box.popup_success_string, "import success", wait_60s).getText();
//        String error_message = mf.wait_element("xpath", Data_box.popup_error_string, "Network Error", wait_60s).getText();

        // ----------------------------- CHECK POINT 18 -----------------------------
        // [C]Import EXP
        check_point_UI(import_success, check_point_no += 1, "Confirmed Import EXP is succeeded.");
//        check_point_UI(error_message, check_point_no += 1, "Got error message.");
        // --------------------------------------------------------------------------

        // [A]Wait import EXP reboot
        reboot_timer(sleep_3m / 1000);
    }

    @Test
    public void test_Step19_Upload_FW() throws Exception {
        // CHECK POINT 19 - Check user (testuser) exists.
        mf.log_message(this.getClass().getName(), "***************************** test_Step19_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Verify that the user 'testuser' exists.                                       *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 18;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
        login_UI("sonicwall", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [A]Reusing step
        reuse_step = 1;
        test_Step10_Upload_FW();
    }

    @Test
    public void test_Step20_Upload_FW() throws Exception {
        // CHECK POINT 20 - Check license activation & sync.
        mf.log_message(this.getClass().getName(), "***************************** test_Step20_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Register the product on License page and verify the licenses.                 *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 19;
        // -------------------------------------------------------------

        // [A]Reusing step
        reuse_step = 1;
        test_Step04_Upload_FW();
    }

    @Test
    public void test_Step21_Upload_FW() throws Exception {
        // CHECK POINT 21 - Check upgrade test version (before current settings).
        mf.log_message(this.getClass().getName(), "***************************** test_Step21_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Upload and boot the upgrade test build with current settings.                 *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 20;
        // -------------------------------------------------------------

        // [A]Upload and boot
        upload_boot(Data_box.TZ_box_Upgrade, Data_box.FW_Check_TZ_box_Upgrade, "1", "Upgrade", "Current");
    }

    @Test
    public void test_Step22_Upload_FW() throws Exception {
        // CHECK POINT 22 - Check unit registration (YES).
        mf.log_message(this.getClass().getName(), "***************************** test_Step22_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Verify that the unit is registered on Home > Dashboard > System page.         *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 21;
        // -------------------------------------------------------------

        // [A]Reusing step
        reuse_step = 1;
        test_Step08_Upload_FW();
    }

    @Test
    public void test_Step23_Upload_FW() throws Exception {
        // CHECK POINT 23 - Check upgrade test version (after current settings).
        mf.log_message(this.getClass().getName(), "******************************************** test_Step23_Upload_FW ********************************************");
        mf.log_message(this.getClass().getName(), "* Verify Firmware Version shows the upgrade build on DEVICE > Settings > Firmware & Settings page.            *");
        mf.log_message(this.getClass().getName(), "***************************************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 22;
        // -------------------------------------------------------------

        // [C]Boot check FW
        boot_check("Upgrade");
    }

    @Test
    public void test_Step24_Upload_FW() throws Exception {
        // CHECK POINT 24 - Check user (testuser) exists.
        mf.log_message(this.getClass().getName(), "***************************** test_Step24_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Verify that the user “testuser” exists.                                       *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 23;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
//        login_UI("sonicwall", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [A]Reusing step
        reuse_step = 1;
        test_Step10_Upload_FW();
        mf.log_message(this.getClass().getName(), "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        mf.log_message(this.getClass().getName(), "^                            -- Ready to Safemode --                            ^");
        mf.log_message(this.getClass().getName(), "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        Thread.sleep(3000);
    }

    @AfterMethod
    public void afterMethod(ITestResult testResult) {

    }

    @AfterClass
    public void afterClass() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        mf.close_exReport();
    }

}
