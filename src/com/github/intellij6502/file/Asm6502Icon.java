package com.github.intellij6502.file;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * User: 67726e
 */

public class Asm6502Icon {
	private static final Icon FILE_ICON = IconLoader.getIcon("/com/github/intellij6502/icon/icon.png");

	public static Icon getFileIcon() {
		// TODO: Refactor icon location to sit in /resource/image/ folder
		return FILE_ICON;
	}
}
