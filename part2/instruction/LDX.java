package part2.instruction;

import part2.memory.MCU;
import part2.util.StringUtil;
import part2.cpu.CPU;
import part2.util.EffectiveAddress;
import part2.util.MachineFaultException;

/**
 *
 * @author Alina
 */
public class LDX extends Abstractinstruction {
	int r;
	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// -----------------------------------
		// 41: LDX -> Load Index Register from Memory
		// -----------------------------------
		r = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);

		// first, we read the content of selected Index Register using [IX]
		cpu.setMAR(effectiveAddress);
		cpu.setMBR(mcu.fetchFromCache(cpu.getMAR()));
		cpu.setXnByNum(ix, cpu.getIntMBR());

		cpu.increasePC();
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "LDX " + r + ", " + ix + ", " + address + ", " + i;
	}    
}
