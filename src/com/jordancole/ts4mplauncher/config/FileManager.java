package com.jordancole.ts4mplauncher.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileManager {

	private static FileManager instance;
	
	public static FileManager getInstance(){
		if(instance == null){
			instance = new FileManager();
		}
		return instance;
	}
	
	Properties prop = new Properties();
	File folder = new File(System.getenv("APPDATA"), "/TS4MPLauncher/");
	File f = new File(folder, "config.properties");
	
	public void setup(){
		if(folder.exists() == false){
			folder.mkdirs();
		}
		
		if(f.exists() == false){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			prop.load(new FileInputStream(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Properties getConfig(){
		return prop;
	}
	
	public void save(){
		try {
			prop.store(new FileOutputStream(f), "Properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
