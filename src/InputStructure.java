

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//To be changed according to the input.in structure description
public class InputStructure {
	
	public class Entity1 {
		public String field1;
		public int field2;
		
		@Override
		public String toString() {
			return "Entity1: {field1: "+field1+", field2: "+field2+"}";
		}
	}
	
	public class Entity2 {
		public double field1;
		
		@Override
		public String toString() {
			return "Entity2: {field1: "+field1+"}";
		}
	}
	
	public List<Entity1> entities1;
	public List<Entity2> entities2;
	
	public InputStructure() throws IOException {
		FileHandler.parseInput("src/input.in");
		int lineOffset = 0;
		int lineIndex = 0;
		
		//SECTION 1: number of different entities
		int numberOfEntities1 = FileHandler.getIntAt(0,0);
		int numberOfEntities2 = FileHandler.getIntAt(0,1);
		entities1 = new ArrayList<Entity1>(numberOfEntities1);
		entities2 = new ArrayList<Entity2>(numberOfEntities2);
		
		//SECTION 2: values for first type of entities
		lineOffset = lineIndex + 1;
		for(lineIndex = 0; lineIndex < numberOfEntities1; lineIndex ++) {
			Entity1 entity1 = new Entity1();
			entity1.field1 = FileHandler.getStringAt(lineOffset + lineIndex, 0);
			entity1.field2 = FileHandler.getIntAt(lineOffset + lineIndex, 1);
			entities1.add(entity1);
		}
		
		//SECTION 3: values for second type of entitites
		lineOffset = lineOffset + lineIndex;
		for(lineIndex = 0; lineIndex < numberOfEntities2; lineIndex ++) {
			Entity2 entity2 = new Entity2();
			entity2.field1 = FileHandler.getDoubleAt(lineOffset + lineIndex, 0);
			entities2.add(entity2);
		}
				
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("InputStructure:");
		result.append("\nentities1:[");
		for(Entity1 e1: entities1) {
			result.append(e1.toString()+" ");
		}
		result.append("\n]\nentities2:[");
		for(Entity2 e2: entities2) {
			result.append(e2.toString()+" ");
		}
		result.append("\n]");
		return result.toString();
		
	}
	

}
