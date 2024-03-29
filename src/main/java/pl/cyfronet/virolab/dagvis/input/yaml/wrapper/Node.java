package pl.cyfronet.virolab.dagvis.input.yaml.wrapper;

import java.awt.Color;

import pl.cyfronet.virolab.dagvis.structure.INode;
import pl.cyfronet.virolab.dagvis.util.CustomColour;
import pl.cyfronet.virolab.dagvis.util.Shape;

/**
 * 
 * @author Krzysztof Nirski
 *
 */
public class Node implements INode {
	
	private String name;
	private String label;
	private Shape shape;
	private int contourCount;
	private boolean active;
	
	public Node(String name) {
		this.name = name;
	}

	public Color getColor() {
		return CustomColour.BLACK;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public Shape getShape() {
		return shape;
	}

	public boolean isBold() {
		return false;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return getName().equals(obj);
	}
	
	@Override
	public String toString() {
		return getLabel() == null || getLabel().isEmpty() ? getName() : getLabel();
	}

	public void setBold(boolean bold) {
		// TODO Auto-generated method stub
		
	}

	public void setColor(Color color) {
		// TODO Auto-generated method stub
		
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public int getContourCount() {
		return contourCount;
	}

	public void setContourCount(int count) {
		contourCount = count;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean h) {
		active = h;
	}

}
