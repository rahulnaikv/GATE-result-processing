package cdac.in.gate.result;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.text.DecimalFormat;

/**
 *@author Chandra Shekhar
 *@email shekhar@cdac.in
 *@date 08/02/2016
 *
 */

class QuestionReport{

	Question question;
	String sessionId;
	int notAttempt;	
	int attempt;
	int correct;
	int wrong;
	boolean flag;

	List<String> answers; 

	QuestionReport(Question question){

		this.question = question;
		this.sessionId = null;
		answers = new ArrayList<String>();
		attempt = 0;
		notAttempt = 0;
		correct = 0;
		wrong = 0;
	}	

	static void view1header(){
		System.out.println("Question No , Total Attempt(top 100), Correct Attempt, Correct(%), Wong Attempt, Wrong(%), Correct Answer, Actual Attempts (out, attempt, %)... ");
	}

	void view1(){


		Map<String, Integer> answersMap = new TreeMap<String, Integer>();

		for(int i = 0; i < answers.size(); i++ ){
			Integer n = (Integer) answersMap.get( answers.get(i) );
			if( n != null){
				n = new Integer( n.intValue() + 1 );
				answersMap.put( answers.get(i), n);
			}
			else{
				answersMap.put( answers.get(i), new Integer("1"));
			}
		}

		if ( (wrong > correct) || (attempt < 30) ){

			DecimalFormat formatter = new DecimalFormat("00.00");
			String corrper = formatter.format((float)(( (float) correct / attempt ) * 100));
			String wronper = formatter.format((float)(( (float) wrong/attempt ) * 100 ));
			System.out.print(question.Id+", "+attempt+", "+correct+", "+ corrper+"%, "+wrong+", "+wronper+"%, "+question.getAnswers());

			Iterator it = answersMap.entrySet().iterator();

			while ( it.hasNext() ) {
				Map.Entry pairs = (Map.Entry)it.next();
				String o = (String) pairs.getKey();
				o = String.format("%s",o );
				int c = ((Integer) pairs.getValue()).intValue();
				String output = formatter.format((float) ( ( (float) c / attempt ) * 100 ));
				System.out.print(", "+o+", "+c+", "+output+" %");
			}
			System.out.println();
		}	

	}

	void view2(){

		Map<String, Integer> answersMap = new TreeMap<String, Integer>();

		for(int i = 0; i < answers.size(); i++ ){
			Integer n = (Integer) answersMap.get( answers.get(i) );
			if( n != null){
				n = new Integer( n.intValue() + 1 );
				answersMap.put( answers.get(i), n);
			}
			else{
				answersMap.put( answers.get(i), new Integer("1"));
			}
		}

		if( (wrong >  correct) || (attempt < 30 ) ){

			Iterator it = answersMap.entrySet().iterator();
			String option = null;
			Integer count = 0 ;

			String answer = String.format("%0$-17s", question.getAnswers() );
			DecimalFormat formatter = new DecimalFormat("00.00");
			String corrper = formatter.format((float)(( (float) correct / attempt ) * 100));
			String wronper = formatter.format((float)(( (float) wrong/attempt ) * 100 ));

			System.out.format(" ___________________________________\n");
			System.out.format("| Question No   | %-4s              |\n", question.Id );
			System.out.format("| Qn  section   | %-4s              |\n", question.section );
			System.out.format("| Total Attempt | %-4d              |\n", attempt );
			System.out.printf("| Correct       | %-4d(%s%%)      |\n",correct, corrper );
			System.out.printf("| Wrong         | %-4d(%s%%)      |\n",wrong, wronper );
			System.out.format("| Answer        | %s |\n", answer );
			System.out.format("|___________________________________|\n");
			System.out.format("| OPTION        | COUNT             |\n");
			System.out.format("|_______________|___________________|\n");

			while ( it.hasNext() ) {
				Map.Entry pairs = (Map.Entry)it.next();
				String o = (String) pairs.getKey();
				o = String.format("%0$-13s",o );
				int c = ((Integer) pairs.getValue()).intValue();
				String output = formatter.format((float) ( ( (float) c / attempt ) * 100 ));
				System.out.format("| %s |%-4d(%s%%)       |\n",o, c, output );
			}

			System.out.format("|_______________|___________________|\n");
			System.out.println();
		}
	}
}

public class Analysis{	

	Map<Integer, QuestionReport> qReports = new TreeMap<Integer, QuestionReport>();

	void PaperAnalyis(Session session, int view){

		Collections.sort( session.listOfCandidate, new RawMarksComp() );
		System.out.println();
		System.out.println("[ Session "+session.id+" Question Analysis ]");

		for(int i = 0; i < 100; i++){

			Candidate can = session.listOfCandidate.get(i);

			for(int j = 0; j < can.responses.size(); j++){
				Response response =  can.responses.get(j);	
				Question question = session.listOfQuestions.get(j);
				Integer key = new Integer( question.Id.substring(1) );
				QuestionReport report = qReports.get( key );

				if( report == null){
					report = new QuestionReport( question );
					report.sessionId = session.id;
				}

				if( response.answer.equals("--") ){
					report.notAttempt++;
				}else{
					report.attempt++;
					if( question.eval( response, can ) > 0){
						report.correct++;
					}else{
						report.wrong++;
					}

					if( question.type().equals("NAT") )	
						report.answers.add( response.options );
					else
						report.answers.add( response.answer );
				}
				qReports.put( key, report );
			}
		}
		print( view );
	}		

	void print(int view){

		if( view == 1){
			QuestionReport.view1header();
		}

		Iterator it = qReports.entrySet().iterator();
		while ( it.hasNext() ) {
			Map.Entry pairs = (Map.Entry)it.next();
			QuestionReport qr = (QuestionReport) pairs.getValue();
			if( view == 1)
				qr.view1();
			else
				qr.view2();
		}      
	}
}
