package Aes;

import java.util.Arrays;

public class Aes {
	PRP prp = new PRP();

	public byte[] encrypt(byte[] input, byte[] key) {

		int[] inputInInt = new int[input.length];
		inputInInt = bytesInInt(input);

		int[] keyInInt = new int[key.length];
		keyInInt = bytesInInt(key);

		int[] inputAfterPadding = padding(inputInInt);
		int[] resultInInt = new int[inputAfterPadding.length];

		KeyExpansion ke = new KeyExpansion();
		int[][] expandedKey = ke.keyExpand(keyInInt);

		for (int i = 0; i < inputAfterPadding.length; i += 16) {
			int[] state = Arrays.copyOfRange(inputAfterPadding, i, i + 16);
			int[] encriptedBlock = encryptBlock(state, expandedKey);
			System.arraycopy(encriptedBlock, 0, resultInInt, i, 16);
		}

		byte[] result = intsInByte(resultInInt);
		return result;
	}

	public byte[] decrypt(byte[] input, byte[] key) {

		int[] inputInInt = new int[input.length];
		inputInInt = bytesInInt(input);

		int[] keyInInt = new int[key.length];
		keyInInt = bytesInInt(key);

		int[] resultInInt = new int[input.length];

		KeyExpansion ke = new KeyExpansion();
		int[][] expandedKey = ke.keyExpand(keyInInt);

		for (int i = 0; i < input.length; i += 16) {
			int[] state = Arrays.copyOfRange(inputInInt, i, i + 16);
			int[] decryptedBlock = decryptBlock(state, expandedKey);
			System.arraycopy(decryptedBlock, 0, resultInInt, i, 16);
		}

		int[] afterRemovePadding;
		afterRemovePadding = removePadding(resultInInt);

		byte[] result = intsInByte(afterRemovePadding);
		return result;
	}

	// erti 16 baitiani blokis dekripti
	private int[] decryptBlock(int[] state, int[][] expandedKey) {
		int[] roundKey = getRoundKey(expandedKey, 10);

		state = prp.AddRoundKeys(state, roundKey);
		state = prp.revShiftRows(state);
		state = prp.revSubBytes(state);

		for (int i = 9; i >= 1; i--) {
			roundKey = getRoundKey(expandedKey, i);
			state = prp.AddRoundKeys(state, roundKey);
			state = prp.revMixColumns(state);
			state = prp.revShiftRows(state);
			state = prp.revSubBytes(state);
		}

		roundKey = getRoundKey(expandedKey, 0);
		state = prp.AddRoundKeys(state, roundKey);

		return state;
	}

	// erti 16 baitiani blokis dakriptva
	private int[] encryptBlock(int[] state, int[][] expandedKey) {
		int[] roundKey = getRoundKey(expandedKey, 0);

		state = prp.AddRoundKeys(state, roundKey);

		for (int i = 1; i < 10; i++) {
			state = prp.subBytes(state);
			state = prp.ShiftRows(state);
			state = prp.MixColumns(state);
			roundKey = getRoundKey(expandedKey, i);
			state = prp.AddRoundKeys(state, roundKey);
		}
		state = prp.subBytes(state);
		state = prp.ShiftRows(state);
		roundKey = getRoundKey(expandedKey, 10);
		state = prp.AddRoundKeys(state, roundKey);

		return state;
	}

	// round keys amogeba round keyebis masividan
	private int[] getRoundKey(int[][] expandedKey, int i) {
		int[] result = new int[16];
		for (int j = 0; j < 4; j++) {
			System.arraycopy(expandedKey[4 * i + j], 0, result, j * 4, 4);
		}
		return result;
	}

	// intebis masivis baitebis masivshi gadayvana
	private byte[] intsInByte(int[] resultInInt) {
		byte[] result = new byte[resultInInt.length];
		for (int i = 0; i < resultInInt.length; i++) {
			result[i] = (byte) resultInInt[i];
		}
		return result;
	}

	// baitebis masivis intebis masivshi gadayvana
	private int[] bytesInInt(byte[] input) {
		int[] result = new int[input.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (int) input[i];
		}
		return result;
	}

	// padingis damateba
	private int[] padding(int[] inputInInt) {
		int resultSize = (inputInInt.length / 16 + 1) * 16;
		int[] result = new int[resultSize];
		for (int i = 0; i < inputInInt.length; i++) {
			result[i] = inputInInt[i];
		}
		result[inputInInt.length] = 0xFF;
		for (int i = inputInInt.length + 1; i < result.length; i++) {
			result[i] = 0x00;
		}
		return result;
	}

	// padingis washla
	private int[] removePadding(int[] resultInInt) {
		for (int i = resultInInt.length - 1; i >= 0; i--) {
			if (resultInInt[i] == 0xFF) {
				return Arrays.copyOfRange(resultInInt, 0, i);
			}
		}
		return resultInInt;
	}

}
