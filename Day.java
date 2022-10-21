public class Day implements Cloneable{
	
	private int year;
	private int month;
	private int day;
	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}
	public static boolean OutofDate(Day nowDay,Day dueDay){
		if(nowDay.year>dueDay.year){
			return true;
		}
		else if(nowDay.year==dueDay.year){
			if(nowDay.month>dueDay.month){
				return true;
			}
			else if(nowDay.month==dueDay.month){
				if(nowDay.day>dueDay.day){
					return true;
				}
				else{
					return false;
				}
			}
			return false;
		}
		return false;
	}

	public boolean isEndOfAMonth() 
	{
		if (valid(year,month,day+1)) //A smart methd: check whether (year month [day+1]) is still a valid date
			return false;
		else
			return true;
	}
    // create and return a new Day object which is the "next day" of the current day object
	public Day next() 
	{
		if (isEndOfAMonth())
			if (month==12)
				return new Day(year+1,1,1); //Since the current day is the end of the year, the next day should be Jan 01
			else
				return new Day(year,month+1,1); // <== your task: first day of next month
		else
			return new Day(year,month,day+1);  // <== your task: next day of current month
	}
	public static Day addDay(Day day,int d){
		for(int i=0;i<d;i++){
			day=day.next();
		}
		return day;
	}

	// Return a string for the day like dd MMM yyyy
    /*public String toString() {
		final String[] MonthNames = {
				"Jan", "Feb", "Mar", "Apr",
				"May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec"};
		
		return day+" "+ MonthNames[month-1] + " "+ year;
	}*/
    @Override
    public String toString() {		
	    return day+"-"+ MonthNames.substring((month-1)*3,month*3) + "-"+ year; // (month-1)*3,(month)*3
    }

	
    
    public static final String MonthNames= "JanFebMarAprMayJunJulAugSepOctNovDec";

public void set(String sDay) { //Set year,month,day based on a string like 01-Jan-2021
	String[] sDayParts = sDay.split("-");
	this.year = Integer.parseInt(sDayParts[2]); //Apply Integer.parseInt for sDayParts[2]; 
	this.day = Integer.parseInt(sDayParts[0]); 
	this.month = MonthNames.indexOf(sDayParts[1])/3+1;
}

public Day(String sDay) {set(sDay);} //Constructor, simply call set(sDay)




@Override
public Day clone() {
	Day copy =null;
	try {
        copy = (Day)super.clone();
    } catch (CloneNotSupportedException e) {
        e.printStackTrace();
    }
	return copy;
}


}