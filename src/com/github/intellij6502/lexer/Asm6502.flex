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

%state WAITING_VALUE

CRLF= \n|\r|\r\n
WHITESPACE=[\ \t\f]
LABEL=[a-zA-Z]+:
EOL_COMMENT=;[^\r\n]*

HEXADECIMAL_VALUE="#$"([0-9]|[a-f]|[A-F])+
DECIMAL_VALUE="#"[0-9]+
BINARY_VALUE="#%"[0-1]+

ADDRESS_VALUE="$"([0-9]|[a-f]|[A-F])+

DIRECTIVE="."[a-z]+

MNEMONIC=([A-Z]|[a-z]){3}

%%

<YYINITIAL> {EOL_COMMENT}								{ yybegin(YYINITIAL); return Asm6502Type.COMMENT; }
<YYINITIAL> {LABEL}										{ yybegin(YYINITIAL); return Asm6502Type.LABEL; }

<YYINITIAL> {HEXADECIMAL_VALUE}							{ yybegin(YYINITIAL); return Asm6502Type.HEXADECIMAL_VALUE; }
<YYINITIAL> {DECIMAL_VALUE}								{ yybegin(YYINITIAL); return Asm6502Type.DECIMAL_VALUE; }
<YYINITIAL> {BINARY_VALUE}								{ yybegin(YYINITIAL); return Asm6502Type.BINARY_VALUE; }

<YYINITIAL> {ADDRESS_VALUE}								{yybegin(YYINITIAL); return Asm6502Type.ADDRESS_VALUE; }

<YYINITIAL> {DIRECTIVE}									{ yybegin(YYINITIAL); return Asm6502Type.DIRECTIVE; }

<YYINITIAL> {MNEMONIC}									{ yybegin(YYINITIAL); return Asm6502Type.MNEMONIC; }

<WAITING_VALUE> {CRLF}									{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
<WAITING_VALUE> {WHITESPACE}+							{ yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

{CRLF}													{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
{WHITESPACE}+											{ yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.														{ return TokenType.BAD_CHARACTER; }
