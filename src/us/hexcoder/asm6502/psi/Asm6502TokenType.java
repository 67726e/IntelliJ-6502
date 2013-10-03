package us.hexcoder.asm6502.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import us.hexcoder.asm6502.configuration.Asm6502Language;

/**
 * User: 67726e
 */

public class Asm6502TokenType extends IElementType {

	public Asm6502TokenType(@NotNull @NonNls String debugName) {
		super(debugName, Asm6502Language.INSTANCE);
	}

	@Override
	public String toString() {
		return "Asm6502TokenType." + super.toString();
	}
}
