package org.nouk.dataview.core.display;


import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.nouk.dataview.core.display.ui.*;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
public abstract class Input<T> implements Serializable,GUIInterface {


    public static final RuntimeTypeAdapterFactory TypeAdapterFactory =
            RuntimeTypeAdapterFactory.of(Input.class, "")
                    .registerSubtype(TextBoxString.class, "TextBoxString")
                    .registerSubtype(TextBoxLong.class, "TextBoxLong")
                    .registerSubtype(SelectDateTime.class, "SelectDateTime")
                    .registerSubtype(CheckBoxLong.class, "CheckBoxLong")
                    .registerSubtype(CheckBoxString.class, "CheckBoxString")
                    .registerSubtype(SelectLong.class, "SelectLong")
                    .registerSubtype(SelectString.class, "SelectString")
            ;

    protected String name;
    protected String displayName;
    protected String text;
    protected T value;
    protected T defaultValue;
    protected boolean hidden;
    protected boolean modified; //是否能修改
    protected String argument;

    private static final String INPUT = "(?=\\$%s\\{).*(?<=\\})";

    private static final String KEY = "${%s}";

    private static final Map<Pattern,Class<?>> INPUT_PATTERNS = new HashMap<>();

    public String getKey(){
        return String.format(KEY, name);
    }

    public static LinkedHashMap<String, Input> extractSimpleQueryForm(StringBuilder script) {
        LinkedHashMap<String, Input> forms = new LinkedHashMap<>();
        if (script == null) {
            return forms;
        }
        if (INPUT_PATTERNS.size()==0) {
            for (Object typeName : TypeAdapterFactory.getLabelToSubtype().keySet()) {
                final String format = String.format(INPUT, typeName);
                final Pattern compile = Pattern.compile(format);
                final Object o = TypeAdapterFactory.getLabelToSubtype().get(typeName);
                INPUT_PATTERNS.put(compile, (Class<?>) o);
            }
            INPUT_PATTERNS.put(Pattern.compile(String.format(INPUT,TypeAdapterFactory.getTypeFieldName())), TypeAdapterFactory.getBaseType());
        }
        for (Pattern pattern : INPUT_PATTERNS.keySet()) {
            Matcher match = pattern.matcher(script);
            while (match.find()) {
                int first = match.start();
                if (first > 0 && script.charAt(first - 1) == '$') {
                    continue;
                }
                Input form = getInputForm(match,INPUT_PATTERNS.get(pattern));
                forms.put(form.getName(), form);
            }
        }

        for (Pattern pattern : INPUT_PATTERNS.keySet()) {
            Matcher match = pattern.matcher(script);
            final List<Input> collect = forms.values().stream().collect(Collectors.toList());
            int i = 0;
            while (match.find()) {
                final Input input = collect.get(i);
                script.replace(match.start(0) ,match.end(0),input.getKey());
                i ++;
            }
        }
        return forms;
    }



    private static Input getInputForm(Matcher match,Class clazz) {
        String key = match.group(0);
        final String substring = key.substring(key.indexOf("{"), key.length());
        final Gson gson = new Gson();
        final Object o = gson.fromJson(substring, clazz);
        if (o instanceof Input) {
            final Input input = (Input) o;
            if (StringUtils.isBlank(input.getDisplayName())) {
                input.setDisplayName(input.getName());
            }
            return input;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Input<?> input = (Input<?>) o;

        if (hidden != input.hidden) {
            return false;
        }
        if (modified != input.modified) {
            return false;
        }
        if (name != null ? !name.equals(input.name) : input.name != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(input.displayName) : input.displayName != null) {
            return false;
        }
        if (defaultValue instanceof Object[]) {
            if (defaultValue != null ?
                    !Arrays.equals((Object[]) defaultValue, (Object[]) input.defaultValue)
                    : input.defaultValue != null) {
                return false;
            }
        } else if (defaultValue != null ?
                !defaultValue.equals(input.defaultValue) : input.defaultValue != null) {
            return false;
        }
        return argument != null ? argument.equals(input.argument) : input.argument == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (hidden ? 1 : 0);
        result = 31 * result + (argument != null ? argument.hashCode() : 0);
        return result;
    }
}
