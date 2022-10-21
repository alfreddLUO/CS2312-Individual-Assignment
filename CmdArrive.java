public class CmdArrive extends RecordedCommand {
    private Item it;

	@Override
	public void execute(String[] cmdParts)
	{
		try{
		it=new Item(cmdParts[1], cmdParts[2]) ;
		addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
		clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
		System.out.println("Done.");
		
		}
		catch(ExItemIDalreadyinUse e){
			System.out.println(e.getMessage());
		}
	}

   
	@Override
	public void undoMe()
	{
        Club club = Club.getInstance();
        club.removeItem(it);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
        Club club = Club.getInstance();
        club.addItem(it);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
