package org.testo.admin.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "order")
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Customer customer;
	private List<OrderItem> orderItems;
	private String status;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
