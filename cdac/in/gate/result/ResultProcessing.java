package cdac.in.gate.result;

import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
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
	public static String NATdelimeter = "to";
	public static String MuliAnsdelimeter = ";";
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
	static HashMap<String, String> categoryMap = new HashMap<String, String>();

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
		paperCodeMap.put("PE", "Petroleum Engineering");		
		paperCodeMap.put("EC", "Electronics and Communication Engineering");		
		paperCodeMap.put("TF", "Textile Engineering and Fibre Science");		
		paperCodeMap.put("EE", "Electrical Engineering");			 	
		paperCodeMap.put("XE", "Engineering Sciences"); 				
		paperCodeMap.put("EY", "Ecology and Evolution"); 				
		paperCodeMap.put("XL", "Life Sciences"); 					

		/*  Section Code Mapping */

		sectionCodeMap.put("GG-1", "Geology");
		sectionCodeMap.put("GP-1", "Geophysics");
		sectionCodeMap.put("GA",   "General Aptitude");

		sectionCodeMap.put("XE-A", "Engineering Mathematics (Compulsory)");
		sectionCodeMap.put("XE-B", "Fluid Mechanics");
		sectionCodeMap.put("XE-C", "Materials Science");
		sectionCodeMap.put("XE-D", "Solid Mechanics");
		sectionCodeMap.put("XE-E", "Thermodynamics");
		sectionCodeMap.put("XE-F", "Polymer Science and Engineering");
		sectionCodeMap.put("XE-G", "Food Technology");
		sectionCodeMap.put("XE-H", "Atmospheric and Oceanic Science");

		sectionCodeMap.put("XL-P", "Chemistry (Compulsory)");
		sectionCodeMap.put("XL-Q", "Biochemistry");
		sectionCodeMap.put("XL-R", "Botany");
		sectionCodeMap.put("XL-S", "Microbiology");
		sectionCodeMap.put("XL-T", "Zoology");
		sectionCodeMap.put("XL-U", "Food Technology");

		/* Category Mapping */

		categoryMap.put("1", "GEN");
		categoryMap.put("2", "OBC");
		categoryMap.put("3", "SC");
		categoryMap.put("4", "ST");
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

		String salt = "2017gAtE";
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
	boolean MTA;
	boolean MTN;

	int R;
	int NA;
	int W;
	int AT;

	double perR;
	double perW;
	double perNA;
	double perRAT;

	public boolean isMTA(){
		return MTA;
	}
	
	public boolean isMTN(){
		return MTN;
	}

	abstract double eval(Response response, Candidate candidate);
	abstract void printDifficulty();
	abstract void print();
	abstract String getAnswers();
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

	private List<String> answers;
	private static String options = "ABCD";
	//private boolean MTA;


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
		this.answers = new ArrayList<String>();

		this.MTA = false;
		this.MTN = false;

		if( answer.equalsIgnoreCase("can") ){

			this.isCancelled = true;

		}else if( answer.equals("MTA") ){

			this.MTA = true;
			this.mark = Double.parseDouble( mark );

		}else if( answer.equals("MTN") ){

			this.MTN = true;
			this.mark = 0.0d;

		}else{
			this.mark = Double.parseDouble( mark );

			String[] tokens = answer.split( Config.MuliAnsdelimeter );

			for(String token: tokens){

				if( token.trim().length() > 1){
					System.out.println("Master key has error (MCQ): "+answer+" QuestionId: "+Id+" Section :"+section);
					System.exit(0);
				}	
				this.answers.add ( token.trim() );
			}

			if( this.answers.size() <= 0 ){
				System.out.println("Master key has error (MCQ): "+answer+" QuestionId: "+Id+" Section :"+section);
				System.exit(0);
			}		

			if( Config.negative.equals("zero") ){
				this.negative = 0.0d;
			}else{ 
				this.negative = -1 * ( this.mark / Integer.parseInt( Config.negative ) );
				this.negative = Math.ceil( this.negative * 100  ) / 100;
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

	double eval( Response response, Candidate candidate ){

		try{	
			if( response == null ){
				System.err.println("1. Error response in MCQ( "+Id+" ) for "+candidate.rollNumber+": Options: <"+response.getOptions()+"> Answer: <"+response.getAnswer()+">");	
				System.exit(0);
			}

			if( this.MTA ){

				this.AT++;
				this.R++;
				return mark;

			}else if( this.MTN ){

				return 0.0d;
	
			}else if( response.getAnswer().equals("--") ){   
				this.NA++;
				return unattempted;

			}else if( options.indexOf( response.getAnswer().charAt(0) ) < 0 ){
				System.err.println("2. Error response in MCQ( "+Id+" ) for "+candidate.rollNumber+": Options: <"+response.getOptions()+"> Answer: <"+response.getAnswer()+">");	
				System.exit(0);

			}else if( this.answers.contains( response.getAnswer() ) ){
				this.AT++;
				this.R++;
				return mark;
			}else{
				this.AT++;
				this.W++;
				return negative;
			}

		}catch(Exception e){
			System.err.println("3. Error response in MCQ( "+Id+" ) for "+candidate.rollNumber+": Options: <"+response.getOptions()+"> Answer: <"+response.getAnswer()+">");	
			System.exit(0);
		}

		this.AT++;
		return this.invalidResponse;
	}

	void print(){

		System.out.print("[MCQ"+Id+", "+getAnswers()+", ");
		FP.printD(mark);
		System.out.print(", ");
		FP.printD(negative);
		System.out.print(", ");
		FP.printD(unattempted);
		System.out.print("]");
	}

	void printDifficulty(){
		System.out.println(this.Id+", "+this.section+", "+this.type()+", "+this.AT+", "+this.R+", "+this.W+", "+this.NA+", "+perR+", "+perW+", "+perNA+", "+perRAT+", "+DL);
	}

	String getDL(){
		return DL;
	}

	String getAnswers(){

		if( this.MTA ){
			return "MTA";
		}else if ( this.MTN ){
			return "MTN";
		}

		String tanswer = "";
		boolean flag = true;
		for(String answer: answers){
			if( flag ){
				flag = false;
				tanswer = answer; 	
			}else{
				tanswer += ";"+ answer; 	
			}
		}
		return tanswer;
	}

	String type(){
		return "MCQ";
	}
}

class Rang{

	private double lower;
	private double upper;

	Rang(double lower, double upper){

		if( lower == upper){
			this.lower = lower - (double) Math.pow(10, -6);
			this.upper = upper + (double) Math.pow(10, -6);	
		}else{
			this.lower = lower;
			this.upper = upper;
		}
	}

	boolean eval(double value){

		if( value >= lower && value <= upper )
			return true;
		else 
			return false;
	}

	double getLower(){
		return lower;	
	}

	double getUpper(){
		return upper;
	}

}

class RangeQuestion extends Question{

	private List<Rang> answers;

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
		this.R = 0;
		this.NA = 0;
		this.W = 0;
		this.AT = 0;
		this.perR = 0.0d;
		this.perW = 0.0d;
		this.perNA = 0.0d;
		this.perRAT = 0.0d;
		this.DL = null;

		this.MTA = false;
		this.MTN = false;

		this.answers = new ArrayList<Rang>();

		if( answer.equals("MTA") ){

			this.MTA = true;
			this.mark = Double.parseDouble( mark );

		}else if ( answer.equals("MTN") ){

			this.MTN = true;
			this.mark = 0.0d;

		}else{
			String []tokens = answer.split( Config.MuliAnsdelimeter );

			for(String token: tokens ){

				String[] tk = token.split( Config.NATdelimeter );

				if( tk.length != 2) {                                          
					System.err.println("Error in master key creation "+answer+" QuestionId :"+Id+" Section: "+section);
					System.exit(0);
				}	

				double lower = Double.parseDouble( tk[0].trim() );
				double upper = Double.parseDouble( tk[1].trim() );	
				
				if( lower <= upper ){
					this.answers.add( new Rang(lower, upper) );
				}else{
					System.err.println("Error in Key: "+Id+" Lower: "+lower+" Upper: "+upper);
					System.exit(0);
				}	
			}	

			if( this.answers.size() <= 0){
				System.err.println("Error in master key creation "+answer+" QuestionId :"+Id+" Section: "+section);
				System.exit(0);
			}

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

	double eval(Response response, Candidate candidate){

		if( response == null ){
			System.err.println("1. Error response in NAT( "+Id+" ) for "+candidate.rollNumber+": Options: <"+response.getOptions()+"> Answer: <"+response.getAnswer()+">");	
			System.exit(0);
		}

		if( this.MTA ){

			this.R++;
			this.AT++;
			return this.mark;

		}else if( this.MTN ){

			return 0.0d;

		}else if ( response.getAnswer().equals("--") && !response.getOptions().equals("--")){  
			System.err.println("2. Error response in NAT( "+Id+" ) for "+candidate.rollNumber+": Options: <"+response.getOptions()+"> Answer: <"+response.getAnswer()+">");	
			System.exit(0);
		}else if( response.getAnswer().equals("--") ){
			this.NA++;
			return this.unattempted;
		}

		try{
			double resp =  Double.parseDouble( response.getOptions() ); 

			for(Rang rang: this.answers){
				if( rang.eval( resp ) ){
					this.R++;
					this.AT++;
					return this.mark;
				}
			}

			this.AT++;
			this.W++;
			return this.negative;                 

		}catch(Exception e){
			System.err.println( "Exception in NAT("+Id+") for "+candidate.rollNumber+": <"+response.getOptions()+"> Action taken: <INVALID RESPONSE>");
			this.AT++;
			return this.invalidResponse;
		}	
	}

	String sanitizeNATResponse(String response)
	{
		response = response.trim();

		if(response.contains("http"))
		{
			response = response.replaceAll("http.*", "");
		}
		if(response.endsWith(".") && response.length() > 1)
		{
			response = response.substring(0, response.lastIndexOf("."));
		}
		if(response.endsWith("-") && response.length() > 1)
		{
			response = response.substring(0, response.lastIndexOf("-"));
		}

		if(".".equals(response))
		{
			response = "";
		}
		if("-".equals(response))
		{
			response = "";
		}

		return response;
	}

	void print(){

		System.out.print("[NAT"+Id+", ("+this.getAnswers()+"), ");
		FP.printD(mark);
		System.out.print(", ");
		FP.printD(negative);
		System.out.print(", ");
		FP.printD(unattempted);
		System.out.print("] ");
	}

	void printDifficulty(){
		System.out.println(this.Id+", "+this.section+", "+this.type()+", "+this.AT+", "+this.R+", "+this.W+", "+this.NA+", "+perR+", "+perW+", "+perNA+", "+perRAT+", "+DL);
	}

	String getAnswers(){

		if( this.MTA ){
			return "MTA";
		}	
		else if( this.MTN ){
			return "MTN";
		}	

		String tanswers = "";
		boolean flag = true;
		for(Rang rang: this.answers){
			if( flag ){
				tanswers = rang.getLower()+":"+rang.getUpper();
				flag = false;
			}else{
				tanswers += ";"+rang.getLower()+":"+rang.getUpper();
			}	
		}
		return tanswers.trim();	
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
	double totalMarks;

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
		this.totalMarks = 0.0d;

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

		/*
		   System.err.println("Mean: "+marks.length );
		   System.err.println("Mean (Sum) :"+ StdStats.sum(marks) );
		   System.err.println("Mean (mean) :"+ StdStats.mean(marks) );
		   System.err.println("-------------------------------------");
		   for(int i = 0; i < marks.length; i++){
		   System.err.println((i+1)+":"+ marks[i] );
		   }
		   System.err.println("-------------------------------------");

	        */			

		this.stdDev = StdStats.stddev( marks );
		this.mTBar = StdStats.mean( marks, 0, zeroPointOnePercent - 1 );
		this.mQ = this.mean + this.stdDev;	
		double [] amarks = StdStats.toArray( listOfActualMarks );
		this.maxRawScore = StdStats.max( amarks );
		this.minRawScore = StdStats.min( amarks );
	}

	void printDetails(){

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

	void printDifficulty(){


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
		}
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

		System.out.println("Q.No, Section, Q-type, Attempt, Right, Wrong, No-attempt, %Right, %Wrong, %No-attempt, %Right/Attempt, Dificulty-Level");

		for(Question question: this.listOfQuestions){
			question.printDifficulty();
		}
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

	int genCutOffGate;
	int obcCutOffGate;
	int sTsCPwDCutOffGate;

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
	int QPwDScB;
	int QPwDStB;

	int QPwDGen;
	int QPwDObc;

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

		this.sessionMap = new TreeMap<String, Session>();
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
		this.QScX = 0;
		this.QStX = 0;
		this.QPwDX = 0;

		this.QObcG = 0;
		this.QScG = 0;
		this.QStG = 0;
		this.QPwDG = 0;
		this.QGenObcB = 0;
		this.QSCObcB = 0;
		this.QSTObcB = 0; 
		this.QPwDObcB = 0;
		this.QPwDScB = 0;
		this.QPwDStB = 0;

		this.QPwDGen = 0;
		this.QPwDObc = 0;

		this.Q = 0;
		this.notQ = 0;

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

	void rankingMCQNAT(){

		double oldMarks = 99999.0d;  
		Collections.sort( listOfCandidate, new MCQMarksComp() );
		for(int i = 0, count = 1, rank = 1; i < listOfCandidate.size(); i++, count++){

			Candidate candidate = listOfCandidate.get(i);
			double marks = candidate.getMCQMarks();
			if( oldMarks > marks)   
				rank = count;
			oldMarks = marks;
			candidate.MCQRank = rank;
		}

		oldMarks = 99999.0d;  
		Collections.sort( listOfCandidate, new NATMarksComp() );
		for(int i = 0, count = 1, rank = 1; i < listOfCandidate.size(); i++, count++){
			Candidate candidate = listOfCandidate.get(i);
			double marks = candidate.getNATMarks();
			if( oldMarks > marks)   
				rank = count;
			oldMarks = marks;
			candidate.NATRank = rank;
		}	

		ranking();

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

				if( candidate.info.isPd && score >= sTsCPwDCutOff ){
					candidate.isQualified = true;
					QPwDX++;
				}

				if( score >= genCutOff )
				{
					candidate.isQualified = true;

					if( candidate.info.category.equals("1") && candidate.info.isPd ){
						QGen++;
						QPwDGen++;
					}else if( candidate.info.category.equals("1") ){
						QGen++;
						QGenX++;
					}else if( candidate.info.category.equals("2") && candidate.info.isPd ){
						QObc++;
						//QObcG++;
						QPwDObc++;
					}else if( candidate.info.category.equals("2") ){
						QObc++;
						QObcG++;
					}else if( candidate.info.category.equals("3") && candidate.info.isPd ){
						QSc++;
						//QScG++;
						QPwDScB++;
					}else if( candidate.info.category.equals("3") ){
						QSc++;
						QScG++;
					}else if( candidate.info.category.equals("4") && candidate.info.isPd ){
						QSt++;
						//QStG++;
						QPwDStB++;
					}else if( candidate.info.category.equals("4") ){
						QSt++;
						QStG++;
					}

					if( candidate.info.isPd ){
						QPwD++;
						QPwDG++;
					}
				}
				else if( score >= obcCutOff )
				{
					candidate.isQualified = true;

					if( candidate.info.category.equals("1") && candidate.info.isPd ){
						QGen++;
						QPwDGen++;
					}else if( candidate.info.category.equals("1") ){
						candidate.isQualified = false;
					}else if( candidate.info.category.equals("2") && candidate.info.isPd ){
						QObc++;
						QPwDObc++;
					}else if( candidate.info.category.equals("2") ){
						QObc++;
						QObcX++;
					}else if( candidate.info.category.equals("3") && candidate.info.isPd ){
						QSc++;
						QSCObcB++;
						QPwDScB++;
					}else if( candidate.info.category.equals("3") ){
						QSc++;
						QSCObcB++;
					}else if( candidate.info.category.equals("4") && candidate.info.isPd ){
						QSt++;
						QSTObcB++;
						QPwDStB++;
					}else if( candidate.info.category.equals("4") ){
						QSt++;
						QSTObcB++;
					}

					if( candidate.info.isPd ){
						QPwD++;
						QPwDObcB++;
					}
				}
				else if( score >= sTsCPwDCutOff )
				{
					candidate.isQualified = true;

					if( candidate.info.category.equals("1") && candidate.info.isPd ){
						QGen++;
						QPwDGen++;
					}else if( candidate.info.category.equals("1") ){
						candidate.isQualified = false;
					}else if( candidate.info.category.equals("2") && candidate.info.isPd ){
						QObc++;
						QPwDObc++;
					}else if( candidate.info.category.equals("2") ){
						candidate.isQualified = false;
					}else if( candidate.info.category.equals("3") && candidate.info.isPd ){
						QSc++;
						QPwDScB++;
					}else if( candidate.info.category.equals("3") ){
						QSc++;
						QScX++;
					}else if( candidate.info.category.equals("4") && candidate.info.isPd ){
						QSt++;
						QPwDStB++;
					}else if( candidate.info.category.equals("4") ){
						QSt++;
						QStX++;
					}

					if( candidate.info.isPd ){
						QPwD++;
					}
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

					Double actualMark =   Double.parseDouble( new DecimalFormat("#0.0#").format( candidate.actualMark ));

					candidate.normalisedMark = ( (mTgBar - mQg) / (sn.mTBar - sn.mQ ) ) * ( actualMark - sn.mQ ) + mQg;

					candidate.actualNormalisedMark = candidate.normalisedMark;
					candidate.normalisedMark = Double.parseDouble( new DecimalFormat("#0.0#").format( candidate.normalisedMark ));
					listOfActualNormalisedMarks.add( candidate.normalisedMark );

					if( candidate.normalisedMark >= 0)
						listOfNormalisedMarks.add( candidate.normalisedMark );
					else
						listOfNormalisedMarks.add( 0.0d );
				}else{

					/*
						Changed on 2017/02/23
						mQ round off 
						GATECutOff Double.parseDouble( df.format( mQ ) )
						candidate.actualGATEScore = ( SQ + ( ST - SQ ) * ( ( candidate.rawMark  - mQ ) / ( mTBar - mQ  ) ) );
					*/
		
					DecimalFormat df = new DecimalFormat("#0.0");
					double _mQ = Double.parseDouble( df.format( mQ ) );

					candidate.actualGATEScore = ( SQ + ( ST - SQ ) * ( ( candidate.rawMark  - _mQ ) / ( mTBar - _mQ  ) ) );
					candidate.GATEScore = (int) Math.round( candidate.actualGATEScore );

					if( candidate.GATEScore > 1000 ){
						candidate.GATEScore = 1000;
					}
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

					/*
						Changed on 2017/02/23
						mQ round off 
						GATECutOff Double.parseDouble( df.format( mQ ) )
						candidate.actualGATEScore = ( SQ + ( ST - SQ ) * ( ( candidate.rawMark  - mQ ) / ( mTBar - mQ  ) ) );

					*/
					
					DecimalFormat df = new DecimalFormat("#0.0");
					double _mQ = Double.parseDouble( df.format( mQ ) );

					candidate.actualGATEScore = ( SQ + ( ST - SQ ) * ( ( candidate.normalisedMark  - _mQ ) / ( mTBar - _mQ  ) ) );
					candidate.GATEScore = (int) Math.round( candidate.actualGATEScore );

					if( candidate.GATEScore > 1000 ){
						candidate.GATEScore = 1000;
					}

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

		DecimalFormat df = new DecimalFormat("#0.0");
		df.setRoundingMode( RoundingMode.DOWN );

		genCutOff = Double.parseDouble( df.format( mQ ) );
		obcCutOff = Double.parseDouble( df.format( genCutOff * 0.9 ) );
		sTsCPwDCutOff = Double.parseDouble( df.format( genCutOff * (2.0/3.0) ) );

		double _mQ =  Double.parseDouble( df.format( mQ ) );

		this.genCutOffGate = (int) Math.round( ( SQ + ( ST - SQ ) * ( ( genCutOff  - _mQ ) / ( mTBar - _mQ  ) ) ) );
		this.obcCutOffGate = (int) Math.round( ( SQ + ( ST - SQ ) * ( ( obcCutOff  - _mQ ) / ( mTBar - _mQ  ) ) ) );
		this.sTsCPwDCutOffGate = (int) Math.round( ( SQ + ( ST - SQ ) * ( ( sTsCPwDCutOff - _mQ ) / ( mTBar - _mQ  ) ) ) );

		System.err.println(paperCode+", "+genCutOffGate+", "+obcCutOffGate+", "+sTsCPwDCutOffGate);

		maxNorMarks = StdStats.max( marks );
		double []amarks = StdStats.toArray( this.listOfActualNormalisedMarks );
		minNorMarks = StdStats.min( amarks );
	}

	void calStats(){

		Collections.sort( listOfObtainedMarks, Collections.reverseOrder());

		double [] marks = StdStats.toArray( listOfObtainedMarks );
		zeroPointOnePercent = ( int ) Math.ceil( ( (double) marks.length) / 1000 );
		maxOf10OR01per = (int) Math.max( zeroPointOnePercent, 10 );

		mTgBar = StdStats.mean( marks, 0, zeroPointOnePercent - 1 );

		mTBar = StdStats.mean( marks, 0, maxOf10OR01per - 1 ); 

		mean = StdStats.mean( marks );
		stdDev = StdStats.stddev( marks );

		mQg = mean + stdDev;
		mQ = Math.max( mQg , 25 );

		DecimalFormat df = new DecimalFormat("#0.0");
		df.setRoundingMode( RoundingMode.DOWN );

		genCutOff = Double.parseDouble( df.format( mQ ) );
		obcCutOff = Double.parseDouble( df.format( genCutOff * 0.9 ) );
		sTsCPwDCutOff = Double.parseDouble( df.format( genCutOff * (2.0/3.0) ) );

		double _mQ= Double.parseDouble( df.format( mQ ) );

		genCutOffGate = (int) Math.round( ( SQ + ( ST - SQ ) * ( ( genCutOff  - _mQ ) / ( mTBar - _mQ  ) ) ) );
		obcCutOffGate = (int) Math.round( ( SQ + ( ST - SQ ) * ( ( obcCutOff  - _mQ ) / ( mTBar - _mQ  ) ) ) );
		sTsCPwDCutOffGate = (int) Math.round( ( SQ + ( ST - SQ ) * ( ( sTsCPwDCutOff - _mQ ) / ( mTBar - _mQ  ) ) ) );

		System.err.println(paperCode+", "+genCutOffGate+", "+obcCutOffGate+", "+sTsCPwDCutOffGate);

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

	void detailsHeader(){

		System.out.format("AIR, RollNumber, GATEScore, NormalisedMark, RawMark, genCutOff, obcCutOff, sTsCPwDCutOff");

		Paper paper = ResultProcessing.paperMap.get( paperCode );

		String sessionId = null;
		for(String session: paper.sessionMap.keySet()){
			sessionId = session;
		}	

                Session session = paper.sessionMap.get( sessionId );
		for(int i = 0; i < session.listOfQuestions.size(); i++){
			System.out.print(", Q"+(i+1));
		}
		System.out.println();
	}

	void printDifficulty(){
		for(String sessionId: sessionMap.keySet() ){
			Session session = sessionMap.get( sessionId );		
			session.printDetails();
			session.printDifficulty();
		}
	}

	void printMCQNATAnalysis(){
		rankingMCQNAT( );
		rankView();
	}

	void printTableResultView(){
		ranking();
		resultTableView();
	}

	void printResultView(){
		ranking();
		details();
		resultView();
	}
	
	void printScoreView(){
		ranking();
		details();
		scoreView();
	}

	void printApplicantDetails(){

		ranking();
		System.out.print("PaperCode, Rank, RegistrationId, Session, GATEScore, actualGATEScore, rawMarks, actualMark, normalisedMark, GENCutOff, OBCCuffOff, SC/ST/PwDCuttOff, Category, PwD-Status, isQualified, Sections");	
		String sessionId = null;
		for(String session: sessionMap.keySet()){
			sessionId = session;
		}	
                Session session = sessionMap.get( sessionId );
		for(int i = 0; i < session.listOfQuestions.size(); i++){
			System.out.print(", Q"+(i+1));
		}
		System.out.println();

		for(int i = 0; i < listOfCandidate.size(); i++){
			Candidate candidate = listOfCandidate.get(i);
			candidate.print();
		}
	}

	void details(){

		System.out.format(" ________________________________________%n");
		System.out.format("| Paper Code             | %-13s |%n", paperCode);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Total Candidates       | %-13d |%n", listOfObtainedMarks.size() );
		System.out.format("| Avg 0.1%% Candidate(G)  | %-13f |%n", mTgBar);
		System.out.format("| 0.1 %% Candidate(G)     | %-13d |%n", zeroPointOnePercent );
		System.out.format("| Max(10, 0.1%%) (G)      | %-13d |%n", maxOf10OR01per );
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
		System.out.format("| CutOff(GEN) GATEScore  | %-13d |%n", genCutOffGate );
		System.out.format("| CutOff(OBC) GATEScore  | %-13d |%n", obcCutOffGate );
		System.out.format("| CutOff(ST/SC/PwD) GATE | %-13d |%n", sTsCPwDCutOffGate );
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Exclusive GEN          | %-13d |%n", QGenX );
		System.out.format("| OBC with GEN cutoff    | %-13d |%n", QObcG);
		System.out.format("| SC with GEN cutoff     | %-13d |%n", QScG);
		System.out.format("| ST with GEN cutoff     | %-13d |%n", QStG);
		System.out.format("| PwD with GEN cutoff    | %-13d |%n", QPwDG);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Exclusive OBC          | %-13d |%n", QObcX );
		//				System.out.format("| GEN with OBC cutoff    | %-13d |%n", QGenObcB);
		System.out.format("| SC with OBC cutoff     | %-13d |%n", QSCObcB);
		System.out.format("| ST with OBC cutoff     | %-13d |%n", QSTObcB);
		System.out.format("| PwD with OBC cutoff    | %-13d |%n", QPwDObcB);
		System.out.format("|________________________|_______________|%n");
		//				System.out.format("| PwD with SC cutoff     | %-13d |%n", QPwDScB);
		//				System.out.format("| PwD with ST cutoff     | %-13d |%n", QPwDStB);
		System.out.format("| Exclusive SC           | %-13d |%n", QScX );
		System.out.format("| Exclusive ST           | %-13d |%n", QStX );
		System.out.format("| Exclusive PD           | %-13d |%n", QPwDX );
		System.out.format("|________________________|_______________|%n");
		System.out.format("| OBC-Qualified-X-PD     | %-13d |%n", (QObc - QPwDObc) );
		System.out.format("| SC-Qualified-X-PD      | %-13d |%n", (QSc - QPwDScB));
		System.out.format("| ST-Qualified-X-PD      | %-13d |%n", (QSt - QPwDStB));
		System.out.format("|________________________|_______________|%n");
		System.out.format("| GEN as PwD             | %-13d |%n", QPwDGen );
		System.out.format("| OBC as PwD             | %-13d |%n", QPwDObc );
		System.out.format("| SC as PwD              | %-13d |%n", QPwDScB );
		System.out.format("| ST as PwD              | %-13d |%n", QPwDStB );
		System.out.format("| Total PwD Qualified    | %-13d |%n", QPwD);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Scorecard Issu ed      | %-13d |%n", Q);
		System.out.format("| No Scorecard           | %-13d |%n", notQ);
		System.out.format("|________________________|_______________|%n");
		System.out.format("| Total GEN              | %-13d |%n", QGen );
		System.out.format("| Total OBC              | %-13d |%n", QObc );
		System.out.format("| Total SC               | %-13d |%n", QSc );
		System.out.format("| Total ST               | %-13d |%n", QSt );
		System.out.format("| Total PwD              | %-13d |%n", QPwD );
		System.out.format("| Total SC/ST/PwD        | %-13d |%n", (QSc + QSt + QPwD) );
		System.out.format("|________________________|_______________|%n");

		System.out.format("| %% Total GEN            | %-13.2f |%n", ( ( (double) QGen / (double) (Q + notQ ) )  * 100 ) );
		System.out.format("| %% Total OBC            | %-13.2f |%n", ( ( (double) ( QObc ) / (double) ( Q + notQ ) ) * 100)  );
		System.out.format("| %% Total SC             | %-13.2f |%n", ( ( (double) ( QSc ) / (double) ( Q + notQ ) ) * 100 ) );
		System.out.format("| %% Total ST             | %-13.2f |%n", ( ( (double) ( QSt ) / (double) ( Q + notQ ) ) * 100 ) );
		System.out.format("| %% Total PwD            | %-13.2f |%n", ( ( (double) ( QPwD ) / (double) ( Q + notQ ) ) * 100 ) );
		System.out.format("| %% Total SC/ST/PwD      | %-13.2f |%n", ( ( (double) ( ( QSc + QSt + QPwD ) ) / (double) ( Q + notQ ))*100) );
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
		System.out.format("|________________________________________|%n");
	}



	void resultView(){

		System.out.println("Registration_id, Enrollment_id, applicant_name, category_id, is_pd, Paper-Code, Paper-Name, Sec-1, Sec-2, Sec-3, Sec-4, RawMarks, Normalized-Marks, AIR, GATE-Score, is_qualified, genCutOff, obcCutOff, sTsCPwDCutOff, Gender");

		for(int i = 0; i < listOfCandidate.size(); i++){
			Candidate c = listOfCandidate.get(i);

			if( c.info == null ){
				System.out.println("Candidate information is msssing");
				return;
			}

			String NRMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualNormalisedMark ) ) + "";

			if( !multiSession )
				NRMark = "Not Applicable";
			System.out.print(c.rollNumber+", "+c.info.applicationId+", "+c.info.name+", "+CodeMapping.categoryMap.get(c.info.category)+", "+c.info.isPd+", "+c.paperCode+", "+CodeMapping.paperCodeMap.get( c.paperCode.trim() ));

			if( c.sections.size() > 0 ){

				Iterator<String> itr = c.sections.iterator();
				int count = 0;
				while( itr.hasNext() ){

					String section = itr.next();
					System.out.print(", "+section.substring(section.indexOf("-") + 1));	
					count++;
				}	

				if( count == 0){
					System.out.print(", , , , ");
				}else if( count == 1){
					System.out.print(", , , ");
				}else if( count == 2){
					System.out.print(", , ");
				}else if( count == 3){
					System.out.print(", ");
				}

			}
			else{
				System.out.print(", , , , ");
			}

			double rMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualMark ) );
			System.out.println(", "+rMark+", "+NRMark+", "+c.rank+", "+c.GATEScore+", "+c.isQualified+", "+genCutOff+", "+obcCutOff+", "+sTsCPwDCutOff+", "+c.info.gender);
		}
	}

	void scoreView(){

	        System.out.println("Registration_id, Enrollment_id, QRCode, GATE-Year,GATE-PaperCode, Paper-Name, Sec-1, Sec-2, Sec-3, Sec-4, Candidate-Name, Number-of-Candidate-appeared, RawMarks, NorMarks, GATEScore, AIR, category, PwD, Scribe, DigitalFingerPrint, Gen-cutoff, OBC-cutoff, SCSTPwD_Cutoff, Gen-GATEScoreCuttoff, OBC-GATEScoreCuttoff, SCSTPwD-GATEScoreCuttoff");

		for(int i = 0; i < listOfCandidate.size(); i++){

			Candidate c = listOfCandidate.get(i);
			if( !c.isQualified )
				continue;

			if( c.info == null ){
				System.out.println("Candidate information is msssing");
				return;
			}
			c.digitalFP = DigitalSignature.getDigitalSignature( c.paperCode+""+c.rollNumber+""+c.name+""+c.info.category+""+c.info.gender+""+c.rawMark+""+c.GATEScore+""+c.rank);
			c.qrCode = QRCodeGenerator.getCode(); 	

			String NRMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualNormalisedMark ) ) + "";
			if( !multiSession )
				NRMark = "Not Applicable";

			System.out.print(c.rollNumber+", "+c.info.applicationId+", "+c.qrCode.trim()+", 2017, "+c.paperCode+", "+CodeMapping.paperCodeMap.get(c.paperCode.trim()));


			if( c.sections.size() > 0 ){

				Iterator<String> itr = c.sections.iterator();
				int count = 0;
				while( itr.hasNext() ){
					String section = itr.next().trim();
					count++;
					System.out.print(", "+section);
				}

				if ( count == 0){
					System.out.print(", , , , ");
				}else if( count == 1){
					System.out.print(", , , ");
				}else if( count == 2){
					System.out.print(", , ");
				}else if( count == 3){
					System.out.print(", ");
				}

			}
			else{
				System.out.print(", , , , ");
			}

			double rMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualMark ) );

			System.out.println(", "+c.info.name+","+listOfCandidate.size()+","+rMark+","+NRMark+","+c.GATEScore+","+c.rank+","+CodeMapping.categoryMap.get(c.info.category)+","+c.info.isPd+","+c.info.scribe+","+c.digitalFP+","+genCutOff+","+obcCutOff+","+sTsCPwDCutOff+","+genCutOffGate+","+obcCutOffGate+","+sTsCPwDCutOffGate);

		}

	}

	void rankView(){

		System.out.println("RegistrationId, NormalizedMarks, GATE-Score, AIR, RawMarks, MCQ-Rank, MCQ-Mraks, NAT-Rank, NAT-Marks, MCQ-Positive, MCQ-Negative, MCQ Correct-Count, MCQ Negative-Count");

		for(int i = 0; i < listOfCandidate.size(); i++){

			Candidate c = listOfCandidate.get(i);

			if( c.info == null ){
				System.out.println("Candidate information is msssing");
				return;
			}

			double rMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualMark ) );
			double mcqMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.MCQMark ) );
			double natMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.NATMark ) );
			double MCQP = Double.parseDouble( new DecimalFormat("#0.0#").format( c.MCQPos ) );
			double MCQN = Double.parseDouble( new DecimalFormat("#0.0#").format( c.MCQNev ) );

			String NRMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualNormalisedMark ) )+"";
			if( !multiSession )
			     NRMark = "0.0";

			System.out.println(c.rollNumber+","+NRMark+","+c.GATEScore+","+c.rank+","+rMark+","+c.MCQRank+","+mcqMark+","+c.NATRank+","+natMark+","+MCQP+","+MCQN+","+c.MCQPosC+","+c.MCQNevC);
		}


	}	

	void resultTableView(){

		System.out.println("registration_id, application_id, raw_marks, normalized_marks, air, score, is_qualified, qual_marks_gen, qual_marks_obc, qual_marks_sc_st_pwd, Sections, qrcode, appeared_candidates");

		for(int i = 0; i < listOfCandidate.size(); i++){

			Candidate c = listOfCandidate.get(i);
			c.qrCode = QRCodeGenerator.getCode();

			if( c.info == null ){
				System.out.println("Candidate information is msssing");
				return;
			}

			double rMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualMark ) );
			String NRMark = Double.parseDouble( new DecimalFormat("#0.0#").format( c.actualNormalisedMark ) )+"";
			if( !multiSession )
				NRMark = "0.0";

			System.out.print(c.rollNumber+","+c.info.applicationId+","+rMark+","+NRMark+","+c.rank+","+c.GATEScore+","+c.isQualified+","+genCutOff+","+obcCutOff+","+sTsCPwDCutOff+",");

			String sections = "";

			if( c.sections.size() > 0 ){

				Iterator<String> itr = c.sections.iterator();

				int count = 0;

				while( itr.hasNext() ){
					String section = itr.next().trim();
					count++;
					if( "GP-1".equals(section ) )	
						sections += "2 "; 
					else
						sections += section.substring( section.indexOf("-") + 1 ) +" "; 
				}
				sections = sections.trim().replaceAll(" ", ":");
			}

			if( sections.trim().length() > 0){
				System.out.print(sections);
			}else{
				System.out.print("");
			}

			System.out.println(","+c.qrCode+","+listOfCandidate.size());
		}
	}
}

class CandidateInfo{

	String applicationId;
	String name;
	String parentName;
	String category;
	String gender;
	String dob;

	boolean scribe;	
	boolean isPd;

	CandidateInfo(String applicationId, String name, String parentName, String dob, String gender, String category, String isPd, String scribe ) {

		this.applicationId = applicationId;                 
		this.name = name;
		this.parentName = parentName;
		this.category = category;
		this.gender = gender;
		this.dob = dob;

		if( scribe.trim().equals("t"))
			this.scribe = true;	

		if( isPd.trim().equals("t"))
			this.isPd = true;
	}

	String print(){
		String out = applicationId+","+name+", "+parentName+", "+dob+", "+gender+", "+CodeMapping.categoryMap.get(category)+", "+isPd+", "+scribe;
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
	int MCQRank;
	int NATRank;
	double actualMark;	
	double rawMark;
	double MCQMark;
	double MCQPos;
	int MCQPosC;
	double MCQNev;
	int MCQNevC;
	double NATMark;
	double normalisedMark;
	double actualNormalisedMark;
	int GATEScore;
	double actualGATEScore;
	Map<String, Double> sectionWiseMarks;

	String digitalFP;
	String qrCode;	

	List<Double> marks;
	List<Response> responses;
	SortedSet<String> sections;

	Candidate(String rollNumber, String name, String sessionId, String paperCode){

		this.rollNumber = rollNumber;
		this.name = name;
		this.sessionId = sessionId;
		this.paperCode = paperCode;


		this.MCQPos = 0.0d;
		this.MCQNev = 0.0d;	
		this.MCQPosC = 0;
		this.MCQNevC = 0;	
		this.rawMark = 0.0d;
		this.MCQMark = 0.0d;
		this.NATMark = 0.0d;
		this.actualMark = 0.0d;
		this.actualGATEScore =  0.0d;
		this.GATEScore =  0;
		this.normalisedMark = 0.0d;
		this.rank = -1;
		this.MCQRank = -1;
		this.NATRank = -1;

		this.digitalFP = null;
		this.qrCode = null;

		marks = new ArrayList<Double>();
		responses = new ArrayList<Response>();
		sections = new TreeSet<String>();
		sectionWiseMarks = new TreeMap<String, Double>();
	}

	double getMCQPos(){
		return this.MCQPos;
	}

	double getMCQNev(){
		return this.MCQNev;
	}
	

	double getMCQMarks(){
		return this.MCQMark;
	}

	double getNATMarks(){
		return this.NATMark;
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

	void calculateRawMarks(){

		/*
		if(this.paperCode.equals("GG") ){	

			this.sections = new TreeSet<String>();
			this.rawMark = 0.0d;

			if( sectionWiseMarks.get( "GA" ) != null ){
				this.rawMark += sectionWiseMarks.get( "GA" );
			}

			if ( sectionWiseMarks.get( "GG-C" ) != null  ){	
				this.rawMark += sectionWiseMarks.get( "GG-C" );
			}

			this.sections.add("GA");
			this.sections.add("GG-C");

			if( sectionWiseMarks.get( "GG-1" ) != null && sectionWiseMarks.get( "GP-1" ) != null ){

				if( sectionWiseMarks.get( "GG-1" ) > sectionWiseMarks.get( "GP-1" ) ){
					this.rawMark += sectionWiseMarks.get( "GG-1" );
					this.sections.add("GG-1");
				}else{
					this.rawMark += sectionWiseMarks.get( "GP-1" );
					this.sections.add("GP-1");
				}

			}else if ( sectionWiseMarks.get( "GG-1" ) != null ){
				this.rawMark += sectionWiseMarks.get( "GG-1" );
				this.sections.add("GG-1");

			}else if ( sectionWiseMarks.get( "GP-1" ) != null ){
				this.rawMark += sectionWiseMarks.get( "GP-1" );
				this.sections.add("GP-1");
			}

		}else{

			this.rawMark = 0.0d;
			Set<String> sections = sectionWiseMarks.keySet();
			for(String section: sections){
				this.rawMark += sectionWiseMarks.get( section );	
			} 	
		}
		*/

		this.rawMark = 0.0d;
		Set<String> sections = sectionWiseMarks.keySet();
		for(String section: sections){
		    this.rawMark += sectionWiseMarks.get( section );	
		} 	
	}

	void print(){

		String paperCode = rollNumber.substring(0,2);
		String sessionId = rollNumber.substring(5,6);

		Paper paper = ResultProcessing.paperMap.get( paperCode );		
		Session session = paper.sessionMap.get( sessionId );

		System.out.print(paperCode+","+rank+","+rollNumber+","+sessionId+","+GATEScore+","+actualGATEScore+","+rawMark+","+actualMark+","+normalisedMark+","+paper.genCutOff+","+paper.obcCutOff+","+paper.sTsCPwDCutOff+","+CodeMapping.categoryMap.get( info.category )+","+info.isPd+","+isQualified );

		Set<String> sections = sectionWiseMarks.keySet();
		String sectionString = "";
		boolean first =  true;
		for(String section: sections){

			if( section.equals("GA") ){
				sectionString =  section+"("+Double.parseDouble( new DecimalFormat("#0.0#").format( sectionWiseMarks.get( section )))+") : "+sectionString;
			}	
			else if( first ){	
				sectionString += section+"("+Double.parseDouble( new DecimalFormat("#0.0#").format( sectionWiseMarks.get( section ) ) ) +")";
				first = false;
			}else{

				sectionString += ":"+section+"("+Double.parseDouble( new DecimalFormat("#0.0#").format( sectionWiseMarks.get( section ) ) ) +")";
			}
		}
		System.out.print(","+sectionString);

		String output = "";

		for(int i = 0; i < marks.size(); i++ ){

			Question question = session.listOfQuestions.get(i);

			String qw = "";

			if( question.type().equals("NAT") ){
				qw = FP.prints( marks.get(i) )+"|"+responses.get(i).options+"|"+question.getAnswers();
			}else{
				qw = FP.prints( marks.get(i) )+"|"+responses.get(i).answer+"|"+question.getAnswers();

			}
			if( i == marks.size() - 1)
				output += ""+qw;
			else	
				output += ""+qw+", ";
		}

		System.out.println(","+output);
	}
}

class MCQMarksComp implements Comparator<Candidate>{
	@Override
	public int compare(Candidate candidate1, Candidate candidate2) {
		if( candidate1.getMCQMarks() < candidate2.getMCQMarks() )
			return 1;
		if( candidate1.getMCQMarks() > candidate2.getMCQMarks() )
			return -1;

		long d1 = Double.doubleToLongBits(candidate1.getMCQMarks());
		long d2 = Double.doubleToLongBits(candidate2.getMCQMarks());

		return (d1 == d2 ?  0 : // Values are equal
				(d1 < d2 ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
				 1));                          // (0.0, -0.0) or (NaN, !NaN)
	}
}

class NATMarksComp implements Comparator<Candidate>{
	@Override
	public int compare(Candidate candidate1, Candidate candidate2) {
		if( candidate1.getNATMarks() < candidate2.getNATMarks() )
			return 1;
		if( candidate1.getNATMarks() > candidate2.getNATMarks() )
			return -1;

		long d1 = Double.doubleToLongBits(candidate1.getNATMarks());
		long d2 = Double.doubleToLongBits(candidate2.getNATMarks());

		return (d1 == d2 ?  0 : // Values are equal
				(d1 < d2 ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
				 1));                          // (0.0, -0.0) or (NaN, !NaN)
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

	static Map<String, Paper> paperMap;
	static Map<String, CandidateInfo> candidateInfoMap;

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

				String paperCode = typeToken[1].trim().substring(0,2);
				String sessionId = typeToken[2].trim();

				Paper paper = paperMap.get( paperCode );

				if( paper == null )
					paper = new Paper( paperCode );

				for(int i = 3, qn = 0; i < typeToken.length; i++, qn++ ){

					Question question  =  null;					
					String questionId = "Q"+(i-2);

					if( (i-2) < 10)
						questionId = "Q0"+(i-2);

					if( typeToken[i].equals("MCQ")  ){
						question = new MultipalChocie(questionId, sectionToken[i].trim(), keyToken[i].trim(), marksToken[i].trim() );
					}else if( typeToken[i].equals("NAT")  ){
						question = new RangeQuestion(questionId, sectionToken[i].trim(), keyToken[i].trim(), marksToken[i].trim() );
					}

					if( question == null ){
						System.out.println( typeToken[i] );
						System.out.println("Question is not proper "+ questionKey);
						System.exit(0);
					}

					paper.addQuestion( qn, question, sessionId );
					paper.sessionMap.get(sessionId).totalMarks += question.mark;
				}

				if( paper == null){

					System.out.println("Paper is not proper "+ questionKey);
					System.exit(0);
				}

				System.err.println("PaperCode:"+paperCode+", Session:"+sessionId+", Total:"+paper.sessionMap.get(sessionId).totalMarks);

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

	void readCandidateInfo(String filename, boolean header){
		try{

			BufferedReader br = new BufferedReader(new FileReader( new File(filename) ) );
			String line = null;

			while( ( line = br.readLine() ) != null ){

				if( header ){
					header = false;
					continue;
				}

				String[] tk = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				CandidateInfo cinfo = new CandidateInfo( tk[1].trim(), tk[2].trim(), tk[3].trim(), tk[4].trim(), tk[5].trim(), tk[6].trim(), tk[7].trim(), tk[8].trim() ); 
				candidateInfoMap.put( tk[0].trim(), cinfo );

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	void readCandidateAndRawMarkCalulation(String file){

		String line = null;
		String options = null;

		try{
			BufferedReader br = new BufferedReader(new FileReader( new File(file) ) );

			/* Reading metadata BEGIN */
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			/* Reading metadata END */

			while( (line = br.readLine()) != null ){

				if( line.split(",").length <= 2 )
					continue;

				options = br.readLine();

				//System.out.println("1> "+ line );
				//System.out.println("2> "+ options );

				String rtoken[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				String otoken[] = options.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				String rollNumber = rtoken[1].trim();
				String name = rtoken[2].trim();

				String paperCode = rollNumber.substring(0,2);

				Paper paper = paperMap.get( paperCode );

				Candidate candidate = null;

				if( paper != null ){

					String sessionId = rollNumber.substring(5,6);

					Session session = paper.sessionMap.get( sessionId );

					if( session != null ){

						candidate = new Candidate( rollNumber, name, sessionId, paperCode );
						CandidateInfo ci = candidateInfoMap.get( rollNumber );

						if( ci != null ){
							candidate.info = ci;

						}

						for(int i = 0, r = 12; i < session.listOfQuestions.size(); i++, r++){

							Response response = new Response( rtoken[r], otoken[r] );
							Question question = session.listOfQuestions.get(i);
							double mark = question.eval( response, candidate );

							candidate.responses.add(i, response );
							candidate.marks.add( mark );

							if( question.isMTA() || isAttempted( response ) ) { 

								Double marks = candidate.sectionWiseMarks.get( question.section );	
								if( marks == null){
									marks = new Double(0);
								}
								marks += mark;
								candidate.sectionWiseMarks.put( question.section, marks );	
								candidate.sections.add( question.section );
							}

							candidate.rawMark += mark;

							if( question.type().equals("MCQ") ){
								candidate.MCQMark += mark;
								if( mark > 0){
									candidate.MCQPos += mark;
									candidate.MCQPosC++;
									
								}else if ( mark < 0) {

									candidate.MCQNev += Math.abs( mark );
									candidate.MCQNevC++;
								}
								
							}else if( question.type().equals("NAT") ){
								candidate.NATMark += mark;
							}	

						}

						candidate.calculateRawMarks();
					
						/*	
						if( !paper.paperCode.equals("GG") && !paper.paperCode.equals("XE") && !paper.paperCode.equals("XL") ){

							candidate.rawMark = ( (candidate.rawMark / 100 ) * session.totalMarks );
						}
						*/

						candidate.actualMark = candidate.rawMark;

						candidate.rawMark = Double.parseDouble(new DecimalFormat("#0.0#").format(candidate.rawMark ));
  						candidate.MCQMark = Double.parseDouble(new DecimalFormat("#0.0#").format(candidate.MCQMark )); 
						candidate.NATMark = Double.parseDouble(new DecimalFormat("#0.0#").format(candidate.NATMark )); 

						if( candidate.rawMark <= 0.0d)
							candidate.rawMark = 0.0d;

						paper.listOfObtainedMarks.add(  candidate.rawMark );
						paper.listOfCandidate.add(  candidate );
						session.listOfObtainedMarks.add( candidate.rawMark );
						session.listOfCandidate.add( candidate );

						session.listOfActualMarks.add ( candidate.actualMark );
						paper.listOfActualMarks.add ( candidate.actualMark );
					}else{
						System.out.println("Session is null!");	
						System.out.println(line);
						System.out.println(options);
						System.exit(0);
					}
				}else{
					System.out.println("Paper not found! "+line);
					System.out.println("Paper Code: "+paperCode);
					System.out.println("Paper options: "+options);
					System.exit(0);
				}
			}
		}catch(Exception e){

			System.out.println("Problem is reading file :"+file);
			System.err.println("1> "+ line );
			System.err.println("2> "+ options );
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

	void printMCQNATAnalysis(){

		Iterator it = paperMap.entrySet().iterator();
		if( candidateInfoMap.size() > 0)
			Print.info = true;

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.printMCQNATAnalysis();
		}
	}

	void printDifficulty(){

		Iterator it = paperMap.entrySet().iterator();
		if( candidateInfoMap.size() > 0)
			Print.info = true;

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.printDifficulty();
		}
	}

	void printResultView(){
		Iterator it = paperMap.entrySet().iterator();
		if( candidateInfoMap.size() > 0)
			Print.info = true;

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.printResultView();
		}
	}

	void printTableResultView(){
		Iterator it = paperMap.entrySet().iterator();
		if( candidateInfoMap.size() > 0)
			Print.info = true;

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.printTableResultView();
		}
	}

	void printScoreView(){
		Iterator it = paperMap.entrySet().iterator();
		if( candidateInfoMap.size() > 0)
			Print.info = true;

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.printScoreView();
		}
	}

	void printApplicantDetails(){
		Iterator it = paperMap.entrySet().iterator();
		if( candidateInfoMap.size() > 0)
			Print.info = true;

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.printApplicantDetails();
		}
	}

	void read(String keyFile, String resFile, String configFile, String applicantFile){

		readConfig( configFile );
		readKey( keyFile );

		if( applicantFile != null ){
			readCandidateInfo( applicantFile, true );
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


	void questionAnalysis( int view ){
		Iterator it = paperMap.entrySet().iterator();
		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.ranking();
			Iterator itr = paper.sessionMap.entrySet().iterator();
			Analysis analysis = null; 
			while( itr.hasNext() ){

				Map.Entry tpairs = (Map.Entry) itr.next();
				Session session = ( Session ) tpairs.getValue();
				analysis = new Analysis(); 
				analysis.PaperAnalyis( session, view );	
			}
		}
	}

	void sectionAnalysis(Session session, Map<String, SectionWiseCount> sectionAnalysis, Paper paper){

		for(Candidate candidate: session.listOfCandidate ){

			for(String section: candidate.sections ){

				SectionWiseCount swc = sectionAnalysis.get( section );
				if( swc == null){
					swc = new SectionWiseCount( section );		
				}
				swc.counts.appOver++;	

				if( candidate.isQualified ){

					swc.counts.Qtot++;	

					if( "1".equals( candidate.info.category ) || "GEN".equals( candidate.info.category )  ){

						swc.counts.appGen++;		
						swc.counts.QGen++;		
						if( candidate.info.isPd ){
							swc.counts.QPD++;
						}

					}else if( "2".equals( candidate.info.category ) || "OBC".equals( candidate.info.category ) ){

						swc.counts.appObc++;
						swc.counts.QObc++;
						if( candidate.rawMark >= paper.genCutOff  || candidate.normalisedMark > paper.genCutOff )
							swc.counts.QGenObc++;

					}else if( "3".equals( candidate.info.category ) || "SC".equals( candidate.info.category ) ){

						swc.counts.appSc++;
						swc.counts.QSc++;
						if( candidate.rawMark >= paper.genCutOff  || candidate.normalisedMark > paper.genCutOff )
							swc.counts.QGenSc++;

					}else if( "4".equals( candidate.info.category ) || "ST".equals( candidate.info.category ) ){

						swc.counts.appSt++;
						swc.counts.QSt++;
						if( candidate.rawMark >= paper.genCutOff  || candidate.normalisedMark > paper.genCutOff )
							swc.counts.QGenSt++;
					}

				}else{
					if( "1".equals( candidate.info.category ) || "GEN".equals( candidate.info.category ) ){
						swc.counts.appGen++;		

					}else if( "2".equals( candidate.info.category ) || "OBC".equals( candidate.info.category ) ){
						swc.counts.appObc++;

					}else if( "3".equals( candidate.info.category ) || "SC".equals( candidate.info.category ) ){
						swc.counts.appSc++;

					}else if( "4".equals( candidate.info.category ) || "ST".equals( candidate.info.category ) ){
						swc.counts.appSt++;
					}
				}

				sectionAnalysis.put( section, swc );
			}
		}
	}

	void sectionAnalysis(){

		Iterator it = paperMap.entrySet().iterator();
		Map<String, SectionWiseCount> sectionAnalysis =  new TreeMap<String, SectionWiseCount>();

		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			Paper paper = (Paper) pairs.getValue();
			paper.ranking();
			Iterator itr = paper.sessionMap.entrySet().iterator();
			while( itr.hasNext() ){
				Map.Entry tpairs = (Map.Entry) itr.next();
				Session session = ( Session ) tpairs.getValue();
				sectionAnalysis( session, sectionAnalysis, paper );	
			}
		}

		Counts.header();
		for(String section: sectionAnalysis.keySet() ){
			sectionAnalysis.get( section ).print();
		}

	}

	public static void main(String[] args){

		try{

			String resFile =  null;
			String keyFile = null;
			String applicantFile = null;
			String configFile = null;

			boolean sectionAnalysis = false;	
			boolean difficulty = false;
			boolean resultView = false;
			boolean tableResultView = false;
			boolean mcqnat = false;
			boolean scoreView = false;
			boolean questionAnalysis = false;
			boolean detailsView = false;
			int view = 1;
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
				}else if( args[i].equals("-dl") ){
					difficulty = true;
				}else if( args[i].equals("-dv") ){
					detailsView = true;
				}else if ( args[i].equals("-qa" ) ) {
					questionAnalysis = true;
					try{
						view = Integer.parseInt( args[i+1].trim() );
						i++;
					}catch(Exception e){
					
					}
	
				}else if(  args[i].equals("-rv") ){
					resultView = true;
				}else if( args[i].equals("-sv" ) ){
					scoreView = true;
				}else if ( args[i].equals("-rt") ){
					tableResultView = true;
				}else if ( args[i].equals("-sa")  ){
					sectionAnalysis = true;
				}else if( args[i].equals("-qn") ){
					mcqnat = true;
				}			

				i++;
			}

			if( resFile == null || keyFile == null){

				System.out.println("Uses: -k <key-file> -r <response-file> -c[optional] <config-file> [ -ap <applicant-file> ]  -l -a -f");

				System.out.println("-dl Printing of Question Deatils (Difficulty Level) ");
				System.out.println("-dv Detail Question wise marks for each applicant");
				System.out.println("-qa [1 - view1, 2 - view2]Top 100 Analysis of question");
				System.out.println("-sv Candidate Score View");
				System.out.println("-rv Candidate result View");
				System.out.println("-rt Result Table View");
				System.out.println("-sa Section Analysis View");
				System.out.println("-qn MCQ/NAT based ranking view");
				System.exit(0);
			}

			ResultProcessing rp = new ResultProcessing();
			rp.read( keyFile, resFile, configFile, applicantFile );
			rp.process();

			if( sectionAnalysis ){	
				rp.sectionAnalysis();
			}else if( difficulty ) {
				rp.printDifficulty();
			}else if ( questionAnalysis ){
				rp.questionAnalysis( view );
			}else if ( resultView ){
				rp.printResultView();
			}else if ( scoreView ){
				rp.printScoreView();
			}else if ( tableResultView ){
				rp.printTableResultView();
			}else if ( mcqnat ) {
				rp.printMCQNATAnalysis();
			}else if ( detailsView ){
				rp.printApplicantDetails();
			}


		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
