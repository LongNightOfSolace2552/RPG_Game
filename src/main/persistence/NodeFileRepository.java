package main.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.domain.world.Node;
import main.domain.world.NodeLocation;
import main.exceptions.DataLoadException;

// Loads nodes, their locations, and their enemy pools from text files.
public class NodeFileRepository {
    private static final String LOCATIONS_PATH = "data/node_locations.txt";
    private static final String NODES_PATH = "data/nodes.txt";
    private static final String FIELD_SEPARATOR = "\\|";
}
