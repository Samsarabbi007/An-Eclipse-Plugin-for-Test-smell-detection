package test_smell_detection_plugin.handlers;

public class OutputCollector {
	String path; String fileName; String lineNo; String smellType;
	public OutputCollector(String path, String fileName, String lineNo, String smellType) {
		this.path = path;
		this.fileName = fileName;
		this.lineNo = lineNo;
		this.smellType = smellType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getLineNo() {
		return lineNo;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getSmellType() {
		return smellType;
	}

}
