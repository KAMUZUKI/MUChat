package com.mu.client.chat_room.tools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.mu.config.Config;

public class MessageSave {
	private DataInputStream dis;
	private OutputStreamWriter osw;
	private List<String> list = new LinkedList<>();
	
	public List<String> loadMessage(String fileName) {
		try {
			File path = new File(Config.MESSAGE_PATH + fileName + ".txt");
			System.out.println("loadMessage : " + path);
			
			//�����ڷ��ؿ�
			if (!path.exists()) return null;
			
			dis = new DataInputStream(new FileInputStream(path));
			
			BufferedReader br  = new BufferedReader(new InputStreamReader(dis));
			
			String count;
			String tmpString = "";
			int line = 1;
		    while((count = br.readLine()) != null){
		        tmpString += count;
		        if (line%2==0) {
					list.add(tmpString + "\r\n");
					tmpString="";
				}else {
					tmpString += "\r\n";
				}
		        line++;
		    }
		} catch (Exception e) {
			try {
				dis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return list;
	}
	
	//˳���ȡ�������һ��
	public String loadLastMessage(String fileName) {
		String count = "";
		String str = "";
		try {
			File path = new File(Config.MESSAGE_PATH + fileName + ".txt");
			System.out.println("loadMessage : " + path);
			
			//�����ڷ��ؿ�
			if (!path.exists()) return null;
			
			dis = new DataInputStream(new FileInputStream(path));
			
			BufferedReader br  = new BufferedReader(new InputStreamReader(dis));
			
		    while((count = br.readLine()) != null){
		    	if (!count.equals("")) {
		    		str = count;
				}
		    }
		} catch (Exception e) {
			try {
				dis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return str;
	}
	
	public void saveMessage(String message,String fileName) {
		File path = new File(Config.MESSAGE_PATH + fileName + ".txt");
		try {
			osw = new OutputStreamWriter(new FileOutputStream(path,true));
			//�����ڴ����ļ�
			if (!path.exists()) path.createNewFile();
			System.out.println(path);
			osw.append(message+"\r\n");
			osw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean existMessage(String name) {
		File path = new File(Config.MESSAGE_PATH + name + ".txt");
		if (path.exists()) {
			return true;
		}
		return false;
	}
	
	public String getLastMessage(String name,int row) {
		String path = Config.MESSAGE_PATH + name  + ".txt";
		String lastMessage = "";
		if (existMessage(name)) {
			try {
				lastMessage = readLastRows(path, Charset.forName("gb2312"), row);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			return "����������Ϣ";
		}
		return lastMessage;
	}
	
	//�ļ�ָ�� ��ȡ�����
	public String readLastRows(String filename, Charset charset, int rows) throws IOException {
        charset = charset == null ? Charset.defaultCharset() : charset;
        byte[] lineSeparator = System.getProperty("line.separator").getBytes();
        try (RandomAccessFile rf = new RandomAccessFile(filename, "r")) {
            // ÿ�ζ�ȡ���ֽ���Ҫ��ϵͳ���з���Сһ��
            byte[] c = new byte[lineSeparator.length];
            // �ڻ�ȡ��ָ�������Ͷ����ĵ�֮ǰ,���ĵ�ĩβ��ǰ�ƶ�ָ��,�����ĵ�ÿһ���ֽ�
            for (long pointer = rf.length(), lineSeparatorNum = 0; pointer >= 0 && lineSeparatorNum < rows;) {
                // �ƶ�ָ��
                rf.seek(pointer--);
                // ��ȡ����
                int readLength = rf.read(c);
                if (readLength != -1 && Arrays.equals(lineSeparator,c)) {
                    lineSeparatorNum++;
                }
                //ɨ������Ȼû���ҵ��㹻������,��ָ���0
                if (pointer == -1 && lineSeparatorNum < rows) {
                    rf.seek(0);
                }
            }
            byte[] tempbytes = new byte[(int) (rf.length() - rf.getFilePointer())];
            rf.readFully(tempbytes);
            return new String(tempbytes, charset);
        }
    }
}
