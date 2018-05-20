package com.jordancole.ts4mplauncher.utils;

import static com.jordancole.ts4mplauncher.window.LauncherWindow.DEFAULT_FONT;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;

import net.lingala.zip4j.core.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.jordancole.ts4mplauncher.TS4MPLauncher;
import com.jordancole.ts4mplauncher.config.FileManager;
import com.jordancole.ts4mplauncher.popup.Popup;
import com.jordancole.ts4mplauncher.popup.PopupBuilder;
import com.jordancole.ts4mplauncher.window.LauncherWindow;

public class Utils {

	public static BufferedImage getImage(String name) {
		try {
			return ImageIO.read(TS4MPLauncher.class
					.getResourceAsStream("/resources/" + name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void unzip(String zipFilePath, String destDir) {
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static InputStream getInputStream(String name) {
		return TS4MPLauncher.class.getResourceAsStream("/resources/" + name);
	}

	public static void downloadFile(String urlText, File dest) throws Exception {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		URL url = new URL(urlText);
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();

		ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(null,
				"Downloading Mod", is);
		int downloadSize = connection.getContentLength();
		ProgressMonitor pm = pmis.getProgressMonitor();
		pm.setMillisToDecideToPopup(0);
		pm.setMillisToPopup(0);
		pm.setMinimum(0);
		pm.setMaximum(downloadSize);
		FileUtils.copyInputStreamToFile(pmis, dest);
	}

	public static void downloadMod() {

		File f = new File(TS4MPLauncher.modsFolder, "download.zip");

		try {
			Utils.downloadFile(LauncherWindow.DOWNLOAD_URL, f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ZipFile zip = new ZipFile(f);
			zip.extractAll(new File(TS4MPLauncher.modsFolder, "download_temp")
					.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.delete();

		File fo = new File(TS4MPLauncher.modsFolder, "download_temp");
		for (File tempF : fo.listFiles()) {
			File toDir = new File(fo, "ts4multiplayer");
			tempF.renameTo(toDir);
			try {
				FileUtils.copyDirectoryToDirectory(toDir,
						TS4MPLauncher.modsFolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileUtils.deleteDirectory(fo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getText(String website) {

		String finalLine = null;

		try {

			URL url = new URL(website);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;

			while ((line = br.readLine()) != null) {
				if (line != null) {
					finalLine = line;
				}
			}

		} catch (IOException e) {
			System.exit(-1);
			e.printStackTrace();
		}

		return finalLine.trim();
	}

	public static void openSettings(boolean freeze) {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(203, 206, 211));
		panel.setSize(500, 400);
		panel.setLayout(null);
		JLabel modsFolderLabel = new JLabel("Mods Folder");
		modsFolderLabel.setFont(DEFAULT_FONT);
		modsFolderLabel.setSize(100, 15);
		panel.add(modsFolderLabel);
		modsFolderLabel.setLocation((panel.getWidth() / 2)
				- (modsFolderLabel
						.getFontMetrics(modsFolderLabel.getFont())
						.stringWidth(modsFolderLabel.getText()) / 2), 100);
		
		JLabel gameFolderLabel = new JLabel("The Sims 4 Application Executable");
		gameFolderLabel.setFont(DEFAULT_FONT);
		gameFolderLabel.setSize(250, 15);
		panel.add(gameFolderLabel);
		gameFolderLabel.setLocation((panel.getWidth() / 2)
				- (gameFolderLabel
						.getFontMetrics(gameFolderLabel.getFont())
						.stringWidth(gameFolderLabel.getText()) / 2), 200);

		JTextField modsFolder = new JTextField();

		modsFolder.setSize(200, 20);
		modsFolder.setFont(DEFAULT_FONT);
		panel.add(modsFolder);
		
		if (FileManager.getInstance().getConfig().containsKey("modsFolder")) {
			modsFolder.setText(FileManager.getInstance().getConfig()
					.getProperty("modsFolder"));
		}
		
		modsFolder.setEnabled(false);
		modsFolder.setLocation(
				(panel.getWidth() / 2) - (modsFolder.getWidth() / 2), 125);
		
		JTextField gameFolder = new JTextField();

		gameFolder.setSize(200, 20);
		gameFolder.setFont(DEFAULT_FONT);
		panel.add(gameFolder);
		
		if (FileManager.getInstance().getConfig().containsKey("gameFolder")) {
			gameFolder.setText(FileManager.getInstance().getConfig()
					.getProperty("gameFolder"));
		}
		
		gameFolder.setEnabled(false);
		gameFolder.setLocation(
				(panel.getWidth() / 2) - (gameFolder.getWidth() / 2), 225);

		gameFolder.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){}
		});
		
		JButton folderOne = new JButton();
		folderOne.setSize(32, 32);
		folderOne.setContentAreaFilled(false);
		folderOne.setFocusPainted(false);
		folderOne.setBorderPainted(false);
		folderOne.setIcon(new ImageIcon(Utils.getImage("folderIcon.png")));
		folderOne.setRolloverEnabled(true);
		folderOne.setRolloverIcon(new ImageIcon(Utils.getImage("folderIconHover.png")));
		panel.add(folderOne);
		folderOne.setLocation((panel.getWidth() / 2) - (modsFolder.getWidth() / 2) + 205, 120);
		
		folderOne.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showOpenDialog(null);
				if(f.getSelectedFile() == null) return;
				modsFolder.setText(f.getSelectedFile().getAbsolutePath());
				TS4MPLauncher.modsFolder = f.getSelectedFile();
				FileManager.getInstance().getConfig().setProperty("modsFolder", f.getSelectedFile().getAbsolutePath());
				FileManager.getInstance().save();
			}
			
		});
		
		JButton folderTwo = new JButton();
		folderTwo.setSize(32, 32);
		folderTwo.setContentAreaFilled(false);
		folderTwo.setFocusPainted(false);
		folderTwo.setBorderPainted(false);
		folderTwo.setIcon(new ImageIcon(Utils.getImage("folderIcon.png")));
		folderTwo.setRolloverEnabled(true);
		folderTwo.setRolloverIcon(new ImageIcon(Utils.getImage("folderIconHover.png")));
		panel.add(folderTwo);
		folderTwo.setLocation((panel.getWidth() / 2) - (gameFolder.getWidth() / 2) + 205, 220);
		
		folderTwo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.FILES_ONLY);
				f.showOpenDialog(null);
				
				if(f.getSelectedFile() == null) return;
				if(FilenameUtils.getExtension(f.getSelectedFile().getName()).equalsIgnoreCase("exe") == false) return;
				
				gameFolder.setText(f.getSelectedFile().getAbsolutePath());
				TS4MPLauncher.gameDir = f.getSelectedFile();
				FileManager.getInstance().getConfig().setProperty("gameFolder", f.getSelectedFile().getAbsolutePath());
				FileManager.getInstance().save();
			}
			
		});
		
		JButton updateBtn = new JButton("Save");
		panel.add(updateBtn);
		updateBtn.setFont(DEFAULT_FONT);
		updateBtn.setSize(300, 35);
		updateBtn.setBorderPainted(false);
		updateBtn.setFocusPainted(false);
		updateBtn.setContentAreaFilled(false);
		updateBtn.setBackground(new Color(147, 150, 155));
		updateBtn.setOpaque(true);
		updateBtn.setFont(updateBtn.getFont().deriveFont(15f));
		updateBtn
				.setLocation(
						(panel.getWidth() / 2)
								- (updateBtn.getWidth() / 2), 350);
		updateBtn.addMouseListener(new MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (Popup.currentPopup == null)
					return;
				
				Popup.currentPopup.closePopup();
			}

			public void mouseEntered(java.awt.event.MouseEvent e) {
				updateBtn.setBackground(new Color(174, 206, 63));
			}

			public void mouseExited(java.awt.event.MouseEvent e) {
				updateBtn.setBackground(new Color(147, 150, 155));
			}

		});
		
		if(freeze){
			PopupBuilder.createPopup("Settings", panel);
		}else{
			PopupBuilder.createPopupNoFreeze("Settings", panel);
		}
	}

}
