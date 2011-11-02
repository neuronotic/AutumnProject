package traffic.jgrapht;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.junit.Rule;
import org.junit.Test;

import traffic.JUnitRuleMockery;
import traffic.Junction;
import traffic.Segment;

public class TestJGraphT {

	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public void kShortestPathsWorksWithSimpleNetwork() throws Exception {
		final Junction junction0 = context.mock(Junction.class, "junction0");
		final Junction junction1 = context.mock(Junction.class, "junction1");
		final Junction junction2 = context.mock(Junction.class, "junction2");
		final Junction junction3 = context.mock(Junction.class, "junction3");

		final Segment segment0 = context.mock(Segment.class, "segment0");
		final Segment segment1 = context.mock(Segment.class, "segment1");
		final Segment segment2 = context.mock(Segment.class, "segment2");
		final Segment segment3 = context.mock(Segment.class, "segment3");

		final SegmentEdge edge0 = new SegmentEdge(segment0);
		final SegmentEdge edge1 = new SegmentEdge(segment1);
		final SegmentEdge edge2 = new SegmentEdge(segment2);
		final SegmentEdge edge3 = new SegmentEdge(segment3);

		final DefaultDirectedWeightedGraph<Junction, SegmentEdge> graph = new DefaultDirectedWeightedGraph<Junction, SegmentEdge>(SegmentEdge.class);

		graph.addVertex(junction0);
		graph.addVertex(junction1);
		graph.addVertex(junction2);
		graph.addVertex(junction3);

		graph.addEdge(junction0, junction1, edge0);
		graph.setEdgeWeight(edge0, 6);

		graph.addEdge(junction1, junction3, edge1);
		graph.setEdgeWeight(edge0, 3);

		graph.addEdge(junction0, junction2, edge2);
		graph.setEdgeWeight(edge0, 2);

		graph.addEdge(junction2, junction1, edge3);
		graph.setEdgeWeight(edge0, 3);

		final List<GraphPath<Junction, SegmentEdge>> paths = new KShortestPaths<Junction, SegmentEdge>(graph, junction0, 2).getPaths(junction3);
		assertThat(paths.get(0).getEdgeList(), contains(edge2, edge3, edge1));
		assertThat(paths.get(1).getEdgeList(), contains(edge0, edge1));

		assertThat(edge0.segment(), equalTo(segment0));
	}

}
