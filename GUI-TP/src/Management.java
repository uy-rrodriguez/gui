import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Management 
{
	private String path;
	
	public Management(String path)
	{
		this.path = path;
	}
	
	public String[] listFiles()
	{
		FilenameFilter javaFilter = new FilenameFilter() 
		{
			public boolean accept(File arg0, String arg1) 
			{
				return arg1.endsWith(".html");
			}
		};

		File directory = new File(path);
		String[] dir_files = directory.list(javaFilter);
		return dir_files;
	}
	
	public ArrayList<String> listDir()
	{
		File directory = new File(path);
		File[] dir = directory.listFiles();
		ArrayList<String> dir_d = new ArrayList<String>();
		for(File d : dir)
		{
			if(d.isDirectory())
				dir_d.add(d.getName());
		}
		return dir_d;
	}
}

