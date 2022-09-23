package org.nouk.dataview.core.display;

import org.junit.jupiter.api.Test;
import org.nouk.dataview.core.display.ui.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InputTest {
    @Test
    public void testFormExtraction() {
        String script;
        Map<String, Input> forms;
        Input form;

        // TextBoxString form
        script = "$TextBoxString{'defaultValue':'1111','name':'loginName','displayName':'loginName'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals("1111", form.defaultValue);
        assertTrue(form instanceof TextBoxString);

        // TextBoxLong form
        script = "$TextBoxLong{'defaultValue':123132,'name':'loginName','displayName':'loginName'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals(123132L, form.defaultValue);
        assertTrue(form instanceof TextBoxLong);

        // SelectDateTime form
        script = "$SelectDateTime{'defaultValue':'2022-05-01 23:59:58','name':'loginName','displayName':'loginName'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertTrue(form.defaultValue instanceof Date);
        assertTrue(form instanceof SelectDateTime);

        // CheckBoxLong form
        script = "$CheckBoxLong{'defaultValue':[111,1111],'name':'loginName','displayName':'loginName'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals(111L, ((List)form.defaultValue).get(0));
        assertEquals(1111L, ((List)form.defaultValue).get(1));
        assertTrue(form instanceof CheckBoxLong);

        // CheckBoxString form
        script = "$CheckBoxString{'defaultValue':[\'111\',\'1111\'],'name':'loginName','displayName':'loginName'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals("111", ((List)form.defaultValue).get(0));
        assertEquals("1111", ((List)form.defaultValue).get(1));
        assertTrue(form instanceof CheckBoxString);

        // SelectString form
        script = "$SelectString{'defaultValue':'111','name':'loginName','displayName':'loginName'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals("111", form.defaultValue);
        assertTrue(form instanceof SelectString);

        // SelectLong form
        script = "$SelectLong{'defaultValue':111,'name':'loginName','displayName':'loginName'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals(111L, form.defaultValue);
        assertTrue(form instanceof SelectLong);
    }


    @Test
    public void testFormExtractionSelectListSql() {
        String script;
        Map<String, Input> forms;
        Input form;
        script = "select * from Member where 1=1 and $SelectLong{'defaultValue':111,'name':'loginName','selectListSql':'select id as userId from member'}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        System.out.println(forms);
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals(111L, form.defaultValue);
        assertTrue(form instanceof SelectLong);
        assertEquals(((SelectLong)form).getSelectListSql(),"select id as userId from member");
    }

    @Test
    public void testFormExtractionSelectList() {
        String script;
        Map<String, Input> forms;
        Input form;
        script = "select * from Member where 1=1 and $SelectLong{'defaultValue':111,'name':'loginName','selectList':{'name':'111'}}";
        forms = Input.extractSimpleQueryForm(new StringBuilder(script));
        System.out.println(forms);
        assertEquals(1, forms.size());
        form = forms.get("loginName");
        assertEquals("loginName", form.name);
        assertEquals("loginName", form.displayName);
        assertEquals(111L, form.defaultValue);
        assertTrue(form instanceof SelectLong);
        assertEquals(((SelectLong)form).getSelectList().get("name"), "111");
    }
}
