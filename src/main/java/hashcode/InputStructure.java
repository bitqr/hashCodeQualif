package hashcode;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//To be changed according to the input.in structure description
public class InputStructure {
	
	
	
	public Map<String,Integer> inputDefinitions = new HashMap<String, Integer>();
	public List<RideInput> rides = new ArrayList<RideInput>();
	
	public class RideInput {
		public Position startPosition;
		public Position finishPosition;
		public int earliestStart;
		public int latestFinish;
		
		
		@Override
		public String toString() {
			return "RideInput:\nstartPosition:"+startPosition.toString()+", finishPosition:"+finishPosition.toString()+",\nearliestStart: "+earliestStart+",\nlatestFinish:"+latestFinish;
		}
		
	}
	
	public class Position {
		public int row;
		public int column;
		
		@Override
		public String toString() {
			return "["+row+","+column+"]";
		}
	}
	
	
	
	public InputStructure(String fileName) throws IOException {
		FileHandler.parseInput("src/resources/"+fileName);
		int lineOffset = 0;
		int lineIndex = 0;

		inputDefinitions.put("rows", FileHandler.getIntAt(0,0));
		inputDefinitions.put("columns", FileHandler.getIntAt(0,1));
		inputDefinitions.put("vehicles", FileHandler.getIntAt(0,2));
		inputDefinitions.put("rides", FileHandler.getIntAt(0,3));
		inputDefinitions.put("bonus", FileHandler.getIntAt(0,4));
		inputDefinitions.put("steps", FileHandler.getIntAt(0,5));
		lineOffset = 1;
		for (lineIndex=1; lineIndex<inputDefinitions.get("rides")+lineOffset; lineIndex++) {
			RideInput rideInput = new RideInput();
			rideInput.startPosition = new Position();
			rideInput.startPosition.row = FileHandler.getIntAt(lineIndex,0);
			rideInput.startPosition.column = FileHandler.getIntAt(lineIndex,1);
			rideInput.finishPosition = new Position();
			rideInput.finishPosition.row = FileHandler.getIntAt(lineIndex,2);
			rideInput.finishPosition.column= FileHandler.getIntAt(lineIndex,3);
			rideInput.earliestStart = FileHandler.getIntAt(lineIndex,4);
			rideInput.latestFinish = FileHandler.getIntAt(lineIndex,5);
			rides.add(rideInput);
		}
				
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("InputFile Structure:\n");
		for(String key:inputDefinitions.keySet()) {
			sb.append("\n"+key+":"+inputDefinitions.get(key));
		}
		for(RideInput rideInput : rides) {
			sb.append("\n"+rideInput.toString());
		}
		return sb.toString();
	}
	

}
