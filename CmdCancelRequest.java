import java.util.ArrayList;


public class CmdCancelRequest extends RecordedCommand{
    private Item requestedItem;
    private Member requestMem;
    private ArrayList<Member> Queue;
    //private Day loanDate;
    //private Day OnholdDate;
    @Override
    public void execute(String[] cmdParts) {
        try{ 
            if(cmdParts.length!=3){
                throw new ExInsufficientCommand();
            }
            Club club=Club.getInstance();
            requestedItem=club.findItem(cmdParts[2]);
            requestMem=club.findMember(cmdParts[1]);
            club.findRequest(requestedItem,requestMem);
            Queue=new ArrayList<Member>();
            Queue.addAll(requestedItem.getQueue());
            club.CancelRequest(requestMem, requestedItem);
            System.out.println("Done.");
            addUndoCommand(this); 
             clearRedoList();
         }catch(ExItemNotFound e){
			System.out.println(e.getMessage());
         } catch (ExMemberIDNotFound e) {
            System.out.println(e.getMessage());
        }  catch(ExRequestedRecordNotFound e){
            System.out.println(e.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }
      
    }

    @Override
    public void undoMe() {
        Club club=Club.getInstance();
        club.reAddRequest(Queue,requestedItem,requestMem);
		addRedoCommand(this);        
    }

    @Override
    public void redoMe() {
        Club club=Club.getInstance();
        club.CancelRequest(requestMem, requestedItem);;
		addUndoCommand(this);
        
    }
    
    
}
