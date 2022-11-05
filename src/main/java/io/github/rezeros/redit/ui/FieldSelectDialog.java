package io.github.rezeros.redit.ui;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMember;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

public class FieldSelectDialog  extends DialogWrapper {
    private final LabeledComponent<JPanel> component;
    private final JList memberList;

    public FieldSelectDialog(String title, PsiClass ownerClass) {
        super(ownerClass.getProject());
        this.setTitle(title);
        List<PsiField> fieldList = new ArrayList<>();
        for (PsiField f : ownerClass.getFields()) {
            fieldList.add(f);
        }
        CollectionListModel list = new CollectionListModel(fieldList.toArray());
        this.memberList = new JBList(list);
        this.memberList.setCellRenderer(new DefaultPsiElementCellRenderer());
        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(this.memberList);
        decorator.disableAddAction();
        decorator.disableRemoveAction();
        decorator.disableUpDownActions();
        JPanel panel = decorator.createPanel();
        this.component = LabeledComponent.create(panel, "Select fields to generate annotation");
        this.init();
    }

    protected JComponent createCenterPanel() {
        return this.component;
    }

    public List<PsiMember> getFields() {
        return this.memberList.getSelectedValuesList();
    }



}