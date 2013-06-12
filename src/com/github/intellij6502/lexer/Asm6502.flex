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
LABEL=[a-zA-Z]+:
LABEL_ARGUMENT=[a-zA-Z]
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

%state DIRECTIVE
%state MNEMONIC
%state INDIRECT
%state INDIRECT_CLOSE

%%

<YYINITIAL> {EOL_COMMENT}								{ return Asm6502Type.COMMENT; }
<YYINITIAL> {LABEL}										{ return Asm6502Type.LABEL; }
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
	{ADDRESS}											{ yybegin(YYINITIAL); return Asm6502Type.ADDRESS; }
	{OPEN_PARENTHESIS}									{ yybegin(INDIRECT); return Asm6502Type.OPEN_PARENTHESIS; }
	{LABEL_ARGUMENT}									{ yybegin(YYINITIAL); return Asm6502Type.LABEL_ARGUMENT; }
	{CRLF}												{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
	{EOL_COMMENT}										{ yybegin(YYINITIAL); return Asm6502Type.COMMENT; }
}

<INDIRECT> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{ADDRESS}											{ yybegin(INDIRECT_CLOSE); return Asm6502Type.ADDRESS; }
}

<INDIRECT_CLOSE> {
	{WHITESPACE}+										{ return TokenType.WHITE_SPACE; }
	{CLOSE_PARENTHESIS}									{ yybegin(YYINITIAL); return Asm6502Type.CLOSE_PARENTHESIS; }
}

{CRLF}													{ return Asm6502Type.CRLF; }
{WHITESPACE}+											{ return TokenType.WHITE_SPACE; }
.														{ return TokenType.BAD_CHARACTER; }


//Immediate	#$4F
//Zero-Page	$44
//Zero-Page,x	$44,x
//Zero-Page,y	$44,y
//Absolute	$4400
//Absolute,x	$4400,x
//Absolute,y	$4400,y
//Indirect	($4400)
//Indirect,x	($44,x)
//Indirect,y	($44,y)
//Accumulator	a
//Implied
//Relative	LABEL
