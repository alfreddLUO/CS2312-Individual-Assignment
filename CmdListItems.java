public class CmdListItems implements Command{
    @Override
    public void execute(String[] cmdParts){
        Club club=Club.getInstance();
        club.listClubItems();
    }
}
