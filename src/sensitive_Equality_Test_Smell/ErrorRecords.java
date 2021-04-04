package sensitive_Equality_Test_Smell;

public class ErrorRecords {
	private int lineNumber;
	private String error = "";
	
	public ErrorRecords(int lineNumber, String error) {
		super();
		this.lineNumber = lineNumber;
		this.error = error;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}