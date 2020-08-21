
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 판매자가 새롭게 티켓을 등록하고 싶을 때 티켓에 필요한 정보입력란을 나타내는 User Interface
 */

package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import classes.Seller;
import classes.Ticket;
import utility.Info;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Font;

public class SellerTicketRegister extends JFrame {
	private JFrame frame;
	private JPanel panelSellerTIcketRegister;
	private JTextField inputTicketName;	
	private JTextField inputPrice;
	private JTextField inputMatchDate;
	private JTextField inputCity;
	private JTextField inputHomeTeam;
	private JTextField inputAwayTeam;
	private JTextField inputSeat;
	private JCheckBox chckbxAuction;
	private Seller seller;
	
	public SellerTicketRegister() {
		this.frame = frame;
		//seller클래스 이용하기 위해 	2019/05/31 수정
		seller = new Seller();
		seller.setSellerId(Info.seller_id);
		
		setTitle("판매자 티켓 등록");
		setBounds(100, 100, 800, 600);
		panelSellerTIcketRegister = new JPanel();
		panelSellerTIcketRegister.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSellerTIcketRegister);
		getContentPane().setBackground(Color.WHITE);
		panelSellerTIcketRegister.setLayout(null);
		initialize();
		buttonAction();
	}
		
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 입출력(Label, list, table 등)을 설정하는 함수
	 */
	public void initialize(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(113, 54, 539, 416);
		panelSellerTIcketRegister.add(panel);
		panel.setLayout(null);
		
		JPanel panelLabel = new JPanel();
		panelLabel.setBackground(Color.WHITE);
		panelLabel.setBounds(0, 0, 130, 416);
		panel.add(panelLabel);
		panelLabel.setLayout(new GridLayout(7, 1, 0, 0));
		
		/*	---- 티켓ID는 자동생성 되므로 필요 없습니다 2019/5/30수정
		JLabel lblNewLabel = new JLabel("티켓 이름");
		panelLabel.add(lblNewLabel);
		*/
		
		/* 2019/05/31 자동 등록되는 티켓의 ID를 페이지에 표시하고 싶은데요 이 기능을 추가 부탁드립니다(고관홍)
		 	아래 함수에 JLabel lblTicketNameValue을 넣었는데요
			JLabel lblTicketNameValue = new JLabel("");
			"" 사이에 자동등록되는 티켓 ID을 넣어주세요.
			
		*/
		
		
		JLabel lblPrice = new JLabel("판매가격");
		lblPrice.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblPrice);
		
		JLabel lblMatchDate = new JLabel("야구 경기 날짜");
		lblMatchDate.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblMatchDate);
		
		JLabel lblCity = new JLabel("도시");
		lblCity.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblCity);
		
		JLabel lblHomeTeam = new JLabel("홈팀");
		lblHomeTeam.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblHomeTeam);
		
		JLabel lblAwayTeam = new JLabel("어웨이팀");
		lblAwayTeam.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblAwayTeam);
		
		JLabel lblSeat = new JLabel("좌석위치");
		lblSeat.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblSeat);
		
		
		JPanel panelOutput = new JPanel();
		panelOutput.setBackground(Color.WHITE);
		panelOutput.setBounds(153, 0, 386, 416);
		panel.add(panelOutput);
		panelOutput.setLayout(new GridLayout(7, 1, 0, 0));
		

		
		inputPrice = new JTextField();
		inputPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		inputPrice.setColumns(10);
		panelOutput.add(inputPrice);
		
		inputMatchDate = new JTextField();
		inputMatchDate.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		inputMatchDate.setColumns(10);
		panelOutput.add(inputMatchDate);
		
		inputCity = new JTextField();
		inputCity.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		inputCity.setColumns(10);
		panelOutput.add(inputCity);
		
		inputHomeTeam = new JTextField();
		inputHomeTeam.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		inputHomeTeam.setColumns(10);
		panelOutput.add(inputHomeTeam);
		
		inputAwayTeam = new JTextField();
		inputAwayTeam.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		inputAwayTeam.setColumns(10);
		panelOutput.add(inputAwayTeam);
		
		inputSeat = new JTextField();
		inputSeat.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		inputSeat.setColumns(10);
		panelOutput.add(inputSeat);
		
		chckbxAuction = new JCheckBox("옥션 가능 여부");
		chckbxAuction.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		chckbxAuction.setBackground(Color.WHITE);
		panelOutput.add(chckbxAuction);
	}
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
	 */
	public void buttonAction(){

		JButton btnTicketRegister = new JButton("티켓 등록");
		btnTicketRegister.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnTicketRegister.setBounds(515, 498, 137, 43);

		btnTicketRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// 현재시간 구하기
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String currentTime = format.format(date);
				
				// 판매자 티켓 등록 부분 --------------------------------------------------2019/5/31수정
				Ticket ticket = new Ticket(1, seller.getSellerId(), inputMatchDate.getText(),
						currentTime, inputCity.getText() ,inputHomeTeam.getText(), inputAwayTeam.getText(),
						Integer.parseInt(inputSeat.getText()), Integer.parseInt(inputPrice.getText()));
				seller.setSellerTicket(ticket);
				seller.getSellerTicket().registerSellerTicket();
				//------------------------------------------------------------------------
				JOptionPane.showMessageDialog(null,  "티켓을 판매 등록했습니다!");
				
				//옥션 등록 체크했을 때		2019/05/31 수정
				//옥션 체크시 타이머는 등록한 시간 + 1일로 설정, winningBidderId와 WinningBid는 초기값 0으로 설정
				if(chckbxAuction.isSelected()) {
					try {
						Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
						Statement stmt = conn.createStatement();
					
						ResultSet rs = stmt.executeQuery("select MAX(id) from ticket;");
						rs.next();
						seller.sellerId = rs.getInt(1);
						rs.close();
	
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);	
						cal.add(Calendar.DATE, 1);
						String timer = format.format(cal.getTime());
						String sql = "insert into auction(ticketId, winningBidderId, winningBid, timer) "
								+ "values(" + seller.sellerId +", 0, 0,'"+ timer + "');";
						stmt.executeUpdate(sql);
						stmt.close();
						conn.close();
					} catch (SQLException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
					
				}
				
				dispose();	
				
			}
		});
		
		panelSellerTIcketRegister.add(btnTicketRegister);
		
		
		
	}
}
