package MenuShow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuFilePuller {


	@SuppressWarnings("resource")
	public Map<Integer, Map<String, Double>>   Pull( String l){

		/*Creating Maps
		 * */

		Map<Integer, Map<String, Double>> outerMap = new HashMap<Integer, Map<String, Double>>();
		Map<String, Double> innerMap = new HashMap<String, Double>();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(l));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("File is missing or maybe on wrong path.");
			e1.printStackTrace();
		}

		String line;

		try {
			while((line=reader.readLine())!=null)
			{
				String [] Column =line.split(",", 3);


				/*index 0 is RetorantID, index 1 is Prices, 
				 * index 2 is Food for the price*/
				//System.out.println(Column[2]);
				/*
				 * Prints out any one column considering the last one as only one Column\
				 *  */

				innerMap = new HashMap<String, Double>();

				if(outerMap.isEmpty()) {
					innerMap.put(Column[2].trim(),Double.parseDouble(Column[1]));
					outerMap.put(Integer.parseInt(Column[0]), innerMap);

				}
				else{
					if(outerMap.containsKey(Integer.parseInt(Column[0]))){
						innerMap=outerMap.get(Integer.parseInt(Column[0]));
						innerMap.put(Column[2].trim(),Double.parseDouble(Column[1]));
						outerMap.put(Integer.parseInt(Column[0]), innerMap);

					}
					else{
						innerMap.put(Column[2].trim(),Double.parseDouble(Column[1]));
						outerMap.put(Integer.parseInt(Column[0]), innerMap);
					}
				}


			}
			display(outerMap);

		} catch (IOException fileNotFException) {
			// TODO Auto-generated catch block
			System.out.println("File not Found on the expected loaction./n :(");
			fileNotFException.printStackTrace();
		}
		return  outerMap;

	}

	public void display(Map<Integer, Map<String, Double>> outerMap)
	{
		Map<String, Double> innerMap = new HashMap<String, Double>();

		System.out.println("List"+outerMap);

	}


	double minimumCost = 100;
    double restorantPrice=0;
	boolean valueMeal=false;
	boolean included=false;

	
	public int getBestDeal(Map<Integer, Map<String, Double>> restaurants, List<String> items)
	{
		boolean hasMenu;
		Map<String, Double> menuItems = new HashMap<String, Double>();
		int restID = -1;
		for(Integer key:restaurants.keySet())
		{
			menuItems.clear();
			menuItems = restaurants.get(key);
			hasMenu = Contains(menuItems, items);

			if(hasMenu)
			{
				System.out.println("Present in Restaurant "+key+ " For $"+ restorantPrice+" :) ");
				System.out.println( );
				if(restorantPrice < minimumCost)
				{
					minimumCost=restorantPrice;
					restID = key;
				}
			}
		}
		return restID;
	}

	public boolean Contains(Map<String, Double> menuItems, List<String> items)
	{
		boolean havingMenu=true, found;
		double cost=0;
		for(String item : items)
		{
			found = containsItem(menuItems, item);
			
			if(found)
			{
				cost += bestPrice(menuItems, item);
				
			}
			if(havingMenu==false || found==false)
				havingMenu = false;
			
		}
		if(havingMenu == true && cost<minimumCost)
			this.restorantPrice =cost;
		
		return havingMenu;
	}


	private double bestPrice(Map<String, Double> menuItems, String item) 
	{
		if(valueMeal)
		{
			if(!included)
			{
				for(String menu : menuItems.keySet())
				{				
					if(menu.contains(item))
					{
						included = true;
						return menuItems.get(menu);
					}			
				}
			}			
		}
		else
		{
			return menuItems.get(item);
		}
		return 0;
	}

	public double getMinimumCost() {
		return minimumCost;
	}

	public void setMinimumCost(double minimumCost) {
		this.minimumCost = minimumCost;
	}

	
	

	public boolean containsItem(Map<String, Double> menuItems, String item)
	{
		for(String menu : menuItems.keySet())
		{
			if(menu.equals(item))			
			{				
				return true;				
			}
			if(menu.contains(item))
			{
				this.valueMeal = true;
				return true;
			}
		}
		return false;
	}




}
