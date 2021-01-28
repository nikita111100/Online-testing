package org.nikita111100.onlinetesting.model.persistent;

import java.util.Set;


public class AnswersToTest {
    private Set<String> checkedItems;

    public Set<String> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(Set<String> checkedItems) {
        this.checkedItems = checkedItems;
    }
}
