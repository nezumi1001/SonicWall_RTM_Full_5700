package com.demo.apitest;

import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test_Clean {
    List<String> delete_paths = new ArrayList<>();
    static File my_path = new File(System.getProperty("user.dir"));
    static String delete_log_report = "\\src\\main\\resources\\Log\\report";
    static String delete_log_log4j = "\\src\\main\\resources\\Log\\log4j";
    static String delete_log_Screenshot_image = "\\src\\main\\resources\\Screenshot\\Image";

    public static void deleteAllFilesOfDir(File file) throws InterruptedException {
        if (!file.exists()) {
            System.out.println("[P]Print out >> No files!");
            return;
        }

        if (file.isFile()) {
            boolean result = file.delete();
            int tryCount = 0;
            while (!result && tryCount++ < 10) {
                System.gc(); // return source
                Thread.sleep(2000);
                result = file.delete();
            }
        }

        File[] files = file.listFiles();

        if (null != files) {
            /*for (int i = 0; i < files.length; i++) {
                deleteAllFilesOfDir(files[i]);
            }*/

            for (File value : files) {
                deleteAllFilesOfDir(value);
            }

            System.out.println("[P]Print out >> Deleted all files under " + file);
        }

        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    @Test
    public void test_delete_file() throws InterruptedException {
        System.out.println("[P]Print out >> Start...");
        System.out.println(my_path);

        delete_paths.add(delete_log_report);
        delete_paths.add(delete_log_log4j);
        delete_paths.add(delete_log_Screenshot_image);

        for (String delete_path : delete_paths)
            deleteAllFilesOfDir(new File(my_path + delete_path));

    }

}
