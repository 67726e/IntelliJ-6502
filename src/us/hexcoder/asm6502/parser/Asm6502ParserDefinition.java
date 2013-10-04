package us.hexcoder.asm6502.parser;

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
import us.hexcoder.asm6502.configuration.Asm6502Language;
import us.hexcoder.asm6502.lexer.Asm6502LexerAdapter;
import us.hexcoder.asm6502.parser.Asm6502Parser;
import us.hexcoder.asm6502.psi.Asm6502File;
import us.hexcoder.asm6502.psi.Asm6502Types;

/**
 * User: 67726e
 */

public class Asm6502ParserDefinition implements ParserDefinition {
	public static final TokenSet WHITESPACES = TokenSet.create(TokenType.WHITE_SPACE);
	public static final TokenSet COMMENTS = TokenSet.create(Asm6502Types.COMMENT);
	public static final TokenSet STRINGS = TokenSet.create(Asm6502Types.DIRECTIVE_STRING);

	public static final IFileElementType FILE = new IFileElementType(Language.findInstance(Asm6502Language.class));

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		return new Asm6502LexerAdapter();
	}

	@Override
	public PsiParser createParser(Project project) {
		return new Asm6502Parser();
	}

	@Override
	public IFileElementType getFileNodeType() {
		return FILE;
	}

	@NotNull
	@Override
	public TokenSet getWhitespaceTokens() {
		return WHITESPACES;
	}

	@NotNull
	@Override
	public TokenSet getCommentTokens() {
		return COMMENTS;
	}

	@NotNull
	@Override
	public TokenSet getStringLiteralElements() {
		return STRINGS;
	}

	@NotNull
	@Override
	public PsiElement createElement(ASTNode astNode) {
		return Asm6502Types.Factory.createElement(astNode);
	}

	@Override
	public PsiFile createFile(FileViewProvider fileViewProvider) {
		return new Asm6502File(fileViewProvider);
	}

	@Override
	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode2) {
		return SpaceRequirements.MAY;
	}
}
