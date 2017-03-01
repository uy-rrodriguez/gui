package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javafx.stage.DirectoryChooser;

public class Management 
{
	private File currentDirectory;
	private final TextField managementFolder;
	private final TreeView managementTreeView;
	
	public Management(TreeView managementTreeView, TextField managementFolder)
	{
		this.managementTreeView = managementTreeView;
		this.managementFolder = managementFolder;
	}
	

	
   /* protected void loadTreeView(String path)
    {
        String[] r = path.split("/");
        TreeItem root = new TreeItem(r[r.length-1]);
        root.setExpanded(true);
        File f = new File(path);
        this.managementTreeView.setRoot(root);
        TreeItem tmp = browseDirectory((String)f.getAbsolutePath(), f.getName());
        root.getChildren().add(tmp);
    }
    
    private TreeItem browseDirectory(String path, String path2)
    {
        TreeItem root = new TreeItem(path2);
        root.setExpanded(true);
        		
		FileFilter directoryFilter = new FileFilter() 
		{
			public boolean accept(File file) 
			{
				if(file.isDirectory() || file.getName().endsWith(".html"))
					return true;
				else return false;
			}
		};
		String name;
        String path_name;
		File directory = new File(path);
        File[] dir_files = directory.listFiles(directoryFilter);

        for (File file : dir_files) {
            if (file.isDirectory()) 
            {
            	path_name = (String)file.getAbsolutePath();
                TreeItem tmp = browseDirectory((String)file.getAbsolutePath(), file.getName());
                root.getChildren().add(tmp);
            }
            else 
            {
            	name = getName(new File(path+"/info.txt"));
            	root.getChildren().add(new TreeItem(name));
            }
        }
        return root;
    }*/
    

    protected void loadTreeView(String path)
    {
        String[] r = path.split("/");
        String name;
        String path_name;
        TreeItem root = new TreeItem(r[r.length-1]);
        root.setExpanded(true);

		FileFilter directoryFilter = new FileFilter() 
		{
			public boolean accept(File file) 
			{
				if(file.isDirectory() || file.getName().endsWith(".html"))
					return true;
				else return false;
			}
		};
        
        File directory = new File(path);
        File[] dir_files = directory.listFiles(directoryFilter);

        for (File file : dir_files) {
            if (file.isDirectory()) 
            {
            	path_name = (String)file.getAbsolutePath();
                TreeItem tmp = browseDirectory((String)file.getAbsolutePath(), file.getName());
                root.getChildren().add(tmp);
            }
            else 
            {
            	name = getName(new File(path+"/info.txt"));
            	root.getChildren().add(new TreeItem(name));
            }
        }
        this.managementTreeView.setRoot(root);
    }
    
    private TreeItem browseDirectory(String path, String path2)
    {
        TreeItem root = new TreeItem(path2);
        root.setExpanded(true);
        		
		FileFilter directoryFilter = new FileFilter() 
		{
			public boolean accept(File file) 
			{
				if(file.isDirectory() || file.getName().endsWith(".html"))
					return true;
				else return false;
			}
		};
		String name;
        String path_name;
		File directory = new File(path);
        File[] dir_files = directory.listFiles(directoryFilter);

        for (File file : dir_files) {
            if (file.isDirectory()) 
            {
            	path_name = (String)file.getAbsolutePath();
                TreeItem tmp = browseDirectory((String)file.getAbsolutePath(), file.getName());
                root.getChildren().add(tmp);
            }
            else 
            {
            	name = getName(new File(path+"/info.txt"));
            	root.getChildren().add(new TreeItem(name));
            }
        }
        return root;
    }
    
    public String getName(File file)
    {
    	try
    	{
    		InputStream ips = new FileInputStream(file); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			String result = "";
			while ((line = br.readLine()) != null)
			{
				System.out.println(line);
				result += line +"\n";
			}
	    	return result;
    	}
    	catch(Exception e){
			System.out.println(e.toString());
			return "";
		}
    }

	public String setDirectory()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(currentDirectory);
		File selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory == null) return "";
		else{
			currentDirectory = new File(selectedDirectory.getAbsolutePath());
			return selectedDirectory.getAbsolutePath();
		}
	}
}