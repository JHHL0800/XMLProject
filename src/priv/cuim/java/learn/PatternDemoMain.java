package priv.cuim.java.learn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternDemoMain {
  public static void main(String[] arrayStr){
	  
	  String str1= "This order was placed for QT3000! OK?";
	  String pattern="(\\D*)(\\d+)(.*)";
	  Pattern pattInst=Pattern.compile(pattern);
	  
	  Matcher matcher=pattInst.matcher(str1);
	  
	 
	  
	
		  System.out.println(Pattern.matches(pattern, str1));
		
	  
	  
  }
}
