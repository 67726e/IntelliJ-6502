package us.hexcoder.asm6502.annotator;

/**
 * User: 67726e
 */

public enum Operand {
	ACCUMULATOR,
	IMMEDIATE,
	ZERO_PAGE,
	ZERO_PAGE_X,
	ZERO_PAGE_Y,
	ABSOLUTE,
	ABSOLUTE_X,
	ABSOLUTE_Y,
	INDIRECT,
	INDIRECT_X,
	INDIRECT_Y,
	IMPLIED,
	RELATIVE;
}
