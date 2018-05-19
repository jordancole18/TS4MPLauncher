package com.jordancole.ts4mplauncher.popup;

import static com.jordancole.ts4mplauncher.window.LauncherWindow.DEFAULT_FONT;

import java.awt.Color;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jordancole.ts4mplauncher.window.LauncherWindow;

public class PopupBuilder{
	
	public static Popup createPopup(String title, JPanel panel){
		
		Popup popup = new Popup(title, panel);
		popup.start();
		
		while(popup.isClosed() == false){
			
		}
		return popup;
	}
	
	public static Popup createPopupNoFreeze(String title, JPanel panel){
		Popup popup = new Popup(title, panel);
		popup.start();
		return popup;
	}
	
	public static Popup createError(String message){
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(300, 200);
		panel.setBackground(new Color(203, 206, 211));
		
		JLabel label = new JLabel(message);
		label.setFont(LauncherWindow.DEFAULT_FONT);
		panel.add(label);
		label.setLocation((panel.getWidth() / 2)
						- (label.getFontMetrics(label.getFont())
								.stringWidth(label.getText()) / 2), 50);
		
		JButton updateBtn = new JButton("Confirm");
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
		
		
		Popup popup = new Popup("Error", panel);
		popup.start();
		return popup;
		
	}
	
}
