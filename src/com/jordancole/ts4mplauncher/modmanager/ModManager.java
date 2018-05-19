package com.jordancole.ts4mplauncher.modmanager;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import com.jordancole.ts4mplauncher.TS4MPLauncher;
import com.jordancole.ts4mplauncher.popup.PopupBuilder;

public class ModManager {

	@SuppressWarnings("resource")
	public static void setServerConfig(String ip, int port) throws Exception{
		File serverConfig = new File(TS4MPLauncher.modsFolder,
				"/ts4multiplayer/Scripts/ts4mp/configs/server_config.py");

		BufferedReader br = new BufferedReader(new FileReader(serverConfig));

		StringBuffer inputBuffer = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			inputBuffer.append(line);
			inputBuffer.append('\n');
		}

		String finalLine = inputBuffer.toString();

		String[] data = finalLine.split("\n");

		data[0] = "HOST = \"" + ip + "\"";
		data[1] = "PORT = " + port;
		finalLine = null;
		for (String s : data) {

			if (finalLine == null) {
				finalLine = s;
			} else {
				finalLine += "\n" + s;
			}
		}

		FileOutputStream fileOut = new FileOutputStream(serverConfig);
		fileOut.write(finalLine.getBytes());
		fileOut.close();
	}
	
	public static void runClient(String ip, int port) throws Exception {
		setServerConfig(ip, port);
		
		File configFile = new File(TS4MPLauncher.modsFolder,
				"/ts4multiplayer/Scripts/ts4mp/core/client.txt");
		
		File serverFile = new File(TS4MPLauncher.modsFolder,
				"/ts4multiplayer/Scripts/ts4mp/core/server.txt");
		
		if(serverFile.exists()){
			serverFile.delete();
		}
		
		if(!configFile.exists()){
			configFile.createNewFile();
		}
		
		if(TS4MPLauncher.gameDir == null){
			PopupBuilder.createError("You do not have a valid \ngame executable set!");
			return;
		}
		
		Desktop.getDesktop().open(TS4MPLauncher.gameDir);
	}

	@SuppressWarnings("resource")
	public static void runServer(String ip, int port) throws Exception{
		setServerConfig(ip, port);
		
		File mpServer = new File(TS4MPLauncher.modsFolder,
				"/ts4multiplayer/Scripts/ts4mp/core/multiplayer_server.py");
	
		BufferedReader br = new BufferedReader(new FileReader(mpServer));

		StringBuffer inputBuffer = new StringBuffer();
		String line;
		boolean hostDone = false, portDone = false;
		while ((line = br.readLine()) != null) {
			
			if(line.contains("self.host = ") && hostDone == false){
				line = "        self.host = \"" + ip + "\"";
				hostDone = true;
			}else if(line.contains("self.port") && portDone == false){
				line = "        self.port = " + port;
				portDone = true;
			}
			
			inputBuffer.append(line);
			inputBuffer.append('\n');
		}

		String finalLine = inputBuffer.toString();
		FileOutputStream fileOut = new FileOutputStream(mpServer);
		fileOut.write(finalLine.getBytes());
		fileOut.close();
		
		File configFile = new File(TS4MPLauncher.modsFolder,
				"/ts4multiplayer/Scripts/ts4mp/core/client.txt");
		
		File serverFile = new File(TS4MPLauncher.modsFolder,
				"/ts4multiplayer/Scripts/ts4mp/core/server.txt");
		
		if(configFile.exists()){
			configFile.delete();
		}
		
		if(!serverFile.exists()){
			serverFile.createNewFile();
		}
		
		if(TS4MPLauncher.gameDir == null){
			PopupBuilder.createError("You do not have a valid \ngame executable set!");
			return;
		}
		
		Desktop.getDesktop().open(TS4MPLauncher.gameDir);
	}
	
}
