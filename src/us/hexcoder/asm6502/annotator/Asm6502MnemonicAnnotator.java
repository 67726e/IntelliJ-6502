package us.hexcoder.asm6502.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import us.hexcoder.asm6502.psi.Asm6502MnemonicOperation;

import java.util.HashMap;
import java.util.Map;

import static us.hexcoder.asm6502.psi.Asm6502Types.*;


/**
 * User: 67726e
 */

public class Asm6502MnemonicAnnotator implements Annotator {
	private static final Map<IElementType, Operand> ELEMENT_TYPE_TO_OPERAND = new HashMap<IElementType, Operand>();
	private static final Map<Operand, String> OPERAND_TO_ERROR_MESSAGE = new HashMap<Operand, String>();

	private static final String UNRECOGNIZED_MNEMONIC_ERROR = "Unrecognized mnemonic";
	private static final String INVALID_OPERAND_IMPLIED = "The mnemonic does not accept implied operands";
	private static final String INVALID_OPERAND_LABEL = "The mnemonic does not accept label operands";

	@Override
	public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder annotationHolder) {
		if (element instanceof Asm6502MnemonicOperation) {
			PsiElement mnemonicElement = ((Asm6502MnemonicOperation) element).getMnemonicCommand();
			PsiElement operandElement = ((Asm6502MnemonicOperation) element).getOperand();
			Mnemonic mnemonic = Mnemonic.fromString(mnemonicElement.getText());

			handleMnemonic(element, mnemonic, annotationHolder);

			// We cannot validate operands on an unrecognized mnemonic
			if (mnemonic != null) {
				handleOperand(mnemonicElement, operandElement, mnemonic, annotationHolder);
			}
		}
	}

	private void handleMnemonic(@NotNull PsiElement mnemonicElement, Mnemonic mnemonic,
								@NotNull AnnotationHolder annotationHolder) {
		// If we don't have a mnemonic, it is not a valid mnemonic
		if (mnemonic == null) {
			annotationHolder.createErrorAnnotation(mnemonicElement, UNRECOGNIZED_MNEMONIC_ERROR);
		}
	}

	private void handleOperand(@NotNull PsiElement mnemonicElement, PsiElement operandElement, Mnemonic mnemonic,
							   @NotNull AnnotationHolder annotationHolder) {
		if (operandElement == null) {
			if (!mnemonic.isValidOperand(Operand.IMPLIED)) {
				annotationHolder.createErrorAnnotation(mnemonicElement, INVALID_OPERAND_IMPLIED);
			}
		} else {
			IElementType operandType = operandElement.getFirstChild().getNode().getElementType();

			if (LABEL_OPERAND.equals(operandType)) {
				// The label is really just a placeholder for absolute or relative operands
				if (!mnemonic.isValidOperand(Operand.ABSOLUTE) && !mnemonic.isValidOperand(Operand.RELATIVE)) {
					annotationHolder.createErrorAnnotation(operandElement, INVALID_OPERAND_LABEL);
				}
			} else {
				Operand operand = ELEMENT_TYPE_TO_OPERAND.get(operandType);

				if (operand != null) {
					if (!mnemonic.isValidOperand(operand)) {
						annotationHolder.createErrorAnnotation(operandElement, OPERAND_TO_ERROR_MESSAGE.get(operand));
					}
				} else {
					throw new IllegalStateException("WTF JUST HAPPENED");
				}
			}
		}
	}

	static {
		ELEMENT_TYPE_TO_OPERAND.put(ACCUMULATOR_OPERAND, Operand.ACCUMULATOR);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_NUMBER, Operand.IMMEDIATE);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_ZERO_PAGE, Operand.ZERO_PAGE);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_ZERO_PAGE_X, Operand.ZERO_PAGE_X);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_ZERO_PAGE_Y, Operand.ZERO_PAGE_Y);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_ABSOLUTE, Operand.ABSOLUTE);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_ABSOLUTE_X, Operand.ABSOLUTE_X);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_ABSOLUTE_Y, Operand.ABSOLUTE_Y);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_INDIRECT, Operand.INDIRECT);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_INDIRECT_X, Operand.INDIRECT_X);
		ELEMENT_TYPE_TO_OPERAND.put(OPERAND_INDIRECT_Y, Operand.INDIRECT_Y);

		OPERAND_TO_ERROR_MESSAGE.put(Operand.ACCUMULATOR, "The mnemonic does not accept the accumulator");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.IMMEDIATE, "The mnemonic does not accept immediate operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.ZERO_PAGE, "The mnemonic does not accept zero page operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.ZERO_PAGE_X, "The mnemonic does not accept zero page (X) operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.ZERO_PAGE_Y, "The mnemonic does not accept zero page (Y) operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.ABSOLUTE, "The mnemonic does not accept absolute operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.ABSOLUTE_X, "The mnemonic does not accept absolute (X) operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.ABSOLUTE_Y, "The mnemonic does not accept absolute (Y) operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.INDIRECT, "The mnemonic does not accept indirect operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.INDIRECT_X, "The mnemonic does not accept indirect (X) operands");
		OPERAND_TO_ERROR_MESSAGE.put(Operand.INDIRECT_Y, "The mnemonic does not accept indirect (Y) operands");
	}
}
