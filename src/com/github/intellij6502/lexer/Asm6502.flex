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
EOL_COMMENT=;[^\r\n]*

HEXADECIMAL_VALUE="#$"([0-9]|[a-f]|[A-F])+
DECIMAL_VALUE="#"[0-9]+
BINARY_VALUE="#%"[0-1]+

ADDRESS="$"([0-9]|[a-f]|[A-F])+

DIRECTIVE="."[a-z]+
DIRECTIVE_NUMBER=[0-9]+
DIRECTIVE_STRING=\".+\"

MNEMONIC=([A-Z]|[a-z]){3}

%state DIRECTIVE

%%

<YYINITIAL> {EOL_COMMENT}								{ yybegin(YYINITIAL); return Asm6502Type.COMMENT; }
<YYINITIAL> {LABEL}										{ yybegin(YYINITIAL); return Asm6502Type.LABEL; }
<YYINITIAL> {HEXADECIMAL_VALUE}							{ yybegin(YYINITIAL); return Asm6502Type.HEXADECIMAL_VALUE; }
<YYINITIAL> {DECIMAL_VALUE}								{ yybegin(YYINITIAL); return Asm6502Type.DECIMAL_VALUE; }
<YYINITIAL> {BINARY_VALUE}								{ yybegin(YYINITIAL); return Asm6502Type.BINARY_VALUE; }
<YYINITIAL> {ADDRESS}									{ yybegin(YYINITIAL); return Asm6502Type.ADDRESS; }
<YYINITIAL> {DIRECTIVE}									{ yybegin(DIRECTIVE); return Asm6502Type.DIRECTIVE; }
<YYINITIAL> {MNEMONIC}									{ yybegin(YYINITIAL); return Asm6502Type.MNEMONIC; }

<DIRECTIVE> {
	{WHITESPACE}+										{ yybegin(DIRECTIVE); return TokenType.WHITE_SPACE; }
	{DIRECTIVE_NUMBER}									{ yybegin(YYINITIAL); return Asm6502Type.DIRECTIVE_NUMBER; }
	{DIRECTIVE_STRING}									{ yybegin(YYINITIAL); return Asm6502Type.DIRECTIVE_STRING; }
}

{CRLF}													{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
{WHITESPACE}+											{ yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.														{ return TokenType.BAD_CHARACTER; }
