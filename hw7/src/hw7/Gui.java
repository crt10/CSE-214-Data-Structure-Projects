package hw7;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * Extra Credit
 * The Gui class should construct and initialize a JTable, JScrollPane, and JFrame
 * to display the data of WebGraph. The columns of the gui should the clickable to sort
 * each row, based off of the column clicked, in ascending or descending order.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class Gui {
	
	public final static String[] COLUMN_NAMES = {"Index", "URL", "PageRank", "Links", "Keywords"};
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JTable table;
	JScrollPane scrollPane;
	JPanel buttonPane;
	JFrame frame;
	WebGraph web;
	JOptionPane input;
	JButton ap;
	JButton rp;
	JButton al;
	JButton rl;
	JButton s;
	JButton q;
	
	/**
	 * Constructs a Gui object with the supplied parameter.
	 * 
	 * @param web
	 *     The WebGraph to create the Gui off of.
	 */
	public Gui(WebGraph web) {
		this.web = web;
		frame = new JFrame();
		buttonPane = new JPanel();
		ap = new JButton("(AP) - Add a new page to the graph.");
		rp = new JButton("(RP) - Remove a page from the graph.");
		al = new JButton("(AL) - Add a link betweenpages in the graph.");
		rl = new JButton("(RL) - Remove a link between pages in the graph.");
		s = new JButton("(S)  - Search for pages with a keyword.");
		q = new JButton("(Q) - Quit.");
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
		ap.setAlignmentX(Component.CENTER_ALIGNMENT);
		rp.setAlignmentX(Component.CENTER_ALIGNMENT);
		al.setAlignmentX(Component.CENTER_ALIGNMENT);
		rl.setAlignmentX(Component.CENTER_ALIGNMENT);
		s.setAlignmentX(Component.CENTER_ALIGNMENT);
		q.setAlignmentX(Component.CENTER_ALIGNMENT);
		ap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == ap) {
					try {
						String url1 = Gui.question("Enter a URL");
						String keyword = Gui.question("Enter keywords (space-seperated)");
						LinkedList<String> keywords = new LinkedList<String>(Arrays.asList(keyword.split(" ")));
						web.addPage(url1, keywords);
						JOptionPane.showMessageDialog(null, url1 + " successfully added to the WebGraph!");
						update();
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (Exception ex) {
					}
				}
			}
		});
		rp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == rp) {
					try {
						String url1 = Gui.question("Enter a URL");
						web.removePage(url1);
						JOptionPane.showMessageDialog(null, url1 + " has been removed from the graph!");
						update();
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (Exception ex) {
					}
				}
			}
		});
		al.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == al) {
					try {
						String url1 = Gui.question("Enter a source URL");
						String url2 = Gui.question("Enter a Destination URL");
						web.addLink(url1, url2);
						JOptionPane.showMessageDialog(null, "Link successfully added from " + url1 + " to " + url2 + "!");
						update();
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (Exception ex) {
					}
				}
			}
		});
		rl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == rl) {
					try {
						String url1 = Gui.question("Enter a source URL");
						String url2 = Gui.question("Enter a Destination URL");
						web.removeLink(url1, url2);
						JOptionPane.showMessageDialog(null, "Link removed from " + url1 + " to " + url2 + "!");
						update();
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (Exception ex) {
					}
				}
			}
		});
		s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == s) {
					try {
						String keyword = Gui.question("Search keyword");
						JOptionPane.showMessageDialog(null, web.searchKeyword(keyword));
						update();
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} catch (Exception ex) {
					}
				}
			}
		});
		q.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == q) {
					JOptionPane.showMessageDialog(null, "Goodbye.");
					System.exit(0);
				}
			}
		});
		buttonPane.add(ap);
		buttonPane.add(rp);
		buttonPane.add(al);
		buttonPane.add(rl);
		buttonPane.add(s);
		buttonPane.add(q);
		frame.add(buttonPane, BorderLayout.EAST);
		update();
		frame.setResizable(false);
	}
	
	/**
	 * Updates the Gui with new data when the JTable is changed.
	 */
	public void update() {
		refreshTable();
		if (scrollPane != null) {
			frame.remove(scrollPane);
		}
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension((int)(screenSize.getWidth()*.6666), table.getRowHeight()*table.getRowCount()+26));
		frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);
		frame.revalidate();
	}
	
	/**
	 * Updates the JTable with new data supplied by web.
	 */
	public void refreshTable() {
		table = new JTable(web.toTable(), COLUMN_NAMES);
		table.setFont(new Font("Verdana", Font.PLAIN, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(25);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(400);
		table.setRowHeight(40);
		table.setAutoCreateRowSorter(true);
		table.setEnabled(false);
	}
	
	/**
	 * Prompts and returns the user's input based on the specified question.
	 * 
	 * @param x
	 *     The question prompted.
	 *     
	 * @return
	 *     The user's answer to x.
	 */
	public static String question(String x) {
		return JOptionPane.showInputDialog(x);
	}
	
}
