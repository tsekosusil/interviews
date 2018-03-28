package main;

import Generator.MessageGenerator;
import scala.collection.immutable.*;
public class Test {
	
	public static void main(String args[]){
		Thread t = new Thread(new MessageGenerator());
		Thread t2 = new Thread(new MessageGenerator());
		t.start();
		t2.start();
		
		
	}

}
