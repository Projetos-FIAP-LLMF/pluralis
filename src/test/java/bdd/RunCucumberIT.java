package bdd;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "bdd.steps")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/cucumber-report.html, json:target/cucumber.json")
public class RunCucumberIT {}
