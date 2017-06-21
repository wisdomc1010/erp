package pres.wisdom.entity;

import java.math.BigDecimal;

import pres.wisdom.vo.Page;

public class AccountPage extends Page {
	 	private Integer id;

	    private BigDecimal balance;

	    private Integer clientId;
	    
	    private String clientName;

	    private Integer type;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public BigDecimal getBalance() {
			return balance;
		}

		public void setBalance(BigDecimal balance) {
			this.balance = balance;
		}

		public Integer getClientId() {
			return clientId;
		}

		public void setClientId(Integer clientId) {
			this.clientId = clientId;
		}

		public String getClientName() {
			return clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}
	    
	    
}
