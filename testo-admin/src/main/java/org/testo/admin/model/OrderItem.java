package org.testo.admin.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "order_item")
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Order order;
	private Product product;
	private BigDecimal price;
	private String status;

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
