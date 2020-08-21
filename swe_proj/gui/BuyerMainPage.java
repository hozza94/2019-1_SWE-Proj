
/*
 * Created: 2019.05.24 - 2019.06.03
 * Author:
 * Description: �����ڰ� ���డ���� Ƽ��(���� ���ε� ����) �� �����ڰ� �ʿ��� �⺻����� ����� �ִ� User Interface
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

	// 2019.06.01 ReservationTable�� ���õ� GUI�� ���� ���ο� Ŭ���� BuyerReservedTicket.java�� �ű�(���ȫ)
	private JPanel buyerPanel;
	private JTable ticketTable, auctionTable;
	private String ticketTableHeader[] = {"Ƽ��ID", "�߱� ��� ��¥", "����", "Ȩ��", "�������", "�¼���ġ", "����"};
	private String auctionTableHearder[] = {"����ID", "�߱� ��� ��¥", "����", "Ȩ��", "�������", "�¼���ġ", "�ְ� ������"};
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
		setTitle("������ ������");
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
	 * Description: �������� �ʿ��� �����(Label, list, table ��)�� �����ϴ� �Լ�
	 */
	public void initialize(){
		
		// teamList
		String[][] teamListNames = {};
		
		//����Ʈ�� ��ũ�ѹ� �߰�
		JScrollPane teamListSelect = new JScrollPane();
		teamListSelect.setBounds(151,109, 100, 68);
		teamListModel = new DefaultListModel<String>(); 
        teamList = new JList(teamListModel);
        teamList.setFont(new Font("���� ���", Font.PLAIN, 17));
		teamListSelect.setViewportView(teamList);
		//����Ʈ ��輱 ����, ���� ���� ��� ����
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
		
		// �̸��߰�
		try {
			Connection conn = DriverManager.getConnection(Info.url, Info.id, Info.pw);
			Statement stmt = conn.createStatement();
			String sql = "select name from member where id="+Info.user_id+";";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				String name = rs.getString(1);
				
				JLabel lblNewLabel_1 = new JLabel(name);
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
				lblNewLabel_1.setFont(new Font("���� ���", Font.BOLD, 23));
				lblNewLabel_1.setBounds(477, 23, 145, 55);
				buyerPanel.add(lblNewLabel_1);
		    }
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblMain = new JLabel("���� ������");
		lblMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblMain.setFont(new Font("���� ���", Font.BOLD, 23));
		lblMain.setBounds(630, 23, 145, 61);
		buyerPanel.add(lblMain);
		
		
		JLabel lblSelectTeam = new JLabel("Ȩ������");
		lblSelectTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTeam.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblSelectTeam.setBounds(34, 129, 113, 38);
		buyerPanel.add(lblSelectTeam);
		
		
		JLabel lblAuctionTable = new JLabel("<<���� ���� ���̺�>>");
		lblAuctionTable.setFont(new Font("���� ���", Font.PLAIN, 15));
		lblAuctionTable.setBounds(34, 392, 192, 18);
		buyerPanel.add(lblAuctionTable);
		
		JLabel lblBasicTable = new JLabel("<<���� ���� ���̺�>>");
		lblBasicTable.setFont(new Font("���� ���", Font.PLAIN, 15));
		lblBasicTable.setBounds(34, 179, 167, 18);
		buyerPanel.add(lblBasicTable);
		
	}
	
	/*
	 * Function: buttonAction()
	 * Created: 2019.05.24 - 2019.06.03
	 * Author: 
	 * Description: �������� �ʿ��� ��ư�� ����, ���, �� ��ɿ� �ʿ��� ����� ����ִ� �Լ�
	 */
	public void buttonAction(){
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBounds(873, 12, 375, 55);
		buyerPanel.add(panelBtn);
		panelBtn.setBackground(Color.WHITE);
		panelBtn.setLayout(new GridLayout(1, 3, 5, 0));
		
		
		JButton btnStat = new JButton("���");
		btnStat.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerTicketStatistics buyerTicketStatistics = new BuyerTicketStatistics();
				buyerTicketStatistics.setVisible(true);
				
			}
		});
		panelBtn.add(btnStat);
		
		
		JButton btnLogout = new JButton("�α׾ƿ�");
		btnLogout.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,  "�α׾ƿ��߽��ϴ�!");
				dispose();
				LoginGui loginGui = new LoginGui();
				loginGui.setVisible(true);
				
			}
		});
		panelBtn.add(btnLogout);
		
		

		JButton btnUserDropOut = new JButton("ȸ��Ż��");
		btnUserDropOut.setFont(new Font("���� ���", Font.PLAIN, 20));
		btnUserDropOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,  "ȸ��Ż���߽��ϴ�!");
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
		
		
		
		
		JButton btnTicketDetail = new JButton("��ȸ");
		btnTicketDetail.setFont(new Font("���� ���", Font.PLAIN, 20));
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
		
		
		JButton btnReservedTicket = new JButton("���� ��ȸ");
		btnReservedTicket.setFont(new Font("���� ��� Semilight", Font.PLAIN, 20));
		btnReservedTicket.setBounds(1081, 613, 167, 48);
		btnReservedTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuyerReservedTicket buyerReservedTicket = new BuyerReservedTicket();
				buyerReservedTicket.setVisible(true);
			}
		});
		buyerPanel.add(btnReservedTicket);
		
		JButton btnRefresh = new JButton("���ΰ�ħ");
		btnRefresh.setFont(new Font("���� ���", Font.PLAIN, 20));
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
 	* Description: ȸ��(������ �� �Ǹ���)�� ��� �� ���࿡ �ʿ��� Ƽ���� ������ִ� ����Ʈ(ǥ)�� �����ϴ� �Լ�
 	*/
	public void createTable(String[][] listContents, JTable table){
		

		table.setEnabled(false);	//���̺� �� ���� ���� ����
		table.getColumn(table.getColumnName(1)).setPreferredWidth(250);	//�ʱ� �� ���� ũ�� ����
		table.setFont(new Font("���� ���", Font.PLAIN, 16));
		table.setRowHeight(25);
		
		table.setBackground(Color.WHITE);
		
		
		// DefaultTableCellHeaderRenderer ���� (��� ������ ����)

		DefaultTableCellRenderer tableCenterSort = new DefaultTableCellRenderer();
		DefaultTableCellRenderer tableRightSort = new DefaultTableCellRenderer();
		

		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����

		tableCenterSort.setHorizontalAlignment(SwingConstants.CENTER);
		tableRightSort.setHorizontalAlignment(SwingConstants.RIGHT);
		// ������ ���̺��� ColumnModel�� ������

		TableColumnModel tcmSchedule = table.getColumnModel();


		// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
		for(int i=0; i<tcmSchedule.getColumnCount(); i++){
			tcmSchedule.getColumn(i).setCellRenderer(tableCenterSort);
		}
		tcmSchedule.getColumn(6).setCellRenderer(tableRightSort);
		
		
		
	}
	
	/*
 	* Function: tableAction() 
 	* Created: 2019.05.24 - 2019.06.03
 	* Author: 
 	* Description: ȸ��(������ �� �Ǹ���)�� Ƽ�� ����Ʈ�� �����ҽ� �۵��ϴ� ����� ����ִ� �Լ�
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
 	* Description: Ƽ�ϸ���Ʈ ��¿� ������~, �� ������ �� �ʿ��� ���Զ��� ���� �� �۵��ϴ� �Լ�
 	*/
	public void ticketToDateList(){

		
		JLabel lblNewLabel = new JLabel("�ð�(��/��/��)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblNewLabel.setBounds(247, 129, 158, 38);
		buyerPanel.add(lblNewLabel);
		
		
		JLabel label_1 = new JLabel("/");
		label_1.setFont(new Font("����", Font.PLAIN, 25));
		label_1.setBounds(889, 129, 24, 38);
		buyerPanel.add(label_1);
		
		toDay = new JTextField();
		toDay.setHorizontalAlignment(SwingConstants.CENTER);
		toDay.setFont(new Font("���� ���", Font.PLAIN, 20));
		toDay.setColumns(10);
		toDay.setBounds(648, 129, 67, 38);
		buyerPanel.add(toDay);
		
		JLabel label_2 = new JLabel("/");
		label_2.setFont(new Font("����", Font.PLAIN, 25));
		label_2.setBounds(623, 129, 24, 38);
		buyerPanel.add(label_2);
		
		toMonth = new JTextField();
		toMonth.setHorizontalAlignment(SwingConstants.CENTER);
		toMonth.setFont(new Font("���� ���", Font.PLAIN, 20));
		toMonth.setColumns(10);
		toMonth.setBounds(542, 129, 67, 38);
		buyerPanel.add(toMonth);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("����", Font.PLAIN, 25));
		label_3.setBounds(518, 129, 24, 38);
		buyerPanel.add(label_3);
		
		toYear = new JTextField();
		toYear.setHorizontalAlignment(SwingConstants.CENTER);
		toYear.setFont(new Font("���� ���", Font.PLAIN, 20));
		toYear.setColumns(10);
		toYear.setBounds(419, 129, 86, 38);
		buyerPanel.add(toYear);
	}
	
	/*
	* Function: ticketFromDateList 
	* Created: 2019.05.24 - 2019.06.03
	* Author: 
	* Description: Ƽ�ϸ���Ʈ ��¿� ~������, �� ������ �� �ʿ��� ���Զ��� ���� �� �۵��ϴ� �Լ�
	*/
	public void ticketFromDateList(){
		

		fromYear = new JTextField();
		fromYear.setHorizontalAlignment(SwingConstants.CENTER);
		fromYear.setFont(new Font("���� ���", Font.PLAIN, 20));
		fromYear.setColumns(10);
		fromYear.setBounds(789, 129, 86, 38);
		buyerPanel.add(fromYear);
		
		fromMonth = new JTextField();
		fromMonth.setHorizontalAlignment(SwingConstants.CENTER);
		fromMonth.setFont(new Font("���� ���", Font.PLAIN, 20));
		fromMonth.setColumns(10);
		fromMonth.setBounds(913, 129, 67, 38);
		buyerPanel.add(fromMonth);
		
		fromDay = new JTextField();
		fromDay.setHorizontalAlignment(SwingConstants.CENTER);
		fromDay.setFont(new Font("���� ���", Font.PLAIN, 20));
		fromDay.setColumns(10);
		fromDay.setBounds(1022, 129, 67, 38);
		buyerPanel.add(fromDay);
		
		JLabel label_3 = new JLabel("/");
		label_3.setFont(new Font("����", Font.PLAIN, 25));
		label_3.setBounds(994, 129, 24, 38);
		buyerPanel.add(label_3);
		
		JLabel label = new JLabel("~");
		label.setFont(new Font("����", Font.PLAIN, 25));
		label.setBounds(742, 129, 33, 38);
		buyerPanel.add(label);
		
	}
}
