package org.testo.admin.model;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "shipment")
public class Shipment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Order order;

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
