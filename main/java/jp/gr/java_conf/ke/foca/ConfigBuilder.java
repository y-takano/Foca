package jp.gr.java_conf.ke.foca;

public class ConfigBuilder {

	private Config config = new Config();

	ConfigBuilder() {}

	public ConfigBuilder map(Mapper mapper) {
		config.add(mapper);
		return this;
	}

	public Config build() {
		return config;
	}
}
