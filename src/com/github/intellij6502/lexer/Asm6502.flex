package com.github.intellij6502.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.github.intellij6502.psi.Asm6502Type;
import com.intellij.psi.TokenType;

%%

%class Asm6502Lexer
%implements FlexLexer
%public
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

%state STRING

CRLF= \n|\r|\r\n
WHITESPACE=[\ \t\f]
LABEL_DECLARATION=[a-zA-Z]+:
LABEL_ARGUMENT=[a-zA-Z]+
EOL_COMMENT=;[^\r\n]*

HEXADECIMAL_VALUE="#$"([0-9]|[a-f]|[A-F])+
DECIMAL_VALUE="#"[0-9]+
BINARY_VALUE="#%"[0-1]+

ADDRESS="$"([0-9]|[a-f]|[A-F])+

DIRECTIVE="."[a-z]+
DIRECTIVE_NUMBER=[0-9]+
DIRECTIVE_STRING=\".+\"

MNEMONIC=([A-Z]|[a-z]){3}

OPEN_PARENTHESIS="("
CLOSE_PARENTHESIS=")"

ACCUMULATOR=(a|A)
REGISTER_X=(x|X)
REGISTER_Y=(y|Y)
REGISTER_COMMA=","

%state DIRECTIVE
%state MNEMONIC
%state INDIRECT
%state INDIRECT_REGISTER_CHECK
%state INDIRECT_REGISTER
%state INDIRECT_CLOSE
%state REGISTER_CHECK
%state REGISTER

%%

<YYINITIAL> {EOL_COMMENT}								{ return Asm6502Type.COMMENT; }
<YYINITIAL> {LABEL_DECLARATION}							{ return Asm6502Type.LABEL_DECLARATION; }
<YYINITIAL> {DIRECTIVE}									{ yybegin(DIRECTIVE); return Asm6502Type.DIRECTIVE; }
<YYINITIAL> {MNEMONIC}									{ yybegin(MNEMONIC); return Asm6502Type.MNEMONIC; }

<DIRECTIVE> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{DIRECTIVE_NUMBER}									{ yybegin(YYINITIAL); return Asm6502Type.DIRECTIVE_NUMBER; }
	{DIRECTIVE_STRING}									{ yybegin(YYINITIAL); return Asm6502Type.DIRECTIVE_STRING; }
}

<MNEMONIC> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{HEXADECIMAL_VALUE}									{ yybegin(YYINITIAL); return Asm6502Type.HEXADECIMAL_VALUE; }
	{DECIMAL_VALUE}										{ yybegin(YYINITIAL); return Asm6502Type.DECIMAL_VALUE; }
	{BINARY_VALUE}										{ yybegin(YYINITIAL); return Asm6502Type.BINARY_VALUE; }
	{ADDRESS}											{ yybegin(REGISTER_CHECK); return Asm6502Type.ADDRESS; }
	{OPEN_PARENTHESIS}									{ yybegin(INDIRECT); return Asm6502Type.OPEN_PARENTHESIS; }
	{ACCUMULATOR}										{ yybegin(YYINITIAL); return Asm6502Type.ACCUMULATOR; }
	{LABEL_ARGUMENT}									{ yybegin(YYINITIAL); return Asm6502Type.LABEL_ARGUMENT; }
	{CRLF}												{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
	{EOL_COMMENT}										{ yybegin(YYINITIAL); return Asm6502Type.COMMENT; }
}

<INDIRECT> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{ADDRESS}											{ yybegin(INDIRECT_REGISTER_CHECK); return Asm6502Type.ADDRESS; }
}

<INDIRECT_REGISTER_CHECK> {
	{WHITESPACE}+										{ yybegin(INDIRECT_CLOSE); return TokenType.WHITE_SPACE; }
	{REGISTER_COMMA}									{ yybegin(INDIRECT_REGISTER); return Asm6502Type.REGISTER_COMMA; }
	{CLOSE_PARENTHESIS}									{ yybegin(YYINITIAL); return Asm6502Type.CLOSE_PARENTHESIS; }
}

<INDIRECT_REGISTER> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{REGISTER_X}										{ yybegin(INDIRECT_CLOSE); return Asm6502Type.REGISTER_X; }
	{REGISTER_Y}										{ yybegin(INDIRECT_CLOSE); return Asm6502Type.REGISTER_Y; }
	.													{ yybegin(INDIRECT_CLOSE); return TokenType.BAD_CHARACTER; }
}

<INDIRECT_CLOSE> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{CLOSE_PARENTHESIS}									{ yybegin(YYINITIAL); return Asm6502Type.CLOSE_PARENTHESIS; }
}

<REGISTER_CHECK> {
	{REGISTER_COMMA}									{ yybegin(REGISTER); return Asm6502Type.REGISTER_COMMA; }
	{CRLF}												{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
	{EOL_COMMENT}										{ yybegin(YYINITIAL); return Asm6502Type.COMMENT; }
	.													{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

<REGISTER> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{REGISTER_X}										{ yybegin(YYINITIAL); return Asm6502Type.REGISTER_X; }
	{REGISTER_Y}										{ yybegin(YYINITIAL); return Asm6502Type.REGISTER_Y; }
	.													{ yybegin(YYINITIAL); return TokenType.BAD_CHARACTER; }
}

{CRLF}													{ return Asm6502Type.CRLF; }
{WHITESPACE}+											{ return TokenType.WHITE_SPACE; }
.														{ return TokenType.BAD_CHARACTER; }


//Immediate		#$4F
//Zero-Page		$44
//Zero-Page,x	$44,x
//Zero-Page,y	$44,y
//Absolute		$4400
//Absolute,x	$4400,x
//Absolute,y	$4400,y
//Indirect		($4400)
//Indirect,x	($44,x)
//Indirect,y	($44,y)
//Accumulator	a
//Implied
//Relative		LABEL
