
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 판매자가 판매 가능한 티켓들 중 옥션항목이 체크되어 있는 티켓을 구매할 구매자 페이지 User Interface
 */



package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.AuctionTicket;
import classes.Ticket;
import utility.Info;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class BuyerAuctionTicketDetailsPage extends JFrame {

	private JPanel panelBuyerAuction;
	private JTextField txtInputAuctionPrice;
	private Connection conn;
	private AuctionTicket auctionTicket;

	/**
	 * Create the frame.
	 */
	public BuyerAuctionTicketDetailsPage(String auctionId) {
		setTitle("구매자 검색한 옥션 티켓 상세정보");
		setBounds(100, 100, 800, 600);
		panelBuyerAuction = new JPanel();
		panelBuyerAuction.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelBuyerAuction);
		getContentPane().setBackground(Color.WHITE);
		panelBuyerAuction.setLayout(null);
		
		String winningBid = null;
		try {
			conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select winningBidderId from auction where id = " + auctionId + ";";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			if(rs.getInt(1) == 0) {
				sql = "select A.winningBid, T.*, A.timer from auction A, ticket T "
						+ "where A.ticketId=T.id and A.id = " + auctionId + ";";
				rs = stmt.executeQuery(sql);
				rs.next();
				
				auctionTicket = new AuctionTicket(auctionId, rs.getString(1), "", rs.getString(4), rs.getString(6), 
						rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(11));
			}else {
				sql = "select A.winningBid, M.name, T.*, A.timer from auction A, ticket T, buyer B, member M "
						+ "where A.ticketId=T.id and A.winningBidderId = B.id and B.userId = M.id and A.id = " + auctionId + ";";
				rs = stmt.executeQuery(sql);
				rs.next();
				auctionTicket = new AuctionTicket(auctionId, rs.getString(1), rs.getString(2), rs.getString(5), rs.getString(7), 
						rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(12));
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initialize(auctionTicket);
		buttonAction(auctionId, auctionTicket.winningBid);
	}
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
	 */
	
	public void buttonAction(String auctionId, String winningBid){
		JButton btnBack = new JButton("뒤로가기");
		btnBack.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnBack.setBounds(595, 492, 124, 48);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panelBuyerAuction.add(btnBack);
		
		JButton btnAuction = new JButton("입찰");
		btnAuction.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnAuction.setBounds(437, 492, 137, 48);
		
		btnAuction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*구매자 티켓 옥션 입찰 기능 함수 추가 예정*/
				int bid = Integer.parseInt(txtInputAuctionPrice.getText());
				int winning = Integer.parseInt(winningBid);
				if(bid < winning)
					JOptionPane.showMessageDialog(null,  "더 높은 금액을 입력하여 주세요.");
				else {
					try {
						conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
						Statement stmt = conn.createStatement();
						String sql = "update auction set winningBidderId = " + Info.buyer_id + ", winningBid = " + bid + " where id = " + auctionId + ";";
						stmt.executeUpdate(sql);
						conn.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null,  "입찰했습니다!");
					dispose();
				}
			}
		});
		
		panelBuyerAuction.add(btnAuction);
	}
	
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 입출력(Label, list, table 등)을 설정하는 함수
	 */
	public void initialize(AuctionTicket auctionTicket){
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(113, 54, 539, 416);
		panelBuyerAuction.add(panel);
		panel.setLayout(null);
		
		JPanel panelLabel = new JPanel();
		panelLabel.setBackground(Color.WHITE);
		panelLabel.setBounds(0, 0, 130, 416);
		panel.add(panelLabel);
		panelLabel.setLayout(new GridLayout(10, 1, 0, 0));
		
		JLabel lblName = new JLabel("옥션ID");
		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblName);
		
		JLabel lblPrice = new JLabel("최고 입찰가");
		lblPrice.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblPrice);
		
		JLabel lblPriceMember = new JLabel("최고 입찰자");
		lblPriceMember.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblPriceMember);
		
		JLabel lblMatchDate = new JLabel("야구 경기 날짜");
		lblMatchDate.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblMatchDate);
		
		JLabel lblCity = new JLabel("도시");
		lblCity.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblCity);
		
		JLabel lblAwayTeam = new JLabel("홈팀");
		lblAwayTeam.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblAwayTeam);
		
		JLabel lblHomeTeam = new JLabel("어웨이팀");
		lblHomeTeam.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblHomeTeam);
		
		JLabel lblSeat = new JLabel("좌석위치");
		lblSeat.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblSeat);
		
		JLabel lblTime = new JLabel("남은시간");
		lblTime.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblTime);

		JLabel lblAuctionBid = new JLabel("입찰 금액");
		lblAuctionBid.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panelLabel.add(lblAuctionBid);
		
		JPanel panelOutput = new JPanel();
		panelOutput.setBackground(Color.WHITE);
		panelOutput.setBounds(144, 0, 395, 416);
		panel.add(panelOutput);
		panelOutput.setLayout(new GridLayout(10, 1, 0, 0));
		
		JLabel label = new JLabel(auctionTicket.auctionId);
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label);
		JLabel label_1 = new JLabel(auctionTicket.winningBid);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label_1);
		JLabel label_2 = new JLabel(auctionTicket.winningBidder);
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label_2);
		JLabel label_3 = new JLabel(auctionTicket.ticket.ticketDate);
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label_3);
		JLabel label_4 = new JLabel(auctionTicket.ticket.city);
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label_4);
		JLabel label_5 = new JLabel(auctionTicket.ticket.homeTeam);
		label_5.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label_5);
		JLabel label_6 = new JLabel(auctionTicket.ticket.awayTeam);
		label_6.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label_6);
		JLabel label_7 = new JLabel(""+auctionTicket.ticket.seat);
		label_7.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(label_7);
		
		int diff_hour = 0, diff_days = 0;
		try {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date setTime = dateFormat.parse(auctionTicket.timer);
			int diff = (int)(setTime.getTime() - now.getTime());
			diff_hour = (diff / 1000 / 60 / 60) % 24;
			diff_days = (diff / 1000 / 60 / 60) / 24;
 		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel lblTimeValue = new JLabel(diff_days + "일 " + diff_hour + "시간 남음");
		lblTimeValue.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(lblTimeValue);
		
		txtInputAuctionPrice = new JTextField();
		txtInputAuctionPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		panelOutput.add(txtInputAuctionPrice);
		txtInputAuctionPrice.setColumns(10);
	}
}

