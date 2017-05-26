package searchGUI;

import javax.swing.JPanel;
import javax.swing.JButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;

public class myPanel extends JPanel {
	public JTextField tfTargetD;
	public JTextField tfAuthor;
	public JTextField tfVersion;
	public JTextField tfDataContent;
	public JTextField tfMiddleRe;
	public JTextField tfFinalRe;
	public JTextField tfHardware;
	public JTextField tfSoftware;
	public JTextField tfParameter;
	public JTextField tfTestPro;
	public JComboBox cbTargetType;
	public JComboBox cbDataSource;
	public JComboBox cbTestLevel;
	public JSpinner  spLevelVal;
	/**
	 * Create the panel.
	 */
	public myPanel() {
		setLayout(null);
		
		JLabel lblTargetdescription = new JLabel("TargetDescription");
		lblTargetdescription.setBounds(10, 10, 117, 15);
		add(lblTargetdescription);
		
		tfTargetD = new JTextField();
		tfTargetD.setBounds(126, 7, 117, 21);
		add(tfTargetD);
		tfTargetD.setColumns(10);
		
		JLabel lblTargettype = new JLabel("TargetType");
		lblTargettype.setBounds(259, 10, 66, 15);
		add(lblTargettype);
		
		JLabel lblNewLabel = new JLabel("Author");
		lblNewLabel.setBounds(10, 41, 54, 15);
		add(lblNewLabel);
		
		tfAuthor = new JTextField();
		tfAuthor.setBounds(126, 38, 117, 21);
		add(tfAuthor);
		tfAuthor.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Version");
		lblNewLabel_1.setBounds(259, 41, 54, 15);
		add(lblNewLabel_1);
		
		tfVersion = new JTextField();
		tfVersion.setBounds(331, 38, 109, 21);
		add(tfVersion);
		tfVersion.setColumns(10);
		
		cbTargetType = new JComboBox();
		cbTargetType.setModel(new DefaultComboBoxModel(new String[] {"UnitTest", "IntegrationTest", "System"}));
		cbTargetType.setBounds(331, 7, 109, 21);
		add(cbTargetType);
		
		JLabel lblNewLabel_2 = new JLabel("DataSource");
		lblNewLabel_2.setBounds(10, 75, 88, 15);
		add(lblNewLabel_2);
		
		cbDataSource = new JComboBox();
		cbDataSource.setModel(new DefaultComboBoxModel(new String[] {"Manual", "Automatics"}));
		cbDataSource.setBounds(126, 72, 117, 21);
		add(cbDataSource);
		
		JLabel lblNewLabel_3 = new JLabel("DataContent");
		lblNewLabel_3.setBounds(259, 75, 72, 15);
		add(lblNewLabel_3);
		
		tfDataContent = new JTextField();
		tfDataContent.setBounds(331, 72, 109, 21);
		add(tfDataContent);
		tfDataContent.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("MiddleResult");
		lblNewLabel_4.setBounds(10, 108, 88, 15);
		add(lblNewLabel_4);
		
		tfMiddleRe = new JTextField();
		tfMiddleRe.setBounds(125, 105, 118, 21);
		add(tfMiddleRe);
		tfMiddleRe.setColumns(10);
		
		JLabel lblFinalresult = new JLabel("FinalResult");
		lblFinalresult.setBounds(259, 108, 66, 15);
		add(lblFinalresult);
		
		tfFinalRe = new JTextField();
		tfFinalRe.setBounds(331, 105, 109, 21);
		add(tfFinalRe);
		tfFinalRe.setColumns(10);
		
		JLabel lblHardwareen = new JLabel("HardWareEn");
		lblHardwareen.setBounds(10, 185, 72, 15);
		add(lblHardwareen);
		
		tfHardware = new JTextField();
		tfHardware.setBounds(126, 182, 117, 21);
		add(tfHardware);
		tfHardware.setColumns(10);
		
		JLabel lblSoftwareen = new JLabel("SoftWareEn");
		lblSoftwareen.setBounds(259, 185, 75, 15);
		add(lblSoftwareen);
		
		tfSoftware = new JTextField();
		tfSoftware.setBounds(331, 182, 109, 21);
		add(tfSoftware);
		tfSoftware.setColumns(10);
		
		JLabel lblParameter = new JLabel("NetWork");
		lblParameter.setBounds(260, 148, 65, 15);
		add(lblParameter);
		
		tfParameter = new JTextField();
		tfParameter.setBounds(331, 145, 109, 21);
		add(tfParameter);
		tfParameter.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("TestProcess");
		lblNewLabel_5.setBounds(10, 148, 88, 15);
		add(lblNewLabel_5);
		
		tfTestPro = new JTextField();
		tfTestPro.setBounds(126, 145, 117, 21);
		add(tfTestPro);
		tfTestPro.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("TestLevel");
		lblNewLabel_6.setBounds(10, 225, 66, 15);
		add(lblNewLabel_6);
		
		cbTestLevel = new JComboBox();
		cbTestLevel.setModel(new DefaultComboBoxModel(new String[] {"HighLevel", "MiddleLevel", "LowLevel"}));
		cbTestLevel.setBounds(126, 222, 117, 21);
		add(cbTestLevel);
		
		spLevelVal = new JSpinner();
		spLevelVal.setBounds(331, 222, 109, 22);
		add(spLevelVal);
		
		JLabel lblNewLabel_7 = new JLabel("LevelValue");
		lblNewLabel_7.setBounds(259, 225, 66, 15);
		add(lblNewLabel_7);

	}
}
