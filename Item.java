import java.util.ArrayList;

public class Item implements Comparable<Item> {
    private String id;
    private String name;
    private Day arriveDate;
    private ItemStatus status;
    private Member borrowingMem;
    private Day loanDate;
    private ArrayList<Member> myQueue;
    private Member Onholder;
    private Day OnholdDate;
   
    public Item(String id, String name)throws ExItemIDalreadyinUse{
        Club club=Club.getInstance();
		if(club.ReturntheExistedItem(id)==null){
            this.id=id;
            this.name=name;
            arriveDate=SystemDate.getInstance().clone();
            Club.getInstance().addItem(this);
            status=new ItemStatusAvailable();
            myQueue=new ArrayList<Member>();
		}
		else{
			throw new ExItemIDalreadyinUse(club.ReturntheExistedItem(id));
		}	
        
    }

    @Override
    public String toString() {
    //Learn: "-" means left-aligned
    return String.format("%-5s%-17s%11s   %s", id, name, arriveDate, status.getStatusandInfo(this));  
}

    public static String getListingHeader() {
        return String.format("%-5s%-17s%11s   %s", "ID", "Name", "  Arrival  ", "Status");  
    }


    @Override
    public int compareTo(Item another) {
        if (this.id.equals(another.id)) return 0;
        else if (this.id.compareTo(another.id)>0) return 1;
        else return -1;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    
    public void resetToAvailable(){
        status=new ItemStatusAvailable();
        borrowingMem=null;
        loanDate=null;
    }

    public void setItemStatusToBorrowed(Member m, Day d){
        borrowingMem=m;
        loanDate=d;
        status=new ItemStatusBorrowed(m,d) ;
    }
    public ItemStatus getItemStatus() {
        return status;
    }
    public void hasBorrowedBythisPerson(Member m)throws ExItemNotBorrowedBythisMember{
        if(this.borrowingMem!=null&&this.borrowingMem.equals(m)){
            return;
        }
       throw new ExItemNotBorrowedBythisMember();
    }
    public Day getloanDate(){
        return loanDate;
    }

    public void beingQueued(Member m){
        myQueue.add(m);
    }

    public int getQueueCount() {
        return myQueue.size();
    }

    public void cancelQueue(Member requestMem) {
        myQueue.remove(requestMem);
    }
    public ArrayList<Member> getQueue(){
        return myQueue;
    }

    public boolean myQueueisNotEmpty() {
        if(myQueue.size()>=1){
            return true;
        }
        return false;
    }

    public void setOnholdStatus(Member onholder, Day onholdDate) {
        this.Onholder=onholder;
        this.OnholdDate=onholdDate;
        myQueue.remove(onholder);
        status=new ItemStatusOnhold(Onholder,OnholdDate);
    }

    public void checkOutOnhold() {
        OnholdDate=null;
        Onholder=null;
    }

    public Day getOnholdDate() {
        return OnholdDate;
    }

    public Member getOnholder() {
        return Onholder;
    }

    public void changeQueue(ArrayList<Member> queue) {
        myQueue.clear();
        myQueue.addAll(queue);
    }

    public Day getOnholddueDate() {
        return Day.addDay(OnholdDate, 3);
    }

    public void goOnholdDueAction() {
        if(myQueue.isEmpty()){
            status=new ItemStatusAvailable();
            Onholder=null;
            OnholdDate=null;
        }
        else{
            Onholder=myQueue.get(0);
            myQueue.remove(Onholder);
            OnholdDate=SystemDate.getInstance().clone();
            Onholder.setOnholdItem(this);
            status=new ItemStatusOnhold(Onholder, OnholdDate);
            System.out.printf("Item [%s %s] is ready for pick up by [%s %s]. On hold due on %s.\n",this.getID(),this.getName(),Onholder.getID(),Onholder.getName(),Day.addDay(OnholdDate, 3));
        }
    }

    public void hasAlreadyBorrowedBySamePerson(Member requestMem)throws ExItemAlreadyBorrowedBytheSameMember {
        if(this.borrowingMem==(requestMem)){
            throw new ExItemAlreadyBorrowedBytheSameMember();
        }
    }



    
}
