package mx.gob.profeco.atlas.cucumber.stepdefs;

import mx.gob.profeco.atlas.AtlasApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AtlasApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
