package org.rgbridge.rgbridge.entities;

import org.rgbridge.commons.effect.EffectParameterDefinition;
import org.rgbridge.commons.effect.RegisterEffectJson;

import java.util.HashSet;

public class Effect {
	private String name;
	private String uuid;
	private HashSet<EffectParameterDefinition> params;

	public Effect() {
	}

	public Effect(String name, String uuid, HashSet<EffectParameterDefinition> params) {
		this.name = name;
		this.uuid = uuid;
		this.params = params;
	}

	public Effect(RegisterEffectJson e) {
		this.name = e.getName();
		this.uuid = e.getUuid();
		this.params = e.getParameters();
	}

	public HashSet<EffectParameterDefinition> getParams() {
		return params;
	}

	public void setParams(HashSet<EffectParameterDefinition> params) {
		this.params = params;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
