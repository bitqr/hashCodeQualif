

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InputFileParser {
	
	public List<String> fileLines;
	
	public InputFileParser(String fileName) throws IOException {
		fileLines = Files.readAllLines(Paths.get(fileName));
	}
	
	public InputFileParser() throws IOException {
		fileLines = Files.readAllLines(Paths.get("src/input.in"));
	}
	
	public String getStringAt(int line, int column) {
		return fileLines.get(line).split(" ")[column];
	}
	
	public int getIntAt(int line, int column) {
		return Integer.valueOf(fileLines.get(line).split(" ")[column]);
	}
	
	public double getDoubleAt(int line, int column) {
		return Double.valueOf(fileLines.get(line).split(" ")[column]);
	}

}
