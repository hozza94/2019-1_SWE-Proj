
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: SellerMainPage에서 판매자가 등록한 티켓을 선택시 그에 대한 상세정보와 함께 수정하고 싶은 금액 입력란을 나타내는 User Interface
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Info;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class SellerTicketDetailsPage extends JFrame {

	private JPanel panelSellerTicketDetail;
	private JTextField textField;
	JTextField textModifyPrice;
	JRadioButton rdbtnAuction;
	boolean defalutAuctionCheck;

	
	public SellerTicketDetailsPage(String ticketName, String price, String matchDate, String city, String homeTeam, String awayTeam, String seat) {
		
		setTitle("판매자 검색 티켓 상세조회");
		setBounds(100, 100, 800, 600);
		panelSellerTicketDetail = new JPanel();
		panelSellerTicketDetail.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSellerTicketDetail);
		getContentPane().setBackground(Color.WHITE);
		panelSellerTicketDetail.setLayout(null);
		
		
		
		
		
		initialize(ticketName, price, matchDate, city, homeTeam, awayTeam, seat);
		buttonAction(ticketName, price);
		

		}
		
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 입출력(Label, list, table 등)을 설정하는 함수
	 */
		public void initialize(String ticketName, String price, String matchDate, String city, String homeTeam, String awayTeam, String seat){
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(113, 54, 539, 416);
			panelSellerTicketDetail.add(panel);
			panel.setLayout(null);
			
			JPanel panelLabel = new JPanel();
			panelLabel.setBounds(0, 0, 130, 416);
			panelLabel.setBackground(Color.WHITE);
			panel.add(panelLabel);
			panelLabel.setLayout(new GridLayout(9, 1, 0, 0));
			
			JLabel lblNewLabel = new JLabel("티켓 이름");
			lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			panelLabel.add(lblNewLabel);
			
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
			
			JLabel lblAuction = new JLabel("옥션 등록 여부");
			lblAuction.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			panelLabel.add(lblAuction);
			
			JLabel lblModifyPrice = new JLabel("수정 판매 가격");
			lblModifyPrice.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			panelLabel.add(lblModifyPrice);
			
			JPanel panelOutput = new JPanel();
			panelOutput.setBounds(157, 0, 382, 416);
			panelOutput.setBackground(Color.WHITE);
			panel.add(panelOutput);
			panelOutput.setLayout(new GridLayout(9, 1, 0, 0));
			


			JLabel lblticketNameValue = new JLabel(ticketName);
			lblticketNameValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(lblticketNameValue);
			
			JLabel lblPriceValue = new JLabel(price);
			lblPriceValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(lblPriceValue);
			
			JLabel lblMatchDateValue = new JLabel(matchDate);
			lblMatchDateValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(lblMatchDateValue);
			
			JLabel lblCityValue = new JLabel(city);
			lblCityValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(lblCityValue);
			
			JLabel lblHomeTeamValue = new JLabel(homeTeam);
			lblHomeTeamValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(lblHomeTeamValue);
			
			JLabel lblAwayTeamValue = new JLabel(awayTeam);
			lblAwayTeamValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(lblAwayTeamValue);
			
			JLabel lblSeatValue = new JLabel(seat);
			lblSeatValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(lblSeatValue);
			
			
			
			rdbtnAuction = new JRadioButton("옥션 등록");
			rdbtnAuction.setBackground(Color.WHITE);
			rdbtnAuction.setSelected(true);
			rdbtnAuction.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			
			/*	2019.05.31 판매자 티켓 옥션등록 여부 체크하는 기능 추가 (예정)(고관홍)
				if 옥션체크가 해지되어 있을 경우					rdbtnAuction.setSelected(false); seller.ticketAuctionCheck = false 
				if 옥션체크되어 있을 경우					rdbtnAuction.setSelected(true); seller.ticketAuctionCheck = true
				같은 식으로 추가 부탁드립니다.
			 */
			// 티켓 옥션여부 체크---------------------------
			try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select * from auction where ticketId =" + Integer.parseInt(ticketName) + ";"; 
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				rdbtnAuction.setSelected(true);
				defalutAuctionCheck = true;
			}
			else {
				rdbtnAuction.setSelected(false);
				defalutAuctionCheck = false;
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// -------------------------------------------
			
			panelOutput.add(rdbtnAuction);
			
			/* 2019.05.31 판매자 티켓 수정 페이지 삭제하고 대신 판매 수정 가격 가입란을 상세페이지에 추가(고관홍)
			
			 */
			textModifyPrice = new JTextField();
			textModifyPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
			panelOutput.add(textModifyPrice);
			textModifyPrice.setColumns(10);
			
			
		}
		
		/*
		 * Function: buttonAction()
		 * Created: 2019.05.24 - 2019.06.03
		 * Author: 
		 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
		 */
	public void buttonAction(String ticketName, String price){

		JButton btnModify = new JButton("수정");
		btnModify.setBounds(383, 498, 130, 43);
		btnModify.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//2019.05.31 아래 추가 함수 만듬. 해당 함수는 Seller가 등록한 티켓의 가격을 수정하는 기능. 안의 내용은 추가 바람(고관홍)
				ticketModifyAction(ticketName, price);
				JOptionPane.showMessageDialog(null,  "등록한 티켓을 수정했습니다!");
				dispose();

			}
		});
		
		panelSellerTicketDetail.add(btnModify);
		
		
		JButton btnCancel = new JButton("등록 취소");
		btnCancel.setBounds(527, 498, 130, 43);
		btnCancel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteTicket(ticketName);
				JOptionPane.showMessageDialog(null,  "등록한 티켓을 취소했습니다!");
				dispose();				
			}
		});
		
		panelSellerTicketDetail.add(btnCancel);
		
	}
	
	
	/*
 	* Function: ticketModifyAction
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 판매자가 등록한 티켓의 판매금액 및 옥션여부를 수정하려는 기능이 담겨있는 함수
 	*/
	
	/*2019/06/01 수정
	 *티켓 조회시 옥션 선택 안되있는 티켓 옥션 선택하면 옥션 등록하도록 수정, 옥션 등록 취소하는 기능은 안 넣음
	 *티켓가격 바꿀 수 있도록 기능 추가
	*/
	public void ticketModifyAction(String ticketName, String price){
		
		Connection conn = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		
		if(rdbtnAuction.isSelected() == true && defalutAuctionCheck == false) {
			try {
				conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
				Statement stmt = conn.createStatement();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);	
				cal.add(Calendar.DATE, 1);
				String timer = format.format(cal.getTime());
				String sql = "insert into auction(ticketId, winningBidderId, winningBid, timer) "
						+ "values(" + Integer.parseInt(ticketName) +", 0, 0,'"+ timer + "');";
				stmt.executeUpdate(sql);
				
				if(!textModifyPrice.getText().equals("")){
					sql = "update ticket set ticketPrice ="+ Integer.parseInt(textModifyPrice.getText())
							+ " where id=" + Integer.parseInt(ticketName) +";";
					stmt.executeUpdate(sql);
					
				}
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			try {
				conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
				Statement stmt = conn.createStatement();
				
				if(!textModifyPrice.getText().equals("")){
					String sql = "update ticket set ticketPrice ="+ Integer.parseInt(textModifyPrice.getText())
							+ " where id=" + Integer.parseInt(ticketName) +";";
					stmt.executeUpdate(sql);
					
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
 	* Function: deleteTicket()
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 등록한 티켓을 판매자의 Database에서 삭제하려는 함수
 	*/
	
	public void deleteTicket(String ticketName) {
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "delete from ticket where id =" + Integer.parseInt(ticketName) + ";"; 
			stmt.executeUpdate(sql);
			
			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
