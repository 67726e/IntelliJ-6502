package us.hexcoder.asm6502.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import us.hexcoder.asm6502.lexer.Asm6502LexerAdapter;
import us.hexcoder.asm6502.psi.Asm6502Types;

import static com.intellij.openapi.editor.colors.TextAttributesKey.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: 67726e
 */

public class Asm6502SyntaxHighlighter extends SyntaxHighlighterBase {
	public static final TextAttributesKey COMMENT = createTextAttributesKey("6502_COMMENT", SyntaxHighlighterColors.LINE_COMMENT);
	public static final TextAttributesKey NUMBER = createTextAttributesKey("6502_NUMBER", SyntaxHighlighterColors.NUMBER);
	public static final TextAttributesKey MNEMONIC = createTextAttributesKey("6502_MNEMONIC", SyntaxHighlighterColors.KEYWORD);
	public static final TextAttributesKey DIRECTIVE = createTextAttributesKey("6502_DIRECTIVE",
			toTextAttributes(JBColor.CYAN, null, null, null, Font.PLAIN));
	public static final TextAttributesKey LABEL = createTextAttributesKey("6502_LABEL",
			toTextAttributes(JBColor.WHITE, null, null, null, Font.PLAIN));
	public static final TextAttributesKey INVALID = createTextAttributesKey("6502_INVALID",
			toTextAttributes(JBColor.RED, null, null, null, Font.BOLD));
	public static final TextAttributesKey STRING = createTextAttributesKey("6502_STRING", SyntaxHighlighterColors.STRING);

	private static final Map<IElementType, TextAttributesKey[]> TOKEN_HIGHLIGHTS =
			new HashMap<IElementType, TextAttributesKey[]>();

	private static TextAttributes toTextAttributes(Color foregroundColor, Color backgroundColor, Color effectColor,
												   EffectType effectType, int font) {
		return new TextAttributes(foregroundColor, backgroundColor, effectColor, effectType, font);
	}

	private static TextAttributesKey[] toArray(TextAttributesKey... arguments) {
		return arguments;
	}

	static {
		TOKEN_HIGHLIGHTS.put(Asm6502Types.COMMENT, toArray(COMMENT));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.STRING, toArray(STRING));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.NUMBER, toArray(NUMBER));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.BINARY_VALUE, toArray(NUMBER));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.DECIMAL_VALUE, toArray(NUMBER));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.HEXADECIMAL_VALUE, toArray(NUMBER));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.ADDRESS_VALUE, toArray(NUMBER));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.INDIRECT_VALUE, toArray(NUMBER));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.MNEMONIC, toArray(MNEMONIC));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.DIRECTIVE, toArray(DIRECTIVE));
		TOKEN_HIGHLIGHTS.put(Asm6502Types.LABEL, toArray(LABEL));
		TOKEN_HIGHLIGHTS.put(TokenType.BAD_CHARACTER, toArray(INVALID));
	}

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new Asm6502LexerAdapter();
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
		TextAttributesKey[] highlights = TOKEN_HIGHLIGHTS.get(iElementType);

		if (highlights == null) {
			highlights = new TextAttributesKey[0];
		}

		return highlights;
	}
}
