package guru.mwangaza.graph.implementation.visitor;

import guru.mwangaza.graph.api.BaseNode;
import guru.mwangaza.graph.api.Criterion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Copyright 2017 Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
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
 * Created by cnanjo on 3/26/17.
 */

public class EchoVisitor<S extends BaseNode<T>, T> implements Consumer<S> {

    public EchoVisitor() {
    }

    public void accept(S node) {
        System.out.println(node.getName());
    }
}