
public class Attribute {
	private double SLength;
	private double SWidth;
	private double PLength;
	private double PWidth;
	private String type;

	/**Holds the data attributes of the iris plant.
	 * @param SLength
	 * @param SWidth
	 * @param PLength
	 * @param PWidth
	 * @param type
	 */
	public Attribute(double SLength, double SWidth, double PLength, double PWidth, String type){
		this.SLength = SLength;
		this.SWidth = SWidth;
		this.PLength = PLength;
		this.PWidth = PWidth;
		this.type = type;
	}

	public double getSepalLenght(){
		return this.SLength;
	}

	public double getSepalWidth(){
		return this.SWidth;
	}

	public double getPetalLength(){
		return this.PLength;
	}

	public double getPetalWidth(){
		return this.PWidth;
	}

	public String getType(){
		return this.type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String toString(){
		return SLength+" "+SWidth+" "+PLength+" "+PWidth+" "+type;
	}
}
