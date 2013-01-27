package com.github.intellij6502.settings;

import com.github.intellij6502.Asm6502Language;
import com.github.intellij6502.file.Asm6502Icon;
import com.github.intellij6502.highlighter.Asm6502SyntaxHighlighter;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * User: 67726e
 */

public class Asm6502ColorSettingsPage implements ColorSettingsPage {
	private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
		new AttributesDescriptor("Address", Asm6502SyntaxHighlighter.ADDRESS),
		new AttributesDescriptor("Comment", Asm6502SyntaxHighlighter.COMMENT),
		new AttributesDescriptor("Directive", Asm6502SyntaxHighlighter.DIRECTIVE),
		new AttributesDescriptor("Label", Asm6502SyntaxHighlighter.LABEL),
		new AttributesDescriptor("Mnemonic", Asm6502SyntaxHighlighter.MNEMONIC),
		new AttributesDescriptor("Number", Asm6502SyntaxHighlighter.NUMBER),
		new AttributesDescriptor("String", Asm6502SyntaxHighlighter.DIRECTIVE_STRING)
	};

	@Nullable
	@Override
	public Icon getIcon() {
		return Asm6502Icon.getFileIcon();
	}

	@NotNull
	@Override
	public SyntaxHighlighter getHighlighter() {
		return new Asm6502SyntaxHighlighter();
	}

	@NotNull
	@Override
	public String getDemoText() {
		return  "	.include \"somefile.asm\" ; Include another source file" +
				"	.inesprg 1 ; 1x 16KB PRG\n" +
				"	.ineschr 1 ; 1x 8KB CHR\n" +
				"	.inesmap 0 ; NROM\n" +
				"	.inesmir 1 ; Background mirroring\n" +
				"\n" +
				"RESET:\n" +
				"	SEI ; Disable IRQ\n" +
				"	CLD ; Disable binary encoded decimal\n" +
				"	LDX #$40\n" +
				"	STX $4017 ; Disable APU IRQ\n" +
				"	LDX #$FF\n" +
				"	TXS ; Setup stack\n" +
				"	INX\n" +
				"	STX $2000 ; Disable NMI\n" +
				"	STX $2001 ; Disable rendering\n" +
				"	STX $4010 ; Disable DMC IRQ";
	}

	@Nullable
	@Override
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	@NotNull
	@Override
	public AttributesDescriptor[] getAttributeDescriptors() {
		return DESCRIPTORS;
	}

	@NotNull
	@Override
	public ColorDescriptor[] getColorDescriptors() {
		return ColorDescriptor.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return Asm6502Language.INSTANCE.getDisplayName();
	}
}
