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
package guru.mwangaza.graph.implementation.visitor;

import guru.mwangaza.graph.api.BaseNode;
import guru.mwangaza.graph.api.Criterion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;



public class SearchVisitor<S extends BaseNode<T>, T> implements Consumer<S> {
    private Criterion<S,T> criterion;
    private List<S> foundItems;

    public SearchVisitor() {
        this.foundItems = new ArrayList<>();
    }

    public SearchVisitor(Criterion<S,T> criterion) {
        this();
        this.criterion = criterion;
    }

    public void accept(S node) {
        if(criterion.matches(node)) {
            this.foundItems.add(node);
        }
    }

    public Criterion<S,T> getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion<S,T> criterion) {
        this.criterion = criterion;
    }

    public List<S> getFoundItems() {
        return foundItems;
    }

    public void setFoundItems(List<S> foundItems) {
        this.foundItems = foundItems;
    }
}
