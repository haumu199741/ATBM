package digital;

import java.io.*;
import java.security.*;

class GenSig {

    public static void main(String[] args) {

        /* Generate a DSA signature */
    	try{
        	KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        	SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        	keyGen.initialize(1024, random);
        	KeyPair pair = keyGen.generateKeyPair();
        	PrivateKey priv = pair.getPrivate();
        	PublicKey pub = pair.getPublic();
        	
        	Signature dsa = Signature.getInstance("SHA1withDSA", "SUN"); 
        	dsa.initSign(priv);
        	FileInputStream fis = new FileInputStream("F:\\1.txt");
        	BufferedInputStream bufin = new BufferedInputStream(fis);
        	byte[] buffer = new byte[1024];
        	int len;
        	while ((len = bufin.read(buffer)) >= 0) {
        	    dsa.update(buffer, 0, len);
        	};
        	bufin.close();
        	byte[] realSig = dsa.sign();
        	
        	//save signature
        	FileOutputStream sigfos = new FileOutputStream("F:\\signature.txt");
        	sigfos.write(realSig);
        	sigfos.close();
        	
        	//save public key
        	
        	byte[] key = pub.getEncoded();
        	FileOutputStream keyfos = new FileOutputStream("F:\\publickey.txt");
        	keyfos.write(key);
        	keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
}