package com.graph.graphics;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.algorithms.BoruvkaMT;
import com.graph.algorithms.GenericMT;
import com.graph.algorithms.KruskalMT;
import com.graph.algorithms.PBF;
import com.graph.algorithms.PDF;
import com.graph.algorithms.PG;

import com.graph.graphics.PaintPane;
import com.graph.graphics.util.Arc;
import com.graph.graphics.util.Graph;
import com.graph.graphics.util.Tree;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;// Warning

	private JLabel mainLabel, currentLabel;
	private JPanel secondLabel;
	public static JSplitPane mainPane;
	private JButton normalGBtn, directGBtn, showArcsBtn, showCurrentBtn, alg1Btn, alg1TBtn, alg2Btn, alg2TBtn, alg3Btn,
			alg3TBtn, exportBtn, importBtn, costBtn, mTreeAlg1Btn, mTreeAlg2Btn,mTreeAlg3Btn;
	private JTextField eField, iField, a1Field1, a2Field1, a3Field1;

	public int width1 = 300;
	public int height = 500;
	public int width2 = 500;

	public Panel() {
		/*---Initializating split pane and his components-----*/

		mainLabel = new JLabel();
		mainLabel.setMinimumSize(new Dimension(width1, height));
		mainLabel.setBounds(0, 0, width1, height);

		currentLabel = new JLabel("Normal");
		currentLabel.setBounds(0, 0, 100, 30);
		mainLabel.add(currentLabel);

		secondLabel = new JPanel();
		secondLabel.setBounds(0, 0, width2, height);

		/*---Buttons----*/
		/****** Main Buttons ******/
		normalGBtn = new JButton("New undirected Graph");
		directGBtn = new JButton("New directed Graph");
		showArcsBtn = new JButton("Current arcs");
		showCurrentBtn = new JButton("Current graph");
		costBtn = new JButton("+ Cost");

		normalGBtn.setBounds(10, 75, 200, 30);
		directGBtn.setBounds(10, 110, 200, 30);
		showArcsBtn.setBounds(180, 40, 110, 30);
		showCurrentBtn.setBounds(175, 5, 120, 30);
		costBtn.setBounds(215, 75, 70, 30);

		mainLabel.add(normalGBtn);
		mainLabel.add(directGBtn);
		mainLabel.add(showArcsBtn);
		mainLabel.add(showCurrentBtn);
		mainLabel.add(costBtn);

		/****** Export/Import buttons and text fields ******/
		exportBtn = new JButton("Export graph to");
		eField = new JTextField("write path");
		importBtn = new JButton("Import graph from");
		iField = new JTextField("write path");

		exportBtn.setBounds(10, 420, 130, 30);
		eField.setBounds(150, 420, 150, 30);
		importBtn.setBounds(5, 450, 140, 30);
		iField.setBounds(150, 450, 150, 30);

		mainLabel.add(exportBtn);
		mainLabel.add(eField);
		mainLabel.add(importBtn);
		mainLabel.add(iField);

		/** -----GenericAlg Buttons/Fields----- **/
		alg1Btn = new JButton("Partial PG");
		a1Field1 = new JTextField();
		alg1TBtn = new JButton("Total PG");

		alg1Btn.setBounds(10, 180, 100, 30);
		a1Field1.setBounds(115, 180, 30, 30);
		alg1TBtn.setBounds(170, 180, 100, 30);

		mainLabel.add(alg1Btn);
		mainLabel.add(a1Field1);
		mainLabel.add(alg1TBtn);

		/** ------PBFAlg Buttons/Fields------ **/
		alg2Btn = new JButton("Partial PBF");
		a2Field1 = new JTextField();
		alg2TBtn = new JButton("Total PBF");

		alg2Btn.setBounds(10, 215, 100, 30);
		a2Field1.setBounds(115, 215, 30, 30);
		alg2TBtn.setBounds(170, 215, 100, 30);

		mainLabel.add(alg2Btn);
		mainLabel.add(a2Field1);
		mainLabel.add(alg2TBtn);

		/** ------PDFAlg Buttons/Fields------ **/
		alg3Btn = new JButton("Partial PDF");
		a3Field1 = new JTextField();
		alg3TBtn = new JButton("Total PDF");

		alg3Btn.setBounds(10, 250, 100, 30);
		a3Field1.setBounds(115, 250, 30, 30);
		alg3TBtn.setBounds(170, 250, 100, 30);

		mainLabel.add(alg3Btn);
		mainLabel.add(a3Field1);
		mainLabel.add(alg3TBtn);

		mTreeAlg1Btn = new JButton("Generic Minimal Tree");
		mTreeAlg2Btn = new JButton("Kruskal Minimal Tree");
		mTreeAlg3Btn = new JButton("Boruvka Minimal Tree");
		mTreeAlg1Btn.setBounds(10, 290, 170, 30);
		mTreeAlg2Btn.setBounds(10, 330, 170, 30);
		mTreeAlg3Btn.setBounds(10, 370, 170, 30);
		mainLabel.add(mTreeAlg1Btn);
		mainLabel.add(mTreeAlg2Btn);
		mainLabel.add(mTreeAlg3Btn);

		/*--- Adding the right/left panes to the split pane----*/

		secondLabel.add(new PaintPane());
		secondLabel.getComponent(0).setBounds(0, 0, width2, height);

		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainLabel, new PaintPane());
		mainPane.setSize(width1 + width2, height);
		mainPane.setEnabled(false);
		mainPane.setPreferredSize(new Dimension(width1 + width2, height));
		mainPane.setDividerLocation(width1);

		/*---Events----*/

		costBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.current == 0) {
					Utilities.reset();
					Main.enableCost = true;
					mainPane.setRightComponent(new PaintPane());

				}

			}
		});

		normalGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLabel.setText("Normal");
				Main.current = 0;
				Utilities.reset();
				mainPane.getRightComponent().repaint();
				mainPane.getRightComponent().setBounds(0, 0, width2, height);
				mainPane.setRightComponent(new PaintPane());
			}
		});

		directGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLabel.setText("Direct");
				Main.current = 1;
				Utilities.reset();
				mainPane.getRightComponent().repaint();
				mainPane.getRightComponent().setBounds(0, 0, width2, height);
				mainPane.setRightComponent(new PaintPane());
			}
		});

		showArcsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Arc.show(Utilities.arcsToString());
				System.out.println(Main.costuri);

			}
		});

		alg1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!a1Field1.getText().isEmpty()) {
					try {
						int s = Integer.parseInt(a1Field1.getText());
						if (s < Main.nodeNr) {
							Vector<Integer> p = PG.partialAlgorithm(s);

							mainPane.getRightComponent().setBounds(0, 0, width2, height);
							mainPane.setRightComponent(new Graph(p));
						} else
							JOptionPane.showMessageDialog(mainPane,
									" Field must have number smaller than " + (Main.nodeNr - 1));
					} catch (Exception ex) {
						// ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Field must have number");
					}

				} else
					JOptionPane.showMessageDialog(null, "Field must not be empty and graph has to be directed");
			}
		});
		alg1TBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<Integer> p = PG.totalAlgorithm();

				mainPane.getRightComponent().setBounds(0, 0, width2, height);
				mainPane.setRightComponent(new Graph(p));
			}
		});

		alg2Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!a2Field1.getText().isEmpty()) {
					try {

						int s = Integer.parseInt(a2Field1.getText());
						if (s < Main.nodeNr) {
							Vector<Integer> p = PBF.partialAlgorithm(s);
							mainPane.getRightComponent().setBounds(0, 0, width2, height);
							mainPane.setRightComponent(new Graph(p));
						} else
							JOptionPane.showMessageDialog(mainPane,
									" Field must have number smaller than " + (Main.nodeNr - 1));
					} catch (Exception ex) {
						// ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Field must have number");
					}

				} else
					JOptionPane.showMessageDialog(null, "Field must not be empty and graph has to be directed");
			}
		});
		alg2TBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<Integer> p = PBF.totalAlgorithm();

				mainPane.getRightComponent().setBounds(0, 0, width2, height);
				mainPane.setRightComponent(new Graph(p));
			}
		});

		alg3Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!a3Field1.getText().isEmpty()) {
					try {
						int s = Integer.parseInt(a3Field1.getText());
						if (s < Main.nodeNr) {
							Vector<Integer> p = PDF.partialAlgorithm(s);
							mainPane.getRightComponent().setBounds(0, 0, width2, height);
							mainPane.setRightComponent(new Graph(p));
						} else
							JOptionPane.showMessageDialog(mainPane,
									" Field must have number smaller than " + (Main.nodeNr - 1));
					} catch (Exception ex) {
						// ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Field must have number");
					}

				} else
					JOptionPane.showMessageDialog(null, "Field must not be empty and graph has to be directed");
			}
		});
		alg3TBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<Integer> p = PDF.totalAlgorithm();

				mainPane.getRightComponent().setBounds(0, 0, width2, height);
				mainPane.setRightComponent(new Graph(p));
			}
		});

		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!eField.getText().equals("write path")) {
					Utilities.exportFile(eField.getText() + ".txt");
					JOptionPane.showMessageDialog(mainPane, "Exportat");
					eField.setText("write path");
				} else
					JOptionPane.showMessageDialog(mainPane, "Introduceti calea");

			}
		});
		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!iField.getText().equals("write path")) {
					Utilities.reset();
					Utilities.importFile(iField.getText() + ".txt");
					if (Main.current == 0) {
						currentLabel.setText("Normal");
						mainPane.getRightComponent().setBounds(0, 0, width2, height);
						mainPane.setRightComponent(new PaintPane());
					} else {
						currentLabel.setText("Direct");
						mainPane.getRightComponent().setBounds(0, 0, width2, height);
						mainPane.setRightComponent(new PaintPane());
					}
					JOptionPane.showMessageDialog(mainPane, "Importat");
					iField.setText("write path");
				} else
					JOptionPane.showMessageDialog(mainPane, "Introduceti calea");
			}
		});

		showCurrentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPane.setRightComponent(new PaintPane());
			}
		});

		mTreeAlg1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.current == 0 && Main.enableCost)
					mainPane.setRightComponent(new Tree(GenericMT.algorithm()));
			}
		});
		
		mTreeAlg2Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.current == 0 && Main.enableCost)
					mainPane.setRightComponent(new Tree(KruskalMT.run()));
					
			}
		});
		
		mTreeAlg3Btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Main.current == 0 && Main.enableCost)
					mainPane.setRightComponent(new Tree(BoruvkaMT.run()));
			}
		});

	}

	public JSplitPane getPane() {
		return mainPane;
	}

}
