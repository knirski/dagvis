package pl.cyfronet.virolab.dagvis.view.jgraph.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

import pl.cyfronet.virolab.dagvis.view.jgraph.CustomGraphConstants;

/**
 * 
 * @author Krzysztof Nirski
 *
 */
public class EllipseVertexView extends VertexView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1894705171471551827L;
//	private static Logger log = Logger.getLogger(EllipseVertexView.class);
	private static transient JGraphEllipseRenderer renderer = new JGraphEllipseRenderer();
	
	protected int contourCount;
	
	public EllipseVertexView(Object graphCell, int contourCount) {
		super(graphCell);
		this.contourCount = contourCount;
	}
	
	public EllipseVertexView(Object graphCell) {
		this(graphCell, 1);
	}
	
	/**
	 * Returns the intersection of the bounding rectangle and the
	 * straight line between the source and the specified point p.
	 * The specified point is expected not to intersect the bounds.
	 */
	@Override
	public Point2D getPerimeterPoint(EdgeView edge, Point2D source, Point2D p) {
		Rectangle2D r = getBounds();
		
		double x = r.getX();
		double y = r.getY();
		double a = (r.getWidth() + 1) / 2;
		double b = (r.getHeight() + 1) / 2;
		
		// x0,y0 - center of ellipse
		double x0 = x + a;
		double y0 = y + b;

		// x1, y1 - point
		double x1 = p.getX();
		double y1 = p.getY();
		
		// calculate straight line equation through point and ellipse center
		// y = d * x + h
		double dx = x1 - x0;
		double dy = y1 - y0;
		
		if (dx == 0)
		    return new Point((int) x0, (int) (y0 + b * dy / Math.abs(dy)));

		double d = dy / dx;
		double h = y0 - d * x0;
					
		// calculate intersection
		double e = a * a * d * d + b * b;
		double f = - 2 * x0 * e;
		double g = a * a * d * d * x0 * x0 + b * b * x0 * x0 - a * a * b * b;
		
		double det = Math.sqrt(f * f - 4 * e * g);
		
		// two solutions (perimeter points)
		double xout1 = (-f + det) / (2 * e);
		double xout2 = (-f - det) / (2 * e);
		double yout1 = d * xout1 + h;
		double yout2 = d * xout2 + h;
		
		double dist1Squ = Math.pow((xout1 - x1), 2)
								 + Math.pow((yout1 - y1), 2);
		double dist2Squ = Math.pow((xout2 - x1), 2)
								 + Math.pow((yout2 - y1), 2);
		
		// correct solution
		double xout, yout;
		
		if (dist1Squ < dist2Squ) {
			xout = xout1;
			yout = yout1;
		} else {
			xout = xout2;
			yout = yout2;
		}
		
		return getAttributes().createPoint(xout, yout);
	}

	/**
	 * @return the renderer for this view
	 */
	public CellViewRenderer getRenderer() {
		renderer.setContourCount(contourCount);
		return renderer;
	}

	/**
	 * The renderer for this view
	 */
	public static class JGraphEllipseRenderer extends VertexRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2087076956339961216L;
		private int contourCount = 1;
		
		public void setContourCount(int contourCount) {
			this.contourCount = contourCount;
		}
		
		/**
		 * Return a slightly larger preferred size than for a rectangle.
		 */
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			d.width += d.width/8;
			d.height += d.height/2;
			return d;
		}
		
		/**
		 * The method that draws the cell
		 */
		public void paint(Graphics g) {
			int b = borderWidth;
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
			if (super.isOpaque()) {
				g.setColor(super.getBackground());
				if (gradientColor != null && !preview) {
					setOpaque(false);
					g2.setPaint(new GradientPaint(0, 0, getBackground(), getWidth(),
							getHeight(), gradientColor, true));
				}
				for (int i = 0; i < contourCount; i++) {
					g.fillOval(b - 1 + 3*i, b - 1 + 3*i, d.width - b - 1 - 6*i, d.height - b - 1 - 6*i);
				}
			}
			try {
				setBorder(null);
				setOpaque(false);
				selected = false;
				super.paint(g);
			} finally {
				selected = tmp;
			}
			if (bordercolor != null) {
				g.setColor(bordercolor);
				g2.setStroke(new BasicStroke(b));
				for (int i = 0; i < contourCount; i++) {
					g.drawOval(b - 1 + 3*i, b - 1 + 3*i, d.width - b - 1 - 6*i, d.height - b - 1 - 6*i);
				}
			}
			if (selected) {
				g2.setStroke(CustomGraphConstants.SELECTION_STROKE);
				g.setColor(highlightColor);
				for (int i = 0; i < contourCount; i++) {
					g.drawOval(b - 1 + 3*i, b - 1 + 3*i, d.width - b - 1 - 6*i, d.height - b - 1 - 6*i);
				}
			}
		}
	}
	
}
