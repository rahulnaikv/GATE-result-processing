package cdac.in.gate.result;

import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.math.BigInteger;
import java.security.MessageDigest;

import cdac.in.gate.result.StdStats;
import cdac.in.gate.util.QRCodeGenerator;


/**
 *  
 * This class contains the configuration parameter for the 
 * Overall result processing program. 
 *
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 27/03/2014
 *
 **/

class Config {

	public static String negative = "3";
	public static String cancelled = "zero";
	public static String unattempted = "zero";	
	public static String invalidResponse = "zero";

}


/**
 *  
 * This class contains the Print configuration parameter for the 
 * Overall result processing program. 
 *
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 27/03/2014
 *
 **/


class Print{

	static boolean actual = false;
	static boolean detail = false;
	static boolean analysis = false;
	static boolean info = false;
	static boolean multiSession = false;
	static boolean resultView = false;
	static boolean scoreView = false;
}


/**
 *  
 *  CodeMapping class just create the mapping of the Paper Codes
 *  and Section code into the hash map, which can be use for final printing
 *	
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 27/03/2014
 *
 **/

class CodeMapping{

	static HashMap<String, String> paperCodeMap = new HashMap<String, String>();
	static HashMap<String, String> sectionCodeMap = new HashMap<String, String>();

	static{

		/*  Paper Code Mapping */

		paperCodeMap.put("AE", "Aerospace Engineering");			 	
		paperCodeMap.put("GG", "Geology and Geophysics");			 	
		paperCodeMap.put("AG", "Agricultural Engineering");			 	
		paperCodeMap.put("IN", "Instrumentation Engineering");			  		
		paperCodeMap.put("AR", "Architecture and Planning");			 	
		paperCodeMap.put("MA", "Mathematics");	 			
		paperCodeMap.put("BT", "Biotechnology");	 		
		paperCodeMap.put("ME", "Mechanical Engineering");			
		paperCodeMap.put("CE", "Civil Engineering");		 		
		paperCodeMap.put("MN", "Mining Engineering");				
		paperCodeMap.put("CH", "Chemical Engineering");			
		paperCodeMap.put("MT", "Metallurgical Engineering");			 	
		paperCodeMap.put("CS", "Computer Science and Information Technology");		
		paperCodeMap.put("PH", "Physics");			 			
		paperCodeMap.put("CY", "Chemistry");						
		paperCodeMap.put("PI", "Production and Industrial Engineering");		
		paperCodeMap.put("EC", "Electronics and Communication Engineering");		
		paperCodeMap.put("TF", "Textile Engineering and Fibre Science");		
		paperCodeMap.put("EE", "Electrical Engineering");			 	
		paperCodeMap.put("XE", "Engineering Sciences"); 				
		paperCodeMap.put("EY", "Ecology and Evolution"); 				
		paperCodeMap.put("XL", "Life Sciences"); 					

		/*  Section Code Mapping */

		sectionCodeMap.put("1", "Geology");
		sectionCodeMap.put("2", "Geophysics");
		sectionCodeMap.put("A", "Engineering Mathematics (Compulsory)");
		sectionCodeMap.put("H", "Chemistry (Compulsory)");
		sectionCodeMap.put("B", "Fluid Mechanics");
		sectionCodeMap.put("I", "Biochemistry");
		sectionCodeMap.put("C", "Materials Science");
		sectionCodeMap.put("J", "Botany");
		sectionCodeMap.put("D", "Solid Mechanics");
		sectionCodeMap.put("K", "Microbiology");
		sectionCodeMap.put("E", "Thermodynamics");
		sectionCodeMap.put("L", "Zoology");
		sectionCodeMap.put("F", "Polymer Science and Engineering");
		sectionCodeMap.put("M", "Food Technology");
		sectionCodeMap.put("G", "Food Technology");
	}	
}

/**
 *  
 * DigitalSignature is a private class use for creating Digital Signature for candidate
 * using the candidate's field, this use for verifying the candidate information from intantioanl 
 * tempering of the data.
 *
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 27/04/2014
 *
 *
 **/

class DigitalSignature{

	static MessageDigest md = null;

	static {	
		try{
			md = MessageDigest.getInstance("MD5");
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	/** 
	* This static method will return the fingerprint(MD5 hashed of the message) string of the message 
	* composed of the following data belong to candidate.  
 	* paperCode, rollNumber, name, category, gender, rawMark, GATEScore, rank
	* This method also add a salt as '2014gAtE' in the end
	*
	* @param message   candidate information into a single string(paperCode, rollNumber, name, category, gender, rawMark, GATEScore, rank). 
	* @return          MD5 hashed of message + '2014gAtE'  
	* @since           1.0
	*/

	public static String getDigitalSignature(String message){

		String salt = "2015gAtE";
		message = message+""+salt;
		md.reset();
		md.update( message.getBytes() );
		byte[] theDigest =  md.digest();
		BigInteger bigInt = new BigInteger( 1, theDigest );
		String hashtext = bigInt.toString(16);

		return hashtext;
	}
}

/**
 *  
 * This class is for Candidate response which store the candidate answer in 'answer' for Multipal Choice Question
 * and 'Options' in case of  for Range Type Question.
 *
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 27/04/2014
 *
 *
 **/

class Response{

	String answer;
	String options;

	/** 
    	* Response Class constructor.
        * @param answer candidate answer
        * @param obtions option id for MCQ type & answer in case of Range Type
    	*/

	Response(String answer, String options){
		this.answer = answer.trim();
		this.options = options.trim();
	}

	String getAnswer(){
		return answer;
	}

	String getOptions(){
		return options;
	}
}


/**
 *  
 * This is a abstract class Question which can be exteded by the actual 
 * this provide the abstract method as eval() & print() which can be actully 
 * implemented by the class who will be extending this class. 
 * Using this more question type can be supported by the system. Just need to extedns the 
 * new question type in the system.	 
 *
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 27/03/2014
 *
 **/

abstract class Question{

	String Id;
	double mark;
	boolean isCancelled;	
	String section;
	String DL;
	double negative;
	double unattempted;
	double invalidResponse;

	int R;
	int NA;
	int W;
	int AT;

	double perR;
	double perW;
	double perNA;
	double perRAT;

	abstract double eval(Response response);
	abstract void printLog( boolean flag );
	abstract void print();
	abstract String getAnswer();
	abstract String type();
	abstract String getDL();	
}


/**
 *  
 * This is MultipalChocie Question class Excending the 'Question' and proving the implemention of the 
 * Eval() and Print() method 
 *
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 27/03/2014
 *
 **/

class MultipalChocie extends Question{

	String answer;
	static String options = "ABCD";


	/** 
    * 
	* MultipalChocie Class constructor which initialised the question and assign the marks for
	* correct answer, Wrong answer, invalid answer based on the configuration on 'Config' Object.
	* 
    * @param Id Question Id 
	* @param section section of the question
	* @param answer answer of the question 
	* @param marks marks of the question if the answer is correct 
  	*
    */
		

	MultipalChocie(String Id, String section, String answer, String mark){

		this.Id = Id;
		this.section = section;
		this.isCancelled = false;
		this.R = 0;
		this.NA = 0;
		this.W = 0;	
		this.AT = 0;
		this.perR = 0.0d;
		this.perW = 0.0d;
		this.perNA = 0.0d;
		this.perRAT = 0.0d;
		this.DL = null;

		if( answer.equalsIgnoreCase("can") ){
			this.isCancelled = true;
		}else{
			this.mark = Double.parseDouble( mark );
			this.answer = answer.trim();

			if( this.answer.trim().length() != 1 ){
				System.out.println("Master key has error (MCQ) "+answer);
				System.exit(0);
			}

			if( Config.negative.equals("zero") ){
				this.negative = 0.0d;
			}else{ 
				this.negative = -1 * ( this.mark / Integer.parseInt( Config.negative ) );
			}

			if( Config.unattempted.equals("zero") ){
				this.unattempted = 0.0d;
			}else{
				this.unattempted = ( this.mark / Integer.parseInt( Config.unattempted ) );
			}

			if( Config.invalidResponse.equals("zero") ){
				this.invalidResponse = 0.0d;
			}else{
				this.invalidResponse = ( this.mark / Integer.parseInt( Config.invalidResponse ) );
			}

		}
	}      					

	/** 
    * 
	* This is eval method of the multipal choice question against a candidate response 
	* This method evalulate the correctness of the candidate response against question 
	* cottect answer and return the marks depending on the correct answer, wrong answer and invalid Response.
	* 
    * @param Id Question Id 
	* @param section section of the question
	* @param answer answer of the question 
	* @param marks marks of the question if the answer is correct 
  	**/

	double eval( Response response ){
		try{	
			if( response == null ){
				System.out.println("1. Error in response (MCQ) "+response.getAnswer() );
				System.exit(0);
			}

			if( isCancelled ){

				if( Config.cancelled.equals("zero") ){
					return 0.0d;
				}else{
					return ( this.mark / Integer.parseInt( Config.cancelled ) );
				}

			}else if( response.getAnswer().equals("--") ){   
				this.NA++;
				return unattempted;

			}else if( options.indexOf( response.getAnswer().charAt(0) ) < 0 ){
				System.out.println("2. Error in response (MCQ) "+response.getAnswer() );
				System.exit(0);

			}else if( response.getAnswer().charAt(0) == this.answer.charAt(0) ){
				this.AT++;
				this.R++;
				return mark;
			}else{
				this.AT++;
				this.W++;
				return negative;
			}

		}catch(Exception e){
			System.out.println("3. Error in response (MCQ) "+response.getAnswer() );
			System.exit(0);
		}

		this.AT++;
		return this.invalidResponse;
	}

	void print(){

		System.out.print("[MCQ"+Id+", "+answer+", ");
		FP.printD(mark);
		System.out.print(", ");
		FP.printD(negative);
		System.out.print(", ");
		FP.printD(unattempted);
		System.out.print("]");
	}

	void printLog(boolean flag){
		System.out.println("R:"+this.R+"# TY:"+type()+" # NA:"+this.NA+" # W:"+W+" # %R:"+perR+" # %W:"+perW+" # %NA:"+perNA+" # %R/A:"+perRAT+" # DL:"+DL);
	}

	String getDL(){
		return DL;
	}

	String getAnswer(){
		return answer;
	}

	String type(){
		return "MCQ";
	}
}

class RangeQuestion extends Question{

	private double lower;
	private double upper;

	/** 
    	* 
	* RangeQuestion Class constructor which initialised the question and assign the marks for
	* correct answer, Wrong answer, invalid answer etc.
	* 
        * @param Id Question Id 
	* @param section section of the question
	* @param answer answer of the question 
	* @param marks marks of the question if the answer is correct 
  	*
	*/

	RangeQuestion( String Id, String section, String answer, String mark){

		this.Id = Id;
		this.section = section;
		this.isCancelled = false;
		this.R = 0;
		this.NA = 0;
		this.W = 0;
		this.AT = 0;
		this.perR = 0.0d;
		this.perW = 0.0d;
		this.perNA = 0.0d;
		this.perRAT = 0.0d;
		this.DL = null;

		if( answer.equalsIgnoreCase("can") ){
			this.isCancelled = true;
		}else{
			String []token = answer.split(":");
			if( token.length != 2) {                                          
				System.out.println("Error in master key creation "+answer);
				System.exit(0);
			}

			this.lower = Double.parseDouble( token[0] );
			this.upper= Double.parseDouble( token[1] );	
			this.mark = Double.parseDouble( mark );
			this.negative = 0.0d;

			if( Config.unattempted.equals("zero") ){
				this.unattempted = 0.0d;
			}else{
				this.unattempted = ( this.mark / Integer.parseInt( Config.unattempted ) );
			}

			if( Config.invalidResponse.equals("zero") ){
				this.invalidResponse = 0.0d;
			}else{
				this.invalidResponse = ( this.mark / Integer.parseInt( Config.invalidResponse ) );
			}
		}
	}

	double eval(Response response){

		if( response == null ){
			System.out.println("1. Error in response (NAT) "+response.getOptions()+" "+response.getAnswer());
			System.exit(0);
		}

		if( isCancelled ){

			if( Config.cancelled.equals("zero") ){
				return 0.0d;
			}else{
				return ( this.mark / Integer.parseInt( Config.cancelled ) );
			}

		}else if ( response.getAnswer().equals("--") && !response.getOptions().equals("--")){  
			System.out.println("2. Error in response (NAT) "+response.getOptions()+" "+response.getAnswer());
			System.exit(0);
		}else if ( !response.getAnswer().equals("--") && response.getOptions().equals("--") ){
			System.out.println("3. Error in response (NAT) "+response.getOptions()+" "+response.getAnswer());
			System.exit(0);
		}else if( response.getAnswer().equals("--") ){
			this.NA++;
			return this.unattempted;
		}

		try{
			double resp =  Double.parseDouble( response.getOptions() ); 
			this.AT++;

			if( resp >= this.lower && resp <= this.upper){
				this.R++;
				return this.mark;
			}else{
				this.W++;
				return this.negative;                 
			}

		}catch(Exception e){
			return this.invalidResponse;
		}	
	}


	void print(){

		System.out.print("[NAT"+Id+", ("+upper+":"+lower+"), ");
		FP.printD(mark);
		System.out.print(", ");
		FP.printD(negative);
		System.out.print(", ");
		FP.printD(unattempted);
		System.out.print("] ");
	}

	void printLog(boolean flag){
		System.out.println("R:"+this.R+" # TY:"+type()+" # NA:"+this.NA+" # W:"+W+" # %R:"+perR+" # %W:"+perW+" # %NA:"+perNA+" # %R/A:"+perRAT+" # DL:"+DL);
	}

	String getAnswer(){
		return lower+":"+upper;
	}

	String getDL(){
		return DL;
	}

	String type(){
		return "NAT";
	}
}

class FP{

	public static void printD(double value){
		System.out.printf("%.2f",value);
	}

	public static String prints(double value){
		String output = String.format("%.2f", value);
		return output.trim();
	}


}

class Session{

	ArrayList <Double> listOfObtainedMarks;
	ArrayList <Double> listOfActualMarks;

	ArrayList <Question> listOfQuestions;
	ArrayList <Candidate> listOfCandidate;

	String id;
	double mTBar;
	double mQ;
	double mean;
	double stdDev;
	double maxRawScore;
	double minRawScore;

	int DL1;
	int DL2;
	int DL3;
	int DL4;
	int DL5;

	int zeroPointOnePercent;

	Session(String id){

		this.id = id;
		this.listOfQuestions = new ArrayList<Question>();
		this.listOfObtainedMarks = new ArrayList<Double>();
		this.listOfActualMarks = new ArrayList<Double>();
		this.listOfCandidate = new ArrayList<Candidate>();

		this.mTBar = 0.0d;
		this.mQ = 0.0d;
		this.mean = 0.0d;
		this.stdDev = 0.0d;
		this.zeroPointOnePercent = 0;
		this.maxRawScore = 0.0d;
		this.minRawScore = 0.0d;

		this.DL1 = 0;
		this.DL2 = 0;
		this.DL3 = 0;
		this.DL4 = 0;
		this.DL5 = 0;
	}	

	void calStats(){

		Collections.sort( listOfObtainedMarks, Collections.reverseOrder() );
		double [] marks = StdStats.toArray( listOfObtainedMarks );
		this.zeroPointOnePercent = ( int ) Math.ceil( ( (double) marks.length) / 1000 );
		this.mean = StdStats.mean( marks );
		this.stdDev = StdStats.stddev( marks );
		this.mTBar = StdStats.mean( marks, 0, zeroPointOnePercent - 1 );
		this.mQ = this.mean + this.stdDev;	
		double [] amarks = StdStats.toArray( listOfActualMarks );
		this.maxRawScore = StdStats.max( amarks );
		this.minRawScore = StdStats.min( amarks );
	}

	void print(){

		System.out.format(" ________________________________________%n");
		System.out.format("| Session ID             | %-13s |%n", id);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Total Candidates       | %-13d |%n", listOfObtainedMarks.size() );
		System.out.format("| 0.1 %% Candidate(s)     | %-13d |%n", zeroPointOnePercent );
		System.out.format("| Avg Of 0.1%%(s)         | %-13f |%n",mTBar );
		System.out.format("| Mean(s) + stdDev(s)    | %-13f |%n",mQ );
		System.out.format("| Mean(s)                | %-13f |%n",mean );
		System.out.format("| stdDev(s)              | %-13f |%n",stdDev );
		System.out.format("| max                    | %-13f |%n",maxRawScore );
		System.out.format("| min                    | %-13f |%n",minRawScore );
		System.out.format("|________________________|_______________|%n%n");

	}

	void printLog(){

		for(int i = 0;  i < this.listOfQuestions.size(); i++){

			Question question = this.listOfQuestions.get(i);

			question.perR =  Double.parseDouble( new DecimalFormat("#0.0#").format( (double) (100 / (double) this.listOfCandidate.size() ) * (double) question.R ) );
			question.perW =  Double.parseDouble( new DecimalFormat("#0.0#").format( (double) (100 / (double) this.listOfCandidate.size() ) * (double) question.W ) );
			question.perNA = Double.parseDouble( new DecimalFormat("#0.0#").format( (double) (100 / (double) this.listOfCandidate.size() ) * (double) question.NA ) );
			question.perRAT = Double.parseDouble( new DecimalFormat("#0.0#").format( (double) (100 / (double) question.AT ) * (double) question.R ) );	

			if( question.perR > 80.00d ){
				question.DL = "DL1";
				DL1++;
			}else if( question.perR >= 60.00d && question.perR <= 80.00d ){	 
				question.DL = "DL2";
				DL2++;
			}else if( question.perR >= 40.00d && question.perR <= 60.00d ){ 
				question.DL = "DL3";
				DL3++;
			}else if( question.perR >= 20.00d && question.perR <= 40.00d ){	 
				question.DL = "DL4";
				DL4++;
			}else if( question.perR < 20.00d ){	 
				question.DL = "DL5";
				DL5++;
			}
			System.out.print("Qn:"+(i+1)+" # ");
			if( i == this.listOfQuestions.size() -1)
				question.printLog( false );
			else
				question.printLog( true );
		}

		System.out.println();
		System.out.format(" ________________________________________%n");
		System.out.format("| D-Level Session        | %-13s |%n", id);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Total Candidates       | %-13d |%n", listOfObtainedMarks.size() );
		System.out.format("|________________________|_______________|%n");
		System.out.format("| DL1                    | %-13d |%n",DL1 );
		System.out.format("| DL2                    | %-13d |%n",DL2 );
		System.out.format("| DL3                    | %-13d |%n",DL3 );
		System.out.format("| DL4                    | %-13d |%n",DL4 );
		System.out.format("| DL5                    | %-13d |%n",DL5 );
		System.out.format("|________________________|_______________|%n%n");
	}
}

class Paper{

	String paperCode;
	Map<String, Session> sessionMap;
	ArrayList<Double> listOfObtainedMarks;
	ArrayList<Double> listOfNormalisedMarks;	
	ArrayList<Double> listOfActualNormalisedMarks;	
	ArrayList<Double> listOfActualMarks;	
	ArrayList<Candidate> listOfCandidate;

	double mTgBar;
	double mQg;
	double mQ;
	double mTBar;
	double mean;
	double stdDev;

	double maxRawMarks;
	double minRawMarks;
	double maxNorMarks;
	double minNorMarks;

	double genCutOff;
	double obcCutOff;
	double sTsCPwDCutOff;

	int maxOf10OR01per;
	int zeroPointOnePercent;
	boolean multiSession;

	int QGen;
	int QObc;
	int QSc;
	int QSt;
	int QPwD;
	int QGenX;
	int QObcX;
	int QPwDX;
	int QObcG;
	int QScG;
	int QStG;
	int QPwDG;

	int QGenObcB;

	int QSCObcB;
	int QSTObcB;
	int QPwDObcB;

	int QScX;
	int QStX;
	int notQ;
	int Q;


	double perQualified;
	double avgMarksQualified;
	double stdMarksQualified;

	int maleQ;
	int femaleQ;
	int otherQ;

	double perMaleQ;
	double perFemaleQ;
	double perOtherQ;

	double maxMarksMaleQ;
	double minMarksMaleQ;

	double maxMarksFemaleQ;
	double minMarksFemaleQ;

	double maxMarksOtherQ;
	double minMarksOtherQ;

	double maxMarksQualified;
	double minMarksQualified;

	static int SQ = 350;
	static int ST = 900;

	Paper(String paperCode){

		this.sessionMap = new HashMap<String, Session>();
		this.listOfObtainedMarks = new ArrayList<Double>();
		this.listOfNormalisedMarks = new ArrayList<Double>();
		this.listOfActualNormalisedMarks = new ArrayList<Double>();	
		this.listOfCandidate = new ArrayList<Candidate>();
		this.listOfActualMarks = new ArrayList<Double>();

		this.paperCode = paperCode;

		this.mTgBar = 0.0d;
		this.mQg = 0.0d;
		this.mQ = 0.0d;
		this.mTBar = 0.0d;
		this.mean = 0.0d;
		this.stdDev = 0.0d;

		this.genCutOff = 0.0d;
		this.obcCutOff = 0.0d;
		this.sTsCPwDCutOff = 0.0d;

		this.zeroPointOnePercent = 0;
		int maxOf10OR01per = 0;
		this.multiSession = false;

		this.maxRawMarks = 0.0d;
		this.minRawMarks = 0.0d;
		this.maxNorMarks = 0.0d;
		this.minNorMarks = 0.0d;

		this.QGen = 0;
		this.QObc = 0;
		this.QSc = 0;
		this.QSt = 0;
		this.QPwD = 0;

		this.QGenX = 0;
		this.QObcX = 0;

		this.QObcG = 0;
		this.QScG = 0;
		this.QStG = 0;
		this.QPwDG = 0;
		this.QGenObcB = 0;
		this.QScX = 0;
		this.QStX = 0;
		this.QPwDX = 0;
		this.notQ = 0;

		this.QSCObcB = 0;
		this.QSTObcB = 0; 
		this.QPwDObcB = 0;
		this.Q = 0;

		this.perQualified = 0.0d;
		this.minMarksQualified = 0.0d;
		this.avgMarksQualified = 0.0d;
		this.stdMarksQualified = 0.0d;

		this.maleQ = 0;
		this.femaleQ = 0;
		this.otherQ = 0;

		this.perMaleQ = 0.0d;
		this.perFemaleQ = 0.0d;

		this.maxMarksMaleQ = 0.0d;
		this.minMarksMaleQ = 0.0d;

		this.maxMarksFemaleQ = 0.0d;
		this.minMarksFemaleQ = 0.0d;

		this.maxMarksOtherQ = 0.0d;
		this.minMarksOtherQ = 0.0d;

		this.maxMarksQualified = 0.0d;
		this.minMarksQualified = 0.0d;

	}

	void ranking(){

		Collections.sort( listOfCandidate, new RawMarksComp() );

		if( multiSession ){
			Print.multiSession = true;
			Collections.sort( listOfCandidate, new NorMarksComp() );
		}

		double oldMarks = 99999.0d;  

		this.maxMarksMaleQ = -999.0d;
		this.minMarksMaleQ = 9999.0d;

		this.maxMarksFemaleQ = -999.0d;
		this.minMarksFemaleQ = 999.0d;

		this.maxMarksOtherQ = -999.0d;
		this.minMarksOtherQ = 999.0d;

		this.maxMarksQualified = -999.0d;
		this.minMarksQualified = 999.0d;

		ArrayList<Double> listOfQualifedMarks = new ArrayList<Double>();

		for(int i = 0, count = 1, rank = 1; i < listOfCandidate.size(); i++, count++){

			Candidate candidate = listOfCandidate.get(i);
			double marks = candidate.getRawMarks();

			if( multiSession )
				marks = candidate.getNRMark();
			if( oldMarks > marks)   
				rank = count;

			oldMarks = marks;
			candidate.rank = rank;

			if( candidate.info != null ){

				double score = candidate.getRawMarks();
				if( multiSession )	
					score = candidate.getNRMark();

				if( score >= obcCutOff ){

					candidate.isQualified = true;

					if( candidate.info.category.equals("1")  && score >= genCutOff ){
						QGenX++;
					}else if( candidate.info.category.equals("2")  && score >= genCutOff ){
						QObcG++;
					}else if( candidate.info.category.equals("3")  && score >= genCutOff ){
						QScG++;
					}else if( candidate.info.category.equals("4")  && score >= genCutOff ){
						QStG++;
					}

					if( candidate.info.isPd  && score >= genCutOff ){
						QPwDG++;
					}

					if( score >= obcCutOff && score < genCutOff ){

						if( candidate.info.category.equals("1") ){
							QGenObcB++;	
						}else if( candidate.info.category.equals("2") ){
							QObcX++;
						}else if( candidate.info.category.equals("3") ){
							QSCObcB++;	
						}else if( candidate.info.category.equals("4") ){
							QSTObcB++;	
						}

						if( candidate.info.isPd ){
							QPwDObcB++;	
						}
					}
				}

				if( ( candidate.info.category.equals("3") || candidate.info.category.equals("4") || candidate.info.isPd ) && score >= sTsCPwDCutOff ){

					candidate.isQualified = true;

					if( candidate.info.category.equals("3")  && score < obcCutOff )
						QScX++;
					else if( candidate.info.category.equals("4") && score < obcCutOff )
						QStX++;
					else if( candidate.info.isPd && score < obcCutOff )
						QPwDX++;
				}

				if( candidate.isQualified  ){

					Q++;

					listOfQualifedMarks.add( score );

					if( candidate.info.gender.equals("Male") )
						maleQ++;
					else if( candidate.info.gender.equals("Female") )
						femaleQ++;
					else
						otherQ++;

					if( candidate.info.gender.equals("Male") ){

						if( score > maxMarksMaleQ )
							maxMarksMaleQ = score;
						if( score < minMarksMaleQ )
							minMarksMaleQ = score;

					}else if( candidate.info.gender.equals("Female") ){

						if( score > maxMarksFemaleQ )
							maxMarksFemaleQ = score;
						if( score < minMarksFemaleQ )
							minMarksFemaleQ = score;
					}else{
						if( score > maxMarksOtherQ )
							maxMarksOtherQ = score;
						if( score < minMarksOtherQ )
							minMarksOtherQ = score;
					}

					if( score > maxMarksQualified )
						maxMarksQualified = score;
					if( score < minMarksQualified )
						minMarksQualified =  score;

					if( candidate.info.category.equals("2") && !candidate.info.isPd)
						QObc++;
					else if( candidate.info.category.equals("3") && !candidate.info.isPd)
						QSc++;
					else if( candidate.info.category.equals("4") && !candidate.info.isPd)
						QSt++;	
					if( candidate.info.isPd )
						QPwD++;
				}else{
					notQ++;
				}
			}

		}

		if( listOfQualifedMarks.size() > 0 ){

			double [] qmarks = StdStats.toArray( listOfQualifedMarks );

			this.avgMarksQualified = Double.parseDouble( new DecimalFormat("#0.0#").format( StdStats.mean( qmarks ) ) );
			this.stdMarksQualified = Double.parseDouble( new DecimalFormat("#0.0#").format( StdStats.stddev( qmarks ) ) );

			this.perQualified = Double.parseDouble( new DecimalFormat("#0.0#").format( ( (double) Q / ( double) (Q + notQ ) ) * 100 )  ) ;
			this.perMaleQ = Double.parseDouble( new DecimalFormat("#0.0#").format( ( (double) maleQ / (double) (Q + notQ ) ) * 100 ) );
			this.perFemaleQ = Double.parseDouble( new DecimalFormat("#0.0#").format( ( (double) femaleQ / (double) (Q + notQ ) ) * 100 )  );
			this.perOtherQ = Double.parseDouble( new DecimalFormat("#0.0#").format( ( (double) otherQ / (double) (Q + notQ ) ) * 100 ) );
		}

	}

	void process(){

		calStats();
		Iterator it = sessionMap.entrySet().iterator();

		while( it.hasNext() ){

			Map.Entry pairs = (Map.Entry)it.next();
			Session sn = (Session) pairs.getValue();
			sn.calStats();

			for(int i = 0; i < sn.listOfCandidate.size(); i++){

				Candidate candidate = sn.listOfCandidate.get(i);
				if( multiSession ){

					/*
					   CHANGED 14032014
					   The actual candidate marks( candidate.actualMark , with -ve), converted to 2 decimal, 
					   keeping the sign(+ve, -ve) as it is and stored in 'actualMark' 
					 */

					Double actualMark =   Double.parseDouble( new DecimalFormat("#0.0#").format( candidate.actualMark ));

					/*
					   CHANGED 14032014
					   Actual marks is used for calculating candidate Normalised marks.
					 */

					candidate.normalisedMark = ( (mTgBar - mQg) / (sn.mTBar - sn.mQ ) ) * ( actualMark - sn.mQ ) + mQg;
                    //System.out.println(" (  ("+mTgBar+" - "+mQg+") / ( "+sn.mTBar +" - "+ sn.mQ +" ) ) * ( "+ actualMark+" - "+sn.mQ+" ) +"+ mQg +" = "+candidate.normalisedMark);
                            
					candidate.actualNormalisedMark = candidate.normalisedMark;
					candidate.normalisedMark = Double.parseDouble( new DecimalFormat("#0.0#").format( candidate.normalisedMark ));

					/*
					   CHANGED 14032014
					   Now candidate actual normalised marks are stored in 'listOfActualNormalisedMarks' for 
					   "JUST" calculating the minimum NormalisedMarks keeping the sign (+ve,-ve) as it is.
					 */

					listOfActualNormalisedMarks.add( candidate.normalisedMark );

					if( candidate.normalisedMark >= 0)
						listOfNormalisedMarks.add( candidate.normalisedMark );
					else
						listOfNormalisedMarks.add( 0.0d );
				}else{
					candidate.actualGATEScore = ( SQ + ( ST - SQ ) * ( ( candidate.rawMark  - mQ ) / ( mTBar - mQ  ) ) );
					candidate.GATEScore = (int) Math.round( candidate.actualGATEScore );
					if( candidate.GATEScore > 1000 )
						candidate.GATEScore = 1000;
				}
			}
		}

		if( multiSession ){
			calStatsAfterNormalisation();
			it = sessionMap.entrySet().iterator();
			while( it.hasNext() ){
				Map.Entry pairs = (Map.Entry)it.next();
				Session sn = (Session) pairs.getValue();
				for(int i = 0; i < sn.listOfCandidate.size(); i++){
					Candidate candidate = sn.listOfCandidate.get(i);
					candidate.actualGATEScore = ( SQ + ( ST - SQ ) * ( ( candidate.normalisedMark  - mQ ) / ( mTBar - mQ  ) ) );
					candidate.GATEScore = (int) Math.round( candidate.actualGATEScore );
					if( candidate.GATEScore > 1000 )
						candidate.GATEScore = 1000;
				}
			}
		}

	}

	void addQuestion(int qn, Question question, String sessionNo){

		Session session = sessionMap.get( sessionNo );
		if( session == null ){
			session = new Session( sessionNo );
			sessionMap.put( sessionNo, session );
		}
		session.listOfQuestions.add( qn, question );
	}

	void calStatsAfterNormalisation(){

		Collections.sort( this.listOfNormalisedMarks, Collections.reverseOrder() );
		double [] marks = StdStats.toArray( this.listOfNormalisedMarks );

		zeroPointOnePercent = ( int ) Math.ceil( ( (double) marks.length) / 1000 );
		maxOf10OR01per = (int) Math.max( zeroPointOnePercent, 10 );
		mTgBar = StdStats.mean( marks, 0, this.zeroPointOnePercent - 1 );

		mTBar = StdStats.mean( marks, 0, this.maxOf10OR01per - 1 );; 
		mean = StdStats.mean( marks );
		stdDev = StdStats.stddev( marks );

		mQg = this.mean + this.stdDev;
		mQ = Math.max( this.mQg , 25 );

		genCutOff = Double.parseDouble( new DecimalFormat("#0.0#").format( mQ ) );
		obcCutOff = Double.parseDouble( new DecimalFormat("#0.0#").format( mQ * 0.9 ) );
		sTsCPwDCutOff = Double.parseDouble( new DecimalFormat("#0.0#").format( mQ * (2.0/3.0) ) );

		maxNorMarks = StdStats.max( marks );

		/*
		   CHANGED 14032014
		   listOfActualNormalisedMarks is used for calculating 'minNorMarks' here			
		 */

		double []amarks = StdStats.toArray( this.listOfActualNormalisedMarks );
		minNorMarks = StdStats.min( amarks );
	}

	void calStats(){

		Collections.sort( listOfObtainedMarks, Collections.reverseOrder());

		double [] marks = StdStats.toArray( listOfObtainedMarks );
		zeroPointOnePercent = ( int ) Math.ceil( ( (double) marks.length) / 1000 );
		maxOf10OR01per = (int) Math.max( zeroPointOnePercent, 10 );

		mTgBar = StdStats.mean( marks, 0, zeroPointOnePercent - 1 );

        for(int i = 0;  i < 186; i++)
                System.out.print(marks[i]+" + " );    
        System.out.println(" => "+ marks.length);        
		mTBar = StdStats.mean( marks, 0, maxOf10OR01per - 1 ); 

		mean = StdStats.mean( marks );
		stdDev = StdStats.stddev( marks );

		mQg = mean + stdDev;
		mQ = Math.max( mQg , 25 );

		genCutOff = Double.parseDouble( new DecimalFormat("#0.0#").format( mQ ) );
		obcCutOff = Double.parseDouble( new DecimalFormat("#0.0#").format( mQ * 0.9 ) );
		sTsCPwDCutOff = Double.parseDouble( new DecimalFormat("#0.0#").format( mQ * (2.0/3.0) ) );

		if( sessionMap.size() > 1)
			multiSession = true;

		Iterator it = sessionMap.entrySet().iterator();
		while( it.hasNext() ){
			Map.Entry pairs = (Map.Entry)it.next();
			Session session = (Session) pairs.getValue();
			session.calStats();
		}

		double [] amarks = StdStats.toArray( listOfActualMarks );
		maxRawMarks = StdStats.max( amarks ) ;
		minRawMarks = StdStats.min( amarks ) ;
	}	

	void header2(){
		System.out.println(" _____________________________________________________________________________________________________________________________________________");
		System.out.println("|        |             |          |                                                                                                           |");
		System.out.println("| Rank   | Reg-Number  | Raw-Mark | Question wise marks                                                                                       |");
		System.out.println("|________|_____________|__________|___________________________________________________________________________________________________________|");
	}
	void footer2(){
		System.out.println("|________|_____________|__________|___________________________________________________________________________________________________________|");
	}

	void header1( ){
		if( Print.info ){
			System.out.println(" _________________________________________________________________________________________________________________________________________________________");
			System.out.println("|        |             |                                     |         |          |                 |           |       |       |             |           |");
			System.out.println("| Rank   | Reg-Number  | Name                                | Session | Raw-Mark | NormalisedMarks | GATEScore | CatID | PwD   | Qualified   | Section   |");
			System.out.println("|________|_____________|_____________________________________|_________|__________|_________________|___________|_______|_______|_____________|___________|");
		}else{
			System.out.println(" _______________________________________________________________________________________");
			System.out.println("|        |              |         |          |                 |           |            |");
			System.out.println("| Rank   | Reg-Number   |Session  | Raw-Mark | NormalisedMarks | GATEScore | Section    |");
			System.out.println("|________|______________|_________|__________|_________________|___________|____________|");
		}
	}

	void footer1(){
		if( Print.info ){
			System.out.println("|________|_____________|_____________________________________|_________|__________|_________________|___________|_______|_______|_____________|___________|");
		}else{
			System.out.println("|________|______________|_________|__________|_________________|___________|____________|");
		}
	}

	void footer0( ){
		if( Print.info ){		
			System.out.println("|________|_____________|____________________|__________|_____________|_________________|_________________|___________|__________________|_______|_______|___________|___________|");
		}else{
			System.out.println("|________|_____________|_________|__________|_____________|_________________|___________|__________________|___________|");
		}
	}

	void header0( ){
		if( Print.info ){
			System.out.println(" ________________________________________________________________________________________________________________________________________________________________________________________");
			System.out.println("|        |             |                                     |         |          |             |                 |           |                  |       |       |           |           |");
			System.out.println("| Rank   | Reg-Number  | Name                                | Session | Raw-Mark | Actual-Mark | NormalisedMarks | GATEScore | Actual GATEScore | CatID | Pwd   | Qualified | Section   |");
			System.out.println("|________|_____________|_____________________________________|_________|__________|_____________|_________________|___________|__________________|_______|_______|___________|___________|");
		}else{
			System.out.println(" ______________________________________________________________________________________________________________________");
			System.out.println("|        |             |         |          |             |                 |           |                  |           |");
			System.out.println("| Rank   | Reg-Number  | Session | Raw-Mark | Actual-Mark | NormalisedMarks | GATEScore | Actual GATEScore | Section   |");
			System.out.println("|________|_____________|_________|__________|_____________|_________________|___________|__________________|___________|");
		}
	}

	void print(boolean log){

		ranking();
		print();

		Iterator it = sessionMap.entrySet().iterator();
		if( log ){
			System.out.format(" ________________________________________%n");
			System.out.format("|                                        |%n");
			System.out.format("|              Log Print                 |%n");
			System.out.format("|________________________________________|%n");
		}
		while( it.hasNext() ){
			Map.Entry pairs = (Map.Entry)it.next();
			Session session = (Session) pairs.getValue();
			session.print();
			if( log ){
				session.printLog( );
			}
		}

		if( Print.resultView ){

			/*
			   CHANGED 16032014
			   In header paper-name, opt-1-Sec-name, and opt-2-sec-name is added 

			 */

			System.out.println("Registration_id, Enrollment_id, applicant_name, category_id, is_pd, Paper-Code, Paper-Name, opt_1_sec, opt_1_sec_name,opt_2_sec, opt_2_sec_name, RawMarks, Normalized-Marks, AIR, GATE-Score, is_qualified, genCutOff, obcCutOff, sTsCPwDCutOff");
			printResultView();
			return;

		}else if( Print.scoreView ){

			/*
			   CHANGED 16032014
			   In header paper-name, opt-1-Sec-name, and opt-2-sec-name is added 

			 */

			System.out.println("Registration_id, Enrollment_id, QRCode, GATE-Year,GATE-PaperCode, Paper-Name, Sec1, Sec1-Name, sec2, Sec2-Name, Candidate-Name, Number-of-Candidate-appeared,RawMarks,NorMarks,GATEScore,AIR, category, PwD,Scribe,Nationality,Gender,dob (dd/mm/yy),Qualifying Degree, Qualifing-Discipline, Qualifing-Year,Phone,State(Permanent Address),Name of the Parent, Email,address_line_1,address_line_2,address_line_3,City,State,PIN,DigitalFingerPrint,PhotoPath,SignaturePath,Gen-cutoff,OBC-cutoff,SCSTPwD_Cutoff");
			printScoreView();
			return;

		}else if( Print.detail )
			header2();
		else if( Print.actual )
			header0();
		else
			header1();

		for(int i = 0; i < listOfCandidate.size(); i++){
			Candidate candidate = listOfCandidate.get(i);
			candidate.print();
		}

		if( Print.detail )
			footer2();
		else if( Print.actual )
			footer0();
		else
			footer1();

	}

	void print(){

		System.out.format(" ________________________________________%n");
		System.out.format("| Paper Code             | %-13s |%n", paperCode);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Total Candidates       | %-13d |%n", listOfObtainedMarks.size() );
		System.out.format("| Avg 0.1%% Candidate(G)  | %-13f |%n", mTgBar);
		System.out.format("| 0.1 %% Candidate(G)     | %-13d |%n", zeroPointOnePercent );
		System.out.format("| Max(10,0.1%%) (G)       | %-13d |%n", maxOf10OR01per );
		System.out.format("| Max of means(G)        | %-13f |%n", mTBar);
		System.out.format("| Max of (mu+sigma,25)   | %-13f |%n", mQ );
		System.out.format("| Mean(G)                | %-13f |%n", mean );
		System.out.format("| stdDev(G)              | %-13f |%n", stdDev );
		System.out.format("| Max(Raw)               | %-13f |%n", maxRawMarks );
		System.out.format("| Min(Raw)               | %-13f |%n", minRawMarks );
		System.out.format("| Max(Nor)               | %-13.2f |%n", maxNorMarks );
		System.out.format("| Min(Nor)               | %-13.2f |%n", minNorMarks );
		System.out.format("| CutOff(GEN)            | %-13.2f |%n", genCutOff );
		System.out.format("| CutOff(OBC)            | %-13.2f |%n", obcCutOff );
		System.out.format("| CutOff(ST/SC/PwD)      | %-13.2f |%n", sTsCPwDCutOff );
		System.out.format("|________________________|_______________|%n");
		System.out.format("| (X-GEN)                | %-13d |%n", QGenX );
		System.out.format("| (OBC-as-GEN)           | %-13d |%n", QObcG);
		System.out.format("| (SC-as-GEN)            | %-13d |%n", QScG);
		System.out.format("| (ST-as-GEN)            | %-13d |%n", QStG);
		System.out.format("| (PwD-as-GEN) *         | %-13d |%n", QPwDG);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| (X-OBC)                | %-13d |%n", QObcX );
		System.out.format("| (GEN-in-OBC-BAND)      | %-13d |%n", QGenObcB);
		System.out.format("| (SC-in-OBC-BAND)       | %-13d |%n", QSCObcB);
		System.out.format("| (ST-in-OBC-BAND)       | %-13d |%n", QSTObcB);
		System.out.format("| (PwD-in-OBC-BAND) *    | %-13d |%n", QPwDObcB);
		System.out.format("| (X-SC)                 | %-13d |%n", QScX );
		System.out.format("| (X-ST)                 | %-13d |%n", QStX );
		System.out.format("| (X-PD)                 | %-13d |%n", QPwDX );
		System.out.format("|________________________|_______________|%n");
		System.out.format("| (OBC-Qualified-Ex-PD)  | %-13d |%n", QObc);
		System.out.format("| (SC-Qualified-EX-PD)   | %-13d |%n", QSc);
		System.out.format("| (ST-Qualified-EX-PD)   | %-13d |%n", QSt);
		System.out.format("| (PwD-Qualified)        | %-13d |%n", QPwD);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| (Score-Card Issued)    | %-13d |%n", Q);
		System.out.format("| (No-Score-Card)        | %-13d |%n", notQ);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Total GEN              | %-13d |%n", QGenX );
		System.out.format("| Total OBC              | %-13d |%n", ( QObcG + QObcX ) );
		System.out.format("| Total SC               | %-13d |%n", ( QScG + QSCObcB + QScX ) );
		System.out.format("| Total ST               | %-13d |%n", ( QStG + QSTObcB + QStX ) );
		System.out.format("| Total PwD              | %-13d |%n", ( QPwDObcB + QPwDX + QPwDG ) );
		System.out.format("| Total SC/ST/PwD        | %-13d |%n", (QScG + QSCObcB + QScX ) + (QStG + QSTObcB + QStX) + ( QPwDObcB + QPwDX + QPwDG ) );
		System.out.format("|________________________|_______________|%n");

		System.out.format("| %% Total GEN            | %-13.2f |%n", ( ( (double) QGenX / (double) (Q + notQ ) )  * 100 ) );
		System.out.format("| %% Total OBC            | %-13.2f |%n", ( ( (double) ( QObcG + QObcX ) / (double) ( Q + notQ ) ) * 100)  );
		System.out.format("| %% Total SC             | %-13.2f |%n", ( ( (double) ( QScG + QSCObcB + QScX ) / (double) ( Q + notQ ) ) * 100 ) );
		System.out.format("| %% Total ST             | %-13.2f |%n", ( ( (double) ( QStG + QSTObcB + QStX ) / (double) ( Q + notQ ) ) * 100 ) );
		System.out.format("| %% Total PwD            | %-13.2f |%n", ( ( (double) ( QPwDObcB + QPwDX + QPwDG ) / (double) ( Q + notQ ) ) * 100 ) );
		System.out.format("| %% Total SC/ST/PwD      | %-13.2f |%n", ( ( (double) ( ( QScG + QSCObcB + QScX ) + ( QStG + QSTObcB + QStX ) + ( QPwDObcB + QPwDX + QPwDG ) ) / (double) ( Q + notQ ))*100) );



		System.out.format("|________________________|_______________|%n");
		System.out.format("| %% Qualified            | %-13.2f |%n", perQualified);
		System.out.format("| Max Marks Qualified    | %-13.2f |%n", maxMarksQualified);
		System.out.format("| Min Marks Qualified    | %-13.2f |%n", minMarksQualified);
		System.out.format("| Avg Marks Qualified    | %-13.2f |%n", avgMarksQualified);
		System.out.format("| StdD Marks Qualified   | %-13.2f |%n", stdMarksQualified);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Male Qualified         | %-13d |%n", maleQ);
		System.out.format("| Female Qualified       | %-13d |%n", femaleQ);
		System.out.format("| Other Qualified        | %-13d |%n", otherQ);
		System.out.format("| %% Male                 | %-13.2f |%n", perMaleQ);
		System.out.format("| %% Female               | %-13.2f |%n", perFemaleQ);
		System.out.format("| %% Other                | %-13.2f |%n", perOtherQ);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Max Male Qualified     | %-13.2f |%n", maxMarksMaleQ);
		System.out.format("| Min Male Qualified     | %-13.2f |%n", minMarksMaleQ);
		System.out.format("| Max FeMale Qualified   | %-13.2f |%n", maxMarksFemaleQ);
		System.out.format("| Min FeMale Qualified   | %-13.2f |%n", minMarksFemaleQ);
		System.out.format("| Max Other Qualified    | %-13.2f |%n", maxMarksOtherQ);
		System.out.format("| Min Other Qualified    | %-13.2f |%n", minMarksOtherQ);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| -999.0/+999.0 No Candidate found       |%n");
		System.out.format("|________________________|_______________|%n");
	}



	void printResultView(){

		for(int i = 0; i < listOfCandidate.size(); i++){
			Candidate c = listOfCandidate.get(i);

			if( c.info == null ){
				System.out.println("Candidate information is msssing");
				return;
			}

			String NRMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualNormalisedMark ) ) + "";

			if( !multiSession )
				NRMark = "Not Applicable";
				System.out.print(c.paperCode+""+c.rollNumber+","+c.info.applicationId+","+c.info.name+","+c.info.category+","+c.info.isPd+","+c.paperCode+","+CodeMapping.paperCodeMap.get( c.paperCode.trim() )+",");

			if( c.sections.size() > 0 && ( c.paperCode.equals("XL") || c.paperCode.equals("GG") || c.paperCode.equals("XE") )  ){

				Iterator<String> itr = c.sections.iterator();

				while( itr.hasNext() ){
					String section = itr.next();
					System.out.print(section+","+CodeMapping.sectionCodeMap.get( section.trim() )+", ");
				}	
				if( c.sections.size() == 1)	
					System.out.print(", , ");
			}
			else{
				System.out.print(", , , , ");
			}
			double rMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualMark ) );

			System.out.println( rMark+", "+NRMark+","+c.rank+","+c.GATEScore+","+c.isQualified+","+genCutOff+","+obcCutOff+","+sTsCPwDCutOff);
		}

	}

	void printScoreView(){

		for(int i = 0; i < listOfCandidate.size(); i++){

			Candidate c = listOfCandidate.get(i);
			if( !c.isQualified )
				continue;

			if( c.info == null ){
				System.out.println("Candidate information is msssing");
				return;
			}
			c.photoPath="photo/"+c.paperCode+""+c.rollNumber;
			c.signaturePath="signature/"+c.paperCode+""+c.rollNumber;
			c.digitalFP = DigitalSignature.getDigitalSignature( c.paperCode+""+c.rollNumber+""+c.name+""+c.info.category+""+c.info.gender+""+c.rawMark+""+c.GATEScore+""+c.rank);
			c.qrCode = QRCodeGenerator.getCode(); 	

			String NRMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualNormalisedMark ) ) + "";
			if( !multiSession )
				NRMark = "Not Applicable";

			System.out.print(c.paperCode+""+c.rollNumber+", "+c.info.applicationId+", "+c.qrCode.trim()+", 2015 ,"+c.paperCode+", "+CodeMapping.paperCodeMap.get(c.paperCode.trim())+", ");

			if( c.sections.size() > 0 && ( c.paperCode.equals("XL") || c.paperCode.equals("GG") || c.paperCode.equals("XE") ) ){

				Iterator<String> itr = c.sections.iterator();

				while( itr.hasNext() ){
					String section = itr.next();
					System.out.print(section+", "+CodeMapping.sectionCodeMap.get( section.trim() )+", ");
				}
				if( c.sections.size() == 1)	
					System.out.print(" , , ");

			}
			else{
				System.out.print(" , , , , ");
			}

			double rMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualMark ) );
			System.out.println(c.info.name+","+listOfCandidate.size()+","+rMark+","+NRMark+","+c.GATEScore+","+c.rank+","+c.info.category+","+c.info.isPd+","+c.info.scribe+","+c.info.nationality+","+c.info.gender+","+c.info.dob+","+c.info.qualifiyngDegree+","+c.info.qualifiyngDiscipline+","+c.info.qualifyingYear+","+c.info.phone+","+c.info.perState+","+c.info.parentName+","+c.info.email+",\""+c.info.address1+"\",\""+c.info.address2+"\",\""+c.info.address3+"\","+c.info.city+","+c.info.state+","+c.info.pincode+","+c.digitalFP+","+c.photoPath+","+c.signaturePath+","+genCutOff+","+obcCutOff+","+sTsCPwDCutOff);

		}

	}

	void printLog(){
		Iterator it = sessionMap.entrySet().iterator();
		while( it.hasNext() ){
			Map.Entry pairs = (Map.Entry)it.next();
			Session session = (Session) pairs.getValue();
			session.printLog();
		}
	}
}

class CandidateInfo{

	String applicationId;
	String name;
	String parentName;
	String category;
	String address1;
	String address2;
	String address3;
	String city;
	String perState;

	String pincode;
	String nationality;
	String gender;
	String dob;
	String qualifiyngDegree;
	String qualifiyngDiscipline;
	String qualifyingYear;
	String phone;
	String email;
	String state;

	boolean scribe;	
	boolean isPd;

	CandidateInfo( String applicationId, String name, String parentName, String dob, String gender, String nationality, String category, String isPd, String scribe, String perState, String email, String phone, String pincode, String qualifiyngDegree, String qualifiyngDegreeDiscipline, String address1, String address2, String address3, String city, String state, String qualifyingYear) {

		this.applicationId = applicationId;                 
		this.name = name;
		this.parentName = parentName;
		this.category = category;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.city = city;
		this.perState = perState;

		this.pincode = pincode;
		this.nationality = nationality;
		this.gender = gender;
		this.dob = dob;

		this.qualifiyngDegree = qualifiyngDegree;
		this.qualifiyngDiscipline = qualifiyngDegreeDiscipline;
		this.qualifyingYear = qualifyingYear;

		this.phone = phone;
		this.email = email;
		this.state = state;

		if( scribe.trim().equals("t"))
			this.scribe = true;	

		if( isPd.trim().equals("t"))
			this.isPd = true;
	}

	String print(){
		String out = applicationId+","+name+", "+parentName+", "+dob+", "+gender+", "+nationality+", "+scribe+", "+email+", "+phone+", "+pincode+", "+qualifiyngDegree+", "+address1+", "+address2+", "+address3+", "+city+", "+state;
		return out.trim();
	}
}


class Candidate {

	String rollNumber;
	String name;	
	String paperCode;
	String sessionId;
	boolean isQualified;
	CandidateInfo info;

	int rank;	
	double actualMark;	
	double rawMark;
	double normalisedMark;
	double actualNormalisedMark;
	int GATEScore;
	double actualGATEScore;

	String digitalFP;
	String photoPath;
	String signaturePath;
	String qrCode;	

	List<Double> marks;
	List<Response> responses;
	SortedSet<String> sections;

	Candidate(String rollNumber, String name, String sessionId, String paperCode){

		this.rollNumber = rollNumber;
		this.name = name;
		this.sessionId = sessionId;
		this.paperCode = paperCode;

		this.rawMark = 0.0d;
		this.actualMark = 0.0d;
		this.actualGATEScore =  0.0d;
		this.GATEScore =  0;
		this.normalisedMark = 0.0d;
		this.rank = -1;

		this.isQualified = false;
		this.info = null;
		this.digitalFP = null;
		this.photoPath = null;
		this.signaturePath = null;
		this.qrCode = null;

		marks = new ArrayList<Double>();
		responses = new ArrayList<Response>();
		sections = new TreeSet<String>();
	}

	double getRawMarks(){
		return this.rawMark;
	}

	double getGATEScore(){
		return this.GATEScore;
	}

	double getNRMark(){
		return this.normalisedMark;
	}

	void print(){

		if( Print.detail ){

			System.out.format("| %5d  | %-11s | %-8.2f |", rank, paperCode+""+rollNumber, rawMark, actualMark);
			String output = "";
			boolean first = true;
			for(int i = 0; i < marks.size(); i++ ){
				String qw = "";
				if( responses.get(i).answer.equals("--") ){
					qw = "NA";
				}else{
					qw += ""+FP.prints( marks.get(i) )+"|"+responses.get(i).answer+"|"+responses.get(i).options;
				}				

				if( i == marks.size() - 1)
					output += ""+qw;
				else	
					output += ""+qw+",";

				if( output.length() > 100){
					if( first ){
						System.out.format("%-106s |\n",output);
					}else{
						System.out.print("|        |             |          |");
						System.out.format("%-106s |\n",output);
					}
					output = "";
					first = false;
				}
			}
			if( output.trim().length() > 1){
				System.out.print("|        |             |          |");
				System.out.format("%-106s |\n",output);

			}
			System.out.println("|        |             |          |                                                                                                           |");

		}else{

			String NrScore = normalisedMark+"";

			if( !Print.multiSession ){
				NrScore = "Not Applicable";
			}

			if( info != null){

				if( Print.actual ){
					System.out.format("| %5d  | %-11s | %-35s | %-7s | %-8.2f | %-11f | %-15s | %-9d | %-16f | %-5s | %-5b | %-9b | ", rank, paperCode+""+rollNumber, info.name, sessionId, rawMark, actualMark, NrScore , GATEScore, actualGATEScore, info.category, info.isPd, isQualified);
				}else{
					System.out.format("| %5d  | %-11s | %-35s | %-7s | %-8.2f | %-15s | %-9d |  %-4s | %-5b | %-11b | ", rank, paperCode+""+rollNumber, info.name, sessionId, rawMark, NrScore, GATEScore, info.category, info.isPd, isQualified );
				}
			}else{

				if( Print.actual ){
					System.out.format("| %5d  | %-11s | %-7s | %-8.2f | %-11f | %-15.2f | %-9d | %-16f | ", rank, paperCode+""+rollNumber, sessionId, rawMark, actualMark, normalisedMark, GATEScore, actualGATEScore);
				}else{
					System.out.format("| %5d  | %-11s | %-7s | %-8.2f | %-15.2f | %-9d | ", rank, paperCode+""+rollNumber, sessionId, rawMark, normalisedMark, GATEScore );
				}

			}

			Iterator<String> itr = sections.iterator();

			String section = "";

			boolean first = true;

			while( itr.hasNext() ){

				String tsection = itr.next().trim();	

				if( first ){
					section = tsection;	
					first = false;
				}else if( "GA".equals( tsection ) ) { 	
				    section = tsection+":"+section;	
				}else{
				    section  = section+":"+tsection;
				}	
			}
			System.out.format("%-9s |%n",section);
		}

	}
	void printInfo(){
		String output =  rank+", "+rollNumber+" ,"+sessionId+", "+rawMark+", "+actualMark+", "+normalisedMark+", "+GATEScore+", "+actualGATEScore+", "+info.category+", "+info.isPd+", "+isQualified;
		output += ", "+info.print()+", "+photoPath+", "+signaturePath+", "+qrCode+", "+digitalFP;
		System.out.println( output.trim() );
	}
}

class NorMarksComp implements Comparator<Candidate>{
	@Override
		public int compare(Candidate candidate1, Candidate candidate2) {
			if( candidate1.getNRMark() < candidate2.getNRMark() )
				return 1;
			if( candidate1.getNRMark() > candidate2.getNRMark() )
				return -1;

			long d1 = Double.doubleToLongBits(candidate1.getNRMark());
			long d2 = Double.doubleToLongBits(candidate2.getNRMark());

			return (d1 == d2 ?  0 : // Values are equal
					(d1 < d2 ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
					 1));                          // (0.0, -0.0) or (NaN, !NaN)
		}
}

class RawMarksComp implements Comparator<Candidate>{
	@Override
		public int compare(Candidate candidate1, Candidate candidate2) {

			if( candidate1.getRawMarks() < candidate2.getRawMarks() )
				return 1;
			if( candidate1.getRawMarks() > candidate2.getRawMarks() )
				return -1;

			long d1 = Double.doubleToLongBits(candidate1.getRawMarks());
			long d2 = Double.doubleToLongBits(candidate2.getRawMarks());

			return (d1 == d2 ?  0 : // Values are equal
					(d1 < d2 ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
					 1));                          // (0.0, -0.0) or (NaN, !NaN)
		}
}


class GATEScore implements Comparator<Candidate>{
	@Override
		public int compare(Candidate candidate1, Candidate candidate2) {
			if( candidate1.getGATEScore() < candidate2.getGATEScore() )
				return 1;
			if( candidate1.getGATEScore() > candidate2.getGATEScore() )
				return -1;

			long d1 = Double.doubleToLongBits(candidate1.getGATEScore());
			long d2 = Double.doubleToLongBits(candidate2.getGATEScore());

			return (d1 == d2 ?  0 : // Values are equal
					(d1 < d2 ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
					 1));                          // (0.0, -0.0) or (NaN, !NaN)
		}
}

public class ResultProcessing{

	Map<String, Paper> paperMap;
	Map<String, CandidateInfo> candidateInfoMap;
	ResultProcessing(){
		paperMap = new HashMap<String, Paper>();
		candidateInfoMap = new HashMap<String, CandidateInfo>();
	}

	void readKey(String file){

		try{
			BufferedReader br = new BufferedReader(new FileReader( new File(file)) );
			String questionType = null;
			String sectionName = null;
			String questionKey = null;
			String questionMarks = null;
			String negativeMarks = null;

			boolean firstline = true;
			int count = 0;

			while( ( questionType = br.readLine() ) != null ){

				if( firstline ){
					firstline = false;
					continue;
				}
				sectionName = br.readLine();
				questionKey = br.readLine();
				questionMarks = br.readLine();

				//System.out.println("QT=> "+ questionType );
				//System.out.println("SN=> "+ sectionName );
				//System.out.println("KY=> "+ questionKey );
				//System.out.println("mK=> "+ questionMarks );

				String[] typeToken = questionType.split(",");
				String[] sectionToken = sectionName.split(",");
				String[] keyToken =  questionKey.split(",");
				String[] marksToken =  questionMarks.split(",");

				String paperCode = typeToken[1].trim();
				String sessionId = typeToken[2].trim();

				Paper paper = paperMap.get( paperCode );

				if( paper == null )
					paper = new Paper( paperCode );

				for(int i = 3, qn = 0; i < typeToken.length; i++, qn++ ){

					Question question  =  null;					

					if( typeToken[i].equals("MCQ")  ){

						//System.out.println( sectionToken.length+", "+ keyToken.length+", "+marksToken.length) ;
						question = new MultipalChocie("Q"+(i-2), sectionToken[i].trim(), keyToken[i].trim(), marksToken[i].trim() );

					}else if( typeToken[i].equals("NAT")  ){
						question = new RangeQuestion("Q"+(i-2), sectionToken[i].trim(), keyToken[i].trim(), marksToken[i].trim() );
					}

					if( question == null ){
						System.out.println( typeToken[i] );
						System.out.println("Question is not proper "+ questionKey);
						System.exit(0);
					}

					paper.addQuestion( qn, question, sessionId );
				}

				if( paper == null){

					System.out.println("Paper is not proper "+ questionKey);
					System.exit(0);
				}

				paperMap.put( paperCode, paper );
				count++;
			}

		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	boolean isAttempted(Response response){

		if( response != null ){
			if( !response.answer.trim().equals("--") ){
				return true;
			}
		}
		return false;
	}

	void readCandidateInfo(String filename){
		try{

			BufferedReader br = new BufferedReader(new FileReader( new File(filename) ) );
			String line = null;

			while( ( line = br.readLine() ) != null ){
				String[] tk = line.split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?");

				CandidateInfo cinfo = new CandidateInfo( tk[1].trim(), tk[5].trim(), tk[6].trim(), tk[7].trim(), tk[8].trim(), tk[9].trim(), tk[10].trim(), tk[11].trim(), tk[12].trim(), tk[13].trim(), tk[15].trim(), tk[16].trim(), tk[17].trim(), tk[18].trim(), tk[22].trim(), tk[25].trim(), tk[26].trim(), tk[27].trim(),tk[28].trim(), tk[29].trim(), tk[30].trim() ); 
				candidateInfoMap.put( tk[2].trim(), cinfo );

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	void readCandidateAndRawMarkCalulation(String file){

		try{
			BufferedReader br = new BufferedReader(new FileReader( new File(file) ) );

			/* false reading */
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			/* false reading end */

			String line = null;

			while( (line = br.readLine()) != null ){

				if( line.split(",").length <= 2 )
					continue;

				String options = br.readLine();
				String rtoken[] = line.split(",");
				String otoken[] = options.split(",");
				String rollNumber = rtoken[1].trim();
				String name = rtoken[2].trim();

				String paperCode = rtoken[9].trim().substring(0,2);

				Paper paper = paperMap.get( paperCode );

				Candidate candidate = null;

				if( paper != null ){

					rollNumber = rollNumber.substring(2);	
					String sessionId = rollNumber.substring(6,7);
					Session session = paper.sessionMap.get( sessionId );

					if( session != null ){

						candidate = new Candidate( rollNumber, name, sessionId, paperCode );
						CandidateInfo ci = candidateInfoMap.get( (paperCode+""+rollNumber).trim() );

						if( ci != null ){
							candidate.info = ci;
						}

						for(int i = 0, r = 11; i < session.listOfQuestions.size(); i++, r++){

							Response response = new Response( rtoken[r], otoken[r] );
							Question question = session.listOfQuestions.get(i);
							double mark = question.eval( response );
							candidate.responses.add(i, response );
							candidate.marks.add( mark );

							if( isAttempted( response ) &&  question.section.trim().length() > 0){
								candidate.sections.add( question.section );
							}

							candidate.rawMark += mark;
						}

						candidate.actualMark = candidate.rawMark;
						candidate.rawMark = Double.parseDouble( new DecimalFormat("#0.0#").format( candidate.rawMark ));

						if( candidate.rawMark <= 0.0d)
							candidate.rawMark = 0.0d;

						paper.listOfObtainedMarks.add(  candidate.rawMark );
						paper.listOfCandidate.add(  candidate );
						session.listOfObtainedMarks.add( candidate.rawMark );
						session.listOfCandidate.add( candidate );

						session.listOfActualMarks.add ( candidate.actualMark );
						paper.listOfActualMarks.add ( candidate.actualMark );
					}else{
						System.out.println("Seesion is null");	
						System.exit(0);
					}
				}else{
					System.out.println("Paper not found! "+line);
					System.exit(0);
				}
			}
		}catch(Exception e){
			System.out.println("Problem is reading file :"+file);
			e.printStackTrace();
		}

	}	

	void readConfig(String configFile){

		try{

			if( configFile != null ){
				BufferedReader br = new BufferedReader(new FileReader( new File(configFile) ) );
				String line = null;
				while( (line = br.readLine() ) != null ){
					String token[] = line.split(":");
					if( token[0].trim().equalsIgnoreCase("negative") ){
						Config.negative = token[1].trim();
					}else if( token[0].trim().equalsIgnoreCase("cancelled") ){
						Config.cancelled = token[1].trim();
					}else if( token[0].trim().equalsIgnoreCase("unattempted") ){
						Config.unattempted = token[1].trim();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}

	void print( boolean log ){

		Iterator it = paperMap.entrySet().iterator();
		if( candidateInfoMap.size() > 0)
			Print.info = true;

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.print( log );
		}
	}

	void read(String keyFile, String resFile, String configFile, String applicantFile){
		readConfig( configFile );
		readKey( keyFile );

		if( applicantFile != null ){
			readCandidateInfo( applicantFile );
		}	

		readCandidateAndRawMarkCalulation( resFile );
	}

	void process(){
		Iterator it = paperMap.entrySet().iterator();
		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.process();
		}
	}

	void analysis(){
		Iterator it = paperMap.entrySet().iterator();
		Analysis analysis = new Analysis(); 
		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();

			Iterator itr = paper.sessionMap.entrySet().iterator();
			while( itr.hasNext() ){
				Map.Entry tpairs = (Map.Entry) itr.next();
				Session session = ( Session ) tpairs.getValue();
				analysis.PaperAnalyis( session );	
			}
		}
	}

	public static void main(String[] args){
		try{
			boolean log = false;
			String resFile =  null;
			String keyFile = null;
			String applicantFile = null;
			String configFile = null;
			int i = 0;

			while( i < args.length ){

				if( args[i].equals("-k") ){

					keyFile =  args[i+1].trim();
					i++;

				}else if( args[i].equals("-an") ){

					Print.analysis = true;

				}else if( args[i].equals("-ap")  ){

					applicantFile = args[i+1];
					i++;

				}else if( args[i].equals("-r") ){

					resFile = args[i+1].trim();
					i++;

				}else if( args[i].equals("-c") ){

					configFile =  args[i+1].trim();
					i++;	

				}else if( args[i].equals("-l") ){
					log = true;

				}else if( args[i].equals("-a") ){

					Print.actual = true;

				}else if( args[i].equals("-d") ){

					Print.detail = true;

				}else if(  args[i].equals("-rv") ){

					Print.resultView = true;

				}else if( args[i].equals("-sv" ) ){

					Print.scoreView = true;
				}
				i++;
			}

			if( resFile == null || keyFile == null){

				System.out.println("Uses: -k <key-file> -r <response-file> -c[optional] <config-file> [ -ap <applicant-file> ]  -l -a -f");
			
				System.out.println("-l Printing of Question Deatils (Difficulty Level) ");
				System.out.println("-a Printing Actual Marks (Without Floor or Ceiling)");
				System.out.println("-d Detail Question wise marks for each applicant");
				System.out.println("-an Top 100 Analysis of question");
				System.out.println("-sv Candidate Score View");
				System.out.println("-rv Candidate result View");
				System.out.println("[optional attribute ] ");
				System.exit(0);
			}

			ResultProcessing rp = new ResultProcessing();

			rp.read( keyFile, resFile, configFile, applicantFile );

			rp.process();
			rp.print( log  );

			if( Print.analysis ){
				rp.analysis();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
