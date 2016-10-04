/*
* City.java
* Models a city
*/

public class City {
    int x;
    //boolean isVisit;
    public int[] distances = new int[101];
    
    // Constructs a randomly placed city
    public City(){
        this.x = 0;
        
    }
    
    public City(int x) {
    	this.x = x;
    	
    }
   
    
    public City(int x, int[] distances) {
    	this.x = x;
    	this.distances = distances;
    }
    
    
    // Gets city's x coordinate
    public int getX(){
        return this.x;
    }
    
    // Sets the distance of the city
    public void setDistance(int[] distances) {
    	this.distances = distances;
    }
    
    // Gets the distance to given city
    public int distanceTo(City city){
    	return this.distances[city.x];
    }
   
    @Override
    public String toString(){
        return Integer.toString(getX());
    }
}
