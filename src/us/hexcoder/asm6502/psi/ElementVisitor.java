package us.hexcoder.asm6502.psi;

import com.intellij.psi.PsiElement;

/**
 * User: 67726e
 */

public abstract class ElementVisitor {
	public abstract VisitorControl visit(PsiElement element);
}
