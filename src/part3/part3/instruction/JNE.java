/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class JNE extends Abstractinstruction {

	int r;
	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// -----------------------------------
		// 011: JNE -> Jump if Not Equal
		// -----------------------------------
		r = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);

		// reading the content of selected register using [R] in the
		// instruction
		if (cpu.getRnByNum(r) != 0) {
			cpu.setPC(effectiveAddress);
		} else {
			cpu.increasePC();
		}
	}

	@Override
	public String getExecuteMessage() {
		// TODO Auto-generated method stub
		return "JNE " + r + ", " + ix + ", " + address + ", " + i;
	}    
}
