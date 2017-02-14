package Aes;

public class KeyExpansion {
	int[][] roundKeys = new int[44][4]; // key + 10 cali round key
	
	public int[][] keyExpand(int[] key) {
		
		// keys gadatana roundkeys pirvel 16 baitshi
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				roundKeys[i][j] = key[(i * 4) + j];
			}
		}
		
		for (int i = 4; i < 44; i++) {
			for (int j = 0; j < 4; j++) {
				roundKeys[i][j] = roundKeys[i-1][j];
			}
			if (i % 4 == 0) {
				rotWord(i);
				subWord(i);
				rconWord(i);
			}
			for (int j = 0; j < 4; j++) {
				roundKeys[i][j] = roundKeys[i][j] ^ roundKeys[i-4][j];
			}
		}
		return roundKeys;
	}

	// svetshi pirveli baiti gadaitanos boloshi
	private void rotWord(int i) {
		int temp = roundKeys[i][0];
		roundKeys[i][0] = roundKeys[i][1];
		roundKeys[i][1] = roundKeys[i][2];
		roundKeys[i][2] = roundKeys[i][3];
		roundKeys[i][3] = temp;
	}
	
	// sbox-idan shesabamis indexebze rac weria isini mogvaqvs da vwert svetshi
	private void subWord(int i) {
		for (int j = 0; j < 4; j++) {
			int index = roundKeys[i][j];
			roundKeys[i][j] = Sboxes.sbox[index & 0xFF];
		}
	}
	
	// rcon-idan i/4 indexsze racaa imas vqsoravt svetis pirvel elementze
	private void rconWord(int i) {
		int index = i / 4;
		roundKeys[i][0] = roundKeys[i][0] ^ Rcon.rcon[index];
	}
}
