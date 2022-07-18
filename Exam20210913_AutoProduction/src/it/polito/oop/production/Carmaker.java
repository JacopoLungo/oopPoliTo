package it.polito.oop.production;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Facade class used to interact with the system.
 *
 */
public class Carmaker {
	
	Map<String, Model> models = new HashMap<>();
	public Map<String, Factory> factories = new HashMap<>();
	Map<String, Warehouse> warehouses = new HashMap<>();


	/** unique code for diesel engine **/
	public static final int DIESEL = 0;
	/** unique code for gasoline engine **/
	public static final int GASOLINE = 1;
	/** unique code for gpl engine **/
	public static final int GPL = 2;
	/** unique code for electric engine **/
	public static final int ELECTRIC = 3;

	
	// **************** R1 ********************************* //

	/**
	 * Add a new model to the brand factory.
	 * 
	 * Models are uniquely identified by a code
	 * 
	 * @param code 	code
	 * @param name  name
	 * @param year	year of introduction in the market
	 * @param displacement  displacement of the engine in cc
	 * @param enginetype	the engine type (e.g., gasoline, diesel, electric)
	 * @return {@code false} if code is duplicate, 
	*/
	public boolean addModel(String code, String name,  int year, float displacement, int enginetype) {
		if(models.containsKey(code)) {
		return false;	
		}
		Model m = new Model(code, name, year, displacement, enginetype);
		models.put(m.code, m);
		return true;
	}
	
	/**
	 * Count the number of models produced by the brand
	 * 
	 * @return models count
	 */
	public int countModels() {
		return models.values().size();
	}
	
	/**
	 * Retrieves information about a model.
	 * Information is formatted as code, name, year, displacement, enginetype
	 * separate by {@code ','} (comma).
	 * 	 
	 * @param code code of the searched model
	 * @return info about the model
	 */
	public String getModel(String code) {
		if (!models.containsKey(code)) {return null;}
		return models.get(code).toString();
	}
	
	
	/**
	 * Retrieves the list of codes of active models.
	 * Active models not older than 10 years with respect to the execution time.
	 * 	 
	 * @return a list of codes of the active models
	 */
	public List<String> getActiveModels() {
		List<String> mActive = new ArrayList<>();
		for (Model m : models.values()) {
			if (m.isActive()) {
				mActive.add(m.code);
			}
		}
		return mActive;
	}
	
	
	/**
	 * Loads a list of models from a file.
	 * @param Filename path of the file
	 * @throws IOException in case of IO problems
	 */
	public void loadFromFile(String Filename) throws IOException  {
//		try {
			BufferedReader r = new BufferedReader(new FileReader(Filename));
			String line;
			while ((line = r.readLine()) != null) {
				String[] elements = line.split("\t");
				String code = elements[0];
				String name = elements[1];
				int relYear = Integer.parseInt(elements[2]);
				float engineSize = Float.parseFloat(elements[3]);
				int engineType = Integer.parseInt(elements[4]); 
				this.addModel(code, name, relYear, engineSize, engineType);
				}
//		}
//		catch(IOException e) {
//				
//			}
		}
	
	
	// **************** R2 ********************************* //

	
	
	/**
	 * Creates a new factory given its name. Throws Brand Exception if the name of the factory already exists.
	 * 
	 * @param name the unique name of the factory.
	 * @throws BrandException
	 */
	public void buildFactory(String name) throws BrandException {
		if(factories.containsKey(name)) {throw new BrandException();}
		Factory f = new Factory(name);
		factories.put(f.name, f);
	}
	
	
	
	/**
	 * Returns a list of the factory names. The list is empty if no factories are created.
	 * @return A list of factory names.
	 */
	public List<String> getFactories() {
		return factories.values().stream().map(f -> f.name).collect(Collectors.toList());
	}
	
	
	/**
	 * Create a set of production lines for a factory.
	 * Each production line is identified by name,capacity and type of engines it can handle.
	 * 
	 * @param fname The factory name
	 * @parm  line	An array of strings. Each string identifies a production line.
	 * 
	 * @throws BrandException if factory name is not defined or line specification is malformed
	 */
	public void setProductionLines (String fname, String... line) throws BrandException {
		if(!factories.containsKey(fname)) {throw new BrandException();}
		Pattern prodLine = Pattern.compile("(?<name>[A-Za-z0-9_]+):(?<capacity>[0-9]+):(?<engineType>[0-3]+)");
		Factory f  = factories.get(fname);
		for (String l : line) {
			Matcher m = prodLine.matcher(l);
			if(!m.matches()) {throw new BrandException();}
			else {
				if(f.lines.containsKey(m.group("name"))) {
					f.lines.get(m.group("name")).capacity = Integer.parseInt(m.group("capacity"));
				}
				else {
					Line pl = new Line(m.group("name"), Integer.parseInt(m.group("capacity")), Integer.parseInt(m.group("engineType")));
					f.lines.put(pl.name, pl);
					}
			}
		}
	}
	
	/**
	 * Returns a map reporting for each engine type the yearly production capacity of a factory.
	 * 
	 * @param fname factory name
	 * @return A map of the yearly productions
	 * @throws BrandException if factory name is not defined or it has no lines
	 */
	public Map<Integer, Integer> estimateYearlyProduction(String fname) throws BrandException {
		if (!factories.containsKey(fname)) {throw new BrandException();}
		if(factories.get(fname).lines.values().size() == 0) {throw new BrandException();}
		return factories.get(fname).lines.values().stream() //stream di Line
//							.flatMap(f -> f.lines.values().stream()) //stream di lines
							.collect(Collectors.toMap(l -> l.engineType,
														l -> l.capacity,
														(c1, c2) -> c1 + c2));
	}

	// **************** R3 ********************************* //

	
	/**
	 * Creates a new storage for the car maker
	 * 
	 * @param name		Name of the storage
	 * @param capacity	Capacity (number of cars) of the storage
	 * @throws BrandException if name already defined or capacity &le; 0
	 */
	public void buildStorage (String name, int capacity) throws BrandException {
		if (warehouses.containsKey(name)) {throw new BrandException();}
		if (capacity <= 0) {throw new BrandException();} 
		Warehouse w = new Warehouse(name, capacity);
		warehouses.put(w.name, w);
	}
	
	
	/**
	 * Retrieves the names of the available storages. 
	 * The list is empty if no storage is available
	 * 
	 * @return List<String> list of storage names
	 */
	public List<String> getStorageList() {
		return warehouses.values().stream().map(w -> w.name).collect(Collectors.toList());
	}
	
	/**
	 * Add a car to the storage if possible
	 * 
	 * @param sname		storage name
	 * @param model		car model
	 * 
	 * @throws BrandException if storage or model not defined or storage is full
	 */										//code del modello
	public void storeCar(String sname, String model) throws BrandException {
		if (!warehouses.containsKey(sname)) {throw new BrandException();}
		if (!models.containsKey(model)) {throw new BrandException();}
		if (warehouses.get(sname).isFull()) {throw new BrandException();}
		
		if (!warehouses.get(sname).parked.containsKey(models.get(model))) {
			warehouses.get(sname).parked.put(models.get(model), 0);
			}							//Model,           
		warehouses.get(sname).parked.put(models.get(model), warehouses.get(sname).parked.get(models.get(model)) + 1); 
		
		}
	
	/**
	 * Remove a car to the storage if possible.
	 * 
	 * @param sname		Storage name
	 * @param model		Car model
	 * @throws BrandException  if storage or model not defined or storage is empty
	 */
	public void removeCar(String sname, String model) throws BrandException {
		if (!warehouses.containsKey(sname)) {throw new BrandException();}
		if (!models.containsKey(model)) {throw new BrandException();}
		if (warehouses.get(sname).getNumbParkedCar() == 0) {throw new BrandException();}
	
		warehouses.get(sname).parked.put(models.get(model), warehouses.get(sname).parked.get(models.get(model)) - 1); 
	
	}
	
	/**
	 * Generates a summary of the storage.
	 * 
	 * @param sname		storage name
	 * @return map of models vs. quantities
	 * @throws BrandException if storage is not defined
	 */
	public Map<String,Integer> getStorageSummary(String sname) throws BrandException {
		if (!warehouses.containsKey(sname)) {throw new BrandException();}
		return warehouses.get(sname).parked.entrySet().stream().collect(Collectors.toMap(es -> es.getKey().code
																						, es -> es.getValue()));
	}
	
	// **************** R4 ********************************* //
	

	/**
	 * Sets the thresholds for the sustainability level.
	 * 
	 * @param ismin	lower threshold
	 * @param ismax upper threshold
	 */
	public void setISThresholds (float ismin, float ismax) {
		for (Model m : models.values()) {
			m.setLimitBands(ismin, ismax);
		}
	}
	
	
	
	
	/**
	 * Retrieves the models classified in the given Sustainability class.
	 * 
	 * @param islevel sustainability level, 0:low 1:medium 2:high
	 * @return the list of model names in the class
	 */
	public List<String> getModelsSustainability(int islevel) {
//		return models.values().stream().map(m -> "" + m.getIs()).collect(Collectors.toList());

//		return models.values().stream().map(m -> "" + m.getSusBand()).collect(Collectors.toList());
		return models.values().stream().filter(m -> m.getSusBand() == islevel).map(m -> m.code).collect(Collectors.toList());
	}
	
	
	/**
	 * Computes the Carmaker Sustainability level
	 * 
	 * @return sustainability index
	 */
	public float getCarMakerSustainability() {

		int totCars = warehouses.values().stream()//Warehouse
						.mapToInt(w -> w.getNumbParkedCar())
						.sum();//il totale di tutte le auto nei garages
		
		float sumIs=0;
		for (Warehouse w : warehouses.values()) {
			for(Model m : w.parked.keySet()) {
				  		//is di quel modello	//numero di auto di modello m
				sumIs += (float) m.getIs() * w.parked.get(m);
			}
		}
		return (float) sumIs/totCars;
		
	}
	
	// **************** R5 ********************************* //

	/**
	 * Generates an allocation production plan
	 * 
	 * @param request allocation request string
	 * @return {@code true} is planning was successful
	 */
	public boolean plan(String request) {
		String[] orders = request.split(",");
		for (String order : orders) {
			Model model = models.get(order.split(":")[0]);
			int qta = Integer.parseInt(order.split(":")[1]);
			
			for (int i=0 ; i < qta; i++) {
				if (!this.addModelToLine(model)) {
					return false;
				}
			}
			
		}
		return true;
	}
	
	
	
	
	boolean addModelToLine(Model model) {
		int engineType = model.engineType;
		for(Factory f : factories.values()) {//per ogni fabbrica
			for (Line l : f.lines.values()) {//per ogni linea (di ogni fabbrica)
				if (l.engineType.equals(engineType) && !(l.isFull())) {//se la linea produce il tipo di motore giusto
					l.modelsToDo.add(model);							//e se la linea non è satura
					return true;
				}
			}
		}
				
		return false;  
	}
	
	
	
	
	/**
	 * Returns the capacity of a line
	 * 
	 * @param fname factory name
	 * @param lname line name
	 * @return total capacity of the line
	 */
	public int getLineCapacity(String fname, String lname) {
		return factories.get(fname).getLineCapacity(lname);
	}
	
	/**
	 * Returns the allocated capacity of a line
	 * @param fname factory name
	 * @param lname line name
	 * @return already allocated capacity for the line
	 */
	public int getLineAllocatedCapacity(String fname, String lname) {
		return factories.get(fname).getLineAllocatedCapacity(lname);
	}
	
	
	
	// **************** R6 ********************************* //
	
	/**
	 * compute the proportion of lines that are fully allocated
	 * (i.e. allocated capacity == total capacity) as a result
	 * of previous calls to method {@link #plan}
	 * 
	 * @return proportion of lines fully allocated
	 */
	public float linesfullyAllocated() {
		int tot=0;
		int full = 0;
		for (Factory f : factories.values()) {
			for(Line l : f.lines.values()) {
				tot ++;
				if(l.isFull()) {full++;}
			}
		}
		return (float)full/(float)tot;
	}

	/**
	 * compute the proportion of lines that are unused
	 * (i.e. allocated capacity == 0) as a result
	 * of previous calls to method {@link #plan}
	 * 
	 * @return proportion of unused lines
	 */
	public float unusuedLines() {
		
		int tot=0;
		int neverUsed = 0;
		for (Factory f : factories.values()) {
			for(Line l : f.lines.values()) {
				tot ++;
				if(l.getAllocatedCapacity()==0) {neverUsed++;}
			}
		}
		return (float)neverUsed/(float)tot;
	}
}
