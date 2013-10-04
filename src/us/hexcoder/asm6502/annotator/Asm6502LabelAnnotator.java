package us.hexcoder.asm6502.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import us.hexcoder.asm6502.psi.Asm6502LabelDeclaration;
import us.hexcoder.asm6502.psi.Asm6502PsiSearchUtil;
import us.hexcoder.asm6502.psi.Asm6502Types;

import java.util.List;

/**
 * User: 67726e
 */

public class Asm6502LabelAnnotator implements Annotator {
	private static final String NO_LABEL_ERROR = "No label exists for this operand";
	private static final String DUPLICATE_LABEL_ERROR = "Duplicate label declaration";

	@Override
	public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
		IElementType elementType = psiElement.getNode().getElementType();

		if (Asm6502Types.LABEL_OPERAND.equals(elementType)) {
			handleLabelOperand(psiElement, annotationHolder);
		} else if (Asm6502Types.LABEL_DECLARATION.equals(elementType)) {
			handleLabelDeclaration(psiElement, annotationHolder);
		}
	}

	private void handleLabelDeclaration(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
		List<PsiElement> labels = Asm6502PsiSearchUtil.findLabels(psiElement.getProject(), psiElement.getText());

		// If there is more than one matching label, that is not this label, we've got duplicate declarations
		if (labels.size() > 1) {
			for (PsiElement label : labels) {
				annotationHolder.createErrorAnnotation(label, DUPLICATE_LABEL_ERROR);
			}
		}
	}

	private void handleLabelOperand(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
		String labelText = psiElement.getText() + ":";
		List<PsiElement> labels = Asm6502PsiSearchUtil.findLabels(psiElement.getProject(), labelText);

		// If we didn't find a label for this element, we've got an error
		if (labels.isEmpty()) {
			annotationHolder.createErrorAnnotation(psiElement, NO_LABEL_ERROR);
		}
	}
}
