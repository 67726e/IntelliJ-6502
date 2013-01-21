package com.github.intellij6502.psi;

import com.github.intellij6502.Asm6502Language;
import com.github.intellij6502.file.Asm6502FileType;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * User: 67726e
 */

public class Asm6502File extends PsiFileBase {
	public Asm6502File(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, Asm6502Language.INSTANCE);
	}

	@NotNull
	@Override
	public FileType getFileType() {
		return Asm6502FileType.INSTANCE;
	}
}
