package us.hexcoder.asm6502.annotator;

import java.util.Arrays;
import java.util.List;

/**
 * User: 67726e
 */

public enum  Mnemonic {
	ADC(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.ABSOLUTE_Y, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	AND(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.ABSOLUTE_Y, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	ASL(Operand.ACCUMULATOR, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X),
	BCC(Operand.RELATIVE),
	BCS(Operand.RELATIVE),
	BEQ(Operand.RELATIVE),
	BIT(Operand.ZERO_PAGE, Operand.ABSOLUTE),
	BMI(Operand.RELATIVE),
	BNE(Operand.RELATIVE),
	BPL(Operand.RELATIVE),
	BRK(Operand.IMPLIED),
	BVC(Operand.RELATIVE),
	CLC(Operand.IMPLIED),
	CLD(Operand.IMPLIED),
	CLI(Operand.IMPLIED),
	CLV(Operand.IMPLIED),
	CMP(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.ABSOLUTE_Y, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	CPX(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ABSOLUTE),
	CPY(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ABSOLUTE),
	DEC(Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X),
	DEX(Operand.IMPLIED),
	DEY(Operand.IMPLIED),
	EOR(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.ABSOLUTE_Y, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	INC(Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X),
	INX(Operand.IMPLIED),
	JMP(Operand.ABSOLUTE, Operand.INDIRECT),
	JSR(Operand.ABSOLUTE),
	LDA(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.ABSOLUTE_Y, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	LDX(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_Y, Operand.ABSOLUTE, Operand.ABSOLUTE_Y),
	LDY(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X),
	LSR(Operand.ACCUMULATOR, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X),
	NOP(Operand.IMPLIED),
	ORA(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.ABSOLUTE_Y, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	PHA(Operand.IMPLIED),
	PHP(Operand.IMPLIED),
	PLA(Operand.IMPLIED),
	PLP(Operand.IMPLIED),
	ROL(Operand.ACCUMULATOR, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X),
	ROR(Operand.ACCUMULATOR, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_Y),
	RTI(Operand.IMPLIED),
	RTS(Operand.IMPLIED),
	SBC(Operand.IMMEDIATE, Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	SEC(Operand.IMPLIED),
	SED(Operand.IMPLIED),
	SEI(Operand.IMPLIED),
	STA(Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE, Operand.ABSOLUTE_X, Operand.ABSOLUTE_Y, Operand.INDIRECT_X, Operand.INDIRECT_Y),
	STX(Operand.ZERO_PAGE, Operand.ZERO_PAGE_Y, Operand.ABSOLUTE),
	STY(Operand.ZERO_PAGE, Operand.ZERO_PAGE_X, Operand.ABSOLUTE),
	TAX(Operand.IMPLIED),
	TAY(Operand.IMPLIED),
	TSX(Operand.IMPLIED),
	TXA(Operand.IMPLIED),
	TXS(Operand.IMPLIED),
	TYA(Operand.IMPLIED);

	private List<Operand> acceptedOperands;

	Mnemonic(Operand... acceptedOperands) {
		this.acceptedOperands = Arrays.asList(acceptedOperands);
	}

	public boolean isValidOperand(Operand operand) {
		return acceptedOperands.contains(operand);
	}

	public static Mnemonic fromString(String mnemonic) {
		try {
			return Mnemonic.valueOf(mnemonic.toUpperCase());
		} catch (IllegalArgumentException ignored) {}

		return null;
	}
}
