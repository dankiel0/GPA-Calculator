
public class Course {
	private String 	name;
	private Grade 	grade;
	private float 	unweightedGPA, weightedGPA;
	private float 	credit;
	private Type 	type;
	
	public String 	getName()			{return name;}
	public Grade 	getGrade()			{return grade;}
	public float 	getUnweightedGPA() 		{return unweightedGPA;}
	public float 	getWeightedGPA()		{return weightedGPA;}
	public float 	getCredit()			{return credit / 5;}
	public Type 	getType()			{return type;}
	
	public Course(String name, Grade grade, float credits, Type type) {
		this.name = name;
		this.grade = grade;
		
		unweightedGPA = grade.toGPA(grade, type).getUnweightedGPA();
		weightedGPA = grade.toGPA(grade, type).getWeightedGPA();
		
		this.credit = credits;
		this.type = type;
	}
	
	// grade component of course.
	public static enum Grade {
		APLUS, A, AMINUS, BPLUS, B, BMINUS, CPLUS, C, CMINUS, DPLUS, D, F;
		
		public static Grade toEnum(String stringGrade) {
			Course.Grade enumGrade;
			
			switch(stringGrade) {
			case "A+": 	enumGrade = Grade.APLUS; 	break;
			case "A": 	enumGrade = Grade.A; 		break;
			case "A-": 	enumGrade = Grade.AMINUS; 	break;
			case "B+": 	enumGrade = Grade.BPLUS; 	break;
			case "B": 	enumGrade = Grade.B; 		break;
			case "B-": 	enumGrade = Grade.BMINUS; 	break;
			case "C+": 	enumGrade = Grade.CPLUS; 	break;
			case "C": 	enumGrade = Grade.C; 		break;
			case "C-": 	enumGrade = Grade.CMINUS; 	break;
			case "D+": 	enumGrade = Grade.DPLUS; 	break;
			case "D": 	enumGrade = Grade.D; 		break;
			case "F":	enumGrade = Grade.F;		break;
			default: 	enumGrade = null;		break;
			}
			
			return enumGrade;
		}
		
		public GPA toGPA(Grade grade, Type type) {
			float unweightedGPA = 0; float weightedGPA = 0;
			
			// conversion scale for unweighted GPA.
			switch(grade) {
			case APLUS: case A: 	unweightedGPA = 4;	break;
			case AMINUS: 		unweightedGPA = 3.7f;	break;
			case BPLUS: 		unweightedGPA = 3.3f;	break;
			case B: 		unweightedGPA =	3;	break;
			case BMINUS: 		unweightedGPA = 2.7f;	break;
			case CPLUS: 		unweightedGPA = 2.3f;	break;
			case C: 		unweightedGPA = 2;	break;
			case CMINUS: 		unweightedGPA = 1.7f;	break;
			case DPLUS: 		unweightedGPA = 1.3f;	break;
			case D: 		unweightedGPA = 1;	break;
			default: 		unweightedGPA = 0;	break;
			}
			
			// conversion scales for weighted GPA.
			if(type.equals(Type.Regular))
				switch(grade) {
				case APLUS: 	weightedGPA = 4.33f;	break;
				case A: 	weightedGPA = 4;	break;
				case AMINUS: 	weightedGPA = 3.66f;	break;
				case BPLUS: 	weightedGPA = 3.33f;	break;
				case B: 	weightedGPA = 3;	break;
				case BMINUS: 	weightedGPA = 2.66f;	break;
				case CPLUS: 	weightedGPA = 2.33f;	break;
				case C: 	weightedGPA = 2;	break;
				case CMINUS: 	weightedGPA = 1.66f;	break;
				case DPLUS: 	weightedGPA = 1.33f;	break;
				case D: 	weightedGPA = 1;	break;
				default: 	weightedGPA = 0;	break;
				}
			
			if(type.equals(Type.Honors))
				switch(grade) {
				case APLUS: 	weightedGPA = 5.08f;	break;
				case A: 	weightedGPA = 4.75f;	break;
				case AMINUS: 	weightedGPA = 4.41f;	break;
				case BPLUS: 	weightedGPA = 4.08f;	break;
				case B: 	weightedGPA = 3.75f;	break;
				case BMINUS: 	weightedGPA = 3.41f;	break;
				case CPLUS: 	weightedGPA = 3.08f;	break;
				case C: 	weightedGPA = 2.75f;	break;
				case CMINUS: 	weightedGPA = 2.41f;	break;
				case DPLUS: 	weightedGPA = 2.08f;	break;
				case D: 	weightedGPA = 1.75f;	break;
				default: 	weightedGPA = 0;	break;
				}
			
			if(type.equals(Type.AP))
				switch(grade) {
				case APLUS: 	weightedGPA = 5.83f;	break;
				case A: 	weightedGPA = 5.5f;	break;
				case AMINUS: 	weightedGPA = 5.16f;	break;
				case BPLUS: 	weightedGPA = 4.83f;	break;
				case B: 	weightedGPA = 4.5f;	break;
				case BMINUS: 	weightedGPA = 4.16f;	break;
				case CPLUS: 	weightedGPA = 3.83f;	break;
				case C: 	weightedGPA = 3.5f;	break;
				case CMINUS: 	weightedGPA = 3.16f;	break;
				case DPLUS: 	weightedGPA = 2.83f;	break;
				case D: 	weightedGPA = 2.5f;	break;
				default: 	weightedGPA = 0;	break;
				}
			
			return new Course.GPA(unweightedGPA, weightedGPA);
		}
	}
	
	// GPA component of course.
	public static class GPA {
		private float unweightedGPA, weightedGPA;
		
		public GPA(float unweightedGPA, float weightedGPA) {
			this.unweightedGPA = unweightedGPA;
			this.weightedGPA = weightedGPA;
		}
		
		public float getUnweightedGPA()	{return unweightedGPA;}
		public float getWeightedGPA()	{return weightedGPA;}
	}
	
	// type component of course.
	public static enum Type {AP, Honors, Regular}
}
