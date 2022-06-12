package clinic;

public class Patient {
	
	String first;
	String last;
	String SSN;
	Doctor doctor;
	
	public Patient(String first, String last, String sSN) {
		this.first = first;
		this.last = last;
		SSN = sSN;
	}
	
	void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public String getLast() {
		return last;
	}
	
	public void setLast(String last) {
		this.last = last;
	}
	
	public String getFirst() {
		return first;
	}
	
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSSN() {
		return SSN;
	}
	
	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public Doctor getDoctor() {
		return doctor;
	}
	
}
