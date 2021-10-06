package com.conguard.homework;

import java.time.LocalDate;

public class Interval {

	public String shipment;
	public int vessel;
	public String sourcePort;
	public String destinationPort;
	public LocalDate start;
	public LocalDate end;
	
	public Interval(String shipment, int vessel, String sourcePort, String destinationPort, LocalDate start, LocalDate end) {
		super();
		this.shipment = shipment;
		this.vessel = vessel;
		this.sourcePort = sourcePort;
		this.destinationPort = destinationPort;
		this.start = start;
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "vessel: " + vessel + ", start: " + start + ", end: " + end ;
	}
	
	public int getVessel() {
		return vessel;
	}
}
