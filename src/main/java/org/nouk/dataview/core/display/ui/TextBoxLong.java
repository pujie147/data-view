package org.nouk.dataview.core.display.ui;

import org.nouk.dataview.core.display.Input;


public class TextBoxLong extends Input<Long> {


    public TextBoxLong() {

    }

    public TextBoxLong(String name, Long defaultValue) {
        super.name = name;
        super.displayName = name;
        super.defaultValue = defaultValue;
    }

}
