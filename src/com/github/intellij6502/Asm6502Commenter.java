package com.github.intellij6502;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 * User: 67726e
 */

public class Asm6502Commenter implements Commenter {

	@Nullable
	@Override
	public String getLineCommentPrefix() {
		return ";";
	}

	@Nullable
	@Override
	public String getBlockCommentPrefix() {
		return null;
	}

	@Nullable
	@Override
	public String getBlockCommentSuffix() {
		return null;
	}

	@Nullable
	@Override
	public String getCommentedBlockCommentPrefix() {
		return null;
	}

	@Nullable
	@Override
	public String getCommentedBlockCommentSuffix() {
		return null;
	}
}
