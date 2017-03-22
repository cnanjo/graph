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
import guru.mwangaza.graph.api.TreeNode;
import guru.mwangaza.graph.exception.InvalidStateException;
import guru.mwangaza.graph.implementation.visitor.NameEqualToStringCriterion;
import guru.mwangaza.graph.implementation.visitor.NodeCountVisitor;
import guru.mwangaza.graph.implementation.visitor.TreeGraphPathAggregator;
import guru.mwangaza.graph.implementation.visitor.SearchVisitor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Lightweight implementation of a tree graph. Each node in the graph can have a single parent and can
 * carry a payload.
 * <br>
 * Some terminology:
 * <ul>
 *     <li>Root node: Top level node in the tree. It has no parent.</li>
 *     <li>L1 node: Child of root node. Can either be a leaf node or an inner node.</li>
 *     <li>Inner node: A node that is neither the root node nor a leaf node.</li>
 *     <li>Leaf node: A node that has no children nodes.</li>
 *     <li>LN node: A node that is neither root, nor L1. That is, a node at least two levels down in the tree. May be an inner node or a leaf node.</li>
 * </ul>
 * @param <T>
 */
public class TreeNodeImpl<T> extends BaseNodeImpl<T> implements TreeNode<T> {

    /**
     * No-argument constructor.
     */
    public TreeNodeImpl() {
    }

    /**
     * Constructor initializing the node with the constructor's name argument.
     *
     * @param name The name of the node.
     */
    public TreeNodeImpl(String name) {
        super(name);
    }

    /**
     * Returns a node's children.
     *
     * @return
     */
    @Override
    public List<TreeNode<T>> getChildren() {
        return (List<TreeNode<T>>) super._getChildren();
    }

    /**
     * Sets this node's children.
     *
     * @param children
     */
    @Override
    public void setChildren(List<TreeNode<T>> children) {
        super._setChildren((List<BaseNode<T>>)(List<?>)children);
    }

    /**
     * Adds a child to this node.
     *
     * @param child
     */
    @Override
    public void addChild(TreeNode<T> child) {
        super._addChild(child);
    }

    /**
     * Adds child only if no child of that name already exists among the children.
     *
     * @param child
     */
    @Override
    public void addChildIfNotExist(TreeNode<T> child) {
        super._addChildIfNotExist(child);
    }

    /**
     * Returns the first child or null if node has no children.
     *
     * @return
     */
    @Override
    public TreeNode<T> getFirstChild() {
        return (TreeNode<T>)super._getFirstChild();
    }


    /**
     * Returns true if a child node with the same name as node argument exists. Comparison is case-insensitive.
     *
     * @param node
     * @return
     */
    @Override
    public boolean childWithNameAlreadyExists(TreeNode node) {
        return super._childWithNameAlreadyExists(node);
    }

    /**
     * Returns the node's sole parent.
     *
     * @return
     */
    @Override
    public TreeNode<T> getParent() {
        if(super._getParents().size() <= 1) {
            return (TreeNode<T>) super._getFirstParent();
        } else {
            throw new InvalidStateException("TreeNode can only have zero to one parent");
        }
    }

    /**
     * Sets the node's sole parent.
     *
     * @param parent
     */
    @Override
    public void setParent(TreeNode<T> parent) {
        if(super._getParents() != null) {
            if(super._getParents().size() == 0) {
                super._addParent(parent);
            } else if(super._getParents().size() == 1) {
                super._getParents().set(0, parent);
            } else {
                throw new InvalidStateException("TreeNode can only have zero to one parent");
            }
        } else {
            super._setParents(new ArrayList<BaseNode<T>>());
            super._addParent(parent);
        }
    }

    /**
     * Returns true if the node has a parent and that parent is the root of the tree.
     *
     * @return
     */
    @Override
    public boolean parentIsRoot() {
        boolean parentIsRoot = false;
        if(getParent() != null && getParent().isRoot()) {
            parentIsRoot = true;
        }
        return parentIsRoot;
    }

    /**
     * Returns true if the parent is not a root node (i.e., parent node has parents).
     *
     * @return
     */
    @Override
    public boolean parentIsNotRoot() {
        return !parentIsRoot();
    }

	/**
	 * Convenience method indicating that node is direct child of the root node.
	 * Only relevant in tree graphs.
	 *
	 * @return
	 */
	@Override
	public boolean isL1() {
		return parentIsRoot();
	}

	/**
	 * Convenience method that returns true if node is not the direct child of the root node.
	 * Only relevant is tree graphs.
	 *
	 * @return
	 */
	@Override
	public boolean isNotL1() {
		return !isL1();
	}

    /**
     * Convenience method that returns true if the node is a direct child
	 * of root and has children.
	 * Only relevant in tree graphs.
	 *
	 * @return
     */
	@Override
	public boolean isInnerL1() {
		return isL1() && isNotLeaf();
	}

    /**
	 * Convenience method that returns true if node is either not an
	 * L1 node or the node is a leaf node.
	 * Only relevant in tree graphs.
	 *
	 * @return
	 */
	@Override
	public boolean isNotInnerL1() {
		return isNotL1() || isLeaf();
	}

	/**
     * Convenience method that returns true if node is neither root nor L1
	 * Only relevant in tree graphs.
	 *
	 * @return
     */
	@Override
	public boolean isLN() {
		return isNotRoot() && parentIsNotRoot();
	}

    /**
     * Method returns true if node is not an LN node.
	 *
	 * @return
     */
	@Override
	public boolean isNotLN() {
		return !isLN();
	}

    /**
     * Method returns true if node is an LN node and node
	 * has children.
	 *
	 * @return
     */
	@Override
	public boolean isInnerLN() {
		return isLN() && hasChildren();
	}

    /**
     * Returns true if node is not an LN node or node has no children.
	 *
	 * @return
     */
	@Override
	public boolean isNotInnerLN() {
		return !isLN() || !hasChildren();
	}

    /**
     * Method returns the path from the root node to the current node.
	 * Relevant for tree graphs.
	 *
	 * @return
     */
	@Override
	public String getPathFromRoot() {
		String path;
		if(getParent() == null) {
			return getName();
		} else {
			path = getParent().getPathFromRoot();
			return path + getPathDelimiter() + getName();
		}
	}

    /**
     * Method returns true if the path argument is the prefix of the node's path starting from the root node.
	 *
	 * @param pathPrefix
     * @return
     */
	@Override
	public boolean isPathPrefix(String pathPrefix) {
		String path = getPathFromRoot();
		return path.startsWith(pathPrefix);
	}

	/**
	 * Method appends nodes at position specified by this path. If the path argument coincides
	 * with an existing path, process moves along the first matching though not necessarily longest
	 * subpath. It is recommended that this method not be used unless node names are unique in the
	 * graph.
	 * <br>
	 * Consider the following tree:
	 * <br>
	 * <code><pre>
	 * A-B-C-D
	 *     |
	 *      -D-E
	 *</pre></code>
	 *<br>
	 * and a path:
	 * <br>
	 * A-B-C-D-E-F-G
	 * <br>
	 * Then the first path will be followed and nodes E, F, and G will be created with
	 * the payload attached to node G.
	 * <br>
	 * The new graph will look like:
	 * <code><pre>
	 * A-B-C-D-*E-*F-*G
	 *     |
	 *      -D-E
	 *</pre></code>
	 *
	 *
	 * @param path
	 * @param payload
	 */
	@Override
	public void buildPathFromCurrentNode(String path, T payload) {
		if(StringUtils.isBlank(path)) {
			return;
		} else {
			String[] pathComponents = path.split("\\" + getPathDelimiter());
			String remainder = path.substring(path.indexOf(getPathDelimiter()) + 1);
			int index = 0;
			if (isNameEqualTo(pathComponents[index])) {
				setPayloadName(pathComponents[index]);
				if(pathComponents.length == 1) {
					setPayload(payload);
				}
				index++;
			}
			boolean found = false;
			if(getChildren() != null && getChildren().size() > 0) {
				for (TreeNode<T> child : getChildren()) {
					if(child.isNameEqualTo(pathComponents[index])) {
						child.buildPathFromCurrentNode(remainder, payload);
						found = true;
						break;
					}
				}
			}
			if(!found) {
				appendPathToCurrentNode(remainder, payload);
			}
		}
	}

	/**
	 * Method appends nodes specified by path to the current node.
	 *
	 * @param path
	 * @param payload
	 */
	@Override
	public void appendPathToCurrentNode(String path, T payload) {
		if(StringUtils.isBlank(path)) {
			return;
		} else {
			String[] pathComponents = path.split("\\" + getPathDelimiter());
			String currentComponent = pathComponents[0];

			TreeNode<T> child = new TreeNodeImpl<T>(StringUtils.capitalize(pathComponents[0]));
			child.setPayloadName(pathComponents[0]);
			if(pathComponents.length ==1) {
				child.setPayload(payload);
			} else {
				String remainder = path.substring(path.indexOf(getPathDelimiter()) + 1);
				child.appendPathToCurrentNode(remainder, payload);
			}
			this.addChild(child);
		}
	}

    /**
     * Method applies visitor logic depth-first before processing children.
	 *
	 * @param command
	 */
	@Override
	public void executeCommandDepthFirstPre(Consumer<TreeNode<T>> command) {
		command.accept(this);
		for(TreeNode<T> child : getChildren()) {
			child.executeCommandDepthFirstPre(command);
		}
	}

    /**
     * Method applies visitor logic depth-first after processing children.
	 *
	 * @param command
	 */
	@Override
	public void executeCommandDepthFirstPost(Consumer<TreeNode<T>> command) {
		for(TreeNode<T> child : getChildren()) {
			child.executeCommandDepthFirstPost(command);
		}
		command.accept(this);
	}

    /**
     * Method applies visitor logic breadth-first.
	 *
	 * @param command
	 */
	@Override
	public void executeCommandBreadthFirst(Consumer<TreeNode<T>> command) {
		if(isRoot()) {
			command.accept(this);
		}
		for(TreeNode<T> child : getChildren()) {
			command.accept(child);
		}
		for(TreeNode<T> child : getChildren()) {
			child.executeCommandBreadthFirst(command);
		}
	}

	/**
	 * Convenience method accumulating nodes with name.
	 *
	 * @param name The node with the name we wish to search of.
	 * @return List of nodes bearing the name argument.
	 */
	@Override
	public List<TreeNode<T>> findNodesWithName(String name) {
		SearchVisitor<TreeNode<T>, T> visitor = new SearchVisitor<TreeNode<T>,T>(new NameEqualToStringCriterion<TreeNode<T>,T>(name));
		executeCommandDepthFirstPre(visitor);
		return visitor.getFoundItems();
	}

    /**
     * Method performs a shallow clone of this node.
	 *
	 * @return
     */
	@Override
	public TreeNodeImpl<T> shallowClone() {
		TreeNodeImpl<T> node = new TreeNodeImpl<T>();
		node.setParent(this.getParent());
		node.setName(this.getName());
		node.setChildren(this.getChildren());
		node.setPayloadName(this.getPayloadName());
		node.setPayload(this.getPayload());
		return node;
	}

	/**
	 * Returns the node count for the subtree starting from this node including this node.
	 *
	 * @return
	 */
	@Override
	public int getSubtreeNodeCount() {
		NodeCountVisitor<TreeNode<T>,T> visitor = new NodeCountVisitor<TreeNode<T>, T>();
		executeCommandDepthFirstPre(visitor);
		return visitor.getCount();
	}

	/**
	 * Returns all paths starting from this node as the root.
	 *
	 * @return List of all sub-branch paths.
	 *
	 */
	@Override
	public List<String> getPathsFromNode() {
		TreeGraphPathAggregator<TreeNode<T>,T> aggregator = new TreeGraphPathAggregator<TreeNode<T>,T>();
		executeCommandDepthFirstPre(aggregator);
		return aggregator.getPaths();
	}

    /**
     * To string returns the name assigned to this node.
     *
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }
}
