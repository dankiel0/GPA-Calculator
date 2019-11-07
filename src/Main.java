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
	
	private List<JPanel> courses = new ArrayList<JPanel>();
	
	private Main() {
		setTitle("GPA Calculator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JPanel mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainPanelLayout);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JPanel descriptionsPanel = new JPanel(); 
		descriptionsPanel.add(new JLabel("                                    Course Name       Grade    Credits                Type"));
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JPanel rowsPanel = new JPanel();
		BoxLayout rowsPanelLayout = new BoxLayout(rowsPanel, BoxLayout.Y_AXIS);
		rowsPanel.setLayout(rowsPanelLayout);
		
		for(int i = 0; i < 4; i++) {
			JPanel defaultPanel = courseRow();
			
			rowsPanel.add(defaultPanel);
			courses.add(defaultPanel);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JPanel buttonsPanel = new JPanel();
		BoxLayout buttonsPanelLayout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
		buttonsPanel.setLayout(buttonsPanelLayout);
		
		buttonsPanel.add(Box.createHorizontalStrut(5));
		
		JButton getGPA = new JButton("Get GPA");
		getGPA.addActionListener((ActionEvent event) -> {
			
			Course[] courses = new Course[this.courses.size()];
			
			for(int i = 0; i < this.courses.size(); i++) {
				
				JPanel courseRow = this.courses.get(i);
				
				String name = ((JTextField) courseRow.getComponent(0)).getText();
				Course.Grade grade = Course.Grade.toEnum((String)((JComboBox<?>) courseRow.getComponent(1)).getSelectedItem());
				float credit = Float.parseFloat((String)((JComboBox<?>) courseRow.getComponent(2)).getSelectedItem());
				Course.Type type = Course.Type.valueOf((String) ((JComboBox<?>) courseRow.getComponent(3)).getSelectedItem());
				
				if(grade == null) {
					JOptionPane.showMessageDialog(this, "please fill in all fields", "that's illegal", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				courses[i] = new Course(name, grade, credit, type);
			}
			
			
			gpa.calculateTotalGPA(courses);
			
			
			JOptionPane.showMessageDialog(this, "unweighted GPA: " + gpa.getUnweightedGPA() + "\nweighted GPA: " + gpa.getWeightedGPA(), " your GPA's", JOptionPane.INFORMATION_MESSAGE);
			
		});
		
		buttonsPanel.add(getGPA);
		buttonsPanel.add(Box.createHorizontalStrut(5));
		
		JButton addCourse = new JButton("Add Course");
		addCourse.addActionListener((ActionEvent event) -> {
			
			// the maximum number of courses is 24; this checks if it exceeds the bounds.
			if(courses.size() == 24) {
				JOptionPane.showMessageDialog(this, "max number of courses met", "that's illegal", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			 JPanel courseRow = courseRow();
			
			courses.add(courseRow);
			rowsPanel.add(courseRow);
			
			pack();
		});
		
		buttonsPanel.add(addCourse);
		buttonsPanel.add(Box.createHorizontalGlue());
		
		JButton openFile = new JButton("Open File");
		openFile.addActionListener((ActionEvent event) -> {});
		
		buttonsPanel.add(openFile);
		buttonsPanel.add(Box.createHorizontalStrut(5));
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		mainPanel.add(Box.createVerticalStrut(5));
		mainPanel.add(buttonsPanel);
		mainPanel.add(descriptionsPanel);
		mainPanel.add(rowsPanel);
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		add(mainPanel);
		
		setResizable(false);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private final JPanel courseRow() {
		JPanel courseRow = new JPanel();
		
		courseRow.add(courseName());
		courseRow.add(courseGrade());
		courseRow.add(courseCredit());
		courseRow.add(courseType());
		courseRow.add(removeCourse());
		
		return courseRow;
	}
	
	private final JTextField courseName() {
		JTextField courseName = new JTextField();
		courseName.setPreferredSize(new Dimension(200, 20));
		
		return courseName;
	}
	
	private final JComboBox<String> courseGrade() {
		JComboBox<String> courseGrade = new JComboBox<String>(new String[] {"", "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"});
		courseGrade.setPreferredSize(new Dimension(41, 20));
		
		return courseGrade;
	}
	
	private final JComboBox<String> courseCredit() {
		JComboBox<String> courseCredit = new JComboBox<String>(new String[] {"5.00", "3.75", "2.50", "1.25"});
		courseCredit.setPreferredSize(new Dimension(47, 20));
		
		courseCredit.setSelectedIndex(2);
		
		return courseCredit;
	}
	
	private final JComboBox<String> courseType() {
		JComboBox<String> courseType = new JComboBox<String>(new String[] {"AP", "Honors", "Regular"});
		courseType.setPreferredSize(new Dimension(64, 20));
		courseType.setSelectedIndex(2);
		
		return courseType;
	}
	
	private final JButton removeCourse() {
		JButton removeCourseRow = new JButton("✖");
		removeCourseRow.setPreferredSize(new Dimension(20, 20));
		
		removeCourseRow.setMargin(new Insets(0, 0, 0, 0));
		
		removeCourseRow.addActionListener((ActionEvent event) -> {
			for(int i = 0; i < courses.size(); i++)
				if(event.getSource() == courses.get(i).getComponent(4) && courses.size() > 1) {
					courses.remove(i);
					((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(3)).remove(i);
					pack();
				}
		});
		
		removeCourseRow.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event) {
				removeCourseRow.setForeground(Color.RED);
			}
			
			public void mouseExited(MouseEvent event) {
				removeCourseRow.setForeground(Color.BLACK);
			}
		});
		
		return removeCourseRow;
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		new Main();
		
		System.out.println((System.currentTimeMillis() - start) / 1000.0 + " seconds.");
	}
}
