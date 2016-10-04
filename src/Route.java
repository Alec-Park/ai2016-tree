import java.util.ArrayList;
import java.util.Iterator;

public class Route {
	
	public ArrayList<City> citySequence = new ArrayList<City>();
	
	
	Route() {
	//	System.out.println("Empty Route Constructor");
	}
	
	Route(Route route) {
		
	//	System.out.println("Route Constructor : size -> " + route.citySequence.size() );
		for( int i = 0 ; i < route.citySequence.size() ; i++) {
			this.citySequence.add(route.citySequence.get(i));
		}
	}
	
	public Iterable<Route> nextroutes() {
		
		Queue<Route> next = new Queue<Route>();
		
		for( int i = 0; i < Cities.numberOfCities() ; i++ ) {
			
			if(!this.isVisit(Cities.getCity(i))) {// unvisited vertex i
			//	System.out.println("*****" + i + "******" + this.toString());
				Route newRoute = this.addcity(Cities.getCity(i));
			//	System.out.println("new route : " + newRoute);
				next.enqueue(newRoute);
			}
		}
		
		return next;
	}
	
	public int getCityNum () {
		return this.citySequence.size();
	}
	
	public int getDistance() {
		int distance = 0 ;
		
		for(int i = 0 ; i < this.citySequence.size() - 1 ; i++ )
			distance += ((City)citySequence.get(i)).distanceTo((City)citySequence.get(i+1));
		
		return distance;
	}
	
	public boolean isVisit(City city) {
		for( int i = 0 ; i < this.citySequence.size() ; i++ ) {
			if( citySequence.get(i).getX() == city.getX() )
				return true;
		}
		return false;
	}
	public Route addcity(City city) {
		
		Route newRoute = new Route(this);
		newRoute.citySequence.add(city);
		
		return newRoute;
	}
	
	@Override
    public String toString() {
		
        String geneString = "|";
        for (int i = 0; i < this.citySequence.size(); i++) {
            geneString += this.citySequence.get(i).getX() +"|";
        }
        return geneString;
    }
	

}
