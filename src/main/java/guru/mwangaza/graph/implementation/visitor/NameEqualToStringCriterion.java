/**
 * Copyright 2017 Claude Nanjo
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
package guru.mwangaza.graph.implementation.visitor;

import guru.mwangaza.graph.api.BaseNode;
import guru.mwangaza.graph.api.Criterion;

public class NameEqualToStringCriterion<S extends BaseNode<T>, T> implements Criterion<S,T> {
    private String name;

    public NameEqualToStringCriterion(String name) {
        this.name = name;
    }

    public boolean matches(S node) {
        return node != null && node.getName() != null && node.getName().equals(name);
    }
}
