
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 아이디(이메일) 및 비밀번호 입력을 통한 구매자(및 판매자) 로그인 기능과 새로운 회원을 등록하는 회원가입 기능이 담겨져 있는 User Interface 
 */

package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utility.Info;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;

public class LoginGui extends JFrame{

	private JPanel loginPanel;
	private JPasswordField pwdPassword;
	private JTextField textId;
	private boolean signUpPage = false;
	private boolean loginPage = true;


	/**
	 * Create the application.
	 */
	public LoginGui() {
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginPanel);
		loginPanel.setLayout(null);

		initialize();
		buttonAction();
	}

	
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 입출력(Label, list, table 등)을 설정하는 함수
	 */
	public void initialize() {
		
		
		pwdPassword = new JPasswordField();
		pwdPassword.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		pwdPassword.setBounds(122, 217, 333, 77);
		loginPanel.add(pwdPassword);

		textId = new JTextField();
		textId.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		textId.setBounds(122, 110, 333, 77);
		loginPanel.add(textId);
		textId.setColumns(10);

		JLabel labelId = new JLabel("이메일");
		labelId.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		labelId.setBounds(14, 110, 74, 77);
		loginPanel.add(labelId);

		JLabel lblPassword = new JLabel("패스워드");
		lblPassword.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblPassword.setBounds(14, 217, 74, 77);
		loginPanel.add(lblPassword);
		
		Label label = new Label("야구경매 프로그램");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label.setAlignment(Label.CENTER);
		label.setBounds(112, 22, 261, 48);
		loginPanel.add(label);
	}

	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
	 */
	public void buttonAction(){
		JButton btnLogin = new JButton("로그인");
		btnLogin.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//// 2019.05.29
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String email = textId.getText();
					String password = pwdPassword.getText();
					String sql = "select * from member where email = '" + email + "' and password = '" + password + "';";
					ResultSet rs = stmt.executeQuery(sql);
					if(!rs.next()) {
						JOptionPane.showMessageDialog(null,  "정보를 찾을 수 없습니다.");
						return;
					} else {
						int user_id = rs.getInt(1);
						// sellerMainpage 상단에 표시하기 위해 userEmail 저장하는 코드 추가 2019/05/31 허범영
						Info.userEmail = rs.getString(2);		
						int member_type = rs.getInt(6);
						Info.user_id = user_id;
						
						if(member_type == 0) {
							// buyer
							sql = "select * from buyer where userId = " + user_id + ";";
							rs = stmt.executeQuery(sql);
							rs.next();
							Info.buyer_id = rs.getInt(1);
							Info.member_id = rs.getInt(2);
							loginPanel.setVisible(false);
							BuyerMainPage buyerMainPage = new BuyerMainPage();
							buyerMainPage.setVisible(true);
							
						} else {
							sql = "select * from seller where userId = " + user_id + ";";
							rs = stmt.executeQuery(sql);
							rs.next();
							Info.seller_id = rs.getInt(1);
							Info.member_id = rs.getInt(2);
							SellerMainPage sellerMainPage = new SellerMainPage();
							sellerMainPage.setVisible(true);
						}
					}
					dispose();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		});

		btnLogin.setBounds(157, 336, 173, 58);
		loginPanel.add(btnLogin);

		//2019.05.31 임시로 설정한 판매자 로그인 버튼 삭제-> 로그인 버튼이 앞으로 구매자, 판매자 사이트 로그인 가능
		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//// 2019.05.29
				textId.setText(null);
				pwdPassword.setText(null);

				signUpPage = true;
				if(signUpPage==true){
					loginPanel.setVisible(false);
					SignUpGui signUpGui = new SignUpGui();
					signUpGui.setVisible(true);
					dispose();
				}
			}
		});

		btnSignUp.setBounds(157, 426, 173, 58);
		loginPanel.add(btnSignUp);
		
		


	}
}
