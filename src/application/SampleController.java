package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import bean.Country;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class SampleController {
	
	private Model m = new Model();
	
	public void setModel(Model m){
		this.m=m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMese;

    @FXML
    private Button btnElenco;

    @FXML
    private Button btnDistanza;

    @FXML
    private TextArea txtResult;

    @FXML
    void doDistanza(ActionEvent event) {   //max Distanza tra due nodi
    	txtResult.clear();
    	String mese = comboMese.getValue();
    	if(mese==null){
    		txtResult.appendText("Seleziona un mese!\n");
    		return;
    	}
    	int numMese = m.getNumeroMese(mese);
    	m.buildGraph(numMese);
    	
    }

    @FXML
    void doElencoArtisti(ActionEvent event) {            //ok
    	txtResult.clear();
    	String mese = comboMese.getValue();
    	
    	if(mese==null){
    		txtResult.appendText("Seleziona un mese!\n");
    		return;
    	}
    	
    	int numMese = m.getNumeroMese(mese);
    	List<String> ascoltati= m.getPiuAsco(numMese);
    	if(ascoltati.size()==0){
    		txtResult.appendText("Non è stato ascoltato nessun artista in questo mese!\n");
    		return;
    	}
    	for(String asc : ascoltati){
    		txtResult.appendText(asc+" \n");
    	}
    	
    	
    	
    }

    @FXML
    void initialize() {
        assert comboMese != null : "fx:id=\"comboMese\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnElenco != null : "fx:id=\"btnElenco\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnDistanza != null : "fx:id=\"btnDistanza\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

        comboMese.getItems().addAll(m.getMesi());
    }
}
