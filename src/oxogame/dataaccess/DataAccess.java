package oxogame.dataaccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import oxogame.player.HumanPlayer;
import oxogame.player.Player;
import oxogame.player.PlayerManager;


public class DataAccess {
	
	public static ArrayList<String> readData() throws IOException
	{
		ArrayList<String> linesList = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader("playerData.txt"));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    linesList.add(line);
		}
		reader.close();
		return linesList;
	}
	
	public static void searchAndWritePlayer(Player player) throws IOException
	{
		ArrayList<String> wholeFile = new ArrayList<String>();
		BufferedWriter out = null;
		try {
			wholeFile = readData();
		} catch (IOException e) {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("playerData.txt"), "UTF-8"));
		}
		ArrayList<String> currNamesList = new ArrayList<>();
		if(out == null)
		{
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("playerData.txt"), "UTF-8"));
		}
		for(int i=0;i<wholeFile.size();i++)
		{
			if(wholeFile.get(i).split(";")[0].equalsIgnoreCase(player.getName()) && player instanceof HumanPlayer)
			{
				String newLine = player.getName()+";"+player.getWins()+";"+player.getLosses()+";"+player.getDrawn()+";";
				wholeFile.set(i, newLine);
			}
			currNamesList.add(wholeFile.get(i).split(";")[0]);
		}
		
		if(!currNamesList.contains(player.getName()) && player instanceof HumanPlayer)
		{
			String newLine = player.getName()+";"+player.getWins()+";"+player.getLosses()+";"+player.getDrawn()+";";
			wholeFile.add(newLine);
		}
		
		for(String line : wholeFile)
		{
			out.write(line+"\n");
		}
		
		out.close();
	}
	
}
