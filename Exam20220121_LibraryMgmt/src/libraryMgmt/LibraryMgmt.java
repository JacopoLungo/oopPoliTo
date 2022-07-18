package libraryMgmt;
import java.util.*;
import java.util.stream.Collectors;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class LibraryMgmt {
	
	LocalDate currentDate;
	Map<String, Book> books = new HashMap<>();
	Map<Integer, Volume> volumes = new HashMap<>();
	Map<String, User> users = new HashMap<>();
	Map<Integer, Loan> loans = new HashMap<>();
	int volumeIndex = 0;
	int loanIndex = 0;
	//R0
	/**
	 * Defines the current date
	 * @param date current date
	 */
	public void setCurrentDate(LocalDate date) {
		currentDate = date;
	}

	/**
	 * retrieves current library system date
	 * @return current date
	 */
	public LocalDate getCurrentDate () {
		return currentDate;
	}

	/**
	 * Moves current date forward
	 * @param nOfDays number of days forward
	 */
	public void addDays (long nOfDays) {
		currentDate = currentDate.plusDays(nOfDays);
	}


	//R1
	/**
	 * Add a new book with corresponding volumes
	 * 
	 * @param title    title of the book
	 * @param nVolumes number of volumes available
	 * @param authors  list of authors
	 * @return volume index range
	 * @throws LMException
	 */
	public String addBook(String title, int nVolumes, String... authors) throws LMException {
		if (books.containsKey(title)) {throw new LMException("Titolo già inserito");}
		Book b = new Book(nVolumes, authors, title);
		books.put(title, b);
		
		int firstIndex = volumeIndex + 1;
		
		for (int i = 0; i<nVolumes; i++) {
			volumeIndex++;
			Volume v = new Volume(volumeIndex, authors, title);
			volumes.put(v.volumeIndex, v);
			b.volumes.add(v);
		}
		
		return firstIndex + ":" + volumeIndex ;
	}

	/**
	 * Adds a new user with relative parameters
	 * 
	 * @param name
	 * @param maxNofBooks
	 * @param duration
	 * @return
	 * @throws LMException
	 */
	public String addUser (String name, int maxNofBooks, int duration) throws LMException {
		if (users.containsKey(name)) {throw new LMException("Nome utente già inserito");}
		User user = new User(name, maxNofBooks, duration);
		users.put(name, user);
		return user.name +":"+ user.maxNofBooks+ ":" + user.duration ;
	}

	//R2
	/**
	 * Adds a new volume loan in the system.
	 * 
	 * @param user : user name
	 * @param title: book title
	 * @return loan index
	 * @throws LMException
	 */
	public int addLoan (String user, String title) throws LMException {
		if (books.get(title).areAllBookTaken()) {throw new LMException("nome libro non esistente");}

		loanIndex++;
		User u = users.get(user);
		if (u.hasOverDueLoans(currentDate)) {throw new LMException("utente in ritardo per la restituzione");}
		if (u.maxNofBooks.equals(u.totActiveLoan)) {throw new LMException("L'utente ha raggiunto il numero massimo di libri attivi");}

		LocalDate d = this.getCurrentDate().plusDays(u.duration);
		Volume v = books.get(title).getFirstAvailVol();
		Loan l = new Loan(v, d, loanIndex, u);
		v.available = false;
		loans.put(l.loanIndex,l);
		u.loans.add(l);
		l.state=LoanState.ONGOING;
		u.totActiveLoan++;
		return loanIndex;
	}

	/**
	 * Retrieves loan information
	 * 
	 * @param loanIndex
	 * @return information as string
	 */
	public String getLoanInfo (int loanIndex) {
		return loans.get(loanIndex).getInfo(currentDate);
	}

	/**
	 * Closes a loan
	 * 
	 * @param loanIndex loan index
	 * @return loan return date
	 */
	public LocalDate closeLoan (int loanIndex)  { //throws LMException
		Loan l = loans.get(loanIndex);
		l.returnDate = currentDate;
		l.volume.available=true;
		l.state=LoanState.CLOSED;
		int i = -1;
		for (int j = 0; j < l.user.loans.size(); j++) {
			if(l.user.loans.get(j).loanIndex == loanIndex)
			{
				i = j; 
			}
		}
		l.user.totActiveLoan--;
		return l.returnDate;
	}


	/**
	 * Retrieves number of volumes currently on loan to user
	 * @param user
	 * @return number of volumes
	 */
	public int numberOfBooks (String user) {
		return users.get(user).totActiveLoan;
	}

	//R3  statistics

	/**
	 * Returns map of authors grouped by title
	 * 
	 * @return map title -> author list
	 */
	public TreeMap<String, ArrayList<String>> authorsByTitle() {
		
		TreeMap<String, ArrayList<String>> ris = new TreeMap<>();
		for(Book b : books.values()) {
			String title = b.title;
			ris.put(title, b.authors);
		}
		return ris;
		
//		oppure
		
//		return books.entrySet().stream()
//			.collect(Collectors.toMap(
//							Map.Entry<String,Book>::getKey,
//							(Map.Entry<String, Book> p) -> p.getValue().authors,
//							(a,b) -> a,
//							() -> new TreeMap<>()));
		
	}


	/**
	 * Retrieves total loans for users (including closed ones)
	 * 
	 * @return map user -> loan number
	 */
	public TreeMap<String, Integer> numberOfTotalLoansByUser() {
		TreeMap<String, Integer> ris = new TreeMap<String, Integer>();
		
		for (User u : users.values()) {
			if (u.loans.size() != 0) {
				ris.put(u.name, u.loans.size());
			}
			
		}
		
		
		return ris;
	}

	//R4  queries

	/**
	 * returns the list of loans whose due date is equal to the current date.
	 * 
	 * @return list of loan indexes
	 */
	public List<Integer> dailyOverdue(){
		return loans.values().stream()
					.filter(l -> l.dueDate.equals(currentDate))
					.map(l -> l.loanIndex)
					.collect(Collectors.toList());
					
	}

	/**
	 * returns the average delay of loan returns for given user
	 * @param userName
	 * @return
	 */
	public double averageDelay(String userName) {
	return loans.values().stream().filter(l -> l.state.equals(LoanState.CLOSED))
								.mapToDouble(l -> {
									double diff = (double) l.dueDate.until(l.returnDate, ChronoUnit.DAYS);
									if(diff > 0) { return diff;}
									else {return 0.0;}
								})
								.average()
								.orElse(0.0);
		
	}

	/**
	 * returns the number of volumes available for the book with the given title
	 * @param title
	 * @return number of available volumes
	 */
	public long availableVolumes(String title) {
		return books.get(title).volumes.stream()
				.filter(v -> v.available==true)
				.collect(Collectors.counting());
	}


}
