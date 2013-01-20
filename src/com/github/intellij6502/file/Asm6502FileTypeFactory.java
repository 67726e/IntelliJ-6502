package com.github.intellij6502.file;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * User: 67726e
 */

public class Asm6502FileTypeFactory extends FileTypeFactory {

	@Override
	public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
		// TODO: Create FileNameMatcher to allow for multiple file type extensions
		fileTypeConsumer.consume(Asm6502FileType.INSTANCE, Asm6502FileType.INSTANCE.getDefaultExtension());
	}
}
