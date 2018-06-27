package com.fiwio.iot.demeter.configuration;

public interface ConfigurationProvider {
  BranchValveParameters getBranchParameters(String branch);
}
