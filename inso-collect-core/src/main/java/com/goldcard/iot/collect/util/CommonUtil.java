package com.goldcard.iot.collect.util;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommonUtil {
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 *            文件路径
	 * @author 2005
	 * @return true 存在 false 不存在
	 */
	public static boolean fileExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 16进制字符串转byte数组
	 * 
	 * @param hex
	 *            16进制字符串
	 * @return byte数组
	 * @author 2005
	 */
	public static byte[] hex2Byte(String hex) {
		int len = hex.length() / 2;
		byte[] bs = new byte[len];
		int step = 0;
		for (int i = 0; i < hex.length(); i += 2) {
			bs[step] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			step++;
		}
		return bs;
	}

	/**
	 * byte数组转16进制字符串
	 * 
	 * @param bs
	 *            byte数组
	 * @return 16进制字符串
	 * @author 2005
	 */
	public static String byte2Hex(byte[] bytes) {
		char[] buf = new char[bytes.length * 2];
		int index = 0;
		for (byte b : bytes) {
			buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
			buf[index++] = HEX_CHAR[b & 0xf];
		}
		return new String(buf);
	}

	public static Integer byte2Dec(byte[] bs) {
		return Integer.parseInt(byte2Hex(bs), 16);
	}

	/**
	 * 字符串转ACSII
	 */
	public static byte[] string2byte(String source) {
		byte[] bs = null;
		try {
			bs = source.getBytes("utf-8");
		} catch (Exception e) {

		}
		return bs;
	}

	public static String dec2Binary(int dec, int len) {
		String str = "";
		List<String> sList = new ArrayList<String>();

		boolean isRun = true;

		while (isRun) {

			int mod = dec % 2;
			sList.add(String.valueOf(mod));
			dec = dec / 2;
			if (dec == 0)
				isRun = false;
			else if (dec / 2 == 0 && dec % 2 == 1) {
				isRun = false;
				mod = dec % 2;
				sList.add(String.valueOf(mod));
			}
		}

		StringBuilder sBuilder = new StringBuilder("");
		for (int i = sList.size() - 1; i >= 0; i--) {
			sBuilder.append(sList.get(i));
		}

		str = sBuilder.toString();

		return StringUtils.leftPad(str, len, '0');
	}

	public static boolean isBinaryNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-1]*");
		return pattern.matcher(str).matches();
	}

	public static Long binary2dec(String source) {
		Long result = (long) 0;
		if (StringUtils.isBlank(source)) {
			return result;
		}
		if (!isBinaryNumeric(source)) {
			return result;
		}
		char[] charArray = source.toCharArray();
		int len = charArray.length - 1;
		for (char c : charArray) {
			Integer integer = (int) (Integer.parseInt(String.valueOf(c)) * Math.pow(2, len));
			result += integer;
			len--;
		}

		return result;
	}

	public static String dec2Binary(int dec) {
		return dec2Binary(dec, 8);
	}

	public static Map<String, Object> stream2Map(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String strJson = sb.toString();
		Map<String, Object> inMap = null;// JSON.parseObject(strJson);
		return inMap;
	}

	public static int crc_ccitt(byte[] bs) {
		int crc = 0x00; // initial value
		int polynomial = 0x1021;
		for (int index = 0; index < bs.length; index++) {
			byte b = bs[index];
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		return crc;
	}

	public static int cs(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		String tmp = null;
		int sum = 0;
		for (byte b : bytes) {
			// 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
			tmp = Integer.toHexString(0xFF & b);
			if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
			{
				tmp = "0" + tmp;
			}
			sum = sum + Integer.parseInt(tmp.toString(), 16);
			sb.append(tmp);
		}
		sum = sum % 256;
		return sum;
	}

	public static String dec2Hex(int dec) {

		String string = Integer.toHexString(dec).toUpperCase();
		int mode = string.length() % 2;
		string = StringUtils.leftPad(string, string.length() + mode, '0');
		return string;

	}

	public static byte[] short2Byte(short x) {
		byte[] bb = new byte[2];
		bb[0] = ((byte) (x >> 8));
		bb[1] = ((byte) (x >> 0));

		return bb;
	}

	/**
	 * 
	 * @param data1
	 * @param data2
	 * @return data1 与 data2拼接的结果
	 */
	public static byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;

	}

	public static String stringToAsciiHex(String value) {
		StringBuilder sb = new StringBuilder();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			sb.append(dec2Hex((int) chars[i]));
		}
		return sb.toString();
	}

	public static String asciiHexToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = getStringForAscii(value);
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i], 16));
		}
		return sbu.toString().trim();
	}

	private static String[] getStringForAscii(String str) {
		int m = str.length() / 2;
		if (m * 2 < str.length()) {
			m++;
		}
		String[] strs = new String[m];
		int j = 0;
		for (int i = 0; i < str.length(); i++) {
			if (i % 2 == 0) {// 每隔两个
				strs[j] = "" + str.charAt(i);
			} else {
				strs[j] = strs[j] + str.charAt(i);
				j++;
			}
		}
		return strs;
	}

}
