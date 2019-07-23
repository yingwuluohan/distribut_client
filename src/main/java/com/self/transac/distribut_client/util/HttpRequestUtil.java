package com.self.transac.distribut_client.util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.chat.client.DefaultHttpClient;


/**
 * 封装常用的HTTP请求的GET/POST方法
 */
public class HttpRequestUtil {
	private static Logger log = Logger.getLogger(HttpRequestUtil.class);
	private static final int defaultConnectTimeout = 3000;
	private static final int defaultSocketTimeout = 5000;
	/**
	 * 向指定URL发送GET方法的请求(连接超时时间为30秒)
	 * 
	 * @param url
	 *            发送请求的URL（包括请求参数）
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendGet(String url) {
		// 连接超时时间为30秒
		return sendGet(url, 30 * 1000);
	}

	/**
	 * 向指定 URL 发送POST方法的请求(连接超时时间为30秒)
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param) {
		// 连接超时时间为30秒
		return sendPost(url, param, 30 * 1000, 30 * 1000);
	}

	/**
	 * 向指定 URL 发送POST方法的请求 (读取超时时间为30秒)
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param conTimeout
	 *            连接超时时间
	 * @return
	 */
	public String sendPost(String url, String param, int conTimeout) {
		// 连接超时时间为30秒
		return sendPost(url, param, conTimeout, 30 * 1000);
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL（包括请求参数）
	 * @param timeout
	 *            连接超时时间
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendGet(String url, int timeout) {
		if (url == null || url.trim().length() == 0) {
			return null;
		}
		BufferedReader in = null;
		HttpURLConnection conn = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");
			conn.setConnectTimeout(timeout);
			// 建立实际的连接
			conn.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
//			logger.error("发送GET请求过程中出错：" + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param
	 *
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param, int conTimeout, int readTimeout) {
		if (url == null || url.trim().length() == 0) {
			return null;
		}

		PrintWriter out = null;
		BufferedReader in = null;
		HttpURLConnection conn = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");

			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
			conn.setRequestProperty("contentType", "GBK");

			conn.setConnectTimeout(conTimeout);
			conn.setReadTimeout(readTimeout);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
//			logger.error("发送POST请求过程中出错：" + e.getMessage(), e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result.toString();
	}


	public String sendGet(String url, int timeout, Map<String, Object> httpHeaders) {
		if (url == null || url.trim().length() == 0) {
			return null;
		}


		HttpURLConnection conn = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");
//            conn.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
			conn.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");
			conn.setConnectTimeout(timeout);

			// 设置HTTP头信息
			if (httpHeaders != null && httpHeaders.size() > 0) {
				for (String key : httpHeaders.keySet()) {
					String value = (String)httpHeaders.get(key);
					if (key == null || key.trim().length() == 0 || value == null || value.trim().length() == 0) {
						continue;
					}
					conn.addRequestProperty(key, value);
				}
			}

			// 建立实际的连接
			conn.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));){
				String line;
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
			}

		} catch (Exception e) {
			log.error("发送GET请求过程中出错：" + e.getMessage(), e);
		} finally {
			try {
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result.toString();
//        return new String ( bytes );
	}
}