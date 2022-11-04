package io.github.rezeros.redit;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.lang.reflect.Field;

public class NodeEdit extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        if (!(psiFile instanceof PsiJavaFile)) {
            return;
        }

        PsiJavaFile javaFile = (PsiJavaFile) psiFile;
        PsiClass[] classesInFile = javaFile.getClasses();

        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);


        WriteCommandAction.runWriteCommandAction(project, () -> {
            for (PsiClass psiClass : classesInFile) {
                PsiField[] fieldList = psiClass.getAllFields();
                int i = 1;
                for (PsiField field : fieldList) {
                    PsiAnnotation annotation = elementFactory.createAnnotationFromText(String.format("@SCFMember(orderId = %d)", i++), field);
                    psiClass.addBefore(annotation, field);
                }
            }
        });

    }
}
