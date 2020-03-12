package com.goldcard.iot.collect.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

public class DesUtil {
	private static final String DES = "DES";
	private static final String CODE = "523593D5B8DC1676";
	private static final String KEY = "C83E7386FA4DB629";

	public static byte[] decrypt(byte[] data) {
		return decrypt(data, KEY);
	}

	public static byte[] encrypt(byte[] data) {
		byte[] keys = CommonUtil.hex2Byte(KEY);
		if (data.length > 8) {
			byte[] data_jia = encrypt(Arrays.copyOfRange(data, 0, 8), keys);
			byte[] dest = new byte[data.length];
			System.arraycopy(data_jia, 0, dest, 0, 8);
			System.arraycopy(data, 8, dest, 8, data.length - 8);
			return dest;
		}
		byte[] data_jia = encrypt(data, keys);

		byte[] dest = Arrays.copyOfRange(data_jia, 0, 8);
		return dest;
	}

	private static byte[] decrypt(byte[] data, String key) {
		byte[] keys = CommonUtil.hex2Byte(key);

		byte[] datas = CommonUtil.hex2Byte(CommonUtil.byte2Hex(data) + CODE);

		byte[] datas_jie = decrypt(datas, keys);

		return datas_jie;
	}

	private static byte[] encrypt(byte[] data, byte[] key) {
		SecureRandom sr = new SecureRandom();

		byte[] b = null;
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance(DES);

			cipher.init(1, securekey, sr);

			b = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	private static byte[] decrypt(byte[] data, byte[] key) {
		SecureRandom sr = new SecureRandom();

		byte[] b = null;
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance(DES);

			cipher.init(2, securekey, sr);

			b = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
