package com.ibm.compbio.misc;
import com.ibm.compbio.seqalign.*;;
public class test {  
   public static void main(String[] args) {  
       String s1 = "AACCTATAGCT";  
       String s2 = "GCGATATA";  
       SmithWaterman sw = new SmithWaterman(s1, s2);  
       sw.getScoreTable();  
       String[] s = (String[]) sw.getTraceback();  
       for (String str : s)  
           System.out.println(str);  
//       NeedlemanWunsch nw = new NeedlemanWunsch(s1, s2);  
//       nw.getScoreTable();  
//       s = (String[]) nw.getTraceback();  
//       for (String str : s)  
//           System.out.println(str);  
   }  
}  
