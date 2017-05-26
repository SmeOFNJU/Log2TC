package handleOntology;

import java.util.*;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Lin;
import edu.sussex.nlp.jws.Resnik;

public class CalSentenceSimi {

	private  JWS ws;
//	public static void main(String args[]){
//		String s1="Dogs are common";
//		String s2="They are common dogs";
//		ArrayList<String> reT=genT(s1,s2);
//		String dir = "C:\\Program Files (x86)\\WordNet";
//		ws = new JWS(dir, "2.1");
//		ArrayList<Double> V=genVect(s1,reT);
//		ArrayList<Double> V0=genVect(s2,reT);
//		Double contentSimi=myCosSimi(V,V0);//语义相似度
//		ArrayList<Integer> V1=genOrderV(s1,reT);
//		ArrayList<Integer> V2=genOrderV(s2,reT);
//		Double orderSimi=calOrderSimi(V1,V2);//词序相似度
//		Double factor=0.9;
//		System.out.println(factor*contentSimi+(1-factor)*orderSimi);
//		
//	}
	public double genSimi(String s1,String s2){
		if(s2.isEmpty())
			return 1;
		ArrayList<String> reT=genT(s1,s2);
		String dir = "C:\\Program Files (x86)\\WordNet";
		ws = new JWS(dir, "2.1");
		ArrayList<Double> V=genVect(s1,reT);
		ArrayList<Double> V0=genVect(s2,reT);
		Double contentSimi=myCosSimi(V,V0);//语义相似度
		ArrayList<Integer> V1=genOrderV(s1,reT);
		ArrayList<Integer> V2=genOrderV(s2,reT);
		Double orderSimi=calOrderSimi(V1,V2);//词序相似度
		Double factor=0.9;
		return factor*contentSimi+(1-factor)*orderSimi;
		
	}
	
	//生成联合词集T
	public  ArrayList<String> genT(String T1,String T2){
		ArrayList<String> reT=new ArrayList<String>();
		String[] source1=T1.split(" ");
		String[] source2=T2.split(" ");
		for (int i = 0; i < source1.length; i++) {
			if(!reT.contains(source1[i]))
				reT.add(source1[i]);
        }
		for (int i = 0; i < source2.length; i++) {
			if(!reT.contains(source2[i]))
				reT.add(source2[i]);
        }
		return reT;
	}
	//生成语句向量
	public  ArrayList<Double> genVect(String T,ArrayList<String> reT){
		ArrayList<Double> reV=new ArrayList<Double>();
		String[] source=T.split(" ");
		ArrayList<String> mySource=new ArrayList<String>();
		for (int i = 0; i < source.length; i++) {
			mySource.add(source[i]);	
        }
		for(Iterator<String> iterator = reT.iterator();iterator.hasNext();){
			String curW=iterator.next();
			if(mySource.contains(curW))
				reV.add(1.0);
			else{
				reV.add(maxSimi(source,curW));
			}
			
		}
		return reV;
	}
	//获取词语最高相似度
	public  Double maxSimi(String[] s, String w){
		//Lin lin = ws.getLin();//使用Lin方法
		Resnik res=ws.getResnik();
		Double tmpMAX=0.0;
		for(int i=0;i<s.length;i++){
			Double score=res.max(w, s[i], "n");
			if(score > tmpMAX)
				tmpMAX=score;
		}
		if(tmpMAX<0.2)//设置阈值
			tmpMAX=0.0;
		return tmpMAX;
	}
	
	//计算余弦相似度
	public  Double myCosSimi(ArrayList<Double> V1,ArrayList<Double> V2){
		int size=V1.size();
		Double reDian=0.0;//点乘结果
		Double sumV1=0.0;//V1平方和
		Double sumV2=0.0;//V2平方和
		for(int i=0;i<size;i++){
			reDian=reDian+V1.get(i)*V2.get(i);
			sumV1=sumV1+V1.get(i)*V1.get(i);
			sumV2=sumV2+V2.get(i)*V2.get(i);
		}
		
		
		return reDian/(Math.sqrt(sumV1)*Math.sqrt(sumV2));
	}
	
	//获取词序向量
	public  ArrayList<Integer> genOrderV(String T,ArrayList<String> reT){
		ArrayList<Integer> orderV=new ArrayList<Integer>();
		String[] source=T.split(" ");
		for(int i=0;i<reT.size();i++){
			String curW=reT.get(i);
			orderV.add(maxNum(source,curW));
			
		}
		return orderV;
	}
	//获取词语最高相似度的序号
	public  Integer maxNum(String[] s, String w){
		//Lin lin = ws.getLin();//使用Lin方法
		Resnik res=ws.getResnik();
		Double tmpMAX=0.0;
		Integer num=0;
		for(int i=0;i<s.length;i++){
			if(w.equals(s[i])){
				num=i+1;
				return num;
			}
			Double score=res.max(w, s[i], "n");
			if(score > tmpMAX){
				tmpMAX=score;
				num=i+1;
			}
		}
		if(tmpMAX<0.2)//设置阈值
			num=0;
		return num;
	}
	//计算词序相似度
	public  Double calOrderSimi(ArrayList<Integer> V1,ArrayList<Integer> V2){
		int size=V1.size();
		Double sum1=0.0;
		Double sum2=0.0;
		for(int i=0;i<size;i++){
			sum1=sum1+Math.pow(V1.get(i)-V2.get(i),2);
			sum2=sum2+Math.pow(V1.get(i)+V2.get(i),2);
		}
		return 1-Math.sqrt(sum1)/Math.sqrt(sum2);
		
	}
}
