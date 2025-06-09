package com.demo.apitest;

import java.io.File;

public class Data_box {
    // #####################################################################################################################################
    // LM info
    public static String MySonicwall_usr_key = "khuang@sonicwall.com";
    public static String MySonicwall_pwd_key = "password";
//    public static String MySonicwall_usr_key = "locali";
//    public static String MySonicwall_pwd_key = "zation12";

    // Maintenance Key
    public static String Maintenance_Key = "be779a26b66"; // NSa5700
//    public static String Maintenance_Key = "e473441b4f1"; // TZ270W
//    public static String Maintenance_Key = "e4677405b4b"; // TZ80

    // CHECK POINT
    public static String FW_Check_TZ_box_Previous = "7.1.3-7014-D6901-j24151";
    public static String FW_Check_TZ_box_Latest = "7.1.3-7015-D6965-j24377";
    public static String FW_Check_TZ_box_Upgrade = "7.1.3-7015-D6965-j24952";

    // Build Location
    public static String FW_location = "C:\\Users\\admin\\IdeaProjects\\SonicWall_RTM_Full\\FW\\";
    public static String TZ_box_Previous = FW_location + "Previous\\" + "sw_nsa_5700_jpn.7.1.3-7014-D6901-j24151.bin.sig";
    public static String TZ_box_Latest = FW_location + "Latest\\" + "sw_nsa_5700_jpn.7.1.3-7015-D6965-j24377.bin.sig";
    public static String TZ_box_Upgrade = FW_location + "Upgrade\\" + "sw_nsa_5700_jpn.7.1.3-7015-D6965-j24952.bin.sig";

    // Upload Firmware
    // [Management UI]
    public static String Factory_reboot_menu = "//div[contains(text(),'工場出荷時')]"; // 7.1.3
//    public static String Factory_reboot_menu = "//div[contains(text(),'既定の構成')]"; // 7.2.0
    // [SafeMode]
    public static String SM_reboot_menu = "//div[contains(text(),'Factory Default')]"; // 7.1.3
//    public static String SM_reboot_menu = "//div[contains(text(),'Default Configuration')]"; // 7.2.0

    // Pop-up success message
//    public static String popup_success_string = "//div[contains(text(),'成功')]"; // ??
    public static String popup_success_string = "//span[contains(text(),'成功')]"; // 8.0.0, 7.1.3, 7.2.0
    public static String popup_LM_success_string = "//span[contains(text(),'成功')]"; // 8.0.0, 7.1.3, 7.2.0

    // Safemode
//    public static String SM_upload_button = "//button[(text()='Upload')]"; // 8.0.0
    public static String SM_upload_button = "//button[contains(text(),'Upload')]"; // 7.1.3, 7.2.0

    // Box Info
    public static String baseUrl_TZ_box_X1 = "https://x.x.x.x"; // TZ_box > JPN
    public static String baseUrl_TZ_box_X0 = "https://10.10.10.10"; // TZ_box > JPN
    public static String baseUrl_TZ_box_MGMT = "https://192.168.168.168"; // TZ_box > JPN
    // #####################################################################################################################################

    // SonicOS version: 7 or 8
    public static String SonicOS_Ver_TopLeft_string = "//div[@class='fw-app-nav__app-details sw-flexbox sw-flexbox--center-items sw-flexbox--column']/div";
    public static String SonicOS_Ver_Dashboard_string = "//div[@class='sw-form-row__field sw-typo-field-value sw-flexbox__flex sw-flexbox sw-flexbox--center-items']/span[contains(text(),'SonicOS')]";

    // Config mode OFF
    public static String Config_OFF_button = "//div[@class='sw-toggle sw-toggle--left sw-toggle--regular sw-toggle--light sw-toggle--off']";
    public static String Preempt_OK_button = "//button[text()='先制']";

    // UI page
    public static String page_Dashboard_System = "/sonicui/7/m/dashboard/overview/status/device";
    public static String page_Firmware_and_Settings = "/sonicui/7/m/mgmt/system/settings/firmware";
    public static String page_Licenses = "/sonicui/7/m/mgmt/system/license-enhanced";
    public static String page_Local_Users_Groups = "/sonicui/7/m/mgmt/users/policies-users-local-users-and-groups";
    public static String page_Tech_Support_Report = "/sonicui/7/m/mgmt/diagnostics/report";
    public static String page_Internal_Wireless_Settings = "/sonicui/7/m/mgmt/wireless/wireless-base-settings";
    public static String page_Internal_Wireless_Status = "/sonicui/7/m/mgmt/wireless/wireless-status";
    public static String page_Internal_Wireless_Security = "/sonicui/7/m/mgmt/wireless/wireless-security";
    public static String page_Network_Interfaces = "/sonicui/7/m/mgmt/network/interfaces";
    public static String page_DNS_Settings = "/sonicui/7/m/mgmt/network/dns";
    public static String page_Administration = "/sonicui/7/m/mgmt/system/administrator";

    // EXP Location
    public static String EXP_location = "C:\\Users\\admin\\Downloads\\";

    // ChromeUser Settings
    public static String user_data_dir = "--user-data-dir=C:\\Users\\admin\\AppData\\Local\\Google\\Chrome\\SonicWall_RTM_Full\\";
    public static String Chrome_userData_TZ_box = user_data_dir + "User Data2";

    // ChromeDriver Settings
    public static File my_path = new File(System.getProperty("user.dir"));
    public static String[] chromeDriver_data = {"webdriver.chrome.driver", my_path.getParent() + "\\Driver\\chromedriver.exe"};

    // Username field
    public static String userName_field = "//input[@class='sw-textfield__wrapper__input sw-typo-field-value sw-flexbox__flex']";
    public static String login_name_key = "admin";

    // Password field
    public static String login_password_field = "//input[@class='sw-textfield__wrapper__input sw-textfield__wrapper__input--with-icon-suffix sw-typo-field-value sw-flexbox__flex']";
    public static String login_pwd_sonicwall_key = "sonicwall";
    public static String login_pwd_password_key = "password";
    public static String PSK_pwd_key = "11111111";

    // Password change
    public static String pwd_change_button = "//button[text()='パスワードの変更']";
    public static String pwd_old_field = "//input[@name='oldPw']";
    public static String pwd_new_field = "//input[@name='newPw']";
    public static String pwd_new_confirm_field = "//input[@name='confirmPw']";
    // Setup Guide
    public static String setup_guide_link = "//div[text()='SonicWall を手動で構成するには、']/a";

    // LOGIN button
    public static String login_button = "//div[text()='ログイン']";
    public static String Safemode_login_button = "//div[text()='LOGIN']";

    // Preempt
    public static String preempt_button = "//button[text()='構成']";

    // Unregister banner
    public static String unregister_string = "//span[text()='未登録/未購読']";
    public static String close_button = "//div[@class='sw-status-info sw-status-info--failed sw-flexbox sw-typo-default']//span[@class='sw-icon__inner sw-font-icon icon-close-thin']";

    // LM not available banner
    public static String LM_NA_string = "//span[text()='ライセンス マネージャ利用不可']";

    // Automatic Firmware Updates
    public static String Auto_FW_Update_Cancel_button = "//button[@class='sw-button sw-button--light fw-app-main__button-cancel']";

    // Pop-up success message
    public static String SM_popup_success_string = "//div[text()='Success']";
    public static String popup_error_string = "//p[contains(text(),'Network Error')]"; // 7.0.1

    // Upload Firmware
    public static String FW_Upload_button = "//span[text()='ファームウェアのアップロード']";
    public static String backupSettings_button = "//button[text()='OK']";
    public static String browse_button = "//button[text()='参照']";
    public static String upload_button = "//button[text()='アップロード']";
    public static String import_button = "//button[text()='インポート']";
    public static String uploaded_FW_string = "//div[text()='アップロードされたファームウェア バージョン']/following-sibling::div";
    public static String reboot_button = "//span[@class='sw-icon__inner sw-font-icon icon-reboot']";
    public static String current_FW_reboot_button = "//div[@data-raw-entry-index='0']//span[@class='sw-icon__inner sw-font-icon icon-reboot']";
    public static String upload_FW_reboot_button = "//div[@data-raw-entry-index='1']//span[@class='sw-icon__inner sw-font-icon icon-reboot']";
    public static String Current_reboot_menu = "//div[contains(text(),'現在の構成')]";
    public static String SM_reboot_button = "//div[@data-raw-entry-index='1']//span[@class='sw-icon__inner sw-font-icon icon-reboot']";
    public static String boot_OK_button = "//button[text()='OK']";
    public static String Import_Export_Configuration_button = "//span[text()='構成のインポート/エクスポート']";
    public static String Export_Configuration_button = "//span[text()='構成のエクスポート']";
    public static String Import_Configuration_button = "//span[text()='構成のインポート']";
    public static String Export_button = "//button[text()='エクスポート']";
    public static String EXP_FileName_Gen7_string = "//span[@class='sw-flexbox--center-items sw-flexbox--center-justify imp-exp-config-component__label-2']";
    public static String EXP_FileName_Gen8_string = "//span[@class='imp-exp-config-component__label-2 sw-flexbox--center-items sw-flexbox--center-justify']";
    public static String current_FW_string = "//span[contains(text(),'現在のファームウェア') and @style='font-weight: 700;']/../following-sibling::div"; // 7.1.3

    // MySonicWall にログイン
    public static String MySonicWall_usr_field = "//input[@name='uname']";
    public static String MySonicWall_pwd_field = "//input[@name='auth']";
    public static String MySonicWall_login_button = "//button[text()='登録']";
    public static String MySonicWall_LOGIN_button = "//span[@class='sw-icon-button__label-cont sw-flexbox__flex-none' and text()='MySonicWall にログイン']";
    public static String Connection_failed_string = "//span[contains(text(),'失敗')]";

    // License
    public static String tenant_string = "//span[contains(text(),'テナント')]";
    public static String sync_button = "//span[@class='sw-icon__inner sw-font-icon icon-renew']";

    // Administration
    public static String login_MultipleAdmin_tab = "//span[contains(text(),'複数の管理者')]";
    public static String logout_time_idle_field = "//input[@name='inactivityTime']";

    // Local Users & Groups
    public static String Add_User_button = "//span[text()='ユーザの追加']";
    public static String Name_field = "//input[@name='username']";
    public static String User_Password_field = "//input[@placeholder='パスワードの入力...']";
    public static String Confirm_User_Password_field = "//input[@placeholder='パスワードの確認']";
    public static String Save_button = "//button[text()='保存']";
    public static String Local_user_test = "//span[text()='testuser']";

    // Tech Support Report
    public static String Download_Tech_Support_Report_button = "//button[text()='テクニカル サポート レポートのダウンロード']";
    public static String Download_Tech_Support_Report_OK_button = "//button[text()='OK']";

    // Dashboard
    public static String Licensed_string = "//span[text()='購読済']";
    public static String Device_entry_button = "//button[text()='デバイスの登録']";

    // Internal Wireless
    public static String Enable_WLAN_Radio_button = "//span[text()='WLAN を有効にする']/../following-sibling::div/div";
    public static String Confirm_button = "//button[text()='確認']";
    public static String Radio_Mode_listbox = "//span[text()='無線モード']/../following-sibling::div/div/div[@class='sw-select__label-cont sw-flexbox__flex sw-flexbox sw-flexbox--center-items sw-flexbox--wrap']";
    public static String Mode_5GHz_n_a_ac_Mixed_option = "//span[@class='sw-flexbox__flex sw-flexbox sw-flexbox--center-items' and text()='5GHz 802.11n/a/ac 混在']";
    public static String Radio_Band_listbox = "//span[text()='無線帯域']/../following-sibling::div/div/div[@class='sw-select__label-cont sw-flexbox__flex sw-flexbox sw-flexbox--center-items sw-flexbox--wrap']";
    public static String Band_Wide_80MHz_Channel_option = "//span[@class='sw-flexbox__flex sw-flexbox sw-flexbox--center-items' and text()='広域 80 MHz チャンネル']";
    public static String Enable_DFS_Channels_button = "//span[text()='DFS チャンネルを有効にする']/../following-sibling::div/div";
    public static String Channel_listbox = "//span[text()='チャンネル']/../following-sibling::div/div/div[@class='sw-select__label-cont sw-flexbox__flex sw-flexbox sw-flexbox--center-items sw-flexbox--wrap']";
    public static String Channel_100_5500MHz_option = "//span[@class='sw-flexbox__flex sw-flexbox sw-flexbox--center-items' and text()='100 チャンネル (5500 MHz*)']";
    public static String SSID_field = "//input[@name='ssid']";
    public static String Station_Status_tab = "//span[text()='ステーション状況']";
    public static String Associated_string = "//div[text()='参加済']";
    public static String Refresh_button = "//span[text()='再表示']";
    public static String Accept_button = "//button[text()='適用']";
    public static String Wireless_NA_string = "//span[contains(text(),'無線は利用できません')]";
    public static String Auth_type_listbox = "//div[@class='sw-select sw-select--light sw-typo-default sw-flexbox sw-flexbox--inline sw-flexbox--center-items sw-select--full sw-select--top-padding']";
    public static String WPA2_Auto_PSK_option = "//div[contains(@id, 'wpa2-auto-psk')]/span[text()='WPA2 – 自動 – PSK']";
    public static String PSK_Password_field = "//input[@name='passphrase']";

    // Interfaces
    public static String X1_IP_string = "//div[@data-raw-entry-index='1']/div[6]/div";
    public static String X1_edit_icon = "//span[@class='sw-icon__inner sw-font-icon icon-pencil']";
    public static String IP_Assignment = "//div[@class='sw-select sw-select--light sw-typo-default sw-flexbox sw-flexbox--inline sw-flexbox--center-items sw-select--top-padding general-ipv4__select-mode-ip-assignment']//span[@class='sw-icon__inner sw-font-icon icon-arrow-up']";
    public static String IP_Assignment_Static = "//span[text()='静的']";
    public static String IP_Address_field = "//input[@name='textfield-ip-address']";
    public static String Default_Gateway_field = "//input[@name='textfield-default-gateway']";
    public static String DNS_Server_1_field = "//input[@name='textfield-dns-server-1']";
    public static String DNS_Server_2_field = "//input[@name='textfield-dns-server-2']";
    public static String HTTPS_option = "//div[text()='HTTPS']/following-sibling::div/div";
    public static String Ping_option = "//div[text()='Ping']/following-sibling::div/div";
    public static String SNMP_option = "//div[text()='SNMP']/following-sibling::div/div";
    public static String SSH_option = "//div[text()='SSH']/following-sibling::div/div";
    public static String UserLogin_HTTPS_option = "//div[text()='HTTPS']/following-sibling::div/div";
    public static String Interface_OK_button = "//button[@class='sw-button sw-button--light sw-button--default configure-modal-ipv4__button-ok']";

    // DNS
    public static String Inherit_DNS_option = "//span[text()='WAN ゾーンと同じ DNS サーバ設定にする']";

    // Safemode
    public static String Maintenance_Key_field = "//input[contains(@placeholder,'Enter Maintenance Key')]";
    public static String Exit_Safe_Mode_button = "//span[text()='Exit Safe Mode']";
    public static String Current_Firmware_Version = "//p[text()='Current Firmware Version']";
    public static String SM_FW_Upload_button = "//span[text()='Upload Firmware']";
    public static String SM_browse_button = "//button[text()='Browse']";
    public static String SM_Refresh_button = "//span[text()='Refresh']";
    public static String Uploaded_Firmware_Version = "//p[text()='Uploaded Firmware Version']";
    public static String SM_uploaded_FW_string = "//p[text()='Uploaded Firmware Version']/following-sibling::span";

    // AuoIT
    public static String AutoIT_path = "C:\\Users\\admin\\IdeaProjects\\SonicWall_RTM_Full\\Auto\\";
    public static String Download_Tech_Support_Report_Open = AutoIT_path + "Download_Tech_Support_Report_Save.exe";

    // Tools
    public static String Tools_path = "C:\\Users\\admin\\IdeaProjects\\SonicWall_RTM_Full\\Tools\\SecureCRT\\";
    public static String SecureCRT_Open = Tools_path + "SecureCRT.exe";

}
