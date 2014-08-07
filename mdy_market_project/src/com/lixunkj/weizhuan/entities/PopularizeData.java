package com.lixunkj.weizhuan.entities;

import java.io.Serializable;

public class PopularizeData implements Serializable {

	private static final long serialVersionUID = 5641182864576942426L;

	public String urlandroid;
	public String urlios;

	@Override
	public String toString() {
		return "PopularizeData [urlandroid=" + urlandroid + ", urlios="
				+ urlios + "]";
	}

}
