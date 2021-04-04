package test_smell_detection_plugin.handlers;

import java.util.ArrayList;

public class AllOutputs {
	private static AllOutputs detectionCollectionObj = null;
	ArrayList<OutputCollector> indirects;
	ArrayList<OutputCollector> assertions;
	ArrayList<OutputCollector> eagers;
	ArrayList<OutputCollector> gfixtures;
	ArrayList<OutputCollector> sensitivities;
	
	private AllOutputs() {
		
	}
	
	protected static AllOutputs getInstance() {
		if (detectionCollectionObj == null) {
			detectionCollectionObj = new AllOutputs();
		}
		return detectionCollectionObj;
	}
	
	public static AllOutputs getExistingInstance() {
		return detectionCollectionObj;
	}
	
	public void setAssertions(ArrayList<OutputCollector> assertions) {
		this.assertions = assertions;
	}
	
	public void setEagers(ArrayList<OutputCollector> eagers) {
		this.eagers = eagers;
	}
	
	public void setIndirects(ArrayList<OutputCollector> indirects) {
		this.indirects = indirects;
	}
	
	public void setGfixtures(ArrayList<OutputCollector> gfixtures) {
		this.gfixtures = gfixtures;
	}
	
	public void setSensitivities(ArrayList<OutputCollector> sensitivities) {
		this.sensitivities = sensitivities;
	}
	
	public ArrayList<OutputCollector> getEagers() {
		return eagers;
	}
	
	public ArrayList<OutputCollector> getGfixtures() {
		return gfixtures;
	}
	
	public ArrayList<OutputCollector> getIndirects() {
		return indirects;
	}
	
	public ArrayList<OutputCollector> getAssertions() {
		return assertions;
	}
	
	public ArrayList<OutputCollector> getSensitivities() {
		return sensitivities;
	}

}
