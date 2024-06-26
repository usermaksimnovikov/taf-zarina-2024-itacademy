package ru.zarina.listener;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.zarina.ui.driver.DriverSingleton;

public class UiTestListener implements TestWatcher {
    private static void saveScreenshot() {
        Allure.getLifecycle().addAttachment("screenshot", "image/png", "png",
                ((TakesScreenshot) DriverSingleton.getDriver()).getScreenshotAs(OutputType.BYTES));
    }

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("Test successful " + context.getDisplayName());
        saveScreenshot();
        DriverSingleton.quitDriver();
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.info("Test aborted " + context.getDisplayName());
        saveScreenshot();
        DriverSingleton.quitDriver();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.info("Test failed " + context.getDisplayName() + " " + cause.getMessage());
        saveScreenshot();
        DriverSingleton.quitDriver();
    }
}
