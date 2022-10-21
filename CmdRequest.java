public class CmdRequest extends RecordedCommand{

    private Item requestedItem;
    private Member requestMem;
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
            if((requestedItem.getItemStatus().getStatus()!="Available")&&requestMem.WhetherOnholdthisItem(requestedItem)==false){
                requestedItem.hasAlreadyBorrowedBySamePerson(requestMem);
                requestMem.hasRequestedthisItem(requestedItem);
                requestMem.StillCanRequest();
                club.MemRequestItems(requestMem,requestedItem);
                System.out.printf("Done. This request is no. %d in the queue.\n",requestedItem.getQueueCount());
                addUndoCommand(this); 
                clearRedoList(); 
            }
            else if((requestedItem.getItemStatus().getStatus().equals("Available")||requestMem.WhetherOnholdthisItem(requestedItem))){
                throw new ExItemCurrentlyAvailable("The item is currently available.");
            }
            

         }catch(ExItemNotFound e){
			System.out.println(e.getMessage());
         } catch (ExMemberIDNotFound e) {
            System.out.println(e.getMessage());
        }  catch (ExItemCurrentlyAvailable e) {
            System.out.println(e.getMessage());
        } catch (ExItemAlreadyBorrowedBytheSameMember e) {
            System.out.println(e.getMessage());
        } catch (ExMemberAlreadyRequestedtheItem e) {
            System.out.println(e.getMessage());
        } catch (ExItemRequestQuotaExceed e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }
      
    }

    @Override
    public void undoMe() {
        Club club=Club.getInstance();
        club.CancelRequest(requestMem,requestedItem);
		addRedoCommand(this);        
    }

    @Override
    public void redoMe() {
        Club club=Club.getInstance();
        club.MemRequestItems(requestMem, requestedItem);;
		addUndoCommand(this);
        
    }
    
}
