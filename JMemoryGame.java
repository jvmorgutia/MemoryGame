//
// Name: Morgutia, Jesse
// Homework: EXTRA CREDIT 1
// Due: MARCH 20, 2015
// Course: cs-245-01-w15
//
// Description:
// I am to implement a memory game program implemented with a 4 x 4 grid of 
// images by default that are only displayed if the user clicks on them.
// this game also must implement a "cheat" where all images are displayed
// at will, however the game will end, a timer, and score count of every 
// instance that occurs at any given time.
//
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class JMemoryGame {

	ArrayList<HiddenImage> imageID;
	HiddenImage tempClicked;
	protected int clickCount;
	protected boolean active;
	protected int seconds;
	protected Timer total_timer;

	public int attempts;
	public int found;
	public JFrame frame;
	private JButton start_button;
	private JLabel attempt_label;
	private JLabel seconds_label;
	private JLabel found_label;

	public JMemoryGame() {
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Options");
		JMenuItem show = new JMenuItem("Quit and show all tiles");
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				active = false;
				for (int i = 0; i < imageID.size(); i++) {
					imageID.get(i).button.setIcon(imageID.get(i).image);
				}
			}
		});
		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				resetGame();
			}
		});

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(frame,
						"(C) Jesse Morgutia, JMemoryGame \nVersion 0.1");
			}
		});
		options.add(show);
		options.add(reset);
		options.add(exit);
		options.add(about);
		bar.add(options);
		start_button = new JButton("Start");
		start_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				for (int i = 0; i < imageID.size(); i++) {
					imageID.get(i).button.setIcon(new ImageIcon("default.png"));
				}
				active = true;
				Collections.shuffle(imageID);
				total_timer.start();
			}

		});

		resetGame();

		frame.setJMenuBar(bar);
		frame.setVisible(true);

	}

	public void resetGame() {
		imageID = new ArrayList<HiddenImage>();
		imageID.add(new HiddenImage(1));
		imageID.add(new HiddenImage(1));
		imageID.add(new HiddenImage(2));
		imageID.add(new HiddenImage(2));
		imageID.add(new HiddenImage(3));
		imageID.add(new HiddenImage(3));
		imageID.add(new HiddenImage(4));
		imageID.add(new HiddenImage(4));
		imageID.add(new HiddenImage(5));
		imageID.add(new HiddenImage(5));
		imageID.add(new HiddenImage(6));
		imageID.add(new HiddenImage(6));
		imageID.add(new HiddenImage(7));
		imageID.add(new HiddenImage(7));
		imageID.add(new HiddenImage(8));
		imageID.add(new HiddenImage(8));

		Collections.shuffle(imageID);
		attempt_label = new JLabel("Attempts: 0");
		seconds_label = new JLabel("Time: 0s");
		found_label = new JLabel("Found: 0");
		attempts = 0;
		found = 0;
		active = false;
		tempClicked = new HiddenImage(-1);
		clickCount = 0;
		total_timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				seconds++;
				seconds_label.setText("Time: " + seconds + "s");
			}
		});

		frame = new JFrame("JMemoryGame");
		frame.setLayout(new GridLayout(4, 5));
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(imageID.get(0).button);
		frame.add(imageID.get(1).button);
		frame.add(imageID.get(2).button);
		frame.add(imageID.get(3).button);
		frame.add(start_button);
		frame.add(imageID.get(4).button);
		frame.add(imageID.get(5).button);
		frame.add(imageID.get(6).button);
		frame.add(imageID.get(7).button);
		frame.add(attempt_label);
		frame.add(imageID.get(8).button);
		frame.add(imageID.get(9).button);
		frame.add(imageID.get(10).button);
		frame.add(imageID.get(11).button);
		frame.add(found_label);
		frame.add(imageID.get(12).button);
		frame.add(imageID.get(13).button);
		frame.add(imageID.get(14).button);
		frame.add(imageID.get(15).button);
		frame.add(seconds_label);

	}

	public static void main(String[] args) {

		new JMemoryGame();

	}

	public class HiddenImage {
		public ImageIcon image;
		public int imageID;
		public boolean isHidden;
		public JButton button;

		private Timer timer;

		public HiddenImage(int id) {
			clickCount = 0;
			this.imageID = id;
			isHidden = true;
			image = new ImageIcon(imageID + ".png");
			timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					button.setIcon(new ImageIcon("default.png"));
					tempClicked.button.setIcon(new ImageIcon("default.png"));
					active = true;
				}
			});
			timer.setRepeats(false);

			button = new JButton(new ImageIcon("default.png"));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if (active) {
						if (isHidden && clickCount == 0) {
							button.setIcon(image);
							tempClicked.button = button;
							tempClicked.imageID = imageID;
							clickCount++;

						} else if (isHidden && clickCount == 1) {
							attempts++;
							attempt_label.setText("Attempts: " + attempts);
							button.setIcon(image);
							clickCount = 0;
							System.out.println(imageID + "   "
									+ tempClicked.imageID);
							if (tempClicked.button == button) {
								System.out.println("Same button clicked");
								button.setIcon(new ImageIcon("default.png"));

							} else if (tempClicked.imageID == imageID) {
								found++;
								found_label.setText("Found: " + found);
								if (found == 8) {
									total_timer.stop();
									JOptionPane.showMessageDialog(frame,
											"Great Job! Completed this game in "
													+ attempts
													+ " attempts and "
													+ seconds + " seconds!");
								}
								tempClicked.isHidden = false;
								isHidden = false;
								tempClicked.button.setEnabled(false);
								button.setEnabled(false);

							} else {
								timer.start();
								active = false;
							}
						}
					}
				}
			});
		}
	}
}