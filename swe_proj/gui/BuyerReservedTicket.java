
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 구매자가 현재까지 예약한 티켓들의 리스트를 보여주는 User Interface
 */


package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import classes.Reservation;
import classes.ReservationManagementSystem;
import classes.Ticket;
import classes.TicketManagementSystem;
import utility.Info;

import javax.swing.JButton;
import javax.swing.JLabel;

public class BuyerReservedTicket extends JFrame {

	private JPanel panelBuyerReserved;
	private String reservationTableHeader[] = {"예약ID", "야구 경기 날짜", "도시", "홈팀", "어웨이팀", "좌석위치", "가격"};
	private JTable reservedTable;
	ReservationManagementSystem reservationManagementSystem = new ReservationManagementSystem();

	/**
	 * Create the frame.
	 */
	public BuyerReservedTicket() {
		setBounds(100, 100, 800, 600);
		
		setTitle("구매자 티켓 예약 조회");
		
		
		panelBuyerReserved = new JPanel();
		panelBuyerReserved.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelBuyerReserved.setLayout(null);
		
		setContentPane(panelBuyerReserved);
		getContentPane().setBackground(Color.WHITE);
		
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
		
     // reservedList
     		String[][] reservedListContents = {};
             DefaultTableModel reservedTableModel = new DefaultTableModel(reservedListContents, reservationTableHeader); 
     		reservedTable = new JTable(reservedTableModel);
     		try {
     			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
     			Statement stmt = conn.createStatement();
     			String sql = "select R.id, T.*, R.reservationDate from reservation R, ticket T where R.buyerId = " + Info.buyer_id + " and T.id = R.ticketId;";
     			ResultSet rs = stmt.executeQuery(sql);
     			
     			while(rs.next()){
     		        int reservationId = rs.getInt(1);
     		        String sellerId = rs.getString(3);
     		        String ticketdate = rs.getString(4);
     		        String city = rs.getString(6);
     		        String home = rs.getString(7);
     		        String away = rs.getString(8);
     		        int seat = rs.getInt(9);
     		        int price = rs.getInt(10);
     		        String reservationDate = rs.getString(11);
     		        
     		       Reservation reservation = new Reservation(reservationId, Info.buyer_id, ticketdate, city, home, away, seat, price, reservationDate);
     		       reservationManagementSystem.addReservation(reservation);
   		        
     		        
     		    }
     			conn.close();
     		} catch (SQLException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
     		
     		for(int i=0;i<reservationManagementSystem.reservationList.size();i++) {
            	Reservation reservation = reservationManagementSystem.reservationList.get(i);
            	String input[] = {""+reservation.reservationId, reservation.reservationDate, reservation.ticket.city, reservation.ticket.homeTeam, 
            			reservation.ticket.awayTeam, ""+reservation.ticket.seat, ""+reservation.ticket.ticketPrice};
 		        reservedTableModel.addRow(input);
            }

     		
     		createTable(reservedListContents, reservedTable);
     		tableAction(reservedTable);
    		
    		
    		
    		JScrollPane scrollPane_1 = new JScrollPane(reservedTable);
    		scrollPane_1.setBounds(41, 83, 700, 377);
    		panelBuyerReserved.add(scrollPane_1);
    		
		
		
    		
    			JLabel lblTitle = new JLabel("구매자 예약 현황");
    			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
    			lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 22));
    			lblTitle.setBounds(237, 12, 311, 37);
    			panelBuyerReserved.add(lblTitle);
    		
	}
	
	/*
 	* Function: tableAction() 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 회원(구매자 및 판매자)의 티켓 리스트를 선택할시 작동하는 기능이 담겨있는 함수
 	*/
	public void tableAction(JTable table){
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
				int col = table.columnAtPoint(arg0.getPoint());
				if(row>=0){
						BuyerReservedTicketDetailsPage buyerReservedTicketDetailsPage = new BuyerReservedTicketDetailsPage(table.getValueAt(row,0).toString());
						dispose();
						buyerReservedTicketDetailsPage.setVisible(true);
						
					}
				
				}
		});
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
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
	 */
	public void buttonAction(){
		JButton btnBack = new JButton("뒤로가기");
		btnBack.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(595, 492, 124, 48);
		panelBuyerReserved.add(btnBack);
		
	}
}
