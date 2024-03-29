package pl.cyfronet.virolab.dagvis.input.dot;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import pl.cyfronet.virolab.dagvis.input.TransformationException;
import pl.cyfronet.virolab.dagvis.input.Transformer;
import pl.cyfronet.virolab.dagvis.input.dot.wrapper.Graph;
import pl.cyfronet.virolab.dagvis.structure.IGraph;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;

/**
 * 
 * @author Krzysztof Nirski
 *
 */
public class DOTTransformer implements Transformer {
	
	private static Logger log = Logger.getLogger(DOTTransformer.class);
	
	public IGraph getGraph(InputStream source) throws TransformationException {
		Parser parser = new Parser();
		try {
			parser.parse(new InputStreamReader(source));
		} catch (ParseException e) {
			log.error("JavaCC parser exception", e);
		}
		List<com.alexmerz.graphviz.objects.Graph> graphs = parser.getGraphs();
		if (graphs.isEmpty()) {
			throw new DOTException("No graphs in file");
		} else if (graphs.size() > 1) {
			throw new DOTException("Only one graph per file allowed");
		}
		com.alexmerz.graphviz.objects.Graph g = graphs.get(0);
		if (g.getType() == com.alexmerz.graphviz.objects.Graph.UNDIRECTED) {
			throw new DOTException("The graph has to be directed");
		}
		return new Graph(g);
	}

}
