import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ListController {

	private ArrayList<String> weekTodoList;
	private int weekTodoAnzahl;
	private FileHandler fileHandler;
	private String filename;


	public ListController(String listName) throws FileNotFoundException, UnsupportedEncodingException {
		fileHandler = new FileHandler();
		filename = "lists//" + listName + ".txt";
		getWeekList();
	}

	/**
	 * Holt sich einen Random Plot aus einer Textdatei
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void getWeekList() throws FileNotFoundException, UnsupportedEncodingException {

		weekTodoList = new ArrayList<String>();

		// Wenn noch keine Liste existiert wird eine erstellt und zugewiesen
		try {
			if (!new File(filename).exists()) {
				fileHandler.createFile(filename);
			}
			weekTodoList = fileHandler.readFile(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		weekTodoAnzahl = weekTodoList.size();
	}

	/**
	 * Übergibt die Statusänderung an den Filehandler
	 * @param old Alter Status
	 * @param updated Neuer Status
	 * @throws IOException
	 */
	public void changeTodoStatus(String old, String updated) throws IOException {
		fileHandler = new FileHandler();
		fileHandler.updateLine(filename, old, updated);
	}

	public void resetTodoList() throws IOException {
		for (String s : weekTodoList) {
			String name;
			String sNew = null;
			// Wenn Wert vorher kein Bool hatte wird übernommen + false
			if (s.indexOf(',') < 0){
				sNew = s + ", false";
				// Wenn Wert vorher Bool hatte wird geschnitten
			} else {
				String[] parts = s.split(", ");
				name = parts[0];
				sNew = name + ", false";
			}
			changeTodoStatus(s, sNew);
		}
	}

	public void changeWeekList(){
	}


	public ArrayList<String> getWeekTodoList() {
		return weekTodoList;
	}

	public void setWeekTodoList(ArrayList<String> weekTodoList) {
		this.weekTodoList = weekTodoList;
	}

	public int getWeekTodoAnzahl() {
		return weekTodoAnzahl;
	}

	public static void main(String[] args) throws IOException {
		ListController test = new ListController("Weeklist");
		System.out.println(test.weekTodoList);
	}
}
