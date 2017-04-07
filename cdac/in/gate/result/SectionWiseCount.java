package cdac.in.gate.result;


/**
 *  
 * This class contains the configuration parameter for the 
 * Overall result processing program. 
 *
 * @author Chandra Shekhar
 * @version 0.1 	
 * @date 12/04/2016
 *
 **/
 
class Counts{
	int appGen;
	int QGen;
	int QPD;
	int appObc;
	int QGenObc;
	int QObc;
	int appSc;
	int QGenSc;
	int QSc;
	int appSt;
	int QGenSt;
	int QSt;
	int appOver;
	int Qtot;
	double Qper;

	Counts(){

	}

	static void header(){

		System.out.println("          , , Genral, , , OBC , , , SC , ,  , ST , , , Over All, ");
		System.out.println("SctionName, App, QGen, QPD, App., QGen, QOBC, App., QGen, QSC, App., QGen, QST, App., Qtot, Qual%%");

	}

	void print(String name){
		Qper = (double) ( ((double) Qtot / (double )appOver) * 100 );	
		System.out.println(name+", "+appGen+", "+QGen+", "+QPD+", "+appObc+", "+QGenObc+", "+QObc+", "+appSc+", "+QGenSc+", "+QSc+", "+appSt+", "+QGenSt+", "+QSt+", "+appOver+", "+Qtot+", "+String.format("%.2f", Qper));
	}	

}

public class SectionWiseCount{

	String name;
	Counts counts;

	SectionWiseCount(String name){
		this.name = name;
		this.counts = new Counts();
	}

	void print(){
		counts.print( name );
	}
}
