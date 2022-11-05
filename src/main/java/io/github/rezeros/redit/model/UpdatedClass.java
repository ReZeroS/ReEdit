package io.github.rezeros.redit.model;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;

import java.util.ArrayList;
import java.util.List;

public class UpdatedClass {

    private PsiClass psiClass;

    private List<PsiField> psiFieldList;

    public UpdatedClass(){
        psiFieldList = new ArrayList<>();
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public void setPsiClass(PsiClass psiClass) {
        this.psiClass = psiClass;
    }

    public List<PsiField> getPsiFieldList() {
        return psiFieldList;
    }

    public void setPsiFieldList(List<PsiField> psiFieldList) {
        this.psiFieldList = psiFieldList;
    }
}
