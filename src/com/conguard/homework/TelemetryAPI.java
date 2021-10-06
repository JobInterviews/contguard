package com.conguard.homework;

import java.time.LocalDate;
import java.util.List;

public interface TelemetryAPI {
	
	public List<Telemetry> getHistoricalAisData(Integer vessel,LocalDate start, LocalDate end);
}
