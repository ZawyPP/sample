package sk.zawy.lahodnosti.control;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptData {

        public static final int DECRYPT_MODE=1;
        public static final int ENCRYPT_MODE=0;
        private static String AES="AES";

        /**Encrypt personally sensitive data
         * @param input String for encrypt
         * @param MODE constant for selection function
         * */
        public static String finalData(final int MODE, String input) {
            try {

                switch (MODE){
                    case ENCRYPT_MODE:
                        return encryptData(input);

                    case DECRYPT_MODE: //options off now
                        return decryptData(input);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("logData","ENCRYPT/DECRYPT error : " + e);
            }
            return "errorCrypt";
        }


        private static String encryptData(String input) throws Exception {
            String pass="";//TODO KEY;

            SecretKey secretKeySpec=new SecretKeySpec(pass.getBytes("UTF-8"),"AES");
            Cipher cipher= Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            byte[]encVal=cipher.doFinal(input.getBytes());
            String encryptedValue= Base64.encodeToString(encVal,Base64.DEFAULT);
            return encryptedValue;
        }


    private static String decryptData(String input) throws Exception {
        String pass="";//TODO KEY;

        SecretKey secretKeySpec=new SecretKeySpec(pass.getBytes("UTF-8"),"AES");
        Cipher cipher=Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[]decodedValue= Base64.decode(input,Base64.DEFAULT);
        byte[]decVal=cipher.doFinal(decodedValue);
        String decryptedValue=new String(decVal);
        return decryptedValue;
    }

}
