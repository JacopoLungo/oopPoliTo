package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	Map<String, Patient> patients = new HashMap<>();
	Map<Integer, Doctor> doctors = new HashMap<>();
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		patients.put(ssn, new Patient(first, last, ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if(!patients.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		Patient p = patients.get(ssn); 
		return p.getLast() + " " + p.getFirst() + " (" + p.getSSN() + ")";
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctors.put(docID, new Doctor(first, last, ssn, docID, specialization));
		patients.put(ssn, new Patient(first, last, ssn));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if(!doctors.containsKey(docID)) {
			throw new NoSuchDoctor();
		}
		Doctor d = doctors.get(docID);
		return d.getLast() + " " + d.getFirst() + " (" + d.getSsn() + ") [" + d.getDocID() + "]: " + d.getSpecialization();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if(!doctors.containsKey(docID)) {throw new NoSuchDoctor();}
		if(!patients.containsKey(ssn)) {throw new NoSuchPatient();}
		patients.get(ssn).setDoctor(doctors.get(docID));
		doctors.get(docID).addPatient(patients.get(ssn));
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if(!patients.containsKey(ssn)) {throw new NoSuchPatient();}
		if(patients.get(ssn).getDoctor() == null) {throw new NoSuchDoctor();}
		return patients.get(ssn).getDoctor().getDocID();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if(!doctors.containsKey(id)) {throw new NoSuchDoctor();}
		return doctors.get(id).getPatientsSsn();
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		return loadData(reader,null);
//		try {
//		BufferedReader buffReader = new BufferedReader(reader);
//		String line;
//		while (buffReader.readLine() != null) {
//			line = buffReader.readLine();
//			if(line.startsWith("P")) {
//				String[] patCreds = line.split(";");
//				String first = patCreds[1].trim();
//				String last = patCreds[2].trim();
//				String ssn = patCreds[3].trim();
//				try {
//					this.addPatient(first, last, ssn);
//				} catch() {}
//				
//			}
//			if(line.startsWith("M")) {
//				String[] docCreds = line.split(";");
//				String first = docCreds[2].trim();
//				String last = docCreds[3].trim();
//				String ssn = docCreds[4].trim();
//				int docID = Integer.parseInt(docCreds[1].trim());
//				String specialization = docCreds[5].trim();
//				this.addDoctor(first, last, ssn, docID, specialization);
//			}
//		}
//		buffReader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			}
	}



	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	private final static Pattern re = Pattern.compile("(?:P|M *; *(?<id>[0-9]+)) *; *(?<first>[^;]+) *; *(?<last>[^;]+) *; *(?<ssn>[0-9A-Z]+)(?: *; *(?<spec>.+))?");
	
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
			BufferedReader r = new BufferedReader(reader);
			String line;
			int n = 0;
			while ( (line = r.readLine()) != null) {
				Matcher m = re.matcher(line);
				if (m.find()) {
					if (m.group("id") == null) {
						addPatient(m.group("first"), m.group("last"), m.group("ssn"));
					}
					else {
						addDoctor(m.group("first"), m.group("last"), m.group("ssn"), Integer.parseInt(m.group("id")), m.group("spec"));
					}
				n++;
				}
				else {
					if (listener != null) {
						listener.offending(line);
					}
				}
			}
			return n;
		}

		
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		return doctors.values().stream().
				filter(doc -> doc.patients.size() == 0).
				sorted().
				map(doctor -> doctor.docID).
				collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		double avg = doctors.values().stream()
					.mapToInt(doc -> doc.patients.size())
					.average()
					.orElse(0.0);
				
		return doctors.values().stream().
				filter(doc -> doc.patients.size() > avg).
				map(doctor -> doctor.docID).
				collect(Collectors.toList());
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		return doctors.values().stream()
				.sorted(Comparator.comparing((Doctor doc)-> doc.patients.size()).reversed())
				.map(d -> String.format("%3d : %s %s %s", d.patients.size(), d.getDocID(), d.getLast(), d.getFirst()))
				.collect(Collectors.toList())
				;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	
	
	
	
	
	public Collection<String> countPatientsPerSpecialization(){
//		patients.values().stream()
//						.filter(p -> p.doctor != null)
//						.collect(Collectors.groupingBy(
//											patient -> patient.doctor.specialization,
//											Collectors.counting()))
//						.entrySet().stream()
//									.sorted(Comparator.comparing(Map.Entry<String, Long>::getValue))
			
		
		
		
		

		
		
		
		
		
		return patients.values().stream()
				.map(Patient::getDoctor)
				.filter(d -> d != null)
				.collect(Collectors.groupingBy(
								(Doctor d) -> d.getSpecialization(),
								Collectors.counting()))
				.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry<String, Long>::getValue, Comparator.reverseOrder())
						.thenComparing(Map.Entry<String, Long>::getKey))
				.map(e -> String.format("%3d - %s", e.getValue(), e.getKey()))
				.collect(Collectors.toList());
	}
	
}
