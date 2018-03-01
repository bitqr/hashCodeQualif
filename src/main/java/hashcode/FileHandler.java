package hashcode;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileHandler {
	
	public static List<String> inputFileLines;
	
	public static void parseInput(String inputFileName) throws IOException {
		inputFileLines = Files.readAllLines(Paths.get(inputFileName));
	}
	
	public static void writeOutput(String outputFileName, String outputContent) throws IOException {
		Files.write(Paths.get(outputFileName), outputContent.getBytes());
		
		File directoryToZip = new File("src/main/java");
		List<File> fileList = new ArrayList<File>();
		getAllFiles(directoryToZip, fileList);
		writeZipFile(directoryToZip, fileList);
	}
	
	
	
	private static void writeZipFile(File directoryToZip, List<File> fileList) {

		try {
			FileOutputStream fos = new FileOutputStream("target/sourceCode.zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory() && !file.getName().equals("cplex.jar")) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
	IOException {
		
		FileInputStream fis = new FileInputStream(file);
		
		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		System.out.println("Writing '" + zipFilePath + "' to zip file");
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);
		
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		
		zos.closeEntry();
		fis.close();
	}

	
	private static void getAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					getAllFiles(file, fileList);
				} else {
					System.out.println("     file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
