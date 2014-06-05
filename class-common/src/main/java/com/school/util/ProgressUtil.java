package com.school.util;


import org.apache.commons.fileupload.ProgressListener;

public class ProgressUtil implements ProgressListener {
	// �ļ��ܳ���
	private long length = 0;
	// ���ϴ����ļ�����
	private long currentLength = 0;
	// �ϴ��Ƿ����
	private boolean isComplete = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.fileupload.ProgressListener#update(long, long,
	 * int)
	 */
	public void update(long bytesRead, long contentLength, int items) {
		this.currentLength = bytesRead;
	}

	/**
	 * the getter method of length
	 * 
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * the getter method of currentLength
	 * 
	 * @return the currentLength
	 */
	public long getCurrentLength() {
		return currentLength;
	}

	/**
	 * the getter method of isComplete
	 * 
	 * @return the isComplete
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * the setter method of the length
	 * 
	 * @param length
	 *            the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/**
	 * the setter method of the currentLength
	 * 
	 * @param currentLength
	 *            the currentLength to set
	 */
	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}

	/**
	 * the setter method of the isComplete
	 * 
	 * @param isComplete
	 *            the isComplete to set
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
} 
