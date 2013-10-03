package us.hexcoder.asm6502.configuration;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * User: 67726e
 */

public class Asm6502FileTypeFactory extends FileTypeFactory {

	@Override
	public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
		fileTypeConsumer.consume(Asm6502FileType.INSTANCE, Asm6502FileType.INSTANCE.getName());
	}
}
