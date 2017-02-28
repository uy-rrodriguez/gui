package controllers;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;

import javafx.stage.DirectoryChooser;

public class Management 
{
	private String path;
	private File currentDirectory;
	private final TreeView managementTreeView;

	
	public Management(TreeView managementTreeView)
	{
		this.managementTreeView = managementTreeView;
		this.buildTreeView();
	}
	
	private void buildTreeView()
	{
		//this.managementTreeView.getSelectionModel().selectedItemProperty()
	}
	
	public String setDirectory()
	{
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(currentDirectory);
		File selectedDirectory = dc.showDialog(null);
		if(selectedDirectory == null) return "";
		else{
			currentDirectory = new File(selectedDirectory.getAbsolutePath());
			return selectedDirectory.getAbsolutePath();
		}
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