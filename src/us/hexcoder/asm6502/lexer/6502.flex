package us.hexcoder.asm6502.lexer;

import java.util.Stack;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import us.hexcoder.asm6502.psi.Asm6502Types;

%%

%{
	private Stack<Integer> stateStack = new Stack<Integer>();

	// Same as `yy_push_state`
	private void pushState(int state) {
		this.stateStack.push(state);
		yybegin(state);
	}

	// Same as `yy_pop_state`
	private int popState() {
		int state = this.stateStack.pop();
		yybegin(state);
		return state;
	}

	// Same as `yy_top_state`
	private int peekState() {
		return this.stateStack.peek();
	}
%}

%public
%class Asm6502Lexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

CRLF=\n|\r|\r\n
WHITESPACE=[\ \t\f]
COMMENT=";"[^\r\n]*
COMMA=","

OPEN_PAREN="("
CLOSE_PAREN=")"

DIRECTIVE="."[a-zA-Z]+
LABEL=[a-zA-Z0-9]+":"
MNEMONIC=[a-zA-Z]{3}

ACCUMULATOR_OPERAND=[aA]
LABEL_OPERAND=[a-zA-Z]+
BINARY_OPERAND="#%"[0-1]+
DECIMAL_OPERAND="#"[0-9]+
HEXADECIMAL_OPERAND="#$"[0-9a-fA-F]+

ADDRESS_VALUE="$"[0-9a-fA-F]+

INDIRECT_VALUE="($"[0-9a-fA-F]+")"
INDIRECT_X_VALUE="($"[0-9a-fA-F]+","[\ \t\f]*[xX]")"
INDIRECT_Y_VALUE="($"[0-9a-fA-F]+","[\ \t\f]*[yY]")"

BINARY_NUMBER="%"[0-1]+
DECIMAL_NUMBER=[0-9]+
HEXADECIMAL_NUMBER="0x"[0-9a-fA-F]+
STRING="\""(.+?)"\""

REGISTER_X=[xX]
REGISTER_Y=[yY]

%state DIRECTIVE_ARGUMENT
%state OPERAND
%state COMMA
%state ADDRESS

%%

<YYINITIAL> {
	{DIRECTIVE}							{ yybegin(DIRECTIVE_ARGUMENT); return Asm6502Types.DIRECTIVE; }
	{LABEL}								{ yybegin(YYINITIAL); return Asm6502Types.LABEL; }
	{MNEMONIC} 							{ yybegin(OPERAND); return Asm6502Types.MNEMONIC; }
}

<DIRECTIVE_ARGUMENT> {
	{BINARY_NUMBER}						{ yybegin(YYINITIAL); return Asm6502Types.BINARY_NUMBER; }
	{DECIMAL_NUMBER}					{ yybegin(YYINITIAL); return Asm6502Types.DECIMAL_NUMBER; }
	{HEXADECIMAL_NUMBER}				{ yybegin(YYINITIAL); return Asm6502Types.HEXADECIMAL_NUMBER; }
	{STRING}							{ yybegin(YYINITIAL); return Asm6502Types.STRING; }

	{WHITESPACE}+						{ return TokenType.WHITE_SPACE; }
	.									{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

<OPERAND> {
	{ACCUMULATOR_OPERAND}				{ yybegin(YYINITIAL); return Asm6502Types.ACCUMULATOR_OPERAND; }
	{LABEL_OPERAND}						{ yybegin(YYINITIAL); return Asm6502Types.LABEL_OPERAND; }
	{BINARY_OPERAND} 					{ yybegin(YYINITIAL); return Asm6502Types.BINARY_OPERAND; }
	{DECIMAL_OPERAND} 					{ yybegin(YYINITIAL); return Asm6502Types.DECIMAL_OPERAND; }
	{HEXADECIMAL_OPERAND} 				{ yybegin(YYINITIAL); return Asm6502Types.HEXADECIMAL_OPERAND; }

	{ADDRESS_VALUE}						{ yybegin(ADDRESS); return Asm6502Types.ADDRESS_VALUE; }

	{INDIRECT_Y_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.INDIRECT_X_VALUE; }
	{INDIRECT_X_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.INDIRECT_Y_VALUE; }
	{INDIRECT_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.INDIRECT_VALUE; }

	{WHITESPACE}+						{ return TokenType.WHITE_SPACE; }
	.									{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

<ADDRESS> {
	{COMMA}								{ yybegin(COMMA); return Asm6502Types.COMMA; }

	{WHITESPACE}+						{ return TokenType.WHITE_SPACE; }
	.									{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

<COMMA> {
	{REGISTER_X} 						{ yybegin(YYINITIAL); return Asm6502Types.REGISTER_X; }
	{REGISTER_Y} 						{ yybegin(YYINITIAL); return Asm6502Types.REGISTER_Y; }

	{WHITESPACE}+						{ return TokenType.WHITE_SPACE; }
	.									{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

{COMMENT}								{ yybegin(YYINITIAL); return Asm6502Types.COMMENT; }
{CRLF}									{ yybegin(YYINITIAL); return Asm6502Types.CRLF; }
{WHITESPACE}+							{ yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.										{ return TokenType.BAD_CHARACTER; }
