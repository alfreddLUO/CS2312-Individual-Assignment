import java.util.ArrayList;

public class CmdCheckIn extends RecordedCommand{
    private Item item;
    private Member returner;
    private Member Onholder;
    private Day preloanDate;
    private boolean isOnhold=false;
    private ArrayList<Member> PreQueue=new ArrayList<Member>();
    @Override
    public void execute(String[] cmdParts) {
        try{ 
            if(cmdParts.length!=3){
                throw new ExInsufficientCommand();
            }
            Club club=Club.getInstance();
            item=club.findItem(cmdParts[2]);
            returner=club.findMember(cmdParts[1]);
            item.hasBorrowedBythisPerson(returner);
            preloanDate=item.getloanDate();
            club.ReturnItems(item, returner);
            if(item.myQueueisNotEmpty()){
                PreQueue.addAll(item.getQueue());
                isOnhold=true;
                Onholder=item.getQueue().get(0);
                club.setOnhold(Onholder,item);
                System.out.printf("Item [%s %s] is ready for pick up by [%s %s]. On hold due on %s.\n",item.getID(),item.getName(),item.getOnholder().getID(),item.getOnholder().getName(),Day.addDay(item.getOnholdDate(), 3));
            }
            System.out.println("Done.");
            addUndoCommand(this); 
            clearRedoList(); 
         }catch(ExItemNotFound e){
			System.out.println(e.getMessage());
         } catch (ExMemberIDNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExItemNotBorrowedBythisMember e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }
      
    }

    @Override
    public void undoMe() {
        Club club=Club.getInstance();
        if(!isOnhold){
        club.BorrowingItemsAction(item,returner,preloanDate);
        }
        else{
            club.BorrowingItemsAction(item,returner,preloanDate);
            club.undoOnholdItemAction(item, Onholder,PreQueue);
        }
		addRedoCommand(this);        
    }

    @Override
    public void redoMe() {
        Club club=Club.getInstance();
        if(!isOnhold){
        club.ReturnItems(item,returner);
        }
        else{
            club.ReturnItems(item,returner);
            club.setOnhold(Onholder, item);
            System.out.printf("Item [%s %s] is ready for pick up by [%s %s].  On hold due on %s.\n",item.getID(),item.getName(),Onholder.getID(),Onholder.getName(),item.getOnholddueDate());
        }
		addUndoCommand(this);
        
    }
}
