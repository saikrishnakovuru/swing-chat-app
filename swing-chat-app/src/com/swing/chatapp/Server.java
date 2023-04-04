package com.swing.chatapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Server extends SwingView {

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private DataInputStream din;
	private DataOutputStream dout;

	private String text;

	public Server() {
		frame.setTitle("Server");
		frame.setLocation(100, 100);
	}

	protected void extracted() {
		text = messageField.getText();
		if (!text.isEmpty()) {
			messagePanel = new JPanel();
			messagePanel.setLayout(new BorderLayout());
			messagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			messagePanel.setMaximumSize(new Dimension(chatAreaPanel.getWidth(), 50));

			try {
				dout.writeUTF(text);
			} catch (IOException e) {
				e.printStackTrace();
			}

			messageLabel = new JLabel(text);
			messageLabel.setOpaque(true);
			messageLabel.setBackground(new Color(7, 94, 84));
			messageLabel.setForeground(Color.WHITE);
			messageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

			messagePanel.add(messageLabel, BorderLayout.EAST);

			chatAreaPanel.add(messagePanel);

			chatAreaPanel.revalidate();
			chatAreaPanel.repaint();
		}
		messageField.setText("");
	}

	private void doStuff() {
		try {
			serverSocket = new ServerSocket(1234);
			while (true) {
				clientSocket = serverSocket.accept();
				din = new DataInputStream(clientSocket.getInputStream());
				dout = new DataOutputStream(clientSocket.getOutputStream());

				while (true) {
					text = din.readUTF();

					messagePanel = new JPanel();
					messagePanel.setLayout(new BorderLayout());
					messagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
					messagePanel.setMaximumSize(new Dimension(chatAreaPanel.getWidth(), 50));

					messageLabel = new JLabel(text);
					messageLabel.setOpaque(true);
					messageLabel.setBackground(new Color(7, 94, 84));
					messageLabel.setForeground(Color.WHITE);
					messageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

					messagePanel.add(messageLabel, BorderLayout.LINE_START);

					chatAreaPanel.add(messagePanel);

					chatAreaPanel.revalidate();
					chatAreaPanel.repaint();

					frame.validate();
					frame.repaint();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dout.close();
				din.close();
				serverSocket.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Server s = new Server();
		s.doStuff();

	}

}
