public class CmdStartNewDay  extends RecordedCommand{

    private String soldDay;
    private String snewDay;


	public void execute(String[] cmdParts)
	{
        try{
		if(cmdParts.length!=2){
            throw new ExInsufficientCommand();
        }
        SystemDate sd=SystemDate.getInstance();
        soldDay=sd.toString();
        snewDay=cmdParts[1];
        sd.set(snewDay);
        Club club=Club.getInstance();
        club.checkWhetherOnholdItemisdue();
        clearRedoList();
        addUndoCommand(this);
        System.out.println("Done.");
        }catch(ExInsufficientCommand e){
            System.out.println(e.getMessage());
        }

	}


    @Override
    public void undoMe() {
        
        SystemDate sd=SystemDate.getInstance();
        sd.set(soldDay);
    }


    @Override
    public void redoMe() {
     
        SystemDate sd=SystemDate.getInstance();
        sd.set(snewDay);
    }

}
