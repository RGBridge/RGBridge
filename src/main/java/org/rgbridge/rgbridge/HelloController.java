package org.rgbridge.rgbridge;

import de.tesis.dynaware.grapheditor.Commands;
import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;
import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.model.GConnector;
import de.tesis.dynaware.grapheditor.model.GModel;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.model.GraphFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.rgbridge.rgbridge.entities.Event;
import org.rgbridge.rgbridge.entities.Game;

public class HelloController {
	private final GraphEditor graphEditor = new DefaultGraphEditor();
	private TreeItem<String> root;
	private GModel model;

	@FXML
	private TreeView<String> locationTreeView;

	@FXML
	private GraphEditorContainer graphEditorContainer;

	@FXML
	private void initialize() {
		root = new TreeItem<String>("Your games");
		root.setExpanded(true);
		locationTreeView.setRoot(root);

		model = GraphFactory.eINSTANCE.createGModel();
		graphEditor.setModel(model);
		graphEditorContainer.setGraphEditor(graphEditor);

		createEffect().setType();
	}

	private GNode createEvent() {
		GNode node = GraphFactory.eINSTANCE.createGNode();
		GConnector output = GraphFactory.eINSTANCE.createGConnector();

		output.setType("right-output");
		node.getConnectors().add(output);

		return node;
	}

	private GNode createEffect() {
		GNode node = GraphFactory.eINSTANCE.createGNode();
		GConnector input = GraphFactory.eINSTANCE.createGConnector();

		input.setType("left-input");
		node.getConnectors().add(input);

		return node;
	}

	public void addEventNote(Event event) {
		GNode firstNode = createEvent();

		firstNode.setX(150);
		firstNode.setY(150);

		Commands.addNode(model, firstNode);
	}

	/*private void addEventNote() {
		GNode firstNode = createEvent();
		GNode secondNode = createEffect();

		firstNode.setX(150);
		firstNode.setY(150);

		secondNode.setX(400);
		secondNode.setY(200);
		secondNode.setWidth(200);
		secondNode.setHeight(150);

		Commands.addNode(model, firstNode);
		Commands.addNode(model, secondNode);
	}*/

	public void clearTree() {
		root.getChildren().clear();
	}

	public void addGame(Game game) {
		root.getChildren().add(new TreeItem<String>(game.getName()));
	}
}