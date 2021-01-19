package org.nikita111100.onlinetesting.model.persistent;

import java.util.Map;


public class AnswersToTest {
    private Map<String, String> checkedItems;

    public Map<String, String> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(Map<String, String> checkedItems) {
        this.checkedItems = checkedItems;
    }
}
