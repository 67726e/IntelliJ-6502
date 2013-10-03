package us.hexcoder.asm6502.configuration;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: 67726e
 */

public class Asm6502FileType extends LanguageFileType {
	private static final String NAME = "6502";
	private static final String DESCRIPTION = "6502 Assembly";
	private static final String DEFAULT_EXTENSION = "6502";
	private static final Icon ICON = Asm6502Icons.ICON;

	public static final Asm6502FileType INSTANCE = new Asm6502FileType();

	private Asm6502FileType() {
		super(Asm6502Language.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return NAME;
	}

	@NotNull
	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return ICON;
	}
}
