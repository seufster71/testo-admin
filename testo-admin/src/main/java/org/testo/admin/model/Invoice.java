package org.testo.admin.model;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "invoice")
public class Invoice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String status;
	private Shipment shipment;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Shipment getShipment() {
		return shipment;
	}
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	
}
