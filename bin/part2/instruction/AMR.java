package part2.instruction;

import part2.cpu.CPU;
import part2.memory.MCU;
import part2.util.Const;
import part2.util.EffectiveAddress;
import part2.util.MachineFaultException;
import part2.util.StringUtil;

/**
 *
 * @author Alina
 */
public class AMR extends Abstractinstruction {

	int r;
	int ix;
	int i;
	int address;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// -----------------------------------
		// 004: AMR -> Add Memory to Register
		// r <- c(r) + c(EA)
		// -----------------------------------
		r = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);
		
		// first, we store the effective address in memory address register
		cpu.setMAR(effectiveAddress);

		// storing what we fetched from memory into the memory buffer
		// register
		cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));

		int result = cpu.getRnByNum(r) + cpu.getIntMBR();

		// we check if we have an overflow
		if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
			cpu.setCCElementByBit(Const.ConditionCode.OVERFLOW.getValue(), true);
		} else {
			// if we do not have an overflow, we update the value of
			// register
			cpu.setRnByNum(r, result);
		}

		cpu.increasePC();
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "AMR " + r + ", " + ix + ", " + address + ", " + i;
	}

}
