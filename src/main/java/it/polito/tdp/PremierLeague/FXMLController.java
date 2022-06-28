/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Opponent;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.PlayerDT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	
    	float x = -1;
    	try {
    		x = Float.parseFloat(this.txtGoals.getText());
    	} catch (Exception e) {
    		this.txtResult.setText("inserire un valore numerico");
    	}
    	
    	if(x <= 0)
    		this.txtResult.setText("inserire un valore numerico maggiore di zero");
    	
    	this.model.creaGrafo(x);
    	
    	int a = this.model.getnVertici();
    	int b = this.model.getnArchi();
    	
    	this.txtResult.appendText("Grafo creato!\n");
    	this.txtResult.appendText("#VERTICI: "+a+"\n");
    	this.txtResult.appendText("#ARCHI: "+b+"\n");
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	int k = 0;
    	try {
    		k = Integer.parseInt(this.txtK.getText());
    	}catch(Exception e) {
    		this.txtResult.appendText("inserire un numero intero positivo per creare il tuo DT!");
    	}
    	if(k<=0)
    		this.txtResult.appendText("inserire un numero intero positivo per creare il tuo DT!");
    	
    	List<PlayerDT> list = this.model.getDreamTeam(k);
    	double gradoMax = this.model.getGradoMax();

    	this.txtResult.appendText("Grado DT: "+gradoMax+"\n\n");
    	
    	for(PlayerDT pdt : list)
    		this.txtResult.appendText(pdt+"\n");
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	
    	this.txtResult.clear();
    	Player best = this.model.getTopPlayer();
    	
    	if(best == null)
    		this.txtResult.setText("creare prima il grafo");
    	
    	List<Opponent> list = this.model.getOpponents(best);
    	
    	this.txtResult.appendText("Top Player: "+best.toString()+"\n\n");
    	
    	for(Opponent o : list)
    		this.txtResult.appendText(o.toString()+"\n");
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
