package us.hexcoder.asm6502.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import us.hexcoder.asm6502.psi.Asm6502Types;

%%

%public
%class Asm6502Lexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

CRLF=\n|\r|\r\n
WHITESPACE=[\ \t\f]
COMMENT=";"[^\r\n]*

DIRECTIVE="."[a-zA-Z]+
LABEL=[a-zA-Z0-9]+":"
MNEMONIC=[a-zA-Z]{3}

ACCUMULATOR_VALUE=[aA]
LABEL_VALUE=[a-zA-Z]+
BINARY_VALUE="#%"[0-1]+
DECIMAL_VALUE="#"[0-9]+
HEXADECIMAL_VALUE="#$"[0-9a-fA-F]+

ZERO_PAGE_X_VALUE="$"[0-9a-fA-F]+","[\ \t\f]*[xX]
ZERO_PAGE_Y_VALUE="$"[0-9a-fA-F]+","[\ \t\f]*[yY]
ADDRESS_VALUE="$"[0-9a-fA-F]+

INDIRECT_VALUE="($"[0-9a-fA-F]+")"
INDIRECT_X_VALUE="($"[0-9a-fA-F]+","[\ \t\f]*[xX]")"
INDIRECT_Y_VALUE="($"[0-9a-fA-F]+","[\ \t\f]*[yY]")"

NUMBER=[0-9]+
STRING="\""(.+?)"\""

%state DIRECTIVE_OPERAND
%state OPERAND

%%

<YYINITIAL> {
	{COMMENT}							{ yybegin(YYINITIAL); return Asm6502Types.COMMENT; }
	{DIRECTIVE}							{ yybegin(DIRECTIVE_OPERAND); return Asm6502Types.DIRECTIVE; }
	{LABEL}								{ yybegin(YYINITIAL); return Asm6502Types.LABEL; }
	{MNEMONIC} 							{ yybegin(OPERAND); return Asm6502Types.MNEMONIC; }
}

<DIRECTIVE_OPERAND> {
	{NUMBER}							{ yybegin(YYINITIAL); return Asm6502Types.NUMBER; }
	{STRING}							{ yybegin(YYINITIAL); return Asm6502Types.STRING; }

	{WHITESPACE}+						{ return TokenType.WHITE_SPACE; }
	.									{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

<OPERAND> {
	{ACCUMULATOR_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.ACCUMULATOR_VALUE; }
	{LABEL_VALUE}						{ yybegin(YYINITIAL); return Asm6502Types.LABEL_VALUE; }
	{BINARY_VALUE} 						{ yybegin(YYINITIAL); return Asm6502Types.BINARY_VALUE; }
	{DECIMAL_VALUE} 					{ yybegin(YYINITIAL); return Asm6502Types.DECIMAL_VALUE; }
	{HEXADECIMAL_VALUE} 				{ yybegin(YYINITIAL); return Asm6502Types.HEXADECIMAL_VALUE; }

	{ZERO_PAGE_X_VALUE} 				{ yybegin(YYINITIAL); return Asm6502Types.ZERO_PAGE_X_VALUE; }
	{ZERO_PAGE_Y_VALUE} 				{ yybegin(YYINITIAL); return Asm6502Types.ZERO_PAGE_Y_VALUE; }
	{ADDRESS_VALUE}						{ yybegin(YYINITIAL); return Asm6502Types.ADDRESS_VALUE; }

	{INDIRECT_Y_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.INDIRECT_X_VALUE; }
	{INDIRECT_X_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.INDIRECT_Y_VALUE; }
	{INDIRECT_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.INDIRECT_VALUE; }

	{WHITESPACE}+						{ return TokenType.WHITE_SPACE; }
	.									{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

{CRLF}									{ yybegin(YYINITIAL); return Asm6502Types.CRLF; }
{WHITESPACE}+							{ yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.										{ return TokenType.BAD_CHARACTER; }
