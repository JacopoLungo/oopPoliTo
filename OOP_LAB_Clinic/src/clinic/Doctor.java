package clinic;

import java.util.LinkedList;
import java.util.List;

public class Doctor implements Comparable<Doctor> {
	String first;
	String last;
	String ssn;
	int docID;
	String specialization;
	List<Patient> patients = new LinkedList<>();
	
	public Doctor(String first, String last, String ssn, int docID, String specialization) {
		this.first = first;
		this.last = last;
		this.ssn = ssn;
		this.docID = docID;
		this.specialization = specialization;
	}
	
	public Doctor(String first, String last, String ssn, String docID, String specialization) {
		this.first = first;
		this.last = last;
		this.ssn = ssn;
		this.docID = Integer.parseInt(docID);
		this.specialization = specialization;
	}
	
	void addPatient(Patient p) {
		patients.add(p);
	}
	
	
	
	public List<String> getPatientsSsn() {
		List<String> strPatients = new LinkedList<String>();
		for (Patient p : patients) {
		strPatients.add(p.getSSN());
		}
		return strPatients;
	}

	public String getFirst() {
		return first;
	}
	public String getLast() {
		return last;
	}
	public String getSsn() {
		return ssn;
	}
	public int getDocID() {
		return docID;
	}
	public String getSpecialization() {
		return specialization;
	}


	@Override
	public int compareTo(Doctor o) {
		if (this.last.compareTo(o.last) != 0) {
			return this.last.compareTo(o.last);
		}
		else {
			return this.first.compareTo(o.first);
		}
	}
	
	

}
