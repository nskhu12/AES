package Aes;

public class PRP {

	public int[] subBytes(int[] state) {
		int[] result = new int[16];
		for (int i = 0; i < state.length; i++) {
			result[i] = Sboxes.sbox[state[i] & 0xFF];
		}
		return result;
	}

	public int[] ShiftRows(int[] state) {
		int[] result = new int[16];

		result[0] = state[0];
		result[1] = state[5];
		result[2] = state[10];
		result[3] = state[15];

		result[4] = state[4];
		result[5] = state[9];
		result[6] = state[14];
		result[7] = state[3];

		result[8] = state[8];
		result[9] = state[13];
		result[10] = state[2];
		result[11] = state[7];

		result[12] = state[12];
		result[13] = state[1];
		result[14] = state[6];
		result[15] = state[11];

		return result;
	}

	public int[] MixColumns(int[] state) {
		int[] result = new int[16];

		for (int i = 0; i < result.length; i++) {
			if (i % 4 == 0) {
				result[i] = MultiplyMatrix.multiple2[state[i] & 0xFF] ^ MultiplyMatrix.multiple3[state[i + 1] & 0xFF]
						^ state[i + 2] ^ state[i + 3];
			}
			if (i % 4 == 1) {
				result[i] = state[i - 1] ^ MultiplyMatrix.multiple2[state[i] & 0xFF]
						^ MultiplyMatrix.multiple3[state[i + 1] & 0xFF] ^ state[i + 2];
			}
			if (i % 4 == 2) {
				result[i] = state[i - 2] ^ state[i - 1] ^ MultiplyMatrix.multiple2[state[i] & 0xFF]
						^ MultiplyMatrix.multiple3[state[i + 1] & 0xFF];
			}
			if (i % 4 == 3) {
				result[i] = MultiplyMatrix.multiple3[state[i - 3] & 0xFF] ^ state[i - 2] ^ state[i - 1]
						^ MultiplyMatrix.multiple2[state[i] & 0xFF];
			}
		}
		return result;
	}

	public int[] AddRoundKeys(int[] state, int[] roundKey) {
		int[] result = new int[16];
		for (int i = 0; i < result.length; i++) {
			result[i] = state[i] ^ roundKey[i];
		}
		return result;
	}

	public int[] revShiftRows(int[] state) {
		int[] result = new int[16];

		result[0] = state[0];
		result[1] = state[13];
		result[2] = state[10];
		result[3] = state[7];

		result[4] = state[4];
		result[5] = state[1];
		result[6] = state[14];
		result[7] = state[11];

		result[8] = state[8];
		result[9] = state[5];
		result[10] = state[2];
		result[11] = state[15];

		result[12] = state[12];
		result[13] = state[9];
		result[14] = state[6];
		result[15] = state[3];

		return result;
	}

	public int[] revSubBytes(int[] state) {
		int[] result = new int[16];
		for (int i = 0; i < state.length; i++) {
			result[i] = Sboxes.invSbox[state[i] & 0xFF];
		}
		return result;
	}

	public int[] revMixColumns(int[] state) {
		int[] result = new int[16];

		for (int i = 0; i < result.length; i++) {
			if (i % 4 == 0) {
				result[i] = MultiplyMatrix.multiple14[state[i] & 0xFF] ^ MultiplyMatrix.multiple11[state[i + 1] & 0xFF]
						^ MultiplyMatrix.multiple13[state[i + 2] & 0xFF]
						^ MultiplyMatrix.multiple9[state[i + 3] & 0xFF];
			}
			if (i % 4 == 1) {
				result[i] = MultiplyMatrix.multiple9[state[i - 1] & 0xFF] ^ MultiplyMatrix.multiple14[state[i] & 0xFF]
						^ MultiplyMatrix.multiple11[state[i + 1] & 0xFF]
						^ MultiplyMatrix.multiple13[state[i + 2] & 0xFF];
			}
			if (i % 4 == 2) {
				result[i] = MultiplyMatrix.multiple13[state[i - 2] & 0xFF]
						^ MultiplyMatrix.multiple9[state[i - 1] & 0xFF] ^ MultiplyMatrix.multiple14[state[i] & 0xFF]
						^ MultiplyMatrix.multiple11[state[i + 1] & 0xFF];
			}
			if (i % 4 == 3) {
				result[i] = MultiplyMatrix.multiple11[state[i - 3] & 0xFF]
						^ MultiplyMatrix.multiple13[state[i - 2] & 0xFF] ^ MultiplyMatrix.multiple9[state[i - 1] & 0xFF]
						^ MultiplyMatrix.multiple14[state[i] & 0xFF];
			}
		}

		return result;
	}

}
