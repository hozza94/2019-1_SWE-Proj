

/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: 판매자가 지금까지 등록한 티켓의 리스트 출력과 새로 등록하고 싶은 티켓 기능을 보여주는 User Interface
 */

package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import classes.Seller;
import utility.Info;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class SellerMainPage extends JFrame {

	private JPanel panelSeller;
	
	private JTable ticketTable;
	private Seller seller;
	
	private String tableHeader[] = {"티켓명", "구매가격", "야구 경기 날짜", "도시", "홈팀", "어웨이팀", "좌석위치"};

	
	JTextField toYear;
	JTextField toMonth;
	JTextField toDay;
	JTextField fromYear;
	JTextField fromMonth;
	JTextField fromDay;
	DefaultTableModel ticketTablemodel;


	public SellerMainPage() {
	
		
		//seller클래스 이용하기 위해 	2019/05/31 수정
		seller = new Seller();
		seller.setSellerId(Info.seller_id);
		seller.setId(Info.member_id);
		seller.setEmail(Info.userEmail);
		
		setTitle("판매자 페이지");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		panelSeller = new JPanel();
		panelSeller.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelSeller);
		getContentPane().setBackground(Color.WHITE);
		panelSeller.setLayout(null);
		

		//판매자 페이지 초기화 (티켓정보 불러오기)
		initialize();
		
		//------------
		ticketToDateList();
		ticketFromDateList();
		buttonAction();
	}
	
	/*
 	* Function: createTable 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: 회원(구매자 및 판매자)가 등록 및 예약에 필요한 티켓을 출력해주는 리스트(표)를 생성하는 함수
 	*/

	public void createTable(String[][] listContents, JTable table){
		
		
		table.setEnabled(false);	//테이블 셀 편집 가능 여부
		table.getColumn(table.getColumnName(2)).setPreferredWidth(300);	//초기 셀 가로 크기 지정
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
		tcmSchedule.getColumn(1).setCellRenderer(tableRightSort);
		
		
		
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
				if(row>=0 && col==0){
					switch(tableType){
					case 0:
						SellerTicketDetailsPage sellerTicketDetailsPage = new SellerTicketDetailsPage(
								table.getValueAt(row,col).toString(),
								table.getValueAt(row,col+1).toString(),
								table.getValueAt(row,col+2).toString(),
								table.getValueAt(row,col+3).toString(),
								table.getValueAt(row,col+4).toString(),
								table.getValueAt(row,col+5).toString(),
								table.getValueAt(row,col+6).toString()
								);
						sellerTicketDetailsPage.setVisible(true);
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
		panelSeller.add(lblNewLabel);
		

		
		
		
		
		JLabel label_1 = new JLabel("/");
		label_1.setFont(new Font("굴림", Font.PLAIN, 25));
		label_1.setBounds(889, 129, 24, 38);
		panelSeller.add(label_1);
		
		toDay = new JTextField();
		toDay.setHorizontalAlignment(SwingConstants.CENTER);
		toDay.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		toDay.setColumns(10);
		toDay.setBounds(648, 129, 67, 38);
		panelSeller.add(toDay);
		
		JLabel label_2 = new JLabel("/");
		label_2.setFont(new Font("굴림", Font.PLAIN, 25));
		label_2.setBounds(623, 129, 24, 38);
		panelSeller.add(label_2);
		
		toMonth = new JTextField();
		toMonth.setHorizontalAlignment(SwingConstants.CENTER);
		toMonth.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		toMonth.setColumns(10);
		toMonth.setBounds(542, 129, 67, 38);
		panelSeller.add(toMonth);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("굴림", Font.PLAIN, 25));
		label_3.setBounds(518, 129, 24, 38);
		panelSeller.add(label_3);
		
		toYear = new JTextField();
		toYear.setHorizontalAlignment(SwingConstants.CENTER);
		toYear.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		toYear.setColumns(10);
		toYear.setBounds(419, 129, 86, 38);
		panelSeller.add(toYear);
		
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
		panelSeller.add(fromYear);
		
		fromDay = new JTextField();
		fromDay.setHorizontalAlignment(SwingConstants.CENTER);
		fromDay.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		fromDay.setColumns(10);
		fromDay.setBounds(1022, 129, 67, 38);
		panelSeller.add(fromDay);
		
		fromMonth = new JTextField();
		fromMonth.setHorizontalAlignment(SwingConstants.CENTER);
		fromMonth.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		fromMonth.setColumns(10);
		fromMonth.setBounds(913, 129, 67, 38);
		panelSeller.add(fromMonth);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("굴림", Font.PLAIN, 25));
		label_3.setBounds(994, 129, 24, 38);
		panelSeller.add(label_3);
		
		JLabel label = new JLabel("~");
		label.setFont(new Font("굴림", Font.PLAIN, 25));
		label.setBounds(742, 129, 33, 38);
		panelSeller.add(label);
		
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
		panelSeller.add(panelBtn);
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setLayout(new GridLayout(1, 3, 5, 0));
		
		JButton btnStat = new JButton("통계");
		btnStat.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerTicketStatistics sellerTicketStatistics = new SellerTicketStatistics();
				sellerTicketStatistics.setVisible(true);
				
			}
		});
		panelBtn.add(btnStat);
		
		JButton btnLogout = new JButton("로그아웃");
		btnLogout.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,  "로그아웃했습니다!");
				LoginGui loginGui = new LoginGui();
				loginGui.setVisible(true);
				dispose();
			}
		});
		panelBtn.add(btnLogout);
		
		
		
		JButton btnUserDropOut = new JButton("회원탈퇴");
		btnUserDropOut.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnUserDropOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*판매자 등록한 티켓이 없는지 확인 및 회원DB에 회원삭제 기능 추가 예정*/
				//완료 2019/06/01
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String sql = "select * from ticket "
							+ "where sellerId=" + seller.getSellerId()
							+ ";";
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						JOptionPane.showMessageDialog(null,  "등록한 티켓이 있습니다. 탈퇴할 수 없습니다");
						rs.close();
						stmt.close();
						conn.close();
				    }
					else {
						sql = "delete from seller where id =" + seller.getSellerId() + ";";
						stmt.executeUpdate(sql);
						sql = "delete from member where id =" + seller.getId() + ";";
						stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null,  "탈퇴 완료");
						rs.close();
						stmt.close();
						conn.close();
						
						LoginGui loginGui = new LoginGui();
						loginGui.setVisible(true);
						dispose();
					}
					
					
				} catch (SQLException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				
			}
		});
		panelBtn.add(btnUserDropOut);
		
		
		//2019.05.31 새로고침 버튼을 추가했으면 합니다. 티켓 등록이나 수정 후 테이블에 바로 반영하려면 버튼을 눌러 새로 정보를 가져오거나 해야할 듯 합니다		
		//2019.06.01 새로고침 버튼 추가
		JButton btnRefresh = new JButton("새로고침");
		btnRefresh.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnRefresh.setBounds(34, 12, 121, 55);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				SellerMainPage sellerMainPage = new SellerMainPage();
				sellerMainPage.setVisible(true);
			}
		});
		panelSeller.add(btnRefresh);
		
		
		JButton btnTicketDetail = new JButton("조회");
		btnTicketDetail.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		btnTicketDetail.setBounds(1135, 129, 86, 38);
		btnTicketDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*등록한 티켓 날짜별로 정렬 기능 함수 추가 예정*/
				for (int i = ticketTablemodel.getRowCount() - 1; i > -1; i--) {
					ticketTablemodel.removeRow(i);
			    }
				
				
				try {
					Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
					Statement stmt = conn.createStatement();
					String date1 = fromYear.getText() + "-" + fromMonth.getText() + "-" + fromDay.getText() + " 00:00:00";
					String date2 = toYear.getText() + "-" + toMonth.getText() + "-" + toDay.getText() + " 00:00:00";
					String sql = "select * from ticket where ticketDate<'"+date1+"' and ticketDate>'"+date2+"' and ticketPrice!=0;";
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()){
				        String ticketId = rs.getString(1);
				        
				        String ticketdate = rs.getString(3);
				        String city = rs.getString(5);
				        String home = rs.getString(6);
				        String away = rs.getString(7);
				        String seat = rs.getString(8);
				        String price = rs.getString(9);
				        
				        String input[] = {ticketId, price, ticketdate, city, home, away, seat};
				        ticketTablemodel.addRow(input);
				    }
					
					
					conn.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		panelSeller.add(btnTicketDetail);
		
		JButton btnTicketInput = new JButton("티켓 등록");
		btnTicketInput.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 20));
		btnTicketInput.setBounds(1075, 597, 167, 48);
		btnTicketInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerTicketRegister sellerTicketRegister = new SellerTicketRegister();
				sellerTicketRegister.setVisible(true);
			}
		});
		panelSeller.add(btnTicketInput);
		
		
		
		
		
	}
	
	
	// -----------판매자 메인 창에 로그인한 판매자의 티켓 리스트를 불러옴 
	//			  2019/05/31 수정 허범영 

	/*
	 * Function: initialize()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: 페이지에 필요한 입출력(Label, list, table 등)을 설정하는 함수
	 */
	public void initialize() {
		
		/*
		 * 2019/05/30 수정 추가
		 아래의 레이블에 사용자이름을 변수로 추가해주세요.
		 최종적인 예상 디자인으로 예를 들어 고관홍의 메인페이지, 가 메인사이트에 나오게 만들고 싶습니다. 
		 
		 추가 완료	2019/05/31
		 */
		
		JLabel lblNewLabel_1 = new JLabel(seller.getEmail());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		lblNewLabel_1.setBounds(477, 23, 145, 55);
		panelSeller.add(lblNewLabel_1);
		
		JLabel lblMain = new JLabel("메인 페이지");
		lblMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblMain.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		lblMain.setBounds(630, 23, 145, 61);
		panelSeller.add(lblMain);
		
		
		String ticketTableContents[][] = null;
				
		ticketTablemodel = new DefaultTableModel(ticketTableContents, tableHeader);
		
		
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select * from ticket "
					+ "where sellerId=" + seller.getSellerId()
					+ " order by ticketPrice DESC;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				
		        String id = rs.getString(1);
		        String sellerId = rs.getString(2);
		        String ticketdate = rs.getString(3);
		        String city = rs.getString(5);
		        String home = rs.getString(6);
		        String away = rs.getString(7);
		        String seat = rs.getString(8);
		        String price = rs.getString(9);
		        
		        
		        
		        String input[] = {id, price, ticketdate, city, home, away, seat};
		        ticketTablemodel.addRow(input);
		    }
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ticketTable = new JTable(ticketTablemodel);
		createTable(ticketTableContents, ticketTable);
		tableAction(ticketTable,0);
		JScrollPane scrollpane = new JScrollPane(ticketTable);
		scrollpane.setBounds(34, 186, 1214, 380);
		panelSeller.add(scrollpane);
		
		
	}
}
