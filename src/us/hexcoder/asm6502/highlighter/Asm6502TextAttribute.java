package us.hexcoder.asm6502.highlighter;

import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;

import java.awt.*;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * User: 67726e
 */

public enum Asm6502TextAttribute {
	COMMENT("Comment", createTextAttributesKey("6502_COMMENT", SyntaxHighlighterColors.LINE_COMMENT)),
	NUMBER("Number", createTextAttributesKey("6502_NUMBER", SyntaxHighlighterColors.NUMBER)),
	MNEMONIC("Mnemonic", createTextAttributesKey("6502_MNEMONIC", SyntaxHighlighterColors.KEYWORD)),
	DIRECTIVE("Directive", createTextAttributesKey("6502_DIRECTIVE", toTextAttributes(JBColor.CYAN, null, null, null, Font.PLAIN))),
	LABEL("Label", createTextAttributesKey("6502_LABEL", toTextAttributes(JBColor.WHITE, null, null, null, Font.PLAIN))),
	STRING("String", createTextAttributesKey("6502_STRING", SyntaxHighlighterColors.STRING)),
	PARENTHESIS("Parenthesis", createTextAttributesKey("6502_PARENTHESIS", SyntaxHighlighterColors.PARENTHS)),
	COMMA("Comma", createTextAttributesKey("6502_COMMA", SyntaxHighlighterColors.COMMA)),
	INVALID("Invalid", createTextAttributesKey("6502_INVALID", toTextAttributes(JBColor.RED, null, null, null, Font.BOLD)));

	private String name;
	private TextAttributesKey textAttributesKey;

	Asm6502TextAttribute(String name, TextAttributesKey textAttributesKey) {
		this.name = name;
		this.textAttributesKey = textAttributesKey;
	}

	public String getName() {
		return this.name;
	}

	public TextAttributesKey toKey() {
		return this.textAttributesKey;
	}

	public TextAttributesKey[] toArray() {
		return new TextAttributesKey[] {this.textAttributesKey};
	}

	private static TextAttributes toTextAttributes(Color foregroundColor, Color backgroundColor, Color effectColor,
												   EffectType effectType, int font) {
		return new TextAttributes(foregroundColor, backgroundColor, effectColor, effectType, font);
	}
}
