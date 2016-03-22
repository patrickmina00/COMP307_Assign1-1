/*
 * Jean Patrick Mina
 * 300196807
 */

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class IrisMain{
	private static ArrayList<Attribute> testList = new ArrayList<Attribute>();
	private static ArrayList<Attribute> dataList = new ArrayList<Attribute>();
	private static ArrayList<Attribute> trainingList = new ArrayList<Attribute>();
	private int k;

	private double rangeSL;
	private double rangeSW;
	private double rangePL;
	private double rangePW;


	/**
	 * Populates the arraylists containing the data obtained from the files.
	 * Makes use of Attribute class to hold the data.
	 * @param trainingFile
	 * @param testFile
	 */	
	public void populateData( String trainingFile, String testFile){
		try {
			BufferedReader brtrain = new BufferedReader(new FileReader(new File(trainingFile)));
			BufferedReader brtest = new BufferedReader(new FileReader(new File(testFile)));
			BufferedReader brdata = new BufferedReader(new FileReader(new File("iris.data"))); 

			try {
				//populate training arraylist
				String x = brtrain.readLine();
				while(!x.isEmpty()){
					Scanner sctrain = new Scanner(x);
					double SLength = sctrain.nextDouble();
					double SWidth = sctrain.nextDouble();
					double PLength = sctrain.nextDouble();
					double PWidth = sctrain.nextDouble();
					String type = sctrain.next();
					trainingList.add(new Attribute(SLength, SWidth, PLength, PWidth, type));
					x = brtrain.readLine();
				}
				brtrain.close();

				//populate test arraylist
				String y = brtest.readLine();
				while(!y.isEmpty()){
					Scanner sctest = new Scanner(y);
					double SLength = sctest.nextDouble();
					double SWidth = sctest.nextDouble();
					double PLength = sctest.nextDouble();
					double PWidth = sctest.nextDouble();
					String type = sctest.next();
					testList.add(new Attribute(SLength, SWidth, PLength, PWidth, type));
					y = brtest.readLine();
				}
				brtest.close();

				//populate data arraylist
				String z = brdata.readLine();
				while(!z.isEmpty()){
					Scanner scdata = new Scanner(z).useDelimiter(",");
					double SLength = scdata.nextDouble();
					double SWidth = scdata.nextDouble();
					double PLength = scdata.nextDouble();
					double PWidth = scdata.nextDouble();
					String type = scdata.next();
					dataList.add(new Attribute(SLength, SWidth, PLength, PWidth, type));
					z = brdata.readLine();
				}
				brdata.close();

			}
			catch (IOException e) {
				System.out.println("File was empty: "+e);
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found: "+e);
		}
	}


	/**
	 * Set k value for euclidian function.
	 * @param i
	 */
	public void setK(int i){
		this.k = i;
	}

	/**Get the minimum and maximum values for each attribute associated with the data.*/
	public void getMinMax(){
		double sLMax = Double.MIN_VALUE;
		double sLMin = Double.MAX_VALUE;
		double pLMax = Double.MIN_VALUE;
		double pLMin = Double.MAX_VALUE;

		double sWMax = Double.MIN_VALUE;
		double sWMin = Double.MAX_VALUE;
		double pWMax = Double.MIN_VALUE;
		double pWMin = Double.MAX_VALUE;

		for(Attribute a:trainingList){
			//Lengths
			if(sLMax <= a.getSepalLenght()){
				sLMax = a.getSepalLenght();
			}
			if(sLMin >= a.getSepalLenght()){
				sLMin = a.getSepalLenght();
			}
			if(pLMax <= a.getPetalLength()){
				pLMax = a.getPetalLength();
			}
			if(pLMin >= a.getPetalLength()){
				pLMin =  a.getPetalLength();
			}


			//Widths
			if(sWMax <= a.getSepalWidth()){
				sWMax = a.getSepalWidth();
			}
			if(sWMin >= a.getSepalWidth()){
				sWMin = a.getSepalWidth();
			}
			if(pWMax <= a.getPetalWidth()){
				pWMax = a.getPetalWidth();
			}
			if(pWMin >= a.getPetalWidth()){
				pWMin = a.getPetalWidth();
			}
		}

		this.rangeSL = Math.pow((sLMax - sLMin),2);
		this.rangeSW = Math.pow((sWMax - sWMin),2);
		this.rangePL = Math.pow((pLMax - pLMin),2);
		this.rangePW = Math.pow((pWMax - pWMin),2);



		//		System.out.println("range petal L: "+this.rangePL);
		//		System.out.println("range petal W: "+this.rangePW);
		//		System.out.println("range sepal L: "+this.rangeSL);
		//		System.out.println("range sepal W: "+this.rangeSW);
		//		
	}


	//Apply euclidian distance formula? use Weighted Euclidian Formula
	/**
	 * Calculate the Euclidian distance between two nodes given their share of attributes.
	 * Returns the result of the Euclidian function.
	 * @param testAttribute
	 * @param trainingAttribute
	 */
	public double calculateKDist(Attribute attr1, Attribute attr2){
		double petL = (attr1.getPetalLength() - attr2.getPetalLength())/rangePL;
		double petW = (attr1.getPetalWidth() - attr2.getPetalWidth())/rangePW;
		double sepL = (attr1.getSepalLenght() - attr2.getSepalLenght())/rangeSL;
		double sepW = (attr1.getSepalWidth() - attr2.getSepalWidth())/rangeSW;

		return Math.sqrt((Math.pow(petL,2) + Math.pow(petW,2) + Math.pow(sepL,2) + Math.pow(sepW,2)));
	}



	/**
	 * Just need name and grade?
	 */
	public void classify(){
		double hits = 0;
		ArrayList<Results> finalResults = null;
		this.getMinMax();

		//testing one input to check data
		//		Attribute a = testList.get(0);

		//		System.out.println("testList size: "+testList.size());
		//		System.out.println("trainingList size: "+trainingList.size());


		/*populate the results list*/
		for(Attribute a: testList){ 
			finalResults =  new ArrayList<Results>();
			//			a = testList.get(testList.size()-1)
			for(Attribute b: trainingList){
				Results r =  new Results(calculateKDist(a, b),b.getType(),a.getType());
				finalResults.add(r);
			}

			/*sort finalResults here smallest to biggest
			the smaller the distance, the closer it is to another point!*/
			Collections.sort(finalResults,new Comparator<Results>(){
				@Override
				public int compare(Results a, Results b) {
					if(a.getDistance() > b.getDistance()){
						return 1;
					}
					else if(a.getDistance() < b.getDistance()){
						return -1;
					}
					return 0;
				}		
			});

			//If k = 1 then total accuracy is the number of correct guesses made as a whole.
			if(k==1){
				Results r = finalResults.get(0);
				System.out.println("\n"+r.toString());
				System.out.println("Match: "+r.getTrainingType().equals(r.getTestType()));
				if(r.getTrainingType().equals(r.getTestType())){
					hits++;
				}
			}

			//If k > 1 get the first k values of finalResults array and get all the types involved
			//the test type is most likely the most occurring training type.
			else if(k>1){
				//Map to "count" the said types
				HashMap<String,Integer> myMap = new HashMap<String,Integer>();

				for(int i = 0 ; i < k ; i++){
					Results r = finalResults.get(i);
					//				System.out.println("r value: "+r.toString());
					if(!myMap.containsKey(r.getTrainingType())){
						myMap.put(r.getTrainingType(), 1);
					}
					else{
						myMap.put(r.getTrainingType(),myMap.get(r.getTrainingType())+1);
					}
				}

				//Majority vote
				Entry<String,Integer> major = null;
				double i = 0;
				for(Entry<String,Integer> r:myMap.entrySet()){
					//find the most occurring class type
					if(r.getValue() > i){
						major = r;
						i = r.getValue();
					}

					//tie-breaker
					if(r.getValue() == i){
						double rand = Math.random()*10;	
						double rand2 = Math.random()*10;

						if(rand > rand2){
							major = r;
							i = r.getValue();
						}
					}
				}
				System.out.println("\nTest Type: "+a.getType());
				System.out.println("Class Types: "+myMap.toString());
				System.out.println("Most Occurring: "+major.toString());
				System.out.println("Probability of Guessing Right: "+(double)major.getValue()/k);
				System.out.println("Match: "+major.getKey().equals(a.getType()));

				if(major.getKey().equals(a.getType())){
					hits++;
					System.out.println("hits: "+hits);
				}

			}
		}
		//accuracy based on correct guesses vs. results count
		System.out.println("\nTotal Hits:"+hits +"\n# of Instances: "+finalResults.size());
		System.out.println("Overall Accuracy: "+ (double)(hits/finalResults.size()));	
		return;
	}



	/**
	 * Requires first argument to be trainingData file name;
	 * Second argument to be testData file name.
	 * @param args
	 */
	public static void main(String[] args){
		IrisMain im = new IrisMain();

		//populate the arraylists from the data file given
		im.populateData(args[0], args[1]);

		//make a JOptionPane for asking k value
		Frame frame = new Frame();
		String s = (String)JOptionPane.showInputDialog(frame, "Input K value", null, JOptionPane.PLAIN_MESSAGE,null,null, "1");
		im.setK(Integer.parseInt(s));

		im.classify();
		System.exit(0);
	}

}

