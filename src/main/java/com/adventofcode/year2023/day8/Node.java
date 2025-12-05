package com.adventofcode.year2023.day8;

import org.apache.commons.lang3.tuple.Pair;
import com.adventofcode.common.grid.Direction;
import lombok.Data;

@Data
public class Node {

    private String id;
    private String leftId;
    private String rightId;
    private Pair<String, String> childNodeIds;
    private Pair<Node, Node> childNodes;

    public Node(String input) {
        id = input.split(" ")[0];
        childNodeIds = Pair.of(input.split(" ")[2].replace("(", "").replace(",", ""), input.split(" ")[3].replace(")", ""));
    }

    public void setChildNodes(Node leftNode, Node rightNode) {
        this.childNodes = Pair.of(leftNode, rightNode);
    }

    public Node getChildNode(Direction direction) {
        return switch (direction) {
            case L -> childNodes.getLeft();
            case R -> childNodes.getRight();
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

}
