package org.testo.admin.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "server_trace")
public class ServerTrace extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private long userId;
	private String service;
	private String startTime;
	private List<WayPoint> wayPoints;
	
	
}
