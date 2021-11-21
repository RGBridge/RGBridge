package org.rgbridge.rgbridge;

import de.tesis.dynaware.grapheditor.Commands;
import de.tesis.dynaware.grapheditor.GraphEditor;
import de.tesis.dynaware.grapheditor.core.DefaultGraphEditor;
import de.tesis.dynaware.grapheditor.core.view.GraphEditorContainer;
import de.tesis.dynaware.grapheditor.model.GConnector;
import de.tesis.dynaware.grapheditor.model.GModel;
import de.tesis.dynaware.grapheditor.model.GNode;
import de.tesis.dynaware.grapheditor.model.GraphFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import org.rgbridge.commons.Game;
import org.rgbridge.rgbridge.entities.Device;
import org.rgbridge.rgbridge.entities.Effect;
import org.rgbridge.rgbridge.graphskins.TitleUtils;
import org.rgbridge.rgbridge.graphskins.ZnidiConnectorSkin;
import org.rgbridge.rgbridge.graphskins.ZnidiNodeSkin;
import org.rgbridge.rgbridge.utils.DeviceUtils;
import org.rgbridge.rgbridge.utils.GameUtils;

import java.util.HashMap;
import java.util.Map;

import static org.rgbridge.rgbridge.HelloApplication.helloController;

public class HelloController {
	private final GraphEditor graphEditor = new DefaultGraphEditor();
	private GModel model;

	private TreeItem<String> gamesRootItem;
	private TreeItem<String> devicesRootItem;

	@FXML
	private TreeView<String> gameTreeView;

	@FXML
	private TreeView<String> deviceTreeView;

	@FXML
	private GraphEditorContainer graphEditorContainer;

	@FXML
	private void initialize() {
		gamesRootItem = new TreeItem<String>("Your games");
		gamesRootItem.setExpanded(true);
		gameTreeView.setRoot(gamesRootItem);

		devicesRootItem = new TreeItem<String>("Your devices");
		devicesRootItem.setExpanded(true);
		deviceTreeView.setRoot(devicesRootItem);

		deviceTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getClickCount() == 2) {
					TreeItem<String> item = deviceTreeView.getSelectionModel().getSelectedItem();

					if(item.getChildren().isEmpty()) {
						TitleUtils.setTitleToSet(item.getValue());

						GNode effectNode = createEffectNode();
						effectNode.setX(150);
						effectNode.setY(150);
						Commands.addNode(model, effectNode);
					}
				}
			}
		});

		model = GraphFactory.eINSTANCE.createGModel();
		graphEditor.setModel(model);
		graphEditor.setConnectorSkinFactory(ZnidiConnectorSkin::factoryMethod);
		graphEditor.setNodeSkinFactory(ZnidiNodeSkin::createSkin);
		graphEditorContainer.setGraphEditor(graphEditor);
	}

	private GNode createEffectNode() {
		GNode node = GraphFactory.eINSTANCE.createGNode();
		GConnector input = GraphFactory.eINSTANCE.createGConnector();

		input.setType("left-input");
		node.getConnectors().add(input);

		return node;
	}

	public void refreshTree() {
		gamesRootItem.getChildren().clear();
		devicesRootItem.getChildren().clear();

		HashMap<String, Game> games = GameUtils.getGames();
		HashMap<String, Device> devices = DeviceUtils.getDevices();

		for(String gameId : games.keySet()) {
			helloController.addGame(games.get(gameId));
		}

		for(String deviceId : devices.keySet()) {
			helloController.addDevice(devices.get(deviceId));
		}
	}

	public void addGame(Game game) {
		gamesRootItem.getChildren().add(new TreeItem<String>(game.getName()));
	}

	public void addDevice(Device device) {
		TreeItem<String> deviceItemRoot = new TreeItem<String>(device.getName());
		Map<String, Effect> deviceEffects = device.getEffects();

		for(String effectId : deviceEffects.keySet()) {
			Effect effect = deviceEffects.get(effectId);
			deviceItemRoot.getChildren().add(new TreeItem<String>(effect.getName()));
		}

		devicesRootItem.getChildren().add(deviceItemRoot);
	}
}