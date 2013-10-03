package us.hexcoder.asm6502.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import us.hexcoder.asm6502.configuration.Asm6502Language;

/**
 * User: 67726e
 */

public class Asm6502ElementType extends IElementType {
	public Asm6502ElementType(@NotNull @NonNls String debugName) {
		super(debugName, Asm6502Language.INSTANCE);
	}
}
