package pl.cyfronet.virolab.dagvis.input.yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

import pl.cyfronet.virolab.dagvis.input.TransformationException;
import pl.cyfronet.virolab.dagvis.input.Transformer;
import pl.cyfronet.virolab.dagvis.structure.IGraph;

/**
 * 
 * @author Krzysztof Nirski
 *
 */
public class YAMLTransformerTest {
	
	public static final String DIRECTORY = "testfiles/yaml/";
	
	@Test
	public void getGraph() throws FileNotFoundException, TransformationException {
		Transformer t = new YAMLTransformer();
		IGraph graph = t.getGraph(new FileInputStream(DIRECTORY + "loop.yaml"));
		//System.out.println(graph);		
	}

}
