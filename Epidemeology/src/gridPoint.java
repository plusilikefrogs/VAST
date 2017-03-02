import java.util.HashMap;
import java.util.*;
import java.lang.String;

/**********************************************************************
							GRID POINT CLASS
**********************************************************************/
public class gridPoint {
	
/**********************************************************************
	Grid variables and constants
**********************************************************************/
	// Parameters list in order
	Integer ID; 						// unique identifier of this grid poInteger for use in hashing
	Double x; 						// lateral position of grid poInteger
	Double y; 						// vertical position of grid in cell
	Double gridSize;					// Space between grid poInteger and neighbor (symmetric)
	ArrayList<neighbor> neighbors = new ArrayList<neighbor>();
	ArrayList<Integer> neighborID = new ArrayList<Integer>();
	// What type of grid is this?
	Boolean isWater; Boolean isEdge;
	Integer numNeighbors;
	
	// Variables list
	/*Simulation implements a generalization of the MacDonald-Ross Model for Malaria, where individuals
	 * are either Susceptible (S) to exposure by disease vectors, Exposed (E) to disease,
	 * Infected (k) with the disease, or Recovered (R). Similarly, the vectors themselves can be
	 * susceptible (s), exposed (e), and infected (k), but do not recover. The total population of humans
	 * is the sum of the sub-populations (N), and likewise for the vectors (n).	 * 
	 */

	final Double tol = .2;					// Tolerance for comparing distances
	final Double dt = 1.0/24;					// Time step is per hour;
	
	Double populationDensityHuman; 		// Initial human population density
	Double populationDensityVector; 	// Initial vector population density
	Double biteRate; 					// rate at which vector exposes disease to human  (converted)
	Double biteSuccessRateV; 			// number of exposures to a vector a human needs to get sick (converted)
	Double biteSuccessRateH; 			// number of exposures to a vector a human needs to get sick (converted)
	Double immunityLossRate; 			// rate at which recovered become susceptible again (converted)
	Double latencyRate; 				// rate at which exposed becomes infected (converted)
	Double recoveryRate; 				// rate at which infected recover (converted)
	Double contagionRate;				// rate at which infected individuals expose susceptible ones [R_0 x recoveryRate] (converted)
	Double birthRateVector;				// rate at which vectors are born
	Double deathRateVector;				// rate at which vectors die
	Double birthRateHuman;				// rate at which humans are born
	Double deathRateHuman;				// rate at which humans die
	Double selfWeight;					// Unit-less factor weighing self-population affects
	Double neighborWeight;				// Unit-less factor weighing neighbor populations (symmetric)
	
	// Variables for human population
	Double S; Double E; Double I; Double R; Double N;
	// Variables for mosquito population
	Double s; Double e; Double i; Double n;
	
/**********************************************************************
	Constructors
**********************************************************************/
	// simulation initialized gridPoint
	public gridPoint(HashMap<String, Double> gridParameters){
		setID(gridParameters.get("ID"));
		setx(gridParameters.get("x location"));
		sety(gridParameters.get("y location"));
		setGridSize(gridParameters.get("Grid Size"));
		setPopHuman(gridParameters.get("Population Density Human"));
		setPopVector(gridParameters.get("Population Density Vector"));
		setBiteRate(gridParameters.get("Bite Rate")); 
		setBiteSuccessRateH(gridParameters.get("Bite Success Rate Human"));
		setBiteSuccessRateV(gridParameters.get("Bite Success Rate Vector")); 
		setImmunityLossRate(gridParameters.get("Immunity Loss Rate")); 
		setLatencyRate(gridParameters.get("Latency Rate")); 
		setRecoveryRate(gridParameters.get("Recovery Rate")); 
		setContagionRate(gridParameters.get("Contagion Rate")); 
		setBirthRateH(gridParameters.get("Birth Rate Human"));
		setBirthRateV(gridParameters.get("Birth Rate Vector")); 
		setDeathRateH(gridParameters.get("Death Rate Human"));
		setDeathRateV(gridParameters.get("Death Rate Vector")); 
		setSelfWeight(gridParameters.get("Self Weight")); 
		setNeighborWeight(gridParameters.get("Neighbor Weight")); 
		setisEdge(gridParameters.get("isEdge"));
		setisWater(gridParameters.get("isWater"));
		setNumNeighbors();
		S = this.populationDensityHuman; s = this.populationDensityVector;
		E = 0.0; e = 0.0; I = 0.0; i = 0.0; R = 0.0; N = this.populationDensityHuman; n = this.populationDensityVector;		
	}

	// null gridPoint
	public gridPoint(){
		setID(null);
		setx(null);
		sety(null);
		setGridSize(null);
		setPopHuman(null);
		setPopVector(null);
		setBiteRate(null); 
		setBiteSuccessRateH(null);
		setBiteSuccessRateV(null); 
		setImmunityLossRate(null); 
		setLatencyRate(null); 
		setRecoveryRate(null); 
		setContagionRate(null); 
		setBirthRateH(null);
		setBirthRateV(null); 
		setDeathRateH(null);
		setDeathRateV(null); 
		setSelfWeight(null); 
		setNeighborWeight(null); 
		setisEdge(null);
		setisWater(null);
		setNumNeighbors(null);
		S = null; s = null;
		E = null; e = null; I = null; i = null; R = null; N = null; n = null;	
	}
	
	
	
/**********************************************************************
	Get all variables
**********************************************************************/
	public Integer getID(){return this.ID;}
	public Integer getNumNeighbors(){return this.numNeighbors;}
	public int hashCode(){return this.ID;}
	
	public Double getx(){return this.x;}
	public Double gety(){return this.y;}

	public Double getPopHuman(){return this.populationDensityHuman;}
	public Double getPopVector(){return this.populationDensityVector;}
	public Double getBiteRate(){return this.biteRate;}; 
	public Double getBiteSuccessRateH(){return this.biteSuccessRateH;}
	public Double getBiteSuccessRateV(){return this.biteSuccessRateV;} 
	public Double getImmunityLossRate(){return this.immunityLossRate;} 
	public Double getLatencyRate(){return this.latencyRate;} 
	public Double getRecoveryRate(){return this.recoveryRate;} 
	public Double getContagionRate(){return this.contagionRate;} 
	public Double getSelfWeight(){return this.selfWeight;} 
	public Double getNeighborWeight(){return this.neighborWeight;} 
	public Double getGridSize(){return this.gridSize;}
	
	public Double setBirthRateH(){return this.birthRateHuman;}
	public Double setBirthRateV(){return this.birthRateVector;}
	public Double setDeathRateH(){return this.deathRateHuman;}
	public Double setDeathRateV(){return this.deathRateVector;}
	
	public Double getS(){return this.S;}
	public Double getE(){return this.E;}
	public Double getI(){return this.I;}
	public Double getR(){return this.R;}
	public Double getN(){return this.N;}
	
	public Double gets(){return this.s;}
	public Double gete(){return this.e;}
	public Double geti(){return this.i;}
	public Double getn(){return this.n;}
	
	public Boolean setisWater(){return this.isWater;};
	public Boolean setisEdge(){return this.isEdge;}
	
	
/**********************************************************************
	Set all variables
**********************************************************************/
	public void setNumNeighbors(){if(this.isEdge){this.numNeighbors = 3;} else {this.numNeighbors = 4;}}
	public void setNumNeighbors(Integer NULL){this.numNeighbors = NULL;}
	public void setID(Double ID){this.ID = ID.intValue();}
	
	public void setisWater(Double bit){if(bit == 1){this.isWater = true;}else{this.isWater = false;};}
	public void setisEdge(Double bit ){if(bit == 1){this.isEdge = true;}else{this.isEdge = false;};}
	
	public void setx(Double x){this.x = x;}
	public void sety(Double y){this.y = y;}
	
	public void setPopHuman(Double popHuman){this.populationDensityHuman = popHuman;}
	public void setPopVector(Double popVector){this.populationDensityVector = popVector;}
	public void setBiteRate(Double biteRate){this.biteRate = biteRate;}; 
	public void setBiteSuccessRateH(Double biteSuccessRateH){this.biteSuccessRateH = biteSuccessRateH;}
	public void setBiteSuccessRateV(Double biteSuccessRateV){this.biteSuccessRateV = biteSuccessRateV;} 
	public void setImmunityLossRate(Double immunityLossRate){this.immunityLossRate = immunityLossRate;} 
	public void setLatencyRate(Double latencyRate){this.latencyRate = latencyRate;} 
	public void setRecoveryRate(Double recoveryRate){this.recoveryRate = recoveryRate;} 
	public void setContagionRate(Double contagionRate){this.contagionRate = contagionRate;} 
	public void setSelfWeight(Double selfWeight){this.selfWeight = selfWeight;} 
	public void setNeighborWeight(Double neighborWeight){this.neighborWeight = neighborWeight;} 
	public void setGridSize(Double gridSize){this.gridSize = gridSize;}
	
	public void setBirthRateH(Double birthRateHuman){this.birthRateHuman = birthRateHuman;}
	public void setBirthRateV(Double birthRateVector){this.birthRateVector = birthRateVector;}
	public void setDeathRateH(Double deathRateHuman){this.deathRateHuman = deathRateHuman;}
	public void setDeathRateV(Double deathRateVector){this.deathRateVector = deathRateVector;}
	
	public void setS(Double S){this.S = S;}
	public void setE(Double E){this.E = E;}
	public void setI(Double I){this.i = I;}
	public void setR(Double R){this.R = R;}
	public void setN(Double N){this.N = N;}
	
	public void sets(Double s){this.s = s;}
	public void sete(Double e){this.e = e;}
	public void seti(Double i){this.i = i;}
	public void setn(Double n){this.n = n;}
	

	
/********************************************************************/
/*							GRID POINT METHODS						*/
/********************************************************************/
	
	public void updateGridPoints(){
		Double dt = this.dt;
		// Variables for human population
		Double S = this.S; Double E = this.E; Double I = this.I; Double R = this.R; Double N = this.N;
		// Variables for mosquito population
		Double s = this.s; Double e = this.e; Double i = this.i; Double n = this.n;
		
		Double a = this.biteRate; Double b_h = this.biteSuccessRateH; Double alpha = this.immunityLossRate;
		Double eps = this.latencyRate; Double g = this.recoveryRate; 
		Double w_s = this.selfWeight; Double w_n = this.neighborWeight; 
		Double cR = this.contagionRate; Double b_v = this.biteSuccessRateV;
		Double bV = this.birthRateVector; Double dV = this.deathRateVector;	
		Double bH = this.birthRateHuman; Double dH = this.deathRateHuman;
		
		Double S_n = this.calcNeighborS(); Double E_n = this.calcNeighborE();
		Double I_n = this.calcNeighborI(); Double R_n = this.calcNeighborR();
		Double N_n = this.calcNeighborN();
		
		Double s_n = this.calcNeighbors(); Double e_n = this.calcNeighbore();
		Double i_n = this.calcNeighbori(); 
		Double n_n = this.calcNeighborn();
		
		// Variables for human population
		Double new_S; Double new_E; Double new_I; Double new_R; Double new_N;
		// Variables for mosquito population
		Double new_s; Double new_e; Double new_i; Double new_n; 
		
		Double NN = w_s*N + w_n*N_n; Double nn = w_s*n + w_n*n_n;
		Double SS = w_s*S + w_n*S_n; Double ss = w_s*s + w_n*s_n;
		Double EE = w_s*E + w_n*E_n; Double ee = w_s*e + w_n*e_n;
		Double II = w_s*I + w_n*I_n; Double ii = w_s*i + w_n*i_n;
		Double RR = w_s*R + w_n*R_n;
		
		// Update human variables
		new_S = -(a*b_h*nn/NN)*dt*ii*SS - cR*dt*SS + alpha*dt*RR + dt*bH*1.0 - dt*dH*SS;
		new_E =  (a*b_h*nn/NN)*dt*ii*SS + cR*dt*SS - eps*dt*EE - dt*dH*E;
		new_I =  eps*dt*EE - g*dt*II - dt*dH*II; 
		new_R =  g*dt*II - alpha*dt*RR - dt*dH*RR;
		new_N =  new_S + new_E + new_I + new_R;
		
		// Update vector variables
		new_s = -a*b_v*dt*ii*ss + dt*bV*1.0 - dt*dV*s;
		new_e =  a*b_v*dt*ii*ss - eps*dt*ss - dt*dV*ee;
		new_i =  eps*dt*ee - g*dt*ii - dt*dV*ii; 
		new_n =  new_s + new_e + new_i;
		
		this.S = new_S; this.E = new_E; this.I = new_I; this.R = new_R; this.n = new_N;
		this.s = new_s; this.e = new_e; this.i = new_i; this.n = new_n;
	}
	
	
/**********************************************************************
	Neighbor Methods
**********************************************************************/
	public void addNeighbor(neighbor newNeighbor){
		if(isNeighbor(newNeighbor)){
			this.neighbors.add(newNeighbor);
			this.numNeighbors = this.numNeighbors + 1;
			this.neighborID.add(newNeighbor.ID);
		}
	}
	
	public void addNeighborID(neighbor newNeighbor){
		int k; boolean IN = false; 
		for(k = 0; k < this.neighborID.size(); k++){if(this.neighborID.get(k) == newNeighbor.ID){IN = true;}}
		if(IN != true){addNeighbor(newNeighbor);
		}
	}
	
	public Boolean isNeighbor(neighbor otherGridPoint){
		double dist = Math.sqrt((this.x - otherGridPoint.x)*(this.x - otherGridPoint.x) + (this.y - otherGridPoint.y)*(this.y - otherGridPoint.y));
		if(dist < (1.0 + tol)*this.gridSize){return true;} else {return false;}
		}

	public Double calcNeighborS(){
		int k; Double S = 0.0;
		for(k = 0; k < this.numNeighbors; k++){S =+ this.neighbors.get(k).S;}
		return S;
		}
	
	public Double calcNeighbors(){
		int k; Double s = 0.0;
		for(k = 0; k < this.numNeighbors; k++){s =+ this.neighbors.get(k).s;}
		return s;
		}
	
	public Double calcNeighborE(){
		int k; Double E = 0.0;
		for(k = 0; k < this.numNeighbors; k++){E =+ this.neighbors.get(k).E;}
		return E;
		}
	
	public Double calcNeighbore(){
		int k; Double e = 0.0;
		for(k = 0; k < this.numNeighbors; k++){s =+ this.neighbors.get(k).e;}
		return e;
		}
	
	public Double calcNeighborI(){
		int k; Double I = 0.0;
		for(k = 0; k < this.numNeighbors; k++){I =+ this.neighbors.get(k).I;}
		return I;
		}
	
	public Double calcNeighbori(){
		int k; Double i = 0.0;
		for(k = 0; k < this.numNeighbors; k++){i =+ this.neighbors.get(k).i;}
		return i;
		}
	
	public Double calcNeighborR(){
		int k; Double R = 0.0;
		for(k = 0; k < this.numNeighbors; k++){R =+ this.neighbors.get(k).R;}
		return R;
		}
	
	public Double calcNeighborN(){
		int k; Double N = 0.0;
		for(k = 0; k < this.numNeighbors; k++){N =+ this.neighbors.get(k).N;}
		return N;
		}
	
	public Double calcNeighborn(){
		int k; Double n = 0.0;
		for(k = 0; k < this.numNeighbors; k++){n =+ this.neighbors.get(k).n;}
		return n;
		}

	public void clearNeighbors(){
		this.neighbors.clear();
	}
}











