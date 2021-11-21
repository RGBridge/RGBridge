package org.rgbridge.rgbridge.graphskins;

import de.tesis.dynaware.grapheditor.GConnectorSkin;
import de.tesis.dynaware.grapheditor.GConnectorStyle;
import de.tesis.dynaware.grapheditor.model.GConnector;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class ZnidiConnectorSkin extends GConnectorSkin {
	private static final double RADIUS = 8;
	private Pane root = new Pane();
	private Circle circle = new Circle(RADIUS);

	public ZnidiConnectorSkin(final GConnector connector) {
		super(connector);
		root.getChildren().add(circle);
		circle.getStyleClass().setAll("foo-connector");
	}

	public static ZnidiConnectorSkin factoryMethod(final GConnector connector) {
		return new ZnidiConnectorSkin(connector);
	}

	@Override
	protected void selectionChanged(final boolean isSelected) {
	}

	@Override
	public Node getRoot() {
		return root;
	}

	@Override
	public double getWidth() {
		return 2 * RADIUS;
	}

	@Override
	public double getHeight() {
		return 2 * RADIUS;
	}

	@Override
	public void applyStyle(final GConnectorStyle style) {
		PseudoClass allowed = PseudoClass.getPseudoClass("allowed");
		PseudoClass forbidden = PseudoClass.getPseudoClass("forbidden");

		circle.pseudoClassStateChanged(forbidden, false);
		circle.pseudoClassStateChanged(allowed, true);
	}
}

