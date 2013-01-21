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
HEX_VALUE="#$"([0-9]|[a-f]|[A-F])+
BIN_VALUE="#%"[0-1]+
DEC_VALUE="#"[0-9]+

%%

<YYINITIAL> {EOL_COMMENT}								{ yybegin(YYINITIAL); return Asm6502Type.COMMENT; }
<YYINITIAL> {LABEL}										{ yybegin(YYINITIAL); return Asm6502Type.LABEL; }
<YYINITIAL> {HEX_VALUE}									{ yybegin(YYINITIAL); return Asm6502Type.HEX_VALUE; }
<YYINITIAL> {BIN_VALUE}									{ yybegin(YYINITIAL); return Asm6502Type.BIN_VALUE; }
<YYINITIAL> {DEC_VALUE}									{ yybegin(YYINITIAL); return Asm6502Type.DEC_VALUE; }

<WAITING_VALUE> {CRLF}									{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
<WAITING_VALUE> {WHITESPACE}+							{ yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

{CRLF}													{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
{WHITESPACE}+											{ yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.														{ return TokenType.BAD_CHARACTER; }
