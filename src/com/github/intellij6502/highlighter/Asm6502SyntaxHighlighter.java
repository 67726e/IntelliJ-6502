package com.github.intellij6502.highlighter;

import com.github.intellij6502.lexer.Asm6502Lexer;
import com.github.intellij6502.psi.Asm6502Type;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Reader;

import static com.intellij.openapi.editor.colors.TextAttributesKey.*;

/**
 * User: 67726e
 */

public class Asm6502SyntaxHighlighter extends SyntaxHighlighterBase {
	public static final TextAttributesKey COMMENT = createTextAttributesKey("6502_COMMENT",
			SyntaxHighlighterColors.LINE_COMMENT);
	public static final TextAttributesKey NUMBER = createTextAttributesKey("6502_NUMBER",
			SyntaxHighlighterColors.NUMBER);
	public static final TextAttributesKey ADDRESS = createTextAttributesKey("6502_ADDRESS",
			SyntaxHighlighterColors.NUMBER);
	public static final TextAttributesKey LABEL = createTextAttributesKey("6502_LABEL",
			new TextAttributes(JBColor.WHITE, null, null, null, Font.PLAIN));
	public static final TextAttributesKey MNEMONIC = createTextAttributesKey("6502_MNEMONIC",
			SyntaxHighlighterColors.KEYWORD);
	public static final TextAttributesKey DIRECTIVE = createTextAttributesKey("6502_DIRECTIVE",
			new TextAttributes(JBColor.CYAN, null, null, null, Font.PLAIN));
	public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("6502_BAD",
			new TextAttributes(JBColor.RED, null, null, null, Font.BOLD));
	public static final TextAttributesKey DIRECTIVE_STRING = createTextAttributesKey("6502_DIRECTIVE_STRING",
			SyntaxHighlighterColors.STRING);
	public static final TextAttributesKey DIRECTIVE_NUMBER = createTextAttributesKey("6502_DIRECTIVE_NUMBER",
			SyntaxHighlighterColors.NUMBER);
	public static final TextAttributesKey PARENTHESIS = createTextAttributesKey("6502_PARENTHESIS",
			SyntaxHighlighterColors.PARENTHS);

	private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] {COMMENT};
	private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[] {NUMBER, DIRECTIVE_NUMBER};
	private static final TextAttributesKey[] ADDRESS_KEYS = new TextAttributesKey[] {ADDRESS};
	private static final TextAttributesKey[] LABEL_KEYS = new TextAttributesKey[] {LABEL};
	private static final TextAttributesKey[] MNEMONIC_KEYS = new TextAttributesKey[] {MNEMONIC};
	private static final TextAttributesKey[] DIRECTIVE_KEYS = new TextAttributesKey[] {DIRECTIVE};
	private static final TextAttributesKey[] BAD_CHARACTER_KEYS = new TextAttributesKey[] {BAD_CHARACTER};
	private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
	private static final TextAttributesKey[] DIRECTIVE_STRING_KEYS = new TextAttributesKey[] {DIRECTIVE_STRING};
	private static final TextAttributesKey[] PARENTHESIS_KEYS = new TextAttributesKey[] {PARENTHESIS};

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new FlexAdapter(new Asm6502Lexer((Reader)null));
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
		if (Asm6502Type.COMMENT.equals(iElementType)) {
			return COMMENT_KEYS;
		} else if (Asm6502Type.HEXADECIMAL_VALUE.equals(iElementType) ||
				Asm6502Type.DECIMAL_VALUE.equals(iElementType) || Asm6502Type.BINARY_VALUE.equals(iElementType) ||
				Asm6502Type.DIRECTIVE_NUMBER.equals(iElementType)) {
			return NUMBER_KEYS;
		} else if (Asm6502Type.ADDRESS.equals(iElementType)) {
			return ADDRESS_KEYS;
		} else if (Asm6502Type.LABEL.equals(iElementType) || Asm6502Type.LABEL_ARGUMENT.equals(iElementType)) {
			return LABEL_KEYS;
		} else if (Asm6502Type.MNEMONIC.equals(iElementType)) {
			return MNEMONIC_KEYS;
		} else if (Asm6502Type.DIRECTIVE.equals(iElementType)) {
			return DIRECTIVE_KEYS;
		} else if (Asm6502Type.DIRECTIVE_STRING.equals(iElementType)) {
			return DIRECTIVE_STRING_KEYS;
		} else if (TokenType.BAD_CHARACTER.equals(iElementType)) {
			return BAD_CHARACTER_KEYS;
		} else if (Asm6502Type.OPEN_PARENTHESIS.equals(iElementType) || Asm6502Type.CLOSE_PARENTHESIS.equals(iElementType)) {
			return PARENTHESIS_KEYS;
		} else {
			return EMPTY_KEYS;
		}
	}
}
