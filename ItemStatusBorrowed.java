public class ItemStatusBorrowed implements ItemStatus {

    private Member borrowingMember;
    private Day loanDate;
    public ItemStatusBorrowed(Member m, Day d){
        this.borrowingMember=m;
        this.loanDate=d;
    }
    

    @Override
    public String getStatusandInfo(Item item) {
        if(item.getQueue().size()==0){
        return "Borrowed by " + borrowingMember.getID()+' '+borrowingMember.getName() + " on " +loanDate;
        }
        String newStatus=String.format("Borrowed by %s %s on %s + %d request(s): " ,borrowingMember.getID(),borrowingMember.getName(),loanDate,item.getQueueCount());
        for(int i=0;i<item.getQueueCount();i++){
            newStatus+=item.getQueue().get(i).getID()+' ';
        }
        return newStatus;
    }


    @Override
    public String getStatus() {
        return "Borrowed";
    }



    
}
