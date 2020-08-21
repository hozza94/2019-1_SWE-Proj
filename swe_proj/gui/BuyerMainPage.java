
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 구매자가 예약가능한 티켓(옥션 여부도 포함) 및 구매자가 필요한 기본기능이 담겨져 있는 User Interface
 */



package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import classes.AuctionTicket;
import classes.AuctionTicketManagementSystem;
import classes.Buyer;
import classes.Reservation;
import classes.Ticket;
import classes.TicketManagementSystem;
import utility.Info;

import java.awt.GridLayout;
import java.awt.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JTextField;

public class BuyerMainPage extends JFrame {

	// 2019.06.01 ReservationTable과 관련된 GUI는 전부 새로운 클래스 BuyerReservedTicket.java로 옮김(고관홍)
	private JPanel buyerPanel;
	private JTable ticketTable, auctionTable;
	private String ticketTableHeader[] = {"티켓ID", "야구 경기 날짜", "도시", "홈팀", "어웨이팀", "좌석위치", "가격"};
	private String auctionTableHearder[] = {"옥션ID", "야구 경기 날짜", "도시", "홈팀", "어웨이팀", "좌석위치", "최고 입찰가"};
	private Buyer buyer;
	private JList teamList;
	JTextField toYear;
	JTextField toMonth;
	JTextField toDay;
	JTextField fromYear;
	JTextField fromMonth;
	JTextField fromDay;
	DefaultTableModel ticketModel;
	DefaultTableModel auctionTableModel;
	DefaultListModel<String> teamListModel;
	
	TicketManagementSystem ticketManagementSystem = new TicketManagementSystem();
	AuctionTicketManagementSystem auctionTicketManagementSystem = new AuctionTicketManagementSystem();

	/**
	 * Create the frame.
	 */
	public BuyerMainPage() {
		setTitle("구매자 페이지");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		buyerPanel = new JPanel();
		buyerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(buyerPanel);
		getContentPane().setBackground(Color.WHITE);
		buyerPanel.setLayout(null);
		
		// TicketList
		String[][] tableListContents = {};
        ticketModel = new DefaultTableModel(tableListContents, ticketTableHeader); 
		
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select * from ticket where ticketPrice != 0;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
		        int ticketId = rs.getInt(1);
		        int sellerId = rs.getInt(2);
		        String ticketdate = rs.getString(3);
		        String registerdate = rs.getString(3);
		        String city = rs.getString(5);
		        String home = rs.getString(6);
		        String away = rs.getString(7);
		        int seat = rs.getInt(8);
		        int price = rs.getInt(9);
		        
		        Ticket ticket = new Ticket(ticketId, sellerId, ticketdate, registerdate, city, home, away, seat, price);
		        ticketManagementSystem.addTicket(ticket);
		    }
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for(int i=0;i<ticketManagementSystem.ticketList.size();i++) {
        	Ticket ticket = ticketManagementSystem.ticketList.get(i);
        	String input[] = {""+ticket.id, ticket.ticketDate, ticket.city, ticket.homeTeam, ticket.awayTeam, ""+ticket.seat, ""+ticket.ticketPrice};
            ticketModel.addRow(input);
        }
        
		
		// actionList
		String[][] auctionListContents = {};
        auctionTableModel = new DefaultTableModel(auctionListContents, auctionTableHearder); 
        auctionTable = new JTable(auctionTableModel);
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select A.id, A.winningBid, T.*, A.timer FROM auction A, ticket T where A.ticketId = T.id;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				String ActionId = rs.getString(1);
		        String winningBid = rs.getString(2);
		        String ticketdate = rs.getString(5);
		        String city = rs.getString(7);
		        String home = rs.getString(8);
		        String away = rs.getString(9);
		        String seat = rs.getString(10);
		        String timer = rs.getString(11);
		        
		        AuctionTicket AuctionTicket = new AuctionTicket(ActionId, winningBid, "", ticketdate, city, home, away, seat, timer);
		        auctionTicketManagementSystem.addTicket(AuctionTicket);
		    }
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for(int i=0;i<auctionTicketManagementSystem.auctionTicketList.size();i++) {
        	AuctionTicket auctionTicket = auctionTicketManagementSystem.auctionTicketList.get(i);
        	String input[] = {auctionTicket.auctionId, auctionTicket.ticket.ticketDate, auctionTicket.ticket.city, 
        			auctionTicket.ticket.homeTeam, auctionTicket.ticket.awayTeam, ""+auctionTicket.ticket.seat, 
        			auctionTicket.winningBid };
	        auctionTableModel.addRow(input);
        }
		
		ticketTable = new JTable(ticketModel);
		
		
		createTable(tableListContents, ticketTable);
		createTable(tableListContents, auctionTable);
		
		tableAction(ticketTable,0);
		tableAction(auctionTable,1);
		
		JScrollPane scrollpane = new JScrollPane(ticketTable);
		scrollpane.setBounds(34, 205, 1214, 175);
		buyerPanel.add(scrollpane);
		
		JScrollPane scrollPane = new JScrollPane(auctionTable);
		scrollPane.setBounds(34, 421, 1214, 175);
		buyerPanel.add(scrollPane);
		
		initialize();
		
		ticketToDateList();
		ticketFromDateList();
		buttonAction();
	}
	
	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 입출력(Label, list, table 등)을 설정하는 함수
	 */
	public void initialize(){
		
		// teamList
		String[][] teamListNames = {};
		
		//리스트에 스크롤바 추가
		JScrollPane teamListSelect = new JScrollPane();
		teamListSelect.setBounds(151,109, 100, 68);
		teamListModel = new DefaultListModel<String>(); 
        teamList = new JList(teamListModel);
        teamList.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		teamListSelect.setViewportView(teamList);
		//리스트 경계선 설정, 단일 선택 모드 설정
		teamList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select distinct homeTeamId from ticket;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				String homeTeamId = rs.getString(1);
				teamListModel.addElement(homeTeamId);
		    }
			teamList.setSelectedIndex(0);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		buyerPanel.add(teamListSelect);
		
		// 이름추가
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select name from member where id="+Info.user_id+";";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				String name = rs.getString(1);
				
				JLabel lblNewLabel_1 = new JLabel(name);
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
				lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 23));
				lblNewLabel_1.setBounds(477, 23, 145, 55);
				buyerPanel.add(lblNewLabel_1);
		    }
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblMain = new JLabel("메인 페이지");
		lblMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblMain.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		lblMain.setBounds(630, 23, 145, 61);
		buyerPanel.add(lblMain);
		
		
		JLabel lblSelectTeam = new JLabel("홈팀선택");
		lblSelectTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTeam.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblSelectTeam.setBounds(34, 129, 113, 38);
		buyerPanel.add(lblSelectTeam);
		
		
		JLabel lblAuctionTable = new JLabel("<<옥션 예약 테이블>>");
		lblAuctionTable.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblAuctionTable.setBounds(34, 392, 192, 18);
		buyerPanel.add(lblAuctionTable);
		
		JLabel lblBasicTable = new JLabel("<<예약 구매 테이블>>");
		lblBasicTable.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblBasicTable.setBounds(34, 179, 167, 18);
		buyerPanel.add(lblBasicTable);
		
	}
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
	 */
	public void buttonAction(){
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBounds(873, 12, 375, 55);
		buyerPanel.add(panelBtn);
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setLayout(new GridLayout(1, 3, 5, 0));
		
		
		JButton btnStat = new JButton("통계");
		btnStat.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerTicketStatistics buyerTicketStatistics = new BuyerTicketStatistics();
				buyerTicketStatistics.setVisible(true);
				
			}
		});
		panelBtn.add(btnStat);
		
		
		JButton btnLogout = new JButton("로그아웃");
		btnLogout.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,  "로그아웃했습니다!");
				dispose();
				LoginGui loginGui = new LoginGui();
				loginGui.setVisible(true);
				
			}
		});
		panelBtn.add(btnLogout);
		
		

		JButton btnUserDropOut = new JButton("회원탈퇴");
		btnUserDropOut.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnUserDropOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,  "회원탈퇴했습니다!");
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String sql = "delete from member where id="+Info.user_id+";";
					stmt.executeUpdate(sql);
					
					dispose();
					LoginGui loginGui = new LoginGui();
					loginGui.setVisible(true);
					
					conn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		
		panelBtn.add(btnUserDropOut);
		
		
		
		
		JButton btnTicketDetail = new JButton("조회");
		btnTicketDetail.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnTicketDetail.setBounds(1135, 129, 86, 38);
		btnTicketDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = ticketModel.getRowCount() - 1; i > -1; i--) {
					ticketModel.removeRow(i);
			    }
				
				for (int i = auctionTableModel.getRowCount() - 1; i > -1; i--) {
					auctionTableModel.removeRow(i);
			    }
				
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String date1 = fromYear.getText() + "-" + fromMonth.getText() + "-" + fromDay.getText() + " 00:00:00";
					String date2 = toYear.getText() + "-" + toMonth.getText() + "-" + toDay.getText() + " 00:00:00";
					String homeTeam = teamList.getSelectedValue().toString();
					String sql = "select * from ticket where ticketDate<'"+date1+"' and ticketDate>'"+date2+"' and ticketPrice!=0 and homeTeamId='"+homeTeam+"';";
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()){
				        String ticketId = rs.getString(1);
				        String sellerId = rs.getString(2);
				        String ticketdate = rs.getString(3);
				        String city = rs.getString(5);
				        String home = rs.getString(6);
				        String away = rs.getString(7);
				        String seat = rs.getString(8);
				        String price = rs.getString(9);
				        
				        String input[] = {ticketId, ticketdate, city, home, away, seat, price};
				        ticketModel.addRow(input);
				    }
					
					sql = "select A.id, T.* from auction A, ticket T where A.ticketId=T.id and T.ticketDate<'"+date1+"' and T.ticketDate>'"+date2+"';";
					rs = stmt.executeQuery(sql);
					while(rs.next()){
				        String AuctionId = rs.getString(1);
				        String ticketdate = rs.getString(4);
				        String city = rs.getString(6);
				        String home = rs.getString(7);
				        String away = rs.getString(8);
				        String seat = rs.getString(9);
				        String price = rs.getString(10);
				        
				        String input[] = {AuctionId, ticketdate, city, home, away, seat, price};
				        auctionTableModel.addRow(input);
				    }
					
					conn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		buyerPanel.add(btnTicketDetail);
		
		
		JButton btnReservedTicket = new JButton("예약 조회");
		btnReservedTicket.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 20));
		btnReservedTicket.setBounds(1081, 613, 167, 48);
		btnReservedTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerReservedTicket buyerReservedTicket = new BuyerReservedTicket();
				buyerReservedTicket.setVisible(true);
			}
		});
		buyerPanel.add(btnReservedTicket);
		
		JButton btnRefresh = new JButton("새로고침");
		btnRefresh.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnRefresh.setBounds(34, 12, 121, 55);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				BuyerMainPage buyerMainPage = new BuyerMainPage();
				buyerMainPage.setVisible(true);
			}
		});
		buyerPanel.add(btnRefresh);
		
		
		
	}
	
	/*
 	* Function: createTable 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 회원(구매자 및 판매자)가 등록 및 예약에 필요한 티켓을 출력해주는 리스트(표)를 생성하는 함수
 	*/
	public void createTable(String[][] listContents, JTable table){
		

		table.setEnabled(false);	//테이블 셀 편집 가능 여부
		table.getColumn(table.getColumnName(1)).setPreferredWidth(250);	//초기 셀 가로 크기 지정
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		table.setRowHeight(25);
		
		table.setBackground(Color.WHITE);
		
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)

		DefaultTableCellRenderer tableCenterSort = new DefaultTableCellRenderer();
		DefaultTableCellRenderer tableRightSort = new DefaultTableCellRenderer();
		

		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정

		tableCenterSort.setHorizontalAlignment(SwingConstants.CENTER);
		tableRightSort.setHorizontalAlignment(SwingConstants.RIGHT);
		// 정렬할 테이블의 ColumnModel을 가져옴

		TableColumnModel tcmSchedule = table.getColumnModel();


		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for(int i=0; i<tcmSchedule.getColumnCount(); i++){
			tcmSchedule.getColumn(i).setCellRenderer(tableCenterSort);
		}
		tcmSchedule.getColumn(6).setCellRenderer(tableRightSort);
		
		
		
	}
	
	/*
 	* Function: tableAction() 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 회원(구매자 및 판매자)의 티켓 리스트를 선택할시 작동하는 기능이 담겨있는 함수
 	*/
	
	public void tableAction(JTable table, int tableType){
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
				int col = table.columnAtPoint(arg0.getPoint());
				if(row>=0){
					switch(tableType){
					case 0:
						BuyerTicketDetailsPage buyerTicketDetailsPage = new BuyerTicketDetailsPage(table.getValueAt(row,0).toString());
						buyerTicketDetailsPage.setVisible(true);
						break;
					case 1:
						BuyerAuctionTicketDetailsPage buyerAuctionTicketDetailsPage = new BuyerAuctionTicketDetailsPage(table.getValueAt(row,0).toString());
						buyerAuctionTicketDetailsPage.setVisible(true);
						break;
					}
				}
			}
		});
	}
	
	/*
 	* Function: ticketToDateList 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 티켓리스트 출력에 어디부터~, 를 정렬할 때 필요한 기입란을 생성 및 작동하는 함수
 	*/
	public void ticketToDateList(){

		
		JLabel lblNewLabel = new JLabel("시간(년/월/일)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblNewLabel.setBounds(247, 129, 158, 38);
		buyerPanel.add(lblNewLabel);
		
		
		JLabel label_1 = new JLabel("/");
		label_1.setFont(new Font("굴림", Font.PLAIN, 25));
		label_1.setBounds(889, 129, 24, 38);
		buyerPanel.add(label_1);
		
		toDay = new JTextField();
		toDay.setHorizontalAlignment(SwingConstants.CENTER);
		toDay.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		toDay.setColumns(10);
		toDay.setBounds(648, 129, 67, 38);
		buyerPanel.add(toDay);
		
		JLabel label_2 = new JLabel("/");
		label_2.setFont(new Font("굴림", Font.PLAIN, 25));
		label_2.setBounds(623, 129, 24, 38);
		buyerPanel.add(label_2);
		
		toMonth = new JTextField();
		toMonth.setHorizontalAlignment(SwingConstants.CENTER);
		toMonth.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		toMonth.setColumns(10);
		toMonth.setBounds(542, 129, 67, 38);
		buyerPanel.add(toMonth);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("굴림", Font.PLAIN, 25));
		label_3.setBounds(518, 129, 24, 38);
		buyerPanel.add(label_3);
		
		toYear = new JTextField();
		toYear.setHorizontalAlignment(SwingConstants.CENTER);
		toYear.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		toYear.setColumns(10);
		toYear.setBounds(419, 129, 86, 38);
		buyerPanel.add(toYear);
	}
	
	/*
	* Function: ticketFromDateList 
	* Created: 2019.05.24 - 2019.06.03
	* Author: 
	* Description: 티켓리스트 출력에 ~어디까지, 를 정렬할 때 필요한 기입란을 생성 및 작동하는 함수
	*/
	public void ticketFromDateList(){
		

		fromYear = new JTextField();
		fromYear.setHorizontalAlignment(SwingConstants.CENTER);
		fromYear.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		fromYear.setColumns(10);
		fromYear.setBounds(789, 129, 86, 38);
		buyerPanel.add(fromYear);
		
		fromMonth = new JTextField();
		fromMonth.setHorizontalAlignment(SwingConstants.CENTER);
		fromMonth.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		fromMonth.setColumns(10);
		fromMonth.setBounds(913, 129, 67, 38);
		buyerPanel.add(fromMonth);
		
		fromDay = new JTextField();
		fromDay.setHorizontalAlignment(SwingConstants.CENTER);
		fromDay.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		fromDay.setColumns(10);
		fromDay.setBounds(1022, 129, 67, 38);
		buyerPanel.add(fromDay);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("굴림", Font.PLAIN, 25));
		label_3.setBounds(994, 129, 24, 38);
		buyerPanel.add(label_3);
		
		JLabel label = new JLabel("~");
		label.setFont(new Font("굴림", Font.PLAIN, 25));
		label.setBounds(742, 129, 33, 38);
		buyerPanel.add(label);
		
	}
}
