package com.ixinnuo.financial.knowledge.algorithm.bigfile;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.CyclicBarrier;

/**
 * 读取切分后的小文件
 * 
 * @author aisino
 *
 */
public class SliceReaderTask implements Runnable {
	/**
	 * 小文件的开始位置
	 */
	private long start;
	/**
	 * 小文件的长度
	 */
	private long sliceSize;
	/**
	 * 缓存字节长度
	 */
	private int bufferSize;
	/**
	 * 缓存的字节数据
	 */
	private byte[] readBuff;
	/**
	 * 读取文件随机内容
	 */
	private RandomAccessFile rAccessFile;
	/**
	 * 线程计数器
	 */
	private CyclicBarrier cyclicBarrier;
	/**
	 * 回调接口，处理每行内容，自行实现
	 */
	private IHandle handle;
	/**
	 * 字符编码
	 */
	private String charset;

	/**
	 * @param cyclicBarrier
	 * @param charset
	 * @param rAccessFile
	 * @param handle
	 * @param start
	 *            read position (include)
	 * @param end
	 *            the position read to(include)
	 */
	public SliceReaderTask(StartEndPair pair, CyclicBarrier cyclicBarrier, int bufferSize,
			String charset, RandomAccessFile rAccessFile, IHandle handle) {
		this.start = pair.start;
		this.sliceSize = pair.end - pair.start + 1;
		this.bufferSize = bufferSize;
		this.readBuff = new byte[bufferSize];
		this.cyclicBarrier = cyclicBarrier;
		this.charset = charset;
		this.rAccessFile = rAccessFile;
		this.handle = handle;
	}

	@Override
	public void run() {
		try {
			//内存映射文件
			MappedByteBuffer mapBuffer = rAccessFile.getChannel().map(MapMode.READ_ONLY, start, this.sliceSize);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// 一次读一个缓存长度的字节
			for (int offset = 0; offset < sliceSize; offset += bufferSize) {
				int readLength;
				if (offset + bufferSize <= sliceSize) {
					readLength = bufferSize;
				} else {
					readLength = (int) (sliceSize - offset);
				}
				mapBuffer.get(readBuff, 0, readLength);
				// 读取到的缓存长度，挨个处理每个字节，到行尾输出，不到行尾就则累加到输出流，
				// 即使缓存长度落在行中间，也不影响累加，变量定义在读取缓存前
				for (int i = 0; i < readLength; i++) {
					byte tmp = readBuff[i];
					if (tmp == '\n' || tmp == '\r') {
						// 到行尾时，把整行内容输出
						handle(bos.toByteArray());
						// 重置输出流
						bos.reset();
					} else {
						// 到行尾前的内容写到输出流中
						bos.write(tmp);
					}
				}
			}
			// 小文件处理后，确认字节流数据全部处理完
			if (bos.size() > 0) {
				handle(bos.toByteArray());
			}
			// 关闭流
			bos.close();
			rAccessFile.close();
			// 等待其他线程调用await，保证所有线程都执行完毕再继续
			System.out.println(Thread.currentThread().getName() + "执行完毕，等待其他线程...");
			cyclicBarrier.await();
			System.out.println(Thread.currentThread().getName() + "执行完毕，其他线程也完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handle(byte[] bytes) throws UnsupportedEncodingException {
		String line = null;
		if (this.charset == null) {
			line = new String(bytes);
		} else {
			line = new String(bytes, charset);
		}
		if (line != null && !"".equals(line)) {
			this.handle.handleLine(line);
			BigFileReader.counter.incrementAndGet();
		}
	}

}