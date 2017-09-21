package org.testo.admin.model;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "shipment_item")
public class ShipmentItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Shipment shipment;
	private OrderItem orderItem;
	private Product product;
	
	public Shipment getShipment() {
		return shipment;
	}
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
