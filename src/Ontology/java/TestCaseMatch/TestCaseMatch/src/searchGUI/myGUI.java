package searchGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFWriter;

import handleOntology.*;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.SpinnerNumberModel;

public class myGUI {

	private JFrame frame;
	private JTable table;
	private getOntoRe myOnto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myGUI window = new myGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public myGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 490, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		myPanel panel=new myPanel();
		panel.spLevelVal.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		panel.setBounds(10,10,466,255);
		frame.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("submit");
		btnNewButton.setBounds(274, 268, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Individual> myTcInd=myOnto.getReInd();
				OntModel ontModel=myOnto.getOntModel();
				String NS="http://www.owl-ontologies.com/zzxTC.owl"+"#";
				//TestLevel
				ObjectProperty hasLevel=ontModel.getObjectProperty(NS+"hasLevel");
				int tcNum=myTcInd.size();
				if(tcNum == 0){
					String s = UUID.randomUUID().toString();  
					Individual newInd=ontModel.createIndividual(NS+"TC_"+s,ontModel.getOntClass(NS+"TestCase"));
					//Target FunctionalProperty
					ObjectProperty hasTarget=ontModel.getObjectProperty(NS+"hasTarget");
					Individual targInd=ontModel.createIndividual(NS+"target_"+s,ontModel.getOntClass(NS+"TestTarget"));
					newInd.setPropertyValue(hasTarget, targInd);
					ObjectProperty hasTargetType=ontModel.getObjectProperty(NS+"hasTargetType");
					Individual targTypeInd=ontModel.createIndividual(NS+"targetType_"+s,ontModel.getOntClass(NS+panel.cbTargetType.getSelectedItem().toString()));
					targInd.setPropertyValue(hasTargetType, targTypeInd);
					DatatypeProperty targetDescripton=ontModel.getDatatypeProperty(NS+"targetDescripton");
					RDFNode tdNode=ontModel.createLiteral(panel.tfTargetD.getText().toString());
					targInd.setPropertyValue(targetDescripton,tdNode);
					//Profile
					Individual authorInd=ontModel.createIndividual(NS+"author_"+s,ontModel.getOntClass(NS+"Author"));
					Individual versionInd=ontModel.createIndividual(NS+"version_"+s,ontModel.getOntClass(NS+"Version"));
					ObjectProperty hasProfile=ontModel.getObjectProperty(NS+"hasProfile");
					newInd.setPropertyValue(hasProfile, authorInd);
					DatatypeProperty authorValue=ontModel.getDatatypeProperty(NS+"authorValue");
					RDFNode authorNode=ontModel.createLiteral(panel.tfAuthor.getText().toString());
					authorInd.setPropertyValue(authorValue, authorNode);
					DatatypeProperty versionValue=ontModel.getDatatypeProperty(NS+"versionValue");
					RDFNode versionNode=ontModel.createLiteral(panel.tfVersion.getText().toString());
					versionInd.setPropertyValue(versionValue, versionNode);
					newInd.addProperty(hasProfile, versionInd);
					//TestData
					ObjectProperty hasData=ontModel.getObjectProperty(NS+"hasData");
					Individual dataInd=ontModel.createIndividual(NS+"data_"+s,ontModel.getOntClass(NS+"TestData"));
					newInd.setPropertyValue(hasData, dataInd);
					//DataSource
					ObjectProperty hasSource=ontModel.getObjectProperty(NS+"hasSource");
					Individual sourceInd=ontModel.createIndividual(NS+"dataSource_"+s,ontModel.getOntClass(NS+panel.cbDataSource.getSelectedItem().toString()));
					dataInd.setPropertyValue(hasSource, sourceInd);
					//DataContent
					ObjectProperty hasContent=ontModel.getObjectProperty(NS+"hasContent");
					Individual contentInd=ontModel.createIndividual(NS+"dataContent_"+s,ontModel.getOntClass(NS+"DataContent"));
					dataInd.setPropertyValue(hasContent, contentInd);
					DatatypeProperty contentValue=ontModel.getDatatypeProperty(NS+"dataContentValue");
					RDFNode contentNode=ontModel.createLiteral(panel.tfDataContent.getText().toString());
					contentInd.setPropertyValue(contentValue,contentNode);
					//TestProcess
					ObjectProperty hasProcess=ontModel.getObjectProperty(NS+"hasProcess");
					Individual processInd=ontModel.createIndividual(NS+"process_"+s,ontModel.getOntClass(NS+"FunctionSequence"));
					newInd.setPropertyValue(hasProcess,processInd);
					DatatypeProperty orderingValue=ontModel.getDatatypeProperty(NS+"orderingValue");
					RDFNode orderingNdoe=ontModel.createLiteral(panel.tfTestPro.getText().toString());
					processInd.setPropertyValue(orderingValue,orderingNdoe);
					//TestResult
					ObjectProperty hasResult=ontModel.getObjectProperty(NS+"hasResult");
					Individual mReInd=ontModel.createIndividual(NS+"middleRe_"+s,ontModel.getOntClass(NS+"MiddleResult"));
					Individual fReInd=ontModel.createIndividual(NS+"finalRe_"+s,ontModel.getOntClass(NS+"FinalResult"));
					newInd.setPropertyValue(hasResult, mReInd);
					newInd.addProperty(hasResult, fReInd);
					DatatypeProperty resultValue=ontModel.getDatatypeProperty(NS+"resultValue");
					RDFNode mReNode=ontModel.createLiteral(panel.tfMiddleRe.getText().toString());
					RDFNode fReNode=ontModel.createLiteral(panel.tfFinalRe.getText().toString());
					mReInd.setPropertyValue(resultValue,mReNode);
					fReInd.setPropertyValue(resultValue,fReNode);
					//TestEnvironment
					ObjectProperty hasEnvironment=ontModel.getObjectProperty(NS+"hasEnvironment");
					Individual hardInd=ontModel.createIndividual(NS+"hardware_"+s,ontModel.getOntClass(NS+"HardWare"));
					Individual softInd=ontModel.createIndividual(NS+"software_"+s,ontModel.getOntClass(NS+"Software"));
					Individual paraInd=ontModel.createIndividual(NS+"network_"+s,ontModel.getOntClass(NS+"Network"));
					newInd.setPropertyValue(hasEnvironment, hardInd);
					newInd.addProperty(hasEnvironment, softInd);
					newInd.addProperty(hasEnvironment, paraInd);
					DatatypeProperty environValue=ontModel.getDatatypeProperty(NS+"environValue");
					RDFNode hardNode=ontModel.createLiteral(panel.tfHardware.getText().toString());
					RDFNode softNode=ontModel.createLiteral(panel.tfSoftware.getText().toString());
					RDFNode paraNode=ontModel.createLiteral(panel.tfParameter.getText().toString());
					hardInd.setPropertyValue(environValue, hardNode);
					softInd.setPropertyValue(environValue, softNode);
					paraInd.setPropertyValue(environValue,paraNode);
					//TestLevel
					Individual levelInd=ontModel.createIndividual(NS+"level_"+s,ontModel.getOntClass(NS+panel.cbTestLevel.getSelectedItem().toString()));
					newInd.setPropertyValue(hasLevel, levelInd);
					DatatypeProperty levelValue=ontModel.getDatatypeProperty(NS+"levelValue");
					RDFNode levelValueNode=ontModel.createLiteral(panel.spLevelVal.getValue().toString());
					levelInd.setPropertyValue(levelValue, levelValueNode);
				}
				else{
				for(int i=0;i<myTcInd.size();i++){
					Individual indTC=myTcInd.get(i);
					RDFNode levelNode=indTC.getPropertyValue(hasLevel);
					String levelName="";
					if(levelNode != null){
						Individual levelInd=ontModel.getIndividual(levelNode.toString());
						levelName=levelInd.getLocalName().toString();
						levelInd.remove();
					}
					Individual levelInd=ontModel.createIndividual(NS+levelName,ontModel.getOntClass(NS+table.getModel().getValueAt(i,2)));
					indTC.setPropertyValue(hasLevel, levelInd);
					DatatypeProperty levelValue=ontModel.getDatatypeProperty(NS+"levelValue");
					if(!table.getModel().getValueAt(i,4).toString().equals(" ")){
					RDFNode rdfNode = ontModel.createLiteral(table.getModel().getValueAt(i,3).toString());
					levelInd.setPropertyValue(levelValue, rdfNode);}
					}
				}	
				String filepath = "C:\\Users\\zzxWin7\\Desktop\\日志分析论文\\测试用例检索\\TC_OWL\\ultimateTC.owl";  
			    FileOutputStream fileOS = null;
				try {
					fileOS = new FileOutputStream(filepath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			    RDFWriter rdfWriter = ontModel.getWriter("RDF/XML");  
			    rdfWriter.setProperty("showXMLDeclaration","true");  
			    rdfWriter.setProperty("showDoctypeDeclaration", "true");  
			    rdfWriter.write(ontModel, fileOS, null);  
			    try {
					fileOS.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		btnNewButton_1.setBounds(377, 268, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//将请求数据封装为Map格式
				Map<String,String> queryMap=new HashMap<String,String>();
				queryMap.put("targetDescription",panel.tfTargetD.getText().toString());
				queryMap.put("targetType",panel.cbTargetType.getSelectedItem().toString());
				queryMap.put("Author",panel.tfAuthor.getText().toString());
				queryMap.put("Version",panel.tfVersion.getText().toString());
				queryMap.put("DataSourceType",panel.cbDataSource.getSelectedItem().toString());
				queryMap.put("DataContent",panel.tfDataContent.getText().toString());
				queryMap.put("MiddleResult",panel.tfMiddleRe.getText().toString());
				queryMap.put("FinalResult",panel.tfFinalRe.getText().toString());
				queryMap.put("TestProcess",panel.tfTestPro.getText().toString());
				queryMap.put("TestLevel",panel.cbTestLevel.getSelectedItem().toString());
				queryMap.put("HardWareEn",panel.tfHardware.getText().toString());
				queryMap.put("SoftWareEn",panel.tfSoftware.getText().toString());
				queryMap.put("Network",panel.tfParameter.getText().toString());
				queryMap.put("levelValue",panel.spLevelVal.getValue().toString());
				myOnto=new getOntoRe("C:\\Users\\zzxWin7\\Desktop\\日志分析论文\\测试用例检索\\TC_OWL\\ultimateTC.owl");
				table = new JTable();
				table.setModel(new DefaultTableModel(
						myOnto.getSearchRe(queryMap, "http://www.owl-ontologies.com/zzxTC.owl"),//return new Object[][]
					new String[] {
						"TC_Num","Description", "Priority","ProrityVal","MatchVal"
					}
				));
				JScrollPane scrollPane=new JScrollPane(table);
				scrollPane.setBounds(10, 296, 466, 178);
				frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
			}
		});
	}
}
