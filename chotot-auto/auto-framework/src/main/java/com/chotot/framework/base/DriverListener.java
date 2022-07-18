package com.chotot.framework.base;

import java.util.Arrays;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

@Log4j
public class DriverListener implements WebDriverEventListener {

    @Override
    public void beforeAlertAccept(final WebDriver webDriver) {
        log.info("Attempting to accept an alert");
    }

    @Override
    public void afterAlertAccept(final WebDriver webDriver) {
        log.info("Accepted the alert");
    }

    @Override
    public void afterAlertDismiss(final WebDriver webDriver) {
        log.info("Dismissed the alert");
    }

    @Override
    public void beforeAlertDismiss(final WebDriver webDriver) {
        log.info("Attempting to dismiss an alert");
    }

    @Override
    public void beforeNavigateTo(final String s, final WebDriver webDriver) {
        log.info(String.format("Attempting navigation to: %s ", s));
    }

    @Override
    public void afterNavigateTo(final String s, final WebDriver webDriver) {
        log.info("Navigated to: " + s);
    }

    @Override
    public void beforeNavigateBack(final WebDriver webDriver) {
        log.info("Invoking backwards navigation of the browser");
    }

    @Override
    public void afterNavigateBack(final WebDriver webDriver) {
        log.info("Navigated the browser back");
    }

    @Override
    public void beforeNavigateForward(final WebDriver webDriver) {
        log.info("Invoking forwards navigation of the browser");
    }

    @Override
    public void afterNavigateForward(final WebDriver webDriver) {
        log.info("Navigated the browser forward");
    }

    @Override
    public void beforeNavigateRefresh(final WebDriver webDriver) {
        log.info("Invoking a browser refresh");
    }

    @Override
    public void afterNavigateRefresh(final WebDriver webDriver) {
        log.info("Successfully refreshed the browser");
    }

    @Override
    public void beforeFindBy(final By by, final WebElement webElement, final WebDriver webDriver) {
        log.info(String.format("Looking for element by: %s ", by));
    }

    @Override
    public void afterFindBy(final By by, final WebElement webElement, final WebDriver webDriver) {
        log.info(String.format("Found element by: %s ", by));
    }

    @Override
    public void beforeClickOn(final WebElement webElement, final WebDriver webDriver) {
        log.info(String.format("Attempting to click on element: %s ", webElement));
    }

    @Override
    public void afterClickOn(final WebElement webElement, final WebDriver webDriver) {
        log.info(String.format("Clicked on element: %s ", webElement));
    }

    @Override
    public void beforeChangeValueOf(final WebElement webElement, final WebDriver webDriver,
        final CharSequence[] charSequences) {
        log.info(String.format("Attempting to set value of: %s  on element: %s ",
            logValuesAvoidingEmpty(charSequences),
            webElement));
    }

    @Override
    public void afterChangeValueOf(final WebElement webElement, final WebDriver webDriver,
        final CharSequence[] charSequences) {
        log.info(String.format("Set the value of an element to: %s ", logValuesAvoidingEmpty(charSequences)));
    }

    private String logValuesAvoidingEmpty(final CharSequence[] charSequences) {
        if (null != charSequences) {
            return Arrays.toString(charSequences);
        }
        return "[EMPTY-VALUE]";
    }

    @Override
    public void beforeScript(final String s, final WebDriver webDriver) {
        log.info(String.format("Executing javascript: %s ", s));
    }

    @Override
    public void afterScript(final String s, final WebDriver webDriver) {
        log.info(String.format("Executed javascript: %s ", s));
    }

    @Override
    public void beforeSwitchToWindow(final String s, final WebDriver webDriver) {
        log.info(String.format("Attempting to switch to window: %s ", s));
    }

    @Override
    public void afterSwitchToWindow(final String s, final WebDriver webDriver) {
        log.info(String.format("Switched window to: %s ", s));
    }

    @Override
    public void onException(final Throwable throwable, final WebDriver webDriver) {
        log.info(String.format("An exception occurred: %s ", throwable.getMessage()));

    }

    @Override
    public <X> void beforeGetScreenshotAs(final OutputType<X> outputType) {
        log.info("About to take a screenshot..");
    }

    @Override
    public <X> void afterGetScreenshotAs(final OutputType<X> outputType, final X x) {
        log.info("Screenshot captured..");
    }

    @Override
    public void beforeGetText(final WebElement webElement, final WebDriver webDriver) {
        log.info(String.format("Attempting to read text from a web element: %s ", webElement));
    }

    @Override
    public void afterGetText(final WebElement webElement, final WebDriver webDriver, final String s) {
        log.info(String.format("The text was: %s ", s));
    }
}

