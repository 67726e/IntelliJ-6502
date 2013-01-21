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

%%

<YYINITIAL> {EOL_COMMENT}								{ yybegin(YYINITIAL); return Asm6502Type.COMMENT; }
<YYINITIAL> {LABEL}										{ yybegin(YYINITIAL); return Asm6502Type.LABEL; }

<WAITING_VALUE> {CRLF}									{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
<WAITING_VALUE> {WHITESPACE}+							{ yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

{CRLF}													{ yybegin(YYINITIAL); return Asm6502Type.CRLF; }
{WHITESPACE}+											{ yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.														{ return TokenType.BAD_CHARACTER; }
