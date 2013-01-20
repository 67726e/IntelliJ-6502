package com.github.intellij6502.file;

import com.github.intellij6502.Asm6502Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: 67726e
 */

public class Asm6502FileType extends LanguageFileType {
	public static final Asm6502FileType INSTANCE = new Asm6502FileType();

	public Asm6502FileType() {
		super(Asm6502Language.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return "6502 Assembly";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "6502 Assembly Language";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return "6502";
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return Asm6502Icon.getFileIcon();
	}
}
