package com.github.intellij6502.psi;

import com.github.intellij6502.Asm6502Language;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * User: 67726e
 */

public class Asm6502TokenType extends IElementType {
	public Asm6502TokenType(@NotNull @NonNls String debugName) {
		super(debugName, Asm6502Language.INSTANCE);
	}
}
