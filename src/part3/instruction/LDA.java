/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instruction;

import memory.MCU;
import util.StringUtil;
import cpu.CPU;
import util.EffectiveAddress;
import util.MachineFaultException;

/**
 *
 * @author Alina
 */
public class LDA extends Abstractinstruction {

	int r;
	int ix;
	int address;
	int i;

	@Override
	public void execute(String instruction, CPU cpu, MCU mcu) throws MachineFaultException {
		// -----------------------------------
		// 03:LDA -> Load Register From Memory
		// -----------------------------------
		r = StringUtil.binaryToDecimal(instruction.substring(6, 8));
		ix = StringUtil.binaryToDecimal(instruction.substring(8, 10));
		i = StringUtil.binaryToDecimal(instruction.substring(10, 11));
		address = StringUtil.binaryToDecimal(instruction.substring(11, 16));

		int effectiveAddress = EffectiveAddress.computeEffectiveAddress(ix, address, i, mcu, cpu);

		// reading the content of selected register using [R] in the
		// instruction
		cpu.setRnByNum(r, effectiveAddress);
		cpu.increasePC();

	}

	@Override
	public String getExecuteMessage() {
		return "LDA " + r + ", " + ix + ", " + address + ", " + i;
	}

}
