public class CmdCheckOut extends RecordedCommand{
    private Item item;
    private Member borrower;
    private Day loanDate;
    private Day Onholddate;
    private Member Onholder;
    private boolean isOnhold=false;
    @Override
    public void execute(String[] cmdParts) {
        try{ 
            if(cmdParts.length!=3){
                throw new ExInsufficientCommand();
            }
            Club club=Club.getInstance();
            if(club.findItem(cmdParts[2]).getItemStatus().getStatus()!="Borrowed"){
                item=club.findItem(cmdParts[2]);
            }
            else{
                 throw new ExItemBorrowedByOthers();
            }
            if(item.getItemStatus().getStatus()=="Available"){
                borrower=club.findMember(cmdParts[1]);
                borrower.StillCanLoan();
                loanDate=SystemDate.getInstance().clone();
                club.BorrowingAvailableItems(item, borrower,loanDate );
                System.out.println("Done.");
                addUndoCommand(this); 
                 clearRedoList(); 
            }
            else if(club.findItem(cmdParts[2]).getItemStatus().getStatus()=="Onhold"){
                isOnhold=true;
                Onholddate=item.getOnholdDate();
                Onholder=club.findMember(cmdParts[1]);
                loanDate=SystemDate.getInstance().clone();
                club.checkOutOnholdItem(Onholder, item, loanDate);
                System.out.println("Done.");
                addUndoCommand(this); 
                clearRedoList(); 
 
            }
         }catch(ExItemNotFound e){
            System.out.println(e.getMessage());
         } catch (ExItemBorrowedByOthers e) {
            System.out.println(e.getMessage());
 
        } catch (ExQuotaLimitForEachPerson e) {
            System.out.println(e.getMessage());
 
        } catch (ExMemberIDNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        } catch (ExItemOnholdByOthers e) {
            System.out.println(e.getMessage());
        }
       
    }
 
    @Override
    public void undoMe() {
        Club club=Club.getInstance();
        if(!isOnhold){
            club.resetBorrowingItem(item,borrower,loanDate);
 
        }
        else{
            club.resetCheckoutOnholdItem(item,Onholder,Onholddate);
        }
        addRedoCommand(this);  
 
    }
 
    @Override
    public void redoMe() {
        Club club=Club.getInstance();
        if(!isOnhold){
        club.BorrowingItemsAction(item,borrower, loanDate);
        }
        else{
            club.RecheckOutOnholdItem(Onholder, item, loanDate);
        }
        addUndoCommand(this);
    }
     
}