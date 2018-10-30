package instruction;

import cpu.CPU;
import memory.MCU;
import util.Const;
import util.EffectiveAddress;
import util.MachineFaultException;
import util.StringUtil;

/**
 *
 * @author Alina
 */
public class FADD extends Abstractinstruction {

	int fr;
	int ix;
	int i;
	int address;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// -----------------------------------
		// 033: FADD -> Floating ADD Memory to Register
		// c(FR) <- c(FR) + c(EA)
		// c(FR) <- c(FR) + c(c(EA)), if l bit set
		// -----------------------------------
		fr = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);
		
		// first, we store the effective address in memory address register
		cpu.setMAR(effectiveAddress);

		// storing what we fetched from memory into the memory buffer
		// register
		cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));

		int result = cpu.getFRByNum(fr) + cpu.getIntMBR();

		// we check if we have an overflow
		int MAX_VALUE = 2^6;
		int MIN_VALUE = -2^6-1;
		if (result > MAX_VALUE && result < MIN_VALUE) {
			cpu.setCCElementByBit(Const.ConditionCode.OVERFLOW.getValue(), true);
		} else {
			// if we do not have an overflow, we update the value of
			// register

			cpu.setFRByNum(fr, result);			

		}

		cpu.increasePC();
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "FADD " + fr + ", " + ix + ", " + address + ", " + i;
	}

}
