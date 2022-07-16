package com.mu.server.chat_server.backstage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import com.mu.common.Message;
import com.mu.common.MessageType;
import com.mu.common.User;
import com.mu.server.chat_server.tools.ServerThreadCollection;
import com.mu.server.chat_server.view.Server_Frame;

/**
 * 服务器后台的处理类
 * @author Administrator
 *
 */
public class ServerManage implements Runnable{
	private  ServerSocket ss ;
	private ObjectInputStream ois;
	private ObjectOutputStream os;
	private Server_Connect_Database server;
	public  ServerManage() {
		Message m = new Message();
		m.setContent("服务器在9999端口监听..\r\n");
		m.setMessageType(MessageType.CommonMessage);
		Server_Frame.showMessage(m);
		try {
			ss = new ServerSocket(9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				try {
					//接受客户端发送过来的信息
					Socket s = null;
					try{
						s = ss.accept();
					}catch(SocketException e){
						if(s!=null){
							s.close();
						}
						break;
					}
					ois = new ObjectInputStream(s.getInputStream());
					User user = (User)ois.readObject();

					//如果读取到的是用户登陆的信息
					if(user.getType().equals(MessageType.UserLogin)){
						server = new Server_Connect_Database();
						Message mess = new Message();
						//判断是否重复登陆
						if(server.Check_IsLogin(user)){
							//登陆过了
							mess.setMessageType(MessageType.Login);
							os = new ObjectOutputStream(s.getOutputStream());
							os.writeObject(mess);
							s.close();
						}else{
							//没登陆过
							mess.setMessageType(MessageType.NoLogin);
							os = new ObjectOutputStream(s.getOutputStream());
							os.writeObject(mess);
							//如果登陆成功
							if(server.CheckLogin(user) && server.Update_IsLogin(user, 1)){//更改成登陆了
								mess.setMessageType(MessageType.Login_Success);
								os = new ObjectOutputStream(s.getOutputStream());
								os.writeObject(mess);		
								//登陆成功后，单独开一个线程为客户端服务，并将该线程放入集合,以便取出遍历
								ServerConClient scc = new ServerConClient(s,user.getName());
								//添加入集合
								ServerThreadCollection.addServerConnectClientThreadCollection(user.getName(), scc);
								//服务器更新在线用户列表
								scc.ServerUpdataOnline();
								//启动线程序
								Thread t = new Thread(scc);
								t.start();
								//提醒其他用户更新在线列表
								scc.UpdataOnline();
							}else{
								mess.setMessageType(MessageType.Login_Fail);
								os = new ObjectOutputStream(s.getOutputStream());
								os.writeObject(mess);
								s.close();
							}
						}
					}
					
				}catch(SocketException e){
					e.printStackTrace();
					break;
				}catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();		
		}
	}
	
	
	
	
	/**
	 * 发送系统消息的方法
	 * @param message 系统消息
	 */
	public static void Send_SystemMessage(String message){
		Message mess = new Message();
		mess.setContent(message);
		mess.setMessageType(MessageType.System_Messages);
		//获得在线用户
		String string = ServerThreadCollection.GetOnline();
		String[] strings = string.split(" ");
		String Name = null;
		for(int i=0;i<strings.length;i++){
			Name = strings[i];
			//设置接收用户
			mess.setGetter(Name);
			String time = (new Date().toLocaleString());
			mess.setTime(time);
			//获得其他服务器端与客户端通信的线程
			ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(Name);
			try {
				ObjectOutputStream os = new ObjectOutputStream(sccc.getS().getOutputStream());
				os.writeObject(mess);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 关闭服务器的方法
	 */
	public void CloseServer(){
		try {
			//获得在线用户
			String string = ServerThreadCollection.GetOnline();
			String[] strings = string.split(" ");
			for(int i=0;i<strings.length;i++){
				//获得其他服务器端与客户端通信的线程
				ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(strings[i]);
				if(sccc!=null){
					sccc.CloseThread();
				}
			}
			this.ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
