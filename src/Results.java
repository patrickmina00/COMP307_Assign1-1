
public class Results {
private double distance;
private String trainingType;
private String testType;


	public Results(double distance, String trainingType, String testType){
		this.distance =  distance;
		this.trainingType = trainingType;
		this.testType = testType;
	}
	
	
	public double getDistance(){
		return this.distance;
	}
	
	public String getTrainingType(){
		return this.trainingType;
	}
	
	public String getTestType(){
		return this.testType;
	}
	
	public String toString(){
		return "TestType: "+testType + "\nTrainingType: "+ trainingType+ "\nDistance: "+ distance;
		 
	}
}
