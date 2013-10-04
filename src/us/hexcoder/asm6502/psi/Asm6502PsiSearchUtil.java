package us.hexcoder.asm6502.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import java.util.List;

/**
 * User: 67726e
 */

public class Asm6502PsiSearchUtil extends AbstractPsiSearchUtil {
	public static List<PsiElement> findLabels(final Project project, final String labelText) {
		return find(project, new ElementVisitor() {
			@Override
			public VisitorControl visit(PsiElement element) {
				if (Asm6502Types.LABEL_DECLARATION.equals(element.getNode().getElementType())) {
					if (labelText.equals(element.getText())) {
						return VisitorControl.ACCEPT;
					} else {
						return VisitorControl.SKIP;
					}
				}

				return VisitorControl.DEEPER;
			}
		});
	}
}
