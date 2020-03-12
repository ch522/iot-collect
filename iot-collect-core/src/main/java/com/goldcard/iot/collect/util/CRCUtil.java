package com.goldcard.iot.collect.util;

import org.apache.commons.lang.StringUtils;

public class CRCUtil {
	public static byte[] getCRC(byte[] data) {
		int crc = 0;
		for (int i = 0; i < data.length; i++) {
			crc = data[i] << 8 ^ crc;
			for (int j = 0; j < 8; j++) {
				if ((crc & 0x8000) != 0) {
					crc -= 32768;
					crc = crc << 1 ^ 0x1021;
				} else {
					crc <<= 1;
				}
			}
		}
		String LO = Integer.toHexString(crc & 0xFF);
		if (LO.length() == 1) {
			LO = "0" + LO;
		}
		String HI = Integer.toHexString(crc >> 8 & 0xFF);
		if (HI.length() == 1) {
			HI = "0" + HI;
		}
		return CommonUtil.hex2Byte(StringUtils.upperCase(HI + LO));
	}

	public static byte[] getCRC_8005(byte[] data) {
		int wCRC = 65535;
		for (int k = 0; k < data.length; k++) {
			wCRC ^= 0xFF & data[k];
			for (int i = 0; i < 8; i++) {
				if ((wCRC & 0x1) > 0) {
					wCRC = wCRC >> 1 ^ 0xA001;
				} else {
					wCRC >>= 1;
				}
			}
		}
		wCRC = wCRC << 8 | wCRC >> 8 & 0xFF;
		String LO = Integer.toHexString(wCRC & 0xFF);
		if (LO.length() == 1) {
			LO = "0" + LO;
		}
		String HI = Integer.toHexString(wCRC >> 8 & 0xFF);
		if (HI.length() == 1) {
			HI = "0" + HI;
		}
		return CommonUtil.hex2Byte(StringUtils.upperCase(HI + LO));
	}

	public static byte[] CRC_CCITT(byte[] bytes) {
		int crc = 0;
		int polynomial = 4129;
		crc = 65535;
		for (int index = 0; index < bytes.length; index++) {
			byte b = bytes[index];
			for (int i = 0; i < 8; i++) {
				boolean bit = (b >> 7 - i & 0x1) == 1;
				boolean c15 = (crc >> 15 & 0x1) == 1;
				crc <<= 1;
				if ((c15 ^ bit)) {
					crc ^= polynomial;
				}
			}
		}
		crc &= 0xFFFF;
		return CommonUtil.hex2Byte(Integer.toHexString(crc));
	}

	public static byte[] autoCRC(byte[] message) {
		byte[] crc = getCRC(message);
		byte[] dest = new byte[message.length + crc.length];

		System.arraycopy(message, 0, dest, 0, message.length);
		System.arraycopy(crc, 0, dest, message.length, crc.length);

		return dest;
	}

	public static byte[] autoCRCCCIT(byte[] message) {
		byte[] crc = calcCRCCCIT(message);
		byte[] dest = new byte[message.length + crc.length];
		System.arraycopy(message, 0, dest, 0, message.length);
		System.arraycopy(crc, 0, dest, message.length, crc.length);

		return dest;
	}

	public static byte[] calcCRCCCIT(byte[] data) {
		short s = (short) calcCRC16(data);
		return CommonUtil.short2Byte(s);
	}

	public static int calcCRC16(byte[] data) {
		int wCRC = 65535;
		for (int k = 0; k < data.length; k++) {
			wCRC ^= 0xFF & data[k];
			for (int i = 0; i < 8; i++) {
				if ((wCRC & 0x1) > 0) {
					wCRC = wCRC >> 1 ^ 0xA001;
				} else {
					wCRC >>= 1;
				}
			}
		}
		wCRC = wCRC << 8 | wCRC >> 8 & 0xFF;
		return wCRC;
	}

	public static final String evalCRC16(byte[] data) {
		int crc = 65535;
		for (int i = 0; i < data.length; i++) {
			crc = data[i] << 8 ^ crc;
			for (int j = 0; j < 8; j++) {
				if ((crc & 0x8000) != 0) {
					crc = crc << 1 ^ 0x1021;
				} else {
					crc <<= 1;
				}
			}
		}
		return Integer.toHexString((crc ^ 0xFFFF) & 0xFFFF);
	}
}
