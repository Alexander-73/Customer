/*
 * Copyright 2013 timovaananen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package customerproject.customerutilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author timovaananen
 */
public class Utilities {
    
    
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
	 
    public static String MD5(String text) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
        
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    public static String SHA256(String email, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        String text = email+"."+password;
        MessageDigest md = DigestUtils.getSha256Digest();
     
        byte[] sha256hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha256hash = md.digest();
        
        //return Base64.encodeBase64String(sha256hash);
        return Base64.encodeBase64URLSafeString(sha256hash);
        //String hex = convertToHex(sha256hash);
       
    }

    
}
