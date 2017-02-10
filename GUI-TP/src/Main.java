import java.util.ArrayList;

public class Main {

	
	public static void main(String[] args) 
	{
		
		Management m= new Management("./test/");
		String files[] = m.listFiles();
		for(String file : files)
			System.out.println("file :" + file);
		
		ArrayList<String> dirs = m.listDir();
		for(String dir : dirs)
			System.out.println("dir : " + dir);
	
	}

}
