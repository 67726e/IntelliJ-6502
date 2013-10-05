package us.hexcoder.asm6502.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import us.hexcoder.asm6502.psi.Asm6502DirectiveOperation;
import us.hexcoder.asm6502.psi.Asm6502Types;

/**
 * User: 67726e
 */

public class Asm6502DirectiveAnnotator implements Annotator {
	private static final String UNRECOGNIZED_DIRECTIVE_ERROR = "Unrecognized directive";
	private static final String INVALID_ARGUMENT_VOID = "The directive requires an argument";
	private static final String INVALID_ARGUMENT_STRING = "The directive does not take a string";
	private static final String INVALID_ARGUMENT_NUMBER = "The directive does not take a number";
	private static final String INVALID_ARGUMENT_UNRECOGNIZED = "The directive argument is not recognized";

	@Override
	public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder annotationHolder) {
		if (element instanceof Asm6502DirectiveOperation) {
			PsiElement directive = ((Asm6502DirectiveOperation) element).getDirectiveCommand();
			PsiElement directiveArgument = ((Asm6502DirectiveOperation) element).getDirectiveArgument();

			handleDirective(directive, annotationHolder);
			handleDirectiveArgument(directive, directiveArgument, annotationHolder);
		}
	}

	private void handleDirective(@NotNull PsiElement element, @NotNull AnnotationHolder annotationHolder) {
		Directive directive = Directive.fromText(element.getText());

		// If the directive was not found amongst our list of directives, it is invalid
		if (directive == null) {
			annotationHolder.createErrorAnnotation(element, UNRECOGNIZED_DIRECTIVE_ERROR);
		}
	}

	private void handleDirectiveArgument(@NotNull PsiElement directiveElement, PsiElement directiveArgumentElement,
										 @NotNull AnnotationHolder annotationHolder) {
		Directive directive = Directive.fromText(directiveElement.getText());

		if (directiveArgumentElement == null) {
			if (!directive.hasVoidArgument()) {
				annotationHolder.createErrorAnnotation(directiveElement, INVALID_ARGUMENT_VOID);
			}
		} else {
			IElementType argumentType = directiveArgumentElement.getFirstChild().getNode().getElementType();

			if (!directive.isAllowableArgument(argumentType)) {
				if (Asm6502Types.DIRECTIVE_NUMBER.equals(argumentType)) {
					annotationHolder.createErrorAnnotation(directiveArgumentElement, INVALID_ARGUMENT_NUMBER);
				} else if (Asm6502Types.DIRECTIVE_STRING.equals(argumentType)) {
					annotationHolder.createErrorAnnotation(directiveArgumentElement, INVALID_ARGUMENT_STRING);
				} else {
					annotationHolder.createErrorAnnotation(directiveArgumentElement, INVALID_ARGUMENT_UNRECOGNIZED);
				}
			}
		}
	}
}
