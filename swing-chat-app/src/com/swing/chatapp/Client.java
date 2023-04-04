package com.swing.chatapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Client extends SwingView {

	private Socket socket = null;
	private DataInputStream din;
	private DataOutputStream dout;
	private String text;

	public Client() {
		frame.setTitle("Client");
		frame.setLocationRelativeTo(null);
	}

	protected void extracted() {
		try {
			text = messageField.getText();
			if (!text.isEmpty()) {
				messagePanel = new JPanel();
				messagePanel.setLayout(new BorderLayout());
				messagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
				messagePanel.setMaximumSize(new Dimension(chatAreaPanel.getWidth(), 50));

				dout.writeUTF(text);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doStuff() {
		try {
			socket = new Socket("127.0.0.1", 1234);
			while (true) {
				din = new DataInputStream(socket.getInputStream());
				dout = new DataOutputStream(socket.getOutputStream());

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
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Client c = new Client();
		c.doStuff();

	}

}
