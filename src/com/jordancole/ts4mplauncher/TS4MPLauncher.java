package com.jordancole.ts4mplauncher;

import static com.jordancole.ts4mplauncher.window.LauncherWindow.DEFAULT_FONT;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FileUtils;

import com.jordancole.ts4mplauncher.config.FileManager;
import com.jordancole.ts4mplauncher.popup.Popup;
import com.jordancole.ts4mplauncher.popup.PopupBuilder;
import com.jordancole.ts4mplauncher.utils.Utils;
import com.jordancole.ts4mplauncher.window.LauncherWindow;

public class TS4MPLauncher {

	public static void main(String[] args) {
		// Creates a instance of TS4MPLauncher which creates the main window.
		new TS4MPLauncher();
	}

	private LauncherWindow mainWindow;

	public static File modsFolder = new File(new JFileChooser()
			.getFileSystemView().getDefaultDirectory().toString(),
			"/Electronic Arts/The Sims 4/Mods");

	public static File gameDir;
	
	public TS4MPLauncher() {
		FileManager.getInstance().setup();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (FileManager.getInstance().getConfig().containsKey("modsFolder") == false
				|| FileManager.getInstance().getConfig()
						.containsKey("gameFolder") == false) {
			Utils.openSettings(true);
		}else{
			
			modsFolder = new File(FileManager.getInstance().getConfig().getProperty("modsFolder"));
			gameDir = new File(FileManager.getInstance().getConfig().getProperty("gameFolder"));
			System.out.println(modsFolder + "\n" + gameDir);
		}

		if (new File(modsFolder, "ts4multiplayer").exists() == false) {
			Utils.downloadMod();
			FileManager
					.getInstance()
					.getConfig()
					.setProperty("lastDownloadFromURL",
							LauncherWindow.DOWNLOAD_URL);
			FileManager.getInstance().save();
		} else {
			if (FileManager.getInstance().getConfig()
					.containsKey("lastDownloadFromURL")) {

				String url = FileManager.getInstance().getConfig()
						.getProperty("lastDownloadFromURL");

				if (LauncherWindow.DOWNLOAD_URL.equals(url) == false) {

					JPanel panel = new JPanel();
					panel.setLayout(null);
					panel.setSize(400, 300);
					JLabel label = new JLabel(
							"Would you like to update your mod?");
					label.setFont(LauncherWindow.DEFAULT_FONT);
					label.setSize(400, 30);
					panel.add(label);
					label.setLocation((panel.getWidth() / 2)
							- (label.getFontMetrics(label.getFont())
									.stringWidth(label.getText()) / 2), 100);

					JButton updateBtn = new JButton("Update");
					panel.add(updateBtn);
					updateBtn.setFont(DEFAULT_FONT);
					updateBtn.setSize(150, 50);
					updateBtn.setBorderPainted(false);
					updateBtn.setFocusPainted(false);
					updateBtn.setContentAreaFilled(false);
					updateBtn.setBackground(new Color(147, 150, 155));
					updateBtn.setOpaque(true);
					updateBtn.setFont(updateBtn.getFont().deriveFont(15f));
					updateBtn
							.setLocation(
									(panel.getWidth() / 2)
											- (updateBtn.getWidth() / 2), 200);
					updateBtn.addMouseListener(new MouseAdapter() {

						public void mouseClicked(java.awt.event.MouseEvent e) {
							try {
								if (Popup.currentPopup == null)
									return;

								Popup.currentPopup.closePopup();

								FileUtils.deleteDirectory(new File(modsFolder,
										"ts4multiplayer"));
								Utils.downloadMod();
								FileManager
										.getInstance()
										.getConfig()
										.setProperty("lastDownloadFromURL",
												LauncherWindow.DOWNLOAD_URL);
								FileManager.getInstance().save();
							} catch (IOException ev) {
								// TODO Auto-generated catch block
								ev.printStackTrace();
							}
						}

						public void mouseEntered(java.awt.event.MouseEvent e) {
							updateBtn.setBackground(new Color(174, 206, 63));
						}

						public void mouseExited(java.awt.event.MouseEvent e) {
							updateBtn.setBackground(new Color(147, 150, 155));
						}

					});
					PopupBuilder.createPopup("Update Mod", panel);
				}

			}

		}

		mainWindow = new LauncherWindow();
	}

	public LauncherWindow getMainWindow() {
		return mainWindow;
	}

}
