import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {

	public static void main(String [] args) throws FileNotFoundException {	
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = new Scanner(new File(filepathname));
		
		//The first command in the file must be to set the system date 
		//(eg. "startNewDay 03-Jan-2021"); and it cannot be undone
		String cmdLine1 = inFile.nextLine();
		String[] cmdLine1Parts = cmdLine1.split(" ");
		System.out.println("\n> "+cmdLine1);
		SystemDate.createTheInstance(cmdLine1Parts[1]);
		
		while (inFile.hasNext()) {
			String cmdLine = inFile.nextLine().trim();
			
			//Blank lines exist in data file as separators.  Skip them.
			if (cmdLine.equals("")) continue;  

			System.out.println("\n> "+cmdLine);
			
			//split the words in actionLine => create an array of word strings
			String[] cmdParts = cmdLine.split(" "); 
			try{
			if (cmdParts[0].equals("register"))
				(new CmdRegister()).execute(cmdParts);
			else if (cmdParts[0].equals("arrive"))
				(new CmdArrive()).execute(cmdParts);
			else if (cmdParts[0].equals("listMembers"))
				(new CmdListMembers()).execute(cmdParts);
			else if (cmdParts[0].equals("listItems"))
				(new CmdListItems()).execute(cmdParts);
			else if (cmdParts[0].equals("checkout"))
				(new CmdCheckOut()).execute(cmdParts);
			else if (cmdParts[0].equals("checkin"))
				(new CmdCheckIn()).execute(cmdParts);
			else if (cmdParts[0].equals("request"))
				(new CmdRequest()).execute(cmdParts);
			else if (cmdParts[0].equals("cancelRequest"))
				(new CmdCancelRequest()).execute(cmdParts);
			else if (cmdParts[0].equals("startNewDay"))
				(new CmdStartNewDay()).execute(cmdParts);
			else if (cmdParts[0].equals("undo"))
				RecordedCommand.undoOneCommand();
			else if (cmdParts[0].equals("redo"))
				RecordedCommand.redoOneCommand();
			else{
				throw new ExUnknownCommand("Unknown command - ignored.");
			}
		}catch(ExUnknownCommand e){
			System.out.println(e.getMessage());
		}
		}
		inFile.close();
			
		in.close();
	}
}