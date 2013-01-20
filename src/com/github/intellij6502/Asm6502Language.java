package com.github.intellij6502;

import com.intellij.lang.Language;

/**
 * User: 67726e
 */

public class Asm6502Language extends Language {
	public static final Asm6502Language INSTANCE = new Asm6502Language();

	private Asm6502Language() {
		super("6502");
	}
}
