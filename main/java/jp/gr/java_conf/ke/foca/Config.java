package jp.gr.java_conf.ke.foca;

import java.util.HashSet;
import java.util.Set;

public class Config {

	public static ConfigBuilder builder() {
		return new ConfigBuilder();
	}

	private Set<Mapper> mappers = new HashSet<Mapper>();

	Config() {}

	void add(Mapper mapper) {
		mappers.add(mapper);
	}

	Iterable<Mapper> mappers() {
		return mappers;
	}

	public String toString() {
		return mappers.toString();
	}

}
