
public class GPA {
	private float unweightedGPA, weightedGPA;
	
	public void calculateTotalGPA(Course[] courses) {
		// formula for gpa calculation is: sum of the product of courses' GPA and courses' credit divided by sum of courses' credit.
		
		
		// reset the GPAs.
		unweightedGPA = 0; weightedGPA = 0;
		
		// first step: sum of the products of the courses' GPA and courses' credit.
		for(Course course : courses) {
			unweightedGPA += (course.getUnweightedGPA() * course.getCredit());
			weightedGPA += (course.getWeightedGPA() * course.getCredit());
		}
		
		// calculate courses' credit.
		float credits = 0;
		for(Course course : courses) credits += course.getCredit();
		
		// last step: divide by total credit.
		unweightedGPA /= credits; weightedGPA /= credits;
	}
	
	public float getUnweightedGPA()	{return unweightedGPA;}
	public float getWeightedGPA() {return weightedGPA;}
}
