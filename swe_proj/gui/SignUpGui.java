
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: ���ο� ȸ����Ͽ� �ʿ��� �������� �Է��ϴ� User Interface
 */

package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import utility.Info;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import java.awt.Color;

public class SignUpGui extends JFrame {

	private JPanel signUpPanel;
	private JTextField textEmail;
	private JTextField textPwd;
	private JTextField textName;
	private JTextField textAddress;
	private ButtonGroup btnGroupUserType;
	private JRadioButton btnBuyer;
	private JRadioButton btnSeller;

	

	/**
	 * Create the frame.
	 */
	public SignUpGui() {
		setTitle("ȸ������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		signUpPanel = new JPanel();
		signUpPanel.setBackground(Color.WHITE);
		signUpPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(signUpPanel);
		signUpPanel.setLayout(null);
		
		initialize();
		buttonAction();
	}
	
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� �����(Label, list, table ��)�� �����ϴ� �Լ�
	 */
	
	
	public void initialize() {
			
		JPanel panelLabel = new JPanel();
		panelLabel.setBackground(Color.WHITE);
		panelLabel.setBounds(93, 26, 131, 443);
		signUpPanel.add(panelLabel);
		panelLabel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JLabel labelEmail = new JLabel("Email");
		labelEmail.setFont(new Font("���� ���", Font.PLAIN, 21));
		panelLabel.add(labelEmail);
		
		JLabel labelPwd = new JLabel("Password");
		labelPwd.setFont(new Font("���� ���", Font.PLAIN, 21));
		panelLabel.add(labelPwd);
		
		JLabel labelName = new JLabel("Name");
		labelName.setFont(new Font("���� ���", Font.PLAIN, 21));
		panelLabel.add(labelName);
		
		JLabel labelAddress = new JLabel("Address");
		labelAddress.setFont(new Font("���� ���", Font.PLAIN, 21));
		panelLabel.add(labelAddress);
		
		JLabel labelUserType = new JLabel("MemberType");
		labelUserType.setFont(new Font("���� ���", Font.PLAIN, 21));
		panelLabel.add(labelUserType);
		
		JPanel panelInput = new JPanel();
		panelInput.setBackground(Color.WHITE);
		panelInput.setBounds(297, 26, 422, 443);
		signUpPanel.add(panelInput);
		panelInput.setLayout(new GridLayout(5, 1, 0, 10));
		
		textEmail = new JTextField();
		textEmail.setFont(new Font("���� ���", Font.PLAIN, 22));
		panelInput.add(textEmail);
		textEmail.setColumns(10);
		
		textPwd = new JTextField();
		textPwd.setFont(new Font("���� ���", Font.PLAIN, 22));
		panelInput.add(textPwd);
		textPwd.setColumns(10);
		
		textName = new JTextField();
		textName.setFont(new Font("���� ���", Font.PLAIN, 22));
		panelInput.add(textName);
		textName.setColumns(10);
		
		textAddress = new JTextField();
		textAddress.setFont(new Font("���� ���", Font.PLAIN, 22));
		panelInput.add(textAddress);
		textName.setColumns(10);
				
		JPanel panelUserType = new JPanel();
		panelUserType.setBackground(Color.WHITE);
		panelInput.add(panelUserType);
		panelUserType.setLayout(new GridLayout(1,2,0,0));;
		
		btnBuyer = new JRadioButton("������");
		btnBuyer.setBackground(Color.WHITE);
		btnBuyer.setFont(new Font("���� ���", Font.PLAIN, 21));
		btnBuyer.setHorizontalAlignment(SwingConstants.CENTER);
		btnSeller = new JRadioButton("�Ǹ���");
		btnSeller.setBackground(Color.WHITE);
		btnSeller.setFont(new Font("���� ���", Font.PLAIN, 21));
		btnSeller.setHorizontalAlignment(SwingConstants.CENTER);
		
		ButtonGroup btnGroupUserType = new ButtonGroup();
		btnGroupUserType.add(btnBuyer);
		btnGroupUserType.add(btnSeller);
		
		panelUserType.add(btnBuyer);
		panelUserType.add(btnSeller);
	}
	
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� ��ư�� ����, ���, �� ��ɿ� �ʿ��� ����� ����ִ� �Լ�
	 */
	public void buttonAction(){
		JButton btnBack = new JButton("�ڷΰ���");
		btnBack.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGui loginGui = new LoginGui();
				loginGui.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(595, 492, 124, 48);
		signUpPanel.add(btnBack);
		
		JButton btnSignUp = new JButton("ȸ������");
		btnSignUp.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnSignUp.setBounds(407, 492, 148, 48);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*ȸ������ Ŭ���� �Լ� ��� �߰� ����*/
				try {
					String userType = "0";
					if(btnSeller.isSelected())
						userType = "1";
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String sql = "insert into member(email, password, name, address, memberType) "
							+ "values('"+ textEmail.getText() +"', '"+ textPwd.getText() +"', '"+ textName.getText() +"', '"
							+ textAddress.getText() +"', "+ userType +");";
					stmt.executeUpdate(sql);
					
					sql = "select id from member where email='"+textEmail.getText()+"' and password='"+textPwd.getText()+"';";
					ResultSet rs = stmt.executeQuery(sql);
					rs.next();
					String userId = rs.getString(1);
					
					if(userType == "0") {
						sql = "insert into buyer(userId, mileagePoint, paymentMoney) " + "values('"+ userId +"', 0, 0);";
					} else {
						sql = "insert into seller(userId, profit) " + "values('"+ userId +"', 0);";
					}
					stmt.executeUpdate(sql);
					
					conn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null,  "ȸ������ �����߽��ϴ�!");
				LoginGui loginGui = new LoginGui();
				loginGui.setVisible(true);
				dispose();
			}
		});
		signUpPanel.add(btnSignUp);
	}
}
