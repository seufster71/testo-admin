package org.testo.admin.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "way_point")
public class WayPoint extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String interSection;
	private Date startTime;
	private Date endTime;
	
}
