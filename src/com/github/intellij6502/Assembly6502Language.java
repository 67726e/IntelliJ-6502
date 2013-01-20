package com.github.intellij6502;

import com.intellij.lang.Language;

/**
 * User: 67726e
 */

public class Assembly6502Language extends Language {
	private static final Assembly6502Language INSTANCE = new Assembly6502Language();

	private Assembly6502Language() {
		super("6502");
	}
}
