package digital;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class VerSig {
	public static void main(String[] args) {
		try {
			File f= new File("F:\\publickey.txt");
			FileInputStream keyfis = new FileInputStream(f);
			byte[] encKey = new byte[(int) f.length()];  
			keyfis.read(encKey);
			keyfis.close();
			
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
			
			FileInputStream sigfis = new FileInputStream("F:\\signature.txt");
			byte[] sigToVerify = new byte[sigfis.available()]; 
			sigfis.read(sigToVerify);
			sigfis.close();
			
			Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
			sig.initVerify(pubKey);
			FileInputStream datafis = new FileInputStream("F:\\1.txt");
			BufferedInputStream bufin = new BufferedInputStream(datafis);

			byte[] buffer = new byte[1024];
			int len;
			while (bufin.available() != 0) {
			    len = bufin.read(buffer);
			    sig.update(buffer, 0, len);
			};

			bufin.close();
			
			boolean verifies = sig.verify(sigToVerify);
			System.out.println("signature verifies: " + verifies);
			
	        } catch (Exception e) {
	            System.err.println("Caught exception " + e.toString());
	        }
	}
	
}
