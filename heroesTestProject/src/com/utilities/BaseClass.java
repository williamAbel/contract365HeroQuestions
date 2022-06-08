package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

//Here we will store the all the application varaiables
public class BaseClass {
	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static File file;
	public static FileInputStream fi;

}
