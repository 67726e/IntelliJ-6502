package us.hexcoder.asm6502.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import us.hexcoder.asm6502.configuration.Asm6502FileType;
import us.hexcoder.asm6502.configuration.Asm6502Language;

import javax.swing.*;

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

	@Override
	public String toString() {
		return "6502 Assembly";
	}

	@Override
	public Icon getIcon(int flags) {
		return super.getIcon(flags);
	}
}
