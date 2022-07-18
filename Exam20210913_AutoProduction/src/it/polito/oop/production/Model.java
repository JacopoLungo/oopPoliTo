package it.polito.oop.production;

import java.time.LocalDate;

public class Model {
	String code; //univoco
	String name;
	int relYear;
	float engineSize;
	int engineType;
	boolean activeProd;// 3 * 100 / 2022 - 2018 = 75
	float is; 
	int susBand;
	float[] upperAndLowerLimits = new float[2];
	
	public Model(String code, String name, int relYear, float engineSize, int engineType) {
		this.code = code;
		this.name = name;
		this.relYear = relYear;
		this.engineSize = engineSize;
		this.engineType = engineType;
	}
	
	boolean isActive() {
		int currentYear = (int) LocalDate.now().getYear();
		if (currentYear - relYear > 10) {return false;}
		return true;
	}

	@Override
	public String toString() {
		return code + "," + name + "," + relYear + "," + engineSize
				+ "," + engineType;
	}
	
	float getIs() {										//sottraggo 1 per riportarlo all'anno dei test
		return  ((float)(engineType * 100) / (float)( LocalDate.now().getYear() - (1 + relYear)));
	}
	
	void setLimitBands(float isMin, float isMax) {
		upperAndLowerLimits[0] = isMin;
		upperAndLowerLimits[1] = isMax;
	}

	public int getSusBand() {
		int r = -1;
		if (getIs() < upperAndLowerLimits[0]) {r = 0;}
		if (getIs() >= upperAndLowerLimits[0] && getIs() <= upperAndLowerLimits[1]) {r = 1;}
		if (getIs() > upperAndLowerLimits[1]) {r = 2;}
		return r;
	}

	
}
