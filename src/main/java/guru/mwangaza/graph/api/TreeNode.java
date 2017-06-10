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

import guru.mwangaza.graph.implementation.TreeNodeImpl;
import guru.mwangaza.graph.implementation.visitor.NodeCountVisitor;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

public interface TreeNode<T> extends guru.mwangaza.graph.api.BaseNode<T>, Serializable {
    /**
     * Returns a node's children.
     *
     * @return Node's children
     */
    List<TreeNode<T>> getChildren();
    /**
     * Sets this node's children.
     *
     * @param children Node's children
     */
    void setChildren(List<TreeNode<T>> children);
    /**
     * Adds a child to this node.
     *
     * @param child Node's child
     */
    void addChild(TreeNode<T> child);
    /**
     * Adds child only if no child of that name already exists among the children.
     *
     * @param child Node's child
     */
    void addChildIfNotExist(TreeNode<T> child);
    /**
     * Returns the first child or null if node has no children.
     *
     * @return The node's first child
     */
    TreeNode<T> getFirstChild();
    /**
     * Returns true if a child node with the same name as node argument exists. Comparison is case-insensitive.
     *
     * @param node The node whose existence we are asserting
     * @return true if a node of that name was encountered amoung the children
     */
    boolean childWithNameAlreadyExists(TreeNode node);
    /**
     * Returns the node's sole parent.
     *
     * @return The node's parent
     */
    TreeNode<T> getParent();
    /**
     * Sets the node's sole parent.
     *
     * @param parent The node's parent
     */
    void setParent(TreeNode<T> parent);

    /**
     * Returns true if the node has a parent and that parent is the root of the tree.
     *
     * @return True if the node is a root node (i.e., it has no parents after the tree is constructed)
     */
    boolean parentIsRoot();

    /**
     * Returns true if the parent is not a root node (i.e., parent node has parents).
     *
     * @return True if the node is not a root node.
     */
    boolean parentIsNotRoot();

    /**
     * Convenience method indicating that node is direct child of the root node.
     * Only relevant in tree graphs.
     *
     * @return True if node is child of root node.
     */
    boolean isL1();

    /**
     * Convenience method that returns true if node is not the direct child of the root node.
     * Only relevant is tree graphs.
     *
     * @return True if node is not child of root node.
     */
    boolean isNotL1();

    /**
     * Convenience method that returns true if the node is a direct child
     * of root and has children.
     * Only relevant in tree graphs.
     *
     * @return true if node is child of root and has children.
     */
    boolean isInnerL1();

    /**
     * Convenience method that returns true if node is either not an
     * L1 node or the node is a leaf node.
     * Only relevant in tree graphs.
     *
     * @return True if node is not child of root or is child of root but is a leaf node.
     */
    boolean isNotInnerL1();

    /**
     * Convenience method that returns true if node is neither root nor L1
     * Only relevant in tree graphs.
     *
     * @return
     */
    boolean isLN();

    /**
     * Method returns true if node is not an LN node.
     *
     * @return True if node is less than two levels deep in the tree.
     */
    boolean isNotLN();

    /**
     * Method returns true if node is an LN node and node
     * has children.
     *
     * @return True if node is at least two levels deep and has children.
     */
    boolean isInnerLN();

    /**
     * Returns true if node is not an LN node or node has no children.
     *
     * @return True if node is either less than two levels deep or is at least two levels deep but is leaf node.
     */
    boolean isNotInnerLN();

    /**
     * Method returns the path from the root node.
     * Relevant for tree graphs.
     *
     * @return The path of this node starting from the root node of the tree.
     */
    String getPathFromRoot();

    /**
     * Method returns true if the path argument is the prefix of the node's path starting from the root node.
     *
     * @param pathPrefix The path prefix
     * @return True if the path  argument is the prefix for this node's path.
     */
    boolean isPathPrefix(String pathPrefix);

    /**
     * Method appends node at position specified by this path.
     *
     * @param path Builds the path specified from this node and adds payload to the node at the end of the path.
     * @param payload The payload of the node at the end of the path.
     */
    public void appendPathToCurrentNode(String path, T payload);

    public void buildPathFromCurrentNode(String path, T payload);

    /**
     * Method applies visitor logic depth-first before processing children.
     *
     * @param command A command to execute.
     */
    void executeCommandDepthFirstPre(Consumer<TreeNode<T>> command);

    /**
     * Method applies visitor logic depth-first after processing children.
     *
     * @param command A command to execute.
     */
    void executeCommandDepthFirstPost(Consumer<TreeNode<T>> command);

    /**
     * Method applies visitor logic breadth-first.
     *
     * @param command A command to execute.
     */
    void executeCommandBreadthFirst(Consumer<TreeNode<T>> command);

    /**
     * Method performs a shallow clone of this node.
     *
     * @return The shallow clone of this tree node.
     */
    TreeNodeImpl<T> shallowClone();

    /**
     * Convenience method accumulating nodes with name.
     *
     * @param name The node with the name we wish to search of.
     * @return List of nodes bearing the name argument.
     */
    List<TreeNode<T>> findNodesWithName(String name);

    /**
     * Returns the node count for the subtree starting from this node including this node.
     *
     * @return
     */
    public int getSubtreeNodeCount();

    /**
     * Returns all paths starting from this node as the root.
     *
     * @return List of all sub-branch paths.
     *
     */
    public List<String> getPathsFromNode();
}
