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
package guru.mwangaza.graph.implementation;

import guru.mwangaza.graph.api.BaseNode;
import guru.mwangaza.graph.api.GraphNode;

import java.util.List;



public class GraphNodeImpl<T> extends BaseNodeImpl<T> implements GraphNode<T> {

    /**
     * No-argument constructor.
     */
    public GraphNodeImpl() {
    }

    /**
     * Constructor initializing the node with the constructor's name argument.
     *
     * @param name The name of the node.
     */
    public GraphNodeImpl(String name) {
        super(name);
    }

    /**
     * Returns the node's children.
     *
     * @return
     */
    @Override
    public List<GraphNode<T>> getChildren() {
        return (List<GraphNode<T>>) super._getChildren();
    }

    /**
     * Sets the node's children.
     *
     * @param children
     */
    @Override
    public void setChildren(List<GraphNode<T>> children) {
        super._setChildren((List<BaseNode<T>>)(List<?>)children);
    }

    /**
     * Adds a child to this node.
     *
     * @param child
     */
    @Override
    public void addChild(GraphNode<T> child) {
        super._addChild(child);
    }

    /**
     * Adds a child only if no child node of the same name exists.
     *
     * @param child
     */
    @Override
    public void addChildIfNotExist(GraphNode<T> child) {
        super._addChildIfNotExist(child);
    }

    /**
     * Returns the node's first child.
     *
     * @return
     */
    @Override
    public GraphNode<T> getFirstChild() {
        return (GraphNode<T>)super._getFirstChild();
    }

    /**
     * Returns the node's parents.
     *
     * @return
     */
    @Override
    public List<GraphNode<T>> getParents() {
        return (List<GraphNode<T>>)(List<?>)super._getParents();
    }

    /**
     * Sets the parents for this node.
     *
     * @param parents
     */
    @Override
    public void setParents(List<GraphNode<T>> parents) {
        super._setParents((List<BaseNode<T>>)(List<?>)parents);
    }

    /**
     * Sets the node's parents.
     *
     * @return
     */
    @Override
    public GraphNode<T> getFirstParent() {
        return (GraphNode<T>)super._getFirstParent();
    }

    /**
     * Adds a parent to this node.
     *
     * @param parent
     */
    @Override
    public void addParent(GraphNode<T> parent) {
        super._getParents().add(parent);
    }

    /**
     * Returns true if a child node with the same name as node argument exists. Comparison is case-insensitive.
     *
     * @param node
     * @return
     */
    @Override
    public boolean childWithNameAlreadyExists(GraphNode node) {
        return super._childWithNameAlreadyExists(node);
    }
}
