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

BINARY_VALUE="#%"[0-1]+
DECIMAL_VALUE="#"[0-9]+
HEXADECIMAL_VALUE="#$"[0-9a-fA-F]+
LABEL_VALUE=[a-zA-Z]+

%state OPERAND

%%

<YYINITIAL> {COMMENT}					{ yybegin(YYINITIAL); return Asm6502Types.COMMENT; }
<YYINITIAL> {DIRECTIVE}					{ yybegin(OPERAND); return Asm6502Types.DIRECTIVE; }
<YYINITIAL> {LABEL}						{ yybegin(YYINITIAL); return Asm6502Types.LABEL; }
<YYINITIAL> {MNEMONIC} 					{ yybegin(OPERAND); return Asm6502Types.MNEMONIC; }

<OPERAND> {BINARY_VALUE} 				{ yybegin(YYINITIAL); return Asm6502Types.BINARY_VALUE; }
<OPERAND> {DECIMAL_VALUE} 				{ yybegin(YYINITIAL); return Asm6502Types.DECIMAL_VALUE; }
<OPERAND> {HEXADECIMAL_VALUE} 			{ yybegin(YYINITIAL); return Asm6502Types.HEXADECIMAL_VALUE; }
<OPERAND> {LABEL_VALUE}					{ yybegin(YYINITIAL); return Asm6502Types.LABEL_VALUE; }

{CRLF}									{ yybegin(YYINITIAL); return Asm6502Types.CRLF; }
{WHITESPACE}+							{ yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.										{ return TokenType.BAD_CHARACTER; }
