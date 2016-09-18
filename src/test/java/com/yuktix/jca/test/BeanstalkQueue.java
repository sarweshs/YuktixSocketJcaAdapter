package com.yuktix.jca.test;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BeanstalkQueue {

	private Socket socket;
	final byte[] CRLF = { '\r', '\n' };

	public BeanstalkQueue() throws Exception {
		socket = new Socket("127.0.0.1", 11300);
	}
	
	public BeanstalkQueue(Socket socket) throws Exception {
		this.socket = socket;
	}

	public void put(String tube, int timeToRun, byte[] message) {

		// from within GFv4 : should we use JCA?
		// open socket to beanstalkd and close the socket after putting the
		// message
		// extra cleanup won;t be required for socket open/close pattern.
		// goals:
		// socket timeout
		// socket request <-> response pattern
		// socket pooling to avoid open/close on each request.
		//
		String response = null ;
		
		try {

			String command = String.format("use %s",tube) ;
			response = sendCommand(command.getBytes(), null);
			System.out.println(response);
			
			if(message == null)
			{
				command = String.format("put 1 1 %d %d",timeToRun, 0);
			}
			else
			{
				command = String.format("put 1 1 %d %d",timeToRun, message.length);
			}
			response = sendCommand(command.getBytes(), message);
			System.out.println(response);
			
			
		} catch (Exception ex) {
			System.out.println("error1: " + ex.getMessage());
		}
		
	}

	public String sendCommand(byte[] command, byte[] payload) {

		InputStream is = null;
		OutputStream os = null;
		int ch = 0 ;
		int lastch = 0 ;
		String response = null ;
		
		try {
			
			System.out.println("command=" + new String(command));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			
			// wait 1100 ms for socket to reply
			// socket.setSoTimeout(1100);

			os = socket.getOutputStream();
			baos.write(command);
			baos.write(CRLF);
			
			if(payload != null ) {
				baos.write(payload);
				baos.write(CRLF);
			}
			
			os = socket.getOutputStream();
			os.write(baos.toByteArray());
			os.flush();
			baos.close();
			
			is = socket.getInputStream();
			
			while( (ch = is.read()) != -1 ) {
				
				bais.write((byte)ch);
				if(lastch == '\r' && ch == '\n') {
					// time to leave!
					break ;
				}
				
				lastch = ch ;
				
			}
			
			// remove trailing CRLF from string 
			response = new String(bais.toByteArray(),0 , (bais.size()-2));
			bais.close();

		} catch (Exception ex) {
			
			System.out.println("error2:" + ex.getMessage());
			response = null ;
		}
		
		return response;
	}
}
