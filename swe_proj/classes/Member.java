package classes;

public class Member {
		private int id;
		private String password;
		private String personalNumber;
		private String email;
		private String address;
		private String name;
		private boolean memberType;
		
		public Member(){
			
		}
		public void checkForMemberType(){
			
		}
		public void setMemberData(){
			
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPersonalNumber() {
			return personalNumber;
		}
		public void setPersonalNumber(String personalNumber) {
			this.personalNumber = personalNumber;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isMemberType() {
			return memberType;
		}
		public void setMemberType(boolean memberType) {
			this.memberType = memberType;
		}
		
}
