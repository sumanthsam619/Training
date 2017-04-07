package ElectionSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


class Region{
  private String name;
  private ArrayList<String> contestants;
  private Map<Integer, String[]> votes;
  private HashMap<String, Integer> points;
  private int invalidVoteCount;
  private static final Integer firstPrefernce=3;
  private static final Integer secondPrefernce=2;
  private static final Integer thirdPrefernce=1;
  
  
  public void addContestant(String[] contestantList)	
  {
	   for(String contestantName:contestantList)
	     contestants.add(contestantName);
	   
	  
  }
  
  
  public void addVotes(Integer voterID, String[] listOfPreferences)
  {      
	     if(!ElectionSystem.voterIDs.contains(voterID) && (listOfPreferences.length>0 && listOfPreferences.length<4)&& isListOfPreferencesValid(listOfPreferences) )
	            {
			      votes.put(voterID, listOfPreferences);
		
	            }else{
		          invalidVoteCount++;
	                 }
	  
  }
 
  
  public HashMap<String, Integer> getContestantPoints()
  {
	 if(!votes.isEmpty())
	 {
	  for(Map.Entry<Integer, String[]> entry : votes.entrySet())
	     {
                     String[] list=entry.getValue();
                     int rank=1;
              for(String member: list)    	
              	 {  
            	     Integer actualValue= points.get(member);               
            	     if(actualValue==null)
                        actualValue=0;	                   
            	     if(rank==1)
            		    points.put(member,calculatePoints(actualValue,firstPrefernce) );  
            	     else if(rank==2)
            	        points.put(member,calculatePoints(actualValue,secondPrefernce) ); 
                	 else if(rank==3)
             	        points.put(member,calculatePoints(actualValue,thirdPrefernce) );
            	  rank++;
                }            
         }	  
	     return points;
	 }
	 else
		 return null;
  }
  
  
  public Integer calculatePoints(int actualValue, int toBeAdded)
  {
	   return actualValue+toBeAdded;
	  
  }
  
  public boolean isContestantValid(String contestantName)
  {   
	  return contestants.contains(contestantName);
	  
  }
 
  public Region(String name)
  {
	 this.name=name; 
	 contestants= new ArrayList<String>();	
	 votes=new HashMap<Integer, String[]>();
	 points=new HashMap<String, Integer>();
	 invalidVoteCount=0;
  }
  public boolean isListOfPreferencesValid(String[] memberList)
  {
	  Set<String> memberSet = new HashSet<String>();
	  for (String memberName : memberList)
	  { 
	    if ((memberSet.contains(memberName.trim())||(!isContestantValid(memberName)))) 
	    return false;	
	    memberSet.add(memberName);
	  }
	    return true;	  
  }

  public void displayRegionalHead()
  {   if(!points.isEmpty())
  	  {
	  Map.Entry<String, Integer> entry =  MaxHashMap.maxHashMapValue(points);
	  System.out.println("Regional Head for region "+toString()+" is : "+entry.getKey()+" with total points: "+entry.getValue());
  	  }
  else{
	  System.out.println("No valid votes found");
      }
  }
  
 
  public int getInvalidVoteCount() {
	return invalidVoteCount;
}


public String toString()
  {
	   return name;	  
  }
  
}
class MaxHashMap{
	public static Entry<String, Integer> maxHashMapValue(HashMap<String, Integer> map){
	 int maxValueInMap=(Collections.max(map.values()));  // This will return max value in the Hashmap
     for (Entry<String, Integer> entry : map.entrySet()) {  // Itrate through hashmap
         if (entry.getValue()==maxValueInMap) {
            return entry;     // Print the key with max value
         }
     }
     return null;
	}
}
public class ElectionSystem {	
	
	private static  int regionCount;
	private static Region[] regions=new Region[5];
	public static Set<Integer> voterIDs;
	private static HashMap<String, Integer> contestantPoints;
	private static  HashMap<String,Integer> totalcontestantPoints=null;
	
	
	public static void main(String args[])
	{  	  
		voterIDs=new HashSet<Integer>();		
		try {
			readData();  
			} 
		catch (Exception e) 
		    {		
    	    e.printStackTrace();
	        }   
		
        displayCheifOfficer();   
        displayRegionalHeads();   
	}
	
	public static void mergeAllRegionContestantPoints(HashMap<String,Integer> a)
	{
		 if(totalcontestantPoints==null)
		 	{	 totalcontestantPoints = new HashMap<String,Integer>();
		 			for (String s : a.keySet()) 
		 			{
		 				totalcontestantPoints.put(s, a.get(s));
		 			}
		 	}
		else	
			{
					for (String s : a.keySet())
					{
						if (totalcontestantPoints.containsKey(s))
						{
							totalcontestantPoints.put(s, a.get(s) + totalcontestantPoints.get(s));
						} else {
							totalcontestantPoints.put(s, a.get(s));
							   }
					}
			}
	 }
	
	public static void displayCheifOfficer()
    {
			for(int i=0; i<regionCount; i++)
			{
				contestantPoints=regions[i].getContestantPoints();
				if(contestantPoints!=null)
				mergeAllRegionContestantPoints(contestantPoints);
			}
			Map.Entry<String, Integer> entry =  MaxHashMap.maxHashMapValue(totalcontestantPoints);
			System.out.println("Chief Officer is : "+entry.getKey()+" with total points: "+entry.getValue());
    }

	public static void displayVoterIDs()
	{		
		for (Integer s : voterIDs)
		    System.out.println(s);		
	}

	public static void displayRegionalHeads()
	{
		for(int i=0;i<regionCount;i++)
		{
			regions[i].displayRegionalHead();
			System.out.println("Invalid vote count in region "+regions[i].toString()+" is "+regions[i].getInvalidVoteCount());
		
		}
	}
 
	public static void readData() throws Exception
	{   
		regionCount=0;
		FileReader in = new FileReader("D:/voting.dat");
		BufferedReader br = new BufferedReader(in);
		String line;
    
		while ((line = br.readLine()) != null) 
		{   	
			String[] tokens=line.split("/");
		
			if(tokens.length>1)
				{  	String[] regionContestants=tokens[1].split(" ");
					regions[regionCount]=new Region(tokens[0].trim());
					regions[regionCount].addContestant(regionContestants);
					regionCount++;
				}
				String region=line.trim();
     
			if(isRegionDetected(region))
				{
					for(int i=0; i<regionCount;i++)
						{
							if(regions[i].toString().equals(region))
								{   do
									{
									region = br.readLine();
									if(region==null)
									return;
									String[] votes = region.split(" ");
									if(votes.length>1)
										{  
											Integer voterID= Integer.parseInt(votes[0].trim());   		      
											regions[i].addVotes(voterID,Arrays.copyOfRange(votes, 1, votes.length) );  
											voterIDs.add(voterID);
										}
									}while(!isRegionDetected(region.trim()));  		  
								}
						}
				}
		}
		br.close();
		in.close();
		
	}

	public static boolean isRegionDetected(String region)
	{
		return (region.equals("R1")||region.equals("R2")||region.equals("R3")||region.equals("R4")||region.equals("R5")); 
	 
	}
}

