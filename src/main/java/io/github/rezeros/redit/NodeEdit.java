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
        // 获取整个项目
        Project project = e.getProject();
        // 获取当前编辑器
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        // 获取这个编辑器的文档 比如现在正在聚焦的这个文件
        Document document = editor.getDocument();

        // 文件的内容都是字符串，直接用会很麻烦，还得自己搞parser
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        if (!(psiFile instanceof PsiJavaFile)) {
            return;
        }
        // idea plugin 提供了一些这种默认的parser，直接转就可以
        PsiJavaFile javaFile = (PsiJavaFile) psiFile;

        // 转成 java 后就能拿到 class
        PsiClass[] classesInFile = javaFile.getClasses();

        // 开个 element 工厂准备干活 element 就是节点，比如类节点，字段节点，注解节点等等
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);

        // 这里没细查，估计是和android那种怕阻塞 UI 主线程之类的
        WriteCommandAction.runWriteCommandAction(project, () -> {
            for (PsiClass psiClass : classesInFile) {
                PsiField[] fieldList = psiClass.getAllFields();
                int i = 1;
                for (PsiField field : fieldList) {
                    PsiAnnotation annotation = elementFactory.createAnnotationFromText(String.format("@SCFMember(orderId = %d)", i++), field);
                    // 这里 add 之后就会进行绘制，add 即刻触发写操作，不需要 write 之类的接口
                    psiClass.addBefore(annotation, field);
                }
            }
        });

    }
}
