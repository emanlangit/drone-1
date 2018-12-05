package com.boeing.drone;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Drone") 
	public class  Drone {
	
	public Drone() {
	}
	
	public Drone(String title, Date firstFlight) {
		this.title = title;
		this.firstFlight = firstFlight;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
    private String title;

    @Column(columnDefinition = "date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date firstFlight;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getFirstFlight() {
		return firstFlight;
	}

	public void setFirstFlight(Date firstFlight) {
		this.firstFlight = firstFlight;
	}
}
