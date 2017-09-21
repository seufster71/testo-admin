package org.testo.admin.model;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Customer customer;
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
