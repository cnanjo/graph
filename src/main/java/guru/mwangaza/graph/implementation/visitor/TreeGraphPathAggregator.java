/**
 * Copyright 2017 Claude Nanjo.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by cnanjo on 3/21/17.
 */
package guru.mwangaza.graph.implementation.visitor;

import guru.mwangaza.graph.api.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Visitor retrieves the full path of each leaf node in a tree graph
 * from the node where the retrieval was invoked.
 * <br>
 * Use for depth-first searches where the visitor logic is applied prior to recursion in children nodes.
 *
 * @param <S> The type of node visited
 * @param <T> The type of the payload for the node
 */
public class TreeGraphPathAggregator<S extends TreeNode<T>, T> implements Consumer<S> {

    /**
     * List of path visited ordered  in the natural order of the graph.
     */
    private List<String> paths;
    /**
     * The node's current path
     */
    private StringBuilder currentPath;
    /**
     * A node-uuid-to-path-index
     */
    private Map<String, String> nodeToPathIndex;
    /**
     * Creates a new TreeGraphPathAggregator.
     */
    public TreeGraphPathAggregator() {
        paths = new ArrayList<>();
        nodeToPathIndex = new HashMap<>();
        currentPath = new StringBuilder();
    }

    /**
     * Perform path generation logic on the node only if the node is a leaf node in the graph.
     *
     * @param node The input tree node
     */
    @Override
    public void accept(S node) {
        if(node.isLeaf()) {
            currentPath.append(node.buildPathComponent());
            nodeToPathIndex.put(node.getUuid(), currentPath.toString());
            paths.add(currentPath.toString());
            currentPath = null;
        } else {
            if(currentPath == null) {//Only happens when seeing an already visited node after hitting a leaf node.
                currentPath = new StringBuilder();
                currentPath.append(nodeToPathIndex.get(node.getParent().getUuid()));
                currentPath.append(node.buildPathComponent());
                nodeToPathIndex.put(node.getUuid(), currentPath.toString());
            } else {
                currentPath.append(node.buildPathComponent());
                nodeToPathIndex.put(node.getUuid(), currentPath.toString());
            }
        }
    }

    /**
     * Returns the set of paths in the graph.
     *
     * @return
     */
    public List<String> getPaths() {
        return paths;
    }

}
