

import java.util.Scanner;


// ----------------------  Alireza Rashidi 96243097
class AES2 {

    public String byteMatrix[][];
    private String keyWordsMatrix[][];
    private final String RCi[] = {"01", "02", "04", "08", "10", "20", "40", "80", "1b", "36"};
    private final char [] singleHexNumbers = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

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

    private final int[][] mixColumnMatrix = {{2, 3, 1, 1}, {1, 2, 3, 1}, {1, 1, 2, 3}, {3, 1, 1, 2}};

    AES2() {
        byteMatrix = new String[4][4];

        keyWordsMatrix = new String[4][4];
    }

    public void setByteMatrix(String[] plainTextBytes) {  // done!

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                byteMatrix[j][i] = plainTextBytes[j + 4 * i];
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

    public void printMatrix(String[][] matrix) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private String[] circularShiftCol() {    // circular shift for last col in key-matrix.  (done!)

        String[] temp = new String[4];

        for (int i = 0; i < 4; i++) {
            temp[i] = keyWordsMatrix[i][3];
        }

        String tmp = temp[0];

        for (int i = 0; i < 3; i++) {
            temp[i] = temp[i + 1];
        }
        temp[3] = tmp;

        return temp;

    }

    private String replaceByte(String b) {     // done !!

        return lookupTable[Integer.parseInt(String.valueOf(b.charAt(0)), 16)][Integer.parseInt(String.valueOf(b.charAt(1)), 16)];
    }

    private String[] Gfunction(int round) {

        String[] tmp = circularShiftCol();


        for (int i = 0; i < 4; i++) {    // s-boxes..
            tmp[i] = replaceByte(tmp[i]);

        }

        tmp[0] = xorHex(tmp[0], RCi[round - 1]);   // xor with Rci[round]..

        return tmp;
    }

    private void keyGeneration(int round) {    // done !

        String[] gW = Gfunction(round);

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                if (i == 0) {
                    keyWordsMatrix[j][i] = xorHex(keyWordsMatrix[j][i], gW[j]);
                } else
                    keyWordsMatrix[j][i] = xorHex(keyWordsMatrix[j][i], keyWordsMatrix[j][(i + 3) % 4]);
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

    public void subBytes() {    // change bytes level.

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                byteMatrix[i][j] = replaceByte(byteMatrix[i][j]);
            }

        }
    }

    public void keyAddition() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                byteMatrix[i][j] = xorHex(byteMatrix[i][j], keyWordsMatrix[i][j]);
            }
        }
    }

    public void shiftRow() {    // done !
        // TODO shift row oprations ...

        for (int i = 0; i < 4; i++) {

            leftRotate(i);
        }
    }

    private void leftRotate(int d) {
        for (int j = 0; j < d; j++) {

            int i;
            String temp = byteMatrix[d][0];

            for (i = 0; i < byteMatrix[d].length - 1; i++)
                byteMatrix[d][i] = byteMatrix[d][i + 1];

            byteMatrix[d][i] = temp;

        }
    }

    private int[][] multiply(int inMatrix[][]) {  // ok!

        int res[][] = new int[mixColumnMatrix.length][inMatrix[0].length];
        int i, j, k;
        for (i = 0; i < res.length; i++) {

            for (j = 0; j < res[i].length; j++) {
                res[i][j] = 0;

                for (k = 0; k < inMatrix.length; k++) {
                    int resultMulti;

                    if (mixColumnMatrix[i][k] == 2) {

                        resultMulti = inMatrix[k][j] * 2;

                        if (resultMulti > 255) {
                            resultMulti ^= 27;
                            resultMulti &= 255;

                        }

                    } else if (mixColumnMatrix[i][k] == 3) {

                        resultMulti = inMatrix[k][j] * 2;
                        resultMulti ^= inMatrix[k][j];

                        if (resultMulti > 255) {
                            resultMulti ^= 27;
                            resultMulti &= 255;
                        }

                    } else
                        resultMulti = inMatrix[k][j];

                    res[i][j] ^= resultMulti;

                }

            }

        }
        return res;
    }

    public void mixColOp() {  // ok!

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

    public void AESEncFunc() {

        int round = 1;
        keyAddition();

        while (round <= 10) {

            subBytes();    // byte substitution
            shiftRow();

            if (round != 10)
                mixColOp();

            keyGeneration(round);

            keyAddition();

            round++;
        }

    }

    public void printChipherText() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(byteMatrix[j][i]);
            }

        }
    }

}


public class AESRun1{

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AES2 aes = new AES2();
        String initKey = scanner.next();

        String[] keyBytes = initKey.split("(?<=\\G.{" + 2 + "})");

        aes.setKeyWordsMatrix(keyBytes);

        String inPlainText = scanner.next();

        String[] plainTextBytes = inPlainText.split("(?<=\\G.{" + 2 + "})");

        aes.setByteMatrix(plainTextBytes);

        //-----------------------------------------------------------------------------------

        aes.AESEncFunc();
        aes.printChipherText();

    }

}
