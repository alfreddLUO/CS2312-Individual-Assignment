import java.util.ArrayList;
import java.util.Collections; //Provides sorting
public class Club {
    private ArrayList<Member> allMembers;	
	private ArrayList<Item>allItems;
	private ArrayList<Item>ListofOnholdItems;
    private static Club instance = new Club(); 

	private Club() { 
		allMembers = new ArrayList<Member>();
		allItems = new ArrayList<Item>();
		ListofOnholdItems=new ArrayList<Item>();
	 }


	public static Club getInstance() { return instance; }

	public void addMember(Member e) {// See how it is called in Member constructor (Step 3)
		allMembers.add(e);
		Collections.sort(allMembers); // allMembers
	}
	public void listClubMembers() {
		System.out.println(Member.getListingHeader()); // Member
		for (Member m: allMembers)
			System.out.println(m); 
	}
	public void removeMember(Member m){
		allMembers.remove(m);
	}

	public void addItem(Item item) {
		allItems.add(item);
		Collections.sort(allItems);
	}


	public void removeItem(Item it) {
		allItems.remove(it);
	}


    public Item ReturntheExistedItem(String aID){
		for(Item it: allItems){
			if(it.getID().equals(aID)){
				return it;
			}
		}
		return null;
    }


	public void listClubItems() {
		System.out.println(Item.getListingHeader()); // Member
		for (Item it: allItems)
			System.out.println(it); 
	}


	public Item findItem(String itID)throws ExItemNotFound {
		for(Item it: allItems){
			if(it.getID().equals(itID)){
				return it;
			}
		}
		throw new ExItemNotFound();
	}


    public Member findMember(String MemID)throws ExMemberIDNotFound {
        for(Member m: allMembers){
			if(m.getID().equals(MemID)){
				return m;
			}
		}
		throw new ExMemberIDNotFound();
    }
	public void BorrowingAvailableItems(Item it,Member m,Day d) throws ExItemBorrowedByOthers{
        if(it.getItemStatus().getStatus()!="Available"){
			throw new ExItemBorrowedByOthers();
		}
		it.setItemStatusToBorrowed(m, d);
		m.addmyItem(it);  
    }

	public void BorrowingItemsAction(Item it,Member m,Day d)  {
		it.setItemStatusToBorrowed(m, d);
		m.addmyItem(it);  
	}

	public Member ReturntheExistedMember(String aID){
		for(Member m: allMembers){
			if(m.getID().equals(aID)){
				return m;
			}
		}
		return null;
	}


	public void resetBorrowingItem(Item item, Member borrower, Day loanDate) {
		item.resetToAvailable();
		borrower.removeBorrowedItem(item);
	}


	public void ReturnItems(Item item, Member returner) {
		item.resetToAvailable();
		returner.returnItem(item);
	}


	public void MemRequestItems(Member requestMem, Item requestedItem) {
		requestedItem.beingQueued(requestMem);
		requestMem.requestItem(requestedItem);
	}


	public void CancelRequest(Member requestMem, Item requestedItem) {
		requestedItem.cancelQueue(requestMem);
		requestMem.CancelRequest(requestedItem);
	}


	public void setOnhold(Member onholder, Item item) {
		Day OnholdDate=SystemDate.getInstance().clone();
		item.setOnholdStatus(onholder,OnholdDate);
		onholder.setOnholdItem(item);
		ListofOnholdItems.add(item);

	}


	public void checkOutOnholdItem(Member onholder, Item item, Day loanDate)throws ExItemOnholdByOthers {
		if(item.getOnholder()!=onholder){
			throw new ExItemOnholdByOthers();
		}
		BorrowingItemsAction(item, onholder, loanDate);
		item.checkOutOnhold();
		onholder.checkOutOnhold(item);
		ListofOnholdItems.remove(item);

	}

	public void RecheckOutOnholdItem(Member onholder, Item item, Day loanDate) {
		BorrowingItemsAction(item, onholder, loanDate);
		item.checkOutOnhold();
		onholder.checkOutOnhold(item);
		ListofOnholdItems.remove(item);

	}


	public void resetCheckoutOnholdItem(Item item, Member onholder, Day onholddate) {
		item.resetToAvailable();
		onholder.removeBorrowedItem(item);
		item.setOnholdStatus(onholder, onholddate);
		onholder.setOnholdItem(item);
		ListofOnholdItems.add(item);

	}


	public void findRequest(Item requestedItem, Member requestMem)throws ExRequestedRecordNotFound {
		if(requestedItem.getQueue().contains(requestMem)&&requestMem.getRequestQueue().contains(requestedItem)){
			return;
		}
		throw new ExRequestedRecordNotFound();
	}


	public void reAddRequest(ArrayList<Member> queue, Item requestedItem, Member requestMem) {
		requestedItem.changeQueue(queue);
		requestMem.requestItem(requestedItem);
	}
	public void checkWhetherOnholdItemisdue(){

		ArrayList<Item> tempList=new ArrayList<Item>();
		tempList.addAll(ListofOnholdItems);
		for(Item it:ListofOnholdItems){
			if(Day.OutofDate(SystemDate.getInstance(),it.getOnholddueDate())){
				System.out.printf("On hold period is over for %s %s.\n",it.getID(),it.getName());
				it.goOnholdDueAction();
				tempList.remove(it);
			}
		}
		ListofOnholdItems.clear();
		ListofOnholdItems.addAll(tempList);
		
	}


    public void undoOnholdItemAction(Item item, Member onholder,ArrayList<Member>Queue) {
		onholder.requestItem(item);
		item.changeQueue(Queue);
		System.out.printf("Sorry. %s %s please ignore the pick up notice for %s %s.\n",onholder.getID(),onholder.getName(),item.getID(),item.getName());
    }


	


}
