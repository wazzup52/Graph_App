package com.graph.graphics.util;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

public class Arc {
	static JFrame frame;
	static JList<String> list;
	static JSplitPane split;

	public static JFrame show(Vector<String> data) {
		frame = new JFrame();
		list = new JList<String>(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBounds(0, 0, 250, 250);

		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScroller, null);
		split.setEnabled(false);
		split.setPreferredSize(new Dimension(listScroller.getWidth(), listScroller.getHeight()));
		split.setDividerLocation(listScroller.getHeight());

		frame.add(split);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		return frame;

	}
}

