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
			
			//不存在返回空
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
	
	//顺序读取加载最后一行
	public String loadLastMessage(String fileName) {
		String count = "";
		String str = "";
		try {
			File path = new File(Config.MESSAGE_PATH + fileName + ".txt");
			System.out.println("loadMessage : " + path);
			
			//不存在返回空
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
			//不存在创建文件
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
			return "暂无聊天消息";
		}
		return lastMessage;
	}
	
	//文件指针 获取最后几行
	public String readLastRows(String filename, Charset charset, int rows) throws IOException {
        charset = charset == null ? Charset.defaultCharset() : charset;
        byte[] lineSeparator = System.getProperty("line.separator").getBytes();
        try (RandomAccessFile rf = new RandomAccessFile(filename, "r")) {
            // 每次读取的字节数要和系统换行符大小一致
            byte[] c = new byte[lineSeparator.length];
            // 在获取到指定行数和读完文档之前,从文档末尾向前移动指针,遍历文档每一个字节
            for (long pointer = rf.length(), lineSeparatorNum = 0; pointer >= 0 && lineSeparatorNum < rows;) {
                // 移动指针
                rf.seek(pointer--);
                // 读取数据
                int readLength = rf.read(c);
                if (readLength != -1 && Arrays.equals(lineSeparator,c)) {
                    lineSeparatorNum++;
                }
                //扫描完依然没有找到足够的行数,将指针归0
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
