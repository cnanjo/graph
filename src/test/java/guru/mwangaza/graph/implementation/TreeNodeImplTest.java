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

import guru.mwangaza.graph.api.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TreeNodeImplTest {
    @Test
    public void getChildren() throws Exception {
        TreeNode<String> root = buildGraph();
        assertEquals(3, root.getChildren().size());
    }

    @Test
    public void setChildren() throws Exception {
        TreeNode<String> root = buildGraph();
        root.setChildren(new ArrayList<TreeNode<String>>());
        assertEquals(0, root.getChildren().size());
    }

    @Test
    public void addChild() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("New_Node");
        assertEquals(0, results.size());
        root.addChild(new TreeNodeImpl<String>("New_Node"));
        results = root.findNodesWithName("New_Node");
        assertEquals(1, results.size());
    }

    @Test
    public void addChildIfNotExist() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_1");
        assertEquals(1, results.size());
        root.addChildIfNotExist(new TreeNodeImpl<String>("L1_1"));
        results = root.findNodesWithName("L1_1");
        assertEquals(1, results.size());
    }

    @Test
    public void getFirstChild() throws Exception {
        TreeNode<String> root = buildGraph();
        assertEquals("L1_1", root.getFirstChild().getName());
    }

    @Test
    public void childWithNameAlreadyExists() throws Exception {
        TreeNode<String> root = buildGraph();
        assertTrue(root.childWithNameAlreadyExists(new TreeNodeImpl<String>("L1_2")));
        assertFalse(root.childWithNameAlreadyExists(new TreeNodeImpl<String>("SomeUnknownName")));
    }

    @Test
    public void getParent() throws Exception {
        TreeNode<String> root = buildGraph();
        assertNull(root.getParent());
        List<TreeNode<String>> results = root.findNodesWithName("L1_1");
        assertNotNull(results.get(0).getParent());
        assertEquals(root, results.get(0).getParent());
    }

    @Test
    public void setParent() throws Exception {
        TreeNode<String> root = buildGraph();
        root.setParent(new TreeNodeImpl<String>("superparent"));
        assertNotNull(root.getParent());
    }

    @Test
    public void parentIsRoot() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_1");
        assertTrue(results.get(0).parentIsRoot());
        assertFalse(root.parentIsRoot());
    }

    @Test
    public void parentIsNotRoot() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_2_1");
        assertTrue(results.get(0).parentIsNotRoot());
    }

    @Test
    public void isL1() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_1");
        assertTrue(results.get(0).isL1());
        results = root.findNodesWithName("L1_2_1");
        assertFalse(results.get(0).isL1());
        assertFalse(root.isL1());
    }

    @Test
    public void isNotL1() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_1");
        assertFalse(results.get(0).isNotL1());
        results = root.findNodesWithName("L1_2_1");
        assertTrue(results.get(0).isNotL1());
        assertTrue(root.isNotL1());
    }

    @Test
    public void isInnerL1() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_2");
        assertTrue(results.get(0).isInnerL1());
        results = root.findNodesWithName("L1_1");
        assertTrue(results.get(0).isNotInnerL1());
        assertTrue(root.isNotInnerL1());
    }

    @Test
    public void testLNCheck() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_2_1");
        assertTrue(results.get(0).isLN());
        results = root.findNodesWithName("L1_1");
        assertTrue(results.get(0).isNotLN());
        assertTrue(root.isNotLN());
    }

    @Test
    public void innerLNCheck() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_2_1");
        assertTrue(results.get(0).isInnerLN());
        results = root.findNodesWithName("L1_2_1_1");
        assertTrue(results.get(0).isLN());
        assertTrue(results.get(0).isNotInnerLN());
    }

    @Test
    public void getPathFromRoot() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_2_1_1");
        assertEquals("root.L1_2.L1_2_1.L1_2_1_1", results.get(0).getPathFromRoot());
        results = root.findNodesWithName("root");
        assertEquals("root", results.get(0).getPathFromRoot());
        results = root.findNodesWithName("L1_2");
        assertEquals("root.L1_2", results.get(0).getPathFromRoot());
    }

    @Test
    public void isPathPrefix() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_2_1_1");
        assertTrue(results.get(0).isPathPrefix("root.L1_2.L1_2_1"));
        assertTrue(results.get(0).isPathPrefix("root.L1_2"));
        assertTrue(results.get(0).isPathPrefix("root"));
        assertTrue(results.get(0).isPathPrefix(""));
        assertFalse(results.get(0).isPathPrefix("some.fake.path"));
        assertFalse(results.get(0).isPathPrefix("root.L1_2.L1_1_1"));
    }

    @Test
    public void appendPathToCurrentNode() throws Exception {
        TreeNode<String> root = new TreeNodeImpl<String>();
        root.appendPathToCurrentNode("child.grandchild", "grandchild");
        assertEquals(3, root.getSubtreeNodeCount());
        List<String> paths = root.getPathsFromNode();
        assertEquals(1, paths.size());
        assertEquals("null.Child.Grandchild", paths.get(0));

        root = new TreeNodeImpl<String>("root");
        root.appendPathToCurrentNode("child", "child");
        assertEquals(2, root.getSubtreeNodeCount());
        paths = root.getPathsFromNode();
        assertEquals(1, paths.size());
        assertEquals("root.Child", paths.get(0));

        root = new TreeNodeImpl<String>("root");
        root.addChild(new TreeNodeImpl<String>("child"));
        root.appendPathToCurrentNode("root.child.grandchild", "grandchild");
        assertEquals(5, root.getSubtreeNodeCount());
        paths = root.getPathsFromNode();
        assertEquals(2, paths.size());
        assertEquals("root.child", paths.get(0));
        assertEquals("root.Root.Child.Grandchild", paths.get(1));

        root = new TreeNodeImpl<String>("");
        root.appendPathToCurrentNode("", "grandchild");
        assertEquals(1, root.getSubtreeNodeCount());
        paths = root.getPathsFromNode();
        assertEquals(1, paths.size());
        assertEquals("", paths.get(0));
    }

    @Test
    public void buildPathFromCurrentNode() {
        TreeNode<String> root = new TreeNodeImpl<String>("root");
        root.buildPathFromCurrentNode("root.child.grandchild", "grandchild");
        assertEquals(3, root.getSubtreeNodeCount());
        List<String> paths = root.getPathsFromNode();
        assertEquals(1, paths.size());
        assertEquals("root.Child.Grandchild", paths.get(0));

        root = new TreeNodeImpl<String>("root");
        root.addChild(new TreeNodeImpl<String>("child"));
        root.buildPathFromCurrentNode("root.child.grandchild", "grandchild");
        assertEquals(3, root.getSubtreeNodeCount());
        paths = root.getPathsFromNode();
        assertEquals(1, paths.size());
        assertEquals("root.child.Grandchild", paths.get(0));

        root = new TreeNodeImpl<String>("root");
        root.addChild(new TreeNodeImpl<String>("child"));
        root.buildPathFromCurrentNode("child.grandchild", "grandchild");
        assertEquals(3, root.getSubtreeNodeCount());
        paths = root.getPathsFromNode();
        assertEquals(1, paths.size());
        assertEquals("root.child.Grandchild", paths.get(0));

        root = new TreeNodeImpl<String>("");
        root.buildPathFromCurrentNode("", "grandchild");
        assertEquals(1, root.getSubtreeNodeCount());
        paths = root.getPathsFromNode();
        assertEquals(1, paths.size());
        assertEquals("", paths.get(0));
    }

    @Test
    public void executeCommandDepthFirstPre() throws Exception {
        TreeNode<String> root = buildGraph();
    }

    @Test
    public void executeCommandDepthFirstPost() throws Exception {
        TreeNode<String> root = buildGraph();
    }

    @Test
    public void executeCommandBreadthFirst() throws Exception {
        TreeNode<String> root = buildGraph();
    }

    @Test
    public void shallowClone() throws Exception {
        TreeNode<String> root = buildGraph();
    }

    @Test
    public void hasChildren() throws Exception {
        TreeNode<String> root = buildGraph();
    }

    @Test
    public void hasNoChildren() throws Exception {
        TreeNode<String> root = buildGraph();
    }

    @Test
    public void isNameEqualTo() throws Exception {
        TreeNode<String> root = buildGraph();
    }

    @Test
    public void testHasParentsCheck() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_1");
        TreeNode<String> L1_1 = results.get(0);
        assertEquals(1, results.size());
        assertEquals("L1_1", L1_1.getName());
        assertTrue(L1_1.hasParents());
        assertFalse(L1_1.hasNoParents());
        assertFalse(root.hasParents());
        assertTrue(root.hasNoParents());
    }

    @Test
    public void testRootNodeCheck() throws Exception {
        TreeNode<String> root = buildGraph();
        List<TreeNode<String>> results = root.findNodesWithName("L1_1");
        TreeNode<String> L1_1 = results.get(0);
        assertTrue(root.isRoot());
        assertFalse(root.isNotRoot());
        assertFalse(L1_1.isRoot());
        assertTrue(L1_1.isNotRoot());
    }

    @Test
    public void isLeaf() throws Exception {

    }

    @Test
    public void isNotLeaf() throws Exception {

    }

    @Test
    public void isInnerNode() throws Exception {

    }

    @Test
    public void isNotInnerNode() throws Exception {

    }

    protected TreeNode<String> buildGraph() {
        TreeNode<String> root = new TreeNodeImpl<String>("root");
        TreeNode<String> L1_1 = new TreeNodeImpl<String>("L1_1");
        TreeNode<String> L1_2 = new TreeNodeImpl<String>("L1_2");
        TreeNode<String> L1_3 = new TreeNodeImpl<String>("L1_3");
        TreeNode<String> L1_2_1 = new TreeNodeImpl<String>("L1_2_1");
        TreeNode<String> L1_2_1_1 = new TreeNodeImpl<String>("L1_2_1_1");
        root.addChild(L1_1);
        root.addChild(L1_2);
        root.addChild(L1_3);
        L1_2.addChild(L1_2_1);
        L1_2_1.addChild(L1_2_1_1);
        return root;
    }

}