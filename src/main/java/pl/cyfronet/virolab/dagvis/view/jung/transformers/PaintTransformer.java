package pl.cyfronet.virolab.dagvis.view.jung.transformers;

import java.awt.Paint;

import org.apache.commons.collections15.Transformer;
import org.apache.log4j.Logger;

import pl.cyfronet.virolab.dagvis.structure.INode;

/**
 * 
 * @author Krzysztof Nirski
 *
 */
public class PaintTransformer implements Transformer<INode, Paint> {

	private static Logger log = Logger.getLogger(PaintTransformer.class);

	public Paint transform(INode input) {
		Paint paint = input.getColor();
		log.trace("Paint initialized: " + paint);
		return paint;
	}

}
