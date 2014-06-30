package us.hexcoder.asm6502.annotator;

import com.intellij.psi.tree.IElementType;
import us.hexcoder.asm6502.psi.Asm6502Types;

import java.util.*;

/**
 * User: 67726e
 */

public enum Directive {
	ADDRESS(".address", false, Asm6502Types.DIRECTIVE_NUMBER),
	BYTE(".byte", false, Asm6502Types.DIRECTIVE_NUMBER),
	INCLUDE(".include", false, Asm6502Types.DIRECTIVE_STRING),
	INCLUDE_BINARY(".includebin", false, Asm6502Types.DIRECTIVE_STRING),
	WORD(".word", false, Asm6502Types.DIRECTIVE_NUMBER);

	private String text;
	private boolean voidArgument;
	private IElementType argumentType;

	Directive(String text, boolean voidArgument, IElementType argumentType) {
		this.text = text;
		this.voidArgument = voidArgument;
		this.argumentType = argumentType;
	}

	public String getText() {
		return this.text;
	}

	public boolean hasVoidArgument() {
		return this.voidArgument;
	}

	public boolean isAllowableArgument(IElementType argumentType) {
		return this.argumentType.equals(argumentType);
	}

	private static final Map<String, Directive> TEXT_TO_DIRECTIVE_MAP;

	static {
		TEXT_TO_DIRECTIVE_MAP = new HashMap<String, Directive>();
		EnumSet<Directive> directives = EnumSet.allOf(Directive.class);

		for (Directive directive : directives) {
			TEXT_TO_DIRECTIVE_MAP.put(directive.getText(), directive);
		}
	}

	public static Directive fromText(String text) {
		return TEXT_TO_DIRECTIVE_MAP.get(text);
	}
}
