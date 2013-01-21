package com.github.intellij6502.parser;

import com.github.intellij6502.Asm6502Language;
import com.github.intellij6502.lexer.Asm6502Lexer;
import com.github.intellij6502.psi.Asm6502File;
import com.github.intellij6502.psi.Asm6502Type;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

/**
 * User: 67726e
 */

public class Asm6502ParserDefinition implements ParserDefinition {
	public static final TokenSet WHITESPACES = TokenSet.create(TokenType.WHITE_SPACE);
	public static final TokenSet COMMENTS = TokenSet.create(Asm6502Type.COMMENT);
	public static final IFileElementType FILE =
		new IFileElementType(Language.<Asm6502Language>findInstance(Asm6502Language.class));

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		return new FlexAdapter(new Asm6502Lexer((Reader) null));
	}

	@NotNull
	public TokenSet getWhitespaceTokens() {
		return WHITESPACES;
	}

	@NotNull
	public TokenSet getCommentTokens() {
		return COMMENTS;
	}

	@NotNull
	public TokenSet getStringLiteralElements() {
		return TokenSet.EMPTY;
	}

	@NotNull
	public PsiParser createParser(final Project project) {
		return new Asm6502Parser();
	}

	@Override
	public IFileElementType getFileNodeType() {
		return FILE;
	}

	public PsiFile createFile(FileViewProvider viewProvider) {
		return new Asm6502File(viewProvider);
	}

	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
		return SpaceRequirements.MAY;
	}

	@NotNull
	public PsiElement createElement(ASTNode node) {
		return Asm6502Type.Factory.createElement(node);
	}
}
