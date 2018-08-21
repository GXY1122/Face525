package com.gxy.entity;

public class Sim_m {
	private double[][] sim;
	private double max;
	private int height;
	private int width;
	private int[] pix;

	public int[] getPix() {
		return pix;
	}

	public void setPix(int[] pix) {
		this.pix = pix;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double[][] getSim() {
		return sim;
	}

	public void setSim(double[][] sim) {
		this.sim = sim;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

}
