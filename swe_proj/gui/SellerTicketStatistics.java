
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 판매자가 지금까지 등록한 티켓의 총 현황과 티켓의 총 판매금액을 출력하는 User Interface 
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import utility.Info;

public class SellerTicketStatistics extends JFrame {

	private JPanel panelSellerStat;
	public JTable ticketTable;
	private JPanel panelDate = new JPanel();
	private String tableHeader[] = {"티켓명", "구매가격", "예약된 날짜", "야구 경기 날짜", "도시", "홈팀", "어웨이팀", "좌석위치"};
	DefaultTableModel ticketModel;
	JTextField fromYear;
	JTextField fromMonth;
	JTextField fromDay;
	JTextField toYear;
	JTextField toMonth;
	JTextField toDay;
	JLabel totalPayment;
	
	public SellerTicketStatistics() {
		setBounds(100, 100, 1000, 600);
		
		panelSellerStat = new JPanel();
		
		setTitle("판매자 티켓 통계");
		panelSellerStat.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSellerStat);
		getContentPane().setBackground(Color.WHITE);
		panelSellerStat.setLayout(null);
		

		String[][] tableListContents = {};
        ticketModel = new DefaultTableModel(tableListContents, tableHeader); 
		ticketTable = new JTable(ticketModel);
		
		createTable(tableListContents, ticketTable);
		
		

		
		
		JScrollPane scrollpane = new JScrollPane(ticketTable);
		scrollpane.setBounds(50, 118, 894, 319);
		panelSellerStat.add(scrollpane);
		
		
		
		panelDate.setBackground(Color.WHITE);
		panelDate.setBounds(163, 29, 707, 53);
		panelSellerStat.add(panelDate);
		panelDate.setLayout(null);
		
		
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
		
		
		
		// 2019.05.31 totalPayment = new JLabel(seller의 티켓 총 합 금액); 식으로 수정 부탁드립니다(고관홍)
		totalPayment = new JLabel("0");
		totalPayment.setFont(new Font("맑은 고딕", Font.PLAIN, 21));
		totalPayment.setBounds(525, 449, 200, 80);
		
		panelSellerStat.add(totalPayment);
		
		JLabel lblTotalPrice = new JLabel("총수익금액");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPrice.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		lblTotalPrice.setBounds(345, 449, 131, 80);
		panelSellerStat.add(lblTotalPrice);
		
		

		JLabel lblNewLabel = new JLabel("시간(년/월/일)");
		lblNewLabel.setBounds(0, 12, 128, 27);
		panelDate.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		
		
		
		JLabel label_4 = new JLabel("~");
		label_4.setBounds(351, 5, 33, 38);
		panelDate.add(label_4);
		label_4.setFont(new Font("굴림", Font.PLAIN, 25));
		

		
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			
			String sql = "select T.*, R.reservationDate from reservation R, ticket T where R.ticketId=T.id and T.sellerId="+Info.seller_id+";";
			ResultSet rs = stmt.executeQuery(sql);
			
			int total_cost = 0;
			while(rs.next()){
		        String ticketId = rs.getString(1);
		        String sellerId = rs.getString(2);
		        String ticketdate = rs.getString(3);
		        String city = rs.getString(5);
		        String home = rs.getString(6);
		        String away = rs.getString(7);
		        String seat = rs.getString(8);
		        String price = rs.getString(9);
		        String reservationDate = rs.getString(10);
		        
		        total_cost+=Integer.parseInt(price);
		        
		        String input[] = {ticketId, price,reservationDate, ticketdate, city, home, away, seat};
		        ticketModel.addRow(input);
		    }
			totalPayment.setText(""+total_cost);
			
			conn.close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		
		
	}
	
	/*
 	* Function: createTable 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 회원(구매자 및 판매자)가 등록 및 예약에 필요한 티켓을 출력해주는 리스트(표)를 생성하는 함수
 	*/
	
	public void createTable(String[][] listContents, JTable table){
		
		
		table.setEnabled(false);	//테이블 셀 편집 가능 여부
		table.getColumn(table.getColumnName(2)).setPreferredWidth(200);	//초기 셀 가로 크기 지정
		table.getColumn(table.getColumnName(3)).setPreferredWidth(200);	//초기 셀 가로 크기 지정
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		table.setRowHeight(23);
		
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
		tcmSchedule.getColumn(1).setCellRenderer(tableRightSort);
		
		
		
	}

	
	/*
 	* Function: ticketToDateList 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 티켓리스트 출력에 어디부터~, 를 정렬할 때 필요한 기입란을 생성 및 작동하는 함수
 	*/

	public void ticketToDateList(){
		

	toYear = new JTextField();
	toYear.setHorizontalAlignment(SwingConstants.RIGHT);
	toYear.setBounds(143, 11, 70, 30);
	panelDate.add(toYear);
	toYear.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
	toYear.setColumns(10);
	
	JLabel label_3 = new JLabel("/");
	label_3.setBounds(220, 9, 24, 30);
	panelDate.add(label_3);
	label_3.setFont(new Font("굴림", Font.PLAIN, 25));
	
	toMonth = new JTextField();
	toMonth.setHorizontalAlignment(SwingConstants.RIGHT);
	toMonth.setBounds(235, 11, 45, 30);
	panelDate.add(toMonth);
	toMonth.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
	toMonth.setColumns(10);
	
	JLabel label_2 = new JLabel("/");
	label_2.setBounds(285, 9, 24, 30);
	panelDate.add(label_2);
	label_2.setFont(new Font("굴림", Font.PLAIN, 25));
	
	toDay = new JTextField();
	toDay.setHorizontalAlignment(SwingConstants.RIGHT);
	toDay.setBounds(300, 11, 45, 30);
	panelDate.add(toDay);
	toDay.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
	toDay.setColumns(10);
		
		
		
		
	}
	
	/*
 	* Function: ticketFromDateList 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 티켓리스트 출력에 ~어디까지, 를 정렬할 때 필요한 기입란을 생성 및 작동하는 함수
 	*/

	public void ticketFromDateList(){
		
		fromYear = new JTextField();
		fromYear.setHorizontalAlignment(SwingConstants.RIGHT);
		fromYear.setBounds(387, 11, 70, 30);
		panelDate.add(fromYear);
		fromYear.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		fromYear.setColumns(10);
		
		JLabel label = new JLabel("/");
		label.setBounds(465, 9, 24, 30);
		panelDate.add(label);
		label.setFont(new Font("굴림", Font.PLAIN, 25));
		
		fromMonth = new JTextField();
		fromMonth.setHorizontalAlignment(SwingConstants.RIGHT);
		fromMonth.setBounds(480, 11, 45, 30);
		panelDate.add(fromMonth);
		fromMonth.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		fromMonth.setColumns(10);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(531, 9, 24, 30);
		panelDate.add(label_1);
		label_1.setFont(new Font("굴림", Font.PLAIN, 25));
		
		fromDay = new JTextField();
		fromDay.setHorizontalAlignment(SwingConstants.RIGHT);
		fromDay.setBounds(547, 11, 45, 30);
		panelDate.add(fromDay);
		fromDay.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		fromDay.setColumns(10);

	}
	
	
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 버튼을 생성, 출력, 및 기능에 필요한 기능이 담겨있는 함수
	 */

	public void buttonAction(){
		

				
				
				JButton btnTicketDetail = new JButton("조회");
				btnTicketDetail.setBounds(613, 5, 80, 41);
				panelDate.add(btnTicketDetail);
				btnTicketDetail.setFont(new Font("맑은 고딕", Font.BOLD, 19));
				btnTicketDetail.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/*판매자가 보고 싶은 티켓 날짜별로 조회 기능 함수 추가 바람(고관홍)*/
						for (int i = ticketModel.getRowCount() - 1; i > -1; i--) {
							ticketModel.removeRow(i);
					    }
						try {
							Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
							Statement stmt = conn.createStatement();
							String date1 = fromYear.getText() + "-" + fromMonth.getText() + "-" + fromDay.getText() + " 00:00:00";
							String date2 = toYear.getText() + "-" + toMonth.getText() + "-" + toDay.getText() + " 00:00:00";
							String sql = "select T.*, R.reservationDate from reservation R, ticket T where R.ticketId=T.id and T.sellerId="+Info.seller_id+" and R.reservationDate<'"+date1+"' and R.reservationDate>'"+date2+"';";
							ResultSet rs = stmt.executeQuery(sql);
							
							int total_cost = 0;
							while(rs.next()){
						        String ticketId = rs.getString(1);
						        String sellerId = rs.getString(2);
						        String ticketdate = rs.getString(3);
						        String city = rs.getString(5);
						        String home = rs.getString(6);
						        String away = rs.getString(7);
						        String seat = rs.getString(8);
						        String price = rs.getString(9);
						        String reservationDate = rs.getString(10);
						        
						        total_cost+=Integer.parseInt(price);
						        
						        String input[] = {ticketId, price,reservationDate,  ticketdate, city, home, away, seat};
						        ticketModel.addRow(input);
						    }
							totalPayment.setText(""+total_cost);
							
							conn.close();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				});
		
	}
}
