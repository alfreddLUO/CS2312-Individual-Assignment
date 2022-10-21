import java.util.ArrayList;

public class Member implements Comparable<Member>{
    private String id;
    private String name;
    private Day joinDate;
    private ArrayList<Item>myItems;
    private ArrayList<Item> myOnHolds;
    private ArrayList<Item>myRequest;

    public Member(String id, String name) throws ExMemberIDalreadyinUse{
        Club club=Club.getInstance();
        if(club.ReturntheExistedMember(id)==null)	{
        this.id=id;
        this.name=name;
        joinDate=SystemDate.getInstance().clone();
        Club.getInstance().addMember(this);
        myRequest=new ArrayList<Item>();
        this.myItems=new ArrayList<Item>();
        this.myOnHolds=new ArrayList<Item>();
        }
        else{
            throw new ExMemberIDalreadyinUse(club.ReturntheExistedMember(id));
        }

    }

    @Override
    public String toString() {
    //Learn: "-" means left-aligned
     return String.format("%-5s%-9s%11s%7d%13d", id, name, joinDate, myItems.size(), this.myRequest.size());
     }
     public String getID(){
         return id;
     }
     public String getName(){
         return name;
     }

    public static String getListingHeader() {
        return String.format("%-5s%-9s%11s%11s%13s", "ID", "Name", "Join Date ", "#Borrowed", "#Requested");
    }
    @Override
    public int compareTo(Member another) {
        if (this.id.equals(another.id)) return 0;
        else if (this.id.compareTo(another.id)>0) return 1;
        else return -1;
    }

    public void StillCanLoan() throws ExQuotaLimitForEachPerson{
        if(myItems==null||myItems.size()<6){
            return;
        }
        throw new ExQuotaLimitForEachPerson();
    }
    
    public void addmyItem(Item it) {
        myItems.add(it);
       // myloanquota.add(it);
    }

    public void removeBorrowedItem(Item item) {
        myItems.remove(item);
    }

    public void returnItem(Item item) {
        myItems.remove(item);
    }

    public void hasRequestedthisItem(Item requestedItem) throws ExMemberAlreadyRequestedtheItem{
        if(myRequest.contains(requestedItem)){
           throw new ExMemberAlreadyRequestedtheItem();
        }
        return;
    }

    public void StillCanRequest()throws ExItemRequestQuotaExceed {
        if(myRequest.size()<3){
            return;
        }
        throw new ExItemRequestQuotaExceed();
    }

    public void requestItem(Item item){
        myRequest.add(item);
    }

    public void CancelRequest(Item requestedItem) {
        myRequest.remove(requestedItem);
    }

    public void setOnholdItem(Item item) {
        myRequest.remove(item);
        myOnHolds.add(item);
    }

    public void checkOutOnhold(Item item) {
        myOnHolds.remove(item);
    }

    public ArrayList<Item> getRequestQueue() {
        return myRequest;
    }

    public boolean WhetherOnholdthisItem(Item item) {
        if(myOnHolds.contains(item)){
            return true;
        }
        return false;
    }
    

}
