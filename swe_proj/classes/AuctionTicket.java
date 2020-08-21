package classes;

public class AuctionTicket {
	public String auctionId;
	public String winningBid;
	public String winningBidder;
	public Ticket ticket;
	public String timer;
	
	public AuctionTicket(String auctionId, String winningBid, String winningBidder, String ticketdate, String city, String home,
			String away, String seat, String timer) {
		super();
		this.auctionId = auctionId;
		this.winningBid = winningBid;
		this.winningBidder = winningBidder;
		this.ticket = new Ticket(ticketdate, city, home, away, seat);
		this.timer = timer;
	}
}
