package com.jordancole.ts4mplauncher.components;

import static com.jordancole.ts4mplauncher.window.LauncherWindow.DEFAULT_FONT;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jordancole.ts4mplauncher.modmanager.ModManager;
import com.jordancole.ts4mplauncher.utils.Utils;
import com.jordancole.ts4mplauncher.window.LauncherWindow;

public class ComponentManager {

	private LauncherWindow window;

	private JPanel dragPane;
	private Point initialClick;

	private JPanel topBar;

	private JPanel hostPanel;
	private JPanel joinPanel;

	private JButton exitBtn;
	private JButton minimizeBtn;
	private JButton settingsBtn;
	private JButton hostBtn;
	private JButton joinBtn;
	private boolean hostSelected = false;

	public ComponentManager(LauncherWindow window) {
		this.window = window;
	}

	public void initComponents() {
		topBar = new JPanel();
		topBar.setFont(DEFAULT_FONT);
		topBar.setLocation(0, 0);
		topBar.setLayout(null);
		topBar.setSize(window.getFrame().getWidth(), 33);
		topBar.setBackground(new Color(174, 206, 63));
		JLabel title = new JLabel("TS4MP Launcher");
		title.setFont(DEFAULT_FONT);
		title.setLocation(10, 2);
		title.setSize(500, 33);
		topBar.add(title);

		dragPane = new JPanel();
		dragPane.setOpaque(false);
		dragPane.setSize(window.getFrame().getWidth() - 100, 30);

		dragPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
			}
		});

		dragPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int thisX = window.getFrame().getLocation().x;
				int thisY = window.getFrame().getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;

				window.getFrame().setLocation(X, Y);
			}
		});

		exitBtn = new JButton();
		minimizeBtn = new JButton();
		settingsBtn = new JButton();
		hostBtn = new JButton("Host");
		joinBtn = new JButton("Join");

		exitBtn.setIcon(new ImageIcon(Utils.getImage("exitButton.png")));
		exitBtn.setFont(DEFAULT_FONT);
		exitBtn.setSize(32, 32);
		exitBtn.setRolloverEnabled(true);
		exitBtn.setRolloverIcon(new ImageIcon(Utils
				.getImage("exitButtonHover.png")));
		exitBtn.setLocation(window.getFrame().getWidth() - 32, 0);
		exitBtn.setBorderPainted(false);
		exitBtn.setFocusPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.addActionListener(e -> {
			System.exit(0);
		});

		minimizeBtn
				.setIcon(new ImageIcon(Utils.getImage("minimizeButton.png")));
		minimizeBtn.setFont(DEFAULT_FONT);
		minimizeBtn.setSize(32, 32);
		minimizeBtn.setRolloverEnabled(true);
		minimizeBtn.setRolloverIcon(new ImageIcon(Utils
				.getImage("minimizeButtonHover.png")));
		minimizeBtn.setLocation(window.getFrame().getWidth() - 64, 0);
		minimizeBtn.setBorderPainted(false);
		minimizeBtn.setFocusPainted(false);
		minimizeBtn.setContentAreaFilled(false);
		minimizeBtn.addActionListener(e -> {
			window.getFrame().setState(Frame.ICONIFIED);
		});

		settingsBtn
				.setIcon(new ImageIcon(Utils.getImage("settingsButton.png")));
		settingsBtn.setFont(DEFAULT_FONT);
		settingsBtn.setSize(28, 28);
		settingsBtn.setRolloverEnabled(true);
		settingsBtn.setRolloverIcon(new ImageIcon(Utils
				.getImage("settingsButtonHover.png")));
		settingsBtn.setLocation(window.getFrame().getWidth() - 98, 2);
		settingsBtn.setBorderPainted(false);
		settingsBtn.setFocusPainted(false);
		settingsBtn.setContentAreaFilled(false);
		settingsBtn.addActionListener(e -> {
			Utils.openSettings(false);
		});

		hostBtn.setContentAreaFilled(false);
		hostBtn.setFont(DEFAULT_FONT);
		hostBtn.setSize(200, 40);
		hostBtn.setBorderPainted(false);
		hostBtn.setContentAreaFilled(false);
		hostBtn.setFocusPainted(false);
		hostBtn.setBackground(new Color(206, 215, 229));
		hostBtn.setOpaque(true);
		hostBtn.setFont(hostBtn.getFont().deriveFont(28f));
		hostBtn.setLocation(
				((window.getFrame().getWidth() / 2) - (hostBtn.getWidth() / 2)) - 100,
				75);
		hostBtn.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				if (!hostSelected) {
					hostBtn.setBackground(new Color(176, 180, 186));
				}
			}

			public void mouseExited(MouseEvent e) {
				if (hostSelected) {
					hostBtn.setBackground(new Color(147, 150, 155));
				} else {
					hostBtn.setBackground(new Color(206, 215, 229));
				}
			}

			public void mousePressed(MouseEvent e) {

				if (!hostSelected) {
					hostSelected = true;
					joinBtn.setBorderPainted(false);
					hostBtn.setBorderPainted(true);
					hostBtn.setBackground(new Color(147, 150, 155));
					joinBtn.setBackground(new Color(206, 215, 229));
					joinPanel.setVisible(false);
					hostPanel.setVisible(true);
				}

			}
		});

		joinBtn.setContentAreaFilled(false);
		joinBtn.setFont(DEFAULT_FONT);
		joinBtn.setSize(200, 40);
		joinBtn.setBorderPainted(false);
		joinBtn.setContentAreaFilled(false);
		joinBtn.setFocusPainted(false);
		joinBtn.setBackground(new Color(147, 150, 155));
		joinBtn.setOpaque(true);
		joinBtn.setFont(joinBtn.getFont().deriveFont(28f));
		joinBtn.setLocation(
				((window.getFrame().getWidth() / 2) - (joinBtn.getWidth() / 2)) + 100,
				75);
		joinBtn.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				if (hostSelected) {
					joinBtn.setBackground(new Color(176, 180, 186));
				}
			}

			public void mouseExited(MouseEvent e) {
				if (!hostSelected) {
					joinBtn.setBackground(new Color(147, 150, 155));
				} else {
					joinBtn.setBackground(new Color(206, 215, 229));
				}
			}

			public void mousePressed(MouseEvent e) {

				if (hostSelected) {
					hostSelected = false;
					joinBtn.setBorderPainted(true);
					hostBtn.setBorderPainted(false);
					joinBtn.setBackground(new Color(147, 150, 155));
					hostBtn.setBackground(new Color(206, 215, 229));
					joinPanel.setVisible(true);
					hostPanel.setVisible(false);
				}

			}

		});

		hostPanel = new JPanel();
		hostPanel.setFont(DEFAULT_FONT);
		hostPanel.setLayout(null);
		hostPanel.setSize(400, 415);
		hostPanel.setBackground(new Color(176, 180, 186));
		hostPanel
				.setLocation(((window.getFrame().getWidth() / 2) - (hostBtn
						.getWidth() / 2)) - 100, 125);
		hostPanel.setVisible(false);
		initHostPanel(hostPanel);

		joinPanel = new JPanel();
		joinPanel.setFont(DEFAULT_FONT);
		joinPanel.setLayout(null);
		joinPanel.setSize(400, 415);
		joinPanel.setBackground(new Color(176, 180, 186));
		joinPanel
				.setLocation(((window.getFrame().getWidth() / 2) - (joinBtn
						.getWidth() / 2)) - 100, 125);
		joinPanel.setVisible(true);
		initJoinPanel(joinPanel);

		window.getFrame().add(hostPanel);
		window.getFrame().add(joinPanel);
		window.getFrame().add(joinBtn);
		window.getFrame().add(hostBtn);
		window.getFrame().add(settingsBtn);
		window.getFrame().add(minimizeBtn);
		window.getFrame().add(exitBtn);
		window.getFrame().add(dragPane);
		window.getFrame().add(topBar);
	}

	private JLabel joinipLabel, joinportLabel;
	private JTextField joinipField, joinportField;

	private JLabel hostipLabel, hostportLabel;
	private JTextField hostipField, hostportField;
	
	public void initHostPanel(JPanel panel){
		panel.setFont(DEFAULT_FONT);
		hostipLabel = new JLabel("IP:");
		hostipLabel.setFont(DEFAULT_FONT);
		hostipLabel.setSize(50, 15);
		hostipLabel.setLocation(
				3
						+ (panel.getWidth() / 2)
						- (hostipLabel.getFontMetrics(hostipLabel.getFont())
								.stringWidth(hostipLabel.getText()) / 2), 15);

		hostportLabel = new JLabel("Port:");
		hostportLabel.setFont(DEFAULT_FONT);
		hostportLabel.setSize(50, 30);
		hostportLabel.setLocation(
				3
						+ (panel.getWidth() / 2)
						- (hostportLabel.getFontMetrics(hostportLabel.getFont())
								.stringWidth(hostportLabel.getText()) / 2), 100);

		hostipField = new JTextField("0.tcp.ngrok.io");
		hostipField.setFont(DEFAULT_FONT);
		hostipField.setSize(200, 25);
		hostipField.setLocation(3 + (panel.getWidth() / 2)
				- (hostipField.getWidth() / 2), 50);

		hostportField = new JTextField("9999");
		hostportField.setFont(DEFAULT_FONT);
		hostportField.setSize(200, 25);
		hostportField.setLocation(
				3 + (panel.getWidth() / 2) - (hostportField.getWidth() / 2), 145);

		JButton connect = new JButton("Host");
		connect.setFont(DEFAULT_FONT);
		connect.setSize(300, 38);
		connect.setBorderPainted(false);
		connect.setFocusPainted(false);
		connect.setContentAreaFilled(false);
		connect.setBackground(new Color(147, 150, 155));
		connect.setOpaque(true);
		connect.setFont(connect.getFont().deriveFont(25f));
		connect.setLocation((panel.getWidth() / 2) - (connect.getWidth() / 2),
				360);

		hostportField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});

		connect.addMouseListener(new MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent e) {
				try {
					ModManager.runServer(hostipField.getText(),
							Integer.parseInt(hostportField.getText()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent e) {
				connect.setBackground(new Color(174, 206, 63));
			}

			public void mouseExited(java.awt.event.MouseEvent e) {
				connect.setBackground(new Color(147, 150, 155));
			}

		});

		panel.add(connect);
		panel.add(hostportField);
		panel.add(hostipField);
		panel.add(hostipLabel);
		panel.add(hostportLabel);
	}
	
	public void initJoinPanel(JPanel panel) {
		panel.setFont(DEFAULT_FONT);
		joinipLabel = new JLabel("IP:");
		joinipLabel.setFont(DEFAULT_FONT);
		joinipLabel.setSize(50, 15);
		joinipLabel.setLocation(
				3
						+ (panel.getWidth() / 2)
						- (joinipLabel.getFontMetrics(joinipLabel.getFont())
								.stringWidth(joinipLabel.getText()) / 2), 15);

		joinportLabel = new JLabel("Port:");
		joinportLabel.setFont(DEFAULT_FONT);
		joinportLabel.setSize(50, 30);
		joinportLabel.setLocation(
				3
						+ (panel.getWidth() / 2)
						- (joinportLabel.getFontMetrics(joinportLabel.getFont())
								.stringWidth(joinportLabel.getText()) / 2), 100);

		joinipField = new JTextField("0.tcp.ngrok.io");
		joinipField.setFont(DEFAULT_FONT);
		joinipField.setSize(200, 25);
		joinipField.setLocation(3 + (panel.getWidth() / 2)
				- (joinipField.getWidth() / 2), 50);

		joinportField = new JTextField("9999");
		joinportField.setFont(DEFAULT_FONT);
		joinportField.setSize(200, 25);
		joinportField.setLocation(
				3 + (panel.getWidth() / 2) - (joinportField.getWidth() / 2), 145);

		JButton connect = new JButton("Connect");
		connect.setFont(DEFAULT_FONT);
		connect.setSize(300, 38);
		connect.setBorderPainted(false);
		connect.setFocusPainted(false);
		connect.setContentAreaFilled(false);
		connect.setBackground(new Color(147, 150, 155));
		connect.setOpaque(true);
		connect.setFont(connect.getFont().deriveFont(25f));
		connect.setLocation((panel.getWidth() / 2) - (connect.getWidth() / 2),
				360);

		joinportField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});

		connect.addMouseListener(new MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent e) {
				try {
					ModManager.runClient(joinipField.getText(),
							Integer.parseInt(joinportField.getText()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent e) {
				connect.setBackground(new Color(174, 206, 63));
			}

			public void mouseExited(java.awt.event.MouseEvent e) {
				connect.setBackground(new Color(147, 150, 155));
			}

		});

		panel.add(connect);
		panel.add(joinportField);
		panel.add(joinipField);
		panel.add(joinipLabel);
		panel.add(joinportLabel);
	}

}
