package org.nouk.dataview.core.display.ui;

import org.nouk.dataview.core.display.Input;

public class TextBoxString extends Input<String> {

    public TextBoxString() {

    }

    public TextBoxString(String name, String defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }

}
