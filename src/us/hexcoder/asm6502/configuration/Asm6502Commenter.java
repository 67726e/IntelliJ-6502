package us.hexcoder.asm6502.configuration;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 * User: 67726e
 */

public class Asm6502Commenter implements Commenter {
	private static final String LINE_COMMENT_PREFIX = ";";

	@Nullable
	@Override
	public String getLineCommentPrefix() {
		return LINE_COMMENT_PREFIX;
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
