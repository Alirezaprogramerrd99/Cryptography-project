

import java.util.Scanner;

// -------------------------- Alireza Rashidi 96243097

class DecAES {

    public String byteMatrix[][];
    private String keyWordsMatrix[][];
    private final String RCi[] = {"01", "02", "04", "08", "10", "20", "40", "80", "1b", "36"};
    private final char [] singleHexNumbers = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    public DecAES() {
        byteMatrix = new String[4][4];
        keyWordsMatrix = new String[4][44];
    }

    private final int[][] invMixColumnMatrix = {{14, 11, 13, 9}, {9, 14, 11, 13}, {13, 9, 14, 11}, {11, 13, 9, 14}};

    public static String[][] invLookupTable = {
            {"52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb"},
            {"7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb"},
            {"54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e"},
            {"08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25"},
            {"72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92"},
            {"6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84"},
            {"90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06"},
            {"d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b"},
            {"3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73"},
            {"96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e"},
            {"47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b"},
            {"fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4"},
            {"1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f"},
            {"60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef"},
            {"a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61"},
            {"17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"}};


    private static final String[][] lookupTable = {
            {"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
            {"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
            {"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
            {"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
            {"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
            {"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
            {"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
            {"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
            {"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
            {"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
            {"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
            {"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
            {"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
            {"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
            {"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
            {"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"}};


    public void setByteMatrix(String[] chiperTextBytes) {  // done!

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                byteMatrix[j][i] = chiperTextBytes[j + 4 * i];
            }
        }
    }

    public void setKeyWordsMatrix(String[] keyBytes) {    // done!

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                keyWordsMatrix[j][i] = keyBytes[j + 4 * i];
            }
        }

    }

    private String[] circularShiftCol(int col) {    // circular shift for last col in key-matrix.  (done!)

        String[] temp = new String[4];

        for (int i = 0; i < 4; i++) {
            temp[i] = keyWordsMatrix[i][col];
        }

        String tmp = temp[0];

        for (int i = 0; i < 3; i++) {
            temp[i] = temp[i + 1];
        }
        temp[3] = tmp;

        return temp;

    }

    private String[] Gfunction(int round, int col) {

        String[] tmp = circularShiftCol(col);

        for (int i = 0; i < 4; i++) {    // s-boxes..
            tmp[i] = replaceByte(tmp[i]);

        }

        tmp[0] = xorHex(tmp[0], RCi[round - 1]);   // xor with Rci[round]..

        return tmp;
    }

    private void allRoundsKeyGeneration() {    // this method generates all sub-keys for all rounds.

        int j = 0;

        for (int i = 1; i <= 10; i++) {

            String[] gW = Gfunction(i, 4 * i - 1);   // for each round we use this function.

            for (int k = 0; k < 4; k++) {   // for multiply 4 col numbers.
                keyWordsMatrix[k][4 * i] = xorHex(keyWordsMatrix[k][4 * (i - 1)], gW[k]);
            }

            for (j = 1; j <= 3; j++) {

                for (int h = 0; h < 4; h++) {

                    keyWordsMatrix[h][4 * i + j] = xorHex(keyWordsMatrix[h][(4 * i) + j - 1], keyWordsMatrix[h][4 * (i - 1) + j]);
                }

            }

        }

    }

    private static int fromHexToDec(char c) {


        if (c >= 97 && c <= 102) {
            return c - 97 + 10;
        }


        if (c >= 65 && c <= 70) {
            return c - 65 + 10;
        }


        if (c >= 48 && c <= 57) {
            return c - 48;
        }


        return -1;
    }

    private String xorHex(String byte1, String byte2) {   // this function xors two string-hex values..

        char[] chars = new char[byte1.length()];
        int current = 0;

        while (current < chars.length){
            chars[current] = toHexConvert(xorInt(fromHexToDec(byte1.charAt(current)), fromHexToDec(byte2.charAt(current))));
            current++;
        }

        String hexNumber = String.copyValueOf(chars);

        return hexNumber;
    }

    private char toHexConvert(int nybble) {

        char ch = singleHexNumbers[nybble];
        return ch;
    }


    private int xorInt(int number1, int number2) {

        return number1 ^ number2;
    }

    private void keyAddition(int round) {


        int diff = -(4 * (round - 1));
        int startInv = keyWordsMatrix[0].length - 1;
        String subKeyMatrix[][] = new String[4][4];
        int index = 0;

        // extract sub-matrix
        for (int i = startInv + diff; i >= startInv - 3 + diff; i--) {

            for (int j = 0; j < 4; j++) {

                subKeyMatrix[j][subKeyMatrix.length - index - 1] = keyWordsMatrix[j][i];
            }
            index++;
        }

//        System.out.println("round " + round + " subKey: ");
//        printMatrix(subKeyMatrix);
//        System.out.println();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                byteMatrix[i][j] = xorHex(byteMatrix[i][j], subKeyMatrix[i][j]);
            }
        }
    }

    private String replaceByte(String b) {     // done !!

        return lookupTable[Integer.parseInt(String.valueOf(b.charAt(0)), 16)][Integer.parseInt(String.valueOf(b.charAt(1)), 16)];
    }

    public void printMatrix(String[][] matrix) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private String invReplaceByte(String b) {     // done !!

        return invLookupTable[Integer.parseInt(String.valueOf(b.charAt(0)), 16)][Integer.parseInt(String.valueOf(b.charAt(1)), 16)];
    }

    public void invSubBytes() {    // change bytes level.

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                byteMatrix[i][j] = invReplaceByte(byteMatrix[i][j]);
            }

        }
    }

    public void invShiftRow() {    // done !

        for (int i = 0; i < 4; i++) {

            rightRotate(i);     // rotate right operation.
        }
    }

    private void rightRotate(int d) {
        for (int j = 0; j < d; j++) {

            int i;
            String temp = byteMatrix[d][byteMatrix[d].length - 1];

            for (i = byteMatrix[d].length - 1; i > 0; i--)
                byteMatrix[d][i] = byteMatrix[d][i - 1];

            byteMatrix[d][i] = temp;

        }
    }

    private int modularMulti(int value) {
        value = value * 2;

        if (value > 255) {
            value ^= 27;     // xor with 1b
            value &= 255;    // masking last 1.
        }

        return value;
    }

    private int[][] multiply(int inMatrix[][]) {  // invMix col multiply..

        int res[][] = new int[invMixColumnMatrix.length][inMatrix[0].length];
        int i, j, k;
        for (i = 0; i < res.length; i++) {

            for (j = 0; j < res[i].length; j++) {
                res[i][j] = 0;

                for (k = 0; k < inMatrix.length; k++) {
                    int resultMulti;

                    if (invMixColumnMatrix[i][k] == 9) {

                        resultMulti = modularMulti(modularMulti(modularMulti(inMatrix[k][j]))) ^ inMatrix[k][j];


                    } else if (invMixColumnMatrix[i][k] == 11) {

                        resultMulti = modularMulti(modularMulti(modularMulti(inMatrix[k][j])) ^ inMatrix[k][j]) ^ inMatrix[k][j];


                    } else if (invMixColumnMatrix[i][k] == 13) {

                        resultMulti = modularMulti(modularMulti(modularMulti(inMatrix[k][j]) ^ inMatrix[k][j])) ^ inMatrix[k][j];


                    } else {

                        resultMulti = modularMulti(modularMulti(modularMulti(inMatrix[k][j]) ^ inMatrix[k][j]) ^ inMatrix[k][j]);

                    }

                    res[i][j] ^= resultMulti;

                }

            }

        }
        return res;
    }

    private void invMixColOp() {  // ok!

        int tmp[][] = new int[4][1];

        String zero = "";

        for (int col = 0; col < byteMatrix.length; col++) {

            for (int row = 0; row < byteMatrix.length; row++) {
                tmp[row][0] = Integer.parseInt(byteMatrix[row][col], 16);
            }

            tmp = multiply(tmp);

            for (int row = 0; row < byteMatrix.length; row++) {

                if (tmp[row][0] < 16)
                    zero = "0";

                byteMatrix[row][col] = zero + Integer.toHexString(tmp[row][0]);
                zero = "";
            }
        }
    }

    public void AESDecFunc() {

        allRoundsKeyGeneration();
        int round = 1;

        while (round <= 10){

            keyAddition(round);

            if ( round != 1)
                invMixColOp();

            invShiftRow();
            invSubBytes();

            round++;
        }

        keyAddition(round);    // final key-addition.

    }

    public void printPlainText() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(byteMatrix[j][i]);
            }

        }
    }
}

public class AESRun2{

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DecAES decAES = new DecAES();

        String initKey = scanner.next();

        String[] keyBytes = initKey.split("(?<=\\G.{" + 2 + "})");

        decAES.setKeyWordsMatrix(keyBytes);

        String inPlainText = scanner.next();

        String[] plainTextBytes = inPlainText.split("(?<=\\G.{" + 2 + "})");

        decAES.setByteMatrix(plainTextBytes);

        //-----------------------------------------------------------------------------------------------
        decAES.AESDecFunc();
        decAES.printPlainText();

    }
}
