

public class CmdRegister extends RecordedCommand //<=== note the change
{

    private Member m;

	@Override
	public void execute(String[] cmdParts)
	{
		try{
		if(cmdParts.length!=3){
			throw new ExInsufficientCommand();
		}
		m=new Member(cmdParts[1], cmdParts[2]) ;
		addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
		clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
		System.out.println("Done.");
		}
		catch(ExMemberIDalreadyinUse e){
			System.out.println(e.getMessage());
		} catch (ExInsufficientCommand e) {
			System.out.println(e.getMessage());
		}
	}

   
	@Override
	public void undoMe()
	{
        Club club = Club.getInstance();
        club.removeMember(m);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
        Club club = Club.getInstance();
        club.addMember(m);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}