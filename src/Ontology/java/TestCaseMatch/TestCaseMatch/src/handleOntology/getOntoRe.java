package handleOntology;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import com.ibm.compbio.seqalign.*;

public class getOntoRe {

	private String ontoFile;
	private double matchDegree;
	private double targetSim;
	private double profileSimi;
	private double dataSimi;
	private double orderingSimi;
	private double resultSimi;
	private double environSimi;
	private double levelSimi;
	private ArrayList<Individual> mTCInd;
	private OntModel ontModel;
	public  getOntoRe(String infile){	
		ontoFile = infile;
		matchDegree=0;
		targetSim=0;
		profileSimi=0;
		dataSimi=0;
		orderingSimi=0;
		resultSimi=0;
		environSimi=0;
		levelSimi=0;
		mTCInd=new ArrayList<Individual>();
	}
	
	public Object[][] getSearchRe(Map<String,String> mQuery,String Source){
		Model model = ModelFactory.createDefaultModel();
		model.read(ontoFile);
		ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,
				model);
		String NS = Source + "#";
		ArrayList<ArrayList<Object>> matchedCase=new ArrayList<ArrayList<Object>>();
		int tcNum=1;
		for(Iterator<Individual> i = ontModel.listIndividuals(); i.hasNext();){
			 Individual indTC=i.next();
			 if(indTC.getOntClass().toString().equals(NS+"TestCase")){
				String td=" ";
				String at=" ";
				String vs=" ";
				String pr=" ";
				String pv=" ";
				matchDegree=0;
				targetSim=0;
				profileSimi=0;
				dataSimi=0;
				orderingSimi=0;
				resultSimi=0;
				environSimi=0;
				levelSimi=0;
				 //Target FunctionalProperty
				 ObjectProperty hasTarget=ontModel.getObjectProperty(NS+"hasTarget");
				 RDFNode targetNode=indTC.getPropertyValue(hasTarget); 
				 if(targetNode !=null){
					 System.out.println(targetNode.toString());
					 Individual targInd=ontModel.getIndividual(targetNode.toString());
					 ObjectProperty hasTargetType=ontModel.getObjectProperty(NS+"hasTargetType");
					 DatatypeProperty targetDescripton=ontModel.getDatatypeProperty(NS+"targetDescripton");
					 RDFNode targetTypeNode=targInd.getPropertyValue(hasTargetType);
					 RDFNode targDescr=targInd.getPropertyValue(targetDescripton);
					 if(targetTypeNode != null){
						 //System.out.println(ontModel.getIndividual(targetTypeNode.toString()).getOntClass().toString());
						 if(ontModel.getIndividual(targetTypeNode.toString()).getOntClass().toString().equals(NS+mQuery.get("targetType").toString()))
							 targetSim=targetSim+0.5*1.0;//0.5 is the weight
					 }
					 else
						 targetSim=targetSim+0.5;
					 if(targDescr != null){
						 td=targDescr.toString();
						 //System.out.println(targDescr.toString());
						 CalSentenceSimi setSim=new CalSentenceSimi();
						 targetSim=targetSim + 0.5*setSim.genSimi(targDescr.toString(), mQuery.get("targetDescription").toString());
					 }
					 else
						 targetSim=targetSim+0.5;
				 }
				 else
					 targetSim=1;
				 
				//Profile
				 ObjectProperty hasProfile=ontModel.getObjectProperty(NS+"hasProfile");
				 boolean bHasAuthor=false;
				 boolean bHasVersion=false;
				 for(Iterator<RDFNode> myPro=indTC.listPropertyValues(hasProfile);myPro.hasNext();){
					 RDFNode profileNode=myPro.next();
					 if(profileNode != null){
						
						 EditDistance editD=new EditDistance();
						 if(ontModel.getIndividual(profileNode.toString()).getOntClass().getLocalName().equals("Author")){
							 DatatypeProperty authorValue=ontModel.getDatatypeProperty(NS+"authorValue");
							 RDFNode authorNode=ontModel.getIndividual(profileNode.toString()).getPropertyValue(authorValue);
							 if(authorNode != null){
								 profileSimi=profileSimi+0.5*1/(1+editD.getEditDistance(authorNode.toString(),mQuery.get("Author").toString()));
								 bHasAuthor=true;
								 at=authorNode.toString();
							 }
						 }
						 else//version
						 {
							 DatatypeProperty versionValue=ontModel.getDatatypeProperty(NS+"versionValue");
							 RDFNode versionNode=ontModel.getIndividual(profileNode.toString()).getPropertyValue(versionValue);
							 if(versionNode != null){
								 profileSimi=profileSimi+0.5*1/(1+editD.getEditDistance(versionNode.toString(),mQuery.get("Version").toString()));
								 bHasVersion=true;
								 vs=versionNode.toString();
							 	}
						}	 
					 }
				 }
				 if(!bHasAuthor)
					 profileSimi=profileSimi+0.5;
				 if(!bHasVersion)
					 profileSimi=profileSimi+0.5;
				 
				 //TestData
				 ObjectProperty hasData=ontModel.getObjectProperty(NS+"hasData");
				 RDFNode TestData=indTC.getPropertyValue(hasData);
				 boolean bHasSourceType=false;
				 boolean bHasDataContent=false;
				 if(TestData != null){
					 Individual dataInd=ontModel.getIndividual(TestData.toString());
					 //DataSource
					 ObjectProperty hasSource=ontModel.getObjectProperty(NS+"hasSource");
					 RDFNode sourceData=dataInd.getPropertyValue(hasSource);
					 if(sourceData != null){
						 ObjectProperty hasSourceType=ontModel.getObjectProperty(NS+"hasSourceType");
						 RDFNode sourceType=ontModel.getIndividual(sourceData.toString()).getPropertyValue(hasSourceType);
						 if(sourceType != null){
							 bHasSourceType=true;
							 if(ontModel.getIndividual(sourceType.toString()).getOntClass().toString().equals(NS+mQuery.get("DataSourceType").toString()))
								 dataSimi=dataSimi+0.5*1.0;
							 }
					 }
					 //DataContent
					 ObjectProperty hasContent=ontModel.getObjectProperty(NS+"hasContent");
					 RDFNode contentData=dataInd.getPropertyValue(hasContent);
					 if(contentData != null){
						 DatatypeProperty contentValue=ontModel.getDatatypeProperty(NS+"dataContentValue");
						 RDFNode dataContent=ontModel.getIndividual(contentData.toString()).getPropertyValue(contentValue);
						 if(dataContent != null){
							 bHasDataContent=true;
							 EditDistance editD=new EditDistance();
							 dataSimi=dataSimi+0.5*1/(1+editD.getEditDistance(dataContent.toString(), mQuery.get("DataContent").toString()));
						 }
					 }
				 }
				 if(!bHasSourceType)
					 dataSimi=dataSimi+0.5;
				 if(!bHasDataContent)
					 dataSimi=dataSimi+0.5;
				 //TestProcess
				 ObjectProperty hasProcess=ontModel.getObjectProperty(NS+"hasProcess");
				 RDFNode processNode=indTC.getPropertyValue(hasProcess);
				 boolean bHasProcess=false;
				 if(processNode != null){
					 DatatypeProperty orderingValue=ontModel.getDatatypeProperty(NS+"orderingValue");
					 RDFNode orderingNode=ontModel.getIndividual(processNode.toString()).getPropertyValue(orderingValue);
					 if(orderingNode != null){
						 bHasProcess=true;
						 if(mQuery.get("TestProcess").toString().isEmpty())
							 orderingSimi=1;
						 else{
							 System.out.println(orderingNode.toString()+"\\"+ mQuery.get("TestProcess").toString());
							 SmithWaterman sw = new SmithWaterman(orderingNode.toString(), mQuery.get("TestProcess").toString());
							 sw.getScoreTable(); 
							 String[] s = (String[]) sw.getTraceback(); 
							 orderingSimi=orderingSimi+s[0].length()/mQuery.get("TestProcess").toString().length();
						 }
					 }
				 }
				 if(!bHasProcess)
					 orderingSimi=orderingSimi+1;
				 //TestResult
				 ObjectProperty hasResult=ontModel.getObjectProperty(NS+"hasResult");
				 boolean bHasMResult=false;
				 boolean bHasFResult=false;
				 for(Iterator<RDFNode> myRe=indTC.listPropertyValues(hasResult);myRe.hasNext();){
					 RDFNode reNode=myRe.next();
					 if(reNode != null)
					 {
						 if(ontModel.getIndividual(reNode.toString()).getOntClass().getLocalName().equals("MiddleResult")){
							 DatatypeProperty resultValue=ontModel.getDatatypeProperty(NS+"resultValue");
							 RDFNode reValue=ontModel.getIndividual(reNode.toString()).getPropertyValue(resultValue);
							 if(reValue != null)
							 {
								 CalSentenceSimi setSim=new CalSentenceSimi();
								 resultSimi=resultSimi+0.5*setSim.genSimi(reValue.toString(), mQuery.get("MiddleResult").toString());
								 bHasMResult=true;
							 }
						 }
						 else//FinalResult
						 {
							 DatatypeProperty resultValue=ontModel.getDatatypeProperty(NS+"resultValue");
							 RDFNode reValue=ontModel.getIndividual(reNode.toString()).getPropertyValue(resultValue);
							 if(reValue != null)
							 {
								 CalSentenceSimi setSim=new CalSentenceSimi();
								 resultSimi=resultSimi+0.5*setSim.genSimi(reValue.toString(), mQuery.get("FinalResult").toString());
								 bHasFResult=true;
							 }
						 }		 
					 } 
				 }
				 if(!bHasMResult)
					 resultSimi=resultSimi+0.5;
				 if(!bHasFResult)
					 resultSimi=resultSimi+0.5;
				 //TestEnvironment
				 ObjectProperty hasEnvironment=ontModel.getObjectProperty(NS+"hasEnvironment");
				 boolean bHasHardware=false;
				 boolean bHasSoftware=false;
				 boolean bHasParameter=false;
				 for(Iterator<RDFNode> myEn=indTC.listPropertyValues(hasEnvironment);myEn.hasNext();){
					 RDFNode enNode=myEn.next();
					 if(enNode != null)
					 {
						 //System.out.println(enNode.toString());
						 DatatypeProperty environValue=ontModel.getDatatypeProperty(NS+"environValue");
						 Individual tmpInd=ontModel.getIndividual(enNode.toString());
						 RDFNode enValue=tmpInd.getPropertyValue(environValue);
						 CalSentenceSimi setSim=new CalSentenceSimi();
						 if(enValue != null){
						 if(tmpInd.getOntClass().toString().equals(NS+"Hardware")){
							 bHasHardware=true;
							 environSimi=environSimi+0.33*setSim.genSimi(enValue.toString(), mQuery.get("HardWareEn").toString()); 
						 }
						 else if(tmpInd.getOntClass().toString().equals(NS+"Software")){
							 bHasSoftware=true;
							 environSimi=environSimi+0.33*setSim.genSimi(enValue.toString(), mQuery.get("SoftWareEn").toString());
						 }
						 else{
							 bHasParameter=true;
							 environSimi=environSimi+0.34*setSim.genSimi(enValue.toString(), mQuery.get("Network").toString()); 
						 }
						 }
					 }	 
				 }
				 if(!bHasHardware)
					 environSimi=environSimi+0.33;
				 if(!bHasSoftware)
					 environSimi=environSimi+0.33;
				 if(!bHasParameter)
					 environSimi=environSimi+0.34;
				 //TestLevel
				 ObjectProperty hasLevel=ontModel.getObjectProperty(NS+"hasLevel");
				 RDFNode levelNode=indTC.getPropertyValue(hasLevel);
				 if(levelNode != null){
					 DatatypeProperty levelValue=ontModel.getDatatypeProperty(NS+"levelValue");
					 RDFNode levelValueNode=ontModel.getIndividual(levelNode.toString()).getPropertyValue(levelValue);
					 pr=ontModel.getIndividual(levelNode.toString()).getOntClass().getLocalName();
					 if(levelValueNode != null){
						 pv=levelValueNode.asLiteral().getString();
						 //System.out.println(levelValueNode.asLiteral().getInt());
					 	 if(ontModel.getIndividual(levelNode.toString()).getOntClass().getLocalName().equals(mQuery.get("TestLevel").toString()))
					 		 levelSimi=levelSimi+1-Math.abs(levelValueNode.asLiteral().getInt()-Integer.parseInt(mQuery.get("levelValue").toString()))/(double)Math.max(levelValueNode.asLiteral().getInt(), Integer.parseInt(mQuery.get("levelValue").toString()));
					 }
				 }
				 else
					 levelSimi=1; 
				 matchDegree=0.15*targetSim+0.1*profileSimi+0.15*dataSimi+0.15*orderingSimi+0.15*resultSimi+0.15*environSimi+0.15*levelSimi;
				 if(matchDegree > 0.7)
				 {
					 ArrayList<Object> newO=new ArrayList<Object>();
					 newO.add(tcNum);
					 newO.add(td);
//					 newO.add(at);
//					 newO.add(vs);
					 newO.add(pr);
					 newO.add(pv);
					 DecimalFormat    df   = new DecimalFormat("######0.00");   
					 newO.add(df.format(matchDegree));
					 mTCInd.add(indTC);
					 matchedCase.add(newO);
					 tcNum++;
				 }
			 }
		 }
		int index=5;
		Object[][] fr=new Object[matchedCase.size()][index];
		for(int i=0;i<matchedCase.size();i++){
			for(int j=0;j<index;j++)
				fr[i][j]=matchedCase.get(i).get(j);
			
		}
		 return fr;
	}
	public ArrayList<Individual> getReInd(){
		return mTCInd;
	}
	public OntModel getOntModel(){
		return ontModel;
	}
}
