package com.swing.chatapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SwingView {

	protected JFrame frame;
	private JPanel headerPanel;
	protected JPanel chatAreaPanel;
	private JButton sendButton;
	protected JTextField messageField;
	protected JLabel messageLabel;
	protected JPanel messagePanel;

	public SwingView() {
		frame = new JFrame();
		sendButton = new JButton("send");
		setMessageField(new JTextField());

		frame.setContentPane(buildView());
		frame.setSize(300, 500);
		frame.setVisible(true);
		setActions();
	}

	private JPanel buildView() {
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("f:1:g", "35dlu,1dlu,f:1:g,2dlu,p"));
		CellConstraints cc = new CellConstraints();
		builder.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		builder.add(headerComponent(), cc.xy(1, 1, CellConstraints.FILL, CellConstraints.FILL));
		builder.add(messageAreaComponent(), cc.xy(1, 3));
		builder.add(textFieldAndButtonComponent(), cc.xy(1, 5));
		return builder.getPanel();
	}

	private JPanel headerComponent() {
		headerPanel = new JPanel(new FormLayout("p,2dlu,p", "p"));
		CellConstraints cc = new CellConstraints();

		ImageIcon backIcon = new ImageIcon("Icons/back.png");
		Image back = backIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		JLabel backLabel = new JLabel(new ImageIcon(back));

		ImageIcon phoneIcon = new ImageIcon("Icons/phone.jpeg");
		Image phone = phoneIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		JLabel phoneLabel = new JLabel(new ImageIcon(phone));

		headerPanel.add(backLabel, cc.xy(1, 1));
		headerPanel.add(phoneLabel, cc.xy(3, 1));

		headerPanel.setBackground(new Color(7, 94, 84));
		headerPanel.setBorder(new CurvedBorder(20));
		return headerPanel;
	}

	private Component messageAreaComponent() {

		chatAreaPanel = new JPanel(new BorderLayout());
		chatAreaPanel.setLayout(new BoxLayout(chatAreaPanel, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane(chatAreaPanel);

		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});

		return scrollPane;

	}

	private JPanel textFieldAndButtonComponent() {
		DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout("f:1:g,4dlu,p", "p"));
		CellConstraints cc = new CellConstraints();
		sendButton.setBackground(new Color(7, 94, 84));
		sendButton.setOpaque(true);
		sendButton.setForeground(Color.WHITE);
		sendButton.setBorderPainted(false);
		sendButton.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		getMessageField().setBorder(new LineBorder(new Color(7, 94, 84)));
		getMessageField().requestFocusInWindow();

		builder.add(getMessageField(), cc.xy(1, 1));
		builder.add(sendButton, cc.xy(3, 1));

		return builder.getPanel();
	}

	private void setActions() {
		sendButton.addActionListener(e -> {
			extracted();
		});

		getMessageField().addActionListener(e -> {
			extracted();
		});
	}

	protected void extracted() {
		String text = getMessageField().getText();
		if (!text.isEmpty()) {
			messagePanel = new JPanel();
			messagePanel.setLayout(new BorderLayout());
			messagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
			messagePanel.setMaximumSize(new Dimension(chatAreaPanel.getWidth(), 50));

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
		getMessageField().setText("");
	}

	public JTextField getMessageField() {
		return messageField;
	}

	public void setMessageField(JTextField messageField) {
		this.messageField = messageField;
	}

}
