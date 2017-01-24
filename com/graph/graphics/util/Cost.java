package com.graph.graphics.util;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.graph.Utilities;

public class Cost extends JDialog {

	private static final long serialVersionUID = 1L;

	public static void show(int x, int y) {

		JDialog frame = new JDialog();
		JLabel label = new JLabel("Insert cost:");
		JTextField field = new JTextField();
		JButton btn = new JButton("OK");

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!field.getText().isEmpty()) {
						Utilities.addCost(x, y, Integer.parseInt(field.getText()));
						frame.dispose();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Cost has to be number.");
				}
			}
		});

		frame.setLayout(new GridLayout(3, 1));
		frame.add(label);
		frame.add(field);
		frame.add(btn);

		frame.setModal(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		frame.pack();
		frame.setVisible(true);
	}

}

