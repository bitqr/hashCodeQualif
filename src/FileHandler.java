

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class FileHandler {
	
	public static List<String> inputFileLines;
	
	public static void parseInput(String inputFileName) throws IOException {
		inputFileLines = Files.readAllLines(Paths.get(inputFileName));
	}
	
	public static void writeOutput(String outputFileName, String outputContent) throws IOException {
		Files.write(Paths.get(outputFileName), outputContent.getBytes());
	}
	
	public static String getStringAt(int line, int column) {
		return inputFileLines.get(line).split(" ")[column];
	}
	
	public static int getIntAt(int line, int column) {
		return Integer.valueOf(inputFileLines.get(line).split(" ")[column]);
	}
	
	public static double getDoubleAt(int line, int column) {
		return Double.valueOf(inputFileLines.get(line).split(" ")[column]);
	}

}
