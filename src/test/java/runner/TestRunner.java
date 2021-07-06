package runner;


import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/Features/login.feature", 
glue= {"steps"},strict = true,
plugin = {"json:target/cucumber.json"})

public class TestRunner {

	
}
