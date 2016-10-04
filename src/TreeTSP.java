import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.regex.Pattern;


/**
 * Travelling Salesman Problem
 * Tree Algorithm
 * 
 * Coram deo
 * @author Jeonghyun Park
 *
 */

public class TreeTSP {
	
	public static ArrayList<Node> possibleSolutions = new ArrayList<Node>();
	
	public class Node implements Comparable<Node> {
		
		public Route route;
		
		//constructor
		public Node( Route route ) {
			
			//System.out.println("Node constructer " +route.citySequence.size());
			this.route = new Route();
			for( int i = 0 ; i < route.citySequence.size() ; i++) {
				this.route.citySequence.add(route.citySequence.get(i));
			}
		}
		
		public int getBound() {
			
			int bound = 0;
			//implement
			int minimum = Integer.MAX_VALUE;
			
			bound += this.route.getDistance();//a
			//System.out.print(this.route +" a :" + bound);
			//b
			for( int i = 0 ; i < Cities.numberOfCities() ; i++ ) {
				if( !(this.route.isVisit(Cities.getCity(i))) && this.route.citySequence.get(this.route.citySequence.size()-1).distanceTo(Cities.getCity(i)) < minimum) {
					minimum = this.route.citySequence.get(this.route.citySequence.size()-1).distanceTo(Cities.getCity(i));
				}
			}
			//System.out.print(" / b :" + minimum );
			bound += minimum;
			
			//c
			//System.out.print(" / c :");
			for( int i = 0 ; i < Cities.numberOfCities() ; i++ ) {
				
				minimum = Integer.MAX_VALUE;
				for ( int j = 0 ; j < Cities.numberOfCities() ; j++ ) {
					
					if(this.route.isVisit(Cities.getCity(i))) {// if visited minimum is zero
						minimum = 0;
						break;
					}
					else {//unvisited
				
						if( (i!=j && !this.route.isVisit(Cities.getCity(j)) ) || ( j==this.route.citySequence.get(0).getX() ) ) {	
							if( Cities.getCity(i).distanceTo(Cities.getCity(j)) < minimum)
							minimum = Cities.getCity(i).distanceTo(Cities.getCity(j));
						}
					}
				}
				//System.out.print(" " + minimum);
				bound += minimum ;
			}
			//System.out.println();
			return bound;
		}
		
		public boolean isFinished() {
			
			// implement
			if(this.route.getCityNum() == 99)//please modify city num
				return true;
			else
				return false;
		}
		
		@Override
		public int compareTo(Node that) {
			// TODO Auto-generated method stub
			return this.getBound() - that.getBound();
			
		}
		
	}
	
	public void branchAndBound() {
		
		MinPQ<Node> minPQ = new MinPQ<Node>();
		MinPQ<Node> minPQ2;//one more minPQ
		
		Route testRoute = new Route();
		
		minPQ.insert(new Node(testRoute.addcity(Cities.getCity(0))));//start from 0;
		
		Node searchNode;
		
		while(true) {
			
			searchNode = minPQ.delMin();
		//System.out.println(searchNode.route + "(" + searchNode.route.getDistance() + ")");
		//System.out.println("searchNode : " + searchNode.getBound() + "-----" +searchNode.route);
			if(searchNode.isFinished()) {
				//System.out.println("Answer : " + searchNode.route + "("+searchNode.route.getDistance()+")");
				System.out.println(searchNode.route.getDistance()+"end " + searchNode.route);
				return;
			}
			
			int count = 0;
			int sum = 0; 
			float average = 0;
			
			minPQ2 = new MinPQ<Node>();
			
			
			for( Route route : (searchNode.route).nextroutes() ) {

				minPQ2.insert(new Node(route));
				
			}	
			
			minPQ.insert(minPQ2.delMin());
			//minPQ.insert(minPQ2.delMin());

			/*
			for( Route route : (searchNode.route).nextroutes() ) {

				count++;
				//sum+= new Node(route).getBound();
				if(count < 3 ) {
				Node newNode = new Node(route);
				minPQ.insert ( newNode );
				}
			}	
			
			if(count!=0) 
				average = (float) sum/count;
		
			for( Route route : (searchNode.route).nextroutes() ) {

				Node newNode = new Node(route);
				if( newNode.getBound() > average )
				minPQ.insert ( newNode );
			}
			*/	
		}
		
		
	}
	
	/**
	 * 경로에 대한 총 거리 계산 (calculate total distance)
	 * @param path
	 * @return
	 */
	
	/**
	 * 찾아진 경로 얻기
	 * @return 경로 배열
	 */
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// 거리 파일 로딩
				System.out.println("Loading File...");
				int nOfCity = 100;
				int [][] distances = new int[nOfCity][];
				int row, column;
				
				Scanner inFile1 = new Scanner(new File("C:/Users/Jeonghyun/workspace/TreeTSP/src/10000Cities.dat")).useDelimiter(" |(\r\n)");
				
				row = 0;
				column = 0;
				
				
				while(inFile1.hasNext()) {
					
					String temp = inFile1.next();
					
					if(!temp.equals("")) {
						
						int pp = Integer.parseInt(temp);
						System.out.println(pp);
						
						if(column == 0 ) 
							distances[row] = new int[nOfCity];
						
							distances[row][column] = pp;
							distances[column][row] = pp;
							
							if(row == column) {
								row++;
								column = 0;
							}
							else
								column++;
					}
					
				}
				
				inFile1.close();
				
				for(int i = 0 ; i < nOfCity ; i++ ) {
			        
			        Cities.addCity(new City(i, distances[i]));
					
				}
				//System.out.println(Cities.getCity(0).distanceTo(Cities.getCity(5)));
				/*File dataSetFile = new File("C:/Users/Jeonghyun/workspace/TreeTSP/src/10000Cities.dat");
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dataSetFile));
				DataInputStream dis = new DataInputStream(bis);
				
				
				for(int i=0;i<100;i++){
					distances[i] = new int[100];
				
					for(int j=0;j<=i;j++){
						
						//int s = dis.readInt();
						//int s = bis.read();
						
						// read 2byte -> short
						//short s = dis.readShort();
						//System.out.println("[" + s + "]" );
						//int s = dis.readInt();
						//int s = bis.read();
						//distances[i][j] = s;
						//distances[j][i] = s;
						distances[i][j]= i*j;
						distances[j][i]= i*j;
						
				//		System.out.println("----" + s);
					}
				}
			
				dis.close();
				*/
				
				// TSP객체 생성 및 최적 경로 탐색
				System.out.println("Find Shortest Path...");
				
				TreeTSP t = new TreeTSP();
				t.branchAndBound();
				
				System.out.println(t.possibleSolutions.size() + "*****");
				
				// 점수와 경로를 파일로 출력
				System.out.println("Write Result...\n");
				File resultFile = new File("result.txt");
				if(!resultFile.exists()) resultFile.createNewFile();
				FileWriter writer = new FileWriter(resultFile);
				/*writer.write(score+"\n");
				for (int i : path) {
					System.out.println(i);
					writer.write(i+"\n");
				}
				writer.close();
				System.out.println("\n* Shortest score = "+score);
				*/

	}

}
