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
 * Created by cnanjo on 3/20/17.
 */
package guru.mwangaza.graph.api;

import guru.mwangaza.graph.implementation.GraphNodeImpl;

import java.util.List;



public interface GraphNode<T> extends guru.mwangaza.graph.api.BaseNode<T> {
    /**
     * Returns the node's children.
     *
     * @return
     */
    List<GraphNode<T>> getChildren();
    /**
     * Sets the node's children.
     *
     * @param children
     */
    void setChildren(List<GraphNode<T>> children);
    /**
     * Adds a child to this node.
     *
     * @param child
     */
    void addChild(GraphNode<T> child);
    /**
     * Adds a child only if no child node of the same name exists.
     *
     * @param child
     */
    void addChildIfNotExist(GraphNode<T> child);
    /**
     * Returns the node's first child.
     *
     * @return
     */
    GraphNode<T> getFirstChild();
    /**
     * Returns the node's parents.
     *
     * @return
     */
    List<GraphNode<T>> getParents();

    /**
     * Sets the parents for this node.
     *
     * @param parents
     */
    public void setParents(List<GraphNode<T>> parents);
    /**
     * Sets the node's parents.
     *
     * @return
     */
    GraphNode<T> getFirstParent();
    /**
     * Adds a parent to this node.
     *
     * @param parent
     */
    void addParent(GraphNode<T> parent);
    /**
     * Returns true if a child node with the same name as node argument exists. Comparison is case-insensitive.
     *
     * @param node
     * @return
     */
    public boolean childWithNameAlreadyExists(GraphNode node);
}
