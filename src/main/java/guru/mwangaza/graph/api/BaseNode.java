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


import java.util.Map;

/**
 * Base graph node interface shared by all graph nodes.
 *
 * @param <T>
 */
public interface BaseNode<T> {
    /**
     * Path delimiter
     */
    String DEFAULT_PATH_DELIMITER = ".";

    /**
     * Returns the node's UUID
     *
     * @return
     */
    String getUuid();

    /**
     * Sets a node's UUID.
     *
     * @param uuid The UUID of the node.
     */
    void setUuid(String uuid);
    /**
     * Returns the name of the node.
     *
     * @return
     */
    String getName();
    /**
     * Sets the name assigned to this node.
     *
     * @param name
     */
    void setName(String name);
    /**
     * Returns the node's payload original name when the node name differs from the payload's name.
     *
     * @return
     */
    String getPayloadName();
    /**
     * Sets the node's payload original name.
     *
     * @param originalName
     */
    void setPayloadName(String originalName);
    /**
     * Returns true if the node has children.
     *
     * @return
     */
    boolean hasChildren();
    /**
     * Returns true if the node has no children.
     *
     * @return
     */
    boolean hasNoChildren();
    /**
     * Returns the node's payload.
     *
     * @return
     */
    T getPayload();
    /**
     * Sets the node's payload.
     *
     * @param payload
     */
    void setPayload(T payload);
    /**
     * Returns true if the node's name is equal to the name argument.
     * Equality is case-insensitive.
     *
     * @param name
     * @return
     */
    boolean isNameEqualTo(String name);
    /**
     * Returns the path delimiter. Note that the default path delimiter is '.'.
     *
     * @return
     */
    String getPathDelimiter();
    /**
     * Sets the path delimiter.
     *
     * @param pathDelimiter
     */
    void setPathDelimiter(String pathDelimiter);
    /**
     * Returns true if the node has one or more parents.
     *
     * @return
     */
    boolean hasParents();
    /**
     * Returns true if the node has no parents.
     *
     * @return
     */
    boolean hasNoParents();
    /**
     * Returns true if the node has no parents.
     *
     * @return
     */
    boolean isRoot();
    /**
     * Returns false if the node has parents.
     *
     * @return
     */
    boolean isNotRoot();
    /**
     * Returns true if node has no children.
     *
     * @return
     */
    boolean isLeaf();
    /**
     * Returns true if node has children.
     *
     * @return
     */
    boolean isNotLeaf();
    /**
     * Returns true if node is not a leaf node (i.e., node has children).
     *
     * @return
     */
    boolean isInnerNode();
    /**
     * Returns true if node does not have children.
     *
     * @return
     */
    boolean isNotInnerNode();

    /**
     * Returns the path component for this node.
     * If the node is a root node, the path component is the node's name.
     * If the node is not a root node, the path component is delimiter + node name.
     *
     * @return
     */
    String buildPathComponent();

    /**
     * Sets set of arbitrary properties that can be set on a node.
     *
     * @param properties Set of indexed ad-hoc properties
     */
    void setProperties(Map<String,Object> properties);

    /**
     * Returns the set of ad-hoc node properties for this node.
     *
     * @return Set of indexed ad-hoc properties
     */
    Map<String, Object> getProperties();

    /**
     * Adds a property to this node.
     *
     * @param key Ad-hoc property name
     * @param value Ad-hoc property value
     */
    void addProperty(String key, Object value);

}
