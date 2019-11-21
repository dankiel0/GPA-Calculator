import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GPA gpa = new GPA();
	
	private List<JPanel> courseRows = new ArrayList<JPanel>();
	
	private Main() {
		setTitle("GPA Calculator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//////////////////////////////creation of the main panel////////////////////////////////////
		JPanel mainPanel = new JPanel();
		
		// components will be added to the mainPanel from top to bottom.
		BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainPanelLayout);
		
		//////////////////////////////creation of the descriptions panel////////////////////////////
		JPanel descriptionsPanel = new JPanel();
		
		// i am too lazy to write fancy code and line everything up perfectly...
		// but it's still pretty accurate.
		descriptionsPanel.add(new JLabel("                                    Course Name       Grade    Credits                Type"));
		
		//////////////////////////////creation of the rows panel////////////////////////////////////
		JPanel rowsPanel = new JPanel();
		
		// courseRows will be added to the rowsPanel from top to bottom.
		BoxLayout rowsPanelLayout = new BoxLayout(rowsPanel, BoxLayout.Y_AXIS);
		rowsPanel.setLayout(rowsPanelLayout);
		
		// adds first four courses to the rowsPanel and to the courseRows list.
		for(int i = 0; i < 4; i++) {
			JPanel defaultPanel = courseRow();
			
			rowsPanel.add(defaultPanel);
			courseRows.add(defaultPanel);
		}
		
		//////////////////////////////creation of the buttons panel/////////////////////////////////
		JPanel buttonsPanel = new JPanel();
		
		// buttons will be added to the buttonsPanel from left to right.
		BoxLayout buttonsPanelLayout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
		buttonsPanel.setLayout(buttonsPanelLayout);
		
		// getGPA button creation.
		JButton getGPA = new JButton("Get GPA");
		getGPA.addActionListener((ActionEvent event) -> {
			
			Course[] courses = new Course[this.courseRows.size()];
			
			for(int i = 0; i < this.courseRows.size(); i++) {
				
				JPanel courseRow = this.courseRows.get(i);
				
				String name = ((JTextField) courseRow.getComponent(0)).getText();
				Course.Grade grade = Course.Grade.toEnum((String)((JComboBox<?>) courseRow.getComponent(1)).getSelectedItem());
				float credit = Float.parseFloat((String)((JComboBox<?>) courseRow.getComponent(2)).getSelectedItem());
				Course.Type type = Course.Type.valueOf((String) ((JComboBox<?>) courseRow.getComponent(3)).getSelectedItem());
				
				if(grade == null) {
					JOptionPane.showMessageDialog(this, "please fill in all fields", "that's illegal", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Course course = new Course(name, grade, credit, type);
				courses[i] = course;
				
			}
			
			gpa.calculateTotalGPA(courses);
			
			
			JOptionPane.showMessageDialog(this, String.format("unweighted GPA: %.4f \nweighted GPA: %.4f", gpa.getUnweightedGPA(), gpa.getWeightedGPA()), " your GPA's", JOptionPane.INFORMATION_MESSAGE);
			
		});
		
		// addCourse button creation.
		JButton addCourse = new JButton("Add Course");
		addCourse.addActionListener((ActionEvent event) -> {
			
			// the maximum number of courses is 24; this checks if it exceeds the bounds.
			if(courseRows.size() == 24) {
				JOptionPane.showMessageDialog(this, "max number of courses met", "that's illegal", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JPanel courseRow = courseRow();
			
			courseRows.add(courseRow);
			rowsPanel.add(courseRow);
			
			pack();
		});
		
		// openFile button creation.
		JButton openFile = new JButton("Open File");
		openFile.addActionListener((ActionEvent event) -> {});
		
		//////////////////////////////adds components to the buttons panel///////////////////////////
		// creates spacing between the left wall of the frame and the getGPA button.
		buttonsPanel.add(Box.createHorizontalStrut(5));
		buttonsPanel.add(getGPA);
		
		// creates spacing between the getGPA button and the addCourse button.
		buttonsPanel.add(Box.createHorizontalStrut(5));
		buttonsPanel.add(addCourse);

		// pushs the addCourse button and the openFile button to opposite sides.
		buttonsPanel.add(Box.createHorizontalGlue());
		buttonsPanel.add(openFile);
		
		// creates spacing between the openFile button and the right wall of the frame.
		buttonsPanel.add(Box.createHorizontalStrut(5));
		
		//////////////////////////////adds components to the main panel//////////////////////////////
		// creates spacing between top of frame and buttonsPanel.
		mainPanel.add(Box.createVerticalStrut(5));
		
		mainPanel.add(buttonsPanel);
		mainPanel.add(descriptionsPanel);
		mainPanel.add(rowsPanel);
		
		// adds the mainPanel to the contentPane.
		add(mainPanel);
		
		setResizable(false);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// the default courseRow.
	private final JPanel courseRow() {
		JPanel courseRow = new JPanel();

		// components of courseRow.
		courseRow.add(courseName());
		courseRow.add(courseGrade());
		courseRow.add(courseCredit());
		courseRow.add(courseType());
		courseRow.add(removeCourse());

		return courseRow;
	}

	// the text box where the user inputs the course name.
	private final JTextField courseName() {
		JTextField courseName = new JTextField();
		courseName.setPreferredSize(new Dimension(200, 20));

		return courseName;
	}

	// the drop down list for selecting the course grade.
	private final JComboBox<String> courseGrade() {
		JComboBox<String> courseGrade = new JComboBox<String>(new String[] {"", "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"});
		courseGrade.setPreferredSize(new Dimension(41, 20));

		return courseGrade;
	}

	// the drop down list for selecting the course credit.
	private final JComboBox<String> courseCredit() {
		JComboBox<String> courseCredit = new JComboBox<String>(new String[] {"5.00", "3.75", "2.50", "1.25"});
		courseCredit.setPreferredSize(new Dimension(47, 20));

		// default selection is "2.50."
		courseCredit.setSelectedIndex(2);

		return courseCredit;
	}

	// the drop down list for selecting the course type.
	private final JComboBox<String> courseType() {
		JComboBox<String> courseType = new JComboBox<String>(new String[] {"AP", "Honors", "Regular"});
		courseType.setPreferredSize(new Dimension(64, 20));

		// default selection is "Regular."
		courseType.setSelectedIndex(2);

		return courseType;
	}

	// the button that removes its corresponding course.
	private final JButton removeCourse() {
		JButton removeCourseRow = new JButton("╳");
		removeCourseRow.setPreferredSize(new Dimension(20, 20));

		// makes the button "invisible".
		removeCourseRow.setMargin(new Insets(0, 0, 0, 0));
		removeCourseRow.setBorderPainted(false);
		removeCourseRow.setContentAreaFilled(false);
		removeCourseRow.setFocusable(false);

		// deletes the courseRow the button is from.
		removeCourseRow.addActionListener((ActionEvent event) -> {

			// loops through every courseRow in the list.
			// cannot be an enhanced for loop because the program will experience a "java.util.ConcurrentModificationException."
			for(int i = 0; i < courseRows.size(); i++)

				// if the event source came from the courseRow. also you cannot delete with only one courseRow left.
				if(event.getSource() == courseRows.get(i).getComponent(4) && courseRows.size() > 1) {

					// removes the courseRow from the list.
					courseRows.remove(i);
					
					// removes the courseRow from the contentPane.
					((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(3)).remove(i);

					// resizes the frame to remove the previous courseRow space.
					pack();
				}
		});

		// determines the colors the button will be.
		removeCourseRow.addMouseListener(new MouseAdapter() {

			// if the mouse hovers over the button.
			public void mouseEntered(MouseEvent event) {
				removeCourseRow.setForeground(Color.RED);
			}

			// if the mouse does not hover over the button.
			public void mouseExited(MouseEvent event) {
				removeCourseRow.setForeground(Color.BLACK);
			}
		});

		return removeCourseRow;
	}

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		// sets the style of the gui.
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}

		new Main();

		System.out.println((System.currentTimeMillis() - start) / 1000.0 + " seconds.");

	}
}
