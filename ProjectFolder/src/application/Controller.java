package application;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
public class Controller {
	Trie Dic1=new Trie();
	Trie Dic2=new Trie();
	@FXML 	
	private TextField Text;
	@FXML 	
	private ListView<String> Sugestions;
	@FXML 	
	private TabPane Dictonaries;
	@FXML 	
	private Tab Grammata24;
	@FXML
	private Tab Babiniotis;
	@FXML
	private SplitPane Pisw;
	@FXML
	private Label GramInfo;
	@FXML
	private Label BambInfo;
	private void print(String s)
	{
		String bamStr, gramStr;
		bamStr=Dic1.findWord(s);
		gramStr=Dic2.findWord(s);
		BambInfo.setText(bamStr);
		GramInfo.setText(gramStr);
		Babiniotis.setDisable(false);
		Grammata24.setDisable(false);
		SingleSelectionModel<Tab> selectionModel = Dictonaries.getSelectionModel();
		if(Dic1.checkWord(s)==false){
			Babiniotis.setDisable(true);
			selectionModel.select(1);
		}
		if(Dic2.checkWord(s)==false){
			Grammata24.setDisable(true);
			selectionModel.select(0);
		}
	}
	@FXML 
	public void search()
	{
		
		String input=Text.getText();
		print(input);
	}
	@FXML 
	public void chooseWord()
	{
		ObservableList<String> lista=Sugestions.getSelectionModel().getSelectedItems();		
		String input=lista.get(0);
		Text.setText(input);
		print(input);
	}
	@FXML
	public void findSugestions()
	{
		String input=Text.getText();
		ArrayList<String> lista=Dic1.isPref(input);
		Trie temp=new Trie();
		for(int i=0; i<lista.size(); i++)
			temp.addWord(lista.get(i), "");
		ArrayList<String> arr=Dic2.isPref(input);
		for(int i=0; i<arr.size(); i++)
			if(!(temp.checkWord(arr.get(i))))
				lista.add(arr.get(i));
		//System.out.println(lista.size());
		ObservableList<String> lista1=FXCollections.observableArrayList(lista);
		Sugestions.setItems(lista1);
	}
	@FXML
	public void initialize() throws Exception{
		InputStream bambi = ClassLoader.getSystemClassLoader().getResourceAsStream("Bambiniotis.txt");
		InputStream gram = ClassLoader.getSystemClassLoader().getResourceAsStream("Grammata24.txt");
		String UTF8 = "UTF-8";
        int BUFFER_SIZE = 8192;
        	
        BufferedReader bam = new BufferedReader(new InputStreamReader(bambi, UTF8), BUFFER_SIZE);
        BufferedReader gr=new BufferedReader(new InputStreamReader(gram, UTF8), BUFFER_SIZE);
        
        String word=bam.readLine();
        while(word!=null){
        	String expl=bam.readLine();
        	Dic1.addWord(word, expl);
        	word=bam.readLine();
        }
        
        String line=gr.readLine();
        while(line!=null){
        	word=line.split(" ")[0];
        	Dic2.addWord(word, line);
        	line=gr.readLine();
        }
        //Font 
        BambInfo.setWrapText(true);
		BambInfo.setTextOverrun(OverrunStyle.CLIP);
		BambInfo.setAlignment(Pos.TOP_LEFT);;
		GramInfo.setWrapText(true);
		GramInfo.setTextOverrun(OverrunStyle.CLIP);
		GramInfo.setAlignment(Pos.TOP_LEFT);;
    }   
}
