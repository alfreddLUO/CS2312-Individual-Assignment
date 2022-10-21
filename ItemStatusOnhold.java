

public class ItemStatusOnhold implements ItemStatus{

    private Member Onholder;
    private Day Onholddate;
    private Day Onholdduedate;

    public ItemStatusOnhold(Member Onholder, Day onholdDate) {
        this.Onholder=Onholder;
        this.Onholddate=onholdDate;
        this.Onholdduedate=Day.addDay(Onholddate, 3);
    }

    @Override
    public String getStatusandInfo(Item item) {
        String status=String.format("On holdshelf for %s %s until %s",Onholder.getID(),Onholder.getName(),Onholdduedate);
        if(item.getQueue().size()==0){
            return status;
        }
        String newstatus=String.format("On holdshelf for %s %s until %s + %d request(s): ",Onholder.getID(),Onholder.getName(),Onholdduedate.toString(),item.getQueueCount());
        for(int i=0;i<item.getQueueCount();i++){
            newstatus+=item.getQueue().get(i).getID()+' ';
        }
        return newstatus;
    }

    @Override
    public String getStatus() {
        return "Onhold";
    }

}
