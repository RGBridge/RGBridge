package org.rgbridge.rgbridge.graphskins;

import de.tesis.dynaware.grapheditor.GConnectorSkin;
import de.tesis.dynaware.grapheditor.GNodeSkin;
import de.tesis.dynaware.grapheditor.model.GNode;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class ZnidiNodeSkin extends GNodeSkin {
	private final Rectangle border = new Rectangle();
	private VBox contentRoot = new VBox();

	private static final String STYLE_CLASS_BORDER = "titled-node-border";

	private static final int BORDER_WIDTH = 1;

	private static final double MIN_WIDTH = 81;
	private static final double MIN_HEIGHT = 81;

	public ZnidiNodeSkin(final GNode node) {
		super(node);

		border.getStyleClass().setAll(STYLE_CLASS_BORDER);
		border.widthProperty().bind(getRoot().widthProperty());
		border.heightProperty().bind(getRoot().heightProperty());

		getRoot().getChildren().add(border);
		getRoot().getChildren().add(new Label(TitleUtils.getTitleToSet()));
		getRoot().setMinSize(MIN_WIDTH, MIN_HEIGHT);

		contentRoot.setLayoutX(BORDER_WIDTH);
		contentRoot.setLayoutY(BORDER_WIDTH);
	}

	public static GNodeSkin createSkin(GNode node) {
		return new ZnidiNodeSkin(node);
	}

	@Override
	public void setConnectorSkins(final List<GConnectorSkin> connectorSkins) {
	}

	@Override
	public void layoutConnectors() {
	}

	@Override
	public Point2D getConnectorPosition(final GConnectorSkin connectorSkin) {
		return null;
	}

	@Override
	protected void selectionChanged(final boolean isSelected) {
	}
}

