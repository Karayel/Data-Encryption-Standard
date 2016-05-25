package DES;

import java.util.ArrayList;

/**
 * Useful Links --> http://www.inf.unideb.hu/~ahuszti/des_tables.pdf
 * http://page.math.tu-berlin.de/~kant/teaching/hess/krypto-ws2006/des.htm
 *
 * @author Karayel
 */
public class DES {

// <editor-fold desc="DES Final Variable">
//<editor-fold desc="Initial Permutatıon">  
    public static final int[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };
//</editor-fold>

//<editor-fold desc="Inverse Initial Permutation">
    public static final int[] IP_1 = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };
    //</editor-fold>  

//<editor-fold desc="Permutation Function">
    public static final int[] P = {
        16, 7, 20, 21, 29, 12, 28, 17,
        1, 15, 23, 26, 5, 18, 31, 10,
        2, 8, 24, 14, 32, 27, 3, 9,
        19, 13, 30, 6, 22, 11, 4, 25
    };

//</editor-fold>  
//<editor-fold desc="Expansion Permutation">
    public static final int[] E = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1

    };
//</editor-fold>

// <editor-fold desc="S-BOX">
    public static final byte[][] SBOX = {{
        14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
        0, 15, 7, 47, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
        4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
        15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
    }, {
        15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
        3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
        0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
        13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
    }, {
        10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
        13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
        13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
        1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
    }, {
        7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
        13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
        10, 6, 9, 0, 14, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
        3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
    }, {
        2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
        14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
        4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
        11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
    }, {
        12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
        10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
        9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
        4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
    }, {
        4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
        13, 0, 11, 7, 4, 9, 1, 10, 16, 3, 5, 12, 2, 15, 8, 6,
        1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
        6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
    }, {
        13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
        1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
        7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
        2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
    }
    };
// </editor-fold> 

// <editor-fold desc="Permuted Chooice">
    // <editor-fold desc="PC1">
    public static final byte[] PC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        //---------------------------//
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4

    };
// </editor-fold>

    // <editor-fold desc="PC2">
    public static final byte[] PC2 = {
        14, 17, 11, 24, 1, 5, 3, 28,
        15, 6, 21, 10, 23, 19, 12, 4,
        26, 8, 16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55, 30, 40,
        51, 45, 33, 48, 44, 49, 39, 56,
        34, 53, 46, 42, 50, 36, 29, 32
    };
// </editor-fold>
// </editor-fold>

// <editor-fold desc="Schedule of Left Shift">    
    private static final int[] shiftLeft = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
// </editor-fold>

// </editor-fold>
    public static void main(String[] args) {
        int[] plainBits = new int[64];
        int[] keyBits = new int[64];
        // Not include special characters
        textToBinary(plainBits, "melikeme");
        textToBinary(keyBits, "melikeme");
        ArrayList subKeys = subKeys(keyBits);
        ArrayList decSubKey = decryptionSubKeys(subKeys);
        System.out.println("Encryption");
        int[] cipherBits = DES(plainBits, subKeys);
        System.out.println("Decryption");
        DES(cipherBits, decSubKey);
    }

    /**
     *
     * DES Algorithms Steps
     *
     * @param plainBits
     * @param subKeys
     * @return
     */
    public static int[] DES(int[] plainBits, ArrayList subKeys) {
        //Initial Permutatation
        int[] initialPermutedText = initialPermutation(plainBits);
        //Left  Half Block
        int[] L = new int[32];
        System.arraycopy(initialPermutedText, 0, L, 0, 32);
        //Right  Half Block
        int[] R = new int[32];
        for (int i = 0; i < 32; i++) {
            R[i] = initialPermutedText[32 + i];
        }
        //Round Starts
        for (int i = 0; i < 16; i++) {
            // Call Feistel function
            int[] feistelFunctionOutput = feistelFunction(R, (int[]) subKeys.get(i));
            //Call Permutation function with using feistel function result
            int[] permutationFunctionOutput = permutationFunction(feistelFunctionOutput);
            // Copy the right half block to temp
            int[] temp = new int[32];
            System.arraycopy(R, 0, temp, 0, temp.length);
            // call the XOR function with using left half block and permutation function output.
            // so new right half block comes.
            R = xor(L, permutationFunctionOutput);
            // Left half block is the previous right half block.
            L = temp;
            System.out.print("SubKey " + i + ": ");
            printGivenArray((int[]) subKeys.get(i));
            System.out.print("R " + i + ": ");
            printGivenArray(R);
            System.out.print("L " + i + ": ");
            printGivenArray(L);
            System.out.println("");
        }
        // Call inverse permutation
        int[] finalOperation = inversePermutataion(mergeTwoArray(R, L));
        System.out.print("Result: ");
        printGivenArray(finalOperation);
        System.out.println("Result String: " + binaryToString(finalOperation));
        return finalOperation;
    }

    /**
     *
     * Convert binary value to String
     *
     * @param result
     * @return
     */
    public static String binaryToString(int[] result) {
        String res = "";
        for (int i = 0; i < result.length; i += 8) {
            //Take 8 bits for a single character
            int[] r = {result[i], result[i + 1], result[i + 2], result[i + 3],
                result[i + 4], result[i + 5], result[i + 6], result[i + 7]};
            int characterASCI = binaryToInt(r);
            res += Character.toString((char) characterASCI);
        }
        return res;
    }

    /**
     *
     * Initial Permutation
     *
     * @param plainText
     * @return
     */
    public static int[] initialPermutation(int[] plainText) {
        int[] initialPermutation = new int[64];
        for (int i = 0; i < 64; i++) {
            initialPermutation[i] = plainText[IP[i] - 1];
        }
        return initialPermutation;
    }

    /**
     *
     * Apply the final permutation
     *
     * @param finalText
     * @return
     */
    public static int[] inversePermutataion(int[] finalText) {
        int[] finalResult = new int[64];
        for (int i = 0; i < 64; i++) {
            finalResult[i] = finalText[IP_1[i] - 1];
        }
        System.out.print("Inverse Permutation : ");
        printGivenArray(finalResult);
        return finalResult;
    }

    /**
     *
     * Apply Feistel Function
     *
     * @param rightHalfBlock
     * @param subKey
     * @return
     */
    public static int[] feistelFunction(int[] rightHalfBlock, int[] subKey) {
        // expande the right half block and then xor it with its subKey 
        int[] XORed = xor(expansionRightHalf(rightHalfBlock), subKey);
        System.out.print("XOR :");
        printGivenArray(XORed);
        int[] result = new int[32];
        int sboxCount = 0;
        int resultCount = 0;
        for (int i = 0; i < XORed.length; i += 6) {
            int[] outer = {XORed[i], XORed[i + 5]};
            int[] inner = {XORed[i + 1], XORed[i + 2], XORed[i + 3], XORed[i + 4]};
            // formula that finding sbox Position
            // sboxPosition = 16 * outerDecimal value + InnerDecimal value 
            // Example : 110010
            // Inner Binary = 1001  Decimal Value = 9 
            // Outter Binary = 10   Decimal Value = 2
            // sboxPosition = 16 * 2 + 9 = 41
            int sboxPosition = 16 * binaryToInt(outer) + binaryToInt(inner);
            int sboxValue = SBOX[sboxCount][sboxPosition];
            //Conver sboxValue to binary
            int[] sboxValueBinary = toBinary(sboxValue, 4);

            // add sbox binary value to result array
            result[resultCount] = sboxValueBinary[0];
            result[resultCount + 1] = sboxValueBinary[1];
            result[resultCount + 2] = sboxValueBinary[2];
            result[resultCount + 3] = sboxValueBinary[3];
            resultCount += 4;
            sboxCount++;
        }
        System.out.print("S-Box :");
        printGivenArray(result);
        return result;
    }

    /**
     *
     * Apply Permutation Function with using Feistel Function Output
     *
     * @param feistelFunctionOutput
     * @return
     */
    public static int[] permutationFunction(int[] feistelFunctionOutput) {
        int[] permutationFunctionResult = new int[32];
        for (int i = 0; i < 32; i++) {
            permutationFunctionResult[i] = feistelFunctionOutput[P[i] - 1];
        }
        System.out.print("Permutation function : ");
        printGivenArray(permutationFunctionResult);
        return permutationFunctionResult;
    }

    //<editor-fold desc="String to Binary">
    /**
     *
     * Convert string to binary numerical illustration
     *
     * @param text
     * @param str
     */
    public static void textToBinary(int[] text, String str) {
        byte[] bytecode = str.getBytes();
        int count = 0;
        for (int i = 0; i < bytecode.length; i++) {
            int[] binaryCharacter = toBinary(bytecode[i], 8);
            unionBinaryCharacter(text, binaryCharacter, count);
            count += 8;
        }
    }

    /**
     *
     * Print the given array
     *
     * @param arr
     */
    public static void printGivenArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println("");
    }

    /**
     *
     * Converting ASCII code to Binary number
     *
     * @param asciNum
     * @return
     */
    public static int[] toBinary(int asciNum, int bitSize) {
        int[] binary = new int[bitSize];
        for (int i = bitSize - 1; i >= 0; i--) {
            binary[i] = asciNum % 2;
            asciNum = asciNum / 2;
        }
        return binary;
    }

    /**
     *
     * text[0-7] = toBinary(asciCharacter1),<br> text[8-15] =
     * toBinary(asciCharacter2),<br>
     * text[16-23] = toBinary(asciCharacter3),<br> text[24-31] =
     * toBinary(asciCharacter4),<br>
     * text[32-39] = toBinary(asciCharacter5),<br> text[40-47] =
     * toBinary(asciCharacter6),<br>
     * text[48-55] = toBinary(asciCharacter7),<br> text[56-63] =
     * toBinary(asciCharacter8)<br>
     *
     * @param text
     * @param arr
     * @param count
     */
    public static void unionBinaryCharacter(int[] text, int[] arr, int count) {
        for (int i = 0; i < arr.length; i++) {
            text[count] = arr[i];
            count++;
        }
    }

    //</editor-fold>
    /**
     * *
     *
     * @param binary
     * @return
     */
    public static int binaryToInt(int[] binary) {
        int result = 0;
        int power = binary.length - 1;
        for (int i = 0; i < binary.length; i++) {
            result += Math.pow(2, power) * binary[i];
            power--;
        }
        return result;
    }

    /**
     * All sub keys for encryption
     *
     * @param keyBits
     * @return
     */
    public static ArrayList subKeys(int[] keyBits) {
        ArrayList subKeys = new ArrayList();
        int[] pc1 = PC1(keyBits);
        // left 28 bit
        int[] C = new int[28];
        for (int i = 0; i < 28; i++) {
            C[i] = pc1[i];
        }
        // right 28 bit
        int[] D = new int[28];
        for (int i = 0; i < 28; i++) {
            D[i] = pc1[28 + i];
        }
        for (int i = 0; i < 16; i++) {
            // shift right and left block with given shift number
            // shiftLeft arrays keep this shifting number.
            C = leftShift(C, shiftLeft[i]);
            D = leftShift(D, shiftLeft[i]);
            // before pc2 to merge C and D
            int[] CD = mergeTwoArray(C, D);
            // Use Permutation Chooice 2
            int[] subKey = PC2(CD);
            // Add subkey to subkey pool
            subKeys.add(subKey);
        }
        return subKeys;
    }

    /**
     * *
     * All sub keys for decryption
     *
     * @param subKeys
     * @return
     */
    public static ArrayList decryptionSubKeys(ArrayList subKeys) {
        // encryption key = k0,k1,k2,k3,k4,k5,...,k15  
        // decryption key = k15,k14,k13,k12,.....,k0
        ArrayList decSubKey = new ArrayList();
        for (int i = 15; i >= 0; i--) {
            decSubKey.add((int[]) subKeys.get(i));
        }
        return decSubKey;
    }

    /**
     *
     * Expand right half block Right half block is 32 bits. After the expansion,
     * it will be 48 bits.
     *
     * @param r
     * @return
     */
    public static int[] expansionRightHalf(int[] r) {
        int[] expandedRightHalf = new int[48];
        for (int i = 0; i < 48; i++) {
            expandedRightHalf[i] = r[E[i] - 1];
        }
        System.out.print("Expanded Right :");
        printGivenArray(expandedRightHalf);
        return expandedRightHalf;
    }

    /**
     *
     * Permutation Choice 1
     *
     * @param keyBits
     * @return
     */
    public static int[] PC1(int[] keyBits) {
        int[] pc1 = new int[56];
        for (int i = 0; i < 56; i++) {
            pc1[i] = keyBits[PC1[i] - 1];
        }
        return pc1;
    }

    /**
     *
     * Permutation Choice 2 for Sub key generation
     *
     * @param CD
     * @return
     */
    public static int[] PC2(int[] CD) {
        int[] pc1 = new int[48];
        for (int i = 0; i < 48; i++) {
            pc1[i] = CD[PC2[i] - 1];
        }
        return pc1;
    }

    /**
     * *
     * Merge left and right halves, C and D, for 56 bits.
     *
     * @param c
     * @param d
     * @return
     */
    public static int[] mergeTwoArray(int[] c, int[] d) {
        int[] result = new int[c.length + d.length];
        for (int i = 0; i < c.length; i++) {
            result[i] = c[i];
        }
        int count = c.length;
        for (int i = 0; i < d.length; i++) {
            result[count] = d[i];
            count++;
        }
        return result;
    }

    /**
     *
     * Shifting left with given number n
     *
     * @param bits
     * @param n
     * @return
     */
    private static int[] leftShift(int[] bits, int n) {
        int answer[] = new int[bits.length];
        for (int i = 0; i < bits.length; i++) {
            answer[i] = bits[i];
        }
        for (int i = 0; i < n; i++) {
            int temp = answer[0];
            for (int j = 0; j < bits.length - 1; j++) {
                answer[j] = answer[j + 1];
            }
            answer[bits.length - 1] = temp;
        }
        return answer;
    }

    /**
     * *
     * XOR function for two int arrays
     *
     * @param r
     * @param l
     * @return
     */
    private static int[] xor(int[] r, int[] l) {
        int size = r.length;
        int result[] = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = r[i] ^ l[i];
        }
        System.out.print("XOR : ");
        printGivenArray(result);
        return result;
    }
}
