package org.nouk.dataview.core.display.ui;

import lombok.Data;
import org.nouk.dataview.core.display.Input;

import java.util.Map;

@Data
public abstract class SelectList<T> extends Input<T> {
    protected Map<String,String> selectList;
}
