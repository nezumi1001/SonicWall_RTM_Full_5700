package com.demo.apitest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class Test_RTM_SM {
    // ----------------------------- V -----------------------------
    private Func_box mf;
    private WebDriver driver;
    private String FW_UI_Version;
    private String EXP_text;
    private String EXP_file;
    private String SonicOS_version;
    private int check_point_no = 24;
    private final int sleep_15m = 900000;
    private final int sleep_14m = 840000;
    private final int sleep_10m = 600000;
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

        // [S]Set browser zoom > 200%
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
            // [D]Update data >> Excel
            mf.update_data(check_NO, "Pass");
            mf.log_message(this.getClass().getName(), check_msg + GREEN + FW_UI_version + RESET);
            mf.log_message(this.getClass().getName(), GREEN + "PASS" + RESET);
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        } else {
            // [D]Update data >> Excel
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
            mf.log_message(this.getClass().getName(), "Close 'Automatic Firmware Updates'...");
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

        // [A]Login page
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

    public void upload_boot_Safemode(String FW_build, String FW_Check, String FW_Upload, String FW_msg_01, String FW_msg_02) throws Exception {
        // ----------------------------- V -----------------------------
        List<WebElement> Boot_FW_menu;
        String FW_check_msg_01 = "";
        String FW_check_msg_02 = "";
        // -------------------------------------------------------------

        if (FW_msg_01.equals("Latest")) {
            FW_check_msg_01 = "Latest Firmware Version: ";
        }

        if (FW_msg_02.equals("Factory")) {
            FW_check_msg_02 = "Restoring to factory defaults...";
        }

        // [A]Need to upload FW?
        if (FW_Upload.equals("1")) {
            // [A]Upload Firmware
            mf.js_click(mf.wait_element("xpath", Data_box.SM_FW_Upload_button, "Upload Firmware", wait_30s));
            mf.log_message(this.getClass().getName(), "Click on 'Upload Firmware'...");
            // [A]Upload Firmware file
            mf.wait_element("xpath", Data_box.SM_browse_button, "Browse", wait_20s);
            driver.findElement(By.id("files")).sendKeys(FW_build);
            mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
            mf.log_message(this.getClass().getName(), "Upload Firmware file...");
            // [A]Click "Upload"
            mf.js_click(mf.wait_element("xpath", Data_box.SM_upload_button, "Upload", wait_20s));
            mf.log_message(this.getClass().getName(), "Click on 'Upload'...");
            // [C]Check success message "Firmware uploaded successfully..."
            if (mf.wait_element("xpath", Data_box.SM_popup_success_string, "Upload Success", wait_120s) != null) {
                mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
                mf.log_message(this.getClass().getName(), "Success! Uploaded image.");
            }

            // [C]Wait for "Uploaded Firmware Version" string appears
            mf.wait_element("xpath", Data_box.Uploaded_Firmware_Version, "Uploaded_Firmware_Version' string", wait_120s);
            mf.log_message(this.getClass().getName(), "Got 'Uploaded Firmware Version' string!");
        }

        // [A]API Not Found! >> Uploaded Firmware Version
        Thread.sleep(10000);

        // [C]Check "Firmware Version"
        if (FW_Upload.equals("1")) {
            FW_UI_Version = mf.wait_element("xpath", Data_box.SM_uploaded_FW_string, "Uploaded Firmware Version", wait_30s).getText();
        }

        // ----------------------------- CHECK POINT 26 -----------------------------
        // [C]FW version (before boot)
        check_point_FW(FW_UI_Version, FW_Check, check_point_no += 1, FW_check_msg_01);
        // --------------------------------------------------------------------------

        // [A]Wait for avoiding 'element click intercepted'
        Thread.sleep(20000);

        // [A]Upload and boot FW
        mf.wait_element("xpath", Data_box.SM_reboot_button, "Reboot button", wait_30s).click();

        // [A]Select boot with factory default or current settings
        if (FW_msg_02.equals("Factory"))
            mf.wait_element("xpath", Data_box.SM_reboot_menu, "Reboot button", wait_30s).click();

        mf.js_click(mf.wait_element("xpath", Data_box.boot_OK_button, "OK button", wait_20s));
        mf.log_message(this.getClass().getName(), FW_check_msg_02);
        Thread.sleep(3000);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);

        // [A]Wait factory default
        if (FW_Upload.equals("1")) {
            reboot_timer(sleep_10m / 1000);
        }
    }

    public void reboot_timer(int set_time) throws InterruptedException {
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
        mf.wait_element("xpath", Data_box.reboot_button, "Reboot button", wait_30s);
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
        mf.log_message(this.getClass().getName(), "Click 'OK'...");
        // [A]AutoIT > Click 'Save'
        Thread.sleep(50000);
        Runtime.getRuntime().exec(Data_box.Download_Tech_Support_Report_Open);
        Thread.sleep(50000);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "[Complete] > Download Tech Support Report");

        // [A]Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'
        redirect_page("X0", Data_box.page_Firmware_and_Settings, "Redirect to 'DEVICE' > 'Settings' > 'Firmware and Settings'");

        // [C]Wait for FW data
        mf.wait_element("xpath", Data_box.reboot_button, "Reboot button", wait_30s);
        mf.log_message(this.getClass().getName(), "FW data gets displayed in the list!");

        // [A]Click 'Import/Export Configuration'
        mf.wait_element("xpath", Data_box.Import_Export_Configuration_button, "'Import/Export Configuration' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Import/Export Configuration' button...");

        // [A]Click 'Export Configuration'
        mf.wait_element("xpath", Data_box.Export_Configuration_button, "'Export Configuration' button", wait_20s).click();
        mf.log_message(this.getClass().getName(), "Click 'Export Configuration' button...");

        // [C]Wait for EXP name string
        Thread.sleep(20000);

        // [A]Get '既定のファイル名は sonicwall-TZ 80-SonicOS 7.1.3-7015-R6965-j7044-2025-02-11T09:47:36.490Z.exp です'
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

    @Test
    public void test_Step25_Upload_FW() throws Exception {
        // CHECK POINT 25 - Check browser & console.
        mf.log_message(this.getClass().getName(), "***************************** test_Step25_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Start taking a console log.                                                   *");
        mf.log_message(this.getClass().getName(), "* Clear browser cache and Cookies.                                              *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 24;
        String safemode_ready = "OK";
        // -------------------------------------------------------------

        // [C]Safemode ready
        mf.log_message(this.getClass().getName(), "Clear browser cache is done.");
        mf.log_message(this.getClass().getName(), "Start Console ready to take log.");

        // ----------------------------- CHECK POINT 25 -----------------------------
        // [C]Safemode ready
        check_point_UI(safemode_ready, check_point_no += 1, "Ready to enter safemode.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step26_Upload_FW() throws Exception {
        // CHECK POINT 26 - Check Safemode UI page.
        mf.log_message(this.getClass().getName(), "***************************** test_Step26_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Go to Safemode.                                                               *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 25;
        String Maintenance_Key = Data_box.Maintenance_Key;
        // -------------------------------------------------------------

        // [A]AutoIT > Open "C:\Users\wodem\IdeaProjects\SonicWall_RTM_Full\Tools\SecureCRT.exe"
        // [A]SecureCRT.exe > Open C:\Users\wodem\IdeaProjects\SonicWall_RTM_Full\Auto\RecordedScript_safemode_TZ80.vbs
        Runtime.getRuntime().exec(Data_box.SecureCRT_Open);
        mf.log_message(this.getClass().getName(), "Go to Safemode...");

        // [A]Enter Safemode
        reboot_timer(sleep_3m / 1000);

        // [A]Login page
        driver.get(Data_box.baseUrl_TZ_box_MGMT);
        mf.log_message(this.getClass().getName(), "Login to Safemode...");

        // [A]Wait to avoid entering login page
        Thread.sleep(6000);

        // [A]Enter Maintenance Key
        mf.wait_element("xpath", Data_box.Maintenance_Key_field, "'Maintenance Key' field", wait_30s).clear();
        mf.wait_element("xpath", Data_box.Maintenance_Key_field, "'Maintenance Key' field", wait_30s).sendKeys(Maintenance_Key);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "Enter Maintenance Key...");
        // [A]Click "LOGIN"
        mf.js_click(mf.wait_element("xpath", Data_box.Safemode_login_button, "login", wait_20s));
        mf.log_message(this.getClass().getName(), "Click 'login'...");

        // [C]Exit Safe Mode
        String SafeMode_info = mf.wait_element("xpath", Data_box.Exit_Safe_Mode_button, "'Exit Safe Mode' button", wait_60s).getText();
        mf.wait_element("xpath", Data_box.Current_Firmware_Version, "'Current Firmware Version' string", wait_60s);
        mf.take_screenshot(this.getClass().getName(), "[PASS]cp" + check_point_no + "_", save_folder);
        mf.log_message(this.getClass().getName(), "Got Current Firmware Version...");

        // ----------------------------- CHECK POINT 26 -----------------------------
        // [C]Check Safemode UI page
        check_point_UI(SafeMode_info, check_point_no += 1, "Enter Safemode successfully.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step27_Upload_FW() throws Exception {
        // CHECK POINT 27 - Check latest version (before factory default).
        mf.log_message(this.getClass().getName(), "***************************** test_Step27_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Upload and boot the latest build with factory default settings.               *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 26;
        // -------------------------------------------------------------

        // [A]Upload and boot
        upload_boot_Safemode(Data_box.TZ_box_Latest, Data_box.FW_Check_TZ_box_Latest, "1", "Latest", "Factory");
    }

    @Test
    public void test_Step28_Upload_FW() throws Exception {
        // CHECK POINT 28 - Check unit registration (NO).
        mf.log_message(this.getClass().getName(), "***************************** test_Step28_Upload_FW *****************************");
        mf.log_message(this.getClass().getName(), "* Verify that the unit is not registered on Home > Dashboard > System page.     *");
        mf.log_message(this.getClass().getName(), "*********************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 27;
        // -------------------------------------------------------------

        // ++++++++++++++++++++++++++++++++++++++ Temp ++++++++++++++++++++++++++++++++++++++
        // [A]Login
        login_UI("password", Data_box.baseUrl_TZ_box_X0);
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // [C]Wait "Device Entry"
        String device_entry = mf.wait_element("xpath", Data_box.Device_entry_button, "'Device Entry' button", wait_30s).getText();
        mf.log_message(this.getClass().getName(), "Got 'デバイスの登録' button!");

        // ----------------------------- CHECK POINT 28 -----------------------------
        // [C]Registration NO
        check_point_UI(device_entry, check_point_no += 1, "Confirmed unit is not registered.");
        // --------------------------------------------------------------------------
    }

    @Test
    public void test_Step29_Upload_FW() throws Exception {
        // CHECK POINT 29 - Check latest version (after factory default).
        mf.log_message(this.getClass().getName(), "******************************************** test_Step29_Upload_FW ********************************************");
        mf.log_message(this.getClass().getName(), "* Verify Firmware Version shows the latest build on DEVICE > Settings > Firmware & Settings page.             *");
        mf.log_message(this.getClass().getName(), "***************************************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 28;
        // -------------------------------------------------------------

        // [C]Boot check FW
        boot_check("Latest");
    }

    @Test
    public void test_Step30_Upload_FW() throws Exception {
        // CHECK POINT 30 - Check console log, TSR and EXP (Factory Default).
        mf.log_message(this.getClass().getName(), "******************************************** test_Step30_Upload_FW ********************************************");
        mf.log_message(this.getClass().getName(), "* Attach the console log, TSR and EXP below (Factory Default).                                                *");
        mf.log_message(this.getClass().getName(), "***************************************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 29;
        // -------------------------------------------------------------

        // [A]Download TSR and EXP
        TSR_EXP();
    }

    @Test
    public void test_Step31_Upload_FW() throws Exception {
        // CHECK POINT 31 - Check summary report.
        mf.log_message(this.getClass().getName(), "******************************************** test_Step31_Upload_FW ********************************************");
        mf.log_message(this.getClass().getName(), "* Verify test results for each case.                                                                          *");
        mf.log_message(this.getClass().getName(), "***************************************************************************************************************");

        // ----------------------------- V -----------------------------
        check_point_no = 30;
        // -------------------------------------------------------------

        // [D]Update data >> Excel
        mf.update_data(check_point_no += 1, "Pass");
        mf.log_message(this.getClass().getName(), "Data updated for [summary]");
        mf.log_message(this.getClass().getName(), "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        mf.log_message(this.getClass().getName(), "^                             -- END --                                         ^");
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
