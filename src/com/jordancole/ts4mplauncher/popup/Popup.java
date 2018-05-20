package com.jordancole.ts4mplauncher.popup;

import static com.jordancole.ts4mplauncher.window.LauncherWindow.DEFAULT_FONT;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jordancole.ts4mplauncher.utils.Utils;

public class Popup {

	public static Popup currentPopup;
	
	private String title;
	private JPanel content, topBar;
	private JButton exitBtn;
	private JFrame frame;
	private boolean isClosed;
	private Point initialClick;
	
	public Popup(String title, JPanel content){
		this.title = title;
		this.content = content;
		currentPopup = this;
	}
	
	public void start(){

		isClosed = false;
		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(content.getSize());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setUndecorated(true);
		//frame.setAlwaysOnTop(true);
		frame.setLayout(null);
		frame.setIconImage(Utils.getImage("ts4_icon.png"));
		
		topBar = new JPanel();
		topBar.setFont(DEFAULT_FONT);
		topBar.setLocation(0, 0);
		topBar.setLayout(null);
		topBar.setSize(frame.getWidth(), 33);
		topBar.setBackground(new Color(174, 206, 63));
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(DEFAULT_FONT);
		titleLabel.setLocation(10, 2);
		titleLabel.setSize(500, 33);
		topBar.add(titleLabel);
		
		exitBtn = new JButton();
		exitBtn.setIcon(new ImageIcon(Utils.getImage("exitButton.png")));
		exitBtn.setFont(DEFAULT_FONT);
		exitBtn.setSize(32, 32);
		exitBtn.setRolloverEnabled(true);
		exitBtn.setRolloverIcon(new ImageIcon(Utils
				.getImage("exitButtonHover.png")));
		exitBtn.setLocation(frame.getWidth() - 32, 0);
		exitBtn.setBorderPainted(false);
		exitBtn.setFocusPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.addActionListener(e -> {
			closePopup();
		});
		
		topBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
			}
		});

		topBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int thisX = frame.getLocation().x;
				int thisY = frame.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;

				frame.setLocation(X, Y);
			}
		});
		
		frame.add(exitBtn);
		frame.add(topBar);
		frame.add(content);
		frame.setVisible(true);
	}
	
	public void closePopup(){
		isClosed = true;
		frame.dispose();
		if(currentPopup == this){
			currentPopup = null;
		}
	}
	
	public String getTitle(){
		return title;
	}
	
	public JPanel getContent(){
		return content;
	}
	
	public boolean isClosed(){
		return isClosed;
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
}
