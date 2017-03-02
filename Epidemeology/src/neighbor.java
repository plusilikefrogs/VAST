	/************************************************************************/
	/*							NEIGHBOR INNER CLASS						*/
	/************************************************************************/

public class neighbor {

			/**********************************************************************
				Neighbor variables and constants
			**********************************************************************/
			Integer ID; 	// unique identifier of this grid poInteger for use in hashing
			Double x; 	// lateral position of grid poInteger
			Double y; 	// vertical position of grid in cell
			Double populationDensityHuman; 	// Initial human population density
			Double populationDensityVector; 	//Initial vector population density
			Double S; Double E; Double I; Double R; Double N;
			Double s; Double e; Double i; Double n;
			public int hashCode(){return this.ID;}
			
			/**********************************************************************
				Get all variables
			**********************************************************************/
			public Integer getID(){return this.ID;}
			
			public Double getx(){return this.x;}
			public Double gety(){return this.y;}
			
			public Double getPopHuman(){return this.populationDensityHuman;}
			public Double getPopVector(){return this.populationDensityVector;}
			
			public Double getS(){return this.S;}
			public Double getE(){return this.E;}
			public Double getI(){return this.I;}
			public Double getR(){return this.R;}
			public Double getN(){return this.N;}
			
			public Double gets(){return this.s;}
			public Double gete(){return this.e;}
			public Double geti(){return this.i;}
			public Double getn(){return this.n;}
			
			/**********************************************************************
				Set all variables
			**********************************************************************/
			public void setID(Integer ID){this.ID = ID;}
			
			public void setx(Double x){this.x = x;}
			public void sety(Double y){this.y = y;}

			public void setPopHuman(Double popHuman){this.populationDensityHuman = popHuman;}
			public void setPopVector(Double popVector){this.populationDensityVector = popVector;}
			
			public void setS(Double S){this.S = S;}
			public void setE(Double E){this.E = E;}
			public void setI(Double I){this.I = I;}
			public void setR(Double R){this.R = R;}
			public void setN(Double N){this.N = N;}
			
			public void sets(Double s){this.s = s;}
			public void sete(Double e){this.e = e;}
			public void seti(Double i){this.i = i;}
			public void setn(Double n){this.n = n;}
			
			/**********************************************************************
				Constructors
			**********************************************************************/
			public neighbor(){
				setID(null);
				setx(null); sety(null);
				setPopHuman(null);
				setPopVector(null);
				setS(null); setE(null); setI(null); setR(null); setN(null);
				sets(null); sete(null); seti(null); setn(null);
			}
			
			public neighbor(gridPoint grid){
				setID(grid.ID);
				setx(grid.x); sety(grid.y);
				setPopHuman(grid.populationDensityHuman);
				setPopVector(grid.populationDensityVector);
				setS(grid.S); setE(grid.E); setI(grid.I); setR(grid.R); setN(grid.N);
				sets(grid.s); sete(grid.e); seti(grid.i); setn(grid.n);
			}
			
		}