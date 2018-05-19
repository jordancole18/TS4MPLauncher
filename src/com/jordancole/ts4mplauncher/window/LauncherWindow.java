package com.jordancole.ts4mplauncher.window;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

import com.jordancole.ts4mplauncher.components.ComponentManager;
import com.jordancole.ts4mplauncher.utils.Utils;

public class LauncherWindow {

	public static final String DOWNLOAD_URL = Utils
			.getText("https://pastebin.com/raw/9LGKc4ai");

	public static Font DEFAULT_FONT;
	
	private JFrame frame;
	private ComponentManager compManager;

	public LauncherWindow() {
		try {
			DEFAULT_FONT = Font.createFont(Font.PLAIN, Utils.getInputStream("Roboto.ttf")).deriveFont(15f);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println(DOWNLOAD_URL);
		frame = new JFrame("TS4MP Launcher");
		frame.setFont(DEFAULT_FONT);
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setLayout(null);
		compManager = new ComponentManager(this);
		compManager.initComponents();
		frame.setIconImage(Utils.getImage("ts4_icon.png"));
		frame.setVisible(true);

//		if(FileManager.getInstance().getConfig().contains("modsFolder") == false){
//			JFileChooser f = new JFileChooser();
//			f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//			f.showOpenDialog(null);
//			System.out.println("SELECTED MODS FOLDER: " + f.getSelectedFile().getAbsolutePath());
//			FileManager.getInstance().getConfig().setProperty("modsFolder", f.getSelectedFile().getAbsolutePath());
//		}

	}

	public JFrame getFrame() {
		return frame;
	}

}
