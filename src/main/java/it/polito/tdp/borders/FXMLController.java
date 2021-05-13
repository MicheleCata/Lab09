
package it.polito.tdp.borders;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML
    private ComboBox<String> cmbStato;
   
    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	txtResult.clear();
    	String anno = txtAnno.getText();
    	
    	try {
    		Integer d = Integer.parseInt(anno);
    		if(d<1816 || d>2016)  {
    			txtResult.setText("INSERISCI UN VALORE NUMERICO tra 1816 e 2016");
    			return;
    		}
    		model.createGraph(d);
    		Graph<Country, DefaultEdge> grafo =model.getGrafo();
    		
    		
    		for (Country c: grafo.vertexSet()) {
    			txtResult.appendText(c.getNome()+" #"+ grafo.degreeOf(c)+"\n");
    		}
    		
    	}catch (NumberFormatException ne) {
    		txtResult.setText("INSERISCI UN VALORE NUMERICO");
    		
    	}
    	
    	txtResult.appendText("Componenti connesse "+ model.getNConnessioni());
    	
    	List<String> stati = new LinkedList<>();
    	for (Country c: model.getCountries()) {
    		stati.add(c.getNome());
    	}
    	
    	
    	this.cmbStato.getItems().addAll(stati);

    }
    
    @FXML
    void doStatiRaggiugibili(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbStato != null : "fx:id=\"cmbStato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    }
}
