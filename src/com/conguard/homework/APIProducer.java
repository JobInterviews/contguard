package com.conguard.homework;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;



public class APIProducer {
	
	public List<Interval> combineIntervals(List<Interval> intervals)  
    {  
        if (intervals == null || intervals.isEmpty())  
            return Collections.emptyList();  
        
        List<Interval> sortedIntervals = sort(intervals); //create a new list in case the intervals list is immutable (can't sort it)
        Map<Integer, Stack<Interval>> vesselIntervals = new HashMap<>();
        
        sortedIntervals.forEach(interval ->         	
        	 addinterval(interval, vesselIntervals)
        );
        
        List<Interval> combinedIntervals = collectIntervals(vesselIntervals);
 
        return combinedIntervals;
    }

	private List<Interval> sort(List<Interval> intervals) {
		return intervals.stream()
                .sorted((i1, i2) -> i1.start.compareTo(i2.start))
                .collect(Collectors.toList());
	} 

	
	private void addinterval(Interval interval, Map<Integer, Stack<Interval>> vesselIntervals) {
		
		vesselIntervals.computeIfAbsent(interval.vessel, k -> {
			Stack<Interval> stack = new Stack<>();
			stack.push(interval);
			return stack;
		});
		
	private List<Interval> collectIntervals(Map<Integer, Stack<Interval>> vesselIntervals) {
		return vesselIntervals.values()
				.stream()
				.flatMap(stack -> stack.stream())
				.collect(Collectors.toList());
	}
		
//		List<Interval> intervals = new ArrayList<>();
//		
//		Map<Integer, Stack<Interval>> sss = intervals.stream()
//				.collect(Collectors.groupingBy(Interval::getVessel, HashMap::new, Collectors.toCollection(Stack::new)));
		 
		
	}
	
	
//	public Map<Integer, Stack> groupingByStringLength(List source, Supplier<Map<Integer, Stack>> mapSupplier,
//			Supplier<Stack> listSupplier) {
//		return (Map<Integer, Stack>) source.stream()
//				.collect(Collectors.groupingBy(Interval::getVessel, HashMap::new, Collectors.toCollection(Stack::new)));
//	}
	
	
//	
//	private void addInterval(Interval interval, Stack<Interval> stack) {
//		Interval top = stack.peek();  
//		 
//		 if (top.end.isBefore(interval.start)) 
//			 stack.push(interval);
//		 else if (top.end.isBefore(interval.end))  
//		     top.end = interval.end;
//	}
//	
	
	
	public static void main(String[] args) {
		//Test
		List<Interval> intervals = List.of(
				new Interval("A", 1, "NY", "ANWERP", LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 14)),
				new Interval("A", 2, "ANTWERP", "HAIFA", LocalDate.of(2021, 1, 14), LocalDate.of(2021, 1, 20)),
				new Interval("B", 2, "ANTWERP", "HAIFA", LocalDate.of(2021, 1, 14), LocalDate.of(2021, 1, 20)),
				new Interval("B", 3, "HAIFA", "ASHDOD", LocalDate.of(2021, 1, 20), LocalDate.of(2021, 1, 21)),
				new Interval("C", 2, "HAIFA", "ANTWERP", LocalDate.of(2021, 1, 20), LocalDate.of(2021, 1, 26)),
				new Interval("D", 3, "ASHDOD", "KOBE", LocalDate.of(2021, 1, 24), LocalDate.of(2021, 2, 10)),
				new Interval("D", 3, "ASHDOD", "KOBE", LocalDate.of(2021, 1, 24), LocalDate.of(2021, 2, 15)),
				new Interval("D", 3, "ASHDOD", "KOBE", LocalDate.of(2021, 1, 26), LocalDate.of(2021, 2, 10)),
				new Interval("D", 3, "ASHDOD", "KOBE", LocalDate.of(2021, 1, 26), LocalDate.of(2021, 2, 10)),
				new Interval("A", 2, "ANTWERP", "HAIFA", LocalDate.of(2021, 1, 27), LocalDate.of(2021, 2, 20))
				
			    );
		
		APIProducer APIProducer =  new APIProducer();
		APIProducer.combineIntervals(intervals).forEach(System.out::println);
		
		
		
		//Demo
		List<Telemetry> telemetries = new ArrayList<>();
		TelemetryAPI ExternalAPI = new TelemetryAPI() {
			@Override
			public List<Telemetry> getHistoricalAisData(Integer vessel, LocalDate start, LocalDate end) {
				// TODO Auto-generated method stub
				return Collections.emptyList();
			}
		};

		List<Interval> combineIntervals = APIProducer.combineIntervals(intervals);

		for (Interval i : combineIntervals)
			telemetries.addAll(ExternalAPI.getHistoricalAisData(i.vessel, i.start, i.end));
		
	}

}
