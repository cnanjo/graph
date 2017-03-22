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

import java.util.*;

public abstract class BaseNodeImpl<T> implements guru.mwangaza.graph.api.BaseNode<T> {

	/**
	 * The name assigned to this node.
	 */
	private String name;
	/**
	 * The original name of the node payload when relevant.
	 */
	private String payloadName;
	/**
	 * The node's descendants.
	 */
	private List<BaseNode<T>> children;
	/**
	 * The payload assigned to this node.
	 */
	private T payload;
	/**
	 * The path delimiter. The default path delimiter is the dot-delimiter '.'.
	 */
	private String pathDelimiter = DEFAULT_PATH_DELIMITER;
	/**
	 * The node's parents.
	 */
	private List<BaseNode<T>> parents;
	/**
	 * Set of indexed ad-hoc properties that can be set on a graph node.
	 */
	private Map<String,Object> properties;
	/**
	 * Node's assigned UUID
	 */
	private String uuid;

	/**
	 * No-argument constructor.
	 */
	public BaseNodeImpl() {
		super();
		parents = new ArrayList<>();
		children = new ArrayList<>();
		properties = new LinkedHashMap<>();
		uuid = UUID.randomUUID().toString();
	}

	/**
	 * Constructor initializing the node with the constructor's name argument.
	 * @param name The name of the node.
	 */
	public BaseNodeImpl(String name) {
		this();
		this.name = name;
	}

	/**
	 * Returns the node's UUID
	 *
	 * @return The assigned or autogenerated UUID for this node.
	 */
	@Override
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the node's UUID.
	 * (Not recommended)
	 *
	 * @return
	 */
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Returns the name of the node.
	 *
	 * @return
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name assigned to this node.
	 *
	 * @param name
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the node's payload original name when the node name differs from the payload's name.
	 *
	 * @return
	 */
	@Override
	public String getPayloadName() {
		return payloadName;
	}

	/**
	 * Sets the node's payload original name.
	 *
	 * @param originalName
	 */
	@Override
	public void setPayloadName(String originalName) {
		this.payloadName = originalName;
	}

	/**
	 * Returns the node's children. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @return
	 */
	protected List<? extends BaseNode<T>> _getChildren() {
		return children;
	}

	/**
	 * Sets a node's children. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @param children
	 */
	protected void _setChildren(List<BaseNode<T>> children) {
		this.children = children;
	}

	/**
	 * Adds a child node to this node. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @param node
	 */
	protected void _addChild(BaseNode<T> node) {
		this.children.add(node);
		((BaseNodeImpl)node)._addParent(this);
	}

	/**
	 * Method adds a child node argument to this node's children if no existing child node of the same name exists.
	 *  Method is intended to be implemented in subclasses. An implementation is provided for convenience.
	 *
	 * @param node
	 */
	protected void _addChildIfNotExist(BaseNode<T> node) {
		if(!_childWithNameAlreadyExists(node)) {
			_addChild(node);
		}
	}

	/**
	 * Returns the node's first child. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @return
	 */
	protected BaseNode<T> _getFirstChild() {
		if(children.size() >= 0) {
			return children.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Returns the node's parents. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @return
	 */
	protected List<BaseNode<T>> _getParents() {
		return parents;
	}

	/**
	 * Method returns the first parent or null if the node does not have parents. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @return
	 */
	protected BaseNode<T> _getFirstParent() {
		if(parents != null && parents.size() > 0) {
			return parents.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Sets the node's parents. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @param parents The node's parents.
	 */
	protected void _setParents(List<BaseNode<T>> parents) {
		this.parents = parents;
	}

	/**
	 * Assigns node a parent. Method is intended to be implemented in subclasses.
	 * An implementation is provided for convenience.
	 *
	 * @param parent
	 */
	protected void _addParent(BaseNode<T> parent) {
		this.parents.add(parent);
	}

	/**
	 * Returns true if a child node with the same name as node argument exists. Comparison is case-insensitive.
	 *
	 * @param node
	 * @return
	 */
	protected boolean _childWithNameAlreadyExists(BaseNode<T> node) {
		boolean childWithNameAlreadyExists = false;
		for(BaseNode<T> child : children) {
			if(child.getName() != null && node.getName() != null && child.getName().equalsIgnoreCase(node.getName())) {
				childWithNameAlreadyExists = true;
				break;
			}
		}
		return childWithNameAlreadyExists;
	}

	/**
	 * Returns true if the node has children.
	 *
	 * @return
	 */
	@Override
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}

	/**
	 * Returns true if the node has no children.
	 *
	 * @return
	 */
	@Override
	public boolean hasNoChildren() {
		return !hasChildren();
	}

	/**
	 * Returns the node's payload.
	 *
	 * @return
	 */
	@Override
	public T getPayload() {
		return payload;
	}

	/**
	 * Sets the node's payload.
	 *
	 * @param payload
	 */
	@Override
	public void setPayload(T payload) {
		this.payload = payload;
	}

	/**
	 * Returns true if the node's name is equal to the name argument.
	 * Equality is case-insensitive.
	 *
	 * @param name
	 * @return
	 */
	@Override
	public boolean isNameEqualTo(String name) {
		boolean isEqual = false;
		if(name != null && this.name != null && this.name.equalsIgnoreCase(name)) {
			isEqual = true;
		}
		return isEqual;
	}
	/**
	 * Returns the path delimiter. Note that the default path delimiter is '.'.
	 *
	 * @return
	 */
	@Override
	public String getPathDelimiter() {
		return pathDelimiter;
	}
	/**
	 * Sets the path delimiter.
	 *
	 * @param pathDelimiter
	 */
	@Override
	public void setPathDelimiter(String pathDelimiter) {
		this.pathDelimiter = pathDelimiter;
	}

	/**
	 * Returns true if the node has parents.
	 *
	 * @return
	 */
	@Override
	public boolean hasParents() {
		return parents != null && parents.size() > 0;
	}

	/**
	 * Returns true if the node has no parents.
	 *
	 * @return
	 */
	@Override
	public boolean hasNoParents() {
		return !hasParents();
	}

	/**
	 * Returns true if the node has no parents.
	 *
	 * @return
	 */
	@Override
	public boolean isRoot() {
		return hasNoParents();
	}

	/**
	 * Returns false if the node has parents.
	 *
	 * @return
	 */
	@Override
	public boolean isNotRoot() {
		return !isRoot();
	}

	/**
	 * Returns true if node has no children.
	 *
	 * @return
	 */
	@Override
	public boolean isLeaf() {
		return hasNoChildren();
	}

	/**
	 * Returns true if node has children.
	 *
	 * @return
	 */
	@Override
	public boolean isNotLeaf() {
		return !isLeaf();
	}

	/**
	 * Returns true if node is not a leaf node (i.e., node has children).
	 *
	 * @return
	 */
	@Override
	public boolean isInnerNode() {
		return isNotLeaf();
	}

	/**
	 * Returns true if node does not have children.
	 *
	 * @return
	 */
	@Override
	public boolean isNotInnerNode() {
		return !isInnerNode();
	}

	/**
	 * Returns the path component for this node.
	 * If the node is a root node, the path component is the node's name.
	 * If the node is not a root node, the path component is delimiter + node name.
	 *
	 * @return
	 */
	public String buildPathComponent() {
		String pathComponent = "";
		if(isRoot()) {
			pathComponent += name;
		} else {
			pathComponent += pathDelimiter + name;
		}
		return pathComponent;
	}

	/**
	 * Returns the set of ad-hoc node properties for this node.
	 *
	 * @return Set of indexed ad-hoc properties
	 */
	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Sets set of arbitrary properties that can be set on a node.
	 *
	 * @param properties Set of indexed ad-hoc properties
	 */
	@Override
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * Adds a property to this node.
	 *
	 * @param key Ad-hoc property name
	 * @param value Ad-hoc property value
	 */
	@Override
	public void addProperty(String key, Object value) {
		if(this.properties == null) {
			this.properties = new LinkedHashMap<>();
		}
		this.properties.put(key, value);
	}
}
