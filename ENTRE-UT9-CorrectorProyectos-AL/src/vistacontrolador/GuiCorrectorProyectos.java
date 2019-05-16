
package vistacontrolador;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modelo.AlumnoNoExistenteExcepcion;
import modelo.CorrectorProyectos;

public class GuiCorrectorProyectos extends Application
{

	private MenuItem itemLeer;
	private MenuItem itemGuardar;
	private MenuItem itemSalir;

	private TextField txtAlumno;
	private Button btnVerProyecto;

	private RadioButton rbtAprobados;
	private RadioButton rbtOrdenados;
	private Button btnMostrar;

	private TextArea areaTexto;

	private Button btnClear;
	private Button btnSalir;

	private CorrectorProyectos corrector; // el modelo

	@Override
	public void start(Stage stage) {

		corrector = new CorrectorProyectos();

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("- Corrector de proyectos -");
		scene.getStylesheets().add(getClass()
		                .getResource("/css/application.css").toExternalForm());
		stage.show();
	}

	private BorderPane crearGui() {

		BorderPane panel = new BorderPane();
		MenuBar barraMenu = crearBarraMenu();
		panel.setTop(barraMenu);

		VBox panelPrincipal = crearPanelPrincipal();
		panel.setCenter(panelPrincipal);

		HBox panelBotones = crearPanelBotones();
		panel.setBottom(panelBotones);

		return panel;
	}

	private MenuBar crearBarraMenu() {

		MenuBar barraMenu = new MenuBar();

		Menu menu = new Menu("Archivo");
		

		itemLeer = new MenuItem("_Leer de fichero");
		itemLeer.setAccelerator(KeyCombination.keyCombination("CTRL+L"));
		itemLeer.setOnAction(event -> leerDeFichero());
		
		
		itemGuardar = new MenuItem("Salvar en el fichero");
		itemGuardar.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
		itemGuardar.setOnAction(event -> salvarEnFichero());
		itemGuardar.setDisable(true);
		
		itemSalir = new MenuItem("Salir");
		itemSalir.setAccelerator(KeyCombination.keyCombination("CTRL+E"));
		itemSalir.setOnAction(event -> salir()
				);

		menu.getItems().addAll(itemLeer,itemGuardar, new SeparatorMenuItem(), itemSalir);
		barraMenu.getMenus().add(menu);
		
		return barraMenu;
	}

	private VBox crearPanelPrincipal() {

		VBox panel = new VBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(10);

		Label lblEntrada = new Label("Panel de entrada");
		lblEntrada.getStyleClass().add("titulo-panel");
		lblEntrada.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		HBox caja = crearPanelEntrada();
		caja.setMaxWidth(Integer.MAX_VALUE);
		
		Label lblOpciones = new Label("Panel de opciones");
		lblOpciones.getStyleClass().add("titulo-panel");
		lblOpciones.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		HBox caja2 = crearPanelOpciones();
		caja2.setMaxWidth(Integer.MAX_VALUE);
		
		areaTexto = new TextArea();
		areaTexto.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

		panel.getChildren().addAll(lblEntrada, caja, lblOpciones, caja2, areaTexto);

		return panel;
	}

	private HBox crearPanelEntrada() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(10);
		
		Label lblPanelEntrada = new Label("Alumno");
		lblPanelEntrada.getStyleClass().add("label");
		
		txtAlumno = new TextField();
		
		txtAlumno.setPrefColumnCount(30);
		
		btnVerProyecto = new Button("Ver proyecto");
		btnVerProyecto.getStyleClass().add("button");
		btnVerProyecto.setMaxWidth(120);
		btnVerProyecto.setOnAction(event -> verProyecto());

		panel.getChildren().addAll(lblPanelEntrada, txtAlumno, btnVerProyecto);


		return panel;
	}

	private HBox crearPanelOpciones() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(50);
		panel.setAlignment(Pos.CENTER);
		
		rbtAprobados = new RadioButton("Mostrar Aporbados");
		
		
		rbtOrdenados = new RadioButton("Mostrar ordenados");
		
		
		ToggleGroup grupo = new ToggleGroup();
		rbtAprobados.setToggleGroup(grupo);		
		rbtOrdenados.setToggleGroup(grupo);
		
		btnMostrar = new Button("Mostrar");
		btnMostrar.getStyleClass().add("button");
		btnMostrar.setOnAction(event -> mostrar());
		
		panel.getChildren().addAll(rbtAprobados,rbtOrdenados,btnMostrar);
		



		return panel;
	}

	private HBox crearPanelBotones() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(10);
		panel.setAlignment(Pos.BOTTOM_RIGHT);;

		btnClear = new Button("Clear");
		btnClear.getStyleClass().add("button");
		btnClear.setMaxWidth(90);
		btnClear.setOnAction(event -> clear());
		

		btnSalir = new Button("Salir");
		btnSalir.getStyleClass().add("button");
		btnSalir.setMaxWidth(90);
		btnSalir.setOnAction(event -> salir());
		
		
		panel.getChildren().addAll(btnClear, btnSalir);
		
		return panel;
	}

	private void salvarEnFichero() {
		
        {
              try {
				corrector.guardarOrdenadosPorNota();
				areaTexto.setText("Se han guardado bien los alumnos");
			} catch (IOException e) {
				
				areaTexto.setText(e.getMessage());
			}
                
                
            
        }
		
 


	}

	private void leerDeFichero() {
		// a completar
		corrector.leerDatosProyectos();
		areaTexto.setText(corrector.toString());
		itemGuardar.setDisable(false);
		itemLeer.setDisable(true);
		



	}

	private void verProyecto() {
		// a completar
		if(itemLeer.isDisable())
		{
			try {
				String texto = txtAlumno.getText();	
				if(!texto.isEmpty()) 
				{
					String proyecto = corrector.proyectoDe(texto).toString();
					areaTexto.setText("Proyecto de " + proyecto);
				}
				areaTexto.setText("No has introducido nada");
				}catch (AlumnoNoExistenteExcepcion e) 
			{
				areaTexto.setText("Ese alumno no existe");
			}
		}else 
		{
			areaTexto.setText("Antes tienes que leer un fichero");
		}
		
		cogerFoco();
		
	
		
	}

	private void mostrar() {

		clear();
		if(rbtAprobados.isSelected()) 
		{
			areaTexto.setText("Número de aprobados: " + corrector.aprobados());
		}else 
		{
			
			areaTexto.setText(corrector.toString());
		}
		
		
		cogerFoco();



	}

	private void cogerFoco() {

		txtAlumno.requestFocus();
		txtAlumno.selectAll();

	}

	private void salir() {

		System.exit(0);
	}

	private void clear() {

		areaTexto.clear();
		cogerFoco();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
